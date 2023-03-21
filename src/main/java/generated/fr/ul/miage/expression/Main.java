package generated.fr.ul.miage.expression;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;


public class Main {
	private static final Logger LOG = Logger.getLogger(Main.class.getName());
	public static void main(String[] args) {
		LOG.info("Démarrage...");
		Yylex scanner = new Yylex(new BufferedReader(new InputStreamReader(System.in)));
		ParserCup parser = new ParserCup(scanner);
		try {
			parser.parse();
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Syntax error !");
		}
		LOG.info("Terminé !");
	}
}
