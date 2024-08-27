package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;


public class RuleVisitor extends VisitorAdaptor{
    int printCallCount;
    Logger log = Logger.getLogger(getClass());
	public void visit(PrintExpr PrintExpr) { 
		printCallCount++;
		
		log.info("Prepoznata naredba Print!");
	}
}
