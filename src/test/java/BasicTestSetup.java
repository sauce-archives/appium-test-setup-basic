import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.Random;

public class BasicTestSetup {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        /* These are the capabilities we must provide to run our test on TestObject. */
        capabilities.setCapability("testobject_api_key", "60286D0A4E704A2FB6C697C52BF437AC");
        capabilities.setCapability("testobject_app_id", "1");
        capabilities.setCapability("testobject_device", "Motorola_Moto_G_2nd_gen_real");

        /* The driver will take care of establishing the connection, so we must provide
        * it with the correct endpoint and the requested capabilities. */
        driver = new AndroidDriver(new URL("https://app.testobject.com:443/api/appium/wd/hub"), capabilities);

        /* Print out the live view and test report URLs */
        System.out.println(driver.getCapabilities().getCapability("testobject_test_live_view_url"));
        System.out.println(driver.getCapabilities().getCapability("testobject_test_report_url"));
    }

    @After
    public void tearDown(){
        driver.quit();
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