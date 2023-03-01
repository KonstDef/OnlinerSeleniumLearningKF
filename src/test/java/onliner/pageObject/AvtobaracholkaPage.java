package onliner.pageObject;

import framework.BasePage;
import framework.Browser;
import framework.elements.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class AvtobaracholkaPage extends BasePage{
    static SoftAssert softAssert = new SoftAssert();
    private static final By PAGE_LOCATOR = By.xpath("//h1[contains(@class,'title_big-alter') and contains(text(),'Автобарахолка')]");
    private static final String CURRENCY_CHANGE_BUTTON_XPATH = "//div[contains(@class,'label-part_2')]//a[contains(@class,'link_base') and contains(text(),'%s')]";
    private static final String FILTER_RANGE_FIELD_BY_NAME_XPATH = "//div[contains(@class,'label-title') and contains(text(),'%s')]/ancestor::div[contains(@class,'vehicle-form__row')]//input[contains(@class,'vehicle-form')]";
    private static final String FILTER_CHECKBOX_BY_NAME_AND_VALUE_XPATH = "//div[contains(@class,'label-title') and contains(text(),'%1$s')]/ancestor::div[contains(@class,'vehicle-form__row')]//div[contains(@class,'checkbox-sign') and contains(text(),'%2$s')]/ancestor::div[contains(@class,'checkbox_base')]/div";
    private static final String CARD_BY_XPATH = "//a[contains(@class,'offers-unit')]";
    private static final Label NEXT_CARDS_BUTTON = new Label(By.xpath("//a[contains(@class,'vehicle-pagination__main')]"));

    public AvtobaracholkaPage(){
        super(PAGE_LOCATOR,"Main page");
    }

    @Step("Set '{currCode}' currency for filter")
    public void setCurrency(String currCode) {
        Button currency = new Button(By.xpath(String.format(CURRENCY_CHANGE_BUTTON_XPATH, currCode)));
        currency.moveToTop();
        currency.moveAndClickByAction();
    }

    @Step("Set '{from}' to lower border for '{filterName}' filter range")
    public void setBottomBorder(int from, String filterName) {
        TextBox filters = new TextBox(By.xpath(String.format(FILTER_RANGE_FIELD_BY_NAME_XPATH, filterName)));
        filters.moveToTop();
        filters.sendKeysToIndex(0, Integer.toString(from));
    }

    @Step("Set '{to}' to top border for '{filterName}' filter range")
    public void setTopBorder(int to, String filterName) {
        TextBox filters = new TextBox(By.xpath(String.format(FILTER_RANGE_FIELD_BY_NAME_XPATH, filterName)));
        filters.moveToTop();
        filters.sendKeysToIndex(1, Integer.toString(to));
    }

    @Step("Set '{from}' - '{to}' range for '{filterName}' filter")
    public void setRangeForFilter(String from, String to, String filterName) {
        if (!from.isBlank()) setBottomBorder(Integer.parseInt(from), filterName);
        if (!to.isBlank()) setTopBorder(Integer.parseInt(to), filterName);
    }

    @Step("Set '{from}' - '{to}'{currCode} range for price filter")
    public void setRangeForPriceFilter(String from, String to, String currCode) {
        setCurrency(currCode);
        setRangeForFilter(from, to, "Цена");
    }

    @Step("Set '{value}' for '{filter}' checkbox filter")
    public void selectCheckboxForFilter(String value, String filter) {
        CheckBox filterBox = new CheckBox(By.xpath(String.format(FILTER_CHECKBOX_BY_NAME_AND_VALUE_XPATH, filter, value)));
        filterBox.moveToTop();
        filterBox.clickByAction();
    }

    @Step("Check parameters for all cards are correct")
    public void checkElementsData(Map<String, String> testData) {
        while(true){
            Label cards = new Label(By.xpath(CARD_BY_XPATH));
            Browser.waitForjQueryLoad();
            cards.waitForElementAttachment();
            int numOfCards = cards.countElements();

            for(int i = 1;i!=numOfCards+1; i++){
                new ABCard(i).checkFields(testData);
            }

            boolean lastList = NEXT_CARDS_BUTTON.getAttribute("class").contains("main_disabled");
            if(lastList) break;
            else {
                NEXT_CARDS_BUTTON.moveAndClickByAction();
                Browser.waitForjQueryLoad();
            }
        }
        softAssert.assertAll();
    }
}
