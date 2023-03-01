package onliner.pageObject;

import framework.BasePage;
import framework.elements.TextBox;
import org.openqa.selenium.By;


import java.util.Map;

public class ABCard extends BasePage {
    private int position;
    private String name;
    private static final String CARD_NAME = "(//a[contains(@class,'offers-unit')])[%d]//div[contains(@class,'form__link_noreflex')]";
    private static final String CARD_PRICE_BY_CURRENCY_SYMBOL_XPATH = "(//a[contains(@class,'offers-unit')])[%d]//div[contains(@class,'part_price')]/div[contains(.,'%s')]";
    private static final String CARD_SPECIFICATION_BY_ENGLISH_CLASS_XPATH = "(//a[contains(@class,'offers-unit')])[%d]//div[contains(@class,'part_specification')]//div[contains(@class,'description_%s')]";

    public ABCard(int position) {
        super(By.xpath(String.format(CARD_NAME, position)),
                new TextBox(By.xpath(String.format(CARD_NAME, position))).getText());
        this.position = position;
        this.name = new TextBox(By.xpath(String.format(CARD_NAME, position))).getText();
    }

    public int getCardPriceBySymbol(String symbol) {
        TextBox cardPrice = new TextBox(By.xpath(String.format(CARD_PRICE_BY_CURRENCY_SYMBOL_XPATH, position, symbol)));
        cardPrice.waitForElementAttachment();
        String price = cardPrice.getText();
        switch (symbol) {
            case "$":
                price = price.split("/")[0];
                break;
            case "€":
                price = price.split("/")[1];
                break;
        }
        return Integer.parseInt(price.replace(" ", "").replace(symbol, ""));
    }

    public String getCardSpecificationByClass(String specClass) {
        TextBox spec = new TextBox(By.xpath(String.format(CARD_SPECIFICATION_BY_ENGLISH_CLASS_XPATH, position, specClass)));
        spec.waitForElementAttachment();
        return spec.getText();
    }

    public void validationByMap(String data, String value) {
        switch (data) {
            case "$ price lesser":
                int price = getCardPriceBySymbol("$");
                int priceExpected = Integer.parseInt(value);
                AvtobaracholkaPage.softAssert.assertTrue(price <= priceExpected,
                        name + ": Price is greater then expected\nActual result: " + price + "\nExpected result: " + value);
                break;
            case "default":
                String specText = getCardSpecificationByClass(data);
                AvtobaracholkaPage.softAssert.assertEquals(specText, value,
                        name + "Attribute not equals to expected\nActual result: " + specText + "\nExpected result: " + data);
                break;
        }
    }

    public void checkFields(Map<String, String> testData) {
        testData.forEach(this::validationByMap);
    }
}