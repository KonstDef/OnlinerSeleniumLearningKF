package onliner.pageObject;

import framework.BasePage;
import framework.elements.Label;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

public class MainPage extends BasePage {
    private static final String NAV_MENU_ITEM = "//span[@class='b-main-navigation__text' and text()='%s']";
    private static final By PAGE_LOCATOR = By.xpath("//img[@alt]");

    public MainPage(){
        super(PAGE_LOCATOR,"Main page");
    }

    @Step("Go to '{pageName}' page")
    public void navigateWithHeaderLabel(String pageName){
        Label mainMenuItem = new Label(By.xpath(String.format(NAV_MENU_ITEM, pageName)));
        mainMenuItem.clickAndWait();
    }
}
