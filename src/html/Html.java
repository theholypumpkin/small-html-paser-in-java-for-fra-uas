package html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import jdk.incubator.http.HttpResponse.*;

public class Html {

 /** @author Pascal S. SENSITIVE INFORMATION REDACTED
  * Created on 20 May 2019
  * The program extracts the search result from the webpage 
  * https://www.frankfurt-university.de and prints it out.
  * It reads in the text using the Buffered Reader and creates HTTP clients
  * open the website.
  * @param args default input, not used.
  */
 public static void main(String[] args)
 {	
	 /*The static values for the program*/
	 final String url = "https://www.frankfurt-university.de/de/"
	 + "suche/suchergebnisse/?&q=";
	 final String condition = "lieferte <span class=\"search-result__text"
	 + " color-blue\">";
	 
	 BufferedReader in = new BufferedReader(new InputStreamReader(
     System.in));
	 
	 System.out.println("What do you want to search? ");
	
	 //String user;
	 try {
		String user = in.readLine();
		
		//Creates Object of type HttpRequest to request the Website.
		//Using URI (Uniform Resource Identifier) to create the request
		 HttpRequest re  = HttpRequest.newBuilder(
		 	URI.create( url + user )).build();
		 //Creates HTTP Client
		 HttpClient ht = HttpClient.newHttpClient();

		 	/*The whole html page will be saved as a "String"*/
		 
		 	HttpResponse <String> reply = ht.send(re, 
		 		BodyHandler.asString());
		 	
		 	String result = reply.body();
		 	
		 	/*finds the  a big chunck of html code which contains the search
		 	 * result within the 
		 	 * html page and returns the index of it*/
		 	int index = result.indexOf(condition) + condition.length();
		 	
		 	/*Extracts the html class with the search result and keeps up to
		 	 * 10 character/digits behind it this is a a static flaw for really
		 	 * big search results but in my testing not more than 4 digit
		 	 * numbers where returned*/
		 	
		 	String sub = (String) result.subSequence( index, index+10 );
		 	/*Removes the remaining text from the result to only get the 
		 	 * numbers*/
		 	sub = sub.replaceAll( "[^0-9]+", "" );
		 	
		 	System.out.println( sub );
	 		}
	 	catch (IOException | InterruptedException e) {
	 		e.printStackTrace();
	 	}
 	}
 }