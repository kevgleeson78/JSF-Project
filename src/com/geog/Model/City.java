package com.geog.Model;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class City {

	private String cty_code;
	private String co_code;
	private String reg_code;
	private String cty_name;
	private int population;
	private double areaKM;
	private String isCoastal;
	private String co_name;
	private String reg_name;

	public City() {

	}

	




	public String getReg_name() {
		return reg_name;
	}






	public void setReg_name(String reg_name) {
		this.reg_name = reg_name;
	}






	public String getCo_name() {
		return co_name;
	}

	public void setCo_name(String co_name) {
		this.co_name = co_name;
	}

	public String getCty_code() {
		return cty_code;
	}

	public void setCty_code(String cty_code) {
		this.cty_code = cty_code;
	}

	public String getCo_code() {
		return co_code;
	}

	public void setCo_code(String co_code) {
		this.co_code = co_code;
	}

	public String getReg_code() {
		return reg_code;
	}

	public void setReg_code(String reg_code) {
		this.reg_code = reg_code;
	}

	public String getCty_name() {
		return cty_name;
	}

	public void setCty_name(String cty_name) {
		this.cty_name = cty_name;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public double getAreaKM() {
		return areaKM;
	}

	public void setAreaKM(double areaKM) {
		this.areaKM = areaKM;
	}
	public String getIsCoastal() {
		return isCoastal;
	}

	public void setIsCoastal(String isCoastal) {
		this.isCoastal = isCoastal;
	}
	
}
