package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.Tab;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.mj.runtime.Code;

import java.util.*;

public class CodeGenerator extends VisitorAdaptor {

    int mainPc;
    String currNamespace;
    int lastVal = 0;

    public void visit(NamespaceName namespaceName) {
        currNamespace = namespaceName.getNspName();
    }

    public void visit(Namespace namespace) {
        currNamespace = "";
    }

    
    public void visit(MultipleTerms multipleTerms) {
        SyntaxNode type = multipleTerms.getMulop();
        if (type instanceof Multiply)
            Code.put(Code.mul);
        else if (type instanceof Divide)
            Code.put(Code.div);
        else if (type instanceof Modulo)
            Code.put(Code.rem);
       
    }


    public void visit(AddExpression addExpression) {
        SyntaxNode operation = addExpression.getAddop();
        if (operation instanceof Plus)
            Code.put(Code.add);
        if (operation instanceof Minus)
            Code.put(Code.sub);
    }



    public void visit(NegativeStartExpr negativeStartExpr) {
        Code.put(Code.neg);
    }

    public void visit(MethodDecl methorDecl) {
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    public void visit(MethodName methodName) {
        if ("main".equals(methodName.getName())) {
        	mainPc = Code.pc;
        }
        int formalParamCnt = methodName.obj.getLevel();
        int localCnt = methodName.obj.getLocalSymbols().size();

        methodName.obj.setAdr(Code.pc);
        
        Code.put(Code.enter);
        Code.put(formalParamCnt);
        Code.put(localCnt);
    }

    


    
    
    
   public void visit(NumberConst numberConst) {
        Obj con = Tab.insert(Obj.Con, "$", numberConst.struct);
        con.setAdr(numberConst.getN1());
        Code.load(con);
    }
    
    public void visit(CharacterConst charConst) {
        Obj con = Tab.insert(Obj.Con, "$", charConst.struct);
        con.setAdr(charConst.getC1());
        //con.setLevel(0);
        Code.load(con);
    }
    
    public void visit(BooleanConst boolConst) {
        Obj con = Tab.insert(Obj.Con, "$", boolConst.struct);
        con.setAdr(boolConst.getB1().equals("true") ? 1 : 0);
        Code.load(con);
    }
    
    public void visit(ConstDef constDef) {
        String name = constDef.getConstName();
        String fullName = !Objects.equals(currNamespace, "") ? currNamespace + "$" + name : name;
        constDef.obj = Tab2.findSymbol2(fullName);
        SyntaxNode constNode = constDef.getConst();
        int value = -1;

        if (constNode instanceof NumConst) {
            value = ((NumConst) constNode).getN1();
        } else if (constNode instanceof CharConst) {
            value = ((CharConst) constNode).getC1();
        } else if (constNode instanceof BoolConst) {
            value = ((BoolConst) constNode).getB1().equals("true") ? 1 : 0;
        }

        constDef.obj.setAdr(value);
        Code.load(constDef.obj);
    }
    
    
    
    public void visit(DesignatorExpr designatorExpr) {
        Obj designatorObj = designatorExpr.getDesignator().obj;
        /*if(designatorObj.getKind() == Obj.Elem && designatorExpr.getDesignator().getDesignatorName().obj.getType().getKind() == 8
        		&& ) {
        	Code.put(Code.astore);
        }*/
        if (designatorObj.getKind() == Obj.Elem) {
            Code.put(designatorObj.getType() == Tab.charType ? Code.bastore : Code.astore);
        } else {
            Code.store(designatorObj);
        }
    }
    
    
    
    public void visit(WithoutNamespace withoutNamespace) {
    	Obj obj = Tab.currentScope().findSymbol(withoutNamespace.obj.getName());
    	SyntaxNode parent = withoutNamespace.getParent().getParent().getParent().getParent().getParent();
        if (obj != null && obj.getKind() == Obj.Meth) {
            Code.loadConst(0);
            return;
        }
        if ((withoutNamespace.obj.getType().getKind() == Struct.Array || withoutNamespace.obj.getType().getKind() == 8)
        		&& ((Designator) withoutNamespace.getParent()).getOptBraExpr() instanceof NoArrayExpression
        		&& !(withoutNamespace.getParent().getParent().getParent().getParent().getParent() instanceof PrintExpr)
        		&& !(withoutNamespace.getParent().getParent() instanceof MaxMatrix)
        		) {		// NE STAVLJA NA STACK AKO JE NIZ BEZ []  niz = new int[3];
        	int a = 5;
        	return;
        }
        
        int a = 2;
        if((withoutNamespace.obj.getKind() == Obj.Var ) &&
        		withoutNamespace.obj.getType().getKind() != Struct.Array &&
        		withoutNamespace.obj.getType().getKind() != 8 &&
        		withoutNamespace.getParent().getParent() instanceof DesignatorExpr
        		)	{ // NE STAVLJA NA STACK AKO JE NE-NIZ		a = 1;
        	return;
        }
        int v = 5;   
        Code.load(withoutNamespace.obj);

    }

    
    public void visit(WithNamespace withNamespace) {
        if (withNamespace.obj.getKind() == Obj.Meth ||
        	(withNamespace.obj.getType().getKind() == 3 && ((Designator) withNamespace.getParent()).getOptBraExpr() instanceof NoArrayExpression))
            return;
        Code.load(withNamespace.obj);
    }
    
    
    public void visit(MaxMatrix maxMatrix) {
    	Code.put(Code.dup);			// 2 2
    	Code.put(Code.const_n);		// 2 2 0
    	Code.put(Code.aload);		// 2 5
    	Code.put(Code.const_n);		// 2 5 0
    	Code.put(Code.aload);		// 2 14
    	Code.put(Code.dup_x1);		// 14 2 14
    	Code.put(Code.pop);			// 14 2
    	 
    	Code.put(Code.const_n);	// 14 2 0
		
		Code.put(Code.const_n);		// 14 2 0 0
		int loopStart = Code.pc;	
		Code.put(Code.pop);  		// 14 2 0
		
		Code.put(Code.dup_x1);		// 14 0 2 0
		Code.put(Code.pop);			// 14 0 2
		
		Code.put(Code.dup_x2);		// 2 14 0 2
		Code.put(Code.dup_x1);		// 2 14 2 0 2
		Code.put(Code.pop);			// 2 14 2 0
		Code.put(Code.dup_x2);		// 2 0 14 2 0
		Code.put(Code.aload);		// 2 0 14 5
		Code.put(Code.const_n);		// 2 0 14 5 0
		
		Code.put(Code.dup2);		// 2 0 14 5 0 5 0
		Code.put(Code.aload);		// 2 0 14 5 0 14
		
		Code.put(Code.dup_x2);
		Code.put(Code.pop);			// 2 0 14 14 5 0
		
		Code.put(Code.const_n);		// 2 0 14 14 5 0 0
		int loopStart1 = Code.pc;	// 
		Code.put(Code.pop);  		// 2 0 14 14 5 0
		
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		
		Code.put(Code.dup_x2);		// 2 0 14 5 14 0 5
		Code.put(Code.dup_x1);		// 2 0 14 5 14 5 0 5
		Code.put(Code.pop);			// 2 0 14 5 14 5 0
		Code.put(Code.dup_x2);		// 2 0 14 5 0 14 5 0
		
		Code.put(Code.aload);		// 2 0 14 5 0 14 14
		
		Code.put(Code.dup2);
		
		Code.put(Code.jcc + Code.gt);
		Code.put2(5);
		
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		
		Code.put(Code.pop);			// 2 0 14 5 0 14 
		
		Code.put(Code.dup_x2);
		Code.put(Code.pop);			// 2 0 14 14 5 0
		
		
		Code.put(Code.const_1);		
		Code.put(Code.add);			// 2 0 14 14 5 1
		Code.put(Code.dup2);
		Code.put(Code.pop);			// 2 0 14 14 5 1 5
		Code.put(Code.arraylength);	// 2 0 14 14 5 1 2
		Code.put(Code.dup2);		// 2 0 14 14 5 1 2 1 2
		Code.put(Code.jcc+Code.ne);
		int loopEnd1 = Code.pc;
		Code.put2(loopStart1 - loopEnd1 + 1);
		Code.put(Code.pop);			// 2 0 14 14 5 2 2 2 2
		Code.put(Code.pop);			// 2 0 14
		Code.put(Code.pop);

		Code.put(Code.dup2);
		Code.put(Code.jcc + Code.gt);
		Code.put2(5);
		
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		
		Code.put(Code.pop);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);

		

		Code.put(Code.const_1);
		Code.put(Code.add);			// 2 1
		Code.put(Code.dup2);
		Code.put(Code.pop);			// 2 1 2
		Code.put(Code.arraylength);	// 2 1 7
		Code.put(Code.dup2);		// 2 1 7 1 7
		Code.put(Code.jcc+Code.ne);
		int loopEnd = Code.pc;
		Code.put2(loopStart - loopEnd + 1);
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.put(Code.pop);
    	
    }
    
