$(document).ready(function(){
	   
	   var today = new Date();
       var timestamp = today.setDate(today.getDate()+7)
       var fecha = new Date(timestamp);
       var dd = fecha.getDate(); //Dia con 7 dias agregado!
       var mm = fecha.getMonth()+1; //Enero is 0!
       var yyyy = fecha.getFullYear();
     
       if (dd < 10) {
	       dd = '0' + dd;
       }

       if (mm < 10) {
	       mm = '0' + mm;
       }

       today = yyyy + '-' + mm + '-' + dd;
       $("#fechaturno").attr("min",today);
		
})
	  