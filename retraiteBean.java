package fr.eni.encheres.bo;

public class retraiteBean {
	private static String address;
	private int codePostal;
	private String ville;
	
	public retraiteBean() {}
	
	public retraiteBean(String address, int codePostal, String ville) {
		this.setAddress(address);
		this.setCodePostal(codePostal);
		this.setVille(ville);
	}

	public static String getAddress() {
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

}
