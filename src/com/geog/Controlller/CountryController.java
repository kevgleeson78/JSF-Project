package com.geog.Controlller;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.geog.DAO.DAO;
import com.geog.Model.Country;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@SessionScoped
@ManagedBean
public class CountryController {
	ArrayList<Country> countries;
	private DAO dao;
	private Country country;

	public CountryController() {
		super();
		countries = new ArrayList<Country>();
		try {
			dao = new DAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CountryController(ArrayList<Country> countries) {
		super();
		this.countries = countries;
	}

	public ArrayList<Country> getCountries() {
		return countries;
	}

	public void setCountries(ArrayList<Country> countries) {
		this.countries = countries;
	}

	public Country getCountry() {
		return country;
	}

	public void getCountries1() throws Exception {
		countries.clear();
		if (dao != null) {
			try {
				countries = dao.loadCountries();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String addCountry(Country country) throws Exception {
		if (dao != null) {
			try {
				dao.addCountry(country);
				return "list_countries";
			} catch (MySQLIntegrityConstraintViolationException e) {
				FacesMessage message = new FacesMessage(
						"Error: Country Code " + country.getCo_code() + " already exists");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} catch (CommunicationsException e) {
				FacesMessage message = new FacesMessage("Error: Cannot connect to Database");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} catch (Exception e) {
				FacesMessage message = new FacesMessage("Error while trying to insert Product " + country.getCo_code());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		}
		return null;
	}

	public String deleteCountry(String co_code) {
		try {
			country = dao.deleteCountry(co_code);

			return "list_countries";
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
	}

	public String loadCountry(String co_code) throws SQLException {

		try {
			country = dao.loadCountry(co_code);
			return "update_country";
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}

	}
	public String updateCountry(String co_code) throws SQLException {

		try {
			dao.updateCountry(country);
			return "list_countries";
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}

	}
}
