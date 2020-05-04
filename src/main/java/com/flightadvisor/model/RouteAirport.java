package com.flightadvisor.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class RouteAirport {

	@Id
	private Integer id;
	private String IATACode;
	private String ICAOCode;
	@OneToMany(mappedBy = "sourceAirport")
	private List<Route> sourceRoute;
	@OneToMany(mappedBy = "destinationAirport")
	private List<Route> destinationRoute;

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

	@Override
	public String toString() {
		return "RouteAirport [id=" + id + ", IATACode=" + IATACode + ", ICAOCode=" + ICAOCode + "]";
	}

	public boolean validate() {
		if (id != null 
				&& (this.IATACode != null && (this.IATACode.equals("Null") || this.IATACode.length() == 3))
				&& (this.ICAOCode != null && (this.ICAOCode.equals("Null") || this.ICAOCode.length() == 4))) {
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
		RouteAirport other = (RouteAirport) obj;
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
