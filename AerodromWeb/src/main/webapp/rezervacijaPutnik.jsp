<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
<meta charset="ISO-8859-1">
<title>Rezervacija</title>
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
		
			<c:if test="${!empty mesta}">

				<form action="/Aerodrom/putnik/sacuvajRezervaciju" method="post">
					<table>
						<h1>Izaberite mesto:</h1>
						<tr>
							<td><select name="mesto">
									<c:forEach items="${mesta}" var="m">
										<option value="${m.idMesto}">Red: ${m.red}, broj: ${m.broj}, klasa: ${m.klasa}</option>
									</c:forEach>
							</select>
							<td>
						<tr>
					</table>
					<br> <input type="submit" value="Rezervisi" class="btn-submit"><br>
				</form>
				
			</c:if>
			
			<c:if test="${empty mesta}">
				<div class="alert">
				  	<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
				  	<h1>Nema slobodnih mesta za izabrani let!</h1>
				</div>
			</c:if>
				
		</div>
	</div>
</body>
</html>