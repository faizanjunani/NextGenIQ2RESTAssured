package Utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class BaseTest {

    SoftAssert softAssert = new SoftAssert();
    @BeforeSuite
    public static void setup() {
        RestAssured.baseURI = Config.addBooking;
    }

    public void printandAssertDataFromMap(String expected, String mapName, Response response, String jsonVariable)
    {
        Map<String,Object> data = response.jsonPath().getMap(mapName);
        String actualvalue = String.valueOf(data.get(jsonVariable));
        System.out.println("Actual Value = "+actualvalue+ ", Expected Value = "+expected);
        softAssert.assertEquals(actualvalue,expected, "Expected and Actual value does not match");
        softAssert.assertAll();
    }
    public void printandAssertDataFromMap(Boolean expected, String mapName, Response response, String jsonVariable)
    {
        Map<String,Object> data = response.jsonPath().getMap(mapName);
        String actualvalue = String.valueOf(data.get(jsonVariable));
        System.out.println("Actual Value = "+Boolean.valueOf(actualvalue)+ ", Expected Value = "+expected);
        softAssert.assertEquals(Boolean.valueOf(actualvalue),expected, "Expected and Actual value does not match");
    }

    @AfterSuite
    public void tearDown()
    {

    }
}
