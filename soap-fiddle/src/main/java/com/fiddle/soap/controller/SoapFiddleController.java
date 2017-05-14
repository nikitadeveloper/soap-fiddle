package com.fiddle.soap.controller;

import java.util.List;

import org.reficio.ws.builder.SoapBuilder;
import org.reficio.ws.builder.SoapOperation;
import org.reficio.ws.builder.core.Wsdl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fiddle.soap.model.SoapFiddleModel;
import com.fiddle.soap.model.SoapFiddleUIBean;
import com.fiddle.soap.service.SoapFiddleService;


@Controller
@SessionAttributes("soapFiddleModel")
@RequestMapping("/soapFiddle")
public class SoapFiddleController {
	
    private final SoapFiddleService soapFiddleService;

    @Autowired
    public SoapFiddleController(SoapFiddleService soapFiddleService) {
        this.soapFiddleService = soapFiddleService;
    }
	
	@GetMapping("/getOperations")
    @ResponseBody
    public List<SoapOperation> getOperationsFromBinding(Model model, @RequestParam("bindingName") String bindingName) {
    	System.out.println("got hit"+model.containsAttribute("soapFiddleModel")+bindingName);

    	List<SoapOperation> operations = null;
		if (model.containsAttribute("soapFiddleModel")) {
			SoapFiddleModel soapFiddleModel= (SoapFiddleModel) model.asMap().get("soapFiddleModel");
			Wsdl wsdl = soapFiddleModel.getParsedWsdl();
			operations = soapFiddleService.getOperations(bindingName, wsdl);
			System.out.println(operations);
		}

    	return operations;
    }

	@PostMapping("/postDataToUrl")
    @ResponseBody 
    public SoapFiddleUIBean postDataToUrl(Model model, @RequestBody SoapFiddleUIBean soapFiddleUIBean) {
		if (model.containsAttribute("soapFiddleModel")) {
			SoapFiddleModel soapFiddleModel= (SoapFiddleModel) model.asMap().get("soapFiddleModel");
			Wsdl wsdl = soapFiddleModel.getParsedWsdl();
			String bindingName = soapFiddleUIBean.getBindingName();
			String operationName = soapFiddleUIBean.getOperationName();
			String urlForPost = soapFiddleUIBean.getUrlForPost();
			String soapRequest = soapFiddleUIBean.getSoapRequest();
			String response = soapFiddleService.getSoapResponse(wsdl, bindingName, operationName, urlForPost, soapRequest);
			soapFiddleUIBean.setSoapResponse(response);		
		}

    	return soapFiddleUIBean;
    }

	@PostMapping("/generateSampleRequest")
    @ResponseBody 
    public SoapFiddleUIBean generateSampleRequest(Model model, @RequestBody SoapFiddleUIBean soapFiddleUIBean) {
		if (model.containsAttribute("soapFiddleModel")) {
			SoapFiddleModel soapFiddleModel= (SoapFiddleModel) model.asMap().get("soapFiddleModel");
			Wsdl wsdl = soapFiddleModel.getParsedWsdl();
			SoapBuilder builder = wsdl.binding().localPart(soapFiddleUIBean.getBindingName()).find();
			SoapOperation operation = builder.operation().name(soapFiddleUIBean.getOperationName()).find();
			soapFiddleUIBean.setSoapRequest(builder.buildInputMessage(operation));		
		}

    	return soapFiddleUIBean;
    }
	
	
	@GetMapping("")
	public String displaySoapFiddle(String url, String request1, Model model) {
		/*System.out.println(model.containsAttribute("soapFiddleModel"));
		if (model.containsAttribute("soapFiddleModel")) {
			SoapFiddleModel soapFiddleModel= (SoapFiddleModel) model.asMap().get("soapFiddleModel");
			Wsdl wsdl = soapFiddleModel.getParsedWsdl();
		}*/
		return "soapFiddle";
	}

}
