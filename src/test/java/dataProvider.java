import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class dataProvider {
    @DataProvider (name = "data-provider")
    public Object[][] dpMethod(){
        return new Object[][]{
                {"Value1",1},
                {"Value2",2}};
    }

    @Test (dataProvider = "data-provider")
    public void myTest (String val, int i){
        System.out.println("Passed Parameter Is : " + val + i);
    }
}
