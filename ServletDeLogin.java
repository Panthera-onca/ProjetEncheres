package fr.eni.encheres.servlets;

import fr.eni.encheres.encheres.BusinessException;
import fr.eni.encheres.bll.UserManager;
import fr.eni.encheres.bo.userBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/ServletDeLogin")
public class ServletDeLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		userBean user = new userBean();
	    List<Integer> listErreur = new ArrayList<>();
	    UserManager userManager = new UserManager();
	    user.setPseudo(request.getParameter("userID"));
        user.setMoteDePasse(request.getParameter("password")); 
        
        if (user.getPseudo().trim().isEmpty() || user.getMoteDePasse().trim().isEmpty()) {
        	listErreur.add(CodesErreurServlet.CHAMPS_VIDES_SERVLETS);
        }

        // Si la liste d'erreur n'est pas vide, on renvoie directement à la page login avec la liste d'erreur en attribut
        if (listErreur.size() > 0) {
            request.setAttribute("errorList", listErreur);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            rd.forward(request, response);
        } else {
        	 try {
                 userManager.verificationLogin(user);
                 user = userManager.getUtilisateurInfor(request.getParameter("userID"));
                 user.setConnected(true);
                 HttpSession session = request.getSession();
                 session.setAttribute("user", user);

                 RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
                 rd.forward(request, response);
        }catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("errorList", e.getListeCodesErreur());
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            rd.forward(request, response);
        }
    }
	     
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		doGet(request, response);
	}

}
