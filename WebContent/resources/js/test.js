/**
 * 
 */
$(document).ready(function() {
	
	$("#p1").click(function() {
		$.ajax({
	    	  
    	    // The URL for the request
    	    url: "invigilation/getajax",
    	 
    	    // The data to send (will be converted to a query string)
    	    data: {"id":"123", "name":"111111"},
    	    // Whether this is a POST or GET request
    	    type: "GET",
    	  
    	    // The type of data we expect back
    	    dataType : "html",
    	    
    	    beforeSend: function(jqXHR){
    	    	$("#mydiv").html("<img src='resources/images/loading.gif' />"); 
           }, 
           
           
    	    // Code to run if the request succeeds;
    	    // the response is passed to the function
    	    success: function(json ) {
    	        $( "#mydiv" ).html(json);
    	    },
    	 
    	    // Code to run if the request fails; the raw request and
    	    // status codes are passed to the function
    	    error: function( xhr, status, errorThrown ) {
    	        alert( "Status: " + status + "; Error: " + errorThrown);
    	        console.log( "Error: " + errorThrown );
    	        console.log( "Status: " + status );
    	        console.dir( xhr );
    	    },
    	 
    	    // Code to run regardless of success or failure
    	    /*complete: function( xhr, status ) {
    	        alert( "The request is complete!" );
    	    }*/
    	});
		
	});
         
	/*$("#p1").click(function() {
		$("#mydiv").load("ajax/load.jsp", function(responseTxt,statusTxt,xhr){
		      if(statusTxt=="success")
		          alert("外部内容加载成功！");
		        if(statusTxt=="error")
		          alert("Error: "+xhr.status+": "+xhr.statusText);
		      });
		      	});*/
});