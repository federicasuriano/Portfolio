<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="scriptMappa.js"></script>

<jsp:useBean id="campagna" scope="session" class="beans.Campagna" />

<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="/DiscaricheIllegali/leaflet.css" />
<script src="/DiscaricheIllegali/leaflet.js"></script>

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
	<c:set var="maxLat" value="${0}" />
	<c:set var="minLat" value="${0}" />
	<c:set var="maxLong" value="${0}" />
	<c:set var="minLong" value="${0}" />

	<div class="navigation">

		<a class="buttonLogout" href="./logout">

			<div class="logout">LOGOUT</div>
		</a> <a class="buttonLogout" href="./ModificaProfiloLavoratore.jsp">

			<div class="logout">MODIFICA</div>

		</a>


	</div>
	<!-- ERRORE -->
	<div class="messaggioErrore">
		<c:out value="${erroreRegistrazioneAnnotazione}" />
	</div>

	<!-- MAPPA -->
	<div id="mapid" class="mapLavoratore"></div>
	<script>
		window.addEventListener('load', initMappa("${localita[0].latitudine}", "${localita[0].longitudine}"));
	</script>


	<c:forEach var="loc" items="${localita}">
		<!-- AGGIUNGI MARKER -->
		<script>
			addMarker("${loc.latitudine}", "${loc.longitudine}",
					"${loc.localitaID}");
		</script>

		<!-- CONTROLLO LAT/LONG -->
		<fmt:parseNumber var="parsedLat" type="number" integerOnly="false"
			value="${loc.latitudine}" />
		<fmt:parseNumber var="parsedLong" type="number" integerOnly="false"
			value="${loc.longitudine}" />
		<c:if test="${parsedLat>=maxLat||maxLat==0}">
			<c:set var="maxLat" value="${parsedLat}" />
		</c:if>
		<c:if test="${parsedLat<=minLat||minLat==0}">
			<c:set var="minLat" value="${parsedLat}" />
		</c:if>
		<c:if test="${parsedLong>=maxLong||maxLong==0}">
			<c:set var="maxLong" value="${parsedLong}" />
		</c:if>
		<c:if test="${parsedLong<=minLong||minLong==0}">
			<c:set var="minLong" value="${parsedLong}" />
		</c:if>


		<!-- /CONTROLLO -->

		<!-- SCRIVO LOCALITA -->
		<div id="${loc.localitaID}" class="elencoImmagini">

			<c:forEach var="immagini" items="${loc.immagini}">
				<i>Data:</i>
				<c:out value="${immagini.data}" />
				<i>Provenienza:</i>
				<c:out value="${immagini.provenienza}" />
				<i>Risoluzione:</i>
				<c:out value="${immagini.risoluzione}" />
				<br>
				<img width="100" height="100"
					src="/Immagini/${immagini.immagineID}${immagini.estensione}" />
				<br>
				<br>
				<button onclick="mostraForm(${immagini.immagineID})"
					class="aggiungiAnnotazione">Aggiungi annotazione</button>
				<br>
				<br>

				<div id="${immagini.immagineID}form" class="formDiv">
					<form name="${immagini.immagineID}" method="post"
						action="./aggiungiAnnotazione">
						
						<input type="hidden" name="immagineAnnotazione"
							value="${immagini.immagineID}">
						<div class="label">Fiducia</div>
						<br> <input type="radio" name="fiduciaAnnotazione" value="0"
							checked> Bassa <input type="radio"
							name="fiduciaAnnotazione" value="1"> Media <input
							type="radio" name="fiduciaAnnotazione" value="2"> Alta <br>
						<br>

						<div class="label">L'immagine rappresenta una discarica
							illegale?</div>
						<br> <input type="radio" name="validitaAnnotazione" value="0"
							checked> No <input type="radio"
							name="validitaAnnotazione" value="1"> Sì <br> <br>
						<div class="label">Vuoi aggiungere delle note?</div>
						<br> <input type="text" name="noteAnnotazione" size="">
						<input type="submit" value="Submit" />

					</form>
				</div>
				<br>
			</c:forEach>
			<button id="chiudiInfoLavoratore"
				onclick='chiudiImmagini(${loc.localitaID})'>CHIUDI</button>
		</div>



	</c:forEach>

	<script>
	addPoligon(${minLat},${minLong},${maxLat},${maxLong});
	</script>

</body>
</html>
