<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessages" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insciption</title>
</head>
<body>
<div class="container">
<form action="${pageContext.request.contextPath}/signup" method="post">
             <div class="form-row">
                    <div class="col-sm-12 col-md-6 col-lg-6">
                        <label class="col-form-label" for="pseudo">Pseudo </label>
                        <input class="form-control" type="text" id="pseudo" name="pseudo" >
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-6 nom">
                        <label for="nom">Nom </label>
                        <input class="form-control" type="text" id="nom" name="nom" >
                    </div>
             </div>
             
             <div class="form-row">
                    <div class="col-sm-12 col-md-6 col-lg-6">
                        <label class="col-form-label" for="prenom">Prénom </label>
                        <input class="form-control" type="text" id="prenom" name="prenom" >
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-6">
                        <label class="col-form-label" for="email">Email </label>
                        <input class="form-control" type="text" id="email" name="email" >
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-sm-12 col-md-6 col-lg-6">
                        <label class="col-form-label" for="telephone">Téléphone </label>
                        <input class="form-control" type="text" id="telephone" name="telephone" >
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-6">
                        <label class="col-form-label" for="address">Adress </label>
                        <input class="form-control" type="text" id="address" name="Address" >
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-sm-12 col-md-6 col-lg-6">
                        <label class="col-form-label" for="codePostal">Code postal </label>
                        <input class="form-control" type="text" id="codePostal" name="codePostal" >
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-6">
                        <label class="col-form-label" for="ville">Ville </label>
                        <input class="form-control" type="text" id="ville" name="ville" >
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-sm-12 col-md-6 col-lg-6">
                        <label class="col-form-label" for="moteDePasse">Mot de passe</label>
                        <input class="form-control" type="password" id="moteDePasse" name="moteDePasse" >
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-6">
                        <label class="col-form-label" for="confirmation">Confirmation </label>
                        <input class="form-control" type="password" id="confirmation" name="confirmation" >
                    </div>
                </div>
                <div class="form-row " >
                    <div class="col-sm-4 offset-sm-4 text-center btn-creer">
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Créer</button>
                    </div>
                </div>
            </form>
            <div class="form-row">
                <div class="col-sm-4 offset-sm-4 text-center btn-supprimer">
                    <form action="${pageContext.request.contextPath}/" method="get">
                        <button class="btn btn-lg btn-primary btn-block" type="submit" >Annuler</button>
                    </form>
                </div>
            </div>
            <div>
                <div class="form-row" >
                    <div class="col-sm-4 offset-sm-4 text-center">
                        <%
                            List<Integer> listErreur = (List<Integer>) request.getAttribute("errorList");
                            if (listErreur != null)
                            {
                                for (int code : listErreur) {
                        %>
                        <p><%= LecteurMessages.getMessageErreur(code) %></p>
                        <br>
                        <%
                                }
                            }
                        %>
                    </div>
                </div>
            </div>
             <small id="emailHelp" class="form-text text-muted">Tous les champs sont obligatoires</small>

</body>
</html>