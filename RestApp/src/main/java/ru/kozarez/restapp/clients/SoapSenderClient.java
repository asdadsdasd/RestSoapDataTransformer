package ru.kozarez.restapp.clients;

import com.test_soap.GetConvertedXmlRequest;
import com.test_soap.GetConvertedXmlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Component
public class SoapSenderClient extends WebServiceGatewaySupport {
    @Autowired
    public SoapSenderClient(Jaxb2Marshaller marshaller) {
        this.setMarshaller(marshaller);
        this.setUnmarshaller(marshaller);
        this.setDefaultUri("http://localhost:8081/ws/convert.wsdl");
    }

    public GetConvertedXmlResponse getConvertedXml(String xmlString) {
        GetConvertedXmlRequest request = new GetConvertedXmlRequest();
        request.setSourceXmlText(xmlString);
        System.out.println(xmlString);

        GetConvertedXmlResponse response = (GetConvertedXmlResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8081/ws", request,
                        new SoapActionCallback(
                                "http://www.test-soap.com/getConvertedXmlRequest"));

        return response;
    }
}
