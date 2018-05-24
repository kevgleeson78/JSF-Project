package com.geog.Controlller;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;



import com.geog.DAO.MongoDAO;

import com.geog.Model.HeadsOfState;
@SessionScoped
@ManagedBean
public class HeadsOfStateController {
	

	ArrayList<HeadsOfState> headsOfStates;
	private MongoDAO mongoDao;
	private HeadsOfState headsOfState;


	public HeadsOfStateController() {
		super();
		headsOfStates= new ArrayList<HeadsOfState>();
		try {
			mongoDao = new MongoDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public HeadsOfStateController(ArrayList<HeadsOfState> headsOfStates) {
		super();
		this.headsOfStates = headsOfStates;
	}
	public ArrayList<HeadsOfState> getHeadsOfStates() {
		return headsOfStates;
	}

	public void setHeadsOfStates(ArrayList<HeadsOfState> headsOfStates) {
		this.headsOfStates = headsOfStates;
	}
	public HeadsOfState getHeadsOfState() {
		return headsOfState;
	}
	
	public void getHeadOfState() throws Exception {
		//headsOfStates.clear();
		if (mongoDao != null) {
			try {
				headsOfStates = mongoDao.loadHeadOfState();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String addHeadOfState(HeadsOfState hos) throws Exception {
		if (mongoDao != null) {
			try {
				headsOfStates = mongoDao.addHeadOfState(hos);
				return "list_heads_of_state";
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		return null;
	}
	public String deleteHeadOfState(String _id) {
		try {
			headsOfState = mongoDao.deleteHeadOfState(_id);
			
			return "list_heads_of_state";
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
	}
}
