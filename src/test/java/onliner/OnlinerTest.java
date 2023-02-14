package onliner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class OnlinerTest {
    static WebDriver driver;
    static int counter;

    public static final String INDEX_FOOTER_SOCIAL_LINK_BY_CLASS_XPATH = "//a[contains(@class,'social-button') and contains(@class,'%s')]";
    public static final String INDEX_PREFOOTER_FILTER_LINK_BY_TEXT_XPATH = "//div[@class='b-ba-layer']//following::div/footer/a[contains(text(),'%s')]";
    public static final String INDEX_FORUM_LEFT_LIST_LINK_BY_TEXT_XPATH = "//header[h2/a[contains(text(),'Форум')]]/following-sibling::div[contains(@class,'grid')]//li/a[contains(text(),'%s')]";
    public static final String INDEX_FORUM_TEASER_IMAGES_BY_TITLE_TEXT_XPATH = "//div[contains(@class,'forum-block')]//span[contains(text(),'%s')]//ancestor::li//img";
    public static final String INDEX_BARAHOLKA_POPULAR_LINKS_BY_TEXT_XPATH = "//ul[contains(@class,'b-tile-popular-list')]//a[span[contains(text(),'%s')]]";
    public static final String INDEX_REAL_VIEWS_BY_TITLE_TEXT_XPATH = "//header[h2/a[contains(text(),'Недвижимость')]]/following-sibling::div//a[contains(text(),'%s')]//ancestor::article//span[contains(@class,'views')]";
    public static final String INDEX_BEST_TABS_BY_TEXT_XPATH = "//div[@id='onliner-best-layer']//div[contains(@class,'page-tabs')]//a[contains(text(),'%s')]";
    public static final String INDEX_ALL_NEWS_BUTTON_BY_CATEGORY_TEXT_XPATH = "//header[h2/a[contains(text(),'%s')]]/following-sibling::footer/a";
    public static final String INDEX_SUBCATEGORY_LINK_OF_HEADLINER_NEWS_BY_CATEGORY_TEXT_XPATH = "//header[h2/a[contains(text(),'%s')]]/following-sibling::div//div[@class='tag']/a";
    public static final String INDEX_LINK_NEWS_FROM_TIME_XPATH = "//article[//span[contains(@class,'time') and contains(text(),'%1$s')]]/h2/a | //li[//span[contains(@class,'time') and contains(text(),'%1$s')]]//h3/a";
    public static final String INDEX_RSS_BY_TITLE_XPATH = "//header[h2/a[contains(text(),'%s')]]/a[@title='RSS']";
    public static final String INDEX_OFFER_PRODUCT_LINK_BY_CATALOG_CATEGORY_DATA_XPATH = "//a[contains(@data-item-name,'%s')]/../../div[contains(@class,'catalog-offers__title')]/a";
    public static final String INDEX_OFFER_PRODUCT_NAME_BY_PRICE_LOWER_THEN_XPATH = "//div[@data-price<%d]/div[contains(@class,'title')]";
    public static final String INDEX_HEADLINER_NEWS_IMAGE_BY_HEADLINE_CATEGORY_TEXT_XPATH = "//a[contains(@class,'b-tile-section') and contains(text(),'%1$s')]/..//img | //span[contains(@class,'b-tile-section') and contains(text(),'%1$s')]/..//img";
    public static final String HEADER_MAIN_NAV_BY_TEXT_XPATH = "//span[contains(@class,'b-main-navigation__text') and contains(text(),'%s')]";
    public static final String REAL_TAB_BY_TEXT_XPATH = "//span[contains(@class,'navigation__sign') and contains(text(),'%s')]";
    public static final String REAL_ZOOM_BY_TITLE_IN_OR_OUT_CSS = "[title='Zoom %s']";
    public static final String REAL_PAGE_BY_ADDRESS_XPATH = "//div[@data-id and //span[contains(@data-bind,'user_address') and contains(text(),'%s')]]/a";
    public static final String CATALOG_TYPE_CATEGORY_NAV_BY_TEXT_XPATH = "//li[@class='catalog-navigation-classifier__close']//following-sibling::li//span[contains(@class,'item-title-wrapper') and contains(text(),'%s')]";
    public static final String CATALOG_BRAND_CATEGORY_IMG_BY_TEXT_XPATH = "//ul[contains(@class,'classifier_brand')]//li[//span[contains(text(),'%s')]]//img";
    public static final String CATALOG_SUBCATEGORY_BY_TEXT_XPATH = "//div[contains(@style,'block')]//div[contains(@class,'aside-title') and contains(text(),'%s')]";
    public static final String CATALOG_CATEGORY_BAR_LINK_BY_TEXT_XPATH = "//a[contains(@class,'catalog-bar__link') and contains(text(),'%s')]";
    public static final String CATALOG_CATEGORY_PAGE_AGGREGATE_SWITCHER_BY_TEXT_XPATH = "//div[@id='schema-segments']//span[contains(@class,'switcher-inner') and contains(text(),'%s')]";
    public static final String CATALOG_CATEGORY_PAGE_COMPARE_CHECKBOX_BASE_PRODUCT_BY_TEXT_XPATH = "//span[contains(@data-bind,'product.full_name') and contains(text(),'%s')]//ancestor::div[@class='schema-product']/div/div[contains(@class,'compare')]//input";
    public static final String CATALOG_CATEGORY_PAGE_FILTER_TITLES_BY_TEXT_XPATH = "//div[@class='schema-filter__label']/span[contains(text(),'%s')]";
    public static final String CATALOG_CATEGORY_PAGE_FILTER_DROPDOWN_BY_TEXT_XPATH = CATALOG_CATEGORY_PAGE_FILTER_TITLES_BY_TEXT_XPATH + "/../following-sibling::div/div";
    public static final String HEADER_NEWS_CATEGORY_BY_TEXT_XPATH = String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Новости") + "/ancestor::li//a[contains(@class,'title-link') and contains(text(),'%s')]";
    public static final String HEADER_NEWS_GOTO_CATEGORY_LINK_BY_TEXT_XPATH = HEADER_NEWS_CATEGORY_BY_TEXT_XPATH + "/../following-sibling::div";
    public static final String HEADER_NEWS_ARTICLES_LINK_BY_PRIMARY_OR_SECONDARY_VIEW_TAG_CLASS_XPATH = "//span[contains(@class,'news-label_%s')]/ancestor::a";
    public static final String HEADER_POGODA_OR_KURS_LINK_BY_HREF_XPATH = "//a[contains(@class,'informers__link') and contains(@href,'%s')]";
    public static final String KURS_CURRENCY_ROW_BY_TEXT_CODE_XPATH = "//p[contains(@class,'rate')]/b[contains(text(),'%s')]/ancestor::tr[@class='tr-main']";
    public static final String KURS_CURRENCY_NBRB_VALUE_BY_TEXT_CODE_XPATH = KURS_CURRENCY_ROW_BY_TEXT_CODE_XPATH + "//p[contains(@class,'delta')]/preceding-sibling::p/b";
    public static final String KURS_CONVERTER_SWITCH_BY_TEXT_XPATH = "//label[contains(text(),'%s')]";
    public static final String POGODA_CITY_SELECT_DROPDOWN_VALUE_BY_TEXT_XPATH = "//div[contains(@class,'city-select')]//a[contains(text(),'%s')]";
    public static final String POGODA_WEEK_WEATHER_ICON_BY_TEXT_XPATH = "//dl[contains(@class,'date')]/dt[contains(text(),'%s')]/ancestor::li//div[contains(@class,'phenomena')]";
    public static final String FORUM_TITLES_BY_CATEGORY_TEXT_XPATH = "//h2[contains(text(),'%s')]/../following-sibling::ul[1]//a[contains(@class,'title')]";
    public static final String FORUM_MESSAGES_BY_CATEGORY_TITLE_TEXT_XPATH = "//div[contains(@class,'subj') and //a[contains(@class,'forumtitle') and contains(text(),'%s')]]/following-sibling::div[1]";
    public static final String FORUM_LAST_PAGE_BY_TOPIC_TITLE_TEXT_XPATH = "//h3[a[@class='topictitle' and contains(text(),'%s')]]/following-sibling::span/a[last()]";
    public static final String AB_LINK_BY_VEHICLE_NAME_TEXT_XPATH = "//div[contains(@class,'link_noreflex') and contains(text(),'%s')]/ancestor::a";
    public static final String AB_COLOR_CHECKBOX_BY_COLOR_TITLE_XPATH = "//ul[contains(@class,'list_color')]/li[contains(@title,'%s')]";

    @BeforeTest
    public static void startDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public static void openIndex() {
        driver.get("http://onliner.by");
        System.out.printf("---%d---\n", ++counter);
    }

    @Test
    public static void test01() {
        WebElement wE = driver.findElement(By.xpath(String.format(INDEX_FOOTER_SOCIAL_LINK_BY_CLASS_XPATH, "vk")));
        System.out.println("Onliner VK link: " + wE.getAttribute("href"));
    }

    @Test
    public static void test02() {
        WebElement wE = driver.findElement(By.xpath(String.format(INDEX_PREFOOTER_FILTER_LINK_BY_TEXT_XPATH, "Новое за 24 часа")));
        System.out.println("Link to new news: " + wE.getAttribute("href"));
    }

    @Test
    public static void test03() {
        WebElement wE = driver.findElement(By.xpath(String.format(INDEX_FORUM_LEFT_LIST_LINK_BY_TEXT_XPATH, "Выбор")));
        System.out.println("Forum theme link: " + wE.getAttribute("href"));
    }

    @Test
    public static void test04() {
        By teaserImgXpath = By.xpath(String.format(INDEX_FORUM_TEASER_IMAGES_BY_TITLE_TEXT_XPATH, "а"));
        new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(teaserImgXpath));

        WebElement wE = driver.findElement(teaserImgXpath);
        System.out.println("Image to forum teaser: " + wE.getAttribute("src"));
    }

    @Test
    public static void test05() {
        WebElement wE = driver.findElement(By.xpath(String.format(INDEX_BARAHOLKA_POPULAR_LINKS_BY_TEXT_XPATH, ".")));
        System.out.println("Popular Baraholka categories: " + wE.getAttribute("src"));
    }

    @Test
    public static void test06() {
        WebElement wE = driver.findElement(By.xpath(String.format(INDEX_REAL_VIEWS_BY_TITLE_TEXT_XPATH, "на")));
        System.out.println("Real estate headliner views: " + wE.getText());
    }

    @Test
    public static void test07() {
        WebElement wE = driver.findElement(By.xpath(String.format(INDEX_BEST_TABS_BY_TEXT_XPATH, "Видео")));
        wE.click();
        System.out.println("Видео clicked");
    }

    @Test
    public static void test08() {
        WebElement wE = driver.findElement(By.xpath(String.format(INDEX_ALL_NEWS_BUTTON_BY_CATEGORY_TEXT_XPATH, "Технологии")));
        wE.click();
        System.out.println("Все новости про технологии clicked");
    }

    @Test
    public static void test09() {
        WebElement wE = driver.findElement(By.xpath(String.format(INDEX_SUBCATEGORY_LINK_OF_HEADLINER_NEWS_BY_CATEGORY_TEXT_XPATH, "Технологии")));
        System.out.println("Технологии headliner category: "+wE.getText());
        wE.click();
    }

    @Test
    public static void test10() {
        List<WebElement> wE = driver.findElements(By.xpath(String.format(INDEX_LINK_NEWS_FROM_TIME_XPATH, "2")));
        System.out.println("New news came in 2 hours:");
        wE.forEach(w -> System.out.println(w.getAttribute("href")));
    }

    @Test
    public static void test11() {
        List<WebElement> wE = driver.findElements(By.xpath(String.format(INDEX_RSS_BY_TITLE_XPATH, "Кошелек")));
        System.out.println("RSS link to Кошелек:");
        wE.forEach(w -> System.out.println(w.getAttribute("href")));
    }

    @Test
    public static void test12() {
        List<WebElement> wE = driver.findElements(By.xpath(String.format(INDEX_OFFER_PRODUCT_LINK_BY_CATALOG_CATEGORY_DATA_XPATH, "Умные часы")));
        System.out.println("Link to products in category умные часы appeared at main:");
        wE.forEach(w -> System.out.println(w.getAttribute("href")));
    }

    @Test
    public static void test13() {
        List<WebElement> wE = driver.findElements(By.xpath(String.format(INDEX_OFFER_PRODUCT_NAME_BY_PRICE_LOWER_THEN_XPATH, 150)));
        System.out.println("Products cheaper then 150BYN:");
        wE.stream().filter(WebElement::isDisplayed).forEach(w -> System.out.println(w.getText()));
    }

    @Test
    public static void test14() {
        List<WebElement> wE = driver.findElements(By.xpath(String.format(INDEX_HEADLINER_NEWS_IMAGE_BY_HEADLINE_CATEGORY_TEXT_XPATH, "Спецпроект")));
        System.out.println("News with tag спецпроект:");
        wE.forEach(w -> System.out.println(w.getAttribute("src")));
    }

    @Test
    public static void test15() {
        WebElement wE = driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Дома")));
        System.out.println("Link to Дома и квартиры: " + wE.getAttribute("href"));
    }

    @Test
    public static void test16() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Дома"))).click();

        WebElement wE = driver.findElement(By.xpath(String.format(REAL_TAB_BY_TEXT_XPATH, "Аренда")));
        System.out.println("Tab has name: " + wE.getText());
        wE.click();
    }

    @Test
    public static void test17() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Дома"))).click();

        WebElement wE = driver.findElement(By.cssSelector(String.format(REAL_ZOOM_BY_TITLE_IN_OR_OUT_CSS, "out")));
        System.out.println("Button + on map has text: " + wE.getText());
    }

    @Test
    public static void test18() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Дома"))).click();

        WebElement wE = driver.findElement(By.xpath(String.format(REAL_PAGE_BY_ADDRESS_XPATH, "3")));
        System.out.println("Estate with address containing 3 has href: " + wE.getAttribute("href"));
        wE.click();
    }

    @Test
    public static void test19() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Каталог"))).click();

        WebElement wE = driver.findElement(By.xpath(String.format(CATALOG_TYPE_CATEGORY_NAV_BY_TEXT_XPATH, "Электроника")));
        System.out.println("Электроника category has name: " + wE.getText());
        wE.click();
    }

    @Test
    public static void test20() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Каталог"))).click();

        WebElement wE = driver.findElement(By.xpath(String.format(CATALOG_BRAND_CATEGORY_IMG_BY_TEXT_XPATH, "Xiaomi")));
        System.out.println("Image of Xiaomi category: " + wE.getAttribute("src"));
    }

    @Test
    public static void test21() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Каталог"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_TYPE_CATEGORY_NAV_BY_TEXT_XPATH, "Электроника"))).click();

        WebElement wE = driver.findElement(By.xpath(String.format(CATALOG_SUBCATEGORY_BY_TEXT_XPATH, "Оптические")));
        System.out.println("Full name of Оптические category: " + wE.getText());
        wE.click();
    }

    @Test
    public static void test22() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Каталог"))).click();

        WebElement wE = driver.findElement(By.xpath(String.format(CATALOG_CATEGORY_BAR_LINK_BY_TEXT_XPATH, "Наушники")));
        System.out.println("Link to Наушники category: " + wE.getAttribute("href"));
    }

    @Test
    public static void test23() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Каталог"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_CATEGORY_BAR_LINK_BY_TEXT_XPATH, "Наушники"))).click();

        WebElement wE = driver.findElement(By.xpath(String.format(CATALOG_CATEGORY_PAGE_AGGREGATE_SWITCHER_BY_TEXT_XPATH, "Объявления")));
        System.out.println("Switcher in catalog has option: " + wE.getText());
        wE.click();
    }

    @Test
    public static void test24() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Каталог"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_CATEGORY_BAR_LINK_BY_TEXT_XPATH, "Наушники"))).click();

        By appleByXpath = By.xpath(String.format(CATALOG_CATEGORY_PAGE_COMPARE_CHECKBOX_BASE_PRODUCT_BY_TEXT_XPATH, "Apple"));
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.presenceOfElementLocated(appleByXpath));
        WebElement wE = driver.findElement(appleByXpath);
        System.out.println("Element to the left of product is: " + wE.getAttribute("type"));
    }

    @Test
    public static void test25() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Каталог"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_CATEGORY_BAR_LINK_BY_TEXT_XPATH, "Наушники"))).click();

        WebElement wE = driver.findElement(By.xpath(String.format(CATALOG_CATEGORY_PAGE_FILTER_TITLES_BY_TEXT_XPATH, "Производитель")));
        System.out.println("There is filter: " + wE.getText());
    }

    @Test
    public static void test26() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Каталог"))).click();
        driver.findElement(By.xpath(String.format(CATALOG_CATEGORY_BAR_LINK_BY_TEXT_XPATH, "Наушники"))).click();

        WebElement wE = driver.findElement(By.xpath(String.format(CATALOG_CATEGORY_PAGE_FILTER_DROPDOWN_BY_TEXT_XPATH, "Производитель")));
        System.out.println("Dropdown of Производитель filter is : " + wE.getText());
    }

    @Test
    public static void test27() {
        WebElement wE = driver.findElement(By.xpath(String.format(HEADER_NEWS_CATEGORY_BY_TEXT_XPATH, "Кошелек")));
        System.out.println("Category of Кошелек news: " + wE.getAttribute("href"));
    }

    @Test
    public static void test28() {
        WebElement wE = driver.findElement(By.xpath(String.format(HEADER_NEWS_GOTO_CATEGORY_LINK_BY_TEXT_XPATH, "Кошелек")));
        System.out.println("Redirection to Кошелек news: " + wE.getAttribute("href"));
    }

    @Test
    public static void test29() {
        List<WebElement> wE = driver.findElements(By.xpath(String.format(HEADER_NEWS_ARTICLES_LINK_BY_PRIMARY_OR_SECONDARY_VIEW_TAG_CLASS_XPATH, "primary")));
        System.out.println("News at header with white label: ");
        wE.forEach(w -> System.out.println(w.getAttribute("href")));
    }

    @Test
    public static void test30() {
        WebElement wE = driver.findElement(By.xpath(String.format(HEADER_POGODA_OR_KURS_LINK_BY_HREF_XPATH, "kurs")));
        System.out.println("Link to Курсы валют page: " + wE.getAttribute("href"));
        wE.click();
    }

    @Test
    public static void test31() {
        By kursByXpath = By.xpath(String.format(HEADER_POGODA_OR_KURS_LINK_BY_HREF_XPATH, "kurs"));
        new WebDriverWait(driver, Duration.ofSeconds(100)).until(ExpectedConditions.elementToBeClickable(kursByXpath));
        driver.findElement(kursByXpath).click();

        WebElement wE = driver.findElement(By.xpath(String.format(KURS_CURRENCY_ROW_BY_TEXT_CODE_XPATH, "EUR")));
        System.out.printf("%s is present: %b\n", "EUR", wE.isDisplayed());
    }

    @Test
    public static void test32() {
        By kursByXpath = By.xpath(String.format(HEADER_POGODA_OR_KURS_LINK_BY_HREF_XPATH, "kurs"));
        new WebDriverWait(driver, Duration.ofSeconds(100)).until(ExpectedConditions.elementToBeClickable(kursByXpath));
        driver.findElement(kursByXpath).click();

        WebElement wE = driver.findElement(By.xpath(String.format(KURS_CURRENCY_NBRB_VALUE_BY_TEXT_CODE_XPATH, "RUB")));
        System.out.println("Value of 100RUB to BYN by NBRB " + wE.getText());
    }

    @Test
    public static void test33() {
        By kursByXpath = By.xpath(String.format(HEADER_POGODA_OR_KURS_LINK_BY_HREF_XPATH, "kurs"));
        new WebDriverWait(driver, Duration.ofSeconds(100)).until(ExpectedConditions.elementToBeClickable(kursByXpath));
        driver.findElement(kursByXpath).click();

        WebElement wE = driver.findElement(By.xpath(String.format(KURS_CONVERTER_SWITCH_BY_TEXT_XPATH, "Продать")));
        System.out.println("BYN is going to be: " + wE.getAttribute("for"));
    }

    @Test
    public static void test34() {
        By pogodaByXpath = By.xpath(String.format(HEADER_POGODA_OR_KURS_LINK_BY_HREF_XPATH, "pogoda"));
        new WebDriverWait(driver, Duration.ofSeconds(100)).until(ExpectedConditions.elementToBeClickable(pogodaByXpath));
        driver.findElement(pogodaByXpath).click();

        WebElement wE = driver.findElement(By.xpath(String.format(POGODA_CITY_SELECT_DROPDOWN_VALUE_BY_TEXT_XPATH, "Брест")));
        System.out.println("Brest city id for weather: " + wE.getAttribute("data-wmoid"));
    }

    @Test
    public static void test35() {
        By pogodaByXpath = By.xpath(String.format(HEADER_POGODA_OR_KURS_LINK_BY_HREF_XPATH, "pogoda"));
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(pogodaByXpath));
        driver.findElement(pogodaByXpath).click();

        WebElement wE = driver.findElement(By.xpath(String.format(POGODA_WEEK_WEATHER_ICON_BY_TEXT_XPATH, "среда")));
        System.out.println("In среда phenomena expected: " + wE.getAttribute("data-phenomena"));
    }

    @Test
    public static void test36() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Форум"))).click();

        WebElement wE = driver.findElement(By.xpath(String.format(FORUM_TITLES_BY_CATEGORY_TEXT_XPATH, "Технологии")));
        System.out.println("Link to first forum subcategory of Технологии: " + wE.getAttribute("href"));
    }

    @Test
    public static void test37() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Форум"))).click();

        WebElement wE = driver.findElement(By.xpath(String.format(FORUM_MESSAGES_BY_CATEGORY_TITLE_TEXT_XPATH, "Операторы")));
        System.out.println("Операторы has messages: " + wE.getText());
    }

    @Test
    public static void test38() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Форум"))).click();
        driver.findElement(By.xpath(String.format(FORUM_TITLES_BY_CATEGORY_TEXT_XPATH, "Технологии"))).click();

        WebElement wE = driver.findElement(By.xpath(String.format(FORUM_LAST_PAGE_BY_TOPIC_TITLE_TEXT_XPATH, "5G")));
        System.out.println("Link to 5G forum: " + wE.getAttribute("href"));
        wE.click();
    }

    @Test
    public static void test39() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Авто"))).click();

        WebElement wE = driver.findElement(By.xpath(String.format(AB_LINK_BY_VEHICLE_NAME_TEXT_XPATH, "BMW")));
        System.out.println("Link to first BMW car at AB: " + wE.getAttribute("href"));
        wE.click();
    }

    @Test
    public static void test40() {
        driver.findElement(By.xpath(String.format(HEADER_MAIN_NAV_BY_TEXT_XPATH, "Авто"))).click();

        WebElement wE = driver.findElement(By.xpath(String.format(AB_COLOR_CHECKBOX_BY_COLOR_TITLE_XPATH, "Бежевый")));
        System.out.println("Бежевый color is: " + wE.getAttribute("title"));
    }

    @AfterTest
    public static void end() {
        driver.quit();
    }
}
