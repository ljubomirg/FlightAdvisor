package com.flightadvisor;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.flightadvisor.dao.CityDAO;
import com.flightadvisor.dao.CommentDAO;
import com.flightadvisor.dao.UserDAO;
import com.flightadvisor.model.Administrator;
import com.flightadvisor.model.City;
import com.flightadvisor.model.Comment;
import com.flightadvisor.model.LoginParamiters;
import com.flightadvisor.model.RegularUser;
import com.flightadvisor.model.User;

@Controller
public class FlightAdvisorController {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private CityDAO cityDAO;
	@Autowired
	private CommentDAO commentDAO;

	@RequestMapping("/login")
	public String login() {
		return "login.jsp";
	}

	@RequestMapping("/checkLogin")
	public ModelAndView checkLogin(LoginParamiters loginParamiters) {

		ModelAndView modelAndView = new ModelAndView();

		User user = userDAO.findByUserNameAndPassword(loginParamiters.getUserName(), loginParamiters.getPassword());
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
		City alreadyExistsCity = cityDAO.findByName(city.getName());

		if (city.valid()) {
			if (!city.equals(alreadyExistsCity)) {
				cityDAO.save(city);
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
		List<City> cities = cityDAO.findAll();

		modelAndView.addObject("cities", cities);

		return modelAndView;
	}

	@RequestMapping("/getCityByName")
	public ModelAndView getCityByName(@RequestParam String name,
			@RequestParam(defaultValue = "-1") Integer commentNumber) {

		ModelAndView modelAndView = new ModelAndView("cities.jsp");
		List<City> cities = cityDAO.findAllByName(name);

		modelAndView.addObject("cities", cities);

		return modelAndView;
	}

	@RequestMapping("/deleteComment")
	@ResponseBody
	public String deleteComment(@RequestParam Integer id) {

		commentDAO.deleteById(id);

		return "Successfully deleted!";
	}

	@RequestMapping("/comment")
	@ResponseBody
	public String deleteComment(@RequestParam Integer id, @RequestParam String commentDescription,
			@RequestParam String button) {

		String returnValue;

		Comment comment = commentDAO.findById(id).orElse(new Comment());
		comment.setCommentDescription(commentDescription);

		Date date = new Date(System.currentTimeMillis());
		if (button.equals("Update")) {
			comment.setModifiedDate(date);
			returnValue = "Successfully updated";
		} else {
			comment.setCreatedDate(date);
			returnValue = "Successfully created";
		}
		commentDAO.save(comment);

		return returnValue;
	}

	@RequestMapping("/updateComment")
	public ModelAndView getCityByName(@RequestParam Integer id) {

		ModelAndView modelAndView = new ModelAndView("comment.jsp");
		Comment comment = commentDAO.findById(id).orElse(new Comment());

		modelAndView.addObject("comment", comment);
		modelAndView.addObject("button", "Update");

		return modelAndView;
	}

	@RequestMapping("/addComment")
	public ModelAndView getCityByName(@RequestParam String cityName) {

		ModelAndView modelAndView = new ModelAndView("comment.jsp");
		Comment comment = new Comment();
		City city = cityDAO.findById(cityName).orElse(new City());
		comment.setCity(city);
		commentDAO.save(comment);

		modelAndView.addObject("comment", comment);
		modelAndView.addObject("button", "Add");

		return modelAndView;
	}
}
