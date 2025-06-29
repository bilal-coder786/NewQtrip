package qtriptest.utils;


import qtriptest.utils.DP;
import java.io.IOException;
import org.testng.annotations.DataProvider;

public class ExternalDataProvider {
    //private final DP dataProvider = new DP();
    DP dataProvider = new DP();

   @DataProvider(name = "qtripData")
   public Object[][] qtripData() throws IOException {
       return dataProvider.dpMethod("Sheet1");
   }


    @DataProvider(name = "qtripCityData")
    public Object[][] qtripCityData() throws IOException {
        //DP dataProvider = new DP();
        return dataProvider.dpMethod("Sheet2");
    }

    @DataProvider(name = "qtripNewUserData")
    public Object[][] qtripNewUserData() throws IOException {
       // DP dataProvider = new DP();
        return dataProvider.dpMethod("Sheet3");
    }

    @DataProvider(name = "qtripNewUserDataSet")
    public Object[][] qtripeNwUserDataSet() throws IOException {
        return dataProvider.dpMethod("Sheet4");
}
}
