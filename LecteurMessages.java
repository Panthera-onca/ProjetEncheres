package fr.eni.encheres.messages;

import java.util.ResourceBundle;

public class LecteurMessages {
		private static ResourceBundle rb;
		
		static {
			try {
				rb = ResourceBundle.getBundle("fr.eni.list.courses.messages.messages_erreur");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		private LecteurMessages() {
			
		}
		public static String getMessageErreur(int code) {
			String message = "";
			try {
				if(rb!=null) {
					message = rb.getString(String.valueOf(code));
				}else {
					message = "Probleme à la lecture du fichier contenant les messages";
				}
			}catch(Exception e) {
				e.printStackTrace();
				message = "Une erreur inconnue est survenue";
			}
			System.out.println("message" + message);
			return message;
		}


}
