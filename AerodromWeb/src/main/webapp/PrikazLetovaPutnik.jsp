<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
<meta charset="ISO-8859-1">
<title>Prikaz letova</title>
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
	
			<form action="/Aerodrom/putnik/filtrirajLetove" method="get">
				<table>
					<tr>
						<td>Datum polaska:</td>
						<td><input type="date" name="datumPolaska"></td>
						<td>Datum polaska:</td>
						<td><input type="date" name="datumDolaska"></td>
						<td>Destinacija:</td>
						<td><input type="text" name="destinacija"></td>
						<td><input type="submit" value="Filtriraj"></td>
					</tr>		
				</table>
			</form>
			
			
				
			<c:if test="${primenjenFilter}">
				<c:if test="${empty letovi}">
					Nema rezultata za odabrane filtere!
				</c:if>
				<a href="/Aerodrom/putnik/prikazLetova">Resetuj filtere</a> <br>
			</c:if>
				
			<c:if test="${!empty letovi}">
		
				<table border="1">
					<tr>
						<th>Destinacija</th>
						<th>Polazak iz</th>
						<th>Datum polaska</th>
						<th>Datum dolaska</th>
						<th>Trajanje leta</th>
						<th>Cena leta</th>
						<th>Informacije</th>
						<th>Rezervisi</th>
					</tr>
		
		
					<c:forEach items="${letovi}" var="l">
						<tr>
							<td>${l.aerodrom2.mesto}</td>
							<td>${l.aerodrom1.mesto}</td>
							<td>${l.datumPolaska}</td>
							<td>${l.datumDolaska}</td>
							<td>${l.trajanjeLeta}</td>
							<td>${l.cena}e</td>
							<td>
								<a href="/Aerodrom/putnik/getLetInfo?idLet=${l.idLet}">Detalji</a>
							</td>
							<td>
								<a href="/Aerodrom/putnik/rezervacija?idLet=${l.idLet}">Rezervisi</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</div>
	</div>

</body>
</html>