package filac.e1;

import config.Configuration;
import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import factoryRequest.RequestLOGIN;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import todoLyTest.TestBase;
import static org.hamcrest.Matchers.*;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

//        body = new JSONObject();
//
//        for (int i = 0; i < 4; i++){
//            String projectName = "p"+i;
//            body.put("Content", projectName);
//            this.createProject(Configuration.host + "/api/projects.json", body, "apost");
//        }
//
//        Response response = readAllProjects("aget", "");
//
//        List<Map<String, Object>> projects = response.jsonPath().getList("");
//
//
//        int[] projectIds = new int[projects.size()];
//
//
//        for (int i = 0; i < projects.size(); i++) {
//            projectIds[i] = Integer.parseInt(projects.get(i).get("Id").toString());
//        }
//
//
////        for (String projectId : projectIds) {
////            System.out.println("Project Id: " + projectId);
////        }
//
//        for (int projectId : projectIds) {
//            deleteProject(projectId, "adelete", body);
//        }
//
//        response = readAllProjects("aget", "");
//
//        projects = response.jsonPath().getList("");
//
//        Assertions.assertEquals(projects.size(), 0, "ERROR no se pudieron borrar todos los proyectos");
//
//
//


        //this.deleteUser("adelete");
        //this.createProjectWithDeletedUser(body, "apost");

//
//
        this.token = getToken(Configuration.host + "/api/authentication/token.json");
        requestInfo = new RequestInfo();
        requestInfo.setHeaders("Token", this.token);
        body = new JSONObject();
        String projectName = "p1";
        body.put("Content", projectName);
        this.createProject(Configuration.host + "/api/projects.json", body, post);
        int idProject = response.then().extract().path("Id");

        body = new JSONObject();
        body.put("Content", "Item1");
        body.put("ProjectId", idProject);


        this.createItem(Configuration.host + "/api/items.json", body, post);
        int idItem = response.then().extract().path("Id");

        requestInfo = new RequestInfo();
        requestInfo.setHeaders("Token", "");
        body.put("Content", "Item2");
        body.put("ProjectId", idProject);

        this.createItemWithoutToken(Configuration.host + "/api/items.json", body, post);

        this.token = getToken(Configuration.host + "/api/authentication/token.json");
        requestInfo = new RequestInfo();
        requestInfo.setHeaders("Token", this.token);
        this.readAllItems(get, body.getString("Content"));

    }

    private void createProjectWithDeletedUser(JSONObject body, String apost) {
        requestInfo.setUrl(Configuration.host + "/api/projects.json")
                .setBody(body.toString());
        response = FactoryRequest.make(apost).send(requestInfo);
        response.then().statusCode(200).
                body("ErrorCode", equalTo(105)).
                body("ErrorMessage", equalTo("Account doesn't exist"));
    }


    private Response readAllProjects(String get, String pName){
        requestInfo.setUrl(Configuration.host + "/api/projects.json");
        response = FactoryRequest.make(get).send(requestInfo);
        response.then().statusCode(200)
                .body("ItemsCount", not(hasItem("Item2")));
        return response;
    }

    private Response readAllItems(String get, String pName){
        requestInfo.setUrl(Configuration.host + "/api/items.json");
        response = FactoryRequest.make(get).send(requestInfo);
        response.then().statusCode(200)
                .body("Content", not(hasItem("Item2")));
        return response;
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
        response.then().statusCode(200);
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

    private void createItem(String host, JSONObject body, String post) {
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

    private void createItemWithoutToken(String host, JSONObject body, String post) {
        requestInfo.setUrl(host)
                .setBody(body.toString());
        response = FactoryRequest.make(post).send(requestInfo);
        response.then().statusCode(200).
                body("ErrorCode", equalTo(102)).
                body("ErrorMessage", equalTo("Not Authenticated"));
    }


}