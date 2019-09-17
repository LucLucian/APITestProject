package employee.services;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import utils.PropertyLoader;

import java.io.IOException;
import java.net.URISyntaxException;

public class BaseRequestClass {

    @BeforeMethod
    public void setup() throws IOException, URISyntaxException {
        RestAssured.baseURI = PropertyLoader.getProp("protocol") + "//" + PropertyLoader.getProp("host") + PropertyLoader.getProp("domain");
        RestAssured.basePath = PropertyLoader.getProp("basepath");
    }
}
