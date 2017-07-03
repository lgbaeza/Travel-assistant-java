<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<title>Travelling Assistant</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	
	<link rel="icon" type="image/x-icon" href="favicon.ico">
	<link rel="stylesheet" href="css/styles.css" />
	<link rel="stylesheet" href="css/travel.css" />
</head>
<body onload="scrollChat()">

	<div id="topBanner" style="">
		<a href="index.jsp"><div id="siteTitle">Travelling Assistant</div></a>
		<img src="images/travel-icon.png" id="siteLogo">
	</div>
	
	<div id="siteBody">
		<div class="center-tag">
			<div id="leftSpace">
				<h5 style="color: #000">Tu viaje a ${destination}</h5>
					
					<div id="pageInsight3">
						<span style="font-size: 20px; font-weight: bold; color:#333">
							Clima y Ubicación
						</span><br>
						<div style="text-align: center;font-size: 18px;">
							${weather}
							<iframe id="GMap" width="90%" height="400" frameborder="0" style="border:0" 
								src="https://www.google.com/maps/embed/v1/search?key=AIzaSyBbEUwx7EtSzL-Ssyz6QmRFBcowWIXtU-0&q=restaurantes&center=${map_lat},${map_lng}&zoom=12"
								allowfullscreen></iframe>
						</div>
						
					</div>
					
					<div id="pageInsight1">
						<div style="font-size: 18px; font-weight: bold; color:#333">
							Demográficos
						</div>
						<div style="text-align: justify; font-size: 18px;">
							${demographics}
						</div>
					</div>
					
					
					
					<div id="pageInsight2">
						<span style="font-size: 20px; font-weight: bold; color:#333">
							To-Do Checklist
						</span><br>
						<div style="text-align: left;font-size: 18px;overflow-y:auto;height:200px" id="toDoList">
							
						</div>
					</div>
					
			</div>
			
			<div id="rightSpace">
				<div id="askWatsonTitle">
					<img src="images/watson-avatar.png" id="watsonAvatar">
					Pregunta a Watson
				</div><br>
				<div id="chatHistory" name="chatHistory">
					${watson_history}
				</div>
				<div style="margin-top:10px">
					<input type="text" id="watson_question" onkeydown = "if (event.keyCode == 13) {document.getElementById('buttonConverse').click()}" style="width:70%">
					<input type="button" id="buttonConverse" value="Enviar" onclick="doPostBack()" style="width:20%">
				</div>
			</div>
		</div>
		
	</div>
	
	<div id="siteFooter" style="">
		@IBM BLUEMIX
	</div>
	
	<script type="text/javascript" src="js/callback.js"></script>
	<script type="text/javascript" src="js/travel.js"></script>

</body>
</html>
