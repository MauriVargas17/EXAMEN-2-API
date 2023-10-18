package factoryRequest;

import config.Configuration;
import io.restassured.response.Response;

import java.util.Base64;

import static io.restassured.RestAssured.given;

public class RequestLOGIN {
    public Response getToken(RequestInfo requestInfo) {

        Response response=given().headers(requestInfo.getHeaders())
                .log()
                .all().
                when()
                .get(requestInfo.getUrl());
        response.then().log().all();
        return response;
    }
}
