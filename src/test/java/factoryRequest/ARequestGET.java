package factoryRequest;

import config.Configuration;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ARequestGET implements IRequest {
    @Override
    public Response send(RequestInfo requestInfo) {
        Response response=given().auth().preemptive().basic(Configuration.user, Configuration.password)
                    .log()
                    .all().
                when()
                    .get(requestInfo.getUrl());
        response.then().log().all();
        return response;
    }
}
