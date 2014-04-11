/**
 * Train
 */
package com.davew.website.templates.train;

import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.text.SimpleDateFormat;

/**
 * @author Dave
 * 
 */
@Entity
@Table(name = "train")
public class Train {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	private Calendar date;

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

	public long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Calendar getCalendar() {
		return date;
	}

	public String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(date.getTime());
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
}
