package loveholidays.techchallenge.tests;

import org.junit.Test;
import org.mockito.Mockito;
import junit.framework.TestCase;
import loveholidays.techchallenge.Address;
import loveholidays.techchallenge.RESTPostcodeService;

public class RESTPostcodeServiceTest extends TestCase{
	
	private Address[] address = new Address[1];
	private Address[] addressNull = null;
	private RESTPostcodeService restPostcodeServiceTest;
    private RESTPostcodeService restPostcodeServiceMockito;


	
	@Override
	protected void setUp() throws Exception {
		
		restPostcodeServiceTest = new RESTPostcodeService();
		//create mock 
		restPostcodeServiceMockito = Mockito.spy(RESTPostcodeService.class);
		
        address[0] = new Address();
        address[0].setSummaryline(
        		"Loveholidayscom, The Triangle, 5-17 Hammersmith Grove, London, Greater London, W6 0LG");
        address[0].setOrganisation("Loveholidayscom");
        address[0].setPremise("The Triangle, 5-17");
        address[0].setStreet("Hammersmith Grove");
        address[0].setPosttown("London");
        address[0].setCounty("Greater London");
        address[0].setPostcode("W6 0LG");
	}

	
	@Test
	public void testBuildURI() {
		restPostcodeServiceTest.buildURI("W6 0LG");
		
        assertEquals(restPostcodeServiceTest.getURI(), 
        		"http://ws.postcoder.com/pcw/PCW7T-GVJN6-5CRYN-HDJSU/address/UK/W6+0LG?format=json");
	}
	
	@Test
	public void testBuildURINoSpace() {
		restPostcodeServiceTest.buildURI("W60LG");

		assertEquals(restPostcodeServiceTest.getURI(), 
        		"http://ws.postcoder.com/pcw/PCW7T-GVJN6-5CRYN-HDJSU/address/UK/W60LG?format=json");
	}
	
	@Test
	public void testBuildURINoString() {
		restPostcodeServiceTest.buildURI("");

		assertEquals(restPostcodeServiceTest.getURI(), 
        		"http://ws.postcoder.com/pcw/PCW7T-GVJN6-5CRYN-HDJSU/address/UK/?format=json");
	}
	
	@Test
	public void testGetAddressForPostcode() {
		
        // define return value for method getURI()
        Mockito.when(restPostcodeServiceMockito.getURI()).thenReturn(
        		"http://ws.postcoder.com/pcw/PCW7T-GVJN6-5CRYN-HDJSU/address/UK/W60+LG?format=json");
        // mock the API call       
        Mockito.doReturn(address).when(restPostcodeServiceMockito).callRestService();
        
        //test using mocked API call response
        assertEquals(restPostcodeServiceMockito.getAddressForPostcode("W6 0LG"), 
        		"W6 0LG results : <br />Loveholidayscom, The Triangle, 5-17 Hammersmith Grove<br />");
	}
	
	@Test
	public void testGetAddressForPostcodeNoURI() {
		
        // define return value for method getURI()l empty string to simulate buildURI failure
        Mockito.when(restPostcodeServiceMockito.getURI()).thenReturn(
        		"");
        // mock the API call       
        Mockito.doReturn(address).when(restPostcodeServiceMockito).callRestService();
        
        //test using mocked API call response
        assertEquals(restPostcodeServiceMockito.getAddressForPostcode("W6 0LG"), 
        		"Failed to call REST service :W6 0LG");
	}
	
	@Test
	public void testGetAddressForPostcodeAPINotAvailable() {
		
        // define return value for method getURI()
        Mockito.when(restPostcodeServiceMockito.getURI()).thenReturn(
        		"http://ws.postcoder.com/pcw/PCW7T-GVJN6-5CRYN-HDJSU/address/UK/W60+LG?format=json");
        // mock the API call, return null to simulate no response       
        Mockito.doReturn(addressNull).when(restPostcodeServiceMockito).callRestService();

        //test using mocked API call response
        assertEquals(restPostcodeServiceMockito.getAddressForPostcode("W6 0LG"), 
        		"W6 0LG results : <br />Failed to get address");
	}
	
	@Test
	public void testBuildResponse(){
		assertEquals(restPostcodeServiceTest.buildResponse(address, "W6 0LG"), 
				"W6 0LG results : <br />Loveholidayscom, The Triangle, 5-17 Hammersmith Grove<br />");
	}
	
	@Test
	public void testBuildResponseNoSpace(){
		assertEquals(restPostcodeServiceTest.buildResponse(address, "W60LG"), 
				"W60LG results : <br />Loveholidayscom, The Triangle, 5-17 Hammersmith Grove<br />");
	}
	
	@Test
	public void testBuildResponseAddressNull(){
		assertEquals(restPostcodeServiceTest.buildResponse(addressNull, "W60LG"), 
				"W60LG results : <br />Failed to get address");
	
	}
	
	@Override
	    protected void tearDown() throws Exception {
	        address = null;
	        restPostcodeServiceTest = null;
	        restPostcodeServiceMockito = null;
	    }
	 



}
