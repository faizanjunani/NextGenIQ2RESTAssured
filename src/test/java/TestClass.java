import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertNotNull;

public class TestClass {

    RequestSpecification requestSpecification;
    Response response;
    int responseCode;
    @Test
    public void AddDevice()
    {
        RestAssured.baseURI = "https://api.restful-api.dev/objects";
        //RestAssured.basePath = "/api-clients";

        String jsonRequestBody = "{ \"name\": \"Apple Max Pro 1TB\", \"data\": { \"year\": 2023, \"price\": 7999.99, \"CPU model\": \"Apple ARM A7\", \"Hard disk size\": \"1 TB\" } }";
        requestSpecification = given();
        //requestSpecification.header("Authorization","Bearer 29b834f73e53912fb8aaa568ccbedf5d769fbb20d9fae5139e53ccc3543b7a75");
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(jsonRequestBody);
        response = requestSpecification.post();
        responseCode = response.getStatusCode();
        System.out.println("Status Code is " +responseCode);
        System.out.println(response.getBody().asString());
        Assert.assertEquals(responseCode, 200);
        String Id = response.jsonPath().getString("id");
        System.out.println(Id);
        String createdAt = response.jsonPath().getString("createdAt");
        System.out.println(createdAt);
        assertNotNull(Id,"Id should not be null");
        assertNotNull(createdAt,"createdAt should not be null");

      /*  given()
                .when()
                .post(baseURI)
                .then().statusCode(201);*/
    }

    public void ReadJSONResponse()
    {
    /*    AddDevice();
        String responseBody = response.getBody().toString();
        System.out.println(responseBody);

        given()
                .when().get(baseURI)
                .then().log().body();*/
    }
    public void ReadJSONResponseAndValdiate()
    {
       /* AddDevice();
        String responseBody = response.getBody().toString();
        Assert.assertTrue(responseBody.contains("a"));

        given()
                .when().get(baseURI)
                .getBody().asString().contains("a");*/
    }
}
