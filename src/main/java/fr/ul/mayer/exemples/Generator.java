package fr.ul.mayer.exemples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import fr.ul.mayer.exemples.Symbole.Scope;
import fr.ul.miage.arbre.*;
import fr.ul.miage.arbre.Noeud.Categories;

public class Generator {

	private String res = "---- Programme généré par le compilateur du projet (MAYER) ----\n";
	private TableDesSymboles tds;
	private Noeud progGeneral;

	public Generator(TableDesSymboles tds) {
		this.tds = tds;
	}

	public static List<Noeud> getEveryFilsParam(Noeud noeud) {
		List<Noeud> listeFinale = new ArrayList<Noeud>();
		return getEverySons(noeud, listeFinale);
	}

	public static List<Noeud> getEverySons(Noeud noeud, List<Noeud> list) {

		boolean in = false;

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getLabel().equals(noeud.getLabel())) {
				in = true;
			}
		}

		if (!in) {
			list.add(noeud);
		}
		if (noeud.getFils() != null) {
			noeud.getFils().forEach(fils -> getEverySons(fils, list));
		}
		return list;
	}

	public String genererCode(Prog prog) {
		this.progGeneral = prog;
		genererProg(prog);
		res += "\nPILE:";
		return this.res;
	}

	public void genererProg(Noeud ast) {

		this.res += "\n.include beta.uasm" + "\n.include intio.uasm" + "\n.options tty" + "\nCMOVE(PILE,SP)"
				+ "\nBR(code)";

		generer_variables(ast);

		List<Noeud> listFonctions = new ArrayList<Noeud>();

		ast.getFils().forEach(n -> {

			if (n.getCat() == Categories.FONCTION && !n.getLabel().contains("main")) {
				listFonctions.add(n);
			}
		});

		listFonctions.forEach(n -> generer_fonction((Fonction) n));

		this.res += "\ncode:";
		
		for (Noeud noeud : ast.getFils()) {
			if (noeud.getCat() == Categories.FONCTION && noeud.getLabel().contains("main")) {
				generer_fonction((Fonction) noeud);
			}
		}
	}

	public void generer_variables(Noeud ast) {
		for (Entry<String, Symbole> entry : this.tds.getTds().entrySet()) {
			Symbole symbole = entry.getValue();
			if (symbole.getScope() == Scope.Glob || symbole.getScope() == Scope.Param) {
				res += "\n" + symbole.getNom() + ": LONG(" + symbole.getValeur() + ")";
			}
		}

	}

	public void generer_fonction(Fonction ast) {
		this.res += "\n" + ast.getValeur() + ":";

		if (ast.getLabel().contains("main")) {
			this.res += "\nPUSH(LP)";
			this.res += "\nPUSH(BP)";
			this.res += "\nMOVE(SP,BP)";
			this.res += "\nALLOCATE(" + this.tds.getNbLoc() + ")";
			
			for (Symbole s : this.tds.getAllSymboles()) {
				if (s.scope == Scope.Param) {
					setParams();
					break;
				}
			}
		}
		
		boolean containsRet = false;
		Noeud ret = null;

		for (Noeud n : ast.getFils()) {
			if (n.getCat() == Categories.RET) {
				containsRet = true;
				ret = n;
			} else {
				generer_instruction(n);
			}
		}
		
		if (containsRet) {
			generer_retour(ret.getFils().get(ret.getFils().size() - 1));
			this.res += "\nPUSH(R0)";
			this.res += "\nRTN()";
		} else if(!ast.getLabel().contains("main")) {
			this.res += "\nBR(fin" + ast.getLabel().replace("FONCTION/", "") + ")";
		}

	}

	public void generer_retour(Noeud ast) {

		generer_instruction(ast);

		this.res += "\nPOP(R0)";
		int tmp = ast.getFils().size();
		this.res += "\nPUTFRAME(R0," + tmp * 4 + ")";
	}

	public void generer_instruction(Noeud ast) {

		Categories cat = ast.getCat();
		switch (cat) {
		case AFF:
			generer_affectation(ast);
			break;
		case APPEL:
			generer_appel(ast);
			break;
		case SI:
			generer_si((Si) ast);
			break;
		case TQ:
			generer_tantQue(ast);
			break;
		case ECR:
			generer_ecrire(ast);
			break;
		case BLOC:
			generer_bloc(ast);
			break;
		default:
			generer_expression(ast);
			break;
		}
	}

	public void generer_ecrire(Noeud ast) {
		Noeud f = ast.getFils().get(0);
		generer_expression(f);
		this.res += "\nPOP(R0)";
		this.res += "\nWRINT()";
	}

	public void generer_tantQue(Noeud ast) {
		this.res += "\nBR(boucle" + ((TantQue) ast).getValeur() + ")";
		this.res += "\nboucle" + ((TantQue) ast).getValeur() + ":";
		generer_condition(ast.getFils().get(0));
		this.res += "POP(R0)";
		this.res += "\nBF(R0,finboucle" + ((TantQue) ast).getValeur() + ")";
		generer_bloc(ast.getFils().get(1));
		this.res += "\nBR(boucle" + ((TantQue) ast).getValeur() + ")";
		this.res += "\nfinboucle" + ((TantQue) ast).getValeur() + ":";
	}

	private void generer_bloc(Noeud ast) {
		for (Noeud n : ast.getFils()) {
			generer_instruction(n);
		}
	}

	private void generer_condition(Noeud ast) {
		generer_expression(ast.getFils().get(0));
		generer_expression(ast.getFils().get(1));

		this.res += "\nPOP(R1)";
		this.res += "\nPOP(R0)";

		switch (ast.getCat()) {
		case INF:
			this.res += "\nCMPLT(R0,R1,R0)";
			this.res += "\nPUSH(R0)\n";
			break;
		case SUP:
			this.res += "\nCMPLT(R1,R0,R0)";
			this.res += "\nPUSH(R0)";
			break;
		case EG:
			this.res += "\nCMPEQ(R0,R1,R0)";
			this.res += "\nPUSH(R0)\n";
			break;
		case INFE:
			this.res += "\nCMPLE(R0,R1,R0)";
			this.res += "\nPUSH(R0)\n";
			break;
		case SUPE:
			this.res += "\nCMPLE(R1,R0,R0)";
			this.res += "\nPUSH(R0)\n";
			break;
		case DIF:
			this.res += "\nCMPEQ(R0,R1,R0)";
			this.res += "\nX0RC(R0,1,R0)\n";
			break;
		default:
			break;
		}
	}

	public void generer_si(Si ast) {

		res += "\nsi" + ast.getValeur() + ":";
		generer_condition(ast.getFils().get(0));
		res += "\nPOP(R0)";
		res += "\nBF(R0,sinon" + ast.getValeur() + ")";
		res += "\nalors" + ast.getValeur() + ":";
		ast.getBlocAlors().getFils();
		generer_bloc(ast.getBlocAlors());
		if (ast.getFils().size() > 2) {
			res += "\nBR(finsi" + ast.getValeur() + ")";
			res += "\nsinon" + ast.getValeur() + ":";
			generer_bloc(ast.getBlocSinon());
		}
		res += "\nfinsi" + ast.getValeur() + ":";

	}

	public void setParams() {

		List<Symbole> listSymboles = this.tds.getAllSymboles();
		List<Symbole> listParam = new ArrayList<Symbole>();

		for (Symbole symbole : listSymboles) {
			if (symbole.scope == Scope.Param) {
				listParam.add(symbole);
			}
		}

		List<Noeud> allNodes = getEveryFilsParam(progGeneral);
		List<String> allNodesLabel = new ArrayList<String>();

		allNodes.forEach(n -> allNodesLabel.add(n.getLabel()));

		for (Noeud n : allNodes) {
			if (n.getLabel().startsWith("APPEL/")) {

				Affectation affParam = new Affectation();
				int ind = 0;
				for (Symbole symbole : listParam) {
					affParam.setFilsGauche(new Idf(symbole.nom));
					affParam.setFilsDroit(n.getFils().get(ind));
					ind++;
					generer_affectation(affParam);
				}

			}
		}
	}

	public void generer_appel(Noeud ast) {
		this.res += "\nCALL(" + ast.getLabel().replace("APPEL/FONCTION/", "") + ")";
		this.res += "\nfin" + ast.getLabel().replace("APPEL/FONCTION/", "") + ": ";
	}

	public void generer_affectation(Noeud ast) {
		generer_expression(ast.getFils().get(0));
		generer_expression(ast.getFils().get(1));
		this.res += "\nPOP(R0)";
		this.res += "\nST(R0," + getEveryFilsParam(ast).get(0).getFils().get(0).getLabel().replace("IDF/", "") + ")";
	}

	public void generer_expression(Noeud ast) {

		if (ast.getCat() == Categories.CONST) {
			this.res += "\nCMOVE(" + ((Const) ast).getValeur() + ",R0)";
			this.res += "\nPUSH(R0)";

		} else if (ast.getCat() == Categories.PLUS) {
			
			generer_expression(ast.getFils().get(0));
			generer_expression(ast.getFils().get(1));

			this.res += "\nPOP(R2)";
			this.res += "\nPOP(R1)";
			this.res += "\nADD(R1,R2,R3)";
			this.res += "\nPUSH(R3)";

		} else if (ast.getCat() == Categories.MOINS) {
			generer_expression(ast.getFils().get(0));
			generer_expression(ast.getFils().get(1));
			this.res += "\nPOP(R0)";
			this.res += "\nPOP(R1)";
			this.res += "\nSUB(R0,R1,R2)";
			this.res += "\nPUSH(R2)";
		} else if (ast.getCat() == Categories.MUL) {
			generer_expression(ast.getFils().get(0));
			generer_expression(ast.getFils().get(1));
			this.res += "\nPOP(R2)";
			this.res += "\nPOP(R1)";
			this.res += "\nMUL(R1,R2,R3)";
			this.res += "\nPUSH(R3)";
		} else if (ast.getCat() == Categories.DIV) {
			generer_expression(ast.getFils().get(0));
			generer_expression(ast.getFils().get(1));
			this.res += "\nPOP(R0)";
			this.res += "\nPOP(R1)";
			this.res += "\nDIV(R0,R1,R2)";
			this.res += "\nPUSH(R2)";
		} else if (ast.getCat() == Categories.LIRE) {
			generer_lire(ast);
			this.res += "\nPOP(R0)";
			this.res += "\nPUSH(R0)";
		} else if (ast.getCat() == Categories.IDF) {
			res += "\nLD(" + ast.getLabel().toString().replace("IDF/", "") + ",R0)"
					+ "\nPUSH(R0)";
		} else if (ast.getCat() == Categories.APPEL) {
			generer_appel(ast);
		}
	}

	public void generer_lire(Noeud ast) {
		this.res += "\nRDINT()";
		this.res += "\nPUSH(R0)";
	}
}
