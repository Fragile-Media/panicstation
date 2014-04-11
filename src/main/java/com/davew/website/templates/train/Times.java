/**
 * 
 * http://www.tfl.gov.uk/gettingaround/1129.aspx
 */
package com.davew.website.templates.train;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.jempbox.xmp.XMPMetadata;
import org.apache.jempbox.xmp.XMPSchemaBasic;
import org.apache.jempbox.xmp.XMPSchemaDublinCore;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.util.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.WordUtils;

/**
 * @author Dave
 * 
 */
public class Times {

	private static final String LINE_BREAK_PATTERN = "(\\n)";
	private static final String TIME_PATTERN = "([0-9]{4})";
	private static final String DAY_SPLIT_PATTERN = "Sundays";

	private static final Logger logger = LoggerFactory.getLogger(Times.class);

	public static final String FILE_PATH = "http://www.tfl.gov.uk/assets/downloads/first-and-last-";
	public static final String FILE_EXT = ".pdf";

	public static final String DAY_FORMAT = "E";
	
	public static final Integer DAY_TIME_CHANGE = 0500;

	/**
	 * @return the dayTimeChange
	 */
	private static Integer getDayTimeChange() {
		return DAY_TIME_CHANGE;
	}

	private Calendar date;
	private List<Station> stations = new ArrayList<Station>();
	
	private Integer lastTrainTime = 0500;

	/**
	 * 
	 */
	public Times() {
		// Set default date
		date = Calendar.getInstance();
	}

