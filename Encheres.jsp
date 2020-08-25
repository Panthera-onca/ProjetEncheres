<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessages" %>
<%@ page import="fr.eni.encheres.bo.retraiteBean" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Encheres</title>
</head>
<body>

<h3>Les details de la vente</h3>

<c:if test="${article.getEtatVente() == 'ET' && article.getBid().getVendeur() == user.getPseudo()}">

            <p>Vous avez obtenu l'article </p>

 </c:if>
  <c:if test="${article.getEtatVente() == 'ET' && article.getVendeur().getPseudo() == user.getPseudo()}">

            <p>${article.getBid().getVendeur()} a obtenu l'article</p>

</c:if>

        <p>${article.getNomArticle()}</p>

        <p>Description : ${article.getDescription()}</p>

        <p>Catégorie : ${article.getNoCategory().getLibelle()}</p>
        
        <p>Meilleure offre:

            <c:choose>

                <c:when test="${article.getPrixVente() == 0}">

                    Il n'y a pas des offres actuellement
 
                </c:when>

                <c:when test="${article.getEtatVente() == 'ET' && article.getBid().getVendeur() == user.getPseudo()}">

                    ${article.getPrixVente()}

                </c:when>

                <c:otherwise>
            <a href="${pageContext.request.contextPath}/profil?pseudo=${element.getVendeur().getPseudo()}">${element.getVendeur().getPseudo()}</a>

                ${article.getSalePrice()} par <a href="${pageContext.request.contextPath}/profil?pseudo=${article.getBid().getVendeur().getPseudo()}">${article.getBid().getVendeur().getPseudo()}</a></p>

                </c:otherwise>

            </c:choose>
        
<p>Mise à prix : ${article.getMiseAPrix()}</p>

<p>Fin de l'enchère : ${article.getDateFinEncheres()}</p>

<p>Retrait : ${article.getRetraite().toString()}</p>

<p>Vendeur : ${article.getVendeur().getPseudo()}</p>

<c:if test="${article.getEtatVente() == 'EC'}">

            <form action="${pageContext.request.contextPath}/enchere" method="post">

                <label for="prix">Ma proposition:</label>

                <input type="number" id="prix" name="bidAmount" step="10" min="${article.getPrixVente()}" value="${article.getPrixVente()}"><br><br>

                <input type="submit" value="Enchérir">

            </form>
            <form method="post" action="${pageContext.request.contextPath}/search?searchcrit=achats&categories=toutes&keyword=&status=EC">

            <input type="submit" value="Back">

        </form>

</c:if>
<%
            List<Integer> errorList = (List<Integer>) request.getAttribute("errorList");
            if (errorList != null)
            {
                for (int code : errorList) {
%>
<p><%= LecteurMessages.getMessageErreur(code) %></p>
        <br>
        <%
                }
            }
        %>
</body>
</html>