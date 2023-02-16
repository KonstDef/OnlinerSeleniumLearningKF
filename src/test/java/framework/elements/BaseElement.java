package framework.elements;

import framework.Browser;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public abstract class BaseElement {
    protected WebElement element;
    protected List<WebElement> elements;
    private By by;
    private String name;

    protected abstract String getElementType();

    public BaseElement(By by) {
        this.by = by;
    }

    public BaseElement(By by,String name) {
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
            System.out.printf("%s: %s - is present;\n",getElementType(),by);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.printf("%s: %s - is not present;\n \"NoSuchElementException\"\n",getElementType(),by);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return false;
    }

    public boolean areElementsPresent(){
        try {
            Browser.getDriver().manage().timeouts().implicitlyWait(Browser.getTimeoutForCondition(), TimeUnit.SECONDS);
            elements = Browser.getDriver().findElements(by);

            System.out.printf("%s(%d): %s - are present;\n",getElementType(),elements.size(),by);
            long elementsPresentNum = elements.stream().filter(WebElement::isDisplayed).count();
            return elementsPresentNum>0;
        } catch (NoSuchElementException e) {
            System.out.printf("%s: %s - are not present;\n \"NoSuchElementException\"\n",getElementType(),by);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return false;
    }

    public boolean isDisplayed(){
        return isElementPresent();
    }

    public void click() {
        isElementPresent();
        element.click();
        System.out.printf("%s: %s - clicked;\n",getElementType(),by);
    }

    public void clickByAction() {
        isElementPresent();
        Actions action = new Actions(Browser.getDriver());
        action.click(element).build().perform();
        System.out.printf("%s: %s - clicked;\n",getElementType(),by);
    }

    public void clickAndWait() {
        click();
        Browser.waitForPageToLoad();
    }

    public void moveTo(){
        isElementPresent();
        Actions actions = new Actions(Browser.getDriver());
        actions.moveToElement(element).build().perform();
    }

    public void moveAndClickByAction(){
        isElementPresent();
        Actions actions = new Actions(Browser.getDriver());
        actions.moveToElement(element).click(element).build().perform();
    }

    public void clickByJS() {
        isElementPresent();
        if (Browser.getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) Browser.getDriver()).executeScript("arguments[0].style.border='3px solid blue'", element);
        }
        ((JavascriptExecutor) Browser.getDriver()).executeScript("arguments[0].click()", element);
    }
}