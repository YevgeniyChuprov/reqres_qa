import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ReqresInTests {
    public String dataUser = "{\"name\": \"morpheus\",\"job\": \"leader\"}",
    updateUser = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}";


    @Test
    public void listUser(){
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("total", is(12));
    }

    @Test
    public void createUser() {
        given()
                .contentType(ContentType.JSON)
                .body(dataUser)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("job", is("leader"));
    }

    @Test
    public void getUser() {
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.first_name", is("Janet"));
    }

    @Test
    public void updateUser(){
        given()
                .contentType(ContentType.JSON)
                .body(updateUser)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("job", is("zion resident"));
    }

    @Test
    public void deleteUser() {
        given()
                .when()
                .delete("https://reqres.in/api/users2")
                .then()
                .statusCode(204);
    }
}
