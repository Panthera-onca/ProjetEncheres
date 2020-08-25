package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.UserManager;
import fr.eni.encheres.bo.userBean;
import fr.eni.encheres.encheres.BusinessException;

@WebServlet("/ServletConfirmationMiseAjour")
public class ServletConfirmationMiseAjour extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserManager userManager = new UserManager();

        // On récupère les détails de l'utilisateur courant
        HttpSession session = request.getSession();
        userBean utilisateurCourant = (userBean) session.getAttribute("user");

        // On récupère les données du formulaire est les insère dans une hashmap
        HashMap<String, String> moteDePasse = new HashMap<String, String>();

        // Assigne l'ensemble des paramètres à une Map
        Map parametres = request.getParameterMap();

        userBean modifs = new userBean();

        moteDePasse.put("moteDePasse", request.getParameter("moteDePasse"));
        moteDePasse.put("nouveaumoteDePasse", request.getParameter("nouveaumoteDePasse"));
        moteDePasse.put("commit", request.getParameter("commit"));

        modifs.setPseudo(request.getParameter("pseudo"));
        modifs.setNomUtil(request.getParameter("nom"));
        modifs.setPrenomUtil(request.getParameter("prenom"));
        modifs.setEmail(request.getParameter("email").trim());
        modifs.setTelephone(request.getParameter("telephone"));
        modifs.setAddress(request.getParameter("address"));
        modifs.setCodePostal(Integer.parseInt(request.getParameter("codePostal")));
        modifs.setVille(request.getParameter("ville"));
        modifs.setMoteDePasse(request.getParameter("nouveaumoteDePasse"));
        
        try {
            // Si l'update a fonctionné, l'utilisateur est redirigé vers son profil
            userManager.misAJourDUtilisateur(parametres, utilisateurCourant, modifs, moteDePasse, request);

            session.setAttribute("user", modifs);

            request.setAttribute("success", "Les modifications demandées ont bien été prises en compte");

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp");
            rd.forward(request, response);
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("errorList", e.getListeCodesErreur());
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp");
            rd.forward(request, response);

        }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
