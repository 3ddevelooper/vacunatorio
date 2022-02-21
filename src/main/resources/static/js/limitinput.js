function soloLetras(e) {
    tecla = (document.getElementById) ? e.keyCode : e.which;

    if (tecla == 8) {
        return true;
    }
    patron = /^[a-zA-Z\ áéíóúÁÉÍÓÚñÑ\s]*$/;
    tecla_final = String.fromCharCode(tecla);
    return patron.test(tecla_final);
}

function soloNumeros(e) {
    tecla = (document.getElementById) ? e.keyCode : e.which;

    if (tecla == 8 || tecla==13) {
        return true;
    }
    patron = /^[0-9]$/;
    tecla_final = String.fromCharCode(tecla);
    return patron.test(tecla_final);
}

