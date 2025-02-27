// generated with ast extension for cup
// version 0.8
// 26/7/2024 18:58:10


package rs.ac.bg.etf.pp1.ast;

public class WithoutNamespace extends DesignatorName {

    private String desName;

    public WithoutNamespace (String desName) {
        this.desName=desName;
    }

    public String getDesName() {
        return desName;
    }

    public void setDesName(String desName) {
        this.desName=desName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("WithoutNamespace(\n");

        buffer.append(" "+tab+desName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [WithoutNamespace]");
        return buffer.toString();
    }
}
