package api;

import jdk.jfr.Name;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class MyPostsPageTests extends AbstractTest {
    @Test
    @Name("Запрос страницы своих постов в порядке возрастания(ASC) с валидными параметрами авторизованным пользователем")
    void getOwnPostsASC() {
        given().spec(requestSpecificationOwn)
                .queryParams("order","ASC")
                .queryParams("page","1")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .spec(responseSpecification);
    }
    @Test
    @Name("Запрос страницы своих постов в порядке возрастания(ASC) с валидными параметрами НЕавторизованным пользователем")
    void getOwnPostsWithoutToken() {
        given()
                .queryParams("sort","createdAt")
                .queryParams("order","ASC")
                .queryParams("page","1")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .assertThat()
                .statusCode(401);
    }
    @Test
    @Name("Запрос страницы своих постов в порядке убывания (DESC) с валидными параметрами авторизованным пользователем")
    void getOwnPostsDESC() {
        given().spec(requestSpecificationOwn)
                .queryParams("order","DESC")
                .queryParams("page","1")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .spec(responseSpecification);
    }
    @Test
    @Name("Запрос страницы своих постов в порядке убывания (DESC) с отрицательным значением страницы авторизованным пользователем")
    void getOwnPostsNegativePageDESC() {
        given().spec(requestSpecificationOwn)
                .queryParams("order","DESC")
                .queryParams("page","-1")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .statusCode(400);
    }
    @Test
    @Name("Запрос страницы своих постов в порядке убывания (ASC) с отрицательным значением страницы авторизованным пользователем")
    void getOwnPostsNegativePageASC() {
        given().spec(requestSpecificationOwn)
                .queryParams("order","ASC")
                .queryParams("page","-7")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .statusCode(400);
    }
}
