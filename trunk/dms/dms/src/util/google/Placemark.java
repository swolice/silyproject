package util.google;

public class Placemark { 
	  
    private String id; 
    private String address; 
    private AddressDetails AddressDetails; 
    private ExtendedData ExtendedData; 
    private Point Point; 
    public String getId() { 
            return id; 
    } 
    public void setId(String id) { 
            this.id = id; 
    } 
    public String getAddress() { 
            return address; 
    } 
    public void setAddress(String address) { 
            this.address = address; 
    } 
    public AddressDetails getAddressDetails() { 
            return AddressDetails; 
    } 
    public void setAddressDetails(AddressDetails addressDetails) { 
            AddressDetails = addressDetails; 
    } 
    public ExtendedData getExtendedData() { 
            return ExtendedData; 
    } 
    public void setExtendedData(ExtendedData extendedData) { 
            ExtendedData = extendedData; 
    } 
    public Point getPoint() { 
            return Point; 
    } 
    public void setPoint(Point point) { 
            Point = point; 
    } 
     
} 