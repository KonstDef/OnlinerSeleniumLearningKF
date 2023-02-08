package Onliner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class OnlinerTest2 {

    private static final String NAV_MENU_ITEM = "//span[@class='b-main-navigation__text' and text()='%s']";
    private static final By PAGE_TITLE_XPATH = By.xpath("//div[@class='catalog-navigation__title' and text()='Каталог']");
    private static final String NAVIGATE_MENU = "//span[@class='catalog-navigation-classifier__item-title-wrapper' and contains(text(),'%s')]";
    private static final String NAV_SUBMENU_CATEGORY = "//div[@class='catalog-navigation-list__category']//span[@class='catalog-navigation-list__dropdown-title' and contains(text(),'Игровые ноутбуки')]";
    private static final String NAV_SUBMENU_ITEM = "//div[@class='catalog-navigation-list__aside-title' and contains(text(),'Ноутбуки, компьютеры, мониторы')]";

    WebDriverWait wait;

    @Test
    public void onlinerTest(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://onliner.by/");

        WebElement navMenu = driver.findElement(By.xpath(String.format(NAV_MENU_ITEM,"Каталог")));
        navMenu.click();

        WebElement pageTitle = driver.findElement(PAGE_TITLE_XPATH);
        pageTitle.click();

        new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement catalogNavMenu = driver.findElement(By.xpath(String.format(NAVIGATE_MENU,"Компьютеры")));
        catalogNavMenu.click();

        WebElement catalogSubCat = driver.findElement(By.xpath(NAV_SUBMENU_ITEM));

        WebElement catalogSubCatItem = driver.findElement(By.xpath(String.format(NAV_SUBMENU_CATEGORY,"Игровые")));
        new Actions(driver).moveToElement(catalogSubCat)
                .moveToElement(catalogSubCatItem)
                .click().build().perform();

        driver.quit();
    }

}
