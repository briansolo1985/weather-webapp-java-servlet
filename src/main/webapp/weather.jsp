<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Simple Weather Forecast</title>
</head>
<body>
	<h1>Simple Weather Forecast</h1>
	<form action="WeatherServlet" method="GET">
		<div style="padding:3px; float:left; border:1px solid black; background:#8DC7BB; margin-right:5px;">Please insert a city name and click Submit!</div> 
		<input name="city" type="text" />
		<input name="submit" value="Submit" type="submit" />
	</form>
</body>
</html>