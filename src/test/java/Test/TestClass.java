package Test;

import Utils.BaseTest;
import Utils.Config;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
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
    String bookingId;

    SoftAssert softAssert = new SoftAssert();
    @Test(testName = "Book Dates")
    @Step
    @Description("Book Dates")
    public void BookDates()
    {
        String jsonRequestBody = config.jsonRequestBody;

        requestSpecification = given();
        requestSpecification.filter(new AllureRestAssured());
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(jsonRequestBody);
        response = requestSpecification.post("/booking");
        responseCode = response.getStatusCode();
        System.out.println("Status Code for POST Request is " +responseCode);
        System.out.println(response.prettyPrint());
        softAssert.assertEquals(responseCode, 200);
        softAssert.assertAll();

    }
    @Test(testName = "verify response details",dependsOnMethods = "BookDates")
    @Step
    @Description("Verify the response details of booking")
    public void verifyResponseDetails()
    {
        printandAssertDataFromMap(config.firstName,"booking",response,"firstname");
        printandAssertDataFromMap(config.lastName,"booking",response,"lastname");
        printandAssertDataFromMap(String.valueOf(config.depositPaid),"booking",response,"depositpaid");
        printandAssertDataFromMap(String.valueOf(config.totalPrice),"booking", response,"totalprice");
        printandAssertDataFromMap(config.checkIn,"booking.bookingdates",response,"checkin");
        printandAssertDataFromMap(config.checkOut,"booking.bookingdates",response,"checkout");
    }
    @Test(testName = "verify null values",dependsOnMethods = "verifyResponseDetails")
    @Step
    @Description("Verify if any values are null")
    public void verifyForNullValues() {
        bookingId = response.jsonPath().getString("bookingid");
        System.out.println("Booking Id is = "+bookingId);
        softAssert.assertNotNull(bookingId, "Id should not be null");
        softAssert.assertAll();
    }

    @Test(testName = "list booking details by booking Id", dependsOnMethods = "verifyForNullValues",priority = 2)
    @Step
    @Description("List the booking details for the new booking Id")
    public void listBookingDetails()
    {
        requestSpecification = given();
        requestSpecification.filter(new AllureRestAssured());
        requestSpecification.baseUri(config.listBooking);
        requestSpecification.contentType(ContentType.JSON);
        response = requestSpecification.get("/booking/"+bookingId);
        responseCode = response.getStatusCode();
        System.out.println("Status Code for GET Request is " +responseCode);
        System.out.println(response.prettyPrint());
        softAssert.assertEquals(responseCode, 200, "Expected and Actual response code does not match");
        softAssert.assertAll();
    }

    @Test(testName = "verify invalid deposit paid details",dependsOnMethods = "verifyForNullValues", priority = 1)
    @Step
    @Description("check invalid deposit paid value")
    public void verifyInvalidDepositPaid()
    {
        printandAssertDataFromMap(false,"booking",response,"depositpaid");
        softAssert.assertAll();

    }

    @Test(testName = "list booking details with invalid booking Id", dependsOnMethods = "verifyInvalidDepositPaid")
    @Step
    @Description("List booking details with invalid booking Id")
    public void listBookingDetailswithInvalidBookingId()
    {
        requestSpecification = given();
        requestSpecification.filter(new AllureRestAssured());
        requestSpecification.baseUri(config.listBooking);
        requestSpecification.contentType(ContentType.JSON);
        response = requestSpecification.get("/booking/"+config.invalidBookingId);
        responseCode = response.getStatusCode();
        System.out.println("Status Code for GET Request is " +responseCode);
        System.out.println(response.prettyPrint());
        softAssert.assertEquals(responseCode, 404,"Expected and Actual response code does not match");
        softAssert.assertAll();

    }
}
