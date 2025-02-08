package apiTests.pet;

import baseURL.BaseURL;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;


public class Post_Image extends BaseURL {

    @Test(groups = "positive")
    public void testPostPositive() {

        // swagger'de istenildiği şekilde masaüstünden image seçildi
        File imageFile = new File("C:/Users/Casper/Desktop/dogs.jpg");

        // daha önce oluşturduğumuz ID'ye image ekleme
        String petId = "987654321";

        Response response = given()
                .contentType("multipart/form-data")
                .multiPart("file", imageFile)
                .when()
                .post("/pet/" + petId + "/uploadImage");

        // response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        //assertion
        // status code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");

    }

    @Test(groups = "negative")
    public void testPostNegative() {
        // geçersiz ID ile
        String petId = "xxxx";

        File imageFile = new File("C:/Users/Casper/Desktop/dogs.jpg");

        Response response = given()
                .contentType("multipart/form-data")
                .multiPart("file", imageFile)
                .when()
                .post("/pet/" + petId + "/uploadImage");

        // response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        // statsus code assertion
        Assert.assertEquals(response.getStatusCode(), 404);
    }
}


