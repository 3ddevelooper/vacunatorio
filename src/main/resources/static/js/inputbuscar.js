  function validarInput() {
     document.getElementById("btnbuscar").disabled = !document.getElementById("dnibuscar").value.length;
 }
 window.onload = validarInput();