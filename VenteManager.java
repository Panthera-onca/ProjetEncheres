package fr.eni.encheres.bll;

import fr.eni.encheres.encheres.BusinessException;
import fr.eni.encheres.bo.userBean;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.etatVenteDAO;
import fr.eni.encheres.dal.etatVenteDAOJdbcImpl;
import fr.eni.encheres.dal.UserDAO;
import fr.eni.encheres.bo.articleBean;
import fr.eni.encheres.bo.beanDEncheres;

import java.awt.geom.PathIterator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class VenteManager {
	private etatVenteDAOJdbcImpl articleaVendre; 
	
	public VenteManager() { this.articleaVendre = (etatVenteDAOJdbcImpl) DAOFactory.etatVenteDAO(); }
	
	public void updateSaleStatus() throws BusinessException {
		articleaVendre.updateSaleStatus();
    }
	
	public articleBean auctionDetail(String nomArticle) throws BusinessException {
        articleBean article = new articleBean();
        article = articleaVendre.detailAuction(nomArticle);

        if (articleaVendre.selectHighestBidder(nomArticle) != null) {
            article.setBid(articleaVendre.selectHighestBidder(nomArticle));
        }
        return article;
    }
	
	public int getAccountBalance(int noUtilisateur) throws BusinessException {
        return articleaVendre.checkUserCredit(noUtilisateur);
    }
	
	 public void updateSalePrice(int prixVente, int noArticle) throws BusinessException {
		 articleaVendre.updateArtSalePrice(prixVente, noArticle);
	    }
	 
	 public beanDEncheres placeBid(beanDEncheres bid) throws BusinessException {
	        return articleaVendre.insertBid(bid);
	    }
	 
	 public List<articleBean> displayAllArticles(String name, String cat, String status) throws BusinessException {
	        return articleaVendre.selectAllArticles(name, cat, status);
	    }
	 
	 public List<articleBean> displayUserArticlesForSale(int noUtilisateur, String nomUtil, String Category, String etatVente) throws BusinessException {
	        return articleaVendre.selectUserItemsForSale(noUtilisateur, nomUtil, Category, etatVente);
	    }
	 
	 public List<articleBean> displayAuctions(int noUtilisateur, String nomUtil, String Category, String etatVente) throws BusinessException {
	        return articleaVendre.selectOngoingAuctions(noUtilisateur, nomUtil, Category, etatVente);
	    }
	 
	 public List<articleBean> displayUserWiningBids(int noUtilisateur, String nomUtil, String Category) throws BusinessException {
	        return articleaVendre.selectUserWinningBids(noUtilisateur, nomUtil, Category);
	    }
	 
	 public articleBean addArticleForSale(Map parametres, articleBean article, userBean user) throws BusinessException {
	        BusinessException businessException = new BusinessException();
	        this.allFieldsAreFilled(parametres, businessException);
	        this.dateIsValid(article.getDateDebutEncheres(), article.getDateFinEncheres(), businessException);
	        article.getNoCategory().setNoCategorie(getCatId(article.getNoCategory().getLibelle()));
	        System.out.println("THIS is the cat nb" + article.getNoCategory());
	        
	        if (!businessException.hasErreurs()) {
	        	articleaVendre.insertArticle(article, user);
	        } else {
	            throw businessException;
	        }

	        return article;
	    }
	 
	 private void dateIsValid(LocalDateTime dateDebutEncheres, LocalDateTime dateFinEncheres,
			BusinessException businessException) {
		 if (dateDebutEncheres == null || dateFinEncheres == null || dateDebutEncheres.isBefore(LocalDateTime.now().minusMinutes(10)) || dateFinEncheres.isBefore(dateDebutEncheres)) {
			 businessException.ajouterErreur(CodesErreur.ERREUR_DATE);}
		
	}

	private void allFieldsAreFilled(Map parametres, BusinessException businessException) {
		for (Object o : parametres.keySet()) {
            String key = (String) o;
            String value = ((String[]) parametres.get(key))[0];
            if (value.trim().isEmpty() && !key.equals("photo")) {
            	businessException.ajouterErreur(CodesErreur.CHAMPS_VIDE_ERREUR);
                break;
            }
        }
		
	}

	public int getCatId(String libelle) throws BusinessException {
	        return articleaVendre.selectCatByName(libelle);
	    }



}
