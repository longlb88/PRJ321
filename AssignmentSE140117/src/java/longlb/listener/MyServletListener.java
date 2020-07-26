/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.listener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Long Le
 */
public class MyServletListener implements ServletContextListener {

    private final String FUNCTION_MAPPING_FILE = "/WEB-INF/mappingFunction.txt";
    private final String AUTH_FUNCTIONS_FILE = "/WEB-INF/authFunctions.txt";

    private Map<String, String> loadFuntionMap(String filePath) {
	FileReader fr = null;
	BufferedReader br = null;
	Map<String, String> functionMap = null;

	try {
	    //read function mapping from file
	    fr = new FileReader(filePath);
	    br = new BufferedReader(fr);

	    while (br.ready()) {
		String row = br.readLine();
		String[] splitedStrings = row.split("=");

		String key = splitedStrings[0];
		String value = splitedStrings[1];

		if (functionMap == null) {
		    functionMap = new HashMap<>();
		}

		functionMap.put(key, value);
	    } //end of while row is not null
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(MyServletListener.class.getName()).log(Level.SEVERE, null, ex);
	} catch (IOException ex) {
	    Logger.getLogger(MyServletListener.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    try {
		if (br != null) {
		    br.close();
		}
		if (fr != null) {
		    fr.close();
		}
	    } catch (IOException ex) {
		Logger.getLogger(MyServletListener.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}

	return functionMap;
    }

    private List<String> loadAuthFuntions(String filePath) {
	FileReader fr = null;
	BufferedReader br = null;
	List<String> authFuntions = null;

	try {
	    fr = new FileReader(filePath);
	    br = new BufferedReader(fr);

	    while (br.ready()) {
		String funtion = br.readLine();

		if (authFuntions == null) {
		    authFuntions = new ArrayList<>();
		}

		authFuntions.add(funtion);
	    }
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(MyServletListener.class.getName()).log(Level.SEVERE, null, ex);
	} catch (IOException ex) {
	    Logger.getLogger(MyServletListener.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    try {
		if (br != null) {
		    br.close();
		}
		if (fr != null) {
		    fr.close();
		}
	    } catch (IOException ex) {
		Logger.getLogger(MyServletListener.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	return authFuntions;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
	ServletContext context = sce.getServletContext();
	String realPath = context.getRealPath("/");

	Map<String, String> functionMap = loadFuntionMap(realPath + FUNCTION_MAPPING_FILE);
	if (functionMap != null) {
	    context.setAttribute("FUNCTION_MAP", functionMap);
	}

	List<String> authFuntions = loadAuthFuntions(realPath + AUTH_FUNCTIONS_FILE);
	if (authFuntions != null){
	    context.setAttribute("AUTH_FUNCTIONS", authFuntions);
	}
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
