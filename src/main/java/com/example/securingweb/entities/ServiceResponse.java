package com.example.securingweb.entities;

public class ServiceResponse {
	private int returnCode;
	private String status;
	
	public ServiceResponse(){
		
	}
	
	public ServiceResponse(String status, int returnCode){
		this.status = status;
		this.returnCode = returnCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
}
