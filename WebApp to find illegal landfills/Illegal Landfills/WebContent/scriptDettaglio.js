var HttpClient = function() {
    this.get = function(aUrl, aCallback) {
        var anHttpRequest = new XMLHttpRequest();
        anHttpRequest.onreadystatechange = function() { 
            if (anHttpRequest.readyState == 4 && anHttpRequest.status == 200)
                aCallback(anHttpRequest.responseText);
        }

        anHttpRequest.open( "GET", aUrl, true );            
        anHttpRequest.send( null );
    }
}

var HttpClientPost=function() {
	 var fileList = inputElement.files;
}

function dropdownLocalita() {

	
	  document.getElementById("listaLocalita").classList.toggle("show");
	}

	// Close the dropdown menu if the user clicks outside of it
	window.onclick = function(event) {
	  if (!event.target.matches('.scegliLocalita')) {
	    var dropdowns = document.getElementsByClassName("listaLocalita");
	    var i;
	    for (i = 0; i < dropdowns.length; i++) {
	      var openDropdown = dropdowns[i];
	      if (openDropdown.classList.contains('show')) {
	        openDropdown.classList.remove('show');
	      }
	    }
	  }
	}

	function prossimoPasso() {
		var ultimo_passo = localStorage.getItem("ultimoPasso");

		if (ultimo_passo==2) {
			//siamo al secondo passo
			var passo_corrente=document.getElementById("passo_2");
			passo_corrente.style.display="none";
			//vado al passo 3
			localStorage.setItem("ultimoPasso", 3);
			var nuovo_passo=document.getElementById("passo_3");
			nuovo_passo.style.display="block";
		}

		else if (ultimo_passo==3){
			var passo_corrente=document.getElementById("passo_3");
			passo_corrente.style.display="none";
			localStorage.setItem("ultimoPasso", 1);
			//COSE DA FARE SUL SERVER
		}
		else {	//siamo al primo passo
			var passo_corrente=document.getElementById("passo_1");
			passo_corrente.style.display="none";
			localStorage.setItem("ultimoPasso", 2);
			var nuovo_passo=document.getElementById("passo_2");
			nuovo_passo.style.display="block";
		}
	}

	function selezionaLocalita(id) {

		localStorage.setItem("localitaScelta", id);
		prossimoPasso();

	}

	function aggiungiLocalita(){

		var latitudine=document.getElementsByName("newLatitudine")[0].value;
		var longitudine=document.getElementsByName("newLongitudine")[0].value;
		var nome=document.getElementsByName("newNomeLocalita")[0].value;
		var comune=document.getElementsByName("newComune")[0].value;
		var regione=document.getElementsByName("newRegione")[0].value;

		localStorage.setItem("localitaScelta", 0);
		localStorage.setItem("newLatitudine", latitudine);
		localStorage.setItem("newLongitudine", longitudine);
		localStorage.setItem("newNome", nome);
		localStorage.setItem("newComune", comune);
		localStorage.setItem("newRegione", regione);
		prossimoPasso();


	}

	function annulla() {

		//chiudi passo attuale
		var ultimo_passo = localStorage.getItem("ultimoPasso");

		if (ultimo_passo==2) {
			//siamo al secondo passo
			var passo_corrente=document.getElementById("passo_2");
			passo_corrente.style.display="none";
		}

		else if (ultimo_passo==3){
			var passo_corrente=document.getElementById("passo_3");
			passo_corrente.style.display="none";
		}

		//cancella tutto dal local storage
		localStorage.clear();

		//torna a passo uno
		localStorage.setItem("ultimoPasso", 1);
		var passo_corrente=document.getElementById("passo_1");
		passo_corrente.style.display="block";
	}

	function tornaIndietro() {
		//sei al passo 3 e vuoi tornare al due
		var passo_corrente=document.getElementById("passo_3");
		passo_corrente.style.display="none";
		localStorage.setItem("ultimoPasso", 2);
		var passo_precedente=document.getElementById("passo_2");
		passo_precedente.style.display="block";
	}

	function aggiungiImmagine() {
		
		
		var wizard = document.getElementById("wizard");
		var passi= document.getElementsByClassName("passo")[0];
		localStorage.setItem("ultimoPasso", 1);
		wizard.style.display = "none";
		passi.style.display="none";

	//prendi i dati dell'immagine
	var provenienza=document.getElementsByName("provenienza")[0].value;
	var dataDiRecupero=document.getElementsByName("dataDiRecupero")[0].value;
	var risoluzione=document.querySelector('input[name="risoluzione"]:checked').value;

	
	
	//prendi la località scelta
	var localita=localStorage.getItem("localitaScelta");
	
	//CASO IN CUI LA LOCALITA E' NUOVA
	if (localita==0){
		var lat= localStorage.getItem("newLatitudine");
		var long=localStorage.getItem("newLongitudine");
		var nome=localStorage.getItem("newNome");
		var comune= localStorage.getItem("newComune");
		var regione= localStorage.getItem("newRegione");
	//chiamiamo la servlet ma dobbiamo salvare la nuova località
		var formData= new FormData();
		var file=document.getElementById("immagineDiscarica"); //sennò document.querySelector
		 var request = new XMLHttpRequest();
		 request.open("POST", "./aggiungiImmagineConLocalita");
		 //formData.append("img", file);
		 formData.append("img", file.files[0]);
		 formData.append("lat", lat);
		 formData.append("long", long);
		 formData.append("nome", nome);
		 formData.append("comune", comune);
		 formData.append("regione", regione);
		 formData.append("provenienza", provenienza);
		 formData.append("dataDiRecupero", dataDiRecupero);
		 formData.append("risoluzione", risoluzione);
		 request.send(formData);
	}

	//chiama la servlet
//CASO IN CUI LA LOCALITA E' GIA' ESISTENTE
	else {
	
		var formData= new FormData();
		var file=document.getElementById("immagineDiscarica"); //sennò document.querySelector
		 var request = new XMLHttpRequest();
		 request.open("POST", "./salvaImmagine");
		 formData.append("localita", localita);
		 formData.append("img", file.files[0]);
		 formData.append("provenienza", provenienza);
		 formData.append("dataDiRecupero", dataDiRecupero);
		 formData.append("risoluzione", risoluzione);
		 request.send(formData);
	}
	}

	window.onload = function openStatistiche() { // wait for the document to finish loading
		var title = document.getElementById("clickable");
		title.onclick = function() {
			var element = document.getElementById("p1");
			if(element.className==="masked"){
				element.className = "displayed";}
			else {element.className="masked";}

		};


	};
	
	function openWizard() {
		var wizard = document.getElementById("wizard");
		var passi= document.getElementsByClassName("passo")[0];
		wizard.style.display = "none";
		passi.style.display= "none";
		localStorage.setItem("ultimoPasso", 1);

		//Get the button that opens the modal
		var btn = document.getElementById("wizardButton");

		//Get the <span> element that closes the modal
		var span = document.getElementsByClassName("close")[0];

		//When the user clicks the button, open the modal 
		btn.onclick = function() {
			wizard.style.display = "block";
			
			var ultimo_passo = localStorage.getItem("ultimoPasso");
			
			if (ultimo_passo==2) {
				//siamo al secondo passo
				var passo_corrente=document.getElementById("passo_2");
				passo_corrente.style.display="block";
			}
			
			else if (ultimo_passo==3){
				var passo_corrente=document.getElementById("passo_3");
				passo_corrente.style.display="block";
			}
			else {	//siamo al primo passo
				localStorage.setItem("ultimoPasso", 1);
				var passo_corrente=document.getElementById("passo_1");
				passo_corrente.style.display="block";
	}
		}

		//When the user clicks on <span> (x), close the modal
		span.onclick = function() {
			wizard.style.display = "none";
			passi.style.display="none";
		}

		//When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
			if (event.target == wizard) {
				wizard.style.display = "none";
				passi.style.display="none";
			}
		}
	}

	function mostraAnnotazioniImmagine(id)
	{
		var f=document.getElementById(id+"annotazioni");
		f.style.display="block";
	}
