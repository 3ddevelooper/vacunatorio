function eliminar(dni){
	swal({
  title: "¿Está seguro que desea Dar de Baja su turno?",
  text: "¡Una vez dado de baja, no podrá hacer uso de este turno!",
  icon: "warning",
  buttons: {
    cancel: "Cancelar",
    success: "Aceptar"
    },
  dangerMode: true,
})
.then((OK) => {
  if (OK) {
	$.ajax({
	url:"/turno/baja?dni=" + dni,
    success: function(){
		swal("¡Su turno fue dado de baja con exito!", {
			icon: "success",
			buttons: {
				success: "Aceptar"
			}
			}).then((ok) => {
				if (ok) {
					location.href = "/";
				}
			});	
			//console.log(res);
	}
	});
   /*swal("¡Su turno fue dado de baja con exito!", {
      icon: "success",
      buttons: {
	     success: "Aceptar"
      }
    }).then((ok)=>{
	if(ok){
		location.href="/";
	}
});*/
  } else {
    swal("¡Su turno no se dio de baja!",{
	  buttons:{succes: "Aceptar"}
    });
  }
});
}



function bajaVacuna(id) {
	swal({
		title: "¿Está seguro que desea Dar de Baja la vacuna?",
		icon: "warning",
		buttons: {
			cancel: "Cancelar",
			success: "Aceptar"
		},
		dangerMode: true,
	})
		.then((ok) => {
			if (ok) {
				$.ajax({
					url: "/admin/baja-vacuna?id=" + id,
					success: function() {
						swal("¡La vacuna fue dada de baja con exito!", {
							icon: "success",
							buttons: {
								success: "Aceptar"
							}
						}).then((ok) => {
							if (ok) {
								location.href = "/admin/vacunas";
							}
						});
					}
				});
			} else {
				swal("¡La vacuna no se dio de baja!", {
					buttons: { succes: "Aceptar" }
				});
			}
		});
}


function inactivarTurno(dni) {
    swal({
        title: "¿Está seguro que desea Eliminar el turno?",
        icon: "warning",
        buttons: {
            cancel: "Cancelar",
            success: "Aceptar"
        },
        dangerMode: true,
    })
    .then((ok) => {
        if(ok) {
            $.ajax({
                url:"/admin/inactivar-turno?dni=" + dni,
                success: function(){
            	    swal("¡El turno fue eliminado con exito!", {
                        icon: "success",
                        buttons: {
                            success: "Aceptar"
                        }
                    }).then((ok)=>{
                        if(ok){
                            location.href="/admin/turnos";
                        }
                    });
                }
            });
        } else {
            swal("¡El turno no se eliminó!",{
                 buttons:{succes: "Aceptar"}
            });
        }
    });
}
