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
<title>Informacije o letu</title>
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
			
			<c:if test="${promena}">
				<div class="success">
				  	<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
				  	Promena je uspesno sacuvana.
				</div>
			</c:if>
			
			<c:if test="${!empty poruka}">
				<div class="alert">
				  	<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
				  	${poruka}
				</div>
			</c:if>
			
			<c:if test="${!empty letForInfo}">
				<form action="/Aerodrom/admin/sacuvajPromeneZaLet?idLet=${letForInfo.idLet}" method="post">
					<table class="tablePadd">
					
						<tr>
							<td>
								<a href="/Aerodrom/admin/dodajMesto?idLet=${letForInfo.idLet}">Dodaj slobodna mesta</a>
							</td>
						</tr>
					
						<tr>
							<td>Polazak iz mesta:</td>
							<td>${letForInfo.aerodrom1.mesto}</td>
						</tr>
						
						<tr>
							<td>Datum polaska:</td>
							<td>${letForInfo.datumPolaska}</td>
							<td>Novi datum polaska:</td>
							<td><input type="date" name="datumPolaska"></td>
						</tr>
						
						<tr>
							<td>Vreme polaska:</td>
							<td>${letForInfo.vremePolaska}</td>
							<td>Novo vreme polaska:</td>
							<td><input type="text" name="vremePolaska"></td>
						</tr>
						
						<tr>
							<td>Krajnja destinacija:</td>
							<td>${letForInfo.aerodrom2.mesto}</td>
						</tr>
						
						<tr>
							<td>Datum dolaska:</td>
							<td>${letForInfo.datumDolaska}</td>
							<td>Novi datum dolaska:</td>
							<td><input type="date" name="datumDolaska"></td>
						</tr>
						
						<tr>
							<td>Predvieno trajanje leta je:</td>
							<td>${letForInfo.trajanjeLeta}</td>
						</tr>
						
						<c:if test="${!empty presedanja}">
							<td>Presedanje na sledecim aerodromima:</td>
							<td>
								<c:forEach items="${presedanja}" var="p">
									[${p.mesto}] 
								</c:forEach>
							</td>
						</c:if>
						
						<tr>
							<td>Cena leta iznosi:</td>
							<td>${letForInfo.cena}</td>
							<td>Nova cena:</td>
							<td><input type="text" name="cena"></td>
						</tr>
						
						<tr>
							<td>Kompanija:</td>
							<td>
								${letForInfo.kompanija.naziv}
							</td>
							<td>
								<a href="/Aerodrom/letovi/getRecenzije?idKompanija=${letForInfo.kompanija.idKompanija}">Pregled recenzija</a>
							</td>
						</tr>
						
						<!-- <tr>
							<td>Rezervisi kartu klikom na dugme!</td>
							<td>
								<a href="/Aerodrom/letovi/rezervacija?idLet=${letForInfo.idLet}">Rezervisi</a>
							</td>
						</tr> -->
						
						<tr>
							<td>Brisanje leta:</td>
							<td>
								<a href="/Aerodrom/admin/izbrisiLet?idLet=${letForInfo.idLet}">Potvrdi</a>
							</td>
						</tr>
						
					</table>
					<input type="submit" value="Sacuvaj promene" class="btn-submit">
				</form>
					
			</c:if>
		</div>
	</div>

</body>
</html>