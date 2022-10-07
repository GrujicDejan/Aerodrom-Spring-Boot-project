<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
<meta charset="ISO-8859-1">
<title>Unos presedanja</title>
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
			<c:if test="${sacuvanLet}">
				<c:if test="${sacuvanoP}">
					<div class="success">
  						<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
						Presedanje je uspesno sacuvano.
					</div>
				</c:if>
				
				<form action="/Aerodrom/admin/dodajPresedanje" method="post">
					<table>
			
						<tr>
							<td>Unos presedanja za let ${letSave}:</td><br>
							<td>
								<select name="idAerodrom">
									<c:forEach items="${aerodromi}" var = "a">
										<option value="${a.idAerodrom}">${a.naziv}, ${a.mesto}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input type="submit" value="Dodaj presedanje" class="btn-submit">
							</td>
						</tr>
			
						<tr>
							<td>
								<a href="/Aerodrom">Nastavi dalje</a>
							</td>
						</tr>
			
					</table>
				</form>
			</c:if>

			<c:if test="${!sacuvanLet}">
				<div class="alert">
					<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
					Let nije uspesno sacuvan. PokusajOpet.
				</div>
			</c:if>
		</div>
	</div>
	
</body>
</html>