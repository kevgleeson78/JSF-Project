package com.geog.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.geog.Model.City;
import com.geog.Model.Country;
import com.geog.Model.Region;

public class DAO {
	private DataSource mysqlDS;
	

			
	public DAO() throws Exception {
		Context context = new InitialContext();
		String jndiName = "java:comp/env/jdbc/geography";
		mysqlDS = (DataSource) context.lookup(jndiName);
		
	}

	

	public ArrayList<Region> loadRegions() throws Exception {
		ArrayList<Region> regions = new ArrayList<Region>();
		Connection myConn = mysqlDS.getConnection();
		PreparedStatement myStmt = myConn.prepareStatement("select * from region;");
		ResultSet myRs = myStmt.executeQuery();

		// process result set
		while (myRs.next()) {

			// retrieve data from result set row
			String co_code = myRs.getString("co_code");
			String reg_code = myRs.getString("reg_code");
			String reg_name = myRs.getString("reg_name");
			String reg_desc = myRs.getString("reg_desc");

			// create new student object
			Region region = new Region(co_code, reg_code, reg_name, reg_desc);

			regions.add(region);
		}
		myConn.close();
		return regions;

	}
	public boolean addRegion(Region region) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		conn = mysqlDS.getConnection();
		String sql = "INSERT INTO region (co_code,reg_code,reg_name,reg_desc) VALUES (?,?,?,?);";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, region.getCo_code());
		stmt.setString(2, region.getReg_code());
		stmt.setString(3, region.getReg_name());
		stmt.setString(4, region.getReg_desc());

		return stmt.executeUpdate() > 0;

	}
	public boolean addCity(City city) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		conn = mysqlDS.getConnection();
		String sql = "INSERT INTO city (cty_code,co_code,reg_code,cty_name,population,areaKM,isCoastal) VALUES (?,?,?,?,?,?,?);";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, city.getCty_code());
		stmt.setString(2, city.getCo_code());
		stmt.setString(3, city.getReg_code());
		stmt.setString(4, city.getCty_name());
		stmt.setInt(5, city.getPopulation());
		stmt.setDouble(6, city.getAreaKM());
		stmt.setString(7, city.getIsCoastal());

		return stmt.executeUpdate() > 0;

	}
	public ArrayList<City> loadCities() throws Exception {
		ArrayList<City> cities = new ArrayList<City>();
		Connection myConn = mysqlDS.getConnection();
		PreparedStatement myStmt = myConn.prepareStatement("select * from city;");
		ResultSet myRs = myStmt.executeQuery();

		// process result set
		while (myRs.next()) {

			// retrieve data from result set row
			String cty_code = myRs.getString("cty_code");
			String co_code = myRs.getString("co_code");
			String reg_code = myRs.getString("reg_code");
			String cty_name = myRs.getString("cty_name");
			int population = myRs.getInt("population");
			double areaKM = myRs.getDouble("areaKM");
			String isCoastal = myRs.getString("isCoastal");

			// create new student object
			City city = new City();
			city.setAreaKM(areaKM);
			city.setCo_code(co_code);
			city.setReg_code(reg_code);
			city.setCty_name(cty_name);
			city.setCty_code(cty_code);
			city.setIsCoastal(isCoastal);
			city.setPopulation(population);
			cities.add(city);
		}
		myConn.close();
		return cities;

	}

	public City loadCity(String ID) throws Exception {
		City city = new City();

		Connection myConn = mysqlDS.getConnection();

		// String sql = "SELECT
		// c.cty_code,c.cty_name,c.population,c.isCoastal,c.areaKM,ctry.co_name,rg.reg_name
		// FROM city c INNER JOIN region rg on c.co_code = rg.co_code INNER JOIN country
		// ctry on rg.co_code = ctry.co_code where cty_code = 'ALB';";
		String sql = "select * from city where cty_code = ?;";
		PreparedStatement myStmt = myConn.prepareStatement(sql);
		myStmt.setString(1, ID);
		ResultSet myRs = myStmt.executeQuery();
		// process result set
		while (myRs.next()) {
			// retrieve data from result set row
			String cty_code = myRs.getString("cty_code");
			String co_code = myRs.getString("co_code");
			String reg_code = myRs.getString("reg_code");
			String cty_name = myRs.getString("cty_name");
			int population = myRs.getInt("population");
			double areaKM = myRs.getDouble("areaKM");
			String isCoastal = myRs.getString("isCoastal");

			city = new City();
			city.setAreaKM(areaKM);
			city.setCo_code(co_code);
			city.setReg_code(reg_code);
			city.setCty_name(cty_name);
			city.setCty_code(cty_code);
			city.setIsCoastal(isCoastal);
			city.setPopulation(population);
		}
		myConn.close();
		return city;

	}
	
	public ArrayList<City> SearchBluewave(String key,String coast,String compare, int comparePopulation) throws SQLException {
		Connection myConn = mysqlDS.getConnection();
        ArrayList<City> list = new ArrayList<City>();
        if(comparePopulation == 0) {
        	 String sql = "SELECT * FROM CITY INNER JOIN COUNTRY ON COUNTRY.CO_CODE = CITY.CO_CODE INNER JOIN REGION ON CITY.REG_CODE = REGION.REG_CODE WHERE "
             		+ "CITY.CO_CODE LIKE ? AND CITY.isCoastal LIKE ?";
        	 try {
                 PreparedStatement stmt = myConn.prepareStatement(sql);
                 stmt.setString(1, "%"+key+"%");
                 stmt.setString(2, "%"+coast+"%");
                 ResultSet rs = stmt.executeQuery();
                 while (rs.next()) {
                 	String cty_code = rs.getString("cty_code");
         			String co_code = rs.getString("co_code");
         			String reg_code = rs.getString("reg_code");
         			String cty_name = rs.getString("cty_name");
         			int population = rs.getInt("population");
         			double areaKM = rs.getDouble("areaKM");
         			String isCoastal = rs.getString("isCoastal");
         			String co_name = rs.getString("co_name");
         			String reg_name = rs.getString("reg_name");
                     City b = new City();
                    b.setCo_name(co_name);
                    b.setAreaKM(areaKM);
                    b.setCo_code(co_code);
                    b.setCty_code(cty_code);
                    b.setIsCoastal(isCoastal);
                    b.setPopulation(population);
                    b.setReg_code(reg_code);
                    b.setCty_name(cty_name);
                    b.setReg_name(reg_name);
                    
                     list.add(b);
                 }
             } catch (SQLException e) {
                 e.printStackTrace();
             }
             myConn.close();
             return list;
        }else {

        String sql = "SELECT * FROM CITY INNER JOIN COUNTRY ON COUNTRY.CO_CODE = CITY.CO_CODE INNER JOIN REGION ON CITY.REG_CODE = REGION.REG_CODE WHERE "
        		+ "CITY.CO_CODE LIKE ? AND CITY.isCoastal LIKE ? AND CITY.population "+compare+" "+comparePopulation;
        try {
            PreparedStatement stmt = myConn.prepareStatement(sql);
            stmt.setString(1, "%"+key+"%");
            stmt.setString(2, "%"+coast+"%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	String cty_code = rs.getString("cty_code");
    			String co_code = rs.getString("co_code");
    			String reg_code = rs.getString("reg_code");
    			String cty_name = rs.getString("cty_name");
    			int population = rs.getInt("population");
    			double areaKM = rs.getDouble("areaKM");
    			String isCoastal = rs.getString("isCoastal");
    			String co_name = rs.getString("co_name");
    			String reg_name = rs.getString("reg_name");
                City b = new City();
               b.setCo_name(co_name);
               b.setAreaKM(areaKM);
               b.setCo_code(co_code);
               b.setCty_code(cty_code);
               b.setIsCoastal(isCoastal);
               b.setPopulation(population);
               b.setReg_code(reg_code);
               b.setCty_name(cty_name);
               b.setReg_name(reg_name);
               
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myConn.close();
        return list;
        }
        
    }
	
	public ArrayList<Country> loadCountries() throws Exception {
		ArrayList<Country> countries = new ArrayList<Country>();
		Connection myConn = mysqlDS.getConnection();
		PreparedStatement myStmt = myConn.prepareStatement("select * from country;");
		ResultSet myRs = myStmt.executeQuery();

		// process result set
		while (myRs.next()) {

			// retrieve data from result set row
			String co_code = myRs.getString("co_code");
			String co_name = myRs.getString("co_name");
			String co_details = myRs.getString("co_details");

			// create new student object
			Country country = new Country(co_code, co_name, co_details);

			countries.add(country);
		}
		myConn.close();
		return countries;

	}
	
	public Country loadCountry(String ID) throws Exception {
		Country country = null;
			Connection myConn = mysqlDS.getConnection();
			String sql = "select * from country where co_code = ?";

			PreparedStatement myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, ID);
			ResultSet myRs = myStmt.executeQuery();

			// process result set
			myRs.next();
				country = new Country();
				// retrieve data from result set row
				country.setCo_code(myRs.getString("co_code"));
				country.setCo_name(myRs.getString("co_name"));
				country.setCo_details(myRs.getString("co_details"));
			
			
			return country;
		}
		public boolean updateCountry(Country country) throws Exception{
			Connection conn = null;
			PreparedStatement stmt = null;
			conn = mysqlDS.getConnection();
			String sql = "update country set co_name=?,co_details=? where co_code=?";
			
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1,country.getCo_name());
			stmt.setString(2,country.getCo_details());
			stmt.setString(3,country.getCo_code());
			
			
			return stmt.executeUpdate()>0;
			
			            
	    }
		
	
	public boolean addCountry(Country country) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		conn = mysqlDS.getConnection();
		String sql = "INSERT INTO country (co_code,co_name,co_details) VALUES (?,?,?);";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, country.getCo_code());
		stmt.setString(2, country.getCo_name());
		stmt.setString(3, country.getCo_details());

		return stmt.executeUpdate() > 0;

	}

	public Country deleteCountry(String ID) throws SQLException {
		Country country = new Country();
		Connection myConn = mysqlDS.getConnection();
		String sql = "delete  from country where co_code = ?";

		PreparedStatement myStmt = myConn.prepareStatement(sql);
		myStmt.setString(1, ID);
		myStmt.executeUpdate();
		return country;
	}

	

	
	
}
