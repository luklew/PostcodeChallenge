package loveholidays.techchallenge;

import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class RESTPostcodeService implements RESTPostcode{
	
    private RestTemplate restTemplate;
	
	//Unique search key (user account) required to send REST call
    private String searchKey;          
    //Sets the search method (Look for address)
    private String method; 
    //Contains the postcode to search for address
    private String searchTerm; 
    //Sets the return format to JSON
    private String format;   
    //Contains the full correct URI ready for REST call
    private String uri;
	
    public RESTPostcodeService() {
    	
    	restTemplate = new RestTemplate();
    	
    	searchKey = "PCW7T-GVJN6-5CRYN-HDJSU";          
        method = "address"; 
        searchTerm = ""; 
        format = "json";   
        uri = "";
    	
	}

    /*
     * Builds the correct URI required to make the REST call
     */
	@Override
	public String buildURI(String searchTerm) {
		
		this.searchTerm = searchTerm;

		try {
			uri = String.format("http://ws.postcoder.com/pcw/%s/%s/UK/%s?format=%s", searchKey, method,
					java.net.URLEncoder.encode(this.searchTerm, "UTF-8"), format);
		} catch (UnsupportedEncodingException e) {
			uri = "";
			System.out.println("Failed to build string");
			e.printStackTrace();
		}
		
		return uri;
		
	}
	
	/*
	 * Gets name and street address for the postcode - searchTerm
	 */
	@Override
	public String getAddressForPostcode(String searchTerm) {

		Address[] address = null;
		String formatedResponse = "";

		buildURI(searchTerm);
		
		if (getURI() != "") {
			
			address = callRestService();
			
		} else {
			
			return "Failed to call REST service :" + searchTerm;
		
		}
		
		formatedResponse = buildResponse(address, searchTerm);

		return formatedResponse;
	}

	
	/*
	 * Builds the response string which contains the search results, 
	 * this string will be sent to the browser (simple HTML),
	 * if no results or error found response = ERROR
	 */
	@Override
	public String buildResponse(Address[] address, String searchTerm) {
		
		String response = "";
		
		response += searchTerm + " results : <br />";
		if (address != null) {
			for (Address a : address) {
				if (a.getOrganisation() != null)
					response += a.getOrganisation() + ", ";

				if (a.getPremise() != null)
					response += a.getPremise() + " ";

				if (a.getStreet() != null)
					response += a.getStreet() + "<br />";
			}
		} else {
			response += "Failed to get address";
		}
		
		return response;
	}
	
	public Address[] callRestService(){
		
		Address[] address = null;
		
		try {
			address = restTemplate.getForObject(getURI(), Address[].class);
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() != HttpStatus.NOT_FOUND) {
				System.out.println("ERROR : " + ex.getStatusCode());
			} else {
				
				ex.printStackTrace();
				
			}
		}

		return address;
		
	}
	
	/*
	 * Returns the built URI
	 */
	@Override
	public String getURI() {
		return uri;
	}

	/*
	 * Sets the URI 
	 */
	@Override
	public void setURI(String uri) {
		this.uri = uri;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
}
