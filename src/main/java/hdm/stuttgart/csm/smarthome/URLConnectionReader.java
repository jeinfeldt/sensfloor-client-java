package hdm.stuttgart.csm.smarthome;

import java.net.*;
import java.io.*;


public class URLConnectionReader {
    
	public void switchLight(String state)  throws Exception {		
        URL url = new URL("http://biffel:8080/CMD?Sleeping=" + state);
        URLConnection urlConn = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
        		urlConn.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
        in.close();
    }
}
