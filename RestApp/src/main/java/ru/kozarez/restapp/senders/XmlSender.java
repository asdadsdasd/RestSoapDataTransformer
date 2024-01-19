package ru.kozarez.restapp.senders;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@PropertySource(value = "classpath:application.properties")
public class XmlSender{
    @org.springframework.beans.factory.annotation.Value("${soap.endpoint.url}")
    private String soapEndpointUrl;
    public void sendXml(String xml) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(this.soapEndpointUrl);
        System.out.println(httpPost);

        httpPost.setHeader("Content-Type", "text/xml");
        httpPost.setHeader("Accept", "text/xml");

        StringEntity entity = new StringEntity(xml);
        httpPost.setEntity(entity);

        try {
            HttpResponse response = httpClient.execute(httpPost);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            httpClient.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
