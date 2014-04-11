/**
 * Train Times Web Controller
 * 
 * 
 * 
 */
package com.davew.website.templates.train;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Dave
 * 
 */
@Controller
public class TrainController {

	@Autowired
	private TrainDAO trainDAO;

	private static final Logger logger = LoggerFactory
			.getLogger(TrainController.class);

	@RequestMapping(value = "/lasttrain/{line}/{station}", method = RequestMethod.GET)
	public String getLastTrainByStation(@PathVariable String line, @PathVariable String station, Locale locale, Model model) {
		logger.trace("Last Train - Line: {}", line);
		logger.trace("Last Train - Station: {}", station);
		
		if (line == null) {
			logger.warn("Line argument not supplied");
			station = "Hammersmith";
		}
		
		if (station == null) {
			logger.warn("Station argument not supplied");
			station = "Goldhawk Road";
		}
		
		
		String lastTrain = null;
		
		Times times = new Times();
		try {
			lastTrain = times.getLastTrain(line, station);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("station", station);
		model.addAttribute("lasttrain", lastTrain);

		return "/train/last";
	}


	@RequestMapping(value = "/lasttrain/{line}", method = RequestMethod.GET)
	public String getStationsByLine(@PathVariable String line, Locale locale, Model model) {
		logger.trace("Last Train - Line: {}", line);
		
		if (line == null) {
			logger.warn("Line argument not supplied");
			line = "Hammersmith";
		}
		
		List<Station> stations = null;
		
		Times times = new Times();
		try {
			stations = times.getStationsList(line);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("stations", stations);

		return "/train/stations";
	}

	@RequestMapping(value = "/traintimes", method = RequestMethod.GET)
	public String getTrainTimes(Locale locale, Model model) {
		logger.trace("Train times! The client locale is {}.", locale);

		List<Train> traintimes = new ArrayList<Train>();

		for (int i = 0; i <= 5; i++) {
			Calendar cal = Calendar.getInstance();
			// cal.set(1981, 2, 2);

			// Create new train
			Train train = new Train();
			train.setId(i);
			train.setName("Hammersmith");
			train.setDate(cal);

			// Add train to list
			traintimes.add(train);
		}

		return "/train/index";
	}

	@RequestMapping(value = "/train", method = RequestMethod.GET)
	public String getTrain(Locale locale, Model model) {
		logger.trace("Train! The client locale is {}.", locale);

		List<Train> trains = trainDAO.getAllTrains();
		model.addAttribute("trains", trains);

		return "/train/index";
	}
}