    /*public void visit(MaxMatrix maxMatrix) {
    	Code.put(Code.dup);			// 2 2 
    	Code.put(Code.const_n);		// 2 2 0
    	Code.put(Code.aload);		// 2 9
    	Code.put(Code.const_n);		// 2 9 0
    	Code.put(Code.aload);		// 2 13
    	Code.put(Code.dup_x1);		// 13 2 13
    	Code.put(Code.pop);			// 13 2
    	
    	Code.put(Code.const_n);
		
		Code.put(Code.const_n);		// 13 2 0 0
		int loopStart = Code.pc;	// 13 2 0 0
		Code.put(Code.pop);  		// 13 2 0
					
		Code.put(Code.dup_x1);		// e0 0 2 0
		Code.put(Code.pop);			// e0 0 2 
		
		Code.put(Code.dup_x2);		// 2 e0 0 2
		Code.put(Code.dup_x1);		// 2 e0 2 0 2
		Code.put(Code.pop);			// 2 e0 2 0
		Code.put(Code.dup_x2);		// 2 0 e0 2 0
		
		//Code.put(Code.dup2);		// 2 0 e0 2 0
		Code.put(Code.aload);		// 2 0 13 9
		Code.put(Code.const_n);		// 2 0 13 9 0
		Code.put(Code.dup2);		// 2 0 13 9 0 9 0
		Code.put(Code.aload);		// 2 0 13 9 0 13
		Code.put(Code.dup_x2);		// 2 0 13 13 9 0 13
		Code.put(Code.pop);			// 2 0 13 13 9 0 

		
		Code.put(Code.const_n);		// 2 0 e0 e0 6 0 0
		int loopStart1 = Code.pc;	// 
		Code.put(Code.pop);  		// 2 0 e0 e0 6 0
		
		Code.put(Code.dup_x1);
		Code.put(Code.pop);			// 2 0 e0 e0 0 6
		
		Code.put(Code.dup_x2);		// 2 0 e0 6 e0 0 6
		Code.put(Code.dup_x1);		// 2 0 e0 6 e0 6 0 6
		Code.put(Code.pop);			// 2 0 e0 6 e0 6 0
		Code.put(Code.dup_x2);		// 2 0 e0 6 0 e0 6 0
		
		Code.put(Code.aload);		// 2 0 e0 6 0 e0 e0
		Code.put(Code.dup2);
		
		Code.put(Code.jcc + Code.gt);
    	Code.put2(5);				// jump over swap
    	
    	Code.put(Code.dup_x1);
    	Code.put(Code.pop);			// swap
    	
    	Code.put(Code.pop);			// 2 0 e0 6 0 e0
    	
    	Code.put(Code.dup_x2);		// 2 0 e0 e0 6 0 e0
    	Code.put(Code.pop);			// 2 0 e0 e0 6 0

		
		Code.put(Code.const_1);		// inc
		Code.put(Code.add);			// 2 0 e0 e0 6 1 
		Code.put(Code.dup2);		// 2 0 e0 e0 6 1 6 1
		Code.put(Code.pop);			// 2 0 e0 e0 6 1 6  
		Code.put(Code.arraylength);	// 2 0 e0 e0 6 1 7 
		Code.put(Code.dup2);		// 2 0 e0 e0 6 1 7  1 7 
		Code.put(Code.jcc+Code.ne);
		int loopEnd1 = Code.pc;
		Code.put2(loopStart1 - loopEnd1 + 1);
									// 2 0 e0 e0 6 7 7
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.put(Code.pop);			// 2 0 e0 e0 
									// 2 0 e0 e0 
		Code.put(Code.dup2);
		
		Code.put(Code.jcc + Code.gt);
    	Code.put2(5);				// jump over swap
    	
    	Code.put(Code.dup_x1);
    	Code.put(Code.pop);			// swap
    	
    	Code.put(Code.pop);			// 2 0 e0
    	Code.put(Code.dup_x2);		// e0 2 0 e0
    	Code.put(Code.pop);			// e0 2 0
		

		Code.put(Code.const_1);
		Code.put(Code.add);			// e0 2 1
		Code.put(Code.dup2);
		Code.put(Code.pop);			// e0 2 1 2
		Code.put(Code.arraylength);	// e0 2 1 7
		Code.put(Code.dup2);		// e0 2 1 7 1 7
		Code.put(Code.jcc+Code.ne);
		int loopEnd = Code.pc;
		Code.put2(loopStart - loopEnd + 1);
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.put(Code.pop);
    	
    }*/
    
    
    
