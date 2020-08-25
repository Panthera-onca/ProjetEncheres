package fr.eni.encheres.bo;

import java.io.Serializable;



public class userBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private boolean vendeur;
	private int noUtilisateur;
	private String pseudo;
	private String nomUtil;
	private String prenomUtil;
	private String email;
	private String telephone;
	private String address;
	private int codePostal;
	private String ville;
	private String moteDePasse;
	private int credit;
	private boolean administrateur;

	private boolean connected;
	
	
	public userBean() {
		this.pseudo = pseudo;
		this.nomUtil = nomUtil; 
		this.prenomUtil = prenomUtil; 
		this.email = email; 
		this.telephone = telephone; 
		this.address = address; 
		this.codePostal = codePostal; 
		this.ville = ville; 
		this.moteDePasse = moteDePasse;
	}

	public boolean isVendeur() {
		return vendeur;
	}

	public void setVendeur(boolean vendeur) {
		this.vendeur = vendeur;
	}

	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNomUtil() {
		return nomUtil;
	}

	public void setNomUtil(String nomUtil) {
		this.nomUtil = nomUtil;
	}

	public String getPrenomUtil() {
		return prenomUtil;
	}

	public void setPrenomUtil(String prenomUtil) {
		this.prenomUtil = prenomUtil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getMoteDePasse() {
		return moteDePasse;
	}

	public void setMoteDePasse(String moteDePasse) {
		this.moteDePasse = moteDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean isAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		if ( administrateur == false) {
			if(vendeur != false) 
				return false;
		}else if (administrateur == false || vendeur == false)
			return false;
		return true;
	}
	public boolean isConnected() {
        return connected;
    }

	public void setConnected(boolean connected) {
		this.connected = connected;
		
	}
	

}
