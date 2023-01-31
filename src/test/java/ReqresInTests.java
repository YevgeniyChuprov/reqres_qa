import org.junit.jupiter.api.Test;
import setting.ListUsers;
import setting.SingleUser;
import setting.Users;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ReqresInTests {

    @Test
    public void listUsers() {
        ListUsers listUsers = given()
                .spec(Specs.request)
                .when()
                .get("/users?page=2")
                .then()
                .spec(Specs.response200)
                .log().status()
                .body("data.findAll{it.id == 8}.last_name.flatten()",
                        hasItem("Ferguson"))
                .extract().as(ListUsers.class);

        assertEquals(2, listUsers.getPage());
        assertEquals(12, listUsers.getTotal());
    }

    @Test
    public void createUser() {
        Users users = new Users();
        users.setName("Jon");
        users.setJob("Work");

        Users createUsers = given()
                .spec(Specs.request)
                .body(users)
                .when()
                .post("/users")
                .then()
                .spec(Specs.response201)
                .extract().as(Users.class);

        assertEquals("Jon", createUsers.getName());
        assertEquals("Work", createUsers.getJob());
    }

    @Test
    public void getUser() {
        SingleUser user = given()
                .spec(Specs.request)
                .when()
                .get("/users/2")
                .then()
                .spec(Specs.response200)
                .extract().as(SingleUser.class);

        assertEquals("Janet", user.getData().getFirstName());
        assertEquals("Weaver", user.getData().getLastName());
    }


    @Test
    public void updateUser() {
        Users updateUser = new Users();
        updateUser.setName("morpheus");
        updateUser.setJob("zion resident");

        Users users = given()
                .spec(Specs.request)
                .body(updateUser)
                .when()
                .put("/users/2")
                .then()
                .spec(Specs.response200)
                .extract().as(Users.class);

        assertEquals("morpheus", users.getName());
        assertEquals("zion resident", users.getJob());
    }

    @Test
    public void deleteUser() {
        given()
                .spec(Specs.request)
                .when()
                .delete("/users2")
                .then()
                .spec(Specs.response204);
    }
}