    public void visit(PrintExpr printExpr) {
    	if(printExpr.getExpr().struct.getKind() == Struct.Array) {
    		if(printExpr.getExpr().struct.getElemType() == Tab.intType 
    				|| printExpr.getExpr().struct.getElemType() == Tab2.boolType) {

    			
    			Code.put(Code.const_n);
    			
    			Code.put(Code.const_n);		// 2 0 0
    			int loopStart = Code.pc;	// 2 0 0
    			Code.put(Code.pop);  		// 2 0
    			
    			Code.put(Code.dup2);		// 2 0 2 0
    			Code.put(Code.aload);		// 2 0 e0
    			
    			Code.loadConst(5);
    			Code.put(Code.print);		// 2 0 
    			Code.put(Code.const_1);
    			Code.put(Code.add);			// 2 1
    			Code.put(Code.dup2);
    			Code.put(Code.pop);			// 2 1 2
    			Code.put(Code.arraylength);	// 2 1 7
    			Code.put(Code.dup2);		// 2 1 7 1 7
    			Code.put(Code.jcc+Code.ne);
    			int loopEnd = Code.pc;
    			Code.put2(loopStart - loopEnd + 1);
    			Code.put(Code.pop);
    			Code.put(Code.pop);
    			Code.put(Code.pop);
    		
    		}else {
    			//niz char-ova
    			 //2
    			Code.put(Code.const_n);
    			Code.put(Code.const_n);		// 2 0 0
    			int loopStart = Code.pc;	// 2 0 0
    			Code.put(Code.pop);  		// 2 0
    			Code.put(Code.dup2);		// 2 0 2 0
    			Code.put(Code.baload);		// 2 0 e0
    			
    			Code.loadConst(1);
    			Code.put(Code.bprint);		// 2 0 
    			Code.put(Code.const_1);
    			Code.put(Code.add);			// 2 1
    			Code.put(Code.dup2);
    			Code.put(Code.pop);			// 2 1 2
    			Code.put(Code.arraylength);	// 2 1 7
    			Code.put(Code.dup2);		// 2 1 7 1 7
    			Code.put(Code.jcc+Code.ne);
    			int loopEnd = Code.pc;
    			Code.put2(loopStart - loopEnd + 1);
    			Code.put(Code.pop);
    			Code.put(Code.pop);
    			Code.put(Code.pop);
    			
    		}
    	}if(printExpr.getExpr().struct.getKind() == 8) {
    		if(printExpr.getExpr().struct.getElemType().getElemType() == Tab.intType 
    				|| printExpr.getExpr().struct.getElemType().getElemType() == Tab2.boolType) {

    			
    			Code.put(Code.const_n);
    			
    			Code.put(Code.const_n);		// 2 0 0
    			int loopStart = Code.pc;	// 2 0 0
    			Code.put(Code.pop);  		// 2 0
    			
    			Code.put(Code.dup2);		// 2 0 2 0
    			Code.put(Code.aload);		// 2 0 e0
    			
    			Code.put(Code.const_n);
    			
    			Code.put(Code.const_n);		// 2 0 0
    			int loopStart1 = Code.pc;	// 2 0 0
    			Code.put(Code.pop);  		// 2 0
    			
    			Code.put(Code.dup2);		// 2 0 2 0
    			Code.put(Code.aload);		// 2 0 e0
    			
    			Code.loadConst(5);
    			Code.put(Code.print);		// 2 0 
    			
    			
    			Code.put(Code.const_1);
    			Code.put(Code.add);			// 2 1
    			Code.put(Code.dup2);
    			Code.put(Code.pop);			// 2 1 2
    			Code.put(Code.arraylength);	// 2 1 7
    			Code.put(Code.dup2);		// 2 1 7 1 7
    			Code.put(Code.jcc+Code.ne);
    			int loopEnd1 = Code.pc;
    			Code.put2(loopStart1 - loopEnd1 + 1);
    			Code.put(Code.pop);
    			Code.put(Code.pop);
    			Code.put(Code.pop);

    			Code.loadConst(10);  // Load ASCII code for newline (EOL) onto the stack
    			Code.loadConst(5);
    			Code.put(Code.bprint);  // Print the newline

    			

    			Code.put(Code.const_1);
    			Code.put(Code.add);			// 2 1
    			Code.put(Code.dup2);
    			Code.put(Code.pop);			// 2 1 2
    			Code.put(Code.arraylength);	// 2 1 7
    			Code.put(Code.dup2);		// 2 1 7 1 7
    			Code.put(Code.jcc+Code.ne);
    			int loopEnd = Code.pc;
    			Code.put2(loopStart - loopEnd + 1);
    			Code.put(Code.pop);
    			Code.put(Code.pop);
    			Code.put(Code.pop);
    		
    		}
    	}
    	else if (printExpr.getExpr().struct == Tab.intType || printExpr.getExpr().struct == Tab2.boolType) {
            Code.loadConst(5);
            Code.put(Code.print);
        } else {
            Code.loadConst(1);
            Code.put(Code.bprint);
        }
    }
    
