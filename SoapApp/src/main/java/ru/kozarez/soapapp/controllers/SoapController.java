package ru.kozarez.soapapp.controllers;

import com.test_soap.GetConvertedXmlRequest;
import com.test_soap.GetConvertedXmlResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(SoapController.class);
    @Autowired
    private final ConvertService convertService;

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "getConvertedXmlRequest")
    @ResponsePayload
    public GetConvertedXmlResponse handleXmlRequest(@RequestPayload GetConvertedXmlRequest request) {
        logger.info("Received XML: {}", request.getSourceXmlText());
        GetConvertedXmlResponse response = new GetConvertedXmlResponse();
        try {
            response.setConvertedXmlText(convertService.convertXml(request.getSourceXmlText()));
        } catch (Exception e) {
            logger.error("XML processing exception: {}", e);
            throw new RuntimeException(e);
        }
        logger.info("Sent response back");
        return response;
    }
}
