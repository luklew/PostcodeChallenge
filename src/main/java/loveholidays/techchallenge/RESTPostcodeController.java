package loveholidays.techchallenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postcode")
public class RESTPostcodeController {
	    
    @Autowired
    private RESTPostcodeService postcodeService;         
	
    @RequestMapping(value="", method=RequestMethod.GET)
    public String postcode() {
    	
    	String response = "";

    	/*
    	 * Calling the API to search address for each required postcode,
    	 * on exception print out HTTP status code to console
		 * then building response string using returned JSON mapped onto Address object
		 */
    	response += postcodeService.getAddressForPostcode("W6 0LG");		        
        response += "<br /><br />";
        response += postcodeService.getAddressForPostcode("SW1A 2AA");
		response += "<br /><br />";
		response += postcodeService.getAddressForPostcode("BT48 6DQ");
		     		
        return response;
    }
    
    @RequestMapping(value="/{searchTerm}", method=RequestMethod.GET)
    public String postcode(@PathVariable String searchTerm) {
    	
    	String response = "";
    	
		response += postcodeService.getAddressForPostcode(searchTerm);
    	
        return response;
    }

}
