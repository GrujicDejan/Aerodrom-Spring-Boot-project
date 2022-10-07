<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/styles/style.css">
<meta charset="UTF-8">
<title>Prijava</title>
</head>


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

			<c:url var="loginUrl" value="/login" />
			<c:if test="${not empty param.error}">
				<div class="alert alert-danger">
					<p>Pogresni podaci.</p>
				</div>
			</c:if>
			<form action="${loginUrl}" method="post">
				<table>
					<tr>
						<td>Korisnicko ime: </td>
						<td><input type="text" name="username"
							placeholder="Unesite korisnicko ime" required></td>
					</tr>
					<tr>
						<td>Sifra: </td>
						<td><input type="password" name="password"
							placeholder="Unesite sifru" required></td>
					</tr>
					<tr>
						<td><input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" /></td>
						<td><input type="submit" value="Prijava" class="btn-submit"></td>
					</tr>
				</table>
				<br />
				<br /> Nemate nalog? <a href="/Aerodrom/auth/registerUser">Registrujte se</a>
			</form>
		</div>
	</div>

</body>
</html>