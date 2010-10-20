package util.google;

public class DependentLocality { 
	  
    private String[] AddressLine; 
    private String DependentLocalityName ; 
    private Thoroughfare Thoroughfare; 
     
     
    public String[] getAddressLine() { 
            return AddressLine; 
    } 

    public void setAddressLine(String[] addressLine) { 
            AddressLine = addressLine; 
    } 

    public Thoroughfare getThoroughfare() { 
            return Thoroughfare; 
    } 

    public void setThoroughfare(Thoroughfare thoroughfare) { 
            Thoroughfare = thoroughfare; 
    } 

    public String getDependentLocalityName() { 
            return DependentLocalityName; 
    } 

    public void setDependentLocalityName(String dependentLocalityName) { 
            DependentLocalityName = dependentLocalityName; 
    } 
} 
