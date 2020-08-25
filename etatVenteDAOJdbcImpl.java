package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.articleBean;
import fr.eni.encheres.bo.beanDEncheres;
import fr.eni.encheres.bo.retraiteBean;
import fr.eni.encheres.bo.userBean;
import fr.eni.encheres.encheres.BusinessException;
import fr.eni.encheres.encheres.SqlStatements;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.encheres.SqlStatements;
public class etatVenteDAOJdbcImpl implements etatVenteDAO{
	    private final String INSERT_NEW_ARTICLE = "insert into articles_vendus (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie, etat_vente) values (?, ?, ?, ?, ?, ?, ?, ?)";
	    private final String SELECT_CAT_BY_NAME = "select no_categorie from categories where libelle = ?";
	    private final String SELECT_ALL_ARTICLES = "select a.nom_article, a.prix_initial, a.date_debut_encheres, a.date_fin_encheres, U.pseudo, U.no_utilisateur from ARTICLES_VENDUS a inner join UTILISATEURS U on a.no_utilisateur = U.no_utilisateur";
	    private final String SELECT_ARTICLE_BY_NAME = "select a.nom_article, a.prix_initial, a.date_debut_encheres, a.date_fin_encheres, U.pseudo, U.no_utilisateur from ARTICLES_VENDUS a inner join UTILISATEURS U on a.no_utilisateur = U.no_utilisateur where a.nom_article = ?";
	    private final String SELECT_ARTICLE_BY_NAME_AND_CAT = "select a.nom_article, a.prix_initial, a.date_debut_encheres, a.date_fin_encheres, U.pseudo, U.no_utilisateur from ARTICLES_VENDUS a inner join UTILISATEURS U on a.no_utilisateur = U.no_utilisateur where a.nom_article = ? and no_categorie = ?";
	

	@Override
	public articleBean insertArticle(articleBean article, userBean utilisateur) throws BusinessException {
		 if (article == null) {
	            BusinessException businessException = new BusinessException();
	            businessException.ajouterErreur(CodesErreurDAO.NULL_OBJECT_EXCEPTION);
	            throw businessException;
	        }
		 
		 try(Connection cnx = ConnectionProvider.getConnection()){
		    	try {
		    		cnx.setAutoCommit(false);
		    		PreparedStatement pstmt = cnx.prepareStatement(INSERT_NEW_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
		    		pstmt.setString(1, article.getNomArticle());
	                pstmt.setString(2, article.getDescription());
	                pstmt.setTimestamp(3, Timestamp.valueOf(article.getDateDebutEncheres()));
	                pstmt.setTimestamp(4, Timestamp.valueOf(article.getDateFinEncheres()));
	                pstmt.setInt(5, article.getMiseAPrix());
	                pstmt.setInt(6, article.getNoArticle());
	                pstmt.setString(7, article.getNoCategory().toString());
	                pstmt.setString(8, article.getEtatVente().toString());
	                
	                pstmt.executeUpdate();
	                ResultSet rs = pstmt.getGeneratedKeys();
	                
	                if (rs.next()) {
	                    article.setNoArticle(rs.getInt(1));
	                }
	                rs.close();
	                pstmt.close();
	                cnx.commit();
	            } catch (Exception e) {
	                e.printStackTrace();
	                cnx.rollback();
	                throw e;
	            } }catch (SQLException throwables) {
	                throwables.printStackTrace();
	                BusinessException businessException = new BusinessException();
	                businessException.ajouterErreur(CodesErreurDAO.ECHEC_INSERT_OBJECT);
	                throw businessException;
	            }
		return article;
		
	}

	@Override
	public  String selectCategory(String libelle) throws BusinessException {
		 if(libelle == null) {
	            BusinessException businessException = new BusinessException();
	            businessException.ajouterErreur(CodesErreurDAO.NULL_OBJECT_EXCEPTION);
	            throw businessException;
	        }
		 try(Connection cnx = ConnectionProvider.getConnection()) {

	            PreparedStatement pstmt = cnx.prepareStatement(SELECT_CAT_BY_NAME);
	            pstmt.setCategory(libelle);
	            ResultSet rs = pstmt.executeQuery();
	            if(rs.next()) {
	            	libelle = ((Object) rs).getLibelle(Integer.parseInt("no_categorie"));
	            } } catch (SQLException throwables) {
	                throwables.printStackTrace();
	                BusinessException businessException = new BusinessException();
	                businessException.ajouterErreur(CodesErreurDAO.ECHEC_LECTURE_DB);
	                throw businessException;
	            }

	            return libelle;
	}

	@Override
	public List<articleBean> selectAllArticles(int noUtilisateur, String libelle, String etatVente) throws BusinessException {
		List<articleBean> allArticles = new ArrayList<articleBean>();
		 try (Connection cnx = ConnectionProvider.getConnection()) {

	            PreparedStatement stmt = cnx.prepareStatement(SELECT_ALL_ARTICLES);

	            ResultSet rs = stmt.executeQuery();

	            while(rs.next()) {
	                allArticles.add(articleBuilder(rs));
	            }

	        } catch (SQLException throwables) {
	            throwables.printStackTrace();
	            BusinessException businessException = new BusinessException();
	            businessException.ajouterErreur(CodesErreurDAO.ECHEC_LECTURE_DB);
	            throw businessException;
	        }

	        return allArticles;
	    }
		
		
	

	private articleBean articleBuilder(ResultSet rs) throws SQLException {
		articleBean article = new articleBean();
		
		article.setNomArticle(rs.getString("nom_article"));
        article.setMiseAPrix(rs.getInt("prix_initial"));
        article.setDateDebutEncheres(rs.getTimestamp("date_debut_encheres").toLocalDateTime());
        article.setDateFinEncheres(rs.getTimestamp("date_fin_encheres").toLocalDateTime());
        article.getVendeur().setPseudo(rs.getString("pseudo"));
        article.getVendeur().setNoUtilisateur(rs.getInt("no_utilisateur"));
		
		return article;
	}

	@Override
	public List<articleBean> selectArticlesByName(String nomArticle) throws BusinessException {
		List<articleBean> articles = new ArrayList<articleBean>();

        try (Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLE_BY_NAME);

            pstmt.setString(1, nomArticle);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                articles.add(articleBuilder(rs));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_LECTURE_DB);
            throw businessException;
        }
		
