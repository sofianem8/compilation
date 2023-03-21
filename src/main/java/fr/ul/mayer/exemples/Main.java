package fr.ul.mayer.exemples;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import fr.ul.miage.arbre.Affectation;
import fr.ul.miage.arbre.Appel;
import fr.ul.miage.arbre.Bloc;
import fr.ul.miage.arbre.Const;
import fr.ul.miage.arbre.Ecrire;
import fr.ul.miage.arbre.Fonction;
import fr.ul.miage.arbre.Idf;
import fr.ul.miage.arbre.Inferieur;
import fr.ul.miage.arbre.Lire;
import fr.ul.miage.arbre.Multiplication;
import fr.ul.miage.arbre.Noeud;
import fr.ul.miage.arbre.Plus;
import fr.ul.miage.arbre.Prog;
import fr.ul.miage.arbre.Retour;
import fr.ul.miage.arbre.Si;
import fr.ul.miage.arbre.Superieur;
import fr.ul.miage.arbre.TantQue;
import fr.ul.miage.arbre.TxtAfficheur;
import fr.ul.mayer.exemples.Symbole.Scope;

public class Main {

	public static void main(String[] args) {

		// Exemple 1
		Prog prog1 = new Prog();
		Fonction principal1 = new Fonction("main");
		prog1.ajouterUnFils(principal1);

		// Exemple 2
		Prog prog2 = new Prog();
		Fonction principal2 = new Fonction("main");
		prog2.ajouterUnFils(principal2);

		TableDesSymboles tds2 = new TableDesSymboles();

		tds2.ajouterSymbole(new Symbole("i", Scope.Glob, 10));
		tds2.ajouterSymbole(new Symbole("j", Scope.Glob, 20));
		tds2.ajouterSymbole(new Symbole("k", Scope.Glob, 0));
		tds2.ajouterSymbole(new Symbole("l", Scope.Glob, 0));

		// Exemple 3
		Prog prog3 = new Prog();
		Fonction principal3 = new Fonction("main");
		Affectation aff3_1 = new Affectation();
		Idf k3 = new Idf("k");
		Const const3_1 = new Const(2);
		Affectation aff3_2 = new Affectation();
		Idf l3 = new Idf("l");
		Plus plus3 = new Plus();
		Idf i3 = new Idf("i");
		Multiplication fois1 = new Multiplication();
		Const const3_2 = new Const(3);
		Idf j3 = new Idf("j");

		prog3.ajouterUnFils(principal3);
		principal3.ajouterUnFils(aff3_1);
		aff3_1.setFilsGauche(k3);
		aff3_1.setFilsDroit(const3_1);

		principal3.ajouterUnFils(aff3_2);
		aff3_2.setFilsGauche(l3);
		aff3_2.setFilsDroit(plus3);
		plus3.setFilsGauche(i3);
		plus3.setFilsDroit(fois1);
		fois1.setFilsGauche(const3_2);
		fois1.setFilsDroit(j3);

		TableDesSymboles tds3 = new TableDesSymboles();

		tds3.ajouterSymbole(new Symbole("i", Scope.Glob, 10));
		tds3.ajouterSymbole(new Symbole("j", Scope.Glob, 20));
		tds3.ajouterSymbole(new Symbole("k", Scope.Glob, 0));
		tds3.ajouterSymbole(new Symbole("l", Scope.Glob, 0));

		// Exemple 4
		Prog prog4 = new Prog();
		Fonction principal4 = new Fonction("main");
		Affectation aff4 = new Affectation();
		Idf i4_1 = new Idf("i");
		Lire lire4 = new Lire();
		Ecrire ecrire4 = new Ecrire();
		Plus plus4 = new Plus();
		Idf i4_2 = new Idf("i");
		Idf j4 = new Idf("j");

		prog4.ajouterUnFils(principal4);
		principal4.ajouterUnFils(aff4);
		principal4.ajouterUnFils(ecrire4);
		aff4.setFilsGauche(i4_1);
		aff4.setFilsDroit(lire4);
		ecrire4.setLeFils(plus4);
		plus4.setFilsGauche(i4_2);
		plus4.setFilsDroit(j4);

		TableDesSymboles tds4 = new TableDesSymboles();

		tds4.ajouterSymbole(new Symbole("i", Scope.Glob, 0));
		tds4.ajouterSymbole(new Symbole("j", Scope.Glob, 20));

		// Exemple 5
		Prog prog5 = new Prog();
		Fonction principal5 = new Fonction("main");
		Affectation aff5 = new Affectation();
		Idf i5_1 = new Idf("i");
		Lire lire5 = new Lire();
		Si si5 = new Si(1);
		Superieur sup5 = new Superieur();
		Idf i5_2 = new Idf("i");
		Const const5_1 = new Const(10);
		Bloc bloc5_1 = new Bloc();
		Ecrire ecrire5_1 = new Ecrire();
		Const const5_2 = new Const(1);
		ecrire5_1.setLeFils(const5_2);
		Bloc bloc5_2 = new Bloc();
		Ecrire ecrire5_2 = new Ecrire();
		Const const5_3 = new Const(2);
		ecrire5_2.setLeFils(const5_3);

		List<Noeud> l1 = new ArrayList<Noeud>();
		l1.add(ecrire5_1);

		List<Noeud> l2 = new ArrayList<Noeud>();
		l2.add(ecrire5_2);

		prog5.ajouterUnFils(principal5);
		principal5.ajouterUnFils(aff5);
		aff5.setFilsGauche(i5_1);
		aff5.setFilsDroit(lire5);
		principal5.ajouterUnFils(si5);
		si5.setCondition(sup5);
		sup5.setFilsGauche(i5_2);
		sup5.setFilsDroit(const5_1);
		bloc5_1.setFils(l1);
		bloc5_2.setFils(l2);
		si5.setBlocAlors(bloc5_1);
		si5.setBlocSinon(bloc5_2);

		TableDesSymboles tds5 = new TableDesSymboles();

		tds5.ajouterSymbole(new Symbole("i", Scope.Glob, 0));

		// Exemple 6
		Prog prog6 = new Prog();
		Fonction principal6 = new Fonction("main");
		Affectation aff6_1 = new Affectation();
		Idf i6_1 = new Idf("i");
		Const const6_1 = new Const(0);
		TantQue tq6 = new TantQue(1);
		Inferieur inf6 = new Inferieur();
		Idf i6_2 = new Idf("i");
		Idf i6_3 = new Idf("n");
		Bloc bloc6 = new Bloc();
		Ecrire ecrire6 = new Ecrire();
		Idf i6_4 = new Idf("i");
		Affectation aff6_2 = new Affectation();
		Idf i6_5 = new Idf("i");
		Plus plus6 = new Plus();
		Idf i6_6 = new Idf("i");
		Const const6_2 = new Const(1);

		prog6.ajouterUnFils(principal6);
		principal6.ajouterUnFils(aff6_1);
		aff6_1.setFilsGauche(i6_1);
		aff6_1.setFilsDroit(const6_1);
		principal6.ajouterUnFils(tq6);
		tq6.setCondition(inf6);
		inf6.setFilsGauche(i6_2);
		inf6.setFilsDroit(i6_3);
		tq6.setBloc(bloc6);
		bloc6.ajouterUnFils(ecrire6);
		ecrire6.setLeFils(i6_4);
		bloc6.ajouterUnFils(aff6_2);
		aff6_2.setFilsGauche(i6_5);
		aff6_2.setFilsDroit(plus6);
		plus6.setFilsGauche(i6_6);
		plus6.setFilsDroit(const6_2);

		TableDesSymboles tds6 = new TableDesSymboles();

		tds6.ajouterSymbole(new Symbole("i", Scope.Glob, 0));
		tds6.ajouterSymbole(new Symbole("n", Scope.Glob, 5));

		// Exemple 7
		Prog prog7 = new Prog();
		Fonction f7 = new Fonction("f");
		Affectation aff7_1 = new Affectation();
		Idf i7_1 = new Idf("x");
		Const const7_1 = new Const(1);
		Affectation aff7_2 = new Affectation();
		Idf i7_2 = new Idf("y");
		Const const7_2 = new Const(1);
		Affectation aff7_3 = new Affectation();
		Idf i7_3 = new Idf("a");
		Plus plus7_1 = new Plus();
		Idf i7_4 = new Idf("i");
		Plus plus7_2 = new Plus();
		Idf i7_5 = new Idf("x");
		Idf i7_6 = new Idf("y");
		Fonction principal7 = new Fonction("main");
		Appel app7 = new Appel(f7);
		Const const7_3 = new Const(3);
		Ecrire ecrire7 = new Ecrire();
		Idf i7_7 = new Idf("a");

		prog7.ajouterUnFils(f7);
		f7.ajouterUnFils(aff7_1);
		aff7_1.setFilsGauche(i7_1);
		aff7_1.setFilsDroit(const7_1);
		f7.ajouterUnFils(aff7_2);
		aff7_2.setFilsGauche(i7_2);
		aff7_2.setFilsDroit(const7_2);
		f7.ajouterUnFils(aff7_3);
		aff7_3.setFilsGauche(i7_3);
		aff7_3.setFilsDroit(plus7_1);
		plus7_1.setFilsGauche(i7_4);
		plus7_1.setFilsDroit(plus7_2);
		plus7_2.setFilsGauche(i7_5);
		plus7_2.setFilsDroit(i7_6);
		prog7.ajouterUnFils(principal7);
		principal7.ajouterUnFils(app7);
		app7.ajouterUnFils(const7_3);
		principal7.ajouterUnFils(ecrire7);
		ecrire7.setLeFils(i7_7);

		TableDesSymboles tds7 = new TableDesSymboles();

		tds7.ajouterSymbole(new Symbole("a", Scope.Glob, 0));
		tds7.ajouterSymbole(new Symbole("i", Scope.Param, 0));
		tds7.ajouterSymbole(new Symbole("x", Scope.Glob, 0));
		tds7.ajouterSymbole(new Symbole("y", Scope.Glob, 0));

		// Exemple 8
		Prog prog8 = new Prog();
		Fonction f8 = new Fonction("f");
		Affectation aff8_1 = new Affectation();
		Idf i8_1 = new Idf("x");
		Plus plus8_1 = new Plus();
		Idf i8_2 = new Idf("i");
		Idf i8_3 = new Idf("j");
		Retour retour8 = new Retour(f8);
		Plus plus8_2 = new Plus();
		Idf i8_4 = new Idf("x");
		Const const8_1 = new Const(10);
		Fonction principal8 = new Fonction("main");
		Affectation aff8_2 = new Affectation();
		Idf i8_5 = new Idf("a");
		Appel app8 = new Appel(f8);
		Const const8_2 = new Const(1);
		Const const8_3 = new Const(2);
		Ecrire ecrire8 = new Ecrire();
		Idf i8_6 = new Idf("a");

		prog8.ajouterUnFils(f8);
		f8.ajouterUnFils(aff8_1);
		aff8_1.setFilsGauche(i8_1);
		aff8_1.setFilsDroit(plus8_1);
		plus8_1.setFilsGauche(i8_2);
		plus8_1.setFilsDroit(i8_3);
		f8.ajouterUnFils(retour8);
		retour8.setLeFils(plus8_2);
		plus8_2.setFilsGauche(i8_4);
		plus8_2.setFilsDroit(const8_1);
		prog8.ajouterUnFils(principal8);
		principal8.ajouterUnFils(aff8_2);
		aff8_2.setFilsGauche(i8_5);
		aff8_2.setFilsDroit(app8);
		app8.ajouterUnFils(const8_2);
		app8.ajouterUnFils(const8_3);
		principal8.ajouterUnFils(ecrire8);
		ecrire8.setLeFils(i8_6);

		TableDesSymboles tds8 = new TableDesSymboles();

		tds8.ajouterSymbole(new Symbole("a", Scope.Glob, 0));
		tds8.ajouterSymbole(new Symbole("i", Scope.Param, 0));
		tds8.ajouterSymbole(new Symbole("x", Scope.Glob, 0));
		tds8.ajouterSymbole(new Symbole("j", Scope.Param, 0));

		System.out.println("Arbre du programme 1 : ");
		TxtAfficheur.afficher(prog1);
		System.out.println("Arbre du programme 2 : ");
		TxtAfficheur.afficher(prog2);
		System.out.println("Arbre du programme 3 : ");
		TxtAfficheur.afficher(prog3);
		System.out.println("Arbre du programme 4 : ");
		TxtAfficheur.afficher(prog4);
		System.out.println("Arbre du programme 5 : ");
		TxtAfficheur.afficher(prog5);
		System.out.println("Arbre du programme 6 : ");
		TxtAfficheur.afficher(prog6);
		System.out.println("Arbre du programme 7 : ");
		TxtAfficheur.afficher(prog7);
		System.out.println("Arbre du programme 8 : ");
		TxtAfficheur.afficher(prog8);

		// Test grammaire affectation
		// x = 0;
		// x = 2+2;
		// x = y;
		// x = y + 2;
		// x = (2+y)*3;
		// x = (f(2)+y)*3;
		// x = lire();

		Generator g = new Generator(tds7);
		String res = g.genererCode(prog7);
		System.out.println(res);

	}

}