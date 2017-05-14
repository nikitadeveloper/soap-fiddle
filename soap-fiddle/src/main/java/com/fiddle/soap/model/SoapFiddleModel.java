package com.fiddle.soap.model;

import java.io.Serializable;

import org.reficio.ws.builder.core.Wsdl;

public class SoapFiddleModel implements Serializable{
	
	public SoapFiddleModel(Wsdl parsedWsdl, String bindingName, String operationName, String urlForPost,
			String soapRequest) {
		super();
		this.parsedWsdl = parsedWsdl;
		this.bindingName = bindingName;
		this.operationName = operationName;
		this.urlForPost = urlForPost;
		this.soapRequest = soapRequest;
	}

	private static final long serialVersionUID = -3003595042251044845L;

	public SoapFiddleModel(Wsdl parsedWsdl) {
		super();
		this.setParsedWsdl(parsedWsdl);
	}

	private Wsdl parsedWsdl;
	private String bindingName;
	private String operationName;
	private String urlForPost;
	private String soapRequest;
	
	public Wsdl getParsedWsdl() {
		return parsedWsdl;
	}

	public void setParsedWsdl(Wsdl parsedWsdl) {
		this.parsedWsdl = parsedWsdl;
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


}
