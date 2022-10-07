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
<title>Informacije o putniku</title>
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
		
			<c:if test="${azurirano}">
				<div class="success">
					<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
					Uspesno izmenjeni podaci.
				</div>
			</c:if>
			
			<c:if test="${!empty poruka}">
				<div class="alert">
					<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
					${poruka}
				</div>
			</c:if>

			<form action="/Aerodrom/putnik/azuriraj?idP=${putnik.idPutnik}&idK=${kartica.idPlatnaKartica}" method="post">
				<c:if test="${!empty putnik}">
					<table class="tablePadd">
						<tr>
							<td>Ime:</td>
							<td>${putnik.ime}</td>
							<td>Novo ime:</td>
							<td><input type="text" name="ime"></td>
						</tr>
						
						<tr>
							<td>Prezime:</td>
							<td>${putnik.prezime}</td>
							<td>Novo ime:</td>
							<td><input type="text" name="Prezime"></td>
						</tr>
						
						<tr>
							<td>E-mail:</td>
							<td>${putnik.email}       </td>
							<td>Novi e-mail:</td>
							<td><input type="text" name="email"></td>
						</tr>
						
						<tr>
							<td>Kontakt telefon:</td>
							<td>${putnik.brojTelefona}</td>
							<td>Novi kontakt broj:</td>
							<td><input type="text" name="brojTelefona"></td>
						</tr>
						
						<tr>
							<td>Adresa:</td>
							<td>${putnik.adresa}</td>
							<td>Nova adresa:</td>
							<td><input type="text" name="adresa"></td>
						</tr>
						
						<tr>
							<td>Adresa:</td>
							<td>${putnik.grad}</td>
							<td>Novi grad:</td>
							<td><input type="text" name="grad"></td>
						</tr>
						
						<tr>
							<td>Datum rodjenja:</td>
							<td>${putnik.datumRodjenja}</td>
						</tr>
						
						<tr>
							<td>Broj kartice:</td>
							<td>${kartica.brojKartice}</td>
							<td>Novo broj:</td>
							<td><input type="text" name="brojKartice"></td>
						</tr>
						
						<tr>
							<td>cvv:</td>
							<td>${kartica.cvv}</td>
							<td>Novi cvv:</td>
							<td><input type="text" name="cvv"></td>
						</tr>
						
						<tr>
							<td>Datum isteka:</td>
							<td>${kartica.datumIsteka}</td>
							<td>Novi datum isteka:</td>
							<td><input type="text" name="datumIsteka"></td>
						</tr>
						
					</table>
					<br>
					<input type="submit" value="Sacuvaj" class="btn-submit">
						
				</c:if>
				<c:if test="${empty putnik}">
					<div class="alert">
						<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
						Nemate registrovane podatke.
					</div>
					<h3>
						<a href="/Aerodrom/putnik/unesiPodatke">Unesi podatke</a>
					</h3>
				</c:if>
			</form>
		</div>
	</div>

</body>
</html>