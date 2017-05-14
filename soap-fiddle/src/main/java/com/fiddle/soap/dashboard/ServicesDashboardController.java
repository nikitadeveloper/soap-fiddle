package com.fiddle.soap.dashboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fiddle.soap.service.SoapFiddleService;

@Controller
@RequestMapping("/servicesDashboard")
public class ServicesDashboardController {

	private final SoapFiddleService soapFiddleService;
	private List<ServicesDashboardEnvironment> dashboardEnvironments;
	private Map<String,ServicesDashboardEnvironmentData> dashboardComponentMap;

	@Autowired
	public ServicesDashboardController(SoapFiddleService soapFiddleService,
			ServicesDashboardProperties dashboardProperties) {
		this.soapFiddleService = soapFiddleService;
		if(dashboardProperties!=null && dashboardProperties.getEnvironments()!=null){
			this.dashboardEnvironments = dashboardProperties.getEnvironments();
		} else {
			this.dashboardEnvironments = new ArrayList<ServicesDashboardEnvironment>();
		}
		
		this.dashboardComponentMap = new HashMap<String, ServicesDashboardEnvironmentData>();
		int envIndex=0;
		for(ServicesDashboardEnvironment dashboardEnvironment:this.dashboardEnvironments){
			if(dashboardEnvironment!=null){
				String keyEnvPrefix = "env"+envIndex++;
				int componentIndex = 0;
				for(ServicesDashboardEnvironmentData component:dashboardEnvironment.getComponents()){
					if(component!=null){
						String componentIdentifier = keyEnvPrefix+"component"+componentIndex++;
						component.setComponentIdentifier(componentIdentifier);
						this.dashboardComponentMap.put(componentIdentifier, component);
					}
				}
			}
		}
	}

	@GetMapping("")
	public String loadServicesDashboard(Model model) {
		model.addAttribute("dashboardEnvironments", this.dashboardEnvironments);

		return "servicesDashboard";
	}
	
	@GetMapping("/getServiceStatus")
    @ResponseBody 
    public Map<String,String> getServiceStatus(Model model, @RequestParam("componentIdentifier") String componentIdentifier) throws IOException {
		ServicesDashboardEnvironmentData dashboardProperties = this.dashboardComponentMap.get(componentIdentifier);
		String soapResponse = soapFiddleService.getSoapResponseFromFiles(dashboardProperties.getWsdlFile(),
				dashboardProperties.getBindingName(), dashboardProperties.getOperationName(),
				dashboardProperties.getServiceEndpoint(), dashboardProperties.getSoapRequest());
		Map<String, String> jsonResponseMap = new HashMap<>();
		jsonResponseMap.put("soapResponse", soapResponse);
		boolean successResponse=true;
		if(!StringUtils.hasText(soapResponse)){
			successResponse=false;
		} else if(StringUtils.hasText(dashboardProperties.getResponseFailureKeyword()) && soapResponse.contains(dashboardProperties.getResponseFailureKeyword())){
			successResponse=false;
		} else if(StringUtils.hasText(dashboardProperties.getResponseSuccessKeyword()) && !soapResponse.contains(dashboardProperties.getResponseSuccessKeyword())){
			successResponse=false;
		} /*else if(!StringUtils.hasText(dashboardProperties.getResponseSuccessKeyword()) && !StringUtils.hasText(dashboardProperties.getResponseFailureKeyword())){
			successResponse=true;
		} */
		jsonResponseMap.put("successResponse", String.valueOf(successResponse));
		return jsonResponseMap;
	}
	
	@GetMapping("/editInFiddle")
	public String editInFiddle(Model model, @RequestParam("componentIdentifier") String componentIdentifier, RedirectAttributes redirectAttributes) throws IOException {
		ServicesDashboardEnvironmentData dashboardProperties = this.dashboardComponentMap.get(componentIdentifier);
		redirectAttributes.addFlashAttribute("soapFiddleModel", soapFiddleService.createSoapFiddleModel(dashboardProperties.getWsdlFile(),
				dashboardProperties.getBindingName(), dashboardProperties.getOperationName(),
				dashboardProperties.getServiceEndpoint(), dashboardProperties.getSoapRequest()));
		return "redirect:/soapFiddle";
	}

}
