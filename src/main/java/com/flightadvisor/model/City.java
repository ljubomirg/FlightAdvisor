package com.flightadvisor.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

@Entity
@Component
public class City {

	@Id
	private String name;
	private String country;
	private String cityDescription;
	@OneToMany(mappedBy = "city", cascade=CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<Comment> comment;
	@OneToMany(mappedBy = "city")
	private List<Airport> airport;

	public City() {
	}

	public List<Airport> getAirport() {
		return airport;
	}

	public void setAirport(List<Airport> airport) {
		this.airport = airport;
	}

	public City(String name, String country, String cityDescription) {
		super();
		this.name = name;
		this.country = country;
		this.cityDescription = cityDescription;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCityDescription() {
		return cityDescription;
	}

	public void setCityDescription(String cityDescription) {
		this.cityDescription = cityDescription;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", country=" + country + ", cityDescription=" + cityDescription + ", comment="
				+ comment + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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

		City other = (City) obj;

		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public boolean validate() {
		try {
			if (!getName().isEmpty() && !getCountry().isEmpty() && !getCityDescription().isEmpty()) {
				return true;
			}
		} catch (NullPointerException e) {
		}
		return false;
	}

}
