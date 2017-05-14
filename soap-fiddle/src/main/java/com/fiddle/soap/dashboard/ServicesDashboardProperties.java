package com.fiddle.soap.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("dashboard")
public class ServicesDashboardProperties {
	
	private List<ServicesDashboardEnvironment> environments = new ArrayList<ServicesDashboardEnvironment>();

	public List<ServicesDashboardEnvironment> getEnvironments() {
		return environments;
	}

	public void setEnvironments(List<ServicesDashboardEnvironment> environments) {
		this.environments = environments;
	}


}
