package com.flightadvisor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.flightadvisor.model.Administrator;
import com.flightadvisor.model.Airline;
import com.flightadvisor.model.Airport;
import com.flightadvisor.model.City;
import com.flightadvisor.model.Comment;
import com.flightadvisor.model.LoginParamiters;
import com.flightadvisor.model.PasswordHash;
import com.flightadvisor.model.RegularUser;
import com.flightadvisor.model.Route;
import com.flightadvisor.model.RouteAirport;
import com.flightadvisor.model.User;
import com.flightadvisor.repository.AirlineRepository;
import com.flightadvisor.repository.AirportRepository;
import com.flightadvisor.repository.BasicAirportRepository;
import com.flightadvisor.repository.CityRepository;
import com.flightadvisor.repository.CommentRepository;
import com.flightadvisor.repository.RouteAirportRepository;
import com.flightadvisor.repository.RouteRepository;
import com.flightadvisor.repository.UserRepository;
import com.flightadvisor.service.TravelService;

@Controller
public class FlightAdvisorController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private AirportRepository airportRepository;
	@Autowired
	private BasicAirportRepository basicAirportRepository;
	@Autowired
	private RouteAirportRepository routeAirportRepository;
	@Autowired
	private AirlineRepository airlineRepository;
	@Autowired
	private RouteRepository routeRepository;
	@Autowired
	private TravelService travelService;
	@PersistenceContext
	EntityManager em;

	@RequestMapping("/login")
	public String login() {
		return "login.jsp";
	}

	@RequestMapping("/register")
	public ModelAndView register(RegularUser newUser) {
		
		ModelAndView modelAndView = new ModelAndView("login.jsp");
		
		Optional<User> user = userRepository.findById(newUser.getUserName());
		if (user != null && user.isPresent()) {
			modelAndView.addObject("registerMessage", "User already exists with that user name");
		} else {
			String hashedPassword = PasswordHash.createHash(newUser.getPassword());
			newUser.setPassword(hashedPassword);
			
			userRepository.save(newUser);
			modelAndView.addObject("registerMessage", "User registered");
		}
		
		return modelAndView;
	}

	@RequestMapping("/checkLogin")
	public ModelAndView checkLogin(LoginParamiters loginParamiters) {

		ModelAndView modelAndView = new ModelAndView();
		String hashedPassword = PasswordHash.createHash(loginParamiters.getPassword());

		User user = userRepository.findByUserNameAndPassword(loginParamiters.getUserName(),
				hashedPassword);
		if (user != null && user instanceof Administrator) {
			modelAndView.setViewName("administratorOverview.jsp");
		} else if (user != null && user instanceof RegularUser) {
			modelAndView.setViewName("regularUserOverview.jsp");
		} else {
			modelAndView.setViewName("login.jsp");
			modelAndView.addObject("message", "Incorect login data!");
		}

		return modelAndView;
	}

	@RequestMapping("/addCity")
	public ModelAndView addCity(City city) {

		ModelAndView modelAndView = new ModelAndView("administratorOverview.jsp");
		City alreadyExistsCity = cityRepository.findById(city.getName()).get();

		if (city.validate()) {
			if (!city.equals(alreadyExistsCity)) {
				cityRepository.save(city);
				modelAndView.addObject("message", "New city added!");
			} else {
				modelAndView.addObject("message", "The city already exists!");
			}
		} else {
			modelAndView.addObject("message", "Fill all fields for a city!");
		}

		return modelAndView;
	}

	@RequestMapping("/getAllCities")
	public ModelAndView getAllCities(@RequestParam(defaultValue = "-1") Integer commentsLimit) {

		ModelAndView modelAndView = new ModelAndView("cities.jsp");
		List<City> cities = cityRepository.findAll();

		filterComments(cities, commentsLimit);
		
		modelAndView.addObject("cities", cities);

		return modelAndView;
	}

	@RequestMapping("/getCityByName")
	public ModelAndView getCityByName(@RequestParam String name,
			@RequestParam(defaultValue = "-1") Integer commentsLimit) {

		ModelAndView modelAndView = new ModelAndView("cities.jsp");

		List<City> cities = cityRepository.findAllByName(name);

		filterComments(cities, commentsLimit);

		modelAndView.addObject("cities", cities);

		return modelAndView;
	}
	
	private void filterComments(List<City> cities, Integer commentsLimit) {
		if (commentsLimit > 0) {
			Pageable paging = PageRequest.of(0, commentsLimit, Sort.by(Sort.Order.desc("createdDate")));

			for (int i = 0; i < cities.size(); i++) {
				Page<Comment> comments = commentRepository.findByCity(cities.get(i), paging);
				cities.get(i).setComment(comments.getContent());
			}
		} else if (commentsLimit == 0) {
			for (int i = 0; i < cities.size(); i++) {
				cities.get(i).setComment(new ArrayList<>());
			}
		}
	}

	@RequestMapping("/deleteComment")
	@ResponseBody
	public String deleteComment(@RequestParam Integer id) {

		commentRepository.deleteById(id);

		return "Successfully deleted!";
	}

	@RequestMapping("/comment")
	@ResponseBody
	public String deleteComment(@RequestParam Integer id, @RequestParam String commentDescription,
			@RequestParam String cityName, @RequestParam String button) {

		String returnValue;
		Comment comment = new Comment();

		Date date = new Date(System.currentTimeMillis());
		if (button.equals("Update")) {
			comment = commentRepository.findById(id).get();
			comment.setModifiedDate(date);
			returnValue = "Successfully updated";
		} else {
			City optinalCity = cityRepository.findById(cityName).get();
			comment.setCity(optinalCity);
			comment.setCreatedDate(date);
			returnValue = "Successfully created";
		}
		comment.setCommentDescription(commentDescription);
		commentRepository.save(comment);

		return returnValue;
	}

	@RequestMapping("/updateComment")
	public ModelAndView getCityByName(@RequestParam Integer id) {

		ModelAndView modelAndView = new ModelAndView("comment.jsp");
		Comment comment = commentRepository.findById(id).orElse(new Comment());

		modelAndView.addObject("id", comment.getId());
		modelAndView.addObject("description", comment.getCommentDescription());
		modelAndView.addObject("button", "Update");

		return modelAndView;
	}

	@RequestMapping("/addComment")
	public ModelAndView getCityByName(@RequestParam String cityName) {

		ModelAndView modelAndView = new ModelAndView("comment.jsp");

		modelAndView.addObject("cityName", cityName);
		modelAndView.addObject("button", "Add");

		return modelAndView;
	}

	@PostMapping("/uploadAirports")
	public ModelAndView uploadAirports(@RequestParam("file") MultipartFile file) {

		ModelAndView modelAndView = new ModelAndView("administratorOverview.jsp");

		List<Airport> airportList = Airport.readFromFile(file);

		airportRepository.saveAll(airportList);

		modelAndView.addObject("uploadMessage", "Successfully updated!");
		return modelAndView;
	}

	@PostMapping("/uploadRoutes")
	public ModelAndView uploadRoutes(@RequestParam("file") MultipartFile file) {

		ModelAndView modelAndView = new ModelAndView("administratorOverview.jsp");

		HashMap<Integer, Airline> airlineMap = new HashMap<>();
		HashMap<Integer, RouteAirport> airportMap = new HashMap<>();
		List<Route> routeList = Route.readFromFile(file, airlineMap, airportMap);

		basicAirportRepository.saveAll(airportMap.values());
		airlineRepository.saveAll(airlineMap.values());
		routeRepository.saveAll(routeList);

		modelAndView.addObject("uploadMessage", "Successfully updated!");
		return modelAndView;
	}

	@RequestMapping("/getRoute")
	public ModelAndView getRoute(@RequestParam String sourceCity, @RequestParam String destinationCity) {

		ModelAndView modelAndView = travelService.cheapestFlight(sourceCity, destinationCity);

		return modelAndView;
	}

}
