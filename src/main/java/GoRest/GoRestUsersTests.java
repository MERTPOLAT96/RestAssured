package GoRest;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoRestUsersTests {

    int userID;
    @Test
    public void createUser(){

        //ihtiyaç listesi

        //POST https://gorest.co.in/public/v2/users

        // "Authorization: Bearer 5c865d1b4264217495f862edcbff57de3aebccc73c65b3e839eb7101deaa1c64

        //{"name":"{{$randomFullName}}", "gender":"male", "email":"{{$randomEmail}}", "status":"active"}

        Faker randomUretici=new Faker();

        String rndFullname=randomUretici.name().fullName();
        String rndEmail=randomUretici.internet().emailAddress();



        given()

                .header("Authorization","Bearer 5c865d1b4264217495f862edcbff57de3aebccc73c65b3e839eb7101deaa1c64")
                .contentType(ContentType.JSON) // gönderilecek data Json
                .body("{\"name\":\""+rndFullname+"\", \"gender\":\"male\", \"email\":\""+rndEmail+"\", \"status\":\"active\"}")
                .log().uri()
                .log().body()

                .when()
                .post("https://gorest.co.in/public/v2/users")


                .then()
                .log().body()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .extract().path("id")


                ;


    }

    @Test
    public void getUserByID(){


    }

    @Test
    public void updateUser(){


    }

    @Test
    public void deleteUser(){


    }

    @Test
    public void deleteUserNegative(){


    }
}
