package fr.eni.encheres.bll;

import fr.eni.encheres.encheres.BusinessException;
import fr.eni.encheres.bo.userBean;
import fr.eni.encheres.bll.CodesErreur;
import fr.eni.encheres.dal.CodesErreurDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UserDAO;
import fr.eni.encheres.dal.UserDAOJdbcImpl;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {
	private UserDAO user;
	
	public UserManager() {
		this.user = DAOFactory.getUserDAO();
	}
	
	public userBean verificationLogin(userBean login) throws BusinessException {
        return this.user.checkID(login);
    }
	
	private void validerTousLesChamps(Map parametres, BusinessException businessException) {
        for (Object o : parametres.keySet()) {
            String key = (String) o;
            String value = ((String[]) parametres.get(key))[0];
            if (value.trim().isEmpty() && !key.equals("telephone")) {
            	businessException.ajouterErreur(CodesErreur.CHAMPS_VIDE_ERREUR);
                break;
            }
        }
    }
	
	private void validerFormatPseudo1(Map parametres, BusinessException businessException) {
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        Matcher m = p.matcher((CharSequence) parametres);
        while(m.find()) {
            if(!m.matches()) {
            	businessException.ajouterErreur(CodesErreur.ERREUR_FORMAT_PSEUDO);
                break;
            }
        }

    }
	
	public userBean ajouterUtilisateur(Map parametres, String pseudo, String email, userBean utilisateur, HttpServletRequest req) throws BusinessException{
		BusinessException businessException = new BusinessException();
		this.validerTousLesChamps(parametres, businessException);
		this.validerFormatPseudo1(parametres, businessException);
		if (!req.getParameter("moteDePasse").equals(req.getParameter("confirmation"))) {
			businessException.ajouterErreur(CodesErreur.ERREUR_SAISIE_MDP);
            throw businessException;
        }
		if (!businessException.hasErreurs()) {
            this.validerPseudo(pseudo, businessException);
            this.validerEmail(email);
            user.insert(utilisateur);
        } else {
            throw businessException;
        }

        return utilisateur;
		
	}
	

	public userBean misAJourDUtilisateur(Map parametres, userBean utilisateurCourant, userBean modifs, HashMap<String, String> mdp, HttpServletRequest req) throws BusinessException{
		BusinessException businessException = new BusinessException();
		this.validerTousLesChamps(parametres, businessException);
		this.validerFormatPseudo(modifs, businessException);
		
		if (!req.getParameter("nouveaumdp").equals(req.getParameter("confirmation"))) {
			businessException.ajouterErreur(CodesErreur.ERREUR_SAISIE_MDP);
            throw businessException;
        }
        if (!businessException.hasErreurs()) {
            if (!modifs.getPseudo().equals(utilisateurCourant.getPseudo())) {
                this.validerPseudo(modifs.getPseudo(), businessException);
            }
            if (!modifs.getEmail().equals(utilisateurCourant.getEmail())) {
                this.validerEmail(modifs.getEmail());
            }

            if (!modifs.getMoteDePasse().equals(utilisateurCourant.getMoteDePasse())) {
            	businessException.ajouterErreur(CodesErreurDAO.ECHEC_VALIDATION_MOTDEPASSE);
                throw businessException;
            }
            modifs.setNoUtilisateur(utilisateurCourant.getNoUtilisateur());
            modifs.setCredit(utilisateurCourant.getCredit());
            user.misAJourDUtilisateur(modifs);

        } else {
            throw businessException;
        }

        return modifs;
		
		
	}
	private void validerFormatPseudo(userBean modifs, BusinessException businessException) {
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        Matcher m = p.matcher((CharSequence) modifs);
        while(m.find()) {
            if(!m.matches()) {
            	businessException.ajouterErreur(CodesErreur.ERREUR_FORMAT_PSEUDO);
                break;
            }
        }

    }

	private void validerPseudo(String pseudo, BusinessException businessException) throws BusinessException {
		this.user.checkPseudo(pseudo);
		
	}
	
	public void validerEmail(String email) throws BusinessException {
        this.user.checkEmail(email);
    }
	
	public userBean afficherUtilisateurInfoPrive(String pseudo) throws BusinessException {
        return this.user.selectUserPublicInfo(pseudo);
    }
	
	public userBean getUtilisateurInfor(String pseudo) throws BusinessException {
        return this.user.selectUserPrivateInfo(pseudo);
    }
	
	public boolean verificationMoteDePasse(userBean user) throws BusinessException {
        return this.user.moteDePasseisValid(user);
    }
	public void deleteUtilisateur(userBean user) throws BusinessException {
        this.user.deleteUtilisateur(user.getNoUtilisateur());
    }

}
