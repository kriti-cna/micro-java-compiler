// generated with ast extension for cup
// version 0.8
// 26/7/2024 18:58:10


package rs.ac.bg.etf.pp1.ast;

public class AddConditionFact extends AddCondFact {

    private AddCondFact AddCondFact;
    private CondFact CondFact;

    public AddConditionFact (AddCondFact AddCondFact, CondFact CondFact) {
        this.AddCondFact=AddCondFact;
        if(AddCondFact!=null) AddCondFact.setParent(this);
        this.CondFact=CondFact;
        if(CondFact!=null) CondFact.setParent(this);
    }

    public AddCondFact getAddCondFact() {
        return AddCondFact;
    }

    public void setAddCondFact(AddCondFact AddCondFact) {
        this.AddCondFact=AddCondFact;
    }

    public CondFact getCondFact() {
        return CondFact;
    }

    public void setCondFact(CondFact CondFact) {
        this.CondFact=CondFact;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AddCondFact!=null) AddCondFact.accept(visitor);
        if(CondFact!=null) CondFact.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AddCondFact!=null) AddCondFact.traverseTopDown(visitor);
        if(CondFact!=null) CondFact.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AddCondFact!=null) AddCondFact.traverseBottomUp(visitor);
        if(CondFact!=null) CondFact.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AddConditionFact(\n");

        if(AddCondFact!=null)
            buffer.append(AddCondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondFact!=null)
            buffer.append(CondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddConditionFact]");
        return buffer.toString();
    }
}
