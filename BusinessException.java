package fr.eni.encheres.encheres;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception{
	private static final long serialVersionUID = 1L;
	private List<Integer> CodesErreur;
	
	public BusinessException() {
		super();
		this.CodesErreur = new ArrayList<>();
	}
	public void ajouterErreur(int code) {
		if(!this.CodesErreur.contains(code)) {
			this.CodesErreur.add(code);
		}
	}
	
	public boolean hasErreurs() {
		return this.CodesErreur.size()>0;
		
	}
	public List<Integer> getListeCodesErreur(){
		return this.CodesErreur;

}
}
