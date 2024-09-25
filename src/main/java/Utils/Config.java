package Utils;

public class Config {
    public static String addBooking="https://restful-booker.herokuapp.com";
    public static String listBooking = "https://restful-booker.herokuapp.com/";
    public String firstName = "Test FirstName";
    public String lastName = "Test LastName";
    public int totalPrice = 10;
    public Boolean depositPaid = true;
    public String checkIn = "2024-01-01";
    public String checkOut = "2024-01-10";
    public String additionalNeeds ="test additional needs";

    public String invalidBookingId = "240011";

    public String jsonRequestBody = "{ " +
            "\"firstname\": \""+firstName+"\"," +
            "\"lastname\": \""+lastName+"\"," +
            "\"totalprice\": \""+totalPrice+"\","+
            "\"depositpaid\": \""+depositPaid+"\"," +
            "\"bookingdates\": {\"checkin\": \""+checkIn+"\"," +
            "\"checkout\": \""+checkOut+"\"}," +
            "\"additionalneeds\": \""+additionalNeeds+"\"" +
            "}";
}
