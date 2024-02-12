package DevTools;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v116.network.Network;
import org.openqa.selenium.devtools.v116.network.model.Request;
import org.openqa.selenium.devtools.v116.network.model.RequestId;
import org.openqa.selenium.devtools.v116.network.model.Response;
import org.openqa.selenium.devtools.v116.log.Log;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DevToolsTests {
    WebDriver driver;
    DevTools devTools;
    Map<String, Request> requests;
    Map<String, Response> responses;
    Map<String, String> respBodies;

    @BeforeEach
    public void createStorage(){
        driver = new EdgeDriver();
        requests = new HashMap<>();
        responses = new HashMap<>();
        respBodies = new HashMap<>();

        devTools = ((HasDevTools)driver).getDevTools();
        devTools.createSession();
        devTools.send(Log.enable());

        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.addListener(Network.requestWillBeSent(), requestWillBeSent ->
                requests.put(requestWillBeSent.getRequestId().toString(), requestWillBeSent.getRequest()));

        devTools.addListener(Network.responseReceived(), responseReceived -> {
            String resId = responseReceived.getRequestId().toString();
            responses.put(resId, responseReceived.getResponse());

            String body = devTools.send(Network.getResponseBody(new RequestId(resId))).getBody();
            respBodies.put(resId, body);
        });

    }



    @AfterEach
    public void tearDown(){
        if(driver != null){
            devTools.disconnectSession();
            driver.quit();
        }

        for (String reqId : requests.keySet()) {
            Request req = requests.get(reqId);

            if(req.getUrl().startsWith("http://todo")){
                Response res = responses.get(reqId);
                String body = respBodies.get(reqId);
                System.out.println(req.getMethod() + " " + req.getUrl() + " " + req.getPostData().orElse("No Body"));
                System.out.println(res.getStatus());
                System.out.println(body);
                System.out.println("--------------------------------------");
            }
        }

    }


    @Test
    public void shouldPostAndGetRequests(){
        driver.get("http://sky-todo-list.herokuapp.com");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        var taskList = driver.findElements(By.cssSelector("table tr"));
    }
}
