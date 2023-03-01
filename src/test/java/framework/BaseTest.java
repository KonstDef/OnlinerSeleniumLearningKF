package framework;

import org.testng.annotations.*;

@Listeners(TestListener.class)
public class BaseTest {
    public static PropertyReader properties = new PropertyReader("config.properties");
    public Browser driver = new Browser();

    @BeforeMethod(alwaysRun = true)
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
