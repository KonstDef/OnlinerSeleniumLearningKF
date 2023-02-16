package framework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    public static PropertyReader properties = new PropertyReader("config.properties");
    public Browser driver = new Browser();

    @BeforeMethod
    public void setup(){
        driver.getInstance();
        driver.windowMaximize();
        driver.getPage(properties.getProperty("base.URL"));
    }

    @AfterMethod(alwaysRun = true, description = "Closing browser")
    public void tearDown(){
        driver.exit();
    }
}
