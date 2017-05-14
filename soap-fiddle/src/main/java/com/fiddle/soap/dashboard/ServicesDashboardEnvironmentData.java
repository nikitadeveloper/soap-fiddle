package com.fiddle.soap.dashboard;

public class ServicesDashboardEnvironmentData {
	
	private String displayName;
	private String wsdlFile;
	private String bindingName;
	private String operationName;
	private String serviceEndpoint;
	private String soapRequest;
	private String responseSuccessKeyword;
	private String responseFailureKeyword;
	private String componentIdentifier;
	
	/**
	 * @return the wsdlFile
	 */
	public String getWsdlFile() {
		return wsdlFile;
	}
	/**
	 * @param wsdlFile the wsdlFile to set
	 */
	public void setWsdlFile(String wsdlFile) {
		this.wsdlFile = wsdlFile;
	}
	/**
	 * @return the bindingName
	 */
	public String getBindingName() {
		return bindingName;
	}
	/**
	 * @param bindingName the bindingName to set
	 */
	public void setBindingName(String bindingName) {
		this.bindingName = bindingName;
	}
	/**
	 * @return the operationName
	 */
	public String getOperationName() {
		return operationName;
	}
	/**
	 * @param operationName the operationName to set
	 */
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	/**
	 * @return the serviceEndpoint
	 */
	public String getServiceEndpoint() {
		return serviceEndpoint;
	}
	/**
	 * @param serviceEndpoint the serviceEndpoint to set
	 */
	public void setServiceEndpoint(String serviceEndpoint) {
		this.serviceEndpoint = serviceEndpoint;
	}
	/**
	 * @return the soapRequest
	 */
	public String getSoapRequest() {
		return soapRequest;
	}
	/**
	 * @param soapRequest the soapRequest to set
	 */
	public void setSoapRequest(String soapRequest) {
		this.soapRequest = soapRequest;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getResponseSuccessKeyword() {
		return responseSuccessKeyword;
	}
	public void setResponseSuccessKeyword(String responseSuccessKeyword) {
		this.responseSuccessKeyword = responseSuccessKeyword;
	}
	public String getResponseFailureKeyword() {
		return responseFailureKeyword;
	}
	public void setResponseFailureKeyword(String responseFailureKeyword) {
		this.responseFailureKeyword = responseFailureKeyword;
	}
	public String getComponentIdentifier() {
		return componentIdentifier;
	}
	public void setComponentIdentifier(String componentIdentifier) {
		this.componentIdentifier = componentIdentifier;
	}

}