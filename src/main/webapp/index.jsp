<%@ page contentType="text/html; charset=UTF-8" %>

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
			width: 400px;
			height: 300px;
			font-size:25px;
			font-weight: bold;
			color:#fff;
			vertical-align: middle;
			padding-top: 5px;
		}
		.suggestion-img
		{
			width:350px;
			padding-top: 20px;
		}
		
	</style>
</head>
<body>

	<div id="topBanner" style="">
		<a href="index.jsp"><div id="siteTitle">Travelling Assistant</div></a>
		<img src="images/travel-icon.png" id="siteLogo">
	</div>
	
	<div id="siteBody">
		<h1>Cuéntame a donde estamos viajando</h1>
		<div class="center-tag">
			<form style="width: 700px" action="DestinationSetUp" method="get">
			<input type="text" name="dest" id="dest" style="width:400px" placeholder="Ingresa la ciudad o estado al que viajamos" >
			<input type="submit" value="¡Vamos allá!">
			</form>
		</div>
		
		<h3>O elije una de mis sugerencias...</h3>
		<div class="center-tag">
			<a href="DestinationSetUp?dest=cancun">
				<div class="suggestion-block" style="background-color: rgb(237,169,12)">
					CANCÚN, QR
					<img src="images/cancun.jpg" class="suggestion-img">
				</div>
			</a>
			<a href="DestinationSetUp?dest=cabo san lucas">
				<div class="suggestion-block" style="background-color: rgb(215,87,246)">
					CABO SAN LUCAS
					<img src="images/cabosl.jpg" class="suggestion-img">
				</div>
			</a>
			<a href="DestinationSetUp?dest=chichen itza">
				<div class="suggestion-block" style="background-color: rgb(20,100,246)">
					CHICHEN-ITZÁ
					<img src="images/chichen.jpg" class="suggestion-img">
				</div>
			</a>
		</div>
		
	</div>
	
	<div id="siteFooter">
		@IBM BLUEMIX
	</div>
	
	
	
</body>
</html>
