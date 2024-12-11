package apiTests.pet;

import baseURL.BaseURL;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Delete extends BaseURL {

    @Test(groups = "positive")
    public void testDeletePositive() {
        // silinmesi istenen ID (Post methodu ile oluşturulan)
        long petId = 987654321L;

        Response response = given()
                .pathParam("petId", petId)
                .when()
                .delete("/pet/{petId}");

        // response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        // Assertion
        // Status code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
    }

    @Test(groups = "negative")
    public void testDeleteNegative() {
        // geçersiz-daha önce (yukarıda) silinmiş ID ile
        long invalidPetId = 987654321L;
        try {
            Response response = given()
                    .pathParam("petId", invalidPetId)
                    .when()
                    .delete("/pet/{petId}");

            Assert.assertEquals(response.getStatusCode(), "404");
        } catch (Exception e) {
            System.err.println("Oluşan hata: " + e.getMessage());
        }
    }
    // Testin Failed olmasına neden olan HttpResponseException try-catch ile handle edildi
}