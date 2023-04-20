<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="utenteCorrente" scope="session" class="beans.Manager" />

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
	<div class="formIniziale">
		<form onsubmit="return check()" name="modificaManager"
			action="./modificaProfilo" method="post">


			Nome Utente <br> <br> <input type="text" name="nome_utente"
				value="${utenteCorrente.nome}" required /> <br> <br>

			Password <br>
			<br> <input type="password" name="password"
				value="${utenteCorrente.password}" required /> <br>
			<br> <input type="submit" value="Submit" />

		</form>
	</div>
</body>
</html>

