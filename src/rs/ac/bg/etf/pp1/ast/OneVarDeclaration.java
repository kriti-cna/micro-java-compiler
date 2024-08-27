// generated with ast extension for cup
// version 0.8
// 26/7/2024 18:58:10


package rs.ac.bg.etf.pp1.ast;

public class OneVarDeclaration extends VarDeclarations {

    private AddVarDecl AddVarDecl;

    public OneVarDeclaration (AddVarDecl AddVarDecl) {
        this.AddVarDecl=AddVarDecl;
        if(AddVarDecl!=null) AddVarDecl.setParent(this);
    }

    public AddVarDecl getAddVarDecl() {
        return AddVarDecl;
    }

    public void setAddVarDecl(AddVarDecl AddVarDecl) {
        this.AddVarDecl=AddVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AddVarDecl!=null) AddVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AddVarDecl!=null) AddVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AddVarDecl!=null) AddVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OneVarDeclaration(\n");

        if(AddVarDecl!=null)
            buffer.append(AddVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OneVarDeclaration]");
        return buffer.toString();
    }
}
