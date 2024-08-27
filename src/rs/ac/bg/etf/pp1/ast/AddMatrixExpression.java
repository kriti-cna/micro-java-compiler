// generated with ast extension for cup
// version 0.8
// 26/7/2024 18:58:10


package rs.ac.bg.etf.pp1.ast;

public class AddMatrixExpression extends OptBraExpr {

    private BraExpr BraExpr;
    private BraExpr BraExpr1;

    public AddMatrixExpression (BraExpr BraExpr, BraExpr BraExpr1) {
        this.BraExpr=BraExpr;
        if(BraExpr!=null) BraExpr.setParent(this);
        this.BraExpr1=BraExpr1;
        if(BraExpr1!=null) BraExpr1.setParent(this);
    }

    public BraExpr getBraExpr() {
        return BraExpr;
    }

    public void setBraExpr(BraExpr BraExpr) {
        this.BraExpr=BraExpr;
    }

    public BraExpr getBraExpr1() {
        return BraExpr1;
    }

    public void setBraExpr1(BraExpr BraExpr1) {
        this.BraExpr1=BraExpr1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(BraExpr!=null) BraExpr.accept(visitor);
        if(BraExpr1!=null) BraExpr1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(BraExpr!=null) BraExpr.traverseTopDown(visitor);
        if(BraExpr1!=null) BraExpr1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(BraExpr!=null) BraExpr.traverseBottomUp(visitor);
        if(BraExpr1!=null) BraExpr1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AddMatrixExpression(\n");

        if(BraExpr!=null)
            buffer.append(BraExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(BraExpr1!=null)
            buffer.append(BraExpr1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddMatrixExpression]");
        return buffer.toString();
    }
}
