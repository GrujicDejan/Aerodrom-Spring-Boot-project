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
<title>Unos podataka</title>
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
	
		<c:if test="${uspesno}">
			<div class="success">
			  	<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
			 	Podaci su uspesno sacuvani.
			</div>
		</c:if>
		
		<c:if test="${!empty poruka}">
			<div class="alert">
			  	<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
			 	${poruka}
			</div>
		</c:if>
		
		<div class="center" style="font-size: 25px; text-align: center;">

			<c:if test="${empty registrovan}">
		
				<form action="/Aerodrom/putnik/sacuvajPodatke" method="post">
			
					<table>
						<h1>Infromacije o putniku:</h1>
						<tr>
							<td>Ime:</td>
							<td><input type="text" name="ime"></td>
						</tr>
						
						<tr>
							<td>Prezime:</td>
							<td><input type="text" name="prezime"></td>
						</tr>
						
						<tr>
							<td>Broj telefona:</td>
							<td><input type="text" name="brojTelefona"></td>
						</tr>
						
						<tr>
							<td>E-mail:</td>
							<td><input type="text" name="email"></td>
						</tr>
						
						<tr>
							<td>Adresa:</td>
							<td><input type="text" name="adresa"></td>
						</tr>
						
						<tr>
							<td>Grad:</td>
							<td><input type="text" name="grad"></td>
						</tr>
						
						<tr>
							<td>Datum rodjenja:</td>
							<td><input type="date" name="datumRodjenja"></td>
						</tr>
			
					</table>
					
					<table>
						<h1>Informacije o placanju</h1>
						<tr>
							<td>Broj kartice:</td>
							<td><input type="text" name="brojKartice"></td>
						</tr>
						
						<tr>
							<td>cvv:</td>
							<td><input type="text" name="cvv"></td>
						</tr>
						
						<tr>
							<td>Datum isteka [mm/gg]:</td>
							<td><input type="text" name="datumIsteka"></td>
						</tr>
					</table>
			
					<input type="submit" value="Potvrdi" class="btn-submit"><br>
				</form>
			</c:if>
			<c:if test="${!empty registrovan}">
			
				<c:if test="${!uspesno}">
					<div class="alert">
					  	<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
					 	Vec ste uneli podatke.
					</div>
				</c:if>
				
			</c:if>
		</div>
	</div>

</body>
</html>