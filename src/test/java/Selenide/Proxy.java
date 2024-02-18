package Selenide;

import com.browserup.bup.BrowserUpProxy;
import com.browserup.bup.proxy.CaptureType;
import com.browserup.harreader.model.Har;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getSelenideProxy;
import static com.codeborne.selenide.proxy.RequestMatcher.HttpMethod.GET;
import static com.codeborne.selenide.proxy.RequestMatchers.urlContains;

public class Proxy {

    @BeforeAll
    public static void setUp(){
        Configuration.proxyEnabled = true;
    }

    @Test
    public void harTest() throws IOException {
        open();
        BrowserUpProxy proxy = getSelenideProxy().getProxy();
        proxy.setHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        proxy.newHar();

        open("https://sky-todo-list.herokuapp.com/");
        $$("tr").shouldHave(CollectionCondition.sizeGreaterThan(1));

        Har har = proxy.endHar();
        har.writeTo(Files.createFile(Path.of("./my.har")).toFile());
    }

    @Test
    public void responseMocker(){
        open();
        getSelenideProxy()
                .responseMocker()
                .mockText(
                        "myMocker",
                        urlContains(GET,"suggest"),
                        () -> getResponse()
                );

        open("https://yandex.ru/search/?text=sad&lr=43");
        open("https://ya.ru/");
        $("#text").val("qwe");
        screenshot("yandex");

    }


    private String  getResponse(){
        return "[\n" +
                "    \"stream\",\n" +
                "    [\n" +
                "        [\n" +
                "            \"\",\n" +
                "            \"streamlabs\",\n" +
                "            {\n" +
                "                \"sg_weight\": 0,\n" +
                "                \"hl\": [\n" +
                "                    [\n" +
                "                        0,\n" +
                "                        6\n" +
                "                    ]\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        [\n" +
                "            \"\",\n" +
                "            \"stream api в java\",\n" +
                "            {\n" +
                "                \"sg_weight\": 0,\n" +
                "                \"hl\": [\n" +
                "                    [\n" +
                "                        0,\n" +
                "                        6\n" +
                "                    ]\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        [\n" +
                "            \"\",\n" +
                "            \"stream deck купить\",\n" +
                "            {\n" +
                "                \"sg_weight\": 0,\n" +
                "                \"hl\": [\n" +
                "                    [\n" +
                "                        0,\n" +
                "                        6\n" +
                "                    ]\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        [\n" +
                "            \"\",\n" +
                "            \"stream\",\n" +
                "            {\n" +
                "                \"sg_weight\": 0,\n" +
                "                \"hl\": [\n" +
                "                    [\n" +
                "                        0,\n" +
                "                        6\n" +
                "                    ]\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        [\n" +
                "            \"\",\n" +
                "            \"streamers battle\",\n" +
                "            {\n" +
                "                \"sg_weight\": 0,\n" +
                "                \"hl\": [\n" +
                "                    [\n" +
                "                        0,\n" +
                "                        6\n" +
                "                    ]\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        [\n" +
                "            \"\",\n" +
                "            \"streamelements\",\n" +
                "            {\n" +
                "                \"sg_weight\": 0,\n" +
                "                \"hl\": [\n" +
                "                    [\n" +
                "                        0,\n" +
                "                        6\n" +
                "                    ]\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        [\n" +
                "            \"\",\n" +
                "            \"streaming assistant pico 4\",\n" +
                "            {\n" +
                "                \"sg_weight\": 0,\n" +
                "                \"hl\": [\n" +
                "                    [\n" +
                "                        0,\n" +
                "                        6\n" +
                "                    ]\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    ],\n" +
                "    {\n" +
                "        \"r\": 11119,\n" +
                "        \"pers_options\": 1,\n" +
                "        \"log\": \"sgtype:BBBBBBB\",\n" +
                "        \"continue\": \"labs\"\n" +
                "    }\n" +
                "]";
    }
}
