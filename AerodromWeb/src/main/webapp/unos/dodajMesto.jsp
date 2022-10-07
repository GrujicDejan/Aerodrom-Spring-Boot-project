<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
<meta charset="ISO-8859-1">
<title>Dodavanje mesta</title>
</head>
<body>

	<div class="nav">
		<a href="/Aerodrom/pocetna.jsp">Pocetna</a>
		<security:authorize access="!isAuthenticated()">
			<a href="/Aerodrom/letovi/prikazLetova">Rezervacija karte</a>
			<a href="/Aerodrom/login.jsp">Login</a>
		</security:authorize>
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<a href="/Aerodrom/admin/unosLeta">Dodaj novi let</a>
			<a href="/Aerodrom/admin/prikazLetovaAdministrator">Pregled letova</a>
			<a href="/Aerodrom/admin/prikazRezervacija">Pregled rezervacija</a>
			<a href="/Aerodrom/auth/logout">Odjava</a>
		</security:authorize>
		<security:authorize access="hasRole('PUTNIK')">
			<a href="/Aerodrom/putnik/prikazLetova">Rezervacija karte</a>
			<a href="/Aerodrom/putnik/prikazRezervacija">Istorija rezervacija</a>
			<a href="/Aerodrom/putnik/unesiPodatke">Unesi podatke</a>
			<a href="/Aerodrom/putnik/azurirajPodatke">Azuriraj podatke</a>
			<a href="/Aerodrom/auth/logout">Odjava</a>
		</security:authorize>
	</div>
	
	<div class="main">
		<div class="center" style="font-size: 25px; text-align: center;">
		
			<c:if test="${!empty mesto}">
				<div class="success">
				  	<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
				  	Mesto [red: ${mesto.red}, br: ${mesto.broj}] uspesno dodato.
				</div>
			</c:if>
			
			<c:if test="${!empty poruka}">
				<div class="alert">
				  	<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
				  	${poruka}
				</div>
			</c:if>
			
			<form action="/Aerodrom/admin/sacuvajMesto" method="post">
				<h1>Unos slobodnog mesta za let ${letForInfo.aerodrom1.mesto} - ${letForInfo.aerodrom2.mesto}:</h1>
				<table>
					
					<tr>
						<td>Uneste red:</td>
						<td><input type="text" name="red"></td>
					</tr>
					
					<tr>
						<td>Uneste redni broj:</td>
						<td><input type="text" name="broj"></td>
					</tr>
					
					<tr>
						<td>Klasa:</td>
						<td><input type="text" name="klasa"></td>
					</tr>
					
					<tr>
						<td><input type="submit" value="Sacuvaj mesto" class="btn-submit"></td>
					</tr>
					
		
				</table>
				<br>
				<a href="/Aerodrom/pocetna.jsp">Nastavi dalje</a>
			</form>
		
		</div>
	</div>

</body>
</html>