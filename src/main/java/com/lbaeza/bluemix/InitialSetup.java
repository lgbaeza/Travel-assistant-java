package com.lbaeza.bluemix;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.ibm.watson.developer_cloud.document_conversion.v1.model.Answers.AnswerUnits;
import com.lbaeza.utils.Utils;
import com.lbaeza.watson.WatsonClient;
/**
 * Servlet implementation class InitialSetup
 */
@WebServlet({"/DestinationSetUp", "/ItinerarySetUp"})
@MultipartConfig
public class InitialSetup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitialSetup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI().substring(request.getContextPath().length());
	    HttpSession session = request.getSession(true);

		RequestDispatcher view;
		if(path.equals("/DestinationSetUp")){
			
			
			request.setCharacterEncoding("UTF-8");
			String dest = request.getParameter("dest");
			
			request.removeAttribute("itinerary");
			session.setAttribute("destination", dest);
			session.removeAttribute("itinerary");
			
			view = request.getRequestDispatcher("itinerary.jsp");
			
		}
		else{ // "ItinerarySetUp"
			Part filePart = request.getPart("file");
			
			String fileName = Utils.getFileName(filePart);
			InputStream fileContent = filePart.getInputStream();
			
		    List<AnswerUnits> data = WatsonClient.convertDocument(fileContent, fileName);
		    
		    String itinerary = "";
	    	String goingfligth = "";
	    	String returningFlight = "";
	    	String lodgement = "";
	    	String hotelAddress = "";
	    	String hotelPhone = "";
	    	String tripDate = "";

	    	System.out.println(data.get(0).getTitle());
	    	System.out.println(data.get(0).getContent().get(0).getText());
	    	
	    	for(AnswerUnits awu : data)
	    	{
	    		switch(awu.getTitle())
	    		{
	    		case "Número de Reservación":  itinerary = awu.getContent().get(0).getText();
	    			break;
	    		case "FECHA DE VIAJE": tripDate = awu.getContent().get(0).getText();
	    			break;
	    		case "VUELO DE IDA": goingfligth = awu.getContent().get(0).getText();
	    			break;
	    		case "VUELO DE VUELTA": returningFlight = awu.getContent().get(0).getText();
	    			break;
	    		case "HOSPEDAJE": lodgement = awu.getContent().get(0).getText();
	    			break;
	    		case "Dirección hotel": hotelAddress = awu.getContent().get(0).getText();
	    			break;
	    		case "Teléfono hotel": hotelPhone = awu.getContent().get(0).getText();
	    			break;
	    		}
	    	}
			    
			session.setAttribute("itinerary", itinerary);
			session.setAttribute("goingFlight", goingfligth);
			session.setAttribute("returningFlight", returningFlight);
			session.setAttribute("lodgement", lodgement);
			session.setAttribute("hotelAddress", hotelAddress);
			session.setAttribute("hotelPhone", hotelPhone);
			session.setAttribute("tripDate", tripDate);
						
			view = request.getRequestDispatcher("itinerary.jsp");
		}
		
		view.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	

}
