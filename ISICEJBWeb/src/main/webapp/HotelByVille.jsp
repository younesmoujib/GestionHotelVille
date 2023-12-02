<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Hotel Search</title>
    <!-- Add Bootstrap CSS link here -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<%@include file="Sidebar.jsp" %>

<div class="container mt-4">
    <h1>Cherecher les hotels </h1>

    <form id="searchForm" action="HotelByVilleController" method="post" class="mt-3">
        <div class="form-group">
            <label for="villeId">Select Ville:</label>
            <select name="villeId" id="villeId" class="form-control" onchange="submitForm()">
                <option value="">Select Ville</option>
                <c:forEach items="${villes}" var="ville">
                    <option value="${ville.id}">${ville.nom}</option>
                </c:forEach>
            </select>
        </div>
    </form>

    <h2 class="mt-4">Hotels</h2>
    
    <c:choose>
       
        <c:when test="${not empty hotels}">
            <ul class="list-group">
                <c:forEach items="${hotels}" var="hotel">
                    <li class="list-group-item">${hotel.nom}</li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            <p class="mt-3">Aucun hôtel trouvé pour la ville sélectionnée.</p>
        </c:otherwise>
    </c:choose>

    <c:if test="${errorMessage}">
        <p style="color: red;" class="mt-4">${errorMessage}</p>
    </c:if>
</div>

<!-- Add Bootstrap JS and jQuery links here -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<script>
    function submitForm() {
        document.getElementById("searchForm").submit();
    }
</script>

</body>
</html>
