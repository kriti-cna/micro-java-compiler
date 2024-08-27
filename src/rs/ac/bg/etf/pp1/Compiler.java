package rs.ac.bg.etf.pp1;


import java.io.BufferedReader;
import rs.etf.pp1.mj.runtime.Code;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Scope;


public class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void tsdump() {
	      System.out.println("=====================SYMBOL TABLE DUMP=========================");
	      Scope scope = Tab2.currentScope();
	      DumpVisit stv = new DumpVisit();
	      for (Scope s = scope; s != null; s = s.getOuter()) {
	          s.accept(stv);
	      }
	      System.out.println(stv.getOutput());
	  }
	
	
	
	public static void main(String[] args) throws Exception {
		
		
		Logger log = Logger.getLogger(Compiler.class);
		
		Reader br = null;
		
		try {
			File sourceCode = new File(args[0]);
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			log.info("================= LEKSICKA ANALIZA =================");
			Yylex lexer = new Yylex(br);
			
			MJParser parser = new MJParser(lexer);
			
	        Symbol s = parser.parse();  //pocetak parsiranja
	        log.info("================= PARSIRANO STABLO =================");
	        Tab2.init2();
	        if (parser.errorDetected)
                log.info("Postoji semanticka greska");
            else {
            	Program prog = (Program)(s.value); 
    			// ispis sintaksnog stabla
    			log.info(prog.toString(""));
    			log.info("================ Semanticka obrada ===================");

    			// ispis prepoznatih programskih konstrukcija
    			RuleVisitor v = new RuleVisitor();
    			SemanticAnalyzer sa = new SemanticAnalyzer();
    			//prog.traverseBottomUp(v); 
    			prog.traverseBottomUp(sa);
    			//log.info(" Print count calls = " + v.printCallCount);
    			//log.info(" Deklarisanih promenljivih ima = " + v.varDeclCount);
            	//Tab2.dump();
            	if (!parser.errorDetected && sa.passed()) {
                    File objFile = new File(args[1]);
                    if (objFile.exists()) objFile.delete();

                    CodeGenerator codeGenerator = new CodeGenerator();
                    Code.dataSize = sa.nVars;
                    prog.traverseBottomUp(codeGenerator);

                    Code.mainPc = codeGenerator.mainPc;
                    Code.write(new FileOutputStream(objFile));

                    log.info("Parser SUCCESS!");
                    tsdump();
                } else {
                    log.error("Parser FAIL!");
                }
                
            }
			
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	
}
