package com.flightadvisor.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Airline {

	@Id
	private Integer id;
	private String IATACode;
	private String ICAOCode;
	@OneToMany(mappedBy = "airline")
	private List<Route> route;

	public String getIATACode() {
		return IATACode;
	}

	public void setIATACode(String iATACode) {
		IATACode = iATACode;
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

	public void setId(String id) {
		try {
			this.id = Integer.parseInt(id);
		} catch (NumberFormatException nfe) {
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIATAOrICAOCode(String code) {
		if (code != null && code.length() == 2) {
			setIATACode(code);
		} else if (code != null && code.length() == 3) {
			setICAOCode(code);
		}
	}

	@Override
	public String toString() {
		return "Airline [id=" + id + ", IATACode=" + IATACode + ", ICAOCode=" + ICAOCode + "]";
	}

	public boolean validate() {
		if (id != null 
				&& ((this.IATACode != null && this.IATACode.length() == 2)
						|| (this.ICAOCode != null && this.ICAOCode.length() == 3))) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Airline other = (Airline) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
}
