<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page isELIgnored="false"%>
<script type="text/javascript" src="scriptMappa.js"></script>

<script type="text/javascript" src="scriptDettaglio.js"></script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

<jsp:useBean id="campagna" scope="session" class="beans.Campagna" />



<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dettagli della campagna</title>
<link href="style.css" rel="stylesheet" type="text/css">

<style>
</style>


<link rel="stylesheet" href="/DiscaricheIllegali/leaflet.css" />
<script src="/DiscaricheIllegali/leaflet.js"></script>

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

	<!-- TASTO HOME -->
	<a class="buttonLogout" href="./getCampagneManager">

		<div class="logout">HOME</div>

	</a>
	<!-- /TASTO HOME -->

<!-- LEGENDA -->
<div class="legenda">
La località:

<p id="verde">ha almeno un'immagine con almeno un'annotazione</p>
<p id="giallo">non ha nemmeno un'immagine annotata</p>
<p id="rosso">ha un conflitto</p>
</div>
<!-- /LEGENDA -->
	<div class="dettagliCampagna">
		<%-- DETTAGLI CAMPAGNA --%>

		<p align="center">
			<b><c:out value="${campagna.nome}" /> </b>
		</p>
		<p>
			Committente:
			<c:out value="${campagna.committente}" />
		</p>
		<p>
			Stato:
			<c:out value="${campagna.stato}" />
		</p>
		<p>
			<c:choose>
				<c:when test="${campagna.stato=='CREATA'}">
					<a href="./avviaCampagna">Avvia</a>
				</c:when>
			</c:choose>
		</p>
		<p>
			<c:choose>
				<c:when test="${campagna.stato=='APERTA'}">
					<a href="./chiudiCampagna">Chiudi</a>
				</c:when>
			</c:choose>
		</p>

		<%-- DETTAGLI CAMPAGNA --%>
	</div>

	<%-- LISTA LOCALITA' + IMMAGINI --%>
	<c:set var="nLocalita" value="${0}" />
	<c:set var="nImmagini" value="${0}" />
	<div class="listaLocalitaImmagini">
		<c:forEach var="localita" items="${localita}">
			<c:set var="nLocalita" value="${nLocalita+1}" />
			<div class="dettagliLocalita">
				<b><c:out value="${localita.nome}" /> </b> [
				<c:out value="${localita.latitudine}" />
				,
				<c:out value="${localita.longitudine}" />
				] <i><c:out value="${localita.comune}" />, <c:out
						value="${localita.regione}" /> </i>
			</div>
			<br>
			<c:forEach var="immagini" items="${localita.immagini}">
				<c:set var="nImmagini" value="${nImmagini+1}" />
				<i>ID: </i>
				<button onclick="mostraInformazioni(${localita.localitaID})">
					<c:out value="${immagini.immagineID}" />
				</button>
				<br>
				<i> Data: </i>
				<c:out value="${immagini.data}" />
				<br>
				<i>Provenienza:</i>
				<c:out value="${immagini.provenienza}" />
				<br>
				<i>Risoluzione:</i>
				<c:out value="${immagini.risoluzione}" />
				<br>
				<c:choose>
					<c:when test="${immagini.validita==false}">
						<b> <c:out value="L'immagine non è valida" />
						</b>
						<br>
					</c:when>
					<c:when test="${immagini.validita==true}">
						<b> <c:out value="L'immagine è valida" />
						</b>
						<br>
					</c:when>
				</c:choose>
				<img width="60" height="60"
					src="/Immagini/${immagini.immagineID}${immagini.estensione}" />
				<br>
				<br>
			</c:forEach>
		</c:forEach>
	</div>
	<br>
	<%-- LISTA LOCALITA' + IMMAGINI --%>

	<c:choose>
		<c:when
			test="${campagna.stato=='CREATA' || campagna.stato=='APERTA' }">


			<%-- WIZARD --%>



			<button id="wizardButton">Carica immagini</button>
			<div id="wizard" title="wizard" class="wizard">
				<div class="wizardContent">
					<span class="close">&times;</span>

					<%-- PASSO 1 --%>

					<div id="passo_1" class="passo">
						<div class="localita">
							Scegli la località:
							<button onclick="dropdownLocalita()" class="scegliLocalita">Localita</button>
							<div id="listaLocalita" class="listaLocalita">
								<c:forEach var="localita" items="${localita}">
									<button onclick=selezionaLocalita(this.id)
										id="${localita.localitaID}" class="selezioneLocalita">
										<c:out value="${localita.nome}" />
									</button>
									<br>
								</c:forEach>
							</div>
						</div>

						<br> <br> O creane una nuova: <br> <br>

						<div id="aggiungiLocalita">


							<div class="label">Latitudine</div>
							<input type="text" name="newLatitudine" required /> <br>

							<div class="label">Longitudine</div>
							<input type="text" name="newLongitudine" required /> <br>

							<div class="label">Nome</div>
							<input type="text" name="newNomeLocalita" required /> <br>

							<div class="label">Comune</div>
							<input type="text" name="newComune" required /> <br>

							<div class="label">Regione</div>
							<input type="text" name="newRegione" required /> <br> <br>

							<button id="submitNewLocalita" onclick="aggiungiLocalita()">Crea
								la nuova località</button>
						</div>

					</div>

					<%-- PASSO 2 --%>
					<div id="passo_2" class="passo">
						Inserisci l'immagine: <br> <br> <input type="file"
							id="immagineDiscarica" accept="image/png, image/jpeg">
						<button id="prossimoPassoButton" onclick="prossimoPasso()">Prossimo</button>
					</div>

					<%-- PASSO 3 --%>

					<div id="passo_3" class="passo">
						<div id="aggiungiInformazioniImmagine">


							<div class=label>Provenienza</div>
							<input type="text" name="provenienza" required /><br>
							<div class=label>Data di Recupero</div>
							<input type="date" name="dataDiRecupero" required /> <br>

							<div class=label>Risoluzione</div>
							<input type="radio" name="risoluzione" value="2">
							Alta <input type="radio" name="risoluzione" value="1">
							Media <input type="radio" name="risoluzione" value="0">
							Bassa <br> <br>


							<button id="submitNewImmagine" onclick="aggiungiImmagine()">Fatto</button>
						</div>
						<br>Hai cambiato idea? Scegli un'altra immagine
						<button id="tornaIndietro" onclick="tornaIndietro()">Torna
							indietro</button>

					</div>

					<br>
					<button id="annulla" onclick="annulla()">ANNULLA</button>
				</div>
			</div>

			<%-- /WIZARD --%>
		</c:when>
	</c:choose>


	<c:choose>
		<c:when test="${campagna.stato=='APERTA'}">

			<%-- MAPPA --%>
			<c:set var="nAnnotazioni" value="${0}" />
			<c:set var="numAnnotazioni" value="${0}" />
			<div id="mappa">
				<div id="mapid"></div>

				<script>
			window.addEventListener('load', initMappa(
					"${localita[0].latitudine}", "${localita[0].longitudine}"));
		</script>

				<c:forEach var="loc" items="${localita}">

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

					<div id="${loc.localitaID}info" class="informazioni">
						<c:forEach var="immagini" items="${loc.immagini}">
						<i>ID:</i>
							<c:out value="${immagini.immagineID}" />
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
							<c:choose>
								<c:when test="${immagini.conflitto==true}">
									<button id="risolviConflitto"
										onclick="mostraAnnotazioniImmagine(${immagini.immagineID})">Risolvi
										conflitto</button>
								</c:when>
							</c:choose>
							<br>
							<div id="${immagini.immagineID}annotazioni" class="masked">
								<c:forEach var="annotazioni" items="${immagini.annotazioni}">
									<c:set var="numAnnotazioni" value="${numAnnotazioni+1}" />
									<c:set var="nAnnotazioni" value="${nAnnotazioni+1}" />
							ID lavoratore: <c:out value="${annotazioni.lavoratore}" />
									<br>
							L'immagine <c:choose>
										<c:when test="${annotazioni.validita==false}"> non </c:when>
									</c:choose> è valida. <br>
							Note aggiuntive: <c:out value="${annotazioni.note}" />
								</c:forEach>
								<br> L'immagine è valida?
								<c:url
									value="/validaImmagine?valida=1&immagine=${immagini.immagineID}"
									var="URLSI"></c:url>
								<c:url
									value="/validaImmagine?valida=0&immagine=${immagini.immagineID}"
									var="URLNO"></c:url>
								<button>
									<a href="${URLSI}"> Si </a>
								</button>
								<button>
									<a href="${URLNO}"> No </a>
								</button>


							</div>

							<c:choose>
								<c:when test="${immagini.conflitto==true||nAnnotazioni==-1}">
									<c:set var="nAnnotazioni" value="${-1}" />
								</c:when>
							</c:choose>

						</c:forEach>
						<button id="chiudiInfo" onclick='chiudiInfo(${loc.localitaID})'>CHIUDI</button>
					</div>

					<c:choose>
						<c:when test="${nAnnotazioni==0}">
							<script>
				addMarkerPerManager("${loc.latitudine}", "${loc.longitudine}",
						"${loc.localitaID}", 1); <!--giallo-->
			</script>
						</c:when>

						<c:when test="${nAnnotazioni>0}">
							<script>
				addMarkerPerManager("${loc.latitudine}", "${loc.longitudine}",
						"${loc.localitaID}", 0);<!--verde-->
			</script>
						</c:when>

						<c:when test="${nAnnotazioni<0}">
							<script>
				addMarkerPerManager("${loc.latitudine}", "${loc.longitudine}",
						"${loc.localitaID}", 2);<!--rosso-->
			</script>
						</c:when>
					</c:choose>
					<c:set var="nAnnotazioni" value="${0}" />
				</c:forEach>
			</div>
			<%-- /MAPPA --%>
		</c:when>
	</c:choose>

	<script> 
