package fr.eni.encheres.dal;

import fr.eni.encheres.bo.articleBean;
import fr.eni.encheres.bo.userBean;
import fr.eni.encheres.encheres.BusinessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class UserDAOJdbcImpl implements UserDAO {
	    private static final String INSERT_NEW_USER = "insert into utilisateurs (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    private static final String SELECT_USER_BY_PWD = "select mot_de_passe from utilisateurs where no_utilisateur = ?";
	    private static final String SELECT_USER_BY_PSEUDO = "select pseudo from utilisateurs where pseudo = ?";
	    private static final String SELECT_USER_BY_EMAIL = "select email from utilisateurs where email = ?";
	    private static final String SELECT_USER_BY_ID = "select pseudo, mot_de_passe from utilisateurs where pseudo = ? and mot_de_passe = ?";
	    private static final String SELECT_USER_PUBLIC_INFO = "select pseudo, nom, prenom, email, telephone, rue, code_postal, ville from utilisateurs where pseudo = ?";
	    private static final String SELECT_USER_PRIVATE_INFO = "select no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit from utilisateurs where pseudo = ?";
	    private static final String UPDATE_USER_INFO = "update utilisateurs set pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? where no_utilisateur = ?";
	    private static final String DELETE_USER = "delete from utilisateurs where no_utilisateur = ?";
	    
	    


	@Override
	public void insert(userBean user) throws BusinessException {
		if(user == null) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.NULL_OBJECT_EXCEPTION);
            throw businessException;
        }
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			try {
                cnx.setAutoCommit(false);
                PreparedStatement pstmt = cnx.prepareStatement(INSERT_NEW_USER, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, user.getPseudo());
                pstmt.setString(2, user.getNomUtil());
                pstmt.setString(3, user.getPrenomUtil());
                pstmt.setString(4, user.getEmail());
                pstmt.setString(5, user.getTelephone());
                pstmt.setString(6, user.getAddress());
                pstmt.setInt(7, user.getCodePostal());
                pstmt.setString(8, user.getVille());
                pstmt.setString(9, user.getMoteDePasse());
                pstmt.setInt(10, user.getCredit());
                
                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                
                if (rs.next()) {
                    user.setNoUtilisateur(rs.getInt(1));
                }
                rs.close();
                pstmt.close();
                cnx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                cnx.rollback();
                throw e;
            }
                
			
		}catch (SQLException throwables) {
            throwables.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_INSERT_OBJECT);
            throw businessException;
        }

	}

	@Override
	public userBean checkID(userBean login) throws BusinessException {
		 boolean ok = false;
		 try(Connection cnx = ConnectionProvider.getConnection()){
			 PreparedStatement pstmt = cnx.prepareStatement(SELECT_USER_BY_ID);
			 pstmt.setString(1, login.getPseudo());
	         pstmt.setString(2, login.getMoteDePasse());
	         ResultSet rs = pstmt.executeQuery();
	         ok = rs.next();
		 }catch (SQLException throwables) {
	            throwables.printStackTrace();
	            BusinessException businessException = new BusinessException();
	            businessException.ajouterErreur(CodesErreurDAO.ECHEC_VALIDATION_LOGIN);
	            throw businessException;
	        }
	        if (!ok) {
	            BusinessException businessException = new BusinessException();
	            businessException.ajouterErreur(CodesErreurDAO.ECHEC_VALIDATION_LOGIN);
	            throw businessException;
	        }
	        return login;

	}

	@Override
	public void checkPseudo(String pseudo) throws BusinessException {
		boolean pseudoNotAvailable = false;
		if(pseudo == null) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.NULL_OBJECT_EXCEPTION);
            throw businessException;
        }
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_USER_BY_PSEUDO);
			pstmt.setString(1, pseudo);
            ResultSet rs = pstmt.executeQuery();
            pseudoNotAvailable = rs.next();
			
		}catch (SQLException throwables) {
            throwables.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_LECTURE_DB);
            throw businessException;
        }

        if (pseudoNotAvailable) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_SIGNUP_PSEUDO_INUSE);
            throw businessException;
        }

	}

	@Override
	public void checkEmail(String email) throws BusinessException {
		boolean emailNotAvailable = false;
		
		if(email == null) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.NULL_OBJECT_EXCEPTION);
            throw businessException;
        }
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_USER_BY_EMAIL);
			pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            emailNotAvailable = rs.next();
			
		}catch (SQLException throwables) {
            throwables.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_LECTURE_DB);
            throw businessException;
        }

        if (emailNotAvailable) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_SIGNUP_EMAIL_INUSE);
            throw businessException;
        }

	}

	@Override
	public userBean selectUserPublicInfo(String pseudo) throws BusinessException {
		 userBean public_info = new userBean();
		 List<String> profileInfo = new ArrayList<String>();
		 if(pseudo == null) {
	            BusinessException businessException = new BusinessException();
	            businessException.ajouterErreur(CodesErreurDAO.NULL_OBJECT_EXCEPTION);
	            throw businessException;
	        }
		 try (Connection cnx = ConnectionProvider.getConnection()){
			 PreparedStatement pstmt = cnx.prepareStatement(SELECT_USER_PUBLIC_INFO);
		 }catch (SQLException throwables) {
	            throwables.printStackTrace();
	            BusinessException businessException = new BusinessException();
	            businessException.ajouterErreur(CodesErreurDAO.ECHEC_LECTURE_DB);
	            throw businessException;
	        }

	        return public_info;
	}

	
	public userBean misAJourDUtilisateur(userBean user) throws BusinessException {
		if(user == null) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.NULL_OBJECT_EXCEPTION);
            throw businessException;
        }
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			try {
                cnx.setAutoCommit(false);
                PreparedStatement pstmt = cnx.prepareStatement(UPDATE_USER_INFO);
                pstmt.setString(1, user.getPseudo());
                pstmt.setString(2, user.getNomUtil());
                pstmt.setString(3, user.getPrenomUtil());
                pstmt.setString(4, user.getEmail());
                pstmt.setString(5, user.getTelephone());
                pstmt.setString(6, user.getAddress());
                pstmt.setInt(7, user.getCodePostal());
                pstmt.setString(8, user.getVille());
                pstmt.setString(9, user.getMoteDePasse());
                pstmt.setInt(10, user.getCredit());
                pstmt.executeUpdate();
                pstmt.close();
                cnx.commit();
			} catch (Exception e) {
                e.printStackTrace();
                cnx.rollback();
                throw e;
            }
			
		}catch (SQLException throwables) {
            throwables.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_INSERT_OBJECT);
            throw businessException;
        }
		return user;
	}

	@Override
	public boolean moteDePasseisValid(userBean login) throws BusinessException {
		boolean ok = false;
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_USER_BY_PWD);
			pstmt.setInt(1, login.getNoUtilisateur());
            ResultSet rs = pstmt.executeQuery();
            ok = rs.next();
			
		}catch (SQLException throwables) {
            throwables.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_VALIDATION_MOTDEPASSE);
            throw businessException;
        }
        if (!ok) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_VALIDATION_MOTDEPASSE);
            throw businessException;
        }
        return ok;
	}

	@Override
	public void deleteUtilisateur(int noUtilisateur) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()){
			
		}catch (SQLException throwables) {
            throwables.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDAO.ECHEC_DELETE_OBJECT);
            throw businessException;
        }

	}
	
	private userBean userBuilder(ResultSet rs) throws SQLException {
		userBean user = new userBean();
		
		user.setNomUtil(rs.getString("nomUtilisateur"));
        user.setCredit(rs.getInt("credit"));
        user.setPseudo(rs.getString("pseudo"));
        user.setNoUtilisateur(rs.getInt("no_utilisateur"));
        user.setPrenomUtil(rs.getString("prenomUtil"));
        user.setCodePostal(rs.getInt("code_postal"));
        user.setEmail(rs.getString("email"));
        user.setMoteDePasse(rs.getString("mot_de_passe"));
        user.setAddress(rs.getString("rue"));
        user.setVille(rs.getString("ville"));
        
		
		return user;
	}
     
	
	public userBean selectUserPrivateInfo(String pseudo) throws BusinessException {
		userBean private_info = new userBean();
	 List<String> profileInfo = new ArrayList<String>();
	 if(pseudo == null) {
           BusinessException businessException = new BusinessException();
           businessException.ajouterErreur(CodesErreurDAO.NULL_OBJECT_EXCEPTION);
           throw businessException;
       }
	 try (Connection cnx = ConnectionProvider.getConnection()){
		 PreparedStatement pstmt = cnx.prepareStatement(SELECT_USER_PUBLIC_INFO);
	 }catch (SQLException throwables) {
           throwables.printStackTrace();
           BusinessException businessException = new BusinessException();
           businessException.ajouterErreur(CodesErreurDAO.ECHEC_LECTURE_DB);
           throw businessException;
       }

       return private_info;
	}


}
