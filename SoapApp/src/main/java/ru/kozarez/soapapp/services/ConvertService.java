package ru.kozarez.soapapp.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.kozarez.soapapp.controllers.SoapController;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

@Service
public class ConvertService {
    private static final Logger logger = LogManager.getLogger(ConvertService.class);
    private final Transformer transformer;

    public ConvertService(){
        TransformerFactory factory = TransformerFactory.newInstance();

        InputStream is = getClass().getClassLoader().getResourceAsStream("xslRules.xsl");
        Source xslt = new StreamSource(is);

        try {
            transformer = factory.newTransformer(xslt);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        } catch (TransformerConfigurationException e) {
            logger.error("Creating transformer error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String convertXml(String xml) {
        Source sourceXml = new StreamSource(new StringReader(xml));
        Writer resultWriter = new StringWriter();

        try {
            transformer.transform(sourceXml, new StreamResult(resultWriter));
            logger.info("Successfully transform XML");
        } catch (TransformerException e){
            logger.error("XML converting exception: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return resultWriter.toString();
    }
}
