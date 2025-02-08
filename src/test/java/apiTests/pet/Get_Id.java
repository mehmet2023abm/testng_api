package apiTests.pet;

import baseURL.BaseURL;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Get_Id extends BaseURL {

    @Test(groups = "positive")
    public void testGetPositive() {
        // POST methodu ile oluşturduğumuz ID'li response çağırma
        int targetId = 987654321;

        Response petResponse = given()
                .pathParam("id", targetId)
                .when()
                .get("/pet/{id}");

        // response
        System.out.println("Response Status Code: " + petResponse.getStatusCode());
        System.out.println("Response Body: " + petResponse.getBody().asString());

        // Assertions
        // ID
        long id = petResponse.jsonPath().getLong("id");
        Assert.assertEquals(id, targetId, "Pet ID is incorrect");
        // category
        String categoryName = petResponse.jsonPath().getString("category.name");
        int categoryId = petResponse.jsonPath().getInt("category.id");
        Assert.assertEquals(categoryName, "string", "Category name is incorrect");
        Assert.assertEquals(categoryId, 10009, "Category ID is incorrect");
        // name
        String petName = petResponse.jsonPath().getString("name");
        Assert.assertEquals(petName, "doggie", "Pet name is incorrect");
        // uhoto URL
        List<String> photoUrls = petResponse.jsonPath().getList("photoUrls");
        Assert.assertTrue(photoUrls.contains("string"), "Photo URL is incorrect");
        // tags
        List<Integer> tagIds = petResponse.jsonPath().getList("tags.id");
        List<String> tagNames = petResponse.jsonPath().getList("tags.name");
        Assert.assertEquals(tagIds.get(0).intValue(), 10007, "Tag ID is incorrect");
        Assert.assertEquals(tagNames.get(0), "string", "Tag name is incorrect");
    }

    @Test(groups = "negative")
    public void testGetNegative() {
        // Geçersiz bir ID ile
        int invalidId = 1119999999;
        try {
            Response petResponse = RestAssured.given()
                    .pathParam("id", invalidId)
                    .when()
                    .get("/pet/{id}");

            // response
            System.out.println("Response Status Code: " + petResponse.getStatusCode());
            System.out.println("Response Body: " + petResponse.getBody().asString());
            // status code 404 bekleniyor (Pet Not Found)
            Assert.assertEquals(petResponse.getStatusCode(), 404);
        } catch (Exception e) {
            System.err.println("Oluşan hata: " + e.getMessage());
        }
    }
}