    public void visit(ReadDesignator rd) {
    	if(rd.getDesignator().obj.getType().getKind() == Struct.Array) {
    		if(rd.getDesignator().obj.getType().getElemType() == Tab.intType || rd.getDesignator().obj.getType().getElemType() == Tab2.boolType) {
    			Obj o = Tab.insert(Obj.Var, "arraySize", new Struct(Struct.Int));
    			Code.put(Code.const_n);
    			
    			Code.put(Code.const_n);		// 2 0 0
    			int loopStart = Code.pc;	// 2 0 0
    			Code.put(Code.pop);  		// 2 0
    			
    			Code.put(Code.dup2);		// 2 0 2 0
    			Code.put(Code.read);
                Code.store(o); //// 2 0 2 0 e0
                Code.load(o);
    			Code.put(Code.astore);		// 2 0 
    			
    			Code.put(Code.const_1);
    			Code.put(Code.add);			// 2 1
    			Code.put(Code.dup2);
    			Code.put(Code.pop);			// 2 1 2
    			Code.put(Code.arraylength);	// 2 1 7
    			Code.put(Code.dup2);		// 2 1 7 1 7
    			Code.put(Code.jcc+Code.ne);
    			int loopEnd = Code.pc;
    			Code.put2(loopStart - loopEnd + 1);
    			Code.put(Code.pop);
    			Code.put(Code.pop);
    			Code.put(Code.pop);
    		
    		}else {
    			//niz char-ova
    			 //2
    			Code.put(Code.const_n);
    			
    			Code.put(Code.const_n);		// 2 0 0
    			int loopStart = Code.pc;	// 2 0 0
    			Code.put(Code.pop);  		// 2 0
    			
    			Code.put(Code.dup2);		// 2 0 2 0
    			Code.put(Code.read);
                Code.store(rd.getDesignator().obj); //// 2 0 2 0 e0
    			Code.put(Code.bastore);		// 2 0 
    			
    			Code.put(Code.const_1);
    			Code.put(Code.add);			// 2 1
    			Code.put(Code.dup2);
    			Code.put(Code.pop);			// 2 1 2
    			Code.put(Code.arraylength);	// 2 1 7
    			Code.put(Code.dup2);		// 2 1 7 1 7
    			Code.put(Code.jcc+Code.ne);
    			int loopEnd = Code.pc;
    			Code.put2(loopStart - loopEnd + 1);
    			Code.put(Code.pop);
    			Code.put(Code.pop);
    			Code.put(Code.pop);
    			
    		}
    	}
    	else if (rd.getDesignator().obj.getType() == Tab.intType || rd.getDesignator().obj.getType() == Tab2.boolType) {
    		if(rd.getDesignator().obj.getKind()!= Obj.Elem)
    			Code.put(Code.pop);
    		Code.put(Code.read);
            Code.store(rd.getDesignator().obj);
        } else {
        	if(rd.getDesignator().obj.getKind()!= Obj.Elem)
    			Code.put(Code.pop);
        	Code.put(Code.bread);
            Code.store(rd.getDesignator().obj);
        }
    }
    
