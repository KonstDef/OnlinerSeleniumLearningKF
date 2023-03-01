package onliner.pageObject;

import framework.BasePage;
import framework.elements.Label;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CatalogPage extends BasePage{
    private static final By PAGE_LOCATOR = By.xpath("//div[@class='catalog-navigation__title' and text()='Каталог']");
    private static final String NAVIGATE_MENU = "//span[@class='catalog-navigation-classifier__item-title-wrapper' and contains(text(),'%s')]";
    private static final Label NAV_SUBMENU_CATEGORY = new Label(By.xpath("//div[@class='catalog-navigation-list__category']//span[@class='catalog-navigation-list__dropdown-title' and contains(text(),'Игровые ноутбуки')]"));
    private static final Label NAV_SUBMENU_ITEM = new Label(By.xpath("//div[@class='catalog-navigation-list__aside-title' and contains(text(),'Ноутбуки, компьютеры, мониторы')]"));

    public CatalogPage(){
        super(PAGE_LOCATOR,"Main page");
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
