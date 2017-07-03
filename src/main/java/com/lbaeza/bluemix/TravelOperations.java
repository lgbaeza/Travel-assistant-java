package com.lbaeza.bluemix;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lbaeza.utils.TravelInfo;
import com.lbaeza.watson.ApiClient;
import com.lbaeza.watson.WMessage;
import com.lbaeza.watson.WatsonClient;

/**
 * Servlet implementation class TravelOperations
 */
@WebServlet({"/travel", "/sendMessageWatson", "getRecordatorios", "getInstrucciones"})
public class TravelOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TravelOperations() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI().substring(request.getContextPath().length());
		
		HttpSession session = request.getSession(true);
		String dest = (String)session.getAttribute("destination");
				
		if(path.equals("/travel") && request.getParameter("reset") != null){
			
			//Initialize information
			String demographics = ApiClient.getDemographics(dest);
			session.setAttribute("demographics", demographics);
			
			String weather = ApiClient.getWeather(dest);
			session.setAttribute("weather", weather);
			
			String location = ApiClient.getLocation(dest);
			session.setAttribute("map_lat", location.split(",")[0]);
			session.setAttribute("map_lng", location.split(",")[1]);
			
			session.setAttribute("toDoList", new ArrayList<String>());
			session.setAttribute("toDoListHtml", "");
			
			session.setAttribute("instruccionesTxt", "false,0,0");
			
			//initialize chatbot watson
			WMessage wminitial = new WMessage();
			TravelInfo tInfo = new TravelInfo();
			tInfo.destino = dest;
			tInfo.itinerary = (String)session.getAttribute("itinerary");
			tInfo.goingFlight = (String)session.getAttribute("goingFlight");
			tInfo.returningFlight = (String)session.getAttribute("returningFlight");
			tInfo.lodgement = (String)session.getAttribute("lodgement");
			tInfo.hotelAddress = (String)session.getAttribute("hotelAddress");
			tInfo.hotelPhone = (String)session.getAttribute("hotelPhone");
			tInfo.tripDate = (String)session.getAttribute("tripDate");
			
			WMessage wm = WatsonClient.converseWatson(wminitial, tInfo);
			String watsonHistory =  wm.getHTMLFormat();
			session.setAttribute("watson_history", watsonHistory);
			session.setAttribute("last_wm", wm);
			
			response.sendRedirect("travel");
		}
		else if(path.equals("/sendMessageWatson"))
		{
			String msg = URLDecoder.decode(request.getQueryString().substring(4), "UTF-8");
			String watsonHistory = (String)session.getAttribute("watson_history");
			WMessage last_wm = (WMessage)session.getAttribute("last_wm"); 
			
			//user input
			WMessage um = new WMessage();
			um.user = true;
			um.message = msg;
			//System.out.println(msg);
			
			watsonHistory += um.getHTMLFormat();
			
			last_wm.message = request.getParameter("msg");
			
			WMessage wm = WatsonClient.converseWatson(last_wm, null);
			
			//check translate
			if(wm.message.contains("<traduccion>"))
			{				
				String idioma = wm.context.getString("idioma_t");
				String texto = wm.context.getString("traducir-texto");
				String traduccion = WatsonClient.traduceTexto(texto, idioma);
				wm.message = wm.message.replace("<traduccion>","" + traduccion + "");
			}
			
			watsonHistory += wm.getHTMLFormat();
			session.setAttribute("watson_history", watsonHistory);
			session.setAttribute("last_wm", wm);
			
			//Check tags
			if(wm.recordatorio != "" && wm.message.contains("Anotado"))
			{
				List<String> toDo = (List<String>)session.getAttribute("toDoList");
				String tdHtml = "";
				
				toDo.add(wm.recordatorio);
				
				for(String td: toDo)
				{
					tdHtml += "<li>" + td + "</li>\r\n";
				}
				
				session.setAttribute("toDoListHtml", tdHtml);
				session.setAttribute("toDoList", toDo);
			}
			else if(wm.instrucciones != "" && wm.message.contains("mapa"))
			{
				session.setAttribute("instruccionesTxt", "true," + dest + "," + wm.instrucciones);
			}
			
			response.setContentType("text/html");
	        response.getWriter().print(watsonHistory);
		}
		else if(path.equals("/getRecordatorios"))
		{
			String tdHtml = (String)session.getAttribute("toDoListHtml");
			
			response.setContentType("text/html");
	        response.getWriter().print(tdHtml);
		}
		else if(path.equals("/getInstrucciones"))
		{
			String instrucHtml = (String)session.getAttribute("instruccionesTxt");
			
			response.setContentType("text/html");
	        response.getWriter().print(instrucHtml);
		}
		else
		{
			RequestDispatcher view;
			view = request.getRequestDispatcher("travel.jsp");
			view.forward(request, response);
		}
		
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
