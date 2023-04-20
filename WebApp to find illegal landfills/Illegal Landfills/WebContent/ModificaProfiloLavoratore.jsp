<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="utenteCorrente" scope="session"
	class="beans.Lavoratore" />

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>Modifica Profilo</title>
<link href="style.css" rel="stylesheet" type="text/css">

</head>
<body>
<c:if test="${empty utenteCorrente.nome}">
		<script>
    window.addEventListener("load",function(){
         alert("Non sei autorizzato, vai al login");
    })
    window.location.replace("./Login.html");

    </script>
	</c:if>
	<br>
	<div class="formIniziale">
		<img width="70" height="70"
			src="/ImmaginiProfilo/${utenteCorrente.lavoratoreID}${utenteCorrente.estensione}" />
		<br>
		<button id="cambiaImg" onclick="mostraCambioImg()">Voglio
			cambiare immagine del profilo</button>
		<br>
		<br>

		<form onsubmit="return check()" name="modificaLavoratore"
			action="./modificaProfiloLavoratore" method="post"
			enctype='multipart/form-data'>

			<div id="cambioImgDiv">
				<input type="file" name="imgProfilo" default="null" />
			</div>
			<br> Nome Utente <br> <input type="text" name="nome_utente"
				value="${utenteCorrente.nome}" required /> <br> Password <br>
			<input type="password" name="password"
				value="${utenteCorrente.password}" required /> <br> <input
				type="hidden" name="cambioImg" value="true"> <br> <input
				type="submit" value="Submit" />

		</form>
	</div>
</body>
<script> function check() {
	var x= document.forms["modificaLavoratore"]["nome_utente"].value;
	if (x=="" || x.length < 3 || x.length> 20) {alert("Username non ben formato"); return false;}
	for (var i=0; i< x.length; i++) {if (x.charAt(i)==" ") {alert("Username non ben formato - non si possono inserire spazi"); return false;}}

	var z= document.forms["modificaLavoratore"]["password"].value;
	if (z=="" || z.length < 3 || z.length> 20) {alert("Password non ben formata"); return false;}
	for (var i=0; i< z.length; i++) {if (z.charAt(i)==" ") {alert("Password non ben formata - non si possono inserire spazi"); return false;}}
		
	
			var y=document.getElementsByName("imgProfilo")[0].value;
			if (!y)
			 document.forms["modificaLavoratore"]["cambioImg"].value="false";
			return true;
		}
		
function mostraCambioImg() {
	var f=document.getElementById("cambioImgDiv");
	f.style.display="block";
}


</script>
</html>

