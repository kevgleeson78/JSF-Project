package com.geog.Controlller;

import java.util.ArrayList;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.geog.DAO.DAO;
import com.geog.Model.City;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
@SessionScoped
@ManagedBean
public class CityController {

	

	ArrayList<City> cities;
	
	private DAO dao;
	private City city;

	public CityController() {
		super();
		cities = new ArrayList<City>();
		try {
			dao = new DAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public CityController(ArrayList<City> cities) {
		super();
		this.cities = cities;
	}

	public ArrayList<City> getCities() {
		return cities;
	}

	public void setCities(ArrayList<City> cities) {
		this.cities = cities;
	}

	public City getCity() {
		return city;
	}

	public void getCities1() throws Exception {
		cities.clear();
		if (dao != null) {
			try {
				cities = dao.loadCities();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String loadCity(String cty_code) throws Exception {
		try {
			// System.out.println("P=>" + city.toString());
			city = dao.loadCity(cty_code);

			return "city_view";
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
	}
private String search;
private String isCoastal;
private String compare;
private int comparePopulation;

	public String getCompare() {
	return compare;
}


public void setCompare(String compare) {
	this.compare = compare;
}


	public int getComparePopulation() {
	return comparePopulation;
}


public void setComparePopulation(int comparePopulation) {
	this.comparePopulation = comparePopulation;
}


	public String getIsCoastal() {
	return isCoastal;
}


public void setIsCoastal(String isCoastal) {
	this.isCoastal = isCoastal;
}


	public String getSearch() {
	return search;
}


public void setSearch(String search) {
	this.search = search;
}

	//Search database by name
    public ArrayList<City> getSearchBluewave() throws Exception {
    	
        return new DAO().SearchBluewave(search,isCoastal,compare,comparePopulation);
    }
	
	public String addCity(City city) throws Exception{
		if (dao != null) {
			try {
				dao.addCity(city);
				return "list_cities";
			} catch (MySQLIntegrityConstraintViolationException e) {
				FacesMessage message = new FacesMessage(
						"Error: Attempeing to add City ,  " + city.getCo_code() + " , "+ city.getReg_code()+", "+ city.getCty_code());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} catch (CommunicationsException e) {
				FacesMessage message = new FacesMessage("Error: Cannot connect to Database");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} catch (Exception e) {
				FacesMessage message = new FacesMessage("Error while trying to insert City " + city.getCo_code());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		}
		return null;
	}
}