    /*public void visit(ReadDesignator readDesignator) {
        Code.put(Code.read);
        Code.store(readDesignator.getDesignator().obj);
    }*/
    
    public void visit(DesignatorPP designatorPP) {
        Code.put(Code.const_1);
        Code.put(Code.add);
        Code.store(designatorPP.getDesignator().obj);
    }


    public void visit(DesignatorMM designatorMM) {
        Code.put(Code.const_1);
        Code.put(Code.sub);
        Code.store(designatorMM.getDesignator().obj);
    }

    

    public void visit(NewType newType) {
        Code.put(Code.newarray);
        if (newType.getType().struct == Tab.charType)
            Code.put(0);
        else
            Code.put(1);
    }
    
    public void visit(NewTypeMat newTypeMat) {
    	Code.put(Code.dup2);		// 7 8 7 8
    	Code.put(Code.pop);			// 7 8 7
    	Code.put(Code.newarray);	
    	Code.put(1);				// 7 8 2
    	Code.put(Code.dup_x2);		// 2 7 8 2
    	Code.put(Code.pop);			// 2 7 8 
    	Code.put(Code.dup_x1);		// 2 8 7 8
    	Code.put(Code.pop);
    	Code.put(Code.pop);			// 2 8 
    	Code.put(Code.dup2);		// 2 8 2 8
    	Code.put(Code.pop);			// 2 8 2
    	Code.put(Code.dup_x1);		// 2 2 8 2
    	Code.put(Code.pop);			// 2 2 8
    	Code.put(Code.newarray);	 
    	if (newTypeMat.getType().struct == Tab.charType)
            Code.put(0);
        else
            Code.put(1);			// 2 2 11 			NAPRAVLJEN NULTI RED
    	Code.put(Code.const_n);		// 2 2 11 0
    	Code.put(Code.dup_x2);		// 2 0 2 11 0
    	Code.put(Code.dup_x1);		// 2 0 2 0 11 0
    	Code.put(Code.pop);			// 2 0 2 0 11		
    	Code.put(Code.astore);		// 2 0 				SACUVAN NULTI RED
    	
    	Code.put(Code.const_1);		// 2 0 1
    	Code.put(Code.add);			// 2 1
    	Code.put(Code.dup);						// zbog pocetka petlje
    	int loopStart = Code.pc;
    	Code.put(Code.pop);
    	Code.put(Code.dup2);		// 2 1 2 1
    	Code.put(Code.dup2);		// 2 1 2 1 2 1
    	Code.put(Code.pop);			// 2 1 2 1 2
    	Code.put(Code.const_n);		// 2 1 2 1 2 0
    	Code.put(Code.aload);		// 2 1 2 1 11
    	Code.put(Code.arraylength);	// 2 1 2 1 8
    	Code.put(Code.newarray);	
    	if (newTypeMat.getType().struct == Tab.charType)
            Code.put(0);
        else
            Code.put(1);			// 2 1 2 1 19
    	Code.put(Code.astore);		// 2 1
    	Code.put(Code.dup2);		// 2 1 2 1
    	Code.put(Code.pop);			// 2 1 2 
    	Code.put(Code.arraylength);	// 2 1 7
    	Code.put(Code.dup2);		// 2 1 7 1 7
    	Code.put(Code.pop);			// 2 1 7 1 
    	Code.put(Code.const_1);		// 2 1 7 1 1 
    	Code.put(Code.add);			// 2 1 7 2
    	Code.put(Code.dup_x2);		// 2 2 1 7 2
    	Code.put(Code.jcc+Code.ne);	// 2 2 1 
		int loopEnd = Code.pc;
		Code.put2(loopStart - loopEnd + 1);
		Code.put(Code.pop);
		Code.put(Code.pop);
    	
    	
    }
    
