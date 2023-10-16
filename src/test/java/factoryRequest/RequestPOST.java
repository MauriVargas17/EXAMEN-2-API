package factoryRequest;

import config.Configuration;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestPOST implements IRequest {
    @Override
    public Response send(RequestInfo requestInfo, String token) {
        Response response=given()
                    .auth()
                    .preemptive()
                    .oauth2(token)
                    .header("Token", token)
                    .body(requestInfo.getBody())
                    .log()
                    .all().
                when()
                    .post(requestInfo.getUrl());
        response.then().log().all();
        return response;
    }
}
