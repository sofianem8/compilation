package fr.ul.mayer.exemples;

public class Symbole {
	protected String nom;
	protected int valeur;
	protected String retour;
	protected Scope scope;
	public static enum Scope {
		Glob, Loc, Param
	}

	public Symbole(String nom, Scope scope) {
		this.nom = nom;
		this.scope = scope;
		this.valeur = 0;
	}

	public Symbole(String nom, Scope scope, int valeur) {
		this.nom = nom;
		this.scope = scope;
		this.valeur = valeur;
	}

	public Symbole(String nom, Scope scope, String retour) {
		this.nom = nom;
		this.scope = scope;
		this.retour = retour;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	
	public String toString() {
		return nom + "/" + scope + "/" + valeur + "/" + retour;
	}

}
