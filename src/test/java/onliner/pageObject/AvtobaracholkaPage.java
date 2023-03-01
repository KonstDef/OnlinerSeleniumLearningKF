package onliner.pageObject;

import framework.BasePage;
import framework.Browser;
import framework.elements.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

public class AvtobaracholkaPage extends BasePage{
    static SoftAssert softAssert = new SoftAssert();
    private static final By PAGE_LOCATOR = By.xpath("//h1[contains(@class,'title_big-alter') and contains(text(),'Автобарахолка')]");
    private static final String CURRENCY_CHANGE_BUTTON_XPATH = "//div[contains(@class,'label-part_2')]//a[contains(@class,'link_base') and contains(text(),'%s')]";
    private static final String FILTER_RANGE_FIELD_BY_NAME_XPATH = "//div[contains(@class,'label-title') and contains(text(),'%s')]/ancestor::div[contains(@class,'vehicle-form__row')]//input[contains(@class,'vehicle-form')]";
    private static final String FILTER_CHECKBOX_BY_NAME_AND_VALUE_XPATH = "//div[contains(@class,'label-title') and contains(text(),'%1$s')]/ancestor::div[contains(@class,'vehicle-form__row')]//div[contains(@class,'checkbox-sign') and contains(text(),'%2$s')]/ancestor::div[contains(@class,'checkbox_base')]/div";
    private static final String CARD_PRICE_BY_CURRENCY_SYMBOL_XPATH = "//div[contains(@class,'part_price')]/div[contains(.,'%s')]";
    private static final String CARD_SPECIFICATION_BY_ENGLISH_CLASS_XPATH = "//div[contains(@class,'part_specification')]//div[contains(@class,'description_%s')]";
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

    public static void validationByMap(String data, String value) {
        TextBox spec;
        List<String> specText;
        Browser.waitForjQueryLoad();
        switch (data) {
            case "$ price lesser":
                spec = new TextBox(By.xpath(String.format(CARD_PRICE_BY_CURRENCY_SYMBOL_XPATH, "$")));
                spec.waitForElementAttachment();
                specText = spec.getTextList();
                specText.forEach(s-> {
                    String priceString = s.split(" / ")[0]
                            .replace(" ", "")
                            .replace("$","");
                    int price = Integer.parseInt(priceString);
                    int expectedPrice = Integer.parseInt(value);
                    softAssert.assertTrue(price<expectedPrice,"Price is greater then expected\nActual result: " + specText + "\nExpected result: " + data);
                });
                break;
            case "default":
                spec = new TextBox(By.xpath(String.format(CARD_SPECIFICATION_BY_ENGLISH_CLASS_XPATH, data)));
                spec.waitForElementAttachment();
                specText = spec.getTextList();
                specText.forEach(s-> {
                    softAssert.assertEquals(s, value,"Attribute not equals to expected\nActual result: " + specText + "\nExpected result: " + data);
                });
                break;
        }
    }
    @Step("Check card parameters are correct")
    public void checkElementsData(Map<String, String> testData) {
        while(true){
            testData.forEach(AvtobaracholkaPage::validationByMap);
            boolean lastList = NEXT_CARDS_BUTTON.getAttribute("class").contains("main_disabled");
            if(lastList) break;
            else {
                NEXT_CARDS_BUTTON.moveAndClickByAction();
                Browser.waitForjQueryLoad();
            }
        }
    }
}