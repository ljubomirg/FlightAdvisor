package com.flightadvisor.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class BasicAirport {

	@Id
	private Integer id;
	private String IATACode;
	private String ICAOCode;

	public String getIATACode() {
		return IATACode;
	}

	public void setIATACode(String IATACode) {
		this.IATACode = IATACode;
	}

	public String getICAOCode() {
		return ICAOCode;
	}

	public void setICAOCode(String iCAOCode) {
		ICAOCode = iCAOCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setId(String id) {
		try {
			this.id = Integer.parseInt(id);
		} catch (NumberFormatException nfe) {
		}
	}
	
	public void setIATAOrICAOCode(String code) {
		if (code != null && code.length() == 3) {
			setIATACode(code);
		} else if (code != null && code.length() == 4) {
			setICAOCode(code);
		}
	}

	public boolean validate() {
		if (id != null 
				&& (this.IATACode != null && (this.IATACode.equals("Null") || this.IATACode.length() == 3))
				&& (this.ICAOCode != null && (this.ICAOCode.equals("Null") || this.ICAOCode.length() == 4))) {
			return true;
		}
		return false;
	}

}
