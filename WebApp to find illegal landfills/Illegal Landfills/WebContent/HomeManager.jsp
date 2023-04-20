<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="utenteCorrente" scope="session" class="beans.Manager" />

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
    window.addEventListener("load",function(){
         alert("Non sei autorizzato, vai al login");
    })
    window.location.replace("./Login.html");

    </script>
	</c:if>
	<div class="navigation">

		<a class="buttonLogout" href="./logout">

			<div class="logout">LOGOUT</div>
		</a> <a class="buttonLogout" href="./ModificaProfiloManager.jsp">

			<div class="logout">MODIFICA</div>

		</a>


	</div>

	<div class="messaggioIniziale">


		Ciao
		<c:out value="${utenteCorrente.nome}" />
		!
	</div>
	<br>
	<br>
	<div class="contentHomeManager">
		<table>
			<td>
				<div class="listaCampagne">
					<c:forEach var="campagne" items="${campagne}">
						<p>
							<b> Nome: </b>
							<c:out value="${campagne.nome}" />
							<b> Committente:</b>
							<c:out value="${campagne.committente}" />
							<b>Stato:</b>
							<c:out value="${campagne.stato}" />


							<c:url
								value="/dettagliaCampagna?id=${campagne.campagnaID}&nome=${campagne.nome}&committente=${campagne.committente}&stato=${campagne.stato}"
								var="URL"></c:url>
							<a href="${URL}"> Dettagli </a>

						</p>
						<br>
					</c:forEach>

				</div>
			</td>

			<td>
				<div class="formCampagna">
					<br>
					<b> Crea campagna </b><br> <br>

					<div>
						<form name="creazioneCampagna" action="./creaCampagna"
							method="post">


							Nome della campagna <br> <input type="text"
								name="nome_campagna" required /><br> <br> Committente
							<br> <input type="text" name="committente" required /><br>
							<br> <input type="submit" value="Crea" />

						</form>
					</div>
			</td>
		</table>
	</div>
	<c:if test="${not empty erroreRegistrazioneCampagna}">
		<script>
    window.addEventListener("load",function(){
         alert("${erroreRegistrazioneCampagna}");
    }
    </script>
	</c:if>

	</div>




</body>
</html>