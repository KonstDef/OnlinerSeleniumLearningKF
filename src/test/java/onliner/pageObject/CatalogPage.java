package onliner.pageObject;

import framework.elements.Label;
import framework.elements.TextBox;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

public class CatalogPage {
    private static final TextBox PAGE_TITLE_XPATH = new TextBox(By.xpath("//div[@class='catalog-navigation__title' and text()='Каталог']"));
    private static final String NAVIGATE_MENU = "//span[@class='catalog-navigation-classifier__item-title-wrapper' and contains(text(),'%s')]";
    private static final Label NAV_SUBMENU_CATEGORY = new Label(By.xpath("//div[@class='catalog-navigation-list__category']//span[@class='catalog-navigation-list__dropdown-title' and contains(text(),'Игровые ноутбуки')]"));
    private static final Label NAV_SUBMENU_ITEM = new Label(By.xpath("//div[@class='catalog-navigation-list__aside-title' and contains(text(),'Ноутбуки, компьютеры, мониторы')]"));

    @Step("Check page loaded")
    public void isPageOpened(){
        Assert.assertTrue(PAGE_TITLE_XPATH.isDisplayed(),"\n###Catalog page is not loaded\n###Expected: Catalog page is loaded\n");
    }

    @Step("Click on Компьютеры label")
    public void navigateToSubmenuLabel(){
        Label navMenuOnCatalogPage = new Label(By.xpath(String.format(NAVIGATE_MENU, "Компьютеры")));
        navMenuOnCatalogPage.click();
    }

    @Step("Move mouse to \"Компьтеры, ноутбуки...\"")
    public void moveToSubmenuItem(){
        NAV_SUBMENU_ITEM.moveTo();
    }

    @Step("Click on \"Игровые ноутбуки...\"")
    public void navigateToSubmenuCategory(){
        NAV_SUBMENU_CATEGORY.moveAndClickByAction();
    }
}
