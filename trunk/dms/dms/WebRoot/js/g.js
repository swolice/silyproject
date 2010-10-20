var map;
var bounds;
var count=0;
	  function initialize() {
    var lat=3990872/100000;
    var lon=11639728/100000;
    var latlng = new google.maps.LatLng(lat,lon);
    var myOptions = {
      zoom: 12,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
    bounds = new google.maps.LatLngBounds();
    
    google.maps.event.addListener(map, "dragend", function() {
				search(map.getBounds())
			})
    
   // google.maps.event.addListener(map, "zoom_changed", function() {
		//		search(map.getBounds())
		//	})
    
  }
  
  function search(rect){
  	//alert(rect.getNorthEast().lat()+","+rect.getNorthEast().lng());
  	//alert(rect.getSouthWest().lat()+","+rect.getSouthWest().lng());
//  	document.searchForm.mlat.value=rect.getNorthEast().lat();
  //	document.searchForm.mlon.value=rect.getNorthEast().lng();
  	//document.searchForm.nlat.value=rect.getSouthWest().lat();
  	//document.searchForm.nlon.value=rect.getSouthWest().lng();

		map.clearMarkers();
		jQuery("#main_left_map").empty();
		jQuery("#main_left_map").load("index.do?method=rangeSearch",{
			'mlat':rect.getNorthEast().lat()*100000,
			'mlon':rect.getNorthEast().lng()*100000,
			'nlat':rect.getSouthWest().lat()*100000,
			'nlon':rect.getSouthWest().lng()*100000,
			'key':document.searchForm.key.value,
			'category':document.searchForm.category.value,
			'dd':document.searchForm.dd.value,
			'pd':document.searchForm.pd.value
		});
  }
  
  function addPoint(latlon,icon,title){
  	  var marker = new google.maps.Marker({
      position: latlon,  
      map: map,  
      icon:icon,
      title:title
    });
    return marker;
  }
  
  function addInfoWindow(marker,info){
  	 var infowindow = new google.maps.InfoWindow({
                        maxWidth: 400
                    });

     google.maps.event.addListener(marker, "click", function() {
        infowindow.setContent(info);
        infowindow.open(map, marker);
     });
     
      google.maps.event.addListener(infowindow, 'domready', function () {
				var p1 = jQuery('.map_items').parent();
				p1.css('overflow', 'visible');
		var p2 = p1.parent();
		p2.css('overflow', 'visible');
   }); 
     
                    
     return infowindow;
  }
  
  
function moveToMarkerAndShowInfo(marker){
if(marker){
this.map.setCenter(marker.getPosition());
this.map.setZoom(12);
google.maps.event.trigger(marker,'click');
}
}