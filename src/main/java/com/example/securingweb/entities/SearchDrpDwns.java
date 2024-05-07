package com.example.securingweb.entities;

import java.util.List;

public class SearchDrpDwns {
	
	private List<Ship> ships;
	private List<TransmissionType> transmissionTypes;
	
	public List<Ship> getShips() {
		return ships;
	}
	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}
	public List<TransmissionType> getTransmissionTypes() {
		return transmissionTypes;
	}
	public void setTransmissionTypes(List<TransmissionType> transmissionTypes) {
		this.transmissionTypes = transmissionTypes;
	}

}
