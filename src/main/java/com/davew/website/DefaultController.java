package com.davew.website;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class DefaultController {

	private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "/home/index";
	}

	/**
	 * Simply selects the default page view to render by returning its name.
	 */
	@RequestMapping(value = "/page/{id}", method = RequestMethod.GET)
	public String page(@PathVariable long id, Locale locale, Model model) {
		logger.info("Default page view! The client locale is {}.", locale);

		//String pageID = Long.toString(id);		
		model.addAttribute("pageID", id);

		return "/default/index";
	}

//	/**
//	 * Handles requests for the application pizza page.
//	 * http://gerrydevstory.com/2013/06/29/spring-mvc-hibernate-mysql-quick-start-from-scratch/
//	 */
//	@Autowired private PizzaDAO pizzaDAO;
//	@RequestMapping(value = "/pizza/", method = RequestMethod.GET)
//	public String list(Locale locale, Model model) {
//		logger.info("Pizza! The client locale is {}.", locale);
//		
//		List<Pizza> pizzas = pizzaDAO.findAll();
//		model.addAttribute("pizzas", pizzas);
//		return "/pizza/index";
//	}
//	
}
