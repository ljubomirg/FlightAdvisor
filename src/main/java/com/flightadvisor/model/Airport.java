package com.flightadvisor.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Airport extends BasicAirport {

	private static final String AIRPORT_DELIMITER = ",";
	private static final String AIRPORT_STRING = "\"";
	private static final String TZ_REGEX = "[A-Z][a-z]+/[A-Z][a-z]+([ |_][A-Z][a-z]+)?";

	private String name;
	@ManyToOne(cascade = CascadeType.ALL)
	private City city;
	private Double latitude;
	private Double longitude;
	private Integer altitude;
	private Integer timezone;
	private String DST;
	private String tz;
	private String type;
	private String source;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLatitude(String latitude) {
		try {
			this.latitude = Double.parseDouble(latitude);
		} catch (NumberFormatException nfe) {
		}
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setLongitude(String longitude) {
		try {
			this.longitude = Double.parseDouble(longitude);
		} catch (NumberFormatException nfe) {
		}
	}

	public Integer getAltitude() {
		return altitude;
	}

	public void setAltitude(Integer altitude) {
		this.altitude = altitude;
	}

	public void setAltitude(String altitude) {
		try {
			this.altitude = Integer.parseInt(altitude);
		} catch (NumberFormatException nfe) {
		}
	}

	public Integer getTimezone() {
		return timezone;
	}

	public void setTimezone(Integer timezone) {
		this.timezone = timezone;
	}

	public void setTimezone(String timezone) {
		try {
			this.timezone = Integer.parseInt(timezone);
		} catch (NumberFormatException nfe) {
		}
	}

	public String getDST() {
		return DST;
	}

	public void setDST(String DST) {
		this.DST = DST;
	}

	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	private boolean validateCity() {
		try {
			if (city.getName().isEmpty() || city.getCountry().isEmpty()) {
				return false;
			} else {
				return true;
			}
		} catch (NullPointerException e) {
			return false;
		}
	}

	@Override
	public boolean validate() {
		boolean result = super.validate();
		if (result && name != null && validateCity() && longitude != null && latitude != null && altitude != null
				&& (DST != null && DST.length() == 1) && (tz != null && Pattern.matches(TZ_REGEX, tz)) && type != null
				&& source != null) {
			return true;
		}
		return false;
	}

	private static String seperateString(String value) {
		String[] values = value.split(AIRPORT_STRING);
		if (values != null && values.length == 2) {
			return values[1];
		}
		return null;
	}

	public static Airport fromString(String airportString) {
		Airport airport = null;
		String[] info = airportString.split(AIRPORT_DELIMITER);
		if (info.length == 14) {
			airport = new Airport();
			airport.setId(info[0]);
			airport.setName(seperateString(info[1]));
			City city = new City();
			city.setName(seperateString(info[2]));
			city.setCountry(seperateString(info[3]));
			airport.setCity(city);
			airport.setIATACode(seperateString(info[4]));
			airport.setICAOCode(seperateString(info[5]));
			airport.setLatitude(info[6]);
			airport.setLongitude(info[7]);
			airport.setAltitude(info[8]);
			airport.setTimezone(info[9]);
			airport.setDST(seperateString(info[10]));
			airport.setTz(seperateString(info[11]));
			airport.setType(seperateString(info[12]));
			airport.setSource(seperateString(info[13]));
		}
		return airport;
	}

	public static List<Airport> readFromFile(MultipartFile file) {
		List<Airport> airportList = new ArrayList<>();
		try {
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
			while ((line = br.readLine()) != null) {
				Airport airport = Airport.fromString(line);
				if (airport != null && airport.validate()) {
					airportList.add(airport);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return airportList;
	}

}
