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
<title>Pregled recenzija</title>
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
	
			<c:if test="${!empty recenzije}">
				<c:if test="${!empty prosecnaOcena}">
					Prosecna ocena kompanije ${kompanija} je ${prosecnaOcena}<br>
				</c:if>
			
				<table border="1">
					<tr>
						<th>Sadrzaj</th>
						<th>Ocena</th>
						<th>Objavljeno</th>
					</tr>
					
					
					<c:forEach items="${recenzije}" var="r">
						<tr>
							<td>${r.sadrzaj}</td>
							<td>${r.ocena}</td>
							<td>${r.datum} - ${r.putnik.ime}</td>
						</tr>
					</c:forEach>
					
		
				</table>
				
			</c:if>
			<c:if test="${empty recenzije}">
				<div class="alert">
				  	<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
				  	Za kompaniju ${kompanija} nema evidentiranih recenzija.<br>
				</div>
				
			</c:if>
		</div>
	</div>
	
</body>
</html>