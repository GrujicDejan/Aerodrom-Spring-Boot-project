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
<title>Unos novog leta</title>
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

		<c:if test="${sacuvanA}">
			<div class="success">
				<span class="closebtn"
					onclick="this.parentElement.style.display='none';">&times;</span>
				Aerodrom je uspesno sacuvan.
			</div>
		</c:if>

		<c:if test="${!empty porukaAerodrom}">
			<div class="alert">
				<span class="closebtn"
					onclick="this.parentElement.style.display='none';">&times;</span>
				${porukaAerodrom}
			</div>
		</c:if>
		
		<c:if test="${!empty porukaLet}">
			<div class="alert">
				<span class="closebtn"
					onclick="this.parentElement.style.display='none';">&times;</span>
				${porukaLet}
			</div>
		</c:if>

		<div class="center" style="font-size: 25px; text-align: center;">

			<form action="/Aerodrom/admin/sacuvajLet" method="post">
				
				<table>
					<tr>
						<td>
							<a href="/Aerodrom/unos/UnosAerodroma.jsp">Dodaj aerodrom</a>
						</td>
					</tr>
				
					<tr>
						<td>Odlazni aerodrom:</td>
						<td>
							<select name="dolazniA">
								<c:forEach items="${aerodromi}" var="a">
									<option value="${a.idAerodrom}">${a.naziv}, ${a.mesto}</option>
								</c:forEach>
							</select>
						<td>
					</tr>
		
					<tr>
						<td>Dolazni aerodrom:</td>
						<td>
							<select name="odlazniA">
								<c:forEach items="${aerodromi}" var="a">
									<option value="${a.idAerodrom}">${a.naziv}, ${a.mesto}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					
					<tr>
						<td>Kompanija:</td>
						<td>
							<select name="idKompanija">
								<c:forEach items="${kompanije}" var="k">
									<option value="${k.idKompanija}">${k.naziv}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					
					<tr>
						<td>Vreme polaska:</td>
						<td><input type="text" name="vremePolaska"></td>
					</tr>
		
					<tr>
						<td>Trajanje leta:</td>
						<td><input type="text" name="trajanjeLeta"></td>
					</tr>
					
					<tr>
						<td>Cena leta:</td>
						<td><input type="text" name="cena"></td>
					</tr>
		
					<tr>
						<td>Datum polaska:</td>
						<td><input type="date" name="datumPolaska"></td>
					</tr>
					
					<tr>
						<td>Datum dolaska:</td>
						<td><input type="date" name="datumDolaska"></td>
					</tr>
					
					<tr>
						<td><input type="submit" value="Sacuvaj" class="btn-submit"></td>
					</tr>
		
				</table>
			</form>
		</div>
	</div>
</body>
</html>