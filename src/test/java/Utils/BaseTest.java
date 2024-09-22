package Utils;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    @BeforeSuite
    public static void setup() {
        RestAssured.baseURI = Config.BaseURL;
    }
}
