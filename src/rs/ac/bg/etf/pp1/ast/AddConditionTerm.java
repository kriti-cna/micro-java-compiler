// generated with ast extension for cup
// version 0.8
// 26/7/2024 18:58:10


package rs.ac.bg.etf.pp1.ast;

public class AddConditionTerm extends AddCondTerm {

    private AddCondTerm AddCondTerm;
    private CondTerm CondTerm;

    public AddConditionTerm (AddCondTerm AddCondTerm, CondTerm CondTerm) {
        this.AddCondTerm=AddCondTerm;
        if(AddCondTerm!=null) AddCondTerm.setParent(this);
        this.CondTerm=CondTerm;
        if(CondTerm!=null) CondTerm.setParent(this);
    }

    public AddCondTerm getAddCondTerm() {
        return AddCondTerm;
    }

    public void setAddCondTerm(AddCondTerm AddCondTerm) {
        this.AddCondTerm=AddCondTerm;
    }

    public CondTerm getCondTerm() {
        return CondTerm;
    }

    public void setCondTerm(CondTerm CondTerm) {
        this.CondTerm=CondTerm;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AddCondTerm!=null) AddCondTerm.accept(visitor);
        if(CondTerm!=null) CondTerm.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AddCondTerm!=null) AddCondTerm.traverseTopDown(visitor);
        if(CondTerm!=null) CondTerm.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AddCondTerm!=null) AddCondTerm.traverseBottomUp(visitor);
        if(CondTerm!=null) CondTerm.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AddConditionTerm(\n");

        if(AddCondTerm!=null)
            buffer.append(AddCondTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondTerm!=null)
            buffer.append(CondTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddConditionTerm]");
        return buffer.toString();
    }
}
