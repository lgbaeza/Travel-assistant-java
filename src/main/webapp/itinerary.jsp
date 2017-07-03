<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Travelling Assistant</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="icon" type="image/x-icon" href="favicon.ico">
	<link rel="stylesheet" href="css/styles.css" />
	<style>
		.suggestion-block
		{
			display: inline-block;
			margin:30px;
			width: 450px;
			height: 300px;
			font-size:25px;
			font-weight: bold;
			border:solid 3px #AAA;
			color: #000;
			vertical-align: middle;
			padding-top: 5px;
		}
		#goButton
		{
			display:inline-block;
			float:right;
			right: 0px;
			width: 200px;
			height: 80px;
			background-color: rgb(49,117,219);
			text-align: center;
			line-height: 80px;
			color: #fff;
			font-weight: bold;
			font-size: 20px;
			margin-right: 10px;
		}
	</style>
</head>
<body>

	<div id="topBanner" style="">
		<a href="index.jsp"><div id="siteTitle">Travelling Assistant</div></a>
		<img src="images/travel-icon.png" id="siteLogo">
	</div>
	
	<div id="siteBody">
	<c:choose>
		<c:when test="${empty itinerary}">
		
			<div class="center-tag">
			<h1>¿Cuál es el Itinerario a ${destination}?</h1>
			
				<form style="width: 700px" action="ItinerarySetUp" method="post" enctype="multipart/form-data">
				<h4>Carga tu itinerario en DOC o PDF.</h4>
				<input type="file" name="file" required>
				<input type="submit" value="Cargar"> 
				<a href="files/itinerario-muestra.pdf" target="_blank">
				<input type="button" value="Descarga el ejemplo"></a>
<!-- 				<a href="travel?reset=1"><input type="button" value="Omitir"></a> -->
				</form>
			</div>
		</c:when>
		
		<c:otherwise>
			<h3>Confirma el Itinerario # ${itinerary}</h3>
			<div class="center-tag">
				<div class="suggestion-block">
					Vuelo de Ida<br>
					<span style="font-weight: normal;">
						${goingFlight}
					</span>
				</div>
				<div class="suggestion-block">
					Vuelo de Regreso<br>
					<span style="font-weight: normal;">
						${returningFlight}
					</span>					
				</div>
				<div class="suggestion-block">
					Hospedaje<br>
					<span style="font-weight: normal;">
						${lodgement}
					</span><br>
					Dirección<br>
					<span style="font-weight: normal;">
						${hotelAddress}
					</span><br>
					Teléfono<br>
					<span style="font-weight: normal;">
						${hotelPhone}
					</span>
				</div>
			</div>
			
			<a href="travel?reset=1">
				<div id="goButton">CONTINUAR</div>
			</a>
		</c:otherwise>
	</c:choose>
		
		
		
		
	</div>
	
	<div id="siteFooter">
		@IBM BLUEMIX
	</div>
	
	
	
</body>
</html>