		return articles;
	}

	@Override
	public List<articleBean> selectArticlesByNameandCategory(String nomArticle, String libelle) throws BusinessException {
		   if(nomArticle == null || libelle == null) {
	            BusinessException businessException = new BusinessException();
	            businessException.ajouterErreur(CodesErreurDAO.NULL_OBJECT_EXCEPTION);
	            throw businessException;
	        }

	        List<articleBean> articles = new ArrayList<articleBean>();
	        
	        try (Connection cnx = ConnectionProvider.getConnection()) {
	            int no_cat = this.selectCategory(libelle);

	            PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLE_BY_NAME_AND_CAT);

	            pstmt.setString(1, nomArticle);
	            pstmt.setInt(2, no_cat);

	            ResultSet rs =pstmt.executeQuery();

	            while(rs.next()) {
	                articles.add(articleBuilder(rs));
	            }

	        } catch (SQLException throwables) {
	            throwables.printStackTrace();
	            BusinessException businessException = new BusinessException();
	            businessException.ajouterErreur(CodesErreurDAO.ECHEC_LECTURE_DB);
	            throw businessException;
	        }

		return articles;
	}

	public List<articleBean>selectUserWinningBids(int noUtilisateur, String nomUtil, String libelle) throws  BusinessException{
		StringBuilder sqlstmt = new StringBuilder();
        sqlstmt.append(SqlStatements.SELECT_WINNING_BIDS);
        boolean skip = false; 
        
        
        try (Connection cnx = ConnectionProvider.getConnection()) {
        if (!nomUtil.isEmpty()) {
            if (!libelle.isEmpty()) {
                sqlstmt.append(SqlStatements.byNameAndCat);
            } else {
                sqlstmt.append(SqlStatements.byName);
            }
        } else if (!libelle.isEmpty()) {
            sqlstmt.append(SqlStatements.byCat);
        } 
        
        PreparedStatement pstmt = cnx.prepareStatement(String.valueOf(sqlstmt));
        pstmt.setInt(1, noUtilisateur);

        if (!nomUtil.isEmpty()) {
            if (!nomUtil.isEmpty()) {
                int no_cat = this.selectCatByName(libelle);
                pstmt.setString(2, nomUtil);
                pstmt.setInt(3, no_cat);
            } else {
                pstmt.setString(2, nomUtil);
            }
        } else if (!libelle.isEmpty()) {
            int no_cat = this.selectCatByName(libelle);
            pstmt.setInt(2, no_cat);
        }

        ResultSet rs = pstmt.executeQuery();
        
        while(rs.next()) {
        	articleBean.add(articleBuilder(rs));
        }

    } catch (SQLException throwables) {
        throwables.printStackTrace();
        BusinessException businessException = new BusinessException();
        businessException.ajouterErreur(CodesErreurDAO.ECHEC_LECTURE_DB);
        throw businessException;
    }

    return selectAllArticles( noUtilisateur, nomUtil, libelle);
		
		
	}


	public articleBean detailAuction(String nomArticle) {
		 articleBean article = new articleBean();
	        try (Connection cnx = ConnectionProvider.getConnection()) {
	            PreparedStatement pstmt = cnx.prepareStatement(SqlStatements.SELECT_AUCTION_DETAIL);
	            System.out.println(SqlStatements.SELECT_AUCTION_DETAIL);
	            pstmt.setObject(1, noArticle);
	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                article = articleBuilder(rs);
	            }

	        } catch (SQLException throwables) {
	            // Si erreur de lecture, on lève une erreur
	            throwables.printStackTrace();
	            BusinessException businessException = new BusinessException();
	            businessException.ajouterErreur(CodesErreurDAO.ECHEC_LECTURE_DB);
	            throw businessException;
	        }
	        System.out.println(article.toString());
	        return article;
	}

	public int checkUserCredit(int noUtilisateur) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<articleBean> mixAjourPrixVente(int prixVente, int noArticle) {
		try (Connection cnx = ConnectionProvider.getConnection()) {

            try {
                cnx.setAutoCommit(false);

                // assigne la requête sql au preparedstatement
                PreparedStatement stmt = cnx.prepareStatement(SqlStatements.UPDATE_ARTICLE_SALE_PRICE);

                // Remplit les placeholders avec les infos passées param dans le formulaire de signup
                stmt.setInt(1, prixVente);
                stmt.setInt(2, noArticle);

                // Envoie la requête
                stmt.executeUpdate();

                stmt.close();
                cnx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                cnx.rollback();
                throw e;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_UPDATE_DB);
            throw businessException;
		
	}

	public List<articleBean> selectUserItemsForSale(int noUtilisateur, String nomUtil, String libelle, String etatVente){
		StringBuilder sqlstmt = new StringBuilder();
        sqlstmt.append(SqlStatements.SELECT_ALL_ARTICLES);
        boolean skip = false;

        // Vérifie les paramètres pour ajouter des conditions à la requête

        if (!nomUtil.isEmpty()) {
            if (!libelle.isEmpty()) {
                sqlstmt.append(SqlStatements.byNameAndCat);
            } else {
                sqlstmt.append(SqlStatements.byName);
            }
        } else if (!libelle.isEmpty()) {
            sqlstmt.append(SqlStatements.byCat);
        } else {
            sqlstmt.append(" where etat_vente = ").append("'").append(etatVente).append("'");
            skip = true;
        }

        if (!etatVente.isEmpty() && !skip) {
            if (etatVente.equals("EC")) {
                sqlstmt.append(SqlStatements.EC);
            } else if (etatVente.equals("CR")) {
                sqlstmt.append(SqlStatements.CR);
            } else {
                sqlstmt.append(SqlStatements.ET);
            }
        }

        sqlstmt.append(" and a.no_utilisateur = ?");
        System.out.println(sqlstmt);
        List<articleBean> allArticles = new ArrayList<articleBean>();


        try (Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement stmt = cnx.prepareStatement(String.valueOf(sqlstmt));

            if (!nomUtil.isEmpty()) {
                if (!libelle.isEmpty()) {
                    int no_cat = this.selectCatByName(libelle);
                    stmt.setString(1, nomUtil);
                    stmt.setInt(2, no_cat);
                    stmt.setInt(3, noUtilisateur);
                } else {
                    stmt.setString(1, nomUtil);
                    stmt.setInt(2, noUtilisateur);
                }
            } else if (!libelle.isEmpty()) {
                int no_cat = this.selectCatByName(libelle);
                stmt.setInt(1, no_cat);
                stmt.setInt(2, noUtilisateur);
            } else
            {
                stmt.setInt(1, noUtilisateur);
            }

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                allArticles.add(articleBuilder(rs));
            }

        } catch (SQLException throwables) {

            // Si erreur de lecture, on lève une erreur
            throwables.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_LECTURE_DB);
            throw businessException;
        }

        return allArticles;
	}

	public List<articleBean> selectOngoingAuctions(int noUtilisateur, String nomUtil, String libelle, enum etatVente) throws BusinessException {
		StringBuilder sqlstmt = new StringBuilder();
		sqlstmt.append(SqlStatements.SELECT_ONGOING_AUCS);
		boolean skip = false;


   
		if (!nomUtil.isEmpty()) {
			if (!libelle.isEmpty()) {
				sqlstmt.append(SqlStatements.byNameAndCat);
			} else {
				sqlstmt.append(SqlStatements.byName);
        }
		} else if (!libelle.isEmpty()) {
			sqlstmt.append(SqlStatements.byCat);
		} else {
			sqlstmt.append(" where AV.etat_vente in ('EC', 'ET')");
			skip = true;
    }

    
		if (!skip) {
			sqlstmt.append(" where AV.etat_vente in ('EC', 'ET')");
    }

		sqlstmt.append(" and A.no_utilisateur = ?");
		System.out.println("Le no utilisateur = " + noUtilisateur);


    

		sqlstmt.append("\ngroup by A.no_article, A.no_utilisateur, AV.nom_article, AV.description, A.max, U.pseudo, AV.date_fin_encheres, AV.prix_initial");

		List<articleBean> allArticles = new ArrayList<articleBean>();
		System.out.println(sqlstmt);


		try (Connection cnx = ConnectionProvider.getConnection()) {

			PreparedStatement pstmt = cnx.prepareStatement(String.valueOf(sqlstmt));

        
			if (!nomUtil.isEmpty()){
           
				if (!libelle.isEmpty()){
					int no_cat = this.selectCatByName(libelle);
					pstmt.setString(1, nomUtil);
					pstmt.setInt(2, no_cat);
					pstmt.setInt(3, noUtilisateur);
               
				} else {
					pstmt.setString(1, nomUtil);
					pstmt.setInt(2, noUtilisateur);
				}
            
			}
			else if (!libelle.isEmpty()){
				int no_cat = this.selectCatByName(libelle);
				pstmt.setInt(1, no_cat);
				pstmt.setInt(2, noUtilisateur);
			}
			else{
				pstmt.setInt(1, noUtilisateur);}


			ResultSet rs = pstmt.executeQuery();


			while(rs.next()) {
				allArticles.add(articleBuilder(rs));
        }

		} catch (SQLException throwables) {
			throwables.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesErreurDAO.ECHEC_LECTURE_DB);
			throw businessException;
		}

		return allArticles;
	}

	public List<articleBean> selectUserWinningBids(int noUtilisateur, String nomUtil, String libelle) throws BusinessException {
		StringBuilder sqlstmt = new StringBuilder();
        sqlstmt.append(SqlStatements.SELECT_WINNING_BIDS);
        boolean skip = false;

        // Vérifie les paramètres pour ajouter des conditions à la requête

        if (!nomUtil.isEmpty()) {
            if (!cat.isEmpty()) {
                sqlstmt.append(SqlStatements.byNameAndCat);
            } else {
                sqlstmt.append(SqlStatements.byName);
            }
        } else if (!cat.isEmpty()) {
            sqlstmt.append(SqlStatements.byCat);
        }


        System.out.println(sqlstmt);
        List<articleBean> allArticles = new ArrayList<articleBean>();


        try (Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement stmt = cnx.prepareStatement(String.valueOf(sqlstmt));
            stmt.setInt(1, noUtilisateur);

            if (!nomUtil.isEmpty()) {
                if (!libelle.isEmpty()) {
                    int no_cat = this.selectCatByName(libelle);
                    stmt.setString(2, nomUtil);
                    stmt.setInt(3, no_cat);
                } else {
                    stmt.setString(2, nomUtil);
                }
            } else if (!cat.isEmpty()) {
                int no_cat = this.selectCatByName(libelle);
                stmt.setInt(2, no_cat);
            }

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                allArticles.add(articleBuilder(rs));
            }

        } catch (SQLException throwables) {

            // Si erreur de lecture, on lève une erreur
            throwables.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_LECTURE_DB);
            throw businessException;
        }

        return allArticles;
	}
	public int getCatId(String libelle) throws BusinessException {
        return articleBean.selectCatByName(libelle);
    }

	public int selectCatByName(String libelle) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public beanDEncheres insertBid(beanDEncheres bid) throws BusinessException {
		if (bid == null) {
			BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.NULL_OBJECT_EXCEPTION);
            throw businessException;
        }

        // tente d'ouvrir une connection à la BDD
        try (Connection cnx = ConnectionProvider.getConnection()) {

            try {
                cnx.setAutoCommit(false);

                // assigne la requête sql au preparedstatement
                PreparedStatement pstmt = cnx.prepareStatement(SqlStatements.INSERT_NEW_BID);

                // Remplit les placeholders avec les infos passées param dans le formulaire de signup
                pstmt.setInt(1, bid.getVendeur());
                pstmt.setInt(2, bid.getNoArticle());
                pstmt.setTimestamp(3, Timestamp.valueOf(bid.getDateDEncheres()));
                pstmt.setInt(4, bid.getMontantDEncheres());

                // Envoie la requête
                pstmt.executeUpdate();

                pstmt.close();
                cnx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                cnx.rollback();
                throw e;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_INSERT_OBJECT);
            throw businessException;
        }


        return bid;
	}

	@Override
	public beanDEncheres selectHighestBidder(String nomArticle) throws BusinessException {
		beanDEncheres bid = new beanDEncheres();

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SqlStatements.SELECT_HIGHEST_BIDDER);
            System.out.println(SqlStatements.SELECT_HIGHEST_BIDDER);
            pstmt.setInt((Integer.parseInt(nomArticle), 0);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                bid.setMontantDEncheres(rs.getInt("highest bid"));
                bid.setIdAcheteur(rs.getInt("no_utilisateur"));
                bid.setNomAcheteur(rs.getString("buyer"));
                bid.setDateDEncheres(rs.getTimestamp("date_enchere").toLocalDateTime());
            }

        } catch (SQLException throwables) {
            // Si erreur de lecture, on lève une erreur
            throwables.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_LECTURE_DB);
            throw businessException;
        }
        return bid;
    }
	}

	@Override
	public void insertRetrait(retraiteBean retrait, int nomArticle) throws BusinessException {
		if (retrait == null) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.NULL_OBJECT_EXCEPTION);
            throw businessException;
        }


        // tente d'ouvrir une connection à la BDD
        try (Connection cnx = ConnectionProvider.getConnection()) {

            try {
                cnx.setAutoCommit(false);

                // assigne la requête sql au preparedstatement
                PreparedStatement pstmt = cnx.prepareStatement(SqlStatements.INSERT_PICKUP_DETAIL);

                // Remplit les placeholders avec les infos passées param dans le formulaire de signup
                pstmt.setInt(1, nomArticle);
                pstmt.setString(2, retrait.getAddress());
                pstmt.setInt(3, retrait.getCodePostal());
                pstmt.setString(4, retrait.getVille());

                // Envoie la requête
                pstmt.executeUpdate();

                pstmt.close();
                cnx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                cnx.rollback();
                throw e;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_INSERT_OBJECT);
            throw businessException;
        }
		
	}

	@Override
	public void updateEtatVente() throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {

            try {
                cnx.setAutoCommit(false);

                // assigne la requête sql au preparedstatement
                PreparedStatement pstmt = cnx.prepareStatement(SqlStatements.SET_STATUS_TO_EC);
                pstmt.executeUpdate();

                pstmt = cnx.prepareStatement(SqlStatements.SET_STATUS_TO_ET);
                pstmt.executeUpdate();

                pstmt.close();
                cnx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                cnx.rollback();
                throw e;
            }

            System.out.println("Status is being updated");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_UPDATE_DB);
            throw businessException;
        }
		
	}

	@Override
	public List<articleBean> selectAllArticles(String nomArticle, String libelle, String etatVente) throws BusinessException {
		StringBuilder sqlstmt = new StringBuilder();
        sqlstmt.append(SqlStatements.SELECT_ALL_ARTICLES);
        boolean skip = false;

        if (!nomArticle.isEmpty()) {
            if (!libelle.isEmpty()) {
                sqlstmt.append(SqlStatements.byNameAndCat);
            } else {
                sqlstmt.append(SqlStatements.byName);
            }
        } else if (!libelle.isEmpty()) {
            sqlstmt.append(SqlStatements.byCat);
        } else {
            sqlstmt.append(" where etat_vente = ").append("'").append(etatVente).append("'");
            skip = true;
        }

        if (!etatVente.isEmpty() && !skip) {
            if (etatVente.equals("EC")) {
                sqlstmt.append(SqlStatements.EC);
            } else if (etatVente.equals("CR")) {
                sqlstmt.append(SqlStatements.CR);
            } else {
                sqlstmt.append(SqlStatements.ET);
            }
        }
        System.out.println(sqlstmt);
        List<articleBean> allArticles = new ArrayList<articleBean>();


        try (Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement stmt = cnx.prepareStatement(String.valueOf(sqlstmt));

            if (!nomArticle.isEmpty()) {
                if (!libelle.isEmpty()) {
                    int no_cat = this.selectCatByName(libelle);
                    stmt.setString(1, nomArticle);
                    stmt.setInt(2, no_cat);
                } else {
                    stmt.setString(1, nomArticle);
                }
            } else if (!libelle.isEmpty()) {
                int no_cat = this.selectCatByName(libelle);
                stmt.setInt(1, no_cat);
            }

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                allArticles.add(articleBuilder(rs));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_LECTURE_DB);
            throw businessException;
        }

        return allArticles;
	}

	

	

	

	

	

	

	


	

}
