<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
<meta charset="ISO-8859-1">
<title>Recenzija</title>
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
			<form action="/Aerodrom/putnik/sacuvajRecenziju" method="post">
				<h1>Unos recenzije za kompaniju ${kompanija.naziv}:</h1>
				<table>
					
					<tr>
						<td>Komentar:</td>
					</tr>
					
					<tr>
						<td><input type="text" name="sadrzaj"></td>
					</tr>
					
					<tr>
						<td>Ocena:</td>
					</tr>
					
					<tr>
						<td><input type="text" name="ocena"></td>
					</tr>
					
					<tr>
						<td><input type="submit" value="Sacuvaj recenziju" class="btn-submit"></td>
					</tr>
					
		
				</table>
			</form>
		</div>
	</div>

</body>
</html>