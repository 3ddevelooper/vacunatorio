
function mostrarPassword() {
	var cambio = document.getElementById("formGroupExampleInput2");

	if (cambio.type == "password") {
		cambio.type = "text";
		$('.icon').removeClass('fas fa-eye-slash').addClass('fas fa-eye');
	} else {
		cambio.type = "password";
		$('.icon').removeClass('fas fa-eye').addClass('fas fa-eye-slash');
	}
}

/*$(document).ready(function() {
	//CheckBox mostrar contraseña
	$('#ShowPassword').click(function() {
		$('#Password').attr('type', $(this).is(':checked') ? 'text' : 'password');
	});
});*/
