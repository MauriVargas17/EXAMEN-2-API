package filaa.e1;

import config.Configuration;
import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import factoryRequest.RequestLOGIN;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import todoLyTest.TestBase;
import static org.hamcrest.Matchers.*;

import java.util.Base64;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;

public class E1Test extends TestBase {

    private String getToken(String host){
        requestInfo.setUrl(host);
        String credential= Configuration.user+":"+Configuration.password;
        requestInfo.setHeaders("Authorization","Basic "+ Base64.getEncoder().encodeToString(credential.getBytes()).toString());

        response = new RequestLOGIN().getToken(requestInfo);

        response.then().statusCode(200);

        return response.then().extract().path("TokenString");

    }

    @Test
    public void createUpdateDeleteProject(){
        JSONObject body = new JSONObject();
        String email = "abelardo"+new Date().getTime()+"@abelardo.com";
        String password = "12345";
        body.put("Email", email);
        body.put("FullName","Abelardo");
        body.put("Password",password);

        Configuration.user = email;
        Configuration.password = password;

        this.createUser(create, body);

        body = new JSONObject();
        String projectName = "JAJAJA";
        body.put("Content", projectName);

        this.createProject(Configuration.host + "/api/projects.json", body, "apost");
        this.deleteUser("adelete");
        this.createProjectWithDeletedUser(body, "apost");

//
//
//        this.token = getToken(Configuration.host + "/api/authentication/token.json");
//        requestInfo = new RequestInfo();
//        requestInfo.setHeaders("Token", this.token);
//
//        body = new JSONObject();
//        body.put("Content", "P1");
//
//
//        this.createProject(Configuration.host + "/api/projects.json", body, post);
//        int idProject = response.then().extract().path("Id");
//
//        requestInfo = new RequestInfo();
//        requestInfo.setHeaders("Token", "");
//        body.put("Content", "P2");
//
//        this.createProjectWithoutToken(Configuration.host + "/api/projects.json", body, post);
//
//        this.token = getToken(Configuration.host + "/api/authentication/token.json");
//        requestInfo = new RequestInfo();
//        requestInfo.setHeaders("Token", this.token);
//        this.readAllProjects(get, body.getString("Content"));

    }

    private void createProjectWithDeletedUser(JSONObject body, String apost) {
        requestInfo.setUrl(Configuration.host + "/api/projects.json")
                .setBody(body.toString());
        response = FactoryRequest.make(apost).send(requestInfo);
        response.then().statusCode(200).
                body("ErrorCode", equalTo(105)).
                body("ErrorMessage", equalTo("Account doesn't exist"));
    }

    private void readAllProjects(String get, String pName){
        requestInfo.setUrl(Configuration.host + "/api/projects.json");
        response = FactoryRequest.make(get).send(requestInfo);
        response.then().statusCode(200)
                .body("Content", not(hasItem("P2")));
    }

    private void createUser(String create, JSONObject body){
        requestInfo.setUrl(Configuration.host + "/api/user.json").setBody(body.toString());;
        response = FactoryRequest.make(create).send(requestInfo);
        response.then().statusCode(200).
                body("Email", equalTo(body.get("Email"))).
                body("FullName", equalTo(body.get("FullName")));

    }

    private void deleteUser(String delete) {
        requestInfo.setUrl(Configuration.host + "/api/user/0.json");
        response = FactoryRequest.make(delete).send(requestInfo);
        response.then().statusCode(200);
    }

    private void deleteProject(int idProject, String delete, JSONObject body) {
        requestInfo.setUrl(Configuration.host + "/api/projects/" + idProject + ".json");
        response = FactoryRequest.make(delete).send(requestInfo);
        response.then().statusCode(200).
                body("Content", equalTo(body.get("Content")));
    }

    private void updateProject(String host, JSONObject body, String put) {
        requestInfo.setUrl(host)
                .setBody(body.toString());
        response = FactoryRequest.make(put).send(requestInfo);
        response.then().statusCode(200).
                body("Content", equalTo(body.get("Content")));
    }

    private void readProject(int idProject, String get, JSONObject body) {
        requestInfo.setUrl(Configuration.host + "/api/projects/" + idProject + ".json");
        response = FactoryRequest.make(get).send(requestInfo);
        response.then().statusCode(200).
                body("Content", equalTo(body.get("Content")));
    }

    private void createProject(String host, JSONObject body, String post) {
        requestInfo.setUrl(host)
                .setBody(body.toString());
        response = FactoryRequest.make(post).send(requestInfo);
        response.then().statusCode(200).
                body("Content", equalTo(body.get("Content")));
    }

    private void createProjectWithoutToken(String host, JSONObject body, String post) {
        requestInfo.setUrl(host)
                .setBody(body.toString());
        response = FactoryRequest.make(post).send(requestInfo);
        response.then().statusCode(200).
                body("ErrorCode", equalTo(102)).
                body("ErrorMessage", equalTo("Not Authenticated"));
    }


}
