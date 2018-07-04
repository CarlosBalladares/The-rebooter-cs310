package com.cs310.TheRebooter.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cs310.TheRebooter.client.BusinessService;
import com.cs310.TheRebooter.shared.Business;
import com.cs310.TheRebooter.shared.BusinessServiceResponse;
import com.cs310.TheRebooter.shared.Coordinate;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class BusinessServiceImpl extends RemoteServiceServlet implements BusinessService{

	DataManager manager;

	@Override
	public BusinessServiceResponse getBusinessesInVicinity(BusinessServiceResponse resp) {
		manager = new DataManager();
		
		try {
			
			
			List<Business> businesses = manager.getBusinessesInVicinity(resp.getCoord(), resp.getDistance());
			
			resp.setBusinesses(businesses);
			
			System.out.println("Received data");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return resp;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Business> businesses=null;
		PrintWriter out=resp.getWriter();
		manager = new DataManager();
		
		try {
			
			
			businesses = manager.getBusinessesInVicinity(new Coordinate(49.2827,123.1207),0.05);
			
			
			System.out.println("Received data");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print(e.getStackTrace().toString()+"sql ");
			
		}
		if(businesses==null){
			out.print("businesses are null");
		}else{
			for(Business b : businesses)
				if(b.getBusinessName()!=null)
					out.print(b.getBusinessName());
		}
		
	}
	
}
