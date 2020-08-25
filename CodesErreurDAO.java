package fr.eni.encheres.dal;

public abstract class CodesErreurDAO {
	
	public static final int NULL_OBJECT_EXCEPTION = 10001;
	public static final int ECHEC_VALIDATION_LOGIN = 10002;
	public static final int ECHEC_SIGNUP_PSEUDO_INUSE = 10003;
	public static final int ECHEC_SIGNUP_EMAIL_INUSE = 10004;
	public static final int ECHEC_VALIDATION_MOTDEPASSE = 10005;
	public static final int ECHEC_INSERT_OBJECT = 10006;
	public static final int ECHEC_DELETE_OBJECT = 10007;
	public static final int ECHEC_UPDATE_DB = 10008;
	public static final int ECHEC_READ_DB = 10009;
	public static final int ECHEC_LECTURE_DB = 10010;

}
