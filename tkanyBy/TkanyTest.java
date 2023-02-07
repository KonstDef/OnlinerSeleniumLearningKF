import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TkanyTest {
    static WebDriver driver;
    static int step;

    public static final String NAV_MENU_VISIBLE_LINK_BY_TEXT_XPATH = "//ul[@id='subMenu']/li[not(@class)]/a[contains(text(),'%s')]";
    public static final String NAV_MENU_HIDE_BUTTON_XPATH = "//a[@class='removedItemsLink']";
    public static final String NAV_MENU_HIDDEN_LINK_BY_TEXT_XPATH = "//ul[@class='removedItemsList']//a[contains(text(),'%s')]";
    public static final String NAV_MENU_ICON_LINKS_BY_CLASS_XPATH = "//div[contains(@id,'headerLine') or contains(@id,'subHeader')]//a[contains(@class,'%s')]";
    public static final String NAV_MENU_AUTH_LINKS_BY_TEXT_XPATH = "//div[@id='topAuth']//a[contains(text(),'%s')]";
    public static final String NAV_MENU_PHONE_NUMBER_BY_TEXT_XPATH = "//div[@class='phonesTc']/span/a[contains(text(),'%s')]";
    public static final String NAV_MENU_PHONE_ORDER_XPATH = "//a[contains(@class,'openWebFormModal')]";
    public static final String FLOATING_FOOTER_LINK_BY_TEXT_XPATH = "//div[@id='footerLine']//a[contains(text(),'%s')]";
    public static final String SEARCH_FIELD_XPATH = "//input[@id='searchQuery']";
    public static final String SEARCH_PRODUCT_FOUND_BY_TEXT_XPATH = "//div[@id='searchResult']//a[@class='name' and span[contains(text(),'%s')]]";
    public static final String PRODUCT_ARTICLE_XPATH = "//span[@class='changeArticle']";
    public static final String CALLBACK_WEBFORM_TITLE_BY_TEXT_XPATH = "//div[@class='webFormItemLabel' and contains(text(),'%s')]";
    public static final String REGISTRATION_FORM_DIV_BY_TEXT_XPATH = "//div[@class='bx-auth-form-line' and div[contains(@class,'label') and contains(text(),'%s')]]";
    public static final String REGISTRATION_BUTTON_XPATH = "//input[@name='Register']";
    public static final String REGISTRATION_AGREE_LABEL_XPATH = "//label[@for='userPersonalInfoReg']";
    public static final String LOGIN_FORM_DIV_BY_TEXT_XPATH = "//div[contains(@class,'auth-by-login')]//div[@class='bx-auth-input-line' and div[contains(@class,'label') and contains(text(),'%s')]]";
    public static final String LOGIN_BUTTON_XPATH = "//div[contains(@class,'auth-by-login')]//input[@name='Login']";
    public static final String USER_MENU_LINK_BY_TEXT_XPATH = "//ul[@id='personalMenu']//a[contains(text(),'%s')]";
    public static final String CATEGORY_NAV_LINK_BY_TEXT_XPATH = "//div[@id='left']/a[contains(text(),'%1$s')] | //ul[@id='subLeftMenu']//a[contains(text(),'%1$s')] | //span[@class='tx']//span[@class='link-title' and contains(text(),'%1$s')]//ancestor::a";
    public static final String CATALOG_MAIN_CATEGORY_BY_TEXT_XPATH = CATEGORY_NAV_LINK_BY_TEXT_XPATH.split(" \\| ")[2];
    public static final String CATALOG_MENU_TOGGLE_XPATH = String.format(CATEGORY_NAV_LINK_BY_TEXT_XPATH.split(" \\| ")[0], "");
    public static final String CATALOG_NAV_SUBCATEGORIES_BY_CATEGORY_AND_SUB_TEXT_XPATH = CATALOG_MAIN_CATEGORY_BY_TEXT_XPATH + "//following-sibling::div//span[contains(text(),'%2$s')]";
    public static final String CATALOG_SUBCATEGORY_FROM_CATEGORY_PAGE_BY_TEXT_XPATH = "//div[@id='nextSection']//a[not(@class='cnt') and contains(text(),'%s')]";
    public static final String CATALOG_FILTER_PARAMETER_BOX_BY_TEXT_XPATH = "//div[@class='paramsBox' and div/span[contains(text(),'%s')]]";
    public static final String CATALOG_BREADCRUMBS_LINK_BY_TEXT_XPATH = "//div[@id='breadcrumbs']//a[span[contains(text(),'%s')]]";
    public static final String CATALOG_PRODUCT_LINK_BY_TEXT_XPATH = "//div[@id='catalogSection']//a[@class='name' and span[contains(text(),'%s')]]";
    public static final String CATALOG_PAGINATION_LINK_BY_TEXT_XPATH = "//div[contains(@class,'pagination')]//li[//span[contains(text(),'%s')]]";
    public static final String CATALOG_VIEW_FILTER_BY_TEXT_XPATH = "//div[@class='label' and contains(text(),'%s')]/following-sibling::div";
    public static final String CATALOG_VIEW_FILTER_OPENED_DROPDOWN_ITEM_BY_TEXT_XPATH = "//div[@class='dropDownItems opened']/div[contains(text(),'%s')]";
    public static final String PRODUCT_HEADER_XPATH = "//h1[@class='changeName']";
    public static final String DESKTOP_PRODUCT_NAVIGATION_TAB_BY_TEXT_XPATH = "//div[@id='elementNavigation']//div[@class='tab' or @class='tab active']/a[contains(text(),'%s')]";
    public static final String DESKTOP_PRODUCT_ADD_TO_CART_BUTTON_XPATH = "//div[@id='elementTools']//a[contains(@class,'addCart')]";
    public static final String DESKTOP_PRODUCT_FAST_ORDER_BUTTON_XPATH = "//div[@id='elementTools']//a[contains(@class,'fastBack')]";
    public static final String PRODUCT_SHORT_DESCRIPTION_XPATH = "//div[@class='changeShortDescription']";
    public static final String PRODUCT_ATTRIBUTE_ROW_BY_FiRST_COLUMN_XPATH = "//div[@class='detailPropertiesTable']//tr[td/span[contains(text(),'%s')]]";
    public static final String FAST_ORDER_INPUT_FIELD_BY_PLACEHOLDER_XPATH = "//div[contains(@class,'formLine')]//input[contains(@placeholder,'%s')]";
    public static final String DESKTOP_INFORMATION_BUTTON_BY_PLUS_OR_MINUS_CLASS_XPATH = "//div[@class='information']//a[@class='%s']";
    public static final String DESKTOP_INFORMATION_GO_TO_BASKET_BUTTON_XPATH = "//div[@id='appBasketContainer']//td[@class='goToBasket']";
    public static final String DESKTOP_CART_PRODUCT_CARD_BY_TEXT_XPATH = "//div[@id='personalCart']//div[@class='productTable']//a/span[contains(text(),'%s')]/ancestor::div[@class='tabloid']";
    public static final String DESKTOP_CART_GO_TO_ORDER_BUTTON_XPATH = "//div[@id='personalCart']/following-sibling::a";
    public static final String DESKTOP_CART_FORM_NEXT_BUTTON_XPATH = "//div[contains(@class,'bx-active')]//div//a[contains(@class,'pull-right btn btn-default btn-md')]";
    public static final String DESKTOP_CART_FORM_CITY_BY_TEXT_XPATH = "//div[not(contains(@style,'none'))]/child::*/child::div/a[@class='quick-location-tag' and contains(text(),'%s')]";
    public static final String DESKTOP_CART_FORM_DELIVERY_BY_TEXT_XPATH = "//div[not(contains(@style,'none'))]/child::*/child::div/a[@class='quick-location-tag' and contains(text(),'%s')]";
    public static final String DESKTOP_CART_FORM_PAYMENT_BY_TEXT_XPATH = "//div[div[input[@name='PAY_SYSTEM_ID']] and div[contains(text(),'%s')]]";
    public static final String DESKTOP_CART_FORM_PAYER_INFO_BY_TEXT_XPATH = "//div[@class='form-group bx-soa-customer-field' and label[contains(text(),'%s')]]//input";
    public static final String DESKTOP_CART_FORM_END_ORDER_BUTTON_XPATH = "//div[@id='bx-soa-orderSave']/a";
    public static final String BACK_TO_TOP_BUTTON_XPATH = "//div[@id='upButton']";

    @BeforeTest
    public static void setDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeMethod
    public static void openIndex() {
        driver.manage().window().maximize();
        driver.get("https://tkany.by");
        System.out.printf("---%d---\n", ++step);
    }

    @Test
    public static void testID01() {
        List<WebElement> navLinks = driver.findElements(By.xpath(String.format(NAV_MENU_VISIBLE_LINK_BY_TEXT_XPATH, "")));
        navLinks.forEach(c -> System.out.println(c.getText()));
        navLinks.clear();

        System.out.println("---");

        driver.findElement(By.xpath(NAV_MENU_HIDE_BUTTON_XPATH)).click();

        navLinks.addAll(driver.findElements(By.xpath(String.format(NAV_MENU_HIDDEN_LINK_BY_TEXT_XPATH, ""))));
        navLinks.forEach(c -> System.out.println(c.getText()));
    }


    @Test
    public static void testID02() {
        List<String> navPages = List.of("Новости", "Модели", "Контакты", "Оплата", "Доставка", "Вопрос ответ",
                "Отзывы", "Акции!", "Коллекции", "Оферта", "Обратная связь");
        List<String> navAuth = List.of("Вход", "Регистрация");
        List<String> navPhones = List.of("+375 (33) 900-10-96", "+375 (29) 563-94-24");
        List<String> navIcons = List.of("socVK", "socFB", "socODN", "socINS", "callBackIcon", "scheduleIcon");

        Consumer<String> pagesLinkGetter = c -> System.out.println(c + " - " +
                driver.findElement(By.xpath(String.format(NAV_MENU_VISIBLE_LINK_BY_TEXT_XPATH + " | " + NAV_MENU_HIDDEN_LINK_BY_TEXT_XPATH, c, c)))
                        .getAttribute("href")
        );
        Consumer<String> authLinkGetter = c -> System.out.println(c + " - " +
                driver.findElement(By.xpath(String.format(NAV_MENU_AUTH_LINKS_BY_TEXT_XPATH, c))).getAttribute("href")
        );
        Consumer<String> phonesLinkGetter = c -> System.out.println(c + " - " +
                driver.findElement(By.xpath(String.format(NAV_MENU_PHONE_NUMBER_BY_TEXT_XPATH, c))).getAttribute("href")
        );
        Consumer<String> buttonLinkGetter = c -> System.out.println(c + " - " +
                driver.findElement(By.xpath(String.format(NAV_MENU_ICON_LINKS_BY_CLASS_XPATH, c))).getAttribute("href")
        );

        driver.findElement(By.xpath(NAV_MENU_HIDE_BUTTON_XPATH)).click();
        navPages.forEach(pagesLinkGetter);
        navAuth.forEach(authLinkGetter);
        navPhones.forEach(phonesLinkGetter);
        navIcons.forEach(buttonLinkGetter);
    }

    @Test
    public static void testID03() {
        List<WebElement> footerLineItems = driver.findElements(By.xpath(String.format(FLOATING_FOOTER_LINK_BY_TEXT_XPATH, "")));
        System.out.println(footerLineItems.size());
    }

    @Test
    public static void testID04() {
        driver.findElement(By.xpath(SEARCH_FIELD_XPATH)).sendKeys("Пальтовая клетка с шерстью зеленая");

        By productFound = By.xpath(String.format(SEARCH_PRODUCT_FOUND_BY_TEXT_XPATH, "Пальтовая клетка"));

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(productFound));
        driver.findElement(productFound).click();

        System.out.println(driver.findElement(By.xpath(PRODUCT_ARTICLE_XPATH)).getText());
    }

    @Test
    public static void testID05() {
        driver.findElement(By.xpath(NAV_MENU_PHONE_ORDER_XPATH)).click();

        List<WebElement> webFormItems = driver.findElements(By.xpath(String.format(CALLBACK_WEBFORM_TITLE_BY_TEXT_XPATH, "")));
        webFormItems.forEach(c -> System.out.println(c.getText()));
    }

    @Test //TODO: Captcha
    public static void testID06() {
        Map<String, WebElement> fields = new LinkedHashMap<>();
        driver.findElement(By.xpath(String.format(NAV_MENU_AUTH_LINKS_BY_TEXT_XPATH, "Регистрация"))).click();

        driver.findElements(By.xpath(String.format(REGISTRATION_FORM_DIV_BY_TEXT_XPATH, "")))
                .forEach(wE -> fields.put(wE.findElement(By.className("bx-authform-label-container")).getText(),
                        wE.findElement(By.tagName("input"))));



        fields.forEach((s, wE) -> {
            System.out.println(s);
            wE.sendKeys("ABC");
        });

        driver.findElement(By.xpath(REGISTRATION_BUTTON_XPATH)).click();
        driver.findElement(By.xpath(REGISTRATION_AGREE_LABEL_XPATH)).click();
    }

    @Test //TODO: Find reason not clickable
    public static void testID07() {
        Map<String, WebElement> fields = new LinkedHashMap<>();
        driver.findElement(By.xpath(String.format(NAV_MENU_AUTH_LINKS_BY_TEXT_XPATH, "Вход"))).click();

        driver.findElements(By.xpath(String.format(LOGIN_FORM_DIV_BY_TEXT_XPATH, "")))
                .forEach(wE -> fields.put(wE.findElement(By.className("bx-authform-label-container")).getText(),
                        wE.findElement(By.tagName("input"))));

        fields.forEach((s, wE) -> {
            System.out.println(s);
            wE.sendKeys("ABC");
        });

        WebElement loginButton = driver.findElement(By.xpath(LOGIN_BUTTON_XPATH));
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(loginButton));
        new Actions(driver).scrollByAmount(0, loginButton.getLocation().getY()).perform();
        loginButton.click();
    }

    @Test
    public static void testID08() {
        driver.findElement(By.xpath(String.format(NAV_MENU_AUTH_LINKS_BY_TEXT_XPATH, "Личный кабинет"))).click();

        List<String> tabNames = List.of("Персональные данные", "Профили покупателя", "История заказов", "Личный счет", "Моя корзина", "Подписка на рассылку");
        Consumer<String> tabLinkGetter = c -> {
            System.out.println(c + " - " +
                    driver.findElement(By.xpath(String.format(USER_MENU_LINK_BY_TEXT_XPATH, c))).getAttribute("href"));
            driver.findElement(By.xpath(String.format(USER_MENU_LINK_BY_TEXT_XPATH, c))).click();
        };
        tabNames.forEach(tabLinkGetter);
    }

    @Test
    public static void testID09() {
        driver.findElement(By.xpath(String.format(NAV_MENU_AUTH_LINKS_BY_TEXT_XPATH, "Выход"))).click();
        driver.findElement(By.xpath(String.format(NAV_MENU_AUTH_LINKS_BY_TEXT_XPATH, "Вход")));
        driver.findElement(By.xpath(String.format(NAV_MENU_AUTH_LINKS_BY_TEXT_XPATH, "Регистрация")));
    }


    @Test
    public static void testID10() {
        List<String> catalogNavNames = List.of("Каталог товаров", "Ткани для одежды", "Ткани для дома", "Аксессуары и прикладные товары", "Новинки",
                "Популярные товары", "Распродажи и скидки", "Товары с кэшбэком", "Уцененные товары", "Скоро в продаже");

        Consumer<String> catalogMenuLinkGetter = c -> {
            WebElement webElement = driver.findElement(By.xpath(String.format(CATEGORY_NAV_LINK_BY_TEXT_XPATH, c)));
            System.out.println(c + " - " + webElement.getAttribute("href"));
        };
        catalogNavNames.forEach(catalogMenuLinkGetter);
    }

    @Test
    public static void testID11() {
        List<String> catalogNavNames = List.of("Ткани для одежды", "Аксессуары и прикладные товары");

        Consumer<String> catalogMenuFloater = c -> {
            WebElement webElement = driver.findElement(By.xpath(String.format(CATEGORY_NAV_LINK_BY_TEXT_XPATH, c)));
            new Actions(driver).moveToElement(webElement).perform();
            System.out.println(driver.findElement(
                    By.xpath(String.format(CATALOG_NAV_SUBCATEGORIES_BY_CATEGORY_AND_SUB_TEXT_XPATH, c, ""))
            ).getText());
        };
        catalogNavNames.forEach(catalogMenuFloater);
    }

    @Test
    public static void testID12() {
        driver.findElement(By.xpath(String.format(CATALOG_MAIN_CATEGORY_BY_TEXT_XPATH, "Ткани для одежды"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_SUBCATEGORY_FROM_CATEGORY_PAGE_BY_TEXT_XPATH, "Атлас"))).click();

        System.out.printf("Catalog navigation present: %s;\n", driver.findElement(By.xpath(CATALOG_MENU_TOGGLE_XPATH)).isDisplayed());
        System.out.printf("Catalog filter present: %s;\n", driver.findElement(By.xpath(String.format(CATALOG_FILTER_PARAMETER_BOX_BY_TEXT_XPATH, "Цвет"))).isDisplayed());
        System.out.printf("Catalog breadcrumbs present: %s;\n", driver.findElement(By.xpath(String.format(CATALOG_BREADCRUMBS_LINK_BY_TEXT_XPATH, "Витрина"))).isDisplayed());
        System.out.printf("Catalog products present: %s;\n", driver.findElement(By.xpath(String.format(CATALOG_PRODUCT_LINK_BY_TEXT_XPATH, "Атлас"))).isDisplayed());
        System.out.printf("Catalog pagination present: %s;\n", driver.findElement(By.xpath(String.format(CATALOG_PAGINATION_LINK_BY_TEXT_XPATH, "2"))).isDisplayed());
    }

    @Test
    public static void testID13() {
        driver.findElement(By.xpath(String.format(CATALOG_MAIN_CATEGORY_BY_TEXT_XPATH, "Ткани для одежды"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_SUBCATEGORY_FROM_CATEGORY_PAGE_BY_TEXT_XPATH, "Атлас"))).click();

        driver.findElement(By.xpath(String.format(CATALOG_VIEW_FILTER_BY_TEXT_XPATH, "Сортировать"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_VIEW_FILTER_OPENED_DROPDOWN_ITEM_BY_TEXT_XPATH, "алфавиту"))).click();

        By productByXpath = By.xpath(String.format(CATALOG_PRODUCT_LINK_BY_TEXT_XPATH, "Атлас корсетный баклажан"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(productByXpath));
        new Actions(driver).scrollByAmount(0, driver.findElement(productByXpath).getLocation().getY()).perform();
        driver.findElement(productByXpath).click();

        System.out.println(driver.findElement(By.xpath(PRODUCT_ARTICLE_XPATH)).getText());
    }

    @Test
    public static void testID14() {
        driver.findElement(By.xpath(String.format(CATALOG_MAIN_CATEGORY_BY_TEXT_XPATH, "Ткани для одежды"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_SUBCATEGORY_FROM_CATEGORY_PAGE_BY_TEXT_XPATH, "Атлас"))).click();

        driver.findElement(By.xpath(String.format(CATALOG_VIEW_FILTER_BY_TEXT_XPATH, "Сортировать"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_VIEW_FILTER_OPENED_DROPDOWN_ITEM_BY_TEXT_XPATH, "алфавиту"))).click();

        By productByXpath = By.xpath(String.format(CATALOG_PRODUCT_LINK_BY_TEXT_XPATH, "Атлас корсетный баклажан"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(productByXpath));
        new Actions(driver).scrollByAmount(0, driver.findElement(productByXpath).getLocation().getY()).perform();
        driver.findElement(productByXpath).click();


        System.out.printf("Catalog navigation present: %s;\n", driver.findElement(By.xpath(CATALOG_MENU_TOGGLE_XPATH)).isDisplayed());
        System.out.printf("Product breadcrumbs present: %s;\n", driver.findElement(By.xpath(String.format(CATALOG_BREADCRUMBS_LINK_BY_TEXT_XPATH, "Витрина"))).isDisplayed());
        System.out.printf("Product header present: %s;\n", driver.findElement(By.xpath(PRODUCT_HEADER_XPATH)).isDisplayed());
        System.out.printf("Product article present: %s;\n", driver.findElement(By.xpath(PRODUCT_ARTICLE_XPATH)).isDisplayed());
        System.out.printf("Product navigation tabs present: %s;\n", driver.findElement(By.xpath(String.format(DESKTOP_PRODUCT_NAVIGATION_TAB_BY_TEXT_XPATH,"Обзор"))).isDisplayed());
        System.out.printf("Product add to cart present: %s;\n", driver.findElement(By.xpath(DESKTOP_PRODUCT_ADD_TO_CART_BUTTON_XPATH)).isDisplayed());
        System.out.printf("Product short description present: %s;\n", driver.findElement(By.xpath(PRODUCT_SHORT_DESCRIPTION_XPATH)).isDisplayed());
        System.out.printf("Product attribute table present: %s;\n", driver.findElement(By.xpath(String.format(PRODUCT_ATTRIBUTE_ROW_BY_FiRST_COLUMN_XPATH,""))).isDisplayed());
    }

    @Test
    public static void testID15() {
        driver.findElement(By.xpath(String.format(CATALOG_MAIN_CATEGORY_BY_TEXT_XPATH, "Ткани для одежды"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_SUBCATEGORY_FROM_CATEGORY_PAGE_BY_TEXT_XPATH, "Атлас"))).click();

        driver.findElement(By.xpath(String.format(CATALOG_VIEW_FILTER_BY_TEXT_XPATH, "Сортировать"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_VIEW_FILTER_OPENED_DROPDOWN_ITEM_BY_TEXT_XPATH, "алфавиту"))).click();

        By productByXpath = By.xpath(String.format(CATALOG_PRODUCT_LINK_BY_TEXT_XPATH, "Атлас корсетный баклажан"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(productByXpath));
        new Actions(driver).scrollByAmount(0, driver.findElement(productByXpath).getLocation().getY()).perform();
        driver.findElement(productByXpath).click();

        driver.findElement(By.xpath(DESKTOP_PRODUCT_FAST_ORDER_BUTTON_XPATH)).click();
        driver.findElements(By.xpath(String.format(FAST_ORDER_INPUT_FIELD_BY_PLACEHOLDER_XPATH,"")))
                .forEach(wE-> System.out.println(wE.getAttribute("placeholder")));
    }

    @Test
    public static void testID16() {
        driver.findElement(By.xpath(String.format(CATALOG_MAIN_CATEGORY_BY_TEXT_XPATH, "Ткани для одежды"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_SUBCATEGORY_FROM_CATEGORY_PAGE_BY_TEXT_XPATH, "Атлас"))).click();

        driver.findElement(By.xpath(String.format(CATALOG_VIEW_FILTER_BY_TEXT_XPATH, "Сортировать"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_VIEW_FILTER_OPENED_DROPDOWN_ITEM_BY_TEXT_XPATH, "алфавиту"))).click();

        By productByXpath = By.xpath(String.format(CATALOG_PRODUCT_LINK_BY_TEXT_XPATH, "Атлас корсетный баклажан"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(productByXpath));
        new Actions(driver).scrollByAmount(0, driver.findElement(productByXpath).getLocation().getY()).perform();
        driver.findElement(productByXpath).click();

        driver.findElement(By.xpath(DESKTOP_PRODUCT_ADD_TO_CART_BUTTON_XPATH)).click();
        By plusButtonByXpath = By.xpath(String.format(DESKTOP_INFORMATION_BUTTON_BY_PLUS_OR_MINUS_CLASS_XPATH,"plus"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(plusButtonByXpath));
        driver.findElement(plusButtonByXpath).click();
        driver.findElement(By.xpath(DESKTOP_INFORMATION_GO_TO_BASKET_BUTTON_XPATH)).click();

        System.out.println(driver.getCurrentUrl());
    }

    @Test
    public static void testID17() {
        driver.findElement(By.xpath(String.format(CATALOG_MAIN_CATEGORY_BY_TEXT_XPATH, "Ткани для одежды"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_SUBCATEGORY_FROM_CATEGORY_PAGE_BY_TEXT_XPATH, "Атлас"))).click();

        driver.findElement(By.xpath(String.format(CATALOG_VIEW_FILTER_BY_TEXT_XPATH, "Сортировать"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_VIEW_FILTER_OPENED_DROPDOWN_ITEM_BY_TEXT_XPATH, "алфавиту"))).click();

        By productByXpath = By.xpath(String.format(CATALOG_PRODUCT_LINK_BY_TEXT_XPATH, "Атлас корсетный баклажан"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(productByXpath));
        new Actions(driver).scrollByAmount(0, driver.findElement(productByXpath).getLocation().getY()).perform();
        driver.findElement(productByXpath).click();

        driver.findElement(By.xpath(DESKTOP_PRODUCT_ADD_TO_CART_BUTTON_XPATH)).click();
        By plusButtonByXpath = By.xpath(String.format(DESKTOP_INFORMATION_BUTTON_BY_PLUS_OR_MINUS_CLASS_XPATH,"plus"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(plusButtonByXpath));
        driver.findElement(plusButtonByXpath).click();
        driver.findElement(By.xpath(DESKTOP_INFORMATION_GO_TO_BASKET_BUTTON_XPATH)).click();

        System.out.println("Product is at cart: " + driver.findElement(By.xpath(String.format(DESKTOP_CART_PRODUCT_CARD_BY_TEXT_XPATH,"Атлас корсетный баклажан"))).isDisplayed());
    }

    @Test
    public static void testID18() {
        driver.findElement(By.xpath(String.format(CATALOG_MAIN_CATEGORY_BY_TEXT_XPATH, "Ткани для одежды"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_SUBCATEGORY_FROM_CATEGORY_PAGE_BY_TEXT_XPATH, "Атлас"))).click();

        driver.findElement(By.xpath(String.format(CATALOG_VIEW_FILTER_BY_TEXT_XPATH, "Сортировать"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_VIEW_FILTER_OPENED_DROPDOWN_ITEM_BY_TEXT_XPATH, "алфавиту"))).click();

        By productByXpath = By.xpath(String.format(CATALOG_PRODUCT_LINK_BY_TEXT_XPATH, "Атлас корсетный баклажан"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(productByXpath));
        new Actions(driver).scrollByAmount(0, driver.findElement(productByXpath).getLocation().getY()).perform();
        driver.findElement(productByXpath).click();

        driver.findElement(By.xpath(DESKTOP_PRODUCT_ADD_TO_CART_BUTTON_XPATH)).click();
        By plusButtonByXpath = By.xpath(String.format(DESKTOP_INFORMATION_BUTTON_BY_PLUS_OR_MINUS_CLASS_XPATH,"plus"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(plusButtonByXpath));
        driver.findElement(plusButtonByXpath).click();
        driver.findElement(By.xpath(DESKTOP_INFORMATION_GO_TO_BASKET_BUTTON_XPATH)).click();

        WebElement goToOrderButton = driver.findElement(By.xpath(DESKTOP_CART_GO_TO_ORDER_BUTTON_XPATH));
        new Actions(driver).scrollByAmount(0, goToOrderButton.getLocation().getY()).perform();
        goToOrderButton.click();

        System.out.println(driver.getCurrentUrl());
    }

    @Test
    public static void testID19() {
        driver.findElement(By.xpath(String.format(CATALOG_MAIN_CATEGORY_BY_TEXT_XPATH, "Ткани для одежды"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_SUBCATEGORY_FROM_CATEGORY_PAGE_BY_TEXT_XPATH, "Атлас"))).click();

        driver.findElement(By.xpath(String.format(CATALOG_VIEW_FILTER_BY_TEXT_XPATH, "Сортировать"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_VIEW_FILTER_OPENED_DROPDOWN_ITEM_BY_TEXT_XPATH, "алфавиту"))).click();

        By productByXpath = By.xpath(String.format(CATALOG_PRODUCT_LINK_BY_TEXT_XPATH, "Атлас корсетный баклажан"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(productByXpath));
        new Actions(driver).scrollByAmount(0, driver.findElement(productByXpath).getLocation().getY()).perform();
        driver.findElement(productByXpath).click();

        driver.findElement(By.xpath(DESKTOP_PRODUCT_ADD_TO_CART_BUTTON_XPATH)).click();

        By plusButtonByXpath = By.xpath(String.format(DESKTOP_INFORMATION_BUTTON_BY_PLUS_OR_MINUS_CLASS_XPATH,"plus"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(plusButtonByXpath));
        driver.findElement(plusButtonByXpath).click();

        driver.findElement(By.xpath(DESKTOP_INFORMATION_GO_TO_BASKET_BUTTON_XPATH)).click();

        WebElement goToOrderButton = driver.findElement(By.xpath(DESKTOP_CART_GO_TO_ORDER_BUTTON_XPATH));
        new Actions(driver).scrollByAmount(0, goToOrderButton.getLocation().getY()).perform();
        goToOrderButton.click();

        driver.findElement(By.xpath(DESKTOP_CART_FORM_NEXT_BUTTON_XPATH)).click();

        driver.findElement(By.xpath(String.format(DESKTOP_CART_FORM_CITY_BY_TEXT_XPATH,"Минск"))).click();
        new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath(DESKTOP_CART_FORM_NEXT_BUTTON_XPATH)));
        WebElement nextButton = driver.findElement(By.xpath(DESKTOP_CART_FORM_NEXT_BUTTON_XPATH));
        new Actions(driver).scrollByAmount(0, nextButton.getLocation().getY()).perform();
        nextButton.click();

        driver.findElement(By.xpath(String.format(DESKTOP_CART_FORM_DELIVERY_BY_TEXT_XPATH,"Курьером по Минску, Ускоренная"))).click();
        nextButton = driver.findElement(By.xpath(DESKTOP_CART_FORM_NEXT_BUTTON_XPATH));
        new Actions(driver).scrollByAmount(0, nextButton.getLocation().getY()).perform();
        nextButton.click();

        driver.findElement(By.xpath(String.format(DESKTOP_CART_FORM_PAYMENT_BY_TEXT_XPATH,"Оплати"))).click();
        nextButton = driver.findElement(By.xpath(DESKTOP_CART_FORM_NEXT_BUTTON_XPATH));
        new Actions(driver).scrollByAmount(0, nextButton.getLocation().getY()).perform();
        nextButton.click();

        driver.findElement(By.xpath(String.format(DESKTOP_CART_FORM_PAYER_INFO_BY_TEXT_XPATH,"Телефон"))).click();
        driver.findElement(By.xpath(DESKTOP_CART_FORM_END_ORDER_BUTTON_XPATH)).click();

        System.out.println(driver.getCurrentUrl());
    }

    @Test
    public static void testID20() {
        new Actions(driver).scrollByAmount(0, 100).perform();

        driver.findElement(By.xpath(BACK_TO_TOP_BUTTON_XPATH)).click();
        System.out.println("Is back to top button displayed: " + driver.findElement(By.xpath(BACK_TO_TOP_BUTTON_XPATH)).isDisplayed());
    }

    @Test //TODO: Captcha
    public static void testCaptcha() {
        driver.findElement(By.xpath(String.format(NAV_MENU_AUTH_LINKS_BY_TEXT_XPATH, "Регистрация"))).click();

        WebElement form = driver.findElement(By.xpath("//div[@class='bx-auth-form-line' and div[contains(@class,'captha')]]"));

        new Actions(driver).scrollByAmount(0, form.getLocation().getY()).perform();

        File captcha = driver.findElement(By.xpath("//div[@class='bx-auth-form-captha-image']/img"))
                .getScreenshotAs(OutputType.FILE);


//        String str;
//        ITesseract image = new Tesseract();
//        image.setDatapath(Paths.get("src/test/resources/2tessdata3").toAbsolutePath().toString());
//        image.setLanguage("eng");
//        try {
//            str = image.doOCR(captcha);
//        } catch (TesseractException e) {
//            throw new RuntimeException(e);
//        }

//        System.out.println(str);
    }

    @Test
    public static void getIMG() {
        driver.findElement(By.xpath(String.format(NAV_MENU_AUTH_LINKS_BY_TEXT_XPATH, "Регистрация"))).click();

        for(int i=0;i<100;i++) {
            WebElement form = driver.findElement(By.xpath("//div[@class='bx-auth-form-line' and div[contains(@class,'captha')]]"));

            new Actions(driver).scrollByAmount(0, form.getLocation().getY()).perform();

            File captcha = driver.findElement(By.xpath("//div[@class='bx-auth-form-captha-image']/img"))
                    .getScreenshotAs(OutputType.FILE);

            captcha.renameTo(new File("~/img/img" + i + ".png"));
            driver.get(driver.getCurrentUrl());
        }

        System.out.println();

    //    String str;
//        ITesseract image = new Tesseract();
//        image.setDatapath(Paths.get("src/test/resources/2tessdata3").toAbsolutePath().toString());
//        image.setLanguage("eng");
//        try {
//            str = image.doOCR(captcha);
//        } catch (TesseractException e) {
//            throw new RuntimeException(e);
//        }

     //   System.out.println(str);
    }

    @AfterMethod
    public static void clear() {
        driver.manage().deleteAllCookies();
    }
    @AfterTest
    public static void end() {
        driver.quit();
    }
}
