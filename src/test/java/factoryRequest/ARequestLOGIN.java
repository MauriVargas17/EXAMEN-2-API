package factoryRequest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ARequestLOGIN {
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
