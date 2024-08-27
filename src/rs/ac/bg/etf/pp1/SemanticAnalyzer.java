package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

	int printCallCount = 0;
	int varDeclCount = 0;
	int nVars;
	Obj currentMethod = null;
	boolean returnFound = false;
	boolean errorDetected = false;
	int parCnt;
	Struct curType = Tab.noType;
	Struct curMethodType = null;
	String currNamespace = "";
	DumpVisit stv = new DumpVisit();
	
	
	Logger log = Logger.getLogger(getClass());
	
    
    
    
    
    
	
    public boolean passed() {
        return !errorDetected;
    }
    
    public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" on line ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		//String infos = getNodeAsString(info);
		if (line != 0)
			msg.append (" on line ").append(line);
		log.info(msg.toString());
	}
	
	
	
	public void visit(ProgName progName) {
        progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
        Tab.openScope();
    }
	
	public void visit(Program program) {
        nVars = Tab.currentScope.getnVars();
        Obj found = Tab.find("main");
        if (found == Tab.noObj || found.getKind() != Obj.Meth || found.getLevel() != 0 || found.getType() != Tab.noType)
            report_info("Main() method not found", program);
        else {
        	report_info(stv.getSingleObjNodeOutput(found), program);
        	//report_info("main() method found", program);
        	
        }
        Tab.chainLocalSymbols(program.getProgName().obj);
        Tab.closeScope();
    }
	
    

    public void visit(Type type) {
    	String name =type.getTypeName();
        Obj typeNode = Tab.find(name);
        if (typeNode == Tab.noObj) {
            report_error("Type " + name + " does not exist", type);
            type.struct = Tab.noType;
        } else if (Obj.Type == typeNode.getKind()) {
           type.struct = typeNode.getType();
           curType = type.struct;
        } else {
            report_error("Type " + name + " is not type", type);
            type.struct = Tab.noType;
         }
    }
    
    public void visit(ConstDef constDef) {
        String name = constDef.getConstName();
        String fullName = !Objects.equals(currNamespace, "") ? currNamespace + "$" + name : name;
        Obj typeNode = Tab.currentScope.findSymbol(fullName);
        int val = -1;
        if (typeNode != null)
            report_error("Const " + name + " already exist", constDef);
        else {
        	Obj newObj = Tab.insert(Obj.Con, fullName, curType);
        	if(constDef.getConst() instanceof NumConst) {
        		val = ((NumConst) constDef.getConst()).getN1();
        	}
        	if(constDef.getConst() instanceof CharConst) {
        		val = ((CharConst) constDef.getConst()).getC1();
        	}
        	if(constDef.getConst() instanceof BoolConst) {
        		val = ((BoolConst) constDef.getConst()).getB1().equals("true")?0:1;
        	}
        	//log.info(Tab.toString())
        	newObj.setAdr(val);
        	//report_info("Const " + name + " defined", constDef);
        	log.info("NEW CONST");
        	report_info(stv.getSingleObjNodeOutput(newObj), constDef);
        }
            
    }
    

    public void visit(AddVarDecl1 addVarDecl) {
        String name = addVarDecl.getVarName();
    	String varName;
        if (curMethodType == null)
            varName = !Objects.equals(currNamespace, "") ? currNamespace + "$" + name : name;
        else
            varName = name;

        Obj typeNode = Tab.currentScope.findSymbol(varName);

        if (typeNode != null) {
            report_error("Variable " + name + " already exists", addVarDecl);
        } else {
            //Struct type = addVarDecl.getOptBrackets() instanceof Brackets ? new Struct(Struct.Array, curType) : curType;
            OptBrackets br = addVarDecl.getOptBrackets();
            Struct type;
            Obj n;
            if(br instanceof Brackets) {
            	type = new Struct(Struct.Array, curType);
            	n = Tab.insert(Obj.Var, varName, type);
            }
            else if( br instanceof TwoBrackets) {
            	
            	type = new Struct(8);
            	type.setElementType(new Struct(Struct.Array, curType));
            	n = Tab.insert(Obj.Var, varName, type);
            	//type.kind=8;
            }
            else {
            	type = curType;
            	n = Tab.insert(Obj.Var, varName, type);
            }
            	
            log.info("NEW SYMBOL");
            report_info(stv.getSingleObjNodeOutput(n), addVarDecl);
        }
    }
    
    
    public void visit(WithoutNamespace withoutNamespace) {
        String varName = withoutNamespace.getDesName();
        String fullName = varName;
        StringBuilder output = new StringBuilder();
        if (currNamespace != null && Tab.currentScope().findSymbol(varName) == null)
            	fullName = Objects.equals(currNamespace, "") ?
                        varName : currNamespace + "$" + withoutNamespace.getDesName();

        Obj found = Tab.find(fullName);
        if (found == Tab.noObj) {
        	report_error("Symbol " + withoutNamespace.getDesName() + " does not exist", withoutNamespace);
        	return;
        }     
        else {
        	withoutNamespace.obj = found; 	
        	//report_info("Symbol " + withoutNamespace.getDesName() + " found", withoutNamespace);
        	report_info(stv.getSingleObjNodeOutput(found), withoutNamespace);
        }
           
    }
    
    public void visit(Designator designator) {
        Obj found = null;
        if (designator.getDesignatorName() instanceof WithoutNamespace) {
            found = ((WithoutNamespace) designator.getDesignatorName()).obj;
        } else if(designator.getDesignatorName() instanceof WithNamespace) {
            found = ((WithNamespace) designator.getDesignatorName()).obj;
        }

        if (found == null) {
            report_error("Identifier does not exist ", designator);
            return;
        } else {
        	//report_info("Identifier found ", designator);
            designator.obj = found;
        }

        if (designator.obj.getType().getKind() == Struct.Array && designator.getOptBraExpr() instanceof AddArrayExpression)
        	designator.obj = ((AddArrayExpression) (designator.getOptBraExpr())).getBraExpr().obj;
        /*else if(designator.obj.getType().getKind() == Struct.Array && ! (designator.getOptBraExpr() instanceof AddArrayExpression )) {
        	report_error("Identifier is of type array ", designator);
        }*/
        if (designator.obj.getType().getKind() == 8 && designator.getOptBraExpr() instanceof AddMatrixExpression)
        	designator.obj = ((AddMatrixExpression) (designator.getOptBraExpr())).getBraExpr1().obj;
        /*else if(designator.obj.getType().getKind() == 8 && !(designator.getOptBraExpr() instanceof AddMatrixExpression)) {
        	report_error("Identifier is of type matrix ", designator);
        }*/
    }

    
    public void visit(ExpressionFact expressionFact) {
    	expressionFact.struct = expressionFact.getExpr().struct;
    }
    
    
    public void visit(ReadDesignator readDesignator) {
    	//log.info(readDesignator.toString());
        Designator d = readDesignator.getDesignator();
        
        boolean isVarOrElem = d.obj.getKind() == Obj.Var || d.obj.getKind() == Obj.Elem ;
        boolean isValidType = d.obj.getType() == Tab2.intType ||
                              d.obj.getType() == Tab2.charType ||
                              d.obj.getType() == Tab2.boolType;
        
        boolean isArrayType = d.obj.getType().getKind() == Struct.Array;

        if (isVarOrElem && isValidType || isArrayType ) {
            report_info("read() function call", readDesignator);
            return;
        }

        report_error("read() argument is not valid", readDesignator);
    }
    
    
    public void visit(PrintExpr printExpr) {
        Struct kind = printExpr.getExpr().struct;
        if (kind != Tab2.intType && kind != Tab2.charType && kind != Tab2.boolType && kind.getKind() != Struct.Array && kind.getKind() != 8 )
            report_error("print() argument is not valid", printExpr);
    }
    

    
    public void visit(AddArrayExpression addArrayExpression) {
    	Designator d = (Designator)(addArrayExpression.getParent());
    	Obj parentObj = d.getDesignatorName().obj;
    	BraExpr braExpr = addArrayExpression.getBraExpr();
    	if(parentObj.getType().getKind()!=Struct.Array) {
    		report_error("Designator must be an array", d);
    	}
    	if (parentObj == null || parentObj == Tab.noObj) {
            braExpr.obj = Tab.noObj;
    	} else {
            if (braExpr.getExpr().struct != Tab.intType) {
                report_error("Array size inside [] must be an Int", braExpr);
            }
            braExpr.obj = new Obj(Obj.Elem, "elem", parentObj.getType().getElemType());
    	}
    	
    	
    }
    public void visit(AddMatrixExpression addMatrixExpression) {
    	Designator d = (Designator)(addMatrixExpression.getParent());
    	Obj parentObj = d.getDesignatorName().obj;
    	BraExpr braExpr = addMatrixExpression.getBraExpr();
    	BraExpr braExpr1 = addMatrixExpression.getBraExpr1();
    	if(parentObj.getType().getKind()!=8) {
    		report_error("Designator must be a matrix", d);
    	}
    	if (parentObj == null || parentObj == Tab.noObj) {
            braExpr.obj = Tab.noObj;
    	} else {
            if (braExpr.getExpr().struct != Tab.intType || braExpr1.getExpr().struct != Tab.intType ) {
                report_error("Matrix size inside [] must be an Int", braExpr);
            }
            braExpr.obj = new Obj(Obj.Elem, "elem", parentObj.getType().getElemType().getElemType());
            braExpr1.obj = new Obj(Obj.Elem, "elem", parentObj.getType().getElemType().getElemType());
            int b = 5;
    	}
    	
    }
    public void visit(NoArrayExpression noArrayExpression) {
    	Designator d = (Designator)(noArrayExpression.getParent());
    	Obj parentObj = d.getDesignatorName().obj;
    	/*if(parentObj.getType().getKind()==8 || parentObj.getType().getKind()==Struct.Array) {
    		report_error("Designator is of type arr or mat", d);
    	}*/
    }
    
    
    private boolean checkDesignatorType(Designator d) {
        return d.obj.getKind() == Obj.Var || d.obj.getKind() == Obj.Elem;
    }
    
    public void visit(DesignatorExpr designatorExpr) {
    	Obj left = designatorExpr.getDesignator().obj;
        Struct leftType = designatorExpr.getDesignator().obj.getType();
        Struct rightType = designatorExpr.getExpr().struct;
        //log.info("LEFT " + leftType.getKind() + designatorExpr.getDesignator().obj.toString());
        //log.info("GIRHT " + rightType.getKind() + designatorExpr.getExpr().struct.toString());
        int a = 5;
        if(left.getKind()!= Obj.Elem && left.getKind()!= Obj.Var && left.getKind()!= Obj.Fld ) {
        	report_error("Designator is not Var Elem or Fld", designatorExpr);
        }
        if (leftType.getKind() == Struct.Array && rightType.getKind() == Struct.Array) {
            leftType = leftType.getElemType();
            rightType = rightType.getElemType();
        }
        if (leftType.getKind() == 8 && rightType.getKind() == 8) {
            leftType = leftType.getElemType().getElemType();
            rightType = rightType.getElemType().getElemType();
        }
        if (leftType.getKind() != rightType.getKind()) {
            report_error("Types not matching ", designatorExpr);
        }
    }
    
    
    public void visit(DesignatorPP designatorPP) {
        if (!checkDesignatorType(designatorPP.getDesignator()) || designatorPP.getDesignator().obj.getType() != Tab.intType)
            report_error("Error: Designator must be int", designatorPP);
    }
    

    public void visit(DesignatorMM designatorMM) {
        if (!checkDesignatorType(designatorMM.getDesignator()) || designatorMM.getDesignator().obj.getType() != Tab.intType)
            report_error("Error: Designator must be int", designatorMM);
    }
    

    
    public void visit(SingleTerm singleTerm) {
        singleTerm.struct = singleTerm.getFactor().struct;
    }
    
    public void visit(MultipleTerms multipleTerms) {
        if (multipleTerms.getTerm().struct != Tab.intType || multipleTerms.getFactor().struct != Tab.intType)
            report_error("Invalid mulop for types", multipleTerms);
        multipleTerms.struct = multipleTerms.getTerm().struct;
    }


    
    public void visit(PositiveStartExpr positiveStartExpr) {
    	//log.info("POSITIVEEXPRSTART" + positiveStartExpr.getTerm().struct.getKind());
    	positiveStartExpr.struct = positiveStartExpr.getTerm().struct;
    }
    
    public void visit(AddExpression addExpression) {
    	addExpression.struct = addExpression.getTerm().struct;
    	
        if ((addExpression.getExpr() instanceof AddExpression && addExpression.getExpr().struct != Tab.intType)
                || addExpression.getTerm().struct != Tab.intType) {
            report_error("Not allowed operation with int", addExpression.getParent());
            addExpression.struct = Tab.noType;
        }
    }
    
    public void visit(RangeExpression rangeExpression) {
    	rangeExpression.struct = new Struct(Struct.Array, Tab.intType);
        if (rangeExpression.getExpr().struct != Tab.intType)
            report_error("Invalid expression for range []", rangeExpression);
    }
    

    public void visit(NegativeStartExpr negativeStartExpr) {
    	negativeStartExpr.struct = negativeStartExpr.getTerm().struct;
        if (negativeStartExpr.struct != Tab.intType) {
            report_error("Invalid expression type", negativeStartExpr);
            negativeStartExpr.struct = Tab.noType;
        }
    }



    
    public void visit(DesignatorFact designatorFact) {
    	if (designatorFact.getDesignator().obj != null)
    		designatorFact.struct = designatorFact.getDesignator().obj.getType();
    }
    
    public void visit(CharacterConst characterConst) {
    	characterConst.struct = Tab2.charType;
    }
    
    public void visit(BooleanConst booleanConst) {
    	booleanConst.struct = Tab2.boolType;
    }
    
    public void visit(NumberConst numberConst) {
        numberConst.struct = Tab2.intType;
    }
    
    public void visit(CharConst charConst) {
        charConst.struct = Tab2.charType;
    }


    public void visit(BoolConst boolConst) {
        boolConst.struct = Tab2.boolType;
    }
    
    public void visit(NumConst numConst) {
    	numConst.struct = Tab2.intType;
    }
    
    public void visit(MaxMatrix maxMatrix) {
    	if(maxMatrix.getDesignator().obj.getType().getKind() != 8) {
    		report_error("Invalid argument for MAX", maxMatrix);
    	}else {
    		maxMatrix.struct = new Struct(Struct.Int);
    	}
    }
    

    public void visit(NewType newType) {
    	newType.struct = new Struct(Struct.Array, newType.getType().struct);
        if (newType.getExpr().struct != Tab.intType)
            report_error("Invalid expression inside []", newType);
    }
    
    public void visit(NewTypeMat newTypeMat) {
    	newTypeMat.struct = new Struct(8);
    	newTypeMat.struct.setElementType(new Struct(Struct.Array, newTypeMat.getType().struct));
    	
        if (newTypeMat.getExpr().struct != Tab.intType || newTypeMat.getExpr1().struct != Tab.intType)
            report_error("Invalid expression inside []", newTypeMat);

    }
    
    
    
    public void visit(WithNamespace withNamespace) {
        Obj nsp = Tab.find(withNamespace.getNspName());
        if (nsp == Tab.noObj) {
            report_error("Namespace " + withNamespace.getNspName() + " does not exist", withNamespace);
            return;
        }
        String symbolName = Objects.equals(withNamespace.getNspName(), "") ?
                withNamespace.getDesName() : withNamespace.getNspName() + "$" + withNamespace.getDesName();
        //log.info("SYMBOL NAME  " + symbolName);
        Obj found = Tab.find(symbolName);
        if (found == null) {
            report_error("Symbol " + withNamespace.getDesName() + " does not exist", withNamespace);
            return;
        } else {
        	report_info("Symbol " + withNamespace.getDesName() + " found", withNamespace);
            withNamespace.obj = found;
        }
    }
    
    
    public void visit(ReturnVoid returnVoid) {
    	if(curMethodType != Tab2.noType) {
    		report_error("Return type"+ curMethodType + ", expected void", returnVoid);
    	}
    }
    
    public void visit(Namespace namespace) {
    currNamespace = "";
    }

	public void visit(NamespaceName namespaceName) {
		String nspName= namespaceName.getNspName();
	    Obj namespaceFind = Tab.currentScope.findSymbol(nspName);
	    if (namespaceFind != null)
	        report_error("Namespace " + nspName + " already exists", namespaceName);
	    else
	        namespaceName.obj = Tab.insert(Obj.Type, nspName, Tab.noType);
	
	    currNamespace = namespaceName.getNspName();
	}
    
    
    public void visit(CondFact condFact) {
    	Expr exprLeft = condFact.getExpr();
    	if(condFact.getOptExpr() instanceof Compare ) {
    		Expr exprRight = ((Compare)condFact.getOptExpr()).getExpr();
    		Relop relop = ((Compare)condFact.getOptExpr()).getRelop();
    	}
    }
    
    public void visit(MethodWithType methodWithType) {
    	methodWithType.struct = methodWithType.getType().struct;
    	curMethodType = methodWithType.struct;
    }
    
    public void visit(MethodVoid methodVoid) {
    	methodVoid.struct = Tab.noType;
    	curMethodType = methodVoid.struct;
    }
    
    
    public void visit(MethodName methodName) {
    	String name = methodName.getName();
    	String fullName = !Objects.equals(currNamespace, "") ? currNamespace + "$" + name : name;
        Obj typeNode = Tab.currentScope.findSymbol(fullName);
        if (typeNode != null)
            report_error("Method " + fullName + " already exist", methodName);
        else
            methodName.obj = Tab.insert(Obj.Meth, fullName, curMethodType);
        Tab.openScope();
    }
    

    public void visit(MethodDecl methodDecl) {
        methodDecl.obj = methodDecl.getMethodName().obj;
        methodDecl.obj.setLevel(parCnt);
        parCnt = 0;
        curMethodType = null;
        Tab.chainLocalSymbols(methodDecl.obj);
        Tab.closeScope();
        
    }
    
    public void visit(FormPars formPars) {
        Struct type = formPars.getType().struct;

        if (formPars.getOptBrackets() instanceof Brackets)
            type = new Struct(Struct.Array, type);

        Tab.insert(Obj.Var, formPars.getParName(), type);
        parCnt++;
    }
    
    
    
    /*public static void miniVisit() {
	      Scope scope = Tab2.currentScope();
	      DumpVisit stv = new DumpVisit();
	      //Scope s = scope.getOuter();
	      scope.accept(stv);
	      
	      
	      System.out.println(stv.getOutput());
	  }*/
    
	    /*public void visit(BraExpr braExpr) {
	    SyntaxNode parent = ((AddArrayExpression) braExpr.getParent()).getParent();
	    Obj parentObj = null;
	
	    if (parent instanceof Designator) {
	        Designator designator = (Designator) parent;
	        if (designator.getDesignatorName() instanceof WithoutNamespace) {
	        	parentObj = ((WithoutNamespace) designator.getDesignatorName()).obj;
	        } else if (designator.getDesignatorName() instanceof WithNamespace) {
	        	parentObj = ((WithNamespace) designator.getDesignatorName()).obj;
	        }
	    }
	
	    if (parentObj == null || parentObj == Tab.noObj) {
	        braExpr.obj = Tab.noObj;
	    } else {
	        if (braExpr.getExpr().struct != Tab.intType) {
	            report_error("Array/Matrix size inside [] must be an Int", braExpr);
	        }
	        braExpr.obj = new Obj(Obj.Elem, "elem", parentObj.getType().getElemType());
	    }
	}*/



	
}