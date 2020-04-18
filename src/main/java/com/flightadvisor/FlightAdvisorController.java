package com.flightadvisor;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.flightadvisor.model.Administrator;
import com.flightadvisor.model.Airport;
import com.flightadvisor.model.City;
import com.flightadvisor.model.Comment;
import com.flightadvisor.model.LoginParamiters;
import com.flightadvisor.model.RegularUser;
import com.flightadvisor.model.User;
import com.flightadvisor.repository.CityRepository;
import com.flightadvisor.repository.CommentRepository;
import com.flightadvisor.repository.FileRepository;
import com.flightadvisor.repository.UserRepository;

@Controller
public class FlightAdvisorController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private FileRepository fileRepository;
	

	@RequestMapping("/login")
	public String login() {
		return "login.jsp";
	}

	@RequestMapping("/checkLogin")
	public ModelAndView checkLogin(LoginParamiters loginParamiters) {

		ModelAndView modelAndView = new ModelAndView();

		User user = userRepository.findByUserNameAndPassword(loginParamiters.getUserName(), loginParamiters.getPassword());
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
		City alreadyExistsCity = cityRepository.findByName(city.getName());

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
	public ModelAndView getAllCities(@RequestParam(defaultValue = "-1") Integer commentNumber) {

		ModelAndView modelAndView = new ModelAndView("cities.jsp");
		List<City> cities = cityRepository.findAll();

		modelAndView.addObject("cities", cities);

		return modelAndView;
	}

	@RequestMapping("/getCityByName")
	public ModelAndView getCityByName(@RequestParam String name,
			@RequestParam(defaultValue = "-1") Integer commentNumber) {

		ModelAndView modelAndView = new ModelAndView("cities.jsp");
		List<City> cities = cityRepository.findAllByName(name);

		modelAndView.addObject("cities", cities);

		return modelAndView;
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
			@RequestParam String button) {

		String returnValue;

		Comment comment = commentRepository.findById(id).orElse(new Comment());
		comment.setCommentDescription(commentDescription);

		Date date = new Date(System.currentTimeMillis());
		if (button.equals("Update")) {
			comment.setModifiedDate(date);
			returnValue = "Successfully updated";
		} else {
			comment.setCreatedDate(date);
			returnValue = "Successfully created";
		}
		commentRepository.save(comment);

		return returnValue;
	}

	@RequestMapping("/updateComment")
	public ModelAndView getCityByName(@RequestParam Integer id) {

		ModelAndView modelAndView = new ModelAndView("comment.jsp");
		Comment comment = commentRepository.findById(id).orElse(new Comment());

		modelAndView.addObject("comment", comment);
		modelAndView.addObject("button", "Update");

		return modelAndView;
	}

	@RequestMapping("/addComment")
	public ModelAndView getCityByName(@RequestParam String cityName) {

		ModelAndView modelAndView = new ModelAndView("comment.jsp");
		Comment comment = new Comment();
		City city = cityRepository.findById(cityName).orElse(new City());
		comment.setCity(city);
		commentRepository.save(comment);

		modelAndView.addObject("comment", comment);
		modelAndView.addObject("button", "Add");

		return modelAndView;
	}
	
	@PostMapping("/uploadFile")
	@ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file) {
		
		List<Airport> airportList = Airport.readFromFile(file);

		fileRepository.saveAll(airportList);
		
		return "Successfully updated!";
    }
}
