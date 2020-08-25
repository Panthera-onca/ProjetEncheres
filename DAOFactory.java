package fr.eni.encheres.dal;

public abstract class DAOFactory {
	public static UserDAO getUserDAO() {
		return new UserDAOJdbcImpl();
		
	}
	
	public static  Object etatVenteDAO(){
		return new etatVenteDAOJdbcImpl();
		
	}

	

}