addPoligon(${minLat},${minLong},${maxLat},${maxLong});
</script>

	<%-- STATISTICHE --%>

	<c:choose>
		<c:when test="${campagna.stato=='APERTA' || campagna.stato=='CHIUSA'}">
			<button id="clickable">Statistiche</button>
		</c:when>
	</c:choose>

	<div id="p1" class="masked">
		<br> <i> Numero totale località:</i> ${nLocalita} <br> <i>Numero
			totale immagini:</i> ${nImmagini}<br> <i>Numero totale
			annotazioni: </i> ${numAnnotazioni} <br>
		<c:if test="${nImmagini!=0 && nLocalita!=0}">
			<i>Numero medio di immagini per località:</i> ${nImmagini div nLocalita}
		<br>
		</c:if>
		<c:if test="${numAnnotazioni!=0 && nLocalita!=0}">
			<i>Numero medio di annotazioni per località:</i> ${numAnnotazioni div nLocalita}<br>
		</c:if>
		<br> <i><b>Elenco conflitti:</b></i>
		<div id="listaConflitti">

			<c:forEach var="loc" items="${localita}">

				<c:forEach var="immagini" items="${loc.immagini}">
					<c:choose>
						<c:when test="${immagini.conflitto==true}">
							<br> Conflitto per l'immagine: <button
								onclick="mostraInformazioni(${loc.localitaID})">
								<c:out value="${immagini.immagineID}" />
							</button>
						</c:when>
					</c:choose>
				</c:forEach>
			</c:forEach>
		</div>
	</div>

	<%-- /STATISTICHE --%>


	<%-- Script per aprire statistiche e wizard --%>

	<script>
		window.addEventListener('load', openWizard());
		window.addEventListener('load', openStatistiche());
		</script>
</body>
</html>
