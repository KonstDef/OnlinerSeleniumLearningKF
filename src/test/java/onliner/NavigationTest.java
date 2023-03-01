package onliner;

import framework.BaseTest;
import io.qameta.allure.Description;
import onliner.pageObject.CatalogPage;
import onliner.pageObject.MainPage;
import org.testng.annotations.Test;

public class NavigationTest extends BaseTest {

    /*
     * 1. Go to Каталог page | Каталог page opened
     * 2. Click on Компьютеры label | Subcategory selection opened
     * 3. Move mouse to "Компьтеры, ноутбуки..." | Subcategory items submenu displayed
     * 4. Click on "Игровые ноутбуки..." | Redirected to "Игровые ноутбуки" page
     * */

    @Test
    @Description("Navigate to \"Игровые ноутбуки subcategory\". ExpectedR: Page 'Ноутбуки...' opened.")
    public void onlinerNavTest() {
        MainPage mainPage = new MainPage();
        mainPage.navigateWithHeaderLabel("Каталог");

        CatalogPage catalogPage = new CatalogPage();
        catalogPage.navigateToSubmenuLabel();
        catalogPage.moveToSubmenuItem();
        catalogPage.navigateToSubmenuCategory();
    }
}
