package fr.eni.encheres.bo;

import java.io.Serializable;
import java.time.LocalDateTime;
import fr.eni.encheres.bo.userBean;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.bo.etatVente;
import fr.eni.encheres.bo.retraiteBean;

public class articleBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private int noArticle;
	private String nomArticle;
	private String description; 
	private LocalDateTime dateDebutEncheres;
	private LocalDateTime dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private etatVente etatVente;
	private userBean vendeur;
	private Category noCategory;
	private Category libelle;
	private beanDEncheres bid;
	
	public articleBean() {
		vendeur = new userBean();
		noCategory = new Category();
		libelle = new Category();
		etatVente = getEtatVente();
	}
	
	
	
	
	
	public int getNoArticle() {
		return noArticle;
	}
	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}
	public String getNomArticle() {
		return nomArticle;
	}
	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getDateDebutEncheres() {
		return dateDebutEncheres;
	}
	public void setDateDebutEncheres(LocalDateTime dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}
	public LocalDateTime getDateFinEncheres() {
		return dateFinEncheres;
	}
	public void setDateFinEncheres(LocalDateTime dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}
	public int getMiseAPrix() {
		return miseAPrix;
	}
	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}
	public int getPrixVente() {
		return prixVente;
	}
	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}
	public fr.eni.encheres.bo.etatVente getEtatVente() {
		return etatVente;
	}
	public void setEtatVente(fr.eni.encheres.bo.etatVente etatVente) {
		this.etatVente = etatVente;
	}
	public userBean getVendeur() {
		return vendeur;
	}
	public void setVendeur(userBean vendeur) {
		this.vendeur = vendeur;
	}
	
	public String getRetraite() {
		return retraiteBean.getAddress();
	}





	public Category getNoCategory() {
		return noCategory;
	}





	public void setNoCategory(Category noCategory) {
		this.noCategory = noCategory;
	}





	public Category getLibelle() {
		return libelle;
	}





	public void setLibelle(Category libelle) {
		this.libelle = libelle;
	}
	
	public beanDEncheres getBid() {
		return bid;
	}





	public void setBid(beanDEncheres bid) {
		this.bid = bid;
		
	}





	public void setRetrait(retraiteBean retraiteBean) {
		// TODO Auto-generated method stub
		
	}





	
	
	
	




		
	}


