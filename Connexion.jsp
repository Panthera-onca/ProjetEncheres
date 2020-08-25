<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessages" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Connexion</title>
</head>
<body>



<form name="login" action="${pageContext.request.contextPath}/login" method="post">
        <label>Identifiant : <input type="text" name="userID"></label>
        <br>
        <label>Mot de passe : <input type="password" name="password"></label>
        <br>
        <%
            List<Integer> listErreur = (List<Integer>) request.getAttribute("errorList");
            if (listErreur != null){
                for (int code : listErreur) { 
        %> 
        <p><%= LecteurMessages.getMessageErreur(code)  %></p>
        <br>
        <%
            }
            }
        %>
<input type="submit" value="Connexion">
        <label for="rememberme">
            <input type="checkbox" name="rememberme" id="rememberme">
            Se souvenir de moi
        </label>
        <a href="">Mot de passe oublié</a>
    </form>
    <form action="${pageContext.request.contextPath}/signup" method="get">
        <input type="submit" value="Créer un compte">
    </form>
<c:choose>
    <c:when test="${!empty user}" >
        <p>${user.getPseudo()} vous êtes connecté</p>
    </c:when>
</c:choose>

</body>
</html>