import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BasicWebTestSetup {

    private AppiumDriver driver;
	DesiredCapabilities capabilities;

    /* This is the setup that will be run before the test. */
    @Before
    public void setUp() throws Exception {
		capabilities = new DesiredCapabilities();
        capabilities.setCapability("testobject_api_key", System.getenv("TESTOBJECT_API_KEY_WEB"));

		setOptionalCapability("TESTOBJECT_APP_ID");
		setOptionalCapability("TESTOBJECT_DEVICE");
		setOptionalCapability("deviceName", "DEVICE_NAME");
		setOptionalCapability("TESTOBJECT_APPIUM_VERSION");
		setOptionalCapability("TESTOBJECT_CACHE_DEVICE");
		setOptionalCapability("TESTOBJECT_SESSION_CREATION_TIMEOUT");
		setOptionalCapability("TESTOBJECT_SESSION_CREATION_RETRY");

        // We generate a random UUID for later lookup in logs for debugging
        String testUUID = UUID.randomUUID().toString();
        System.out.println("TestUUID: " + testUUID);
        capabilities.setCapability("testobject_testuuid", testUUID);

        System.out.println(capabilities.toString());
        driver = new AndroidDriver(new URL(System.getenv("APPIUM_URL")), capabilities);

        System.out.println(driver.getCapabilities().getCapability("testobject_test_report_url"));
        System.out.println(driver.getCapabilities().getCapability("testobject_test_live_view_url"));
    }

    @After
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }

    @Test
    public void openWebpageAndTakeScreenshot() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String url = "https://www.google.com";

        driver.get(url);
        takeScreenshot();
    }

    private void takeScreenshot() {
        try {
            driver.getScreenshotAs(OutputType.FILE);
        } catch (Exception e) {
            System.out.println("Exception while saving the file " + e);
        }
    }

	private void setOptionalCapability(String var) {
		Optional.ofNullable(System.getenv(var.toUpperCase()))
				.filter(env -> !env.isEmpty())
				.ifPresent(data -> capabilities.setCapability(var, data));
	}

	private void setOptionalCapability(String desiredCapabilityName, String environmentVariableName) {
		Optional.ofNullable(System.getenv(environmentVariableName))
				.filter(env -> !env.isEmpty())
				.ifPresent(value -> capabilities.setCapability(desiredCapabilityName, value));
	}
}
