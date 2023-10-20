package factoryRequest;

import config.Configuration;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ARequestPUT implements IRequest {
    @Override
    public Response send(RequestInfo requestInfo) {
        Response response=given().auth().preemptive().basic(Configuration.user, Configuration.password)

                    .body(requestInfo.getBody())

                    .log()
                    .all().
                when()
                    .put(requestInfo.getUrl());
        response.then().log().all();
        return response;
    }
}
