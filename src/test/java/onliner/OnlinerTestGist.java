package onliner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

/*
 * 1. Перейти на вкладку Каталог
 * 2. Выбрать Компьютеры и сети в меню навигации
 * 3.  В подменю навести на  Ноутбуки, компьютеры, мониторы
 * 4. Перйти на страницу Игровые ноутбуки*/


public class OnlinerTestGist{
    private static final String NAV_MENU_ITEM = "//span[@class='b-main-navigation__text' and text()='%s']";
    private static final By PAGE_TITLE_XPATH = By.xpath("//div[@class='catalog-navigation__title' and text()='Каталог']");
    private static final String NAVIGATE_MENU = "//span[@class='catalog-navigation-classifier__item-title-wrapper' and contains(text(),'%s')]";
    private static final String NAV_SUBMENU_CATEGORY = "//div[@class='catalog-navigation-list__category']//span[@class='catalog-navigation-list__dropdown-title' and contains(text(),'Игровые ноутбуки')]";
    private static final String NAV_SUBMENU_ITEM = "//div[@class='catalog-navigation-list__aside-title' and contains(text(),'Ноутбуки, компьютеры, мониторы')]";


    WebDriverWait wait;

    @Test
    public void onlinerTestGist() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://onliner.by/");

        WebElement navMenu = driver.findElement(By.xpath(String.format(NAV_MENU_ITEM, "Каталог")));
        navMenu.click();

        WebElement pageTitle = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(driver.findElement(PAGE_TITLE_XPATH)));
        if (!pageTitle.isDisplayed()) {
            System.out.println("ERROR: Страница Каталог не была загружена!!!");
        }
        WebElement navMenuOnCatalogPage = driver.findElement(By.xpath(String.format(NAVIGATE_MENU, "Компьютеры")));
        navMenuOnCatalogPage.click();

        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath(NAV_SUBMENU_ITEM)))
                .moveToElement(driver.findElement(By.xpath(NAV_SUBMENU_CATEGORY)))
                .click()
                .build()
                .perform();

        driver.quit();
    }
}
