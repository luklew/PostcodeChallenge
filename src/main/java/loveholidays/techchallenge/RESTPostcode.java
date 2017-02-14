package loveholidays.techchallenge;

public interface RESTPostcode {

	public String buildURI(String searchTerm);
	
	public String getAddressForPostcode(String searchTerm);
	
	public String buildResponse(Address[] address, String searchTerm);
	
	public Address[] callRestService();
	
	public String getURI();
	
	public void setURI(String uri);
}
