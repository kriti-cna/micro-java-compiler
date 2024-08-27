// generated with ast extension for cup
// version 0.8
// 26/7/2024 18:58:10


package rs.ac.bg.etf.pp1.ast;

public class AddFormPar extends AddFormPars {

    private AddFormPars AddFormPars;
    private FormPars FormPars;

    public AddFormPar (AddFormPars AddFormPars, FormPars FormPars) {
        this.AddFormPars=AddFormPars;
        if(AddFormPars!=null) AddFormPars.setParent(this);
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
    }

    public AddFormPars getAddFormPars() {
        return AddFormPars;
    }

    public void setAddFormPars(AddFormPars AddFormPars) {
        this.AddFormPars=AddFormPars;
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AddFormPars!=null) AddFormPars.accept(visitor);
        if(FormPars!=null) FormPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AddFormPars!=null) AddFormPars.traverseTopDown(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AddFormPars!=null) AddFormPars.traverseBottomUp(visitor);
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AddFormPar(\n");

        if(AddFormPars!=null)
            buffer.append(AddFormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddFormPar]");
        return buffer.toString();
    }
}
