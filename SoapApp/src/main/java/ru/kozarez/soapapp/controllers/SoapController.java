package ru.kozarez.soapapp.controllers;

import com.test_soap.GetConvertedXmlRequest;
import com.test_soap.GetConvertedXmlResponse;
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
        GetConvertedXmlResponse response = new GetConvertedXmlResponse();
        try {
            response.setConvertedXmlText(convertService.convertXml(request.getSourceXmlText()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
