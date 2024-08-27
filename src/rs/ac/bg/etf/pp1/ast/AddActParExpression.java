// generated with ast extension for cup
// version 0.8
// 26/7/2024 18:58:10


package rs.ac.bg.etf.pp1.ast;

public class AddActParExpression extends AddActParExpr {

    private AddActParExpr AddActParExpr;
    private Expr Expr;

    public AddActParExpression (AddActParExpr AddActParExpr, Expr Expr) {
        this.AddActParExpr=AddActParExpr;
        if(AddActParExpr!=null) AddActParExpr.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public AddActParExpr getAddActParExpr() {
        return AddActParExpr;
    }

    public void setAddActParExpr(AddActParExpr AddActParExpr) {
        this.AddActParExpr=AddActParExpr;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AddActParExpr!=null) AddActParExpr.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AddActParExpr!=null) AddActParExpr.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AddActParExpr!=null) AddActParExpr.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AddActParExpression(\n");

        if(AddActParExpr!=null)
            buffer.append(AddActParExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddActParExpression]");
        return buffer.toString();
    }
}
