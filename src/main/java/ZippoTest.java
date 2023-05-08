import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
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
                .body("country", equalTo("United States")) // body nin country değişkeni "UNİTED STATES" eşit mi



        ;



    }

    @Test
    public void checkstateInResponseBody(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()
                .statusCode(200)
                .body("places[0].state", equalTo("California"))



        ;



    }

    @Test
    public void checkHasItemy(){

        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")


                .then()
                //.log().body()
                .statusCode(200)
                .body("places. 'place name'", hasItem("Dörtağaç Köyü"))
        //bütün place namelerin herhangi birinde Dörtağaç Köyü varmı



        ;



    }

    @Test
    public void bodyArrayHasSizeTest(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                //.log().body()
                .statusCode(200)
                .body("places", hasSize(1)) // size ı 1 mi

        ;



    }

    @Test
    public void combiningTest(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                //.log().body()
                .statusCode(200)
                .body("places", hasSize(1)) // size ı 1 mi
                .body("places.state", hasItem("California")) // verilen path de ki list bu iteme sahip mi
                .body("places[0].'place name'", equalTo("Beverly Hills")) // verilen path de ki değer buna eşit mi



        ;



    }
  @Test
    public void pathParamTest()
    {
        given()
                .pathParam("ulke","us")
                .pathParam("postaKod", 90210)
                .log().uri() //request link

                .when()
                .get("http://api.zippopotam.us/{ulke}/{postaKod}")

                .then()
                .statusCode(200)
                //.log().body()
                ;
    }

    @Test
    public void queryParamTest()
    {
        //https://gorest.co.in/public/v1/users?page=3

        given()
                .param("page", 1)
                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .statusCode(200)
                .log().body()

                ;
    }

    @Test
    public void queryParamTest2()
    {

        // https://gorest.co.in/public/v1/users?page=3
        // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
        // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.

        for (int i = 1; i < 10; i++) {

            given()

                    .param("page", i) //page=1 şeklinde linke ekleniyor
                    .log().uri() //request link

                    .when()
                    .get("https://gorest.co.in/public/v1/users")

                    .then()
                    .statusCode(200)
                    .log().body()
                    .body("meta.pagination.page" , equalTo(i))
                    ;

        }



    }

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void Setup(){

        baseURI="https://gorest.co.in/public/v1";

        requestSpec =new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setContentType(ContentType.JSON)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .log(LogDetail.BODY)
                .build();
    }

    @Test
    public void requestResponseSpecificationn()
    {
        //https://gorest.co.in/public/v1/users?page=3

        given()
                .param("page" ,1) //   ?page=1 şeklinde linke ekleniyor
                .spec(requestSpec)

                .when()
                .get("/users") // ?page=1

                .then()
                .spec(responseSpec)
                ;
    }

    @Test
    public void extractingJsonPath(){

        String countryName=

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .extract().path("country")

                ;

        System.out.println("countryName = " + countryName);
        Assert.assertEquals(countryName,"United States");
    }

    @Test
    public void extractingJsonPath2(){

        String placeName=

                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        .extract().path("places[0].'place name'")  //places[0]['place name']
                ;

        System.out.println("placeName = " + placeName);
        Assert.assertEquals(placeName,"Beverly Hills");
    }
}