package employee.services;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.PropertyLoader;

import static io.restassured.RestAssured.given;


public class EmployeeServiceAPITests extends BaseRequestClass {
    private static String myId;

    public static void RequestsGeneralAssert(Response response, String property, String expectedResult, int reponseCode) {
        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == reponseCode);
        String salaryValue = response.jsonPath().get(property);
        Assert.assertEquals(expectedResult, salaryValue);
    }

    @Test(priority = 1)
    public void CreateEmployee() throws Exception {
        RequestSpecification request = given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", PropertyLoader.getProp("employeename") + Math.ceil(Math.random() * 10000));
        requestParams.put("salary", PropertyLoader.getProp("salary"));
        requestParams.put("age", PropertyLoader.getProp("age"));

        request.header("Content-Type", "application/json");
        request.body(requestParams.toString());
        Response response = request.post("/create");

        ResponseBody body = response.getBody();
        System.out.println("Response Body is: " + body.asString());

        //Asserts the POST request body on salary field and status code
        RequestsGeneralAssert(response, "salary", "123", 200);

        myId = response.jsonPath().get("id");
    }

    @Test(priority = 2)
    public void GetEmployee() {
        RequestSpecification request = given();
        Response response = request.get("/employee/" + myId);
        ResponseBody body = response.getBody();
        System.out.println("Response Body is: " + body.asString());

        //Asserts the POST request body on salary field and status code
        RequestsGeneralAssert(response, "employee_age", "23", 200);
    }

    @Test(priority = 3)
    public void EditEmployee() throws Exception {
        RequestSpecification request = given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", PropertyLoader.getProp("employeename") + Math.ceil(Math.random() * 10000));
        requestParams.put("salary", PropertyLoader.getProp("editsalary"));
        requestParams.put("age", PropertyLoader.getProp("age"));
        request.header("Content-Type", "application/json");

        request.body(requestParams.toString());
        Response response = request.put("/update/" + myId);

        ResponseBody body = response.getBody();
        System.out.println("Response Body is: " + body.asString());

        //Asserts the POST request body on salary field and status code
        RequestsGeneralAssert(response, "salary", "456", 200);
    }

    @Test(priority = 4)
    public void GetAllEmployeess() {
        RequestSpecification request = given();
        Response response = request.get("/employees");
        ResponseBody body = response.getBody();
        System.out.println("Response Body is: " + body.asString());

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 200);
        String contentType = response.getContentType();
        Assert.assertEquals("text/html; charset=UTF-8", contentType);
    }

    @Test(priority = 5)
    public void DeleteEmployee() {
        RequestSpecification request = given();
        Response response = request.delete("/delete/" + myId);
        ResponseBody body = response.getBody();
        System.out.println("Response Body is: " + body.asString());

        //Asserts the POST request body on salary field and status code
        RequestsGeneralAssert(response, "success.text", "successfully! deleted Records", 200);
    }
}

