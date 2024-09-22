package Test;

import Utils.BaseTest;
import Utils.Config;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.collections.Maps;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertNotNull;

public class TestClass extends BaseTest {

    Config config = new Config();
    protected RequestSpecification requestSpecification;
    protected Response response;
    protected int responseCode;
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
    @Test(testName = "verify response details", dependsOnMethods = "AddDevice")
    public void verifyResponseDetails()
    {
        String name = response.jsonPath().getString("name");
        Map<String,Object> data = response.jsonPath().getMap("data");
        Assert.assertEquals(name,config.name);
        Assert.assertEquals(data.get("year"),config.year);
        Assert.assertEquals(data.get("price"),config.price);
        Assert.assertEquals(data.get("CPU model"),config.CPUModel);
        Assert.assertEquals(data.get("Hard disk size"),config.HDDSize);

    }
    @Test(testName = "verify null values",dependsOnMethods = "verifyResponseDetails")
    public void verifyForNullValues()
    {
        String Id = response.jsonPath().getString("id");
        System.out.println(Id);
        String createdAt = response.jsonPath().getString("createdAt");
        System.out.println(createdAt);
        assertNotNull(Id,"Id should not be null");
        assertNotNull(createdAt,"createdAt should not be null");

    }
}
