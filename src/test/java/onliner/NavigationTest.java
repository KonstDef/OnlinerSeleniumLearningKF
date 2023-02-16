package onliner;

import framework.BaseTest;
import framework.elements.Label;
import framework.elements.TextBox;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class NavigationTest extends BaseTest {
    private static final String NAV_MENU_ITEM = "//span[@class='b-main-navigation__text' and text()='%s']";
    private static final TextBox PAGE_TITLE_XPATH = new TextBox(By.xpath("//div[@class='catalog-navigation__title' and text()='Каталог']"));
    private static final String NAVIGATE_MENU = "//span[@class='catalog-navigation-classifier__item-title-wrapper' and contains(text(),'%s')]";
    private static final Label NAV_SUBMENU_CATEGORY = new Label(By.xpath("//div[@class='catalog-navigation-list__category']//span[@class='catalog-navigation-list__dropdown-title' and contains(text(),'Игровые ноутбуки')]"));
    private static final Label NAV_SUBMENU_ITEM = new Label(By.xpath("//div[@class='catalog-navigation-list__aside-title' and contains(text(),'Ноутбуки, компьютеры, мониторы')]"));

    @Test
    public void onlinerNavTest() {
        Label mainMenuItem = new Label(By.xpath(String.format(NAV_MENU_ITEM, "Каталог")));
        mainMenuItem.clickAndWait();

        Assert.assertTrue(PAGE_TITLE_XPATH.isDisplayed(),"\n###Catalog page is not loaded\n###Expected: Catalog page is loaded\n");

        Label navMenuOnCatalogPage = new Label(By.xpath(String.format(NAVIGATE_MENU, "Компьютеры")));
        navMenuOnCatalogPage.click();

        NAV_SUBMENU_ITEM.moveTo();
        NAV_SUBMENU_CATEGORY.moveAndClickByAction();
    }
}
