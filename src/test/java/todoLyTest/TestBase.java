package todoLyTest;

import factoryRequest.RequestInfo;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {
    public String post ="post";
    public String put = "put";
    public String get ="get";
    public String delete ="delete";
    public String create = "create";

    public RequestInfo requestInfo;
    public Response response;
    public String token;
    @BeforeEach
    public void before(){
        requestInfo = new RequestInfo();
    }

    @AfterEach
    public void after(){

    }

}
