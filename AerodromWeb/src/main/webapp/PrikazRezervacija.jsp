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
<title>Prikaz rezervacija</title>
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
		
			<c:if test="${izbrisanaRezervacija}">
				<div class="success">
				  	<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
				  	Rezervacija je uspesno izbrisana.
				</div>
			</c:if>

			<form action="/Aerodrom/admin/filtrirajRezervacije" method="get">
				<table>
					<tr>
						<td>Ime putnika:</td>
						<td><input type="text" name="ime"></td>
						<td>Prezime putnika:</td>
						<td><input type="text" name="prezime"></td>
					</tr>
					<tr>
						<td>Destinacija:</td>
						<td><input type="text" name="destinacija"></td>
						<td>Datum:</td>
						<td><input type="date" name="datum"></td>
						<td><input type="submit" value="Filtriraj"></td>
					</tr>	
				</table>
			</form> <br>
			
			<a href="/Aerodrom/admin/generisiIzvestaj">Generisi izvestaj sa aktuelnim rezervacijama</a><br>
				
			<c:if test="${primenjenFilter}">
				<c:if test="${empty karte}">
					Nema rezultata za odabrane filtere!
				</c:if>
				<a href="/Aerodrom/admin/prikazRezervacija">Resetuj filtere</a> <br>
			</c:if>
				
			<c:if test="${!empty karte}">
		
				<table border="1">
					<tr>
						<th>Let</th>
						<th>Putnik</th>
						<th>Mesto</th>
						<th>Klasa</th>
						<th>Cena</th>
						<th>Datum kreiranja</th>
						<th>Otkazi rezervaciju</th>
					</tr>
		
		
					<c:forEach items="${karte}" var="k">
						<tr>
							<td>
								<a href="/Aerodrom/admin/getLetInfoAdministrator?idLet=${k.let.idLet}">${k.let.aerodrom1.mesto} - ${k.let.aerodrom2.mesto}</a>
							</td>
							<td>
								<a href="/Aerodrom/admin/getInfoPutnik?idPutnik=${k.putnik.idPutnik}">${k.putnik.ime} ${k.putnik.prezime}</a>
							</td>
							<td>red: ${k.mesto.red}, broj: ${k.mesto.broj}</td>
							<td>${k.opis}</td>
							<td>${k.cena}e </td>
							<td>${k.datum}</td>
							<td>
								<a href="/Aerodrom/admin/izbrisiRezervaciju?idKarta=${k.idKarta}">Potvrdi</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</div>
	</div>
	
</body>
</html>