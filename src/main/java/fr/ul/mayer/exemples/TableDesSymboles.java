package fr.ul.mayer.exemples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import fr.ul.mayer.exemples.Symbole.Scope;

public class TableDesSymboles {

	protected HashMap<String, Symbole> tds;

	public TableDesSymboles() {
		this.tds = new HashMap<String, Symbole>();
	}

	public void ajouterSymbole(Symbole sym) {
		this.tds.put(sym.nom, sym);
	}

	public void supprimerSymbole(String clef) {
		this.tds.remove(clef);
	}

	public void supprimerSymbole(Symbole sym) {

		for (Entry<String, Symbole> entry : this.tds.entrySet()) {
			String clef = entry.getKey();
			Symbole symbole = entry.getValue();
			if (symbole.getNom() == sym.getNom() && symbole.getScope() == sym.getScope()) {
				this.supprimerSymbole(clef);
				break;
			}
		}
	}

	public Symbole getSymbole(String clef) {
		return this.tds.get(clef);
	}

	public Symbole findSymbole(String sym) {
		for (Entry<String, Symbole> entry : this.tds.entrySet()) {
			String clef = entry.getKey();
			Symbole symbole = entry.getValue();
			if (symbole.getNom().equals(sym)) {
				return this.getSymbole(clef);
			}
		}
		return null;
	}

	public HashMap<String, Symbole> getTds() {
		return tds;
	}

	public void setTds(HashMap<String, Symbole> tds) {
		this.tds = tds;
	}
	
	@Override
	public String toString() {
		String res = "";
		for (Entry<String, Symbole> entry : this.tds.entrySet()) {
			res += entry.getKey() + " " + entry.getValue() + "\n";
		}
		return res;
	}

	public List<Symbole> getAllSymboles() {
		List<Symbole> toReturn = new ArrayList<Symbole>();
		
		for (Entry<String, Symbole> entree : this.tds.entrySet()) {
			toReturn.add(entree.getValue());
		}
		
		return toReturn;
	}

	
	public int getNbLoc() {
		int toReturn = 0;
		
		for (Entry<String, Symbole> entree : this.tds.entrySet()) {
			if (entree.getValue().scope == Scope.Loc) {
				toReturn++;
			}
		}
		
		return toReturn;
	}
}
