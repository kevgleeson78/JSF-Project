package com.geog.Model;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class HeadsOfState {
	
	private String _id;
	private String headOfState;
	
	public HeadsOfState() {
		
	}
	public HeadsOfState(String _id,String headOfState) {
		this._id = _id;
		this.headOfState = headOfState;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id.toString();
	}
	public String getHeadOfState() {
		return headOfState;
	}
	public void setHeadOfState(String headOfState) {
		this.headOfState = headOfState.toString();
	}
}
