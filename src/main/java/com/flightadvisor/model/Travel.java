package com.flightadvisor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Travel {

	@Id
	@Autowired
	private City sourceCity;
	@Autowired
	private City destinationCity;
	private List<TravelRoute> travelRoutes;

	public Travel() {
		travelRoutes = new ArrayList<>();
	}
	
	public Travel(City sourceCity, City destinationCity) {
		super();
		this.sourceCity = sourceCity;
		this.destinationCity = destinationCity;
		travelRoutes = new ArrayList<>();
	}

	public City getSourceCity() {
		return sourceCity;
	}

	public void setSourceCity(City sourceCity) {
		this.sourceCity = sourceCity;
	}

	public City getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(City destinationCity) {
		this.destinationCity = destinationCity;
	}

	public List<TravelRoute> getTravelRoutes() {
		return travelRoutes;
	}

	public void setTravelRoutes(List<TravelRoute> travelRoutes) {
		this.travelRoutes = travelRoutes;
	}

	public void addTravelRoute(TravelRoute travelRoute) {
		travelRoutes.add(travelRoute);
	}
	
	public void sortTravelRouts() {
		Collections.sort(travelRoutes, new Comparator<TravelRoute>() {

			@Override
			public int compare(TravelRoute o1, TravelRoute o2) {
				return o1.getTotalPrice() > o2.getTotalPrice() ? 1 : (o1.getTotalPrice() < o2.getTotalPrice()) ? -1 : 0;
			}
		});
	}

	@Override
	public String toString() {
		return "Travel [sourceCity=" + sourceCity + ", destinationCity=" + destinationCity + ", travelRoutes="
				+ travelRoutes + "]";
	}

}
