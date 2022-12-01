package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AbstractTest {
    static Properties prop = new Properties();// класс проперти
    private static InputStream configFile;
    private static String baseUrl;
    private static String token;
    private static String username;
    private static String password;
    protected static ResponseSpecification responseSpecification;// создали переменную для спецификации ответа
    protected static RequestSpecification requestSpecification;
    protected static RequestSpecification requestSpecificationOwn;
    protected static RequestSpecification requestSpecificationNotOwn;// создали переменную для спецификации запроса

    @BeforeAll// перед всеми тестами
    static void initTest() throws IOException {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        // получаем объект типа InputStream из файла propertiesForHW3 (путь к файлу)
        configFile = new FileInputStream("src/main/resources/project.properties");
        // загружаем значения файла в объект типа Properties
        prop.load(configFile);
        //определяем статические переменные - считываем в них соответствующие значения
        token =  prop.getProperty("token");
        baseUrl= prop.getProperty("base_url");
        username =  prop.getProperty("username");
        password =  prop.getProperty("password");
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(6000L))
                .build();

        //requestSpecification содержит общие значения для всех тестов
        requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .build();

        requestSpecificationOwn = new RequestSpecBuilder()
                .addHeader("X-Auth-Token",token)
                .addQueryParam( "sort", "createdAt")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        requestSpecificationNotOwn = new RequestSpecBuilder()
                .addHeader("X-Auth-Token",token)
                .addQueryParam("owner", "notMe")
                .addQueryParam( "sort", "createdAt")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
// можно объявить их в глобальные переменные restassured( но тогда они будут для вообще всего проекта)

//        RestAssured.responseSpecification = responseSpecification;
//        RestAssured.requestSpecification = requestSpecification;
//        RestAssured.requestSpecification = requestSpecificationOwn;
//        RestAssured.requestSpecification = requestSpecificationNotOwn;

    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getToken() {
        return token;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}
