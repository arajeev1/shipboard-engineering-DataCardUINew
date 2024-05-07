package com.example.securingweb;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("app")
public class AppProperties {

    private String ipAddress;
    private String serviceLocation;
    

    
        

	public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getIpAddress() {
        return this.ipAddress;
    }
   
    public String getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(String serviceLocation) {
		this.serviceLocation = serviceLocation;
	}


    
    @Override
    public String toString() {
        return "AppProperties{" +
                "ipAddress='" + ipAddress + '\'' +
                "serviceLocation='" + serviceLocation + '\'' +       
                '}';
    }
}
