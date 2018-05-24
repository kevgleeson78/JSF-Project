package com.geog.Controlller;


import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.geog.DAO.DAO;
import com.geog.Model.Region;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
@RequestScoped
@ManagedBean
public class RegionsController {
	ArrayList<Region> regions;
	private DAO dao;
	private Region region;


	public RegionsController() {
		super();
		regions = new ArrayList<Region>();
		try {
			dao = new DAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public RegionsController(ArrayList<Region> regions) {
		super();
		this.regions = regions;
	}

	public ArrayList<Region> getRegions() {
		return regions;
	}

	public void setRegions(ArrayList<Region> regions) {
		this.regions = regions;
	}
	
	
	
	public Region getRegion() {
		return region;
	}

	public void getRegions1() throws Exception {
		regions.clear();
		if (dao != null) {
			try {
				regions = dao.loadRegions();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public String addRegion(Region region) {
		if (dao != null) {
			try {
				dao.addRegion(region);
				return "list_regions";
			} catch (MySQLIntegrityConstraintViolationException e) {
				FacesMessage message = new FacesMessage("Error: Country Code " + region.getCo_code() + " does not exist");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} catch (CommunicationsException e) {
				FacesMessage message = new FacesMessage("Error: Cannot connect to Database");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			} catch (Exception e) {
				FacesMessage message = new FacesMessage("Error while trying to insert Region " + region.getReg_code());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		}
		return null;
	}
	
}
