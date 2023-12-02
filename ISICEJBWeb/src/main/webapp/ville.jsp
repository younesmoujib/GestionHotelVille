<%@ page import="entities.Ville" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Gestion des Villes</title>
    <!-- Ajout des liens vers Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
    <%@include file="Sidebar.jsp" %>
    <div class="container mt-3">
        <h1>Ajouter une ville</h1>

        <form action="VilleController" method="post" class="mt-3">
            <div class="form-group">
                <label for="ville">Nom :</label>
                <input type="text" name="ville" id="ville" class="form-control" required
                    value="${empty ville ? '' : ville.nom}" />
            </div>

            <c:choose>
                <c:when test="${empty ville}">
                    <button type="submit" name="action" value="ajouter" class="btn btn-primary">Enregistrer</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" name="action" value="modifier" class="btn btn-warning">Modifier</button>
                    <input type="hidden" name="id" value="${ville.id}" />
                </c:otherwise>
            </c:choose>
        </form>

        <h1 class="mt-3">Liste des villes </h1>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${villes}" var="v">
                    <tr>
                        <td>${v.id}</td>
                        <td>${v.nom}</td>
                        <td>
                            <a href="#" onclick="modifierVille(${v.id})" class="btn btn-warning btn-sm">Modifier</a>
                            <a href="#" onclick="confirmDelete(${v.id})" class="btn btn-danger btn-sm">Supprimer</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
    <script>
        // Placez le code JavaScript ici
        function confirmDelete(villeId) {
            Swal.fire({
                title: 'Confirmation de suppression',
                text: 'Êtes-vous sûr de vouloir supprimer ce Ville ?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Oui, supprimer',
                cancelButtonText: 'Annuler'
            }).then((result) => {
                if (result.isConfirmed) {
                    // L'utilisateur a confirmé la suppression, redirigez vers la page de suppression
                    window.location.href = 'VilleController?action=supprimer&id=' + villeId;
                }
            });
        }
    </script>

    <script>
       

        function modifierVille(villeId) {
            window.location.href = 'VilleController?action=modifier&id=' + villeId;
        }
    </script>
</body>
</html>