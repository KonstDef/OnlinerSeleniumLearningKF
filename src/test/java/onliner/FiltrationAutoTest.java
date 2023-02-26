package onliner;

import framework.BaseTest;
import io.qameta.allure.Description;
import onliner.pageObject.AvtobaracholkaPage;
import onliner.pageObject.MainPage;
import org.testng.annotations.Test;

import java.util.Map;

public class FiltrationAutoTest extends BaseTest {
    /*
    * 1. Go to 'Автобарахолка' page
    * 2. Set '' - '100000$' for 'Цена' range filter
    * 3. Set 'Седан' for 'Тип кузова' checkbox filter
    * 4. Set 'Автоматическая' for 'Коробка передач' checkbox filter
    * 5. Check filter selects correct element
    * */
    @Test(testName = "Test 'Автобарахолка' filtration")
    @Description("Test 'Автобарахолка' filtration by selecting 'Седан до 100 000$ с автоматической коробкой передач' filter.")
    public void filterAutoTest(){
        MainPage mainPage = new MainPage();
        mainPage.navigateWithHeaderLabel("Автобарахолка");

        AvtobaracholkaPage avtobaracholkaPage = new AvtobaracholkaPage();
        avtobaracholkaPage.isPageOpened();
        avtobaracholkaPage.setRangeForPriceFilter("","100000","USD");
        avtobaracholkaPage.selectCheckboxForFilter("Седан","Тип кузова");
        avtobaracholkaPage.selectCheckboxForFilter("Автоматическая","Коробка передач");

        Map<String,String> testData = Map.of(
                "$ price lesser","100000",
                "transmission","Автоматическая",
                "car","Седан"
        );
        avtobaracholkaPage.checkFirst(testData);
    }
}
