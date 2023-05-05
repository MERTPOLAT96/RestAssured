import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    @Test
    public void test(){

        given()

                //Hazırlık işlemleri: (token,send body,parametreler)
                .when()
                // endpoint(url), metodu



                .then()
                 // assertion,test, data işlemleri

        ;
    }

    @Test
    public void ilkTest(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body() // dönen body json datası, log.all()
                .statusCode(200) // dönüş kodu 200 mü
                .contentType(ContentType.JSON) // dönen sonuç JSON mu

                ;

    }

    @Test
    public void checkCountryInResponseBody(){

        given()

        .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body() // dönen body json datası, log.all()
                .statusCode(200) // dönüş kodu 200 mü
                .body("country", equalTo("United States"))






        ;



    }
}
