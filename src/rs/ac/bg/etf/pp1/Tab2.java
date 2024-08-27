package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

import java.util.Collection;

// Usluzne metode koje nadopunjuju tab
public class Tab2 extends Tab{
	public static final Struct boolType = new Struct(Struct.Bool);
	public static final Struct matrixType = new Struct(8);


  public static void init2() {
      init();
      currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
      //currentScope.addToLocals(new Obj(Obj.Type, "matrix", matrixType));
  }
  
  

  public static Obj findSymbol2(String name) {
	    Obj resultObj = null;

	    for (Scope scope = currentScope; scope != null; scope = scope.getOuter()) {
	        SymbolDataStructure localSymbols = scope.getLocals();

	        if (localSymbols != null) {
	            for (Obj symbol : localSymbols.symbols()) {
	                if (symbol.getName().equals(name)) {
	                    resultObj = symbol;
	                    break;
	                } else {
	                    Collection<Obj> nestedLocalSymbols = symbol.getLocalSymbols();
	                    if (nestedLocalSymbols != null) {
	                        for (Obj nestedSymbol : nestedLocalSymbols) {
	                            if (nestedSymbol.getName().equals(name)) {
	                                resultObj = nestedSymbol;
	                                break;
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    }

	    return (resultObj != null) ? resultObj : noObj;
	}



}
