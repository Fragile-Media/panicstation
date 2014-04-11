/**
 * Pizza Shop Web Controller
 */
package com.davew.website.templates.pizza;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Dave
 * 
 */
@Controller
public class PizzaController {

	@Autowired
	private PizzaDAO pizzaDAO;

	private static final Logger logger = LoggerFactory
			.getLogger(PizzaController.class);

	@RequestMapping(value = "/pizza", method = RequestMethod.GET)
	public String getPizza(Locale locale, Model model) {
		logger.trace("Pizza! The client locale is {}.", locale);

		List<Pizza> pizzas = pizzaDAO.getAllPizzas();
		model.addAttribute("pizzas", pizzas);

		return "/pizza/index";
	}

	@RequestMapping(value = "/pizza/{id}", method = RequestMethod.GET)
	public String viewPizza(@PathVariable long id, Locale locale, Model model) {
		logger.trace("View Pizza: {}", id);

		Pizza pizza = pizzaDAO.getPizza(id);
		model.addAttribute("pizza", pizza);
		return "/pizza/view";
	}

	@RequestMapping(value = "/pizza/{id}/edit", method = RequestMethod.GET)
	public String editPizza(@PathVariable long id, Locale locale, Model model) {
		logger.trace("Edit Pizza: {}", id);

		Pizza pizza = pizzaDAO.getPizza(id);
		model.addAttribute("pizza", pizza);
		return "/pizza/edit";
	}

	@RequestMapping(value = "/pizza/{[0-9]+}/edit", method = RequestMethod.POST)
	public String updatePizza(@ModelAttribute("pizza") Pizza pizza,
			BindingResult result, Locale locale, Model model) {
		logger.info("Saving Pizza: {}", pizza.getId());

		try {
			pizza = pizzaDAO.updatePizza(pizza);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("result", result);
		model.addAttribute("pizza", pizza);

		return "/pizza/edit";
		
		//if (result.hasErrors()) {
			// There are errors, return to form
			//logger.info("hasErrors");
		//} else {
			// All's good return to list
			//return "redirect:/pizza";
		//}

	}

	@RequestMapping(value = "/pizza/add", method = RequestMethod.GET)
	public String addPizza(Locale locale, Model model) {
		logger.trace("Add Pizza...");

		Pizza pizza = new Pizza();
		pizza.setId(0);
		model.addAttribute("pizza", pizza);
		return "/pizza/add";
	}

	@RequestMapping(value = "/pizza/add", method = RequestMethod.POST)
	public String savePizza(@ModelAttribute("pizza") Pizza pizza,
			BindingResult result, Locale locale, Model model) {
		logger.info("Adding Pizza: {}", pizza.getId());

		pizza = pizzaDAO.savePizza(pizza);

		//if (result.hasErrors()) {
			// There are errors, return to form
			//logger.info("hasErrors");

			model.addAttribute("result", result);
			model.addAttribute("pizza", pizza);

			return "/pizza/add";
		//} else {
			// All's good return to list
			//return "redirect:/pizza";
		//}

	}
}
