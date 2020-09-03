package base;

import java.util.List;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class PageBase  {
	//Define the Object Repistory 
	
	
	//Define the methods
	//the first method is a constrator to the class Page
	public PageBase()  {}
	
	
	//Variables

	
    @SuppressWarnings("unchecked")
	public List<String> getControlsDetails(WebDriver driver)
	{
	     List<String> List;
		 List = (List<String>) 
				((JavascriptExecutor) driver).executeScript(" var allElements = document.getElementsByTagName('*'); "
					  + "var allIds = [];"
		              + " for (var i = 0, n = allElements.length; i < n; ++i) "
		              + "{ "
		              + " var el = allElements[i]; "
		              + " if (el.id) "
		                 + "{ allIds.push('@ID:' + el.id + ' - @Name:' + el.name + '- @Title:' + el.title + '- @Class: ' + el.class ); } "
		              + "}"
		              + "return allIds; ");
		    System.out.println("IDs: " + List);
		    System.out.println("========================: =========  ===========  ============   ============ ");
		    System.out.println("      Control NO        : ControlID  ControlName  ControlTitle   ControlClass ");
		    System.out.println("========================: =========  ===========  ============   ============ ");
		    String Header;
		    
		    for (int i=0; i<List.size(); i++)
		    { 	
		    	Header = "Control: (";
		    	if (i<9)
		    		{Header = Header + "0"+ Integer.valueOf(i+1) +") of (" + List.size() + ") : ";}
		    	else
		    	    {Header = Header + Integer.valueOf(i+1) +") of (" + List.size() + ") : " ;}
		    	
		    	System.out.println(Header + List.subList(i, i+1) );	    
		    }
		    System.out.println("======================================================================================== ");
			return List;
	}
    
}