    public void visit(RangeExpression rangeExpression) {
        // Duplicate the array size on top of the stack
        Code.put(Code.dup);

        // Create a new array of integers (int array type is represented by 1)
        Code.put(Code.newarray);
        Code.put(1);

        // Duplicate the array reference and array size on the stack
        Code.put(Code.dup2);
        Code.put(Code.pop);

        // Decrement the size to start from the last index
        Code.put(Code.const_1);
        Code.put(Code.sub);

        // Loop start label
        int loopStart = Code.pc;

        // Store the index value into the array
        Code.put(Code.dup2);
        Code.put(Code.dup);
        Code.put(Code.astore);

        // Decrement the index
        Code.put(Code.const_1);
        Code.put(Code.sub);

        // Check if the index is -1 to exit the loop
        Code.put(Code.dup);
        Code.put(Code.const_m1);
        Code.put(Code.jcc+Code.ne); 
        int jumpAddr = Code.pc;
        Code.put2(loopStart-jumpAddr+1);


        Code.put(Code.pop);
        Code.put(Code.dup_x1);
        Code.put(Code.pop);
        Code.put(Code.pop);
    }

    /*public void visit(SingleTerm singleTerm) {
    	Factor f = singleTerm.getFactor();
    	if(f instanceof RangeExpression) {
    		Code.put(Code.pop);
    	}	
    }*/
    
    


    
    public void visit(AddArrayExpression addArrayExpression) {
        Obj obj = addArrayExpression.getParent() instanceof Designator ?
                ((Designator) addArrayExpression.getParent()).obj :
                ((AddArrayExpression) addArrayExpression.getParent()).obj;
    }
    
