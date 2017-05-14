package com.fiddle.soap.dashboard;

import java.util.ArrayList;
import java.util.List;

public class ServicesDashboardEnvironment {
	
	
    private String environmentName;
    private List<ServicesDashboardEnvironmentData> components = new ArrayList<ServicesDashboardEnvironmentData>();
   

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}

	
	public List<ServicesDashboardEnvironmentData> getComponents() {
		return components;
	}

	public void setComponents(List<ServicesDashboardEnvironmentData> components) {
		this.components = components;
	}



}
