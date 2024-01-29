package ru.kozarez.soapapp.controllers;

import com.test_soap.GetConvertedXmlRequest;
import com.test_soap.GetConvertedXmlResponse;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.xml.ws.WebEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.kozarez.soapapp.configurations.WebServiceConfig;
import ru.kozarez.soapapp.services.ConvertService;

@Endpoint
@RequiredArgsConstructor
public class SoapController {
    @Autowired
    private final ConvertService convertService;

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "getConvertedXmlRequest")
    @ResponsePayload
    public GetConvertedXmlResponse handleXmlRequest(@RequestPayload GetConvertedXmlRequest request) {
        System.out.println("676");
        GetConvertedXmlResponse response = new GetConvertedXmlResponse();
        try {
            response.setConvertedXmlText(convertService.convertXml(request.getSourceXmlText()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        System.out.println("34343");
        return response;
    }

/*
    private String sourceToString(Source source) {
        if (source instanceof DOMSource) {
            Document document = ((DOMSource) source).getNode().getOwnerDocument();
            // Здесь вы можете использовать библиотеки для преобразования Document в строку XML
            // Пример: XMLTransformer.transformDocumentToString(document)
            // Верните строку XML
        }
        return null; // Обработка других типов Source, если необходимо
    }
*/






    /*@PayloadRoot(namespace = "http://your-namespace-uri", localPart = "your-request-local-part")
    @ResponsePayload
    public YourResponseClass handleSoapRequest(@RequestPayload YourRequestClass request) {
        // Обработка запроса и формирование ответа
        YourResponseClass response = new YourResponseClass();
        // Заполнение данных в response
        return response;
    }*/
}
