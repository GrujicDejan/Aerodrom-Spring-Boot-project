<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/styles/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registrovanje</title>
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
		<div class="center" style="font-size: 20px; text-align: left;">

			<sf:form modelAttribute="user" action="register" method="post">
				<table>
					<tr>
						<td>Korisnicko ime: </td>
						<td><sf:input path="korisnickoIme" /></td>
					</tr>
					<tr>
						<td>Sifra: </td>
						<td><sf:password path="sifra" /></td>
					</tr>
					<tr>
						<td>Uloga</td>
						<td><sf:select path="ulogas" items="${roles}"
								itemValue="idUloga" itemLabel="naziv" /></td>
					</tr>
					<tr>
						<td />
						<td><input type="submit" value="Sacuvaj"  class="btn-submit">
					</tr>
				</table>
			</sf:form>
		</div>
	</div>
</body>
</html>