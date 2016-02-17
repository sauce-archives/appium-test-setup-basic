import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testobject.appium.common.TestObject;
import org.testobject.appium.junit.TestObjectAppiumSuite;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.net.URL;
import java.util.Random;

@TestObject(testObjectApiKey = "8832375AC85E41B8A749DD8F733F3DF7", testObjectSuiteId = 7)
@RunWith(TestObjectAppiumSuite.class)
public class BasicTestSetup {

    private AppiumDriver driver;

    @Rule
    public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        /* These are the capabilities we must provide to run our test on TestObject. */
        capabilities.setCapability("testobject_api_key", resultWatcher.getApiKey());
        capabilities.setCapability("testobject_test_report_id", resultWatcher.getTestReportId());

        /* The driver will take care of establishing the connection, so we must provide
        * it with the correct endpoint and the requested capabilities. */
        driver = new AndroidDriver(new URL("https://app.testobject.com:443/api/appium/wd/hub"), capabilities);

        resultWatcher.setAppiumDriver(driver);

        /* Print out the live view and test report URLs */
        System.out.println(driver.getCapabilities().getCapability("testobject_test_live_view_url"));
        System.out.println(driver.getCapabilities().getCapability("testobject_test_report_url"));
    }

    @Test
    public void AppFilterTest() {

        new WebDriverWait(driver, 60)
                .until(ExpectedConditions.presenceOfElementLocated(By.name("Mein Bauprojekt")));

        driver.navigate().back();

        new WebDriverWait(driver, 60)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("com.bosch.tt.de.mybuildingproject:id/editBudget"))).sendKeys("1234");

        new WebDriverWait(driver, 60)
                .until(ExpectedConditions.presenceOfElementLocated(By.name("weiter"))).click();

        new WebDriverWait(driver, 60)
                .until(ExpectedConditions.presenceOfElementLocated(By.name("Gewerke anlegen")));

    }

}