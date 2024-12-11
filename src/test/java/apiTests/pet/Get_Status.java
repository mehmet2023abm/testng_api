package apiTests.pet;

import baseURL.BaseURL;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Get_Status extends BaseURL {

    @Test(groups = "positive")
    public void testGetPositive() {

        //üç status değeri (available,pending,sold) için ...
        String statuses = "available,pending,sold";

        Response response = given()
                .param("status", statuses)
                .when()
                .get("/pet/findByStatus");

        //// response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // belirlenen bir ID ile assertion'lar
        long targetId = 703850L;
        // ID
        long id = response.jsonPath().getLong("find { it.id == " + targetId + " }.id");
        Assert.assertEquals(id, targetId, "Pet ID is incorrect");
        // category
        String categoryName = response.jsonPath().getString("find { it.id == " + targetId + " }.category.name");
        int categoryId = response.jsonPath().getInt("find { it.id == " + targetId + " }.category.id");
        Assert.assertEquals(categoryName, "string", "Category name is incorrect");
        Assert.assertEquals(categoryId, 2020, "Category ID is incorrect");
        // pet name
        String petName = response.jsonPath().getString("find { it.id == " + targetId + " }.name");
        Assert.assertEquals(petName, "doggie", "Pet name is incorrect");
        // photo URL
        List<String> photoUrls = response.jsonPath().getList("find { it.id == " + targetId + " }.photoUrls");
        Assert.assertFalse(photoUrls.isEmpty(), "Photo URLs list is empty");
        Assert.assertTrue(photoUrls.contains("string"), "Photo URL is incorrect");
        // tags a
        List<Integer> tagIds = response.jsonPath().getList("find { it.id == " + targetId + " }.tags.id");
        List<String> tagNames = response.jsonPath().getList("find { it.id == " + targetId + " }.tags.name");
        Assert.assertEquals(tagIds.get(0).intValue(), 0, "Tag ID is incorrect");
        Assert.assertEquals(tagNames.get(0), "string", "Tag name is incorrect");
        // status
        String status = response.jsonPath().getString("find { it.id == " + targetId + " }.status");
        Assert.assertEquals(status, "available", "Status is incorrect");
    }


    @Test(groups = "positive")
    public void testGetNegative() {

        // geçersiz bir status değeri ile ...
        String invalidStatusValue = "xxxxyyyyyzzzzzz";

        Response response = given()
                .param("status", invalidStatusValue)
                .when()
                .get("/pet/findByStatus");

        // response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        // status code
        Assert.assertEquals(response.getStatusCode(), 400, "Status code is not 400");

        // HATA: Swagger dökümanında beklenen 400 kod yerine 200 kod döndürüyor.
    }
}