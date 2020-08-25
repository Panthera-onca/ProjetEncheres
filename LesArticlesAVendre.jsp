<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessages" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Les Articles a vendre</title>
</head>
<body>


        <main>

            <div class="container">
                <h3 class="text-center">Nouvelle vente</h3>

                <form id="saleform" action="${pageContext.request.contextPath}/nouvellevente" method="post">
                    <div class="form-group">
                        <label for="article">Article</label>
                        <input type="text" name="article" class="form-control" id="article" <c:if test="${added}">value="${article.getNomArticle()}"</c:if>>
                    </div>
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea class="form-control" id="description" name="description" rows="3" ><c:if test="${added}">${article.getDescription()}</c:if></textarea>
                    </div>
                    <div class="form-group">
                        <label for="categorie"> Catégorie</label>
                        <select class="form-control" name="categorie" id="categorie">
                            <option value="Articleavendre" ><c:if test="${added}"><c:if test="${article.getNomArticle() == 'Informatique'}">selected</c:if></c:if> >Article à vendre</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label" for="photo">Photo de l'article </label>
                        <input class="form-control-file" type="file" id="photo" name="photo">
                    </div>
                    <div class="form-group">
                        <label for="prix">Points:</label>
                        <input class="form-control" type="number" id="prix" name="prix" step="10" min="0" <c:if test="${added}">value="${article.getMiseAPrix()}"</c:if> >

                    </div>
                    <div class="form-group">
                        <label for="salestart">Début de l'enchère</label>
                        <input class="form-control" type="datetime-local" id="salestart" name="salestart" <c:if test="${added}">value="${article.getDateDebutEncheres()}"</c:if>>
                    </div>
                    <div class="form-group">
                        <label for="saleend">Fin de l'enchère</label>
                        <input class="form-control" type="datetime-local" name="saleend" id="saleend" <c:if test="${added}">value="${article.getDateFinEncheres()}"</c:if>>
                    </div>
                    <fieldset class="form-group">
                        <legend>Retrait</legend>
                        <div class="form-group">
                            <label for="rue">Rue</label>
                            <input class="form-control" id="rue" type="text" name="rue" value="${user.getRue()}" <c:if test="${added}">value="${article.retraiteBean().getAddress()}"</c:if>>
                        </div>
                        <div class="form-group">
                            <label for="cpo">Code postal</label>
                            <input class="form-control" id="cpo" type="text" name="cpo" value="${user.getCpo()}" <c:if test="${added}">value="${article.retraiteBean().getCodePostal()}"</c:if>>
                        </div>
                        <div class="form-group">
                            <label for="ville">Ville</label>
                            <input class="form-control" id="ville" type="text" name="ville" value="${user.getVille()}" <c:if test="${added}">value="${article.retraiteBean().getVille()}"</c:if>>
                        </div>
                    </fieldset>
                    <div class="form-row " >
                        <div class="col-sm-12 col-md-6 col-lg-6 btn-modifier">
                            <input class="form-control" type="submit" value="Enregistrer">
                        </div>
                    </div>
                </form>

                <div class="form-row">
                    <div class="col-sm-12 col-md-6 col-lg-6 btn-supprimer">
                        <form action="${pageContext.request.contextPath}/" method="get">
                            <input class="form-control" type="submit" name="deletebtn" value="Annuler">
                        </form>
                    </div>
                </div>
                <c:if test="${added}">
                    <div class="form-row">
                        <div class="col-sm-12 col-md-6 col-lg-6 btn-supprimer">
                            <form action="${pageContext.request.contextPath}/" method="get">
                                <input class="form-control" type="submit" name="deletebtn" value="Annuler la vente">
                            </form>
                        </div>
                    </div>

                    <p class="text-center">L'annonce a bien été ajoutée.</p>

                </c:if>

                <%
                    List<Integer> listErreur = (List<Integer>) request.getAttribute("errorList");
                    if (listErreur != null)
                    {
                        for (int code : listErreur) {
                %>
                <p class="text-center"><%= LecteurMessages.getMessageErreur(code) %></p>
                <br>
                <%
                        }
                    }
                %>

            </div>
        

</body>
</html>