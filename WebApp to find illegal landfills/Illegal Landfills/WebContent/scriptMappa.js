
var map;

function initMappa(lat, long){
	var latitudine=parseInt(lat);
	var longitudine=parseInt(long);
	map=L.map('mapid').setView([latitudine/100, longitudine/100], 6);
	L.tileLayer('https://api.maptiler.com/maps/streets/{z}/{x}/{y}.png?key=tlr3Sxv2673hljjftKm4', {attribution: '<a href="https://www.maptiler.com/copyright/" target="_blank">&copy; MapTiler</a> <a href="https://www.openstreetmap.org/copyright" target="_blank">&copy; OpenStreetMap contributors</a>'}).addTo(map);


}

function addPoligon(minLat, minLong, maxLat, maxLong) {

	var polygon = L.polyline([
	    [minLat/100-0.02, minLong/100-0.02],
	    [minLat/100-0.02, maxLong/100+0.02],
	    [maxLat/100+0.02, maxLong/100+0.02],
	    [maxLat/100+0.02, minLong/100-0.02],
	    [minLat/100-0.02, minLong/100-0.02]
	]).addTo(map);
}

function addMarker(lat, long, id) {
	var latitudine=parseInt(lat);
	var longitudine=parseInt(long);
	var circle = L.circle([latitudine/100, longitudine/100], {
		color: 'red',
		fillColor: '#f03',
		fillOpacity: 0.5,
		radius: 500
	}).addTo(map);
	circle.bindPopup("INFORMAZIONI<br> <button onclick='mostraImmagini("+id+")'>Visualizza</button> <br>" );

}


function addMarkerPerManager(lat, long, id, colore) {
	var latitudine=parseInt(lat);
	var longitudine=parseInt(long);
	
	if (colore==0) {var circle = L.circle([latitudine/100, longitudine/100], 
			{
		color: 'green',
		fillColor: '#00ff03',
		fillOpacity: 0.5,
		radius: 500
	}).addTo(map);
	circle.bindPopup("INFORMAZIONI<br> <button onclick='mostraInformazioni("+id+")'>Visualizza</button> <br>" );
	}
	
else if (colore==1) {
	var circle = L.circle([latitudine/100, longitudine/100], {
		color: 'yellow',
		fillColor: '#FFC500',
		fillOpacity: 0.5,
		radius: 500
	}).addTo(map);
	circle.bindPopup("INFORMAZIONI<br> <button onclick='mostraInformazioni("+id+")'>Visualizza</button> <br>" );
}
	
else if (colore==2) {	var circle = L.circle([latitudine/100, longitudine/100], {
	color: 'red',
	fillColor: '#f03',
	fillOpacity: 0.5,
	radius: 500
}).addTo(map);
circle.bindPopup("INFORMAZIONI<br> <button onclick='mostraInformazioni("+id+")'>Visualizza</button> <br>" );
}
}

function mostraImmagini(id)
{	var immagini = document.getElementById(id);
immagini.style.display="block";
}

function chiudiImmagini(id)
{var immagini = document.getElementById(id);
immagini.style.display="none";}

 function mostraInformazioni(id) { // wait for the document to finish loading
		var info = document.getElementById(id+"info");
		info.style.display="block";
		var element= document.getElementById("p1");
		
		element.className="masked";
	}
function mostraForm(id) {
	var f=document.getElementById(id+"form");
	f.style.display="block";
}

function chiudiInfo(id) {
	var i = document.getElementById(id+"info");
	i.style.display="none";
}
