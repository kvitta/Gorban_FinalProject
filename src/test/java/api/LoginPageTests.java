package api;

import jdk.jfr.Name;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class LoginPageTests extends AbstractTest{
    @Test
    @Name("Авторизация с валидным логином и паролем")
    void loginValidValue (){
         given().spec(requestSpecification)
            .body("{\n"
                          + " \"username\": test22,\n"
                          + " \"password\":4d42bf9c18,\n"
                          + "}")
                .when()
                .post(getBaseUrl()+"login")// делаем postзапрос
                .then()
                .statusCode(200);


    }
    @Test
    @Name("Авторизация с паролем содержащим символы")
    void loginInvalidValue (){
        given().spec(requestSpecification)
                .body("{\n"
                        + " \"username\":2344*?*:?:?,\n"
                        + " \"password\":555555,\n"
                        + "}")
                .when()
                .post(getBaseUrl()+"login")// делаем postзапрос
                .then()
                .statusCode(400);


    }
    @Test
    @Name("Авторизация с незаполненными Логином и Паролем")
    void loginEmptyValue (){
        given().spec(requestSpecification)
                .body("{\n"
                        + " \"username\":,\n"
                        + " \"password\":,\n"
                        + "}")
                .when()
                .post(getBaseUrl()+"login")// делаем postзапрос
                .then()
                .statusCode(400);


    }
    @Test
    @Name("Авторизация с невалидным количеством знаков в Логине")
    void loginShotLengthValue (){
        given().spec(requestSpecification)
                .body("{\n"
                        + " \"username\":ап,\n"
                        + " \"password\":555555,\n"
                        + "}")
                .when()
                .post(getBaseUrl()+"login")// делаем postзапрос
                .then()
                .statusCode(400);



    }

}
