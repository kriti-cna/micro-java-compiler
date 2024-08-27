// generated with ast extension for cup
// version 0.8
// 26/7/2024 18:58:10


package rs.ac.bg.etf.pp1.ast;

public class AddVarDecl1 extends AddVarDecl {

    private String varName;
    private OptBrackets OptBrackets;

    public AddVarDecl1 (String varName, OptBrackets OptBrackets) {
        this.varName=varName;
        this.OptBrackets=OptBrackets;
        if(OptBrackets!=null) OptBrackets.setParent(this);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public OptBrackets getOptBrackets() {
        return OptBrackets;
    }

    public void setOptBrackets(OptBrackets OptBrackets) {
        this.OptBrackets=OptBrackets;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptBrackets!=null) OptBrackets.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptBrackets!=null) OptBrackets.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptBrackets!=null) OptBrackets.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AddVarDecl1(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(OptBrackets!=null)
            buffer.append(OptBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddVarDecl1]");
        return buffer.toString();
    }
}