	private URL getPdfUrl(String line) {
		StringBuilder path = new StringBuilder();
		path.append(FILE_PATH); // Add folder path
		path.append(line.toLowerCase()); // Add line name
		path.append(FILE_EXT); // Add file extension

		URL url = null;
		try {
			logger.trace("The URL", path.toString());

			url = new URL(path.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return url;
	}

	public PDDocument getPdf(String line) throws IOException {
		PDDocument pdf = new PDDocument();
		URL pdfUrl = getPdfUrl(line);
		pdf = PDDocument.load(pdfUrl);

		// Get meta data
		PDDocumentCatalog catalog = pdf.getDocumentCatalog();
		PDMetadata meta = catalog.getMetadata();
		XMPMetadata metadata = meta.exportXMPMetadata();

		XMPSchemaDublinCore dc = metadata.getDublinCoreSchema();
		if (dc != null) {
			display("Title:", dc.getTitle());
			list("Creators: ", dc.getCreators());
		}

		XMPSchemaBasic basic = metadata.getBasicSchema();
		if (basic != null) {
			display("Create Date:", basic.getCreateDate());
			display("Modify Date:", basic.getModifyDate());
		}

		return pdf;
	}

	public String getLastTrain(String line, String station) throws IOException {
		String returnStr = "";
		PDDocument pdf = new PDDocument();

		try {
			// Get PDF Document
			pdf = getPdf(line);

			// System.out.println("Pages:" + pdf.getNumberOfPages());

			// extract text
			PDFTextStripper stripper = new PDFTextStripper();

			Pattern timePattern = Pattern.compile(TIME_PATTERN);

			String text = stripper.getText(pdf);

			// Check for station match on this line
			String dayPattern = getDaySplitPattern();
			System.out.println("Day: " + dayPattern);

			// Spilt pdf string on days
			String pages[] = text.split(dayPattern);

			int pageIndex = getDayPageID(pages.length, false);

			// Get page to analyse
			String page = pages[pageIndex];

			//System.out.println("== NEW PAGE " + pageIndex + " - start!!! ================================");
			//System.out.println(page);
			//System.out.println("== NEW PAGE " + pageIndex + " - end !!! ================================");

			// Spilt pdf string on line breaks
			String rows[] = page.split(LINE_BREAK_PATTERN);

			for (int i = 0; i < rows.length; i++) {
				//System.out.println(i + ". " + rows[i]);

				// Check for station match on this line
				if (rows[i].contains(station)) {
					//System.out.println("==new '"+station+"' row ================================");
					// System.out.println(rows[i]);
					Matcher m = timePattern.matcher(rows[i]);
					while (m.find()) {
						// System.out.println("match: "+m.group(1));
						Integer timeMatch = Integer.parseInt(m.group(1).trim());
						
						/* 
						 * Apply differnt logic to early morning (late night) 0000 hours times
						 * e.g. Make sure that 0115 is later than 0745
						 */
						boolean updateLastTrainTime = false;
						if (getLastTrainTime() < getDayTimeChange()) {
							System.out.println("we're into the early hours!");		
							if (timeMatch < getDayTimeChange() && timeMatch > getLastTrainTime()) {
								updateLastTrainTime = true;
							}							
						} else if (timeMatch > getLastTrainTime()) {
							updateLastTrainTime = true;
						}
						
						if (updateLastTrainTime) {
							System.out.println("updating..."+timeMatch);
							setLastTrainTime(timeMatch);
						}
					}
					// System.out.println("==================================");
				}
			}

			// Pad numbers to put leading 0 back on string
			returnStr = String.format("%04d", getLastTrainTime());

			System.out.println("Last train from: " + station + " is at: " + returnStr);

			// print extracted text
			// System.out.println(text);
		} finally {
			if (pdf != null) {
				pdf.close();
			}
		}

		return returnStr;

	}

	/*
	 * TODO - Write something better
	 * Basically this method works out which set of pages in teh PDF to look for
	 * times in
	 */

	/**
	 * @return the lastTrainTime
	 */
	public Integer getLastTrainTime() {
		return lastTrainTime;
	}

	/**
	 * @param lastTrainTime the lastTrainTime to set
	 */
	public void setLastTrainTime(Integer lastTrainTime) {
		this.lastTrainTime = lastTrainTime;
	}

	private int getDayPageID(int PageLength, boolean debugSunday) {
		SimpleDateFormat sdf = new SimpleDateFormat(DAY_FORMAT);
		String day = sdf.format(date.getTime());

		if (debugSunday) {
			System.out.println("debugSunday: " + debugSunday);
		} else {
			System.out.println("PageLength: " + PageLength);
			System.out.println("Day Pattern: " + day);
		}

		int id = 0;
		if (debugSunday || (PageLength > 1 && DAY_SPLIT_PATTERN.contains(day))) {
			id = 1;
		}

		System.out.println("id: " + id);

		return id;
	}

	/*
	 * Get the pattern to split pages on
	 */
	private String getDaySplitPattern() {
		return DAY_SPLIT_PATTERN;
	}

	private boolean addStation(String stationName) {

		boolean found = false;
		for (Station station : getStations()) {
			// Check for station match on this line and add to array if
			// we dont already have it
			if (stationName.equals(station.getName())) {
				found = true;
			}
		}

		if (!found) {
			// Create new station
			Station st = new Station();
			st.setName(WordUtils.capitalizeFully(stationName));
			st.setRawName(stationName);
			stations.add(st);
		}

		return found;

	}

	public List<Station> getStations() {
		return stations;
	}

	public List<Station> getStationsList(String line) throws IOException {

		PDDocument pdf = new PDDocument();

		try {
			// Open PDF
			pdf = getPdf(line);

			// System.out.println("Pages:" + pdf.getNumberOfPages());

			// extract text
			PDFTextStripper stripper = new PDFTextStripper();
			String text = stripper.getText(pdf);
			Pattern stationPattern = Pattern.compile("^([A-Za-z’]{3,}\\s*[A-Za-z’]*\\s*[A-Za-z’]*)\\s[.]{3}");

			// Spilt pdf string on line breaks
			String split[] = text.split(LINE_BREAK_PATTERN);

			for (int i = 0; i < split.length; i++) {
				// System.out.println(i + " " + split[i]);
				System.out.println(split[i]);

				Matcher m = stationPattern.matcher(split[i]);
				while (m.find()) {
					String stationName = m.group(1);
					System.out.println("match: " + stationName);

					// Try and add station to array
					addStation(stationName);

				}
			}

		} finally {
			if (pdf != null) {
				pdf.close();
			}
		}

		return getStations();
	}

	private static void display(String title, Object value) {
		if (value != null) {
			System.out.println(title + " " + format(value));
		}
	}

	private static void list(String title, @SuppressWarnings("rawtypes") List list) {
		if (list == null) {
			return;
		}
		System.out.println(title);
		@SuppressWarnings("rawtypes")
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Object o = iter.next();
			System.out.println("  " + format(o));
		}
	}

	private static String format(Object o) {
		if (o instanceof Calendar) {
			Calendar cal = (Calendar) o;
			return DateFormat.getDateInstance().format(cal.getTime());
		} else {
			return o.toString();
		}
	}

}
