package com.fiddle.soap.service;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.reficio.ws.builder.SoapBuilder;
import org.reficio.ws.builder.SoapOperation;
import org.reficio.ws.builder.core.Wsdl;
import org.reficio.ws.client.core.SoapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.fiddle.soap.model.SoapFiddleModel;
import com.fiddle.soap.storage.StorageService;

@Service
public class SoapFiddleService {
    private final StorageService storageService;

    @Autowired
    public SoapFiddleService(StorageService storageService) {
        this.storageService = storageService;
        storageService.deleteAll();
        storageService.init();
    }
	
	
	public List<SoapOperation> getOperations(String bindingName, Wsdl wsdl) {
		List<SoapOperation> operations;
		SoapBuilder builder = wsdl.binding().localPart(bindingName).find();
		operations = builder.getOperations();
		return operations;
	}

	public String getSoapResponse(Wsdl wsdl, String bindingName, String operationName, String endpointUri, String soapRequest) {
		SoapBuilder builder = wsdl.binding().localPart(bindingName).find();
		SoapOperation operation = builder.operation().name(operationName).find();
		SoapClient client = SoapClient.builder().endpointUri(endpointUri).build();
		String response = client.post(operation.getSoapAction(), soapRequest);
		String formattedResponse;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(response));
			Document doc = db.parse(is);
			
			Transformer tf = TransformerFactory.newInstance().newTransformer();
			tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			Writer out = new StringWriter();
			tf.transform(new DOMSource(doc), new StreamResult(out));
			formattedResponse=out.toString();
		} catch (Exception e) {
			formattedResponse=response;
			e.printStackTrace();
		}
		return formattedResponse;
	}

	public String getSoapResponseFromFiles(String wsdlFilePath, String bindingName, String operationName, String endpointUri, String soapRequestPath) throws IOException {
		Path loadedWsdlFilePath = storageService.load(wsdlFilePath);
		Wsdl wsdl = Wsdl.parse(loadedWsdlFilePath.toUri().toURL());
		String soapRequest = new String(Files.readAllBytes(Paths.get(storageService.load(soapRequestPath).toUri())));
		System.out.println(soapRequest);
		return getSoapResponse(wsdl, bindingName, operationName, endpointUri, soapRequest);
	}


	public SoapFiddleModel createSoapFiddleModel(String wsdlFilePath, String bindingName, String operationName, String endpointUri, String soapRequestPath) throws IOException {
		Path loadedWsdlFilePath = storageService.load(wsdlFilePath);
		Wsdl wsdl = Wsdl.parse(loadedWsdlFilePath.toUri().toURL());
		String soapRequest = new String(Files.readAllBytes(Paths.get(storageService.load(soapRequestPath).toUri())));
		SoapFiddleModel model = new SoapFiddleModel(wsdl, bindingName, operationName, endpointUri, soapRequest);
		return model;
	}
}
