package fr.eni.encheres.dal;

import fr.eni.encheres.encheres.BusinessException;
import fr.eni.encheres.bo.userBean;

public interface UserDAO{
	public void insert(userBean Utilisateur) throws BusinessException;
	public userBean checkID(userBean log) throws BusinessException;
	public void checkPseudo(String pseudo) throws BusinessException;
	public void checkEmail(String email) throws BusinessException;
	public userBean selectUserPublicInfo(String pseudo) throws BusinessException;
	public userBean misAJourDUtilisateur(userBean Utilisateur) throws BusinessException;
	public boolean moteDePasseisValid(userBean login) throws BusinessException;
	public void deleteUtilisateur(int noUtilisateur) throws BusinessException;
	public userBean selectUserPrivateInfo(String pseudo) throws BusinessException;
	

}
