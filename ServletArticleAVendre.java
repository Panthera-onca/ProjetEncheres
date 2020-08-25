package fr.eni.encheres.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.VenteManager;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.bo.articleBean;
import fr.eni.encheres.bo.retraiteBean;
import fr.eni.encheres.bo.userBean;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/ServletArticleAVendre")
public class ServletArticleAVendre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 VenteManager saleManager = new VenteManager();
	     articleBean article = new articleBean();
	     List<Integer> listErreur = new ArrayList<>();
	     
	     HttpSession session = request.getSession();
	     userBean user = new userBean();
	     user = (userBean) session.getAttribute("user");
	     Map parametres = request.getParameterMap();
	     
	     article.setNomArticle(request.getParameter("article"));
	     article.setDescription(request.getParameter("description"));
	     article.setLibelle(new Category(0, request.getParameter("libelle")));
	     System.out.println("Libelle: " + request.getParameter("libelle"));
	     try {
	            article.setMiseAPrix(Integer.parseInt(request.getParameter("prix")));

	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	            listErreur.add(CodesErreurServlet.CHAMPS_VIDES_SERVLETS);
	        }
	     
	     try {
	            article.setDateDebutEncheres(LocalDateTime.parse(request.getParameter("salestart")));
	            article.setDateFinEncheres(LocalDateTime.parse(request.getParameter("saleend")));
	        } catch (Exception e) {
	            e.printStackTrace();
	            if (listErreur.size() == 0) {
	            	listErreur.add(CodesErreurServlet.CHAMPS_VIDES_SERVLETS);
	            }
	        }
	     
	     if (listErreur.size() > 0) {
	            request.setAttribute("errorList", listErreur);
	            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forsale.jsp");
	            rd.forward(request, response);
	        }

	        article.setRetrait(new retraiteBean());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	} 

}
