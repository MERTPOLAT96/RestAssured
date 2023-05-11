import GoRest.User;
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

}
