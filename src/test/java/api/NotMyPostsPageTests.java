package api;

import jdk.jfr.Name;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class NotMyPostsPageTests extends AbstractTest {
    @Test
    @Name("Запрос страницы чужих постов в порядке возрастания(ASC) с валидными параметрами авторизованным пользователем")
    void getNotOwnPostsASC() {
        given().spec(requestSpecificationNotOwn)
                .queryParams("order","ASC")
                .queryParams("page","1")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .spec(responseSpecification);
    }
    @Test
    @Name("Запрос страницы чужих постов в порядке возрастания(ASC) с валидными параметрами НЕавторизованным пользователем")
    void getNotOwnPostsWithoutToken() {
        given()
                .queryParams("owner","notMe")
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
    @Name("Запрос страницы чужих постов в порядке убывания (DESC) с валидными параметрами авторизованным пользователем")
    void getOwnPostsDESC() {
        given().spec(requestSpecificationNotOwn)
                .queryParams("order","DESC")
                .queryParams("page","4")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .spec(responseSpecification);
    }
    @Test
    @Name("Запрос страницы чужих постов в порядке убывания (DESC) с отрицательным значением страницы авторизованным пользователем")
    void getOwnPostsNegativePageDESC() {
        given().spec(requestSpecificationNotOwn)
                .queryParams("order","DESC")
                .queryParams("page","-1")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .statusCode(400);
    }
    @Test
    @Name("Запрос страницы чужих постов в рандомном порядке (ALL) с валидными параметрами авторизованным пользователем")
    void getOwnPostsALL() {
        given().spec(requestSpecificationNotOwn)
                .queryParams("order","ALL")
                .queryParams("page","2")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .spec(responseSpecification);
    }
}
