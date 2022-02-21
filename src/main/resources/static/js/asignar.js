function asignarturno(dni, fechaid) {
  
    function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + (d.getDate()+2),
        year = d.getFullYear();

    if (month.length < 2) 
        month = '0' + month;
    if (day.length < 2) 
        day = '0' + day;

    return [year, month, day].join('-');
    }
  
    let csrf_token = document.getElementById('csrf').getAttribute('content');
    let data = {
        dni         : dni,
        fechaTurno  : formatDate(fechaid),
    }
  
    fetch("/admin/asignar-turno", {
        method  : "post",
        headers : {
            'X-CSRF-TOKEN': csrf_token,
            'Content-Type': 'application/json'
        },
        body:JSON.stringify(data),
       
    })
    .then(function(res){
        return res.json();
     })
     .then(function(data) {
        if(data.error) alert("Ocurrió un error, error: "+ data.message);
        console.log(data);
     })
}

