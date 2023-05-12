import GoRest.User;
import Model.Location;
import Model.Place;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;


public class PathAndJsonPath {

    @Test
    public void extractingPath() {

        int postCode =

                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        .extract().path("'post code'");
        System.out.println("postCode = " + postCode);


    }

    @Test
    public void extractingJSONPath() {

        int postCode =

                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        .extract().jsonPath().getInt("'post code'");
        System.out.println("postCode = " + postCode);
    }

    @Test
    public void getUsers()
    {
        Response response=
        given()
                .when()
                .get("https://gorest.co.in/public/v2/users")

                .then()
                //.log().body()
                .extract().response();

        ;

               int idPath= response.path("[2].id");
               int idJSONPath= response.jsonPath().getInt("[2].id");

        System.out.println("idPath = " + idPath);
        System.out.println("idJSONPath = " + idJSONPath);

        User[] usersPath= response.as(User[].class); // as nesne dönüşümde (POJO) dizi destekli
        List<User> usersJsonPath =response.jsonPath().getList("", User.class); // JSONPATH ise list olarak verilebiliyor

        System.out.println("usersPath = " + Arrays.toString(usersPath));
        System.out.println("usersJsonPath = " + usersJsonPath);
    }

    @Test
    public void getUsersV1(){

        Response body=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                //log().body()
                        .extract().response();

        List<User> dataUsers=body.jsonPath().getList("data" , User.class);
        //JSONPATH bir response içindeki bir parçayı nesneye dönüştürebiliriz.
        System.out.println("dataUsers = " + dataUsers);

        //Daha önceki örneklerde (as) Class dönüşümleri için tüm yapıya karşılık gelen
        //gereken tüm classları yazarak dönüştürüp istediğimiz elemanlara ulaşıyorduk.
        //Burada ise(JSONPATH) aradaki bir veriyi classa dönüştürerek bir list olarak atamamıza
        //imkan veren JSONPATH i kullandık.Böylece tyek class ise veri alınmış oldu
        //diğer class lara gerek kalmadan

        //path: class  veya tip dönüşümüne imkan veremeyen direk veriyi verir.List<String> gibi
        //JSONPATH : class dönüşümüne ve tip dönüşümüne izin vererek veriyi istediğimiz formatta verir.
    }

    @Test
    public void getZipCode(){

        Response response =

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .extract().response()

                ;

        Location locPathAs = response.as(Location.class); // Bütün classları yazmak zorundasın

        System.out.println("locPathAs = " + locPathAs.getPlaces());

        List<Place> places=response.jsonPath().getList("places", Place.class); // nokta atışı istediğimiz nesneyi aldık
        System.out.println("places = " + places);
    }

}
