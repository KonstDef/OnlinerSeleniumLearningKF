package onliner.pageObject;

import framework.elements.Label;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

public class MainPage {
    private static final String NAV_MENU_ITEM = "//span[@class='b-main-navigation__text' and text()='%s']";
    private static final Label PAGE_LOCATOR = new Label(By.xpath("//img[@alt]"));

    @Step("Check page loaded")
    public void isPageOpened(){
        Assert.assertTrue(PAGE_LOCATOR.isDisplayed(),"\n###Main page is not loaded\n###Expected: Catalog page is loaded\n");
    }

    @Step("Go to '{pageName}' page")
    public void navigateWithHeaderLabel(String pageName){
        Label mainMenuItem = new Label(By.xpath(String.format(NAV_MENU_ITEM, pageName)));
        mainMenuItem.clickAndWait();
    }
}
