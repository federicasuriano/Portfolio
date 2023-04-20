<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="utenteCorrente" scope="session"
	class="beans.Lavoratore" />
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<link href="style.css" rel="stylesheet" type="text/css">

</head>
<body>
	<c:if test="${empty utenteCorrente.nome}">
		<script>
			window.addEventListener("load", function() {
				alert("Non sei autorizzato, vai al login");
			})
			window.location.replace("./Login.html");
		</script>
	</c:if>
	<div class="navigation">

		<a class="buttonLogout" href="./logout">

			<div class="logout">LOGOUT</div>
		</a> <a class="buttonLogout" href="./ModificaProfiloLavoratore.jsp">

			<div class="logout">MODIFICA</div>

		</a>


	</div>
	<div class="messaggioIniziale">

		Ciao
		<c:out value="${utenteCorrente.nome}" />
		!
	</div>
	<br>
	<div class="listaCampagne">
		<div id="campagneDaScegliere">
			<b>Campagne avviate da scegliere:</b> <br>

			<c:forEach var="campagneAvviate" items="${campagneAvviate}">
				<p>
					<c:out value="${campagneAvviate.nome}" />
					<c:out value="${campagneAvviate.committente}" />
					<c:out value="${campagneAvviate.stato}" />

					<c:url
						value="/scegliCampagna?campagna=${campagneAvviate.campagnaID}"
						var="URL"></c:url>
					<a href="${URL}"> Scegli </a>
				</p>
			</c:forEach>
		</div>
		<br>
		<div id="campagneScelte">
			<b>Campagne avviate già scelte:</b>

			<c:forEach var="campagneScelte" items="${campagneScelte}">
				<p>
					<c:out value="${campagneScelte.nome}" />
					<c:out value="${campagneScelte.committente}" />
					<c:out value="${campagneScelte.stato}" />

					<c:url
						value="/getCampagnaPerLavoratore?campagna=${campagneScelte.campagnaID}"
						var="URL"></c:url>
					<a href="${URL}"> Dettagli </a>
				</p>
			</c:forEach>
		</div>
	</div>

</body>
</html>