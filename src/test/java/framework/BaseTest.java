package framework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    public WebDriver driver;
    public static PropertyReader properties = new PropertyReader("config.properties");

    @BeforeMethod
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();

        driver.get(properties.getProperty("base.URL"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(properties.getIntProperty("timeout")));
    }

    @AfterMethod(alwaysRun = true, description = "Closing browser")
    public void tearDown(){
        if(driver!=null){
            driver.quit();
        }
    }
}
