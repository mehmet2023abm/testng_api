package apiTests.store;

import baseURL.BaseURL;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Get extends BaseURL {

    @Test(priority = 1)
    public void testPositive() {

        Response response = given()
                .when()
                .get("/store/inventory");

        // response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // status code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");

        // response body deki bazı değerlerin varlığının kontrolü
        Assert.assertTrue(response.jsonPath().getMap("$").containsKey("sold"), "'sold' key is not present");
    }

    @Test(priority = 2)
    public void testNegative() {

        // Yanlış bir endpoint ile
        try {
            Response response = given()
                    .when()
                    .get("/store/inventory/xxx");

            // response
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody().asString());

            // status code
            Assert.assertEquals(response.getStatusCode(), 404, "Status code is not 404");
        }catch (Exception e) {
            System.err.println("Oluşan hata: " + e.getMessage());
        }
    }
}

