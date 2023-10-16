package factoryRequest;

import config.Configuration;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestPUT implements IRequest {
    @Override
    public Response send(RequestInfo requestInfo, String token) {
        Response response=given()
                    .auth()
                    .preemptive()
                    .oauth2(token)
                    .body(requestInfo.getBody())
                    .header("Token", token)
                    .log()
                    .all().
                when()
                    .put(requestInfo.getUrl());
        response.then().log().all();
        return response;
    }
}
