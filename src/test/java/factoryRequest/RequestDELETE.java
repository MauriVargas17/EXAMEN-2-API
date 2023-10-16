package factoryRequest;

import config.Configuration;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestDELETE implements IRequest {
    @Override
    public Response send(RequestInfo requestInfo, String token) {
        Response response=given()
                    .auth()
                    .preemptive()
                    .oauth2(token)
                    .header("Token", token)
                    .log()
                    .all().
                when()
                    .delete(requestInfo.getUrl());
        response.then().log().all();
        return response;
    }
}
