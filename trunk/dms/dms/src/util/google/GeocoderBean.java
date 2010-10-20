package util.google;

import java.util.List; 

public class GeocoderBean { 
 
        private String name; 
        private Status Status; 
        private List<Placemark> Placemark; 
        public String getName() { 
                return name; 
        } 
        public void setName(String name) { 
                this.name = name; 
        } 
        public Status getStatus() { 
                return Status; 
        } 
        public void setStatus(Status status) { 
                Status = status; 
        } 
        public List<Placemark> getPlacemark() { 
                return Placemark; 
        } 
        public void setPlacemark(List<Placemark> placemark) { 
                Placemark = placemark; 
        } 
         
         
         
} 
