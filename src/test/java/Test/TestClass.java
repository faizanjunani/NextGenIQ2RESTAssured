package Test;

import Utils.BaseTest;
import Utils.Config;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.collections.Maps;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertNotNull;

public class TestClass extends BaseTest {

    private Config config = new Config();
    private RequestSpecification requestSpecification;
    private Response response;
    private int responseCode;
    SoftAssert softAssert = new SoftAssert();
    @Test(testName = "Add device")
    public void AddDevice()
    {
        String jsonRequestBody = "{ \"name\": \""+config.name+"\", \"data\": { \"year\": \""+config.year+"\", \"price\": \""+config.price+"\", \"CPU model\": \""+config.CPUModel+"\", \"Hard disk size\": \""+config.HDDSize+"\" } }";
        requestSpecification = given();
        //requestSpecification.header("Authorization","Bearer 29b834f73e53912fb8aaa568ccbedf5d769fbb20d9fae5139e53ccc3543b7a75");
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(jsonRequestBody);
        response = requestSpecification.post();
        responseCode = response.getStatusCode();
        System.out.println("Status Code is " +responseCode);
        System.out.println(response.getBody().asString());
        Assert.assertEquals(responseCode, 200);

    }
    @Test(testName = "verify response details",dependsOnMethods = "AddDevice")
    public void verifyResponseDetails()
    {
        String name = response.jsonPath().getString("name");
        Map<String,Object> data = response.jsonPath().getMap("data");
        softAssert.assertEquals(name,config.name);
        System.out.println("Actual Name = "+name+ " Expected Name "+config.name);
        softAssert.assertEquals(data.get("year"),config.year);
        System.out.println("Actual year = "+data.get("year")+ " Expected Name "+config.year);
        softAssert.assertEquals(data.get("price"),config.price);
        System.out.println("Actual Name = "+data.get("price")+ " Expected Name "+config.price);
        softAssert.assertEquals(data.get("CPU model"),config.CPUModel);
        System.out.println("Actual Name = "+data.get("CPU model")+ " Expected Name "+config.CPUModel);
        softAssert.assertEquals(data.get("Hard disk size"),config.HDDSize);
        System.out.println("Actual Name = "+data.get("Hard disk size")+ " Expected Name "+config.HDDSize);
        softAssert.assertAll();
    }
    @Test(testName = "verify null values",dependsOnMethods = "verifyResponseDetails")
    public void verifyForNullValues() {
        String Id = response.jsonPath().getString("id");
        System.out.println(Id);
        String createdAt = response.jsonPath().getString("createdAt");
        System.out.println(createdAt);
        assertNotNull(Id, "Id should not be null");
        assertNotNull(createdAt, "createdAt should not be null");
    }

}
