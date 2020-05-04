package com.flightadvisor.model;

import java.util.ArrayList;

import javax.persistence.Id;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TravelRoute {

	private ArrayList<City> cities;
	@Id
	private String citiesName;
	private Double totalPrice;
	private Double length;
	
	public TravelRoute() {
		cities = new ArrayList<>();
	}

	public String getCitiesName() {
		return citiesName;
	}

	public void generateCitiesName() {
		for (int i = 0; i < cities.size(); i++) {
			if (i == 0) {
				citiesName = cities.get(i).getName();
			} else {
				citiesName += " - " + cities.get(i).getName();
			}
		}
	}

	public void setCitiesName(String citiesName) {
		this.citiesName = citiesName;
	}

	public ArrayList<City> getCities() {
		return cities;
	}

	public void setCities(ArrayList<City> cities) {
		this.cities = cities;
	}

	public void addCity(City city) {
		cities.add(city);
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "TravelRoute [cities=" + cities + ", citiesName=" + citiesName + ", totalPrice=" + totalPrice
				+ ", length=" + length + "]";
	}
	
}
