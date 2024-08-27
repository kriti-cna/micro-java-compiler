// generated with ast extension for cup
// version 0.8
// 26/7/2024 18:58:10


package rs.ac.bg.etf.pp1.ast;

public class AddArrayExpression extends OptBraExpr {

    private BraExpr BraExpr;

    public AddArrayExpression (BraExpr BraExpr) {
        this.BraExpr=BraExpr;
        if(BraExpr!=null) BraExpr.setParent(this);
    }

    public BraExpr getBraExpr() {
        return BraExpr;
    }

    public void setBraExpr(BraExpr BraExpr) {
        this.BraExpr=BraExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(BraExpr!=null) BraExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(BraExpr!=null) BraExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(BraExpr!=null) BraExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AddArrayExpression(\n");

        if(BraExpr!=null)
            buffer.append(BraExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddArrayExpression]");
        return buffer.toString();
    }
}
