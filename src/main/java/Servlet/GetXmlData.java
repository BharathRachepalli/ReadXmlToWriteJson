package Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Services.FetchXmlData;

/**
 * Servlet implementation class FetchXmlData
 */
@WebServlet("/GetXmlData")
public class GetXmlData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		
		String var = "";
		String xml = "";
		while ((var = br.readLine()) != null) {
            xml = xml+var+"\n";
        }
//		System.out.println(xml);
		new FetchXmlData().getBookData(xml);
		
	}

}
