package fr.eni.encheres.bo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class beanDEncheres implements Serializable{
	private static final long serialVersionUID = 1L;
	private LocalDateTime dateDEncheres;
    private int montantDEncheres;
    private int idAcheteur;
    private String nomAcheteur;
    private int noArticle;
	
    
    public beanDEncheres() {}
    
    public beanDEncheres(int montantDEncheres,LocalDateTime dateDEncheres, int noArticle, int idAcheteur) {
    	this.setMontantDEncheres(montantDEncheres);
    	this.setDateDEncheres(dateDEncheres);
    	this.setNoArticle(noArticle);
    	this.setIdAcheteur(idAcheteur);
    	
    }

	public LocalDateTime getDateDEncheres() {
		return dateDEncheres;
	}

	public void setDateDEncheres(LocalDateTime dateDEncheres) {
		this.dateDEncheres = dateDEncheres;
	}

	public int getMontantDEncheres() {
		return montantDEncheres;
	}

	public void setMontantDEncheres(int montantDEncheres) {
		this.montantDEncheres = montantDEncheres;
	}

	public int getIdAcheteur() {
		return idAcheteur;
	}

	public void setIdAcheteur(int idAcheteur) {
		this.idAcheteur = idAcheteur;
	}

	public String getNomAcheteur() {
		return nomAcheteur;
	}

	public void setNomAcheteur(String nomAcheteur) {
		this.nomAcheteur = nomAcheteur;
	}

	public int getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

}
