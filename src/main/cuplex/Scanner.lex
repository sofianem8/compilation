/*
Scanner pour le parser d'expression de constantes
TD Compil L3 Miage
Azim Roussanaly
(c) Université de Lorraine
2020
*/
package generated.fr.ul.miage.expression;
import java_cup.runtime.Symbol;

%%
/* options */
%line
%public
%cupsym Sym
%cup

%{
	void erreur(){
		System.out.println("Caractère inattendu");
		System.exit(1);
	}
%}
/* macros */
SEP     =   [ \t]
NUM     =   [0-9]+
FIN     =   \r|\n|\r\n

%%
/* règles */
"+"		{ return new Symbol(Sym.ADD);}
"*"		{ return new Symbol(Sym.MUL);}
"("		{ return new Symbol(Sym.PO);}
")"		{ return new Symbol(Sym.PF);}
{NUM}	{ return new Symbol(Sym.NUM);}
{SEP}	{;}
{FIN}	{return new Symbol(Sym.EOF);}
.		{erreur();}