    public void visit(BraExpr braExpr) {
        /*if (braExpr.getParent().getParent() instanceof Designator) {
            SyntaxNode parent = braExpr.getParent().getParent().getParent();
            if (!(parent instanceof DesignatorExpr)
                    && !(parent instanceof ReadDesignator)
            )
            	Code.put(braExpr.obj.getType() == Tab.charType ? Code.baload : Code.aload);
        } else {
        	Code.put(braExpr.obj.getType() == Tab.charType ? Code.baload : Code.aload);
        }*/
    	
    	
    	if(braExpr.getParent() instanceof AddMatrixExpression && 
    			((AddMatrixExpression)braExpr.getParent()).getBraExpr1() == braExpr &&
    			braExpr.getParent().getParent().getParent() instanceof DesignatorExpr){
    		return;		//UCITAVANJE U ELEMENT MATRICE
    	}
    	if(braExpr.getParent() instanceof AddMatrixExpression && 
    			((AddMatrixExpression)braExpr.getParent()).getBraExpr1() == braExpr &&
    			braExpr.getParent().getParent().getParent() instanceof ReadDesignator){
    		return;		//READ U ELEMENT MATRICE
    	}
    	if(braExpr.getParent() instanceof AddArrayExpression && 
    			braExpr.getParent().getParent().getParent() instanceof DesignatorExpr){
    		return;		//UCITAVANJE U ELEMENT NIZA
    	}
    	if(braExpr.getParent() instanceof AddArrayExpression && 
    			braExpr.getParent().getParent().getParent() instanceof ReadDesignator){
    		return;		//READ U ELEMENT NIZA
    	}
    	if(braExpr.getParent() instanceof AddMatrixExpression && 
    			((AddMatrixExpression)braExpr.getParent()).getBraExpr() == braExpr){
    			Code.put(Code.aload);  // ako je prva zagrada radi se aload nezavisno od tipa promenljive
    			return;
    	}
    	Code.put(braExpr.obj.getType() == Tab.charType ? Code.baload : Code.aload);
    	int t = 0;
    }
    
    

    
  
    
}