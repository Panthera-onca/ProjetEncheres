package fr.eni.encheres.dal;

import fr.eni.encheres.encheres.BusinessException;

import java.util.List;

import fr.eni.encheres.bo.Category;
import fr.eni.encheres.bo.userBean;
import fr.eni.encheres.bo.articleBean;
import fr.eni.encheres.bo.beanDEncheres;
import fr.eni.encheres.bo.retraiteBean;

public interface etatVenteDAO {
	public articleBean  insertArticle(articleBean article , userBean utilisateur) throws BusinessException;
	public int selectCategory(int noCategory) throws BusinessException;
	public List<articleBean> selectAllArticles(String nomArticle, String libelle, String etatVente) throws BusinessException;
	public List<articleBean> selectArticlesByName(String nomArticle) throws BusinessException;
	public List<articleBean> selectArticlesByNameandCategory(String libelle, int noCategory) throws BusinessException;
	public int selectCatByName(String libelle) throws BusinessException;
	public List<articleBean> selectOngoingAuctions(int noUtilisateur, String nomUtil, String libelle, String etatVente) throws BusinessException;
	public List<articleBean> selectUserWinningBids(int noUtilisateur, String nomUtil, String libelle) throws BusinessException;
	public List<articleBean> selectUserItemsForSale(int noUtilisateur, String nomArticle, String libelle, String etatVente) throws BusinessException;
	public beanDEncheres insertBid(beanDEncheres bid) throws BusinessException;
	public void updateArtSalePrice(int price, int noArticle) throws BusinessException;
	public int checkUserCredit(int userNb) throws BusinessException;
	public articleBean detailAuction(String nomArticle) throws BusinessException;
	public beanDEncheres selectHighestBidder(String nomArticle) throws BusinessException;
	public void insertRetrait(retraiteBean retrait, int nomArticle) throws BusinessException;
	public void updateSaleStatus() throws BusinessException;
	public List<articleBean> selectAllArticles(int noUtilisateur, String libelle, String etatVente) throws BusinessException;
	public List<articleBean> selectArticlesByNameandCategory(String nomArticle, String libelle) throws BusinessException;
	public  List<articleBean> selectCategory(String libelle) throws BusinessException;
	public void updateEtatVente() throws BusinessException;
}
