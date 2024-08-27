// generated with ast extension for cup
// version 0.8
// 26/7/2024 18:58:10


package rs.ac.bg.etf.pp1.ast;

public class YesFormPars extends FormParsOpt {

    private AddFormPars AddFormPars;

    public YesFormPars (AddFormPars AddFormPars) {
        this.AddFormPars=AddFormPars;
        if(AddFormPars!=null) AddFormPars.setParent(this);
    }

    public AddFormPars getAddFormPars() {
        return AddFormPars;
    }

    public void setAddFormPars(AddFormPars AddFormPars) {
        this.AddFormPars=AddFormPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AddFormPars!=null) AddFormPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AddFormPars!=null) AddFormPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AddFormPars!=null) AddFormPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("YesFormPars(\n");

        if(AddFormPars!=null)
            buffer.append(AddFormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [YesFormPars]");
        return buffer.toString();
    }
}
