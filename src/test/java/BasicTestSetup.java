import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.Optional;
import java.util.UUID;

public class BasicTestSetup {

	private AppiumDriver driver;
	DesiredCapabilities capabilities;

	private final static String EXPECTED_RESULT_FOUR = "4";
	private final static String EXPECTED_RESULT_NAN = "NaN";

	@Before
	public void setUp() throws Exception {

		capabilities = new DesiredCapabilities();
		capabilities.setCapability("testobject_api_key", System.getenv("TESTOBJECT_API_KEY"));

		setOptionalCapability("TESTOBJECT_APP_ID");
		setOptionalCapability("TESTOBJECT_DEVICE");
		setOptionalCapability("deviceName", "DEVICE_NAME");
		setOptionalCapability("platformVersion", "PLATFORM_VERSION");
		setOptionalCapability("automationName", "AUTOMATION_NAME");
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

	/* We disable the driver after EACH test has been executed. */
	@After
	public void tearDown() {
		driver.quit();
	}

	/* A simple addition, it expects the correct result to appear in the result field. */
	@Test
	public void twoPlusTwoOperation() {

        /* Get the elements. */
		MobileElement buttonTwo = (MobileElement) (driver.findElement(By.id("net.ludeke.calculator:id/digit2")));
		MobileElement buttonPlus = (MobileElement) (driver.findElement(By.id("net.ludeke.calculator:id/plus")));
		MobileElement buttonEquals = (MobileElement) (driver.findElement(By.id("net.ludeke.calculator:id/equal")));

		MobileElement resultField = (MobileElement) (driver.findElement(By.xpath("//android.widget.EditText[1]")));
        /* Add two and two. */
		buttonTwo.click();
		buttonPlus.click();
		buttonTwo.click();
		buttonEquals.click();

        /* Check if within given time the correct result appears in the designated field. */
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.textToBePresentInElement(resultField, EXPECTED_RESULT_FOUR));

	}

	/* A simple zero divided by zero operation. */
	@Test
	@Ignore /* This test sometimes fails, depending on the device. */
	public void zerosDivisionOperation() {

        /* Get the elements. */
		MobileElement digitZero = (MobileElement)(driver.findElement(By.id("net.ludeke.calculator:id/digit0")));
		MobileElement buttonDivide = (MobileElement)(driver.findElement(By.id("net.ludeke.calculator:id/div")));
		MobileElement buttonEquals = (MobileElement)(driver.findElement(By.id("net.ludeke.calculator:id/equal")));

		MobileElement resultField = (MobileElement) (driver.findElement(By.xpath("//android.widget.EditText[1]")));
        /* Divide zero by zero. */
		digitZero.click();
		buttonDivide.click();
		digitZero.click();
		buttonEquals.click();

        /* Check if within given time the correct error message appears in the designated field. */
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.textToBePresentInElement(resultField, EXPECTED_RESULT_NAN));

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
