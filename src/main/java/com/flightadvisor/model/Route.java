package com.flightadvisor.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; 
	@ManyToOne
	private Airline airline;
	@ManyToOne
	private RouteAirport sourceAirport;
	@ManyToOne
	private RouteAirport destinationAirport;
	private String codeshare;
	private Integer stops;
	private String equipment;
	private Double price;
	
	private static final String ROUTE_DELIMITER = ",";
	private static final String EQUIPMENT_REGEX = "[A-Z0-9]{3}";
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public RouteAirport getSourceAirport() {
		return sourceAirport;
	}

	public void setSourceAirport(RouteAirport sourceAirport) {
		this.sourceAirport = sourceAirport;
	}

	public RouteAirport getDestinationAirport() {
		return destinationAirport;
	}

	public void setDestinationAirport(RouteAirport destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	public String getCodeshare() {
		return codeshare;
	}

	public void setCodeshare(String codeshare) {
		this.codeshare = codeshare;
	}

	public Integer getStops() {
		return stops;
	}
	
	public void setStops(String stops) {
		try {
			this.stops = Integer.parseInt(stops);
		} catch (NumberFormatException nfe) {
		}
	}

	public void setStops(Integer stops) {
		this.stops = stops;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		if(equipment != null) {
			String[] equipmentArrays = equipment.split(" ");
			boolean mactches = true;
			for (int i = 0; i < equipmentArrays.length; i++) {
				if (!Pattern.matches(EQUIPMENT_REGEX, equipmentArrays[i])) {
					mactches = false;
				}
			}
			if (mactches) {
				this.equipment = equipment;
			}
		}
	}

	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}

	public void setPrice(String price) {
		try {
	        this.price = Double.parseDouble(price);
	    } catch (NumberFormatException nfe) {
	    }
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
		Route other = (Route) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Route [id=" + id + ", airline=" + airline + ", sourceAirport=" + sourceAirport + ", destinationAirport="
				+ destinationAirport + ", codeshare=" + codeshare + ", stops=" + stops + ", equipment=" + equipment
				+ ", price=" + price + "]";
	}

	private static boolean validateAirport(RouteAirport airport) {
		if (airport != null 
				&& airport.getId() != null 
				&& ((airport.getIATACode() != null && airport.getIATACode().length() == 3)
						|| (airport.getICAOCode() != null && airport.getICAOCode().length() == 4))
				) {
			return true;
		}
		return false;
	}
	
	public boolean validate() {
		try {
			if (getAirline().validate()
					&& Route.validateAirport(sourceAirport)
					&& Route.validateAirport(destinationAirport)
					&& (codeshare != null && (codeshare.equals("Y") || codeshare.equals("")))
					&& stops !=  null
					&& equipment !=  null
					&& price != null) {
				return true;
			}
		} catch (NullPointerException e) {
		}
		return false;
	}

	public static Route fromString(String airportString) {
		Route route = null;
		String[] info = airportString.split(ROUTE_DELIMITER);
		if (info.length == 10) {
			route = new Route();
			Airline airline = new Airline();
			airline.setIATAOrICAOCode(info[0]);
			airline.setId(info[1]);
			route.setAirline(airline);
			RouteAirport sourceAirport = new RouteAirport();
			sourceAirport.setIATAOrICAOCode(info[2]);
			sourceAirport.setId(info[3]);
			route.setSourceAirport(sourceAirport);
			RouteAirport destinationAirport = new RouteAirport();
			destinationAirport.setIATAOrICAOCode(info[4]);
			destinationAirport.setId(info[5]);
			route.setDestinationAirport(destinationAirport);
			route.setCodeshare(info[6]);
			route.setStops(info[7]);
			route.setEquipment(info[8]);
			route.setPrice(info[9]);
		}
		return route;
	}

	public static List<Route> readFromFile(MultipartFile file, HashMap<Integer, Airline> airlineMap, HashMap<Integer, RouteAirport> airportMap) {
		List<Route> routeList = new ArrayList<>();
		try {
			String line;
			Airline airline = null;
			RouteAirport airport = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
			while ((line = br.readLine()) != null) {
				Route route = Route.fromString(line);
				if (route != null && route.validate()) {
					airline = route.getAirline();
					if (!airlineMap.containsKey(airline.getId())) {
						airlineMap.put(airline.getId(), airline);
					}
					airport = route.getSourceAirport();
					if (!airportMap.containsKey(airport.getId())) {
						airportMap.put(airport.getId(), airport);
					}
					airport = route.getDestinationAirport();
					if (!airportMap.containsKey(airport.getId())) {
						airportMap.put(airport.getId(), airport);
					}
					routeList.add(route);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return routeList;
	}

}
