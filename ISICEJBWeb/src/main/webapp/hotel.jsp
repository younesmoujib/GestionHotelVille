<%@ page import="entities.Hotel"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gestion des Hôtels</title>
<!-- Ajout des liens vers Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
     <%@include file="Sidebar.jsp" %>
	<div class="container mt-3">
		<h1>Ajouter une hotel </h1>

		<form action="HotelController" method="post" class="mt-3">
			<div class="form-group">
				<label for="nom">Nom :</label> <input type="text" name="nom"
					id="nom" class="form-control" required
					value="${empty hotel ? '' : hotel.nom}" />
			</div>
			<div class="form-group">
				<label for="adresse">Adresse :</label> <input type="text"
					name="adresse" id="adresse" class="form-control" required
					value="${empty hotel ? '' : hotel.adresse}" />
			</div>
			<div class="form-group">
				<label for="telephone">Téléphone :</label> <input type="text"
					name="telephone" id="telephone" class="form-control" required
					value="${empty hotel ? '' : hotel.telephone}" />
			</div>
			<div class="form-group">
    <label for="ville">Ville :</label>
    <select name="ville" id="ville" class="form-control">
        <option value="-1">Select Ville</option>
        <c:forEach items="${villes}" var="v">
            <option value="${v.id}" ${!empty hotel && hotel.ville.id == v.id ? 'selected' : ''}>${v.nom}</option>
        </c:forEach>
    </select>
</div>
			


			<c:choose>
				<c:when test="${empty hotel}">
					<button type="submit" name="action" value="ajouter"
						class="btn btn-primary">Enregistrer</button>
				</c:when>
				<c:otherwise>
					<button type="submit" name="action" value="modifier"
						class="btn btn-warning">Modifier</button>
					
					<input type="hidden" name="id" value="${hotel.id}" />
				</c:otherwise>
			</c:choose>
		</form>

		<h1 class="mt-3">Liste des hôtels  </h1>
		<table class="table">
			<thead>
				<tr>
					<th>ID</th>
					<th>Nom</th>
					<th>Adresse</th>
					<th>Téléphone</th>
					<th>Ville</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${hotels}" var="h">
					<tr>
						<td>${h.id}</td>
						<td>${h.nom}</td>
						<td>${h.adresse}</td>
						<td>${h.telephone}</td>
						<td>${h.ville.nom}</td>
						<td><a href="#" onclick="modifierHotel(${h.id})"
							class="btn btn-warning btn-sm">Modifier</a> <a href="#"
							onclick="confirmDelete(${h.id})" class="btn btn-danger btn-sm">Supprimer</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- Ajout des liens vers Bootstrap JS et jQuery (si besoin) -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
	<script>
        function confirmDelete(hotelId) {
            Swal.fire({
                title: 'Confirmation de suppression',
                text: 'Êtes-vous sûr de vouloir supprimer cet Hôtel ?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Oui, supprimer',
                cancelButtonText: 'Annuler'
            }).then((result) => {
                if (result.isConfirmed) {
                    // L'utilisateur a confirmé la suppression, redirigez vers la page de suppression
                    window.location.href = 'HotelController?action=supprimer&id=' + hotelId;
                }
            });
        }

        function modifierHotel(hotelId) {
            window.location.href = 'HotelController?action=modifier&id=' + hotelId;
        }
    </script>
</body>
</html>
