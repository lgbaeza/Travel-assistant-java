function doPostBack(){
	var msg = encodeURI(document.getElementById('watson_question').value);
		
	xhrGet("sendMessageWatson?msg=" + msg, function(responseText){
		document.getElementById("chatHistory").innerHTML = responseText;
		document.getElementById('watson_question').value = "";
		scrollChat();
		
		xhrGet("getRecordatorios", function(responseText){
			document.getElementById("toDoList").innerHTML = "<ul>" + responseText + "</ul>";
		}, function(err){
			console.log(err);
		});
		
		xhrGet("getInstrucciones", function(responseText){
			var mapa = responseText.split(",")[0];
			var origen = responseText.split(",")[1];
			var destino = responseText.split(",")[2];
			
			if(mapa == "true")
				{document.getElementById("GMap").src = "https://www.google.com/maps/embed/v1/directions?key=AIzaSyBbEUwx7EtSzL-Ssyz6QmRFBcowWIXtU-0&origin=" + origen + "&destination=" + destino;}
		
		}, function(err){
			console.log(err);
		});
		
		
		
	}, function(err){
		console.log(err);
	});
	
	
}

function scrollChat() {
	var objDiv = document.getElementById("chatHistory");
	objDiv.scrollTop = objDiv.scrollHeight;
}