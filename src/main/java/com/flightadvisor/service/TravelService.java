package com.flightadvisor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.flightadvisor.model.Airport;
import com.flightadvisor.model.City;
import com.flightadvisor.model.Route;
import com.flightadvisor.model.RouteAirport;
import com.flightadvisor.model.Travel;
import com.flightadvisor.model.TravelRoute;
import com.flightadvisor.repository.AirportRepository;
import com.flightadvisor.repository.CityRepository;
import com.flightadvisor.repository.RouteAirportRepository;
import com.flightadvisor.repository.RouteRepository;

@Service
public class TravelService {

	@Autowired
	private RouteRepository routeRepository;
	@Autowired
	private AirportRepository airportRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private RouteAirportRepository routeAirportRepository;

	private HashMap<RouteAirport, List<Route>> hashRouts = new HashMap<>();

	private static final double FEET_KM_RATIO = 0.0003048;
	private static final double EARTH_RADIUS = 6371;
	private static final int MAX_TRAVEL_STOPS = 4;

	public ModelAndView cheapestFlight(String sourceCity, String destinationCity) {

		ModelAndView modelAndView = new ModelAndView("regularUserOverview.jsp");

		City source = cityRepository.findById(sourceCity).get();
		City destination = cityRepository.findById(destinationCity).get();
		if (source == null && destination == null) {
			modelAndView.addObject("messageRoute", "Source and destination city names are wrong!");
		} else if (source == null) {
			modelAndView.addObject("messageRoute", "Source city name is wrong!");
		} else if (destination == null) {
			modelAndView.addObject("messageRoute", "Destination city name is wrong!");
		} else {
			Airport sourceAirport = airportRepository.findByCityName(sourceCity);
			Airport destinationAirport = airportRepository.findByCityName(destinationCity);
			if (source == null || destination == null) {
				modelAndView.addObject("messageRoute", "Source or destination Airport missing!");
			} else {
				RouteAirport routeSourceAirport = routeAirportRepository.findById(sourceAirport.getId()).get();
				RouteAirport routeDestinationAirport = routeAirportRepository.findById(destinationAirport.getId())
						.get();
				if (source == null || destination == null) {
					modelAndView.addObject("messageRoute", "Source or destination Airport missing!");
				} else {

					List<Route> routes = routeRepository.findAll();
					hashAllRoute(routes);

					Travel travel = new Travel(source, destination);
					findRoutsForCities(travel, routeSourceAirport, routeDestinationAirport);

					travel.sortTravelRouts();

					modelAndView.addObject("travel", travel);

				}
			}
		}
		return modelAndView;
	}

	private void findRoutsForCities(Travel travel, RouteAirport routeSourceAirport,
			RouteAirport routeDestinationAirport) {
		HashMap<Integer, RouteAirport> parent = new HashMap<>();
		List<Route> all = new ArrayList<Route>();
		List<Object> routes = new ArrayList<Object>();
		parent.put(routeSourceAirport.getId(), routeSourceAirport);
		findRouts(routeSourceAirport, routeDestinationAirport, parent, all, routes);

		List<TravelRoute> travelList = new ArrayList<TravelRoute>();
		for (int i = 0; i < routes.size(); i++) {
			if (routes.get(i) instanceof ArrayList<?>) {
				ArrayList<Route> routeList = (ArrayList<Route>) routes.get(i);
				if (routeList.size() > 0) {
					double totalPrice = 0;
					double totalLength = 0;
					City routeDestinationCity = null;
					TravelRoute travelRoute = new TravelRoute();

					for (int j = 0; j < routeList.size(); j++) {
						Route route = routeList.get(j);
						Airport sourceAirport = airportRepository.findById(route.getSourceAirport().getId()).get();
						City routeSourceCity = sourceAirport.getCity();
						Airport destinationairport = airportRepository.findById(route.getDestinationAirport().getId())
								.get();
						routeDestinationCity = destinationairport.getCity();

						travelRoute.addCity(routeSourceCity);

						totalPrice += route.getPrice();

						double length = distance(sourceAirport.getLatitude(), sourceAirport.getLongitude(),
								sourceAirport.getAltitude(), destinationairport.getLatitude(),
								destinationairport.getLongitude(), destinationairport.getAltitude());
						totalLength += length;
					}
					travelRoute.addCity(routeDestinationCity);
					travelRoute.generateCitiesName();
					travelRoute.setTotalPrice(totalPrice);
					travelRoute.setLength(totalLength);
					travel.addTravelRoute(travelRoute);
				}
			}
		}
	}

	private double distance(double latitudeA, double longitudeA, double altitudeA, double latitudeB, double longitudeB,
			double altitudeB) {

		double latDistance = Math.toRadians(latitudeB - latitudeA);
		double lonDistance = Math.toRadians(longitudeB - longitudeA);
		double multiplication = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(latitudeA)) * Math.cos(Math.toRadians(latitudeB)) * Math.sin(lonDistance / 2)
						* Math.sin(lonDistance / 2);
		double theta = 2 * Math.atan2(Math.sqrt(multiplication), Math.sqrt(1 - multiplication));

		double height = altitudeA - altitudeB;

		double distance = Math.pow(EARTH_RADIUS * theta, 2) + Math.pow(height * FEET_KM_RATIO, 2);

		return Math.sqrt(distance);
	}

	private void hashAllRoute(List<Route> routes) {
		for (Route route : routes) {
			if (hashRouts.containsKey(route.getSourceAirport())) {
				hashRouts.get(route.getSourceAirport()).add(route);
			} else {
				List<Route> list = new ArrayList<>();
				list.add(route);
				hashRouts.put(route.getSourceAirport(), list);
			}
		}
	}

	private void findRouts(RouteAirport routeSourceAirport, RouteAirport routeDestinationAirport,
			HashMap<Integer, RouteAirport> parent, List<Route> routs, List<Object> allRoutes) {
		List<Route> routeList = hashRouts.get(routeSourceAirport);
		if (routeList != null) {
			for (Route route : routeList) {
				RouteAirport destinationAirport = route.getDestinationAirport();
				if (destinationAirport.equals(routeDestinationAirport)) {
					routs.add(route);
					allRoutes.add(new ArrayList<>(routs));
					routs.remove(route);
				} else if (!parent.containsKey(destinationAirport.getId()) && parent.size() < MAX_TRAVEL_STOPS) {
					parent.put(destinationAirport.getId(), destinationAirport);
					routs.add(route);
					findRouts(destinationAirport, routeDestinationAirport, parent, routs, allRoutes);
					parent.remove(destinationAirport.getId());
					routs.remove(route);
				}
			}
		}
	}

}
