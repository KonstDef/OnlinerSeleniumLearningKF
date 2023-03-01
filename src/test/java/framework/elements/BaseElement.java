package framework.elements;

import com.fasterxml.jackson.databind.ser.Serializers;
import framework.Browser;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class BaseElement {
    protected WebElement element;
    protected List<WebElement> elements;
    private By by;
    private String name;

    protected abstract String getElementType();

    public BaseElement(By by) {
        this.by = by;
    }

    public BaseElement(By by, String name) {
        this.by = by;
        this.name = name;
    }

    public WebElement getElement() {
        isElementPresent();
        return element;
    }

    public List<WebElement> getElements() {
        areElementsPresent();
        return elements;
    }

    public boolean isElementPresent() {
        try {
            Browser.getDriver().manage().timeouts().implicitlyWait(Browser.getTimeoutForCondition(), TimeUnit.SECONDS);
            element = Browser.getDriver().findElement(by);
            System.out.printf("%s: %s - is present;\n", getElementType(), by);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.printf("%s: %s - is not present;\n \"NoSuchElementException\"\n", getElementType(), by);
            return false;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return false;
    }

    public boolean areElementsPresent() {
        try {
            Browser.getDriver().manage().timeouts().implicitlyWait(Browser.getTimeoutForCondition(), TimeUnit.SECONDS);
            elements = Browser.getDriver().findElements(by);

            System.out.printf("%s(%d): %s - are present;\n", getElementType(), elements.size(), by);
            long elementsPresentNum = elements.stream().filter(WebElement::isDisplayed).count();
            return elementsPresentNum > 0;
        } catch (NoSuchElementException e) {
            System.out.printf("%s: %s - are not present;\n \"NoSuchElementException\"\n", getElementType(), by);
            return false;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return false;
    }

    public boolean isDisplayed() {
        return isElementPresent();
    }

    public boolean isClickable(){
        isElementPresent();
        try{
            new WebDriverWait(Browser.getDriver(), Duration.ZERO)
                    .until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (TimeoutException timeoutException){
            return false;
        }
    }

    public String getAttribute(String attributeName) {
        isElementPresent();
        String attributeValue = element.getAttribute(attributeName);

        System.out.printf("%s: %s - has '%s' attribute = %s;\n", getElementType(), by, attributeName, attributeValue);
        return attributeValue;
    }

    public String getText() {
        isElementPresent();
        String elementText = element.getText();

        System.out.printf("%s: %s - has text = %s;\n", getElementType(), by, elementText);
        return elementText;
    }

    public List<String> getTextList() {
        areElementsPresent();
        List<String> elementTextList = new ArrayList<>();
        elements.forEach(e -> elementTextList.add(e.getText()));

        long nonEmpty = elementTextList.stream().filter(Predicate.not(String::isBlank)).count();

        System.out.printf("%s: %s - has %d non empty Strings;\n", getElementType(), by, nonEmpty);
        return elementTextList;
    }

    public int countElements() {
        areElementsPresent();
        return elements.size();
    }

    public void forEach(Consumer<WebElement> consumer) {
        areElementsPresent();
        elements.forEach(consumer);
    }

    public void scrollTo() {
        isElementPresent();
        Actions actions = new Actions(Browser.getDriver());
        actions.scrollToElement(element).build().perform();
    }

    public void click() {
        isElementPresent();
        element.click();
        System.out.printf("%s: %s - clicked;\n", getElementType(), by);
    }

    public void clickByAction() {
        isElementPresent();
        Actions action = new Actions(Browser.getDriver());
        action.click(element).build().perform();
        System.out.printf("%s: %s - clicked;\n", getElementType(), by);
    }

    public void waitForElementAttachment(){
        FluentWait<WebDriver> wait = new FluentWait<>(Browser.getDriver())
                .withTimeout(Duration.ofSeconds(Browser.getTimeoutForCondition()))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(StaleElementReferenceException.class);

        wait.until(driver->isElementPresent());
    }

    public void clickAndWait() {
        click();
        Browser.waitForPageToLoad();
    }

    public void moveTo() {
        isElementPresent();
        Actions actions = new Actions(Browser.getDriver());
        actions.moveToElement(element).build().perform();
    }

    public void moveToTop() {
        moveTo();
        JavascriptExecutor js = (JavascriptExecutor) Browser.getDriver();
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public void moveAndClickByAction() {
        isElementPresent();
        Actions actions = new Actions(Browser.getDriver());
        actions.moveToElement(element).click(element).build().perform();
    }

    public void sendKeys(String keys) {
        isElementPresent();
        element.sendKeys(keys);
    }

    public void sendKeysToIndex(int i,String keys) {
        areElementsPresent();
        elements.get(i).sendKeys(keys);
    }

    public void clickByJS() {
        isElementPresent();
        if (Browser.getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) Browser.getDriver()).executeScript("arguments[0].style.border='3px solid blue'", element);
        }
        ((JavascriptExecutor) Browser.getDriver()).executeScript("arguments[0].click()", element);
    }
}