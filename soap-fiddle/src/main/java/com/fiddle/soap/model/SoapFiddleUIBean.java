package com.fiddle.soap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SoapFiddleUIBean {

	private String bindingName;
	private String operationName;
	private String urlForPost;
	private String soapRequest;
	private String soapResponse;
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
	 * @return the urlForPost
	 */
	public String getUrlForPost() {
		return urlForPost;
	}
	/**
	 * @param urlForPost the urlForPost to set
	 */
	public void setUrlForPost(String urlForPost) {
		this.urlForPost = urlForPost;
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
	/**
	 * @return the soapResponse
	 */
	public String getSoapResponse() {
		return soapResponse;
	}
	/**
	 * @param soapResponse the soapResponse to set
	 */
	public void setSoapResponse(String soapResponse) {
		this.soapResponse = soapResponse;
	}
}
