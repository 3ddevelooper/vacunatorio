var valor = window.location.search;
var param = new URLSearchParams(valor);
var estado = param.get("estado");
estado == null ? $("#estado").val("TODOS") : $("#estado").val(estado);