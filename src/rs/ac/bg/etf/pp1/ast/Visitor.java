// generated with ast extension for cup
// version 0.8
// 26/7/2024 18:58:10


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Mulop Mulop);
    public void visit(FormParsOpt FormParsOpt);
    public void visit(Relop Relop);
    public void visit(AddFactor AddFactor);
    public void visit(Assignop Assignop);
    public void visit(MethodType MethodType);
    public void visit(AddCondTerm AddCondTerm);
    public void visit(OptMinus OptMinus);
    public void visit(StatementList StatementList);
    public void visit(NamespaceList NamespaceList);
    public void visit(Addop Addop);
    public void visit(OptBraExpr OptBraExpr);
    public void visit(Factor Factor);
    public void visit(ConstList ConstList);
    public void visit(Term Term);
    public void visit(DesignatorName DesignatorName);
    public void visit(OptNumConst OptNumConst);
    public void visit(AddActParExpr AddActParExpr);
    public void visit(OptNamespace OptNamespace);
    public void visit(VarDeclList VarDeclList);
    public void visit(AddCondFact AddCondFact);
    public void visit(Expr Expr);
    public void visit(AddFormPars AddFormPars);
    public void visit(VarDeclarationsList VarDeclarationsList);
    public void visit(DeclarationsList DeclarationsList);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(Const Const);
    public void visit(AddVarDecl AddVarDecl);
    public void visit(OptBrackets OptBrackets);
    public void visit(Statement Statement);
    public void visit(NamespaceOpt NamespaceOpt);
    public void visit(VarDecl VarDecl);
    public void visit(AddTerm AddTerm);
    public void visit(ConstDecl ConstDecl);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(Declaration Declaration);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(OptExpr OptExpr);
    public void visit(Modulo Modulo);
    public void visit(Divide Divide);
    public void visit(Multiply Multiply);
    public void visit(Minus Minus);
    public void visit(Plus Plus);
    public void visit(LessEqual LessEqual);
    public void visit(LessThan LessThan);
    public void visit(GreaterEqual GreaterEqual);
    public void visit(GreaterThan GreaterThan);
    public void visit(NotEquals NotEquals);
    public void visit(Equals Equals);
    public void visit(AssignopDerived1 AssignopDerived1);
    public void visit(AssignOperation AssignOperation);
    public void visit(Label Label);
    public void visit(BraExpr BraExpr);
    public void visit(NoArrayExpression NoArrayExpression);
    public void visit(AddMatrixExpression AddMatrixExpression);
    public void visit(AddArrayExpression AddArrayExpression);
    public void visit(WithoutNamespace WithoutNamespace);
    public void visit(WithNamespace WithNamespace);
    public void visit(Designator Designator);
    public void visit(MaxMatrix MaxMatrix);
    public void visit(RangeExpression RangeExpression);
    public void visit(NewTypeMat NewTypeMat);
    public void visit(NewType NewType);
    public void visit(DesignatorFact DesignatorFact);
    public void visit(BooleanConst BooleanConst);
    public void visit(ExpressionFact ExpressionFact);
    public void visit(CharacterConst CharacterConst);
    public void visit(NumberConst NumberConst);
    public void visit(SingleTerm SingleTerm);
    public void visit(MultipleTerms MultipleTerms);
    public void visit(AddExpression AddExpression);
    public void visit(PositiveStartExpr PositiveStartExpr);
    public void visit(NegativeStartExpr NegativeStartExpr);
    public void visit(NoCompare NoCompare);
    public void visit(Compare Compare);
    public void visit(CondFact CondFact);
    public void visit(NoConditionFact NoConditionFact);
    public void visit(AddConditionFact AddConditionFact);
    public void visit(CondTerm CondTerm);
    public void visit(NoConditionTerm NoConditionTerm);
    public void visit(AddConditionTerm AddConditionTerm);
    public void visit(Condition Condition);
    public void visit(NoActParExpression NoActParExpression);
    public void visit(AddActParExpression AddActParExpression);
    public void visit(ActPars ActPars);
    public void visit(DesignatorMM DesignatorMM);
    public void visit(DesignatorPP DesignatorPP);
    public void visit(DesignatorExpr DesignatorExpr);
    public void visit(NoNumConstOpt NoNumConstOpt);
    public void visit(NumConstOpt NumConstOpt);
    public void visit(StatementsBraces StatementsBraces);
    public void visit(ReturnExpression ReturnExpression);
    public void visit(ReturnVoid ReturnVoid);
    public void visit(PrintExpr PrintExpr);
    public void visit(ReadDesignator ReadDesignator);
    public void visit(DesignatorStat DesignatorStat);
    public void visit(StaticInitializer StaticInitializer);
    public void visit(OneFormPar OneFormPar);
    public void visit(AddFormPar AddFormPar);
    public void visit(FormPars FormPars);
    public void visit(NoFormPars NoFormPars);
    public void visit(YesFormPars YesFormPars);
    public void visit(MethodName MethodName);
    public void visit(MethodDecl MethodDecl);
    public void visit(NoVarDeclarations NoVarDeclarations);
    public void visit(ListOfVarDecl ListOfVarDecl);
    public void visit(NoStatements NoStatements);
    public void visit(Statements Statements);
    public void visit(MethodVoid MethodVoid);
    public void visit(MethodWithType MethodWithType);
    public void visit(NoBrackets NoBrackets);
    public void visit(TwoBrackets TwoBrackets);
    public void visit(Brackets Brackets);
    public void visit(AddVarDeclDerived1 AddVarDeclDerived1);
    public void visit(AddVarDecl1 AddVarDecl1);
    public void visit(OneVarDeclaration OneVarDeclaration);
    public void visit(MultipleVarDeclarations MultipleVarDeclarations);
    public void visit(VarDeclaration VarDeclaration);
    public void visit(BoolConst BoolConst);
    public void visit(CharConst CharConst);
    public void visit(NumConst NumConst);
    public void visit(ConstDef ConstDef);
    public void visit(ConDecl ConDecl);
    public void visit(NoConstantDeclaration NoConstantDeclaration);
    public void visit(AddConstantDeclaration AddConstantDeclaration);
    public void visit(Type Type);
    public void visit(NamespaceName NamespaceName);
    public void visit(Namespace Namespace);
    public void visit(NoMethodDeclarations NoMethodDeclarations);
    public void visit(MethodDeclarations MethodDeclarations);
    public void visit(VariableDeclaration VariableDeclaration);
    public void visit(ConstantDeclaration ConstantDeclaration);
    public void visit(NoDeclarations NoDeclarations);
    public void visit(Declarations Declarations);
    public void visit(NoNamespaceDeclarations NoNamespaceDeclarations);
    public void visit(NamespaceDeclarations NamespaceDeclarations);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
