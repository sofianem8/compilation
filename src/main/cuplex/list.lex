import java_cup.runtime.Symbol;
%%
%line
%public
%cupsym Sym
%cup

SEP = [\t]
NUM = [0-9]+
VIR = [,]
%%

{NUM} {return new Symbol(Sym.NUM);}
{SEP} {}
{VIR} {return new Symbol(Sym.VIR}
[^] {System.exit(1);}