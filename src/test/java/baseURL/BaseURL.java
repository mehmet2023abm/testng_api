package baseURL;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BaseURL {

    // Base URL tanımını burada yapıyoruz
    public static final String BASE_URL = "https://petstore.swagger.io/v2";

    @BeforeSuite
    // BaseURL'i testlerde kullanılabilir hale getirmek için setup metodu
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

}
