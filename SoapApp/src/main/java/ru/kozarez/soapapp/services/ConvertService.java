package ru.kozarez.soapapp.services;

import org.springframework.stereotype.Service;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

@Service
public class ConvertService {
    public String convertXml(String xml) throws Exception {

        TransformerFactory factory = TransformerFactory.newInstance();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("xslRules.xsl")) {
            Source xslt = new StreamSource(is);

            Transformer transformer = factory.newTransformer(xslt);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            Source sourceXml = new StreamSource(new StringReader(xml));
            Writer resultWriter = new StringWriter();
            transformer.transform(sourceXml, new StreamResult(resultWriter));
            return resultWriter.toString();
        } catch (TransformerException | IOException e) {
            throw new Exception("Exception in ConvertService");
        }
    }
}
