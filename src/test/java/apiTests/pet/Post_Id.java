package apiTests.pet;

import baseURL.BaseURL;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Post_Id extends BaseURL {

    @Test(groups = "positive")
    public void testPostPositive() {
            /* HATA :
                 int petId = 123456789;
                Response response = given()
                .contentType("application/json")
                .when()
                .post("/pet/" + petId);
                .............yukarıdaki gibi endpoint sonuna id ekleyerek denendi fakat aşağıdaki hata alındı (not: swagger dökümanında da id ile post yapılamamakta)
                Response Status Code: 415
                Response Body: <apiResponse>
                <type>unknown</type>
                </apiResponse>

                ÇÖZÜM :
                Aşağıdaki şekilde Json Body içinde gönderimle Post edildi*/

        String requestBody = "{\n" +
                "  \"id\": 123456789\n" +
                "}";

        // Sending the POST request
        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/pet");

        // response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asPrettyString());
        // assertions
        // status assertion
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
        // Id
        int actualId = response.jsonPath().getInt("id");
        Assert.assertEquals(actualId, 123456789);
    }

    @Test(groups = "negative")
    public void testPostNegative() {
        // geçersi Id ile
        String requestBody = "{\n" +
                "  \"id\": xxxx\n" +
                "}";

        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/pet");

        // response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asPrettyString());
        // status assertion
        Assert.assertEquals(response.getStatusCode(), 400);
    }
}
