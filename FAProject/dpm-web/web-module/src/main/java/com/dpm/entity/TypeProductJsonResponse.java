package com.dpm.entity;

import java.util.Map;

public class TypeProductJsonResponse {

	private TypeProduct typeProduct;
	private boolean validated;
	private Map<String, String> errorMessages;

	public TypeProduct getTypeProduct() {
		return typeProduct;
	}

	public void setTypeProduct(TypeProduct typeProduct) {
		this.typeProduct = typeProduct;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public Map<String, String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(Map<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

}
