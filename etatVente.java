package fr.eni.encheres.bo;

public enum etatVente {
	CREE("Article est offert"),
	EN_COURS("L'article est en cours d'etre vendu"),
	TEMINE("La vente est terminée"),
	EFFECTUE("La vente est effectuée");
	
    private final String description;
    
    private etatVente(String description) {
    	this.description = description;
    }
    
    @Override
    public String toString() {
		return description;
    	
    }

}
