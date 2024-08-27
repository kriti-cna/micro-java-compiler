package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;
//Usluzne metode za stampanje tabele

public class DumpVisit extends DumpSymbolTableVisitor {

    protected StringBuilder output = new StringBuilder();
    protected final String indent = "   ";
    protected StringBuilder currentIndent = new StringBuilder();

    protected void nextIndentationLevel() {
        currentIndent.append(indent);
    }

    protected void previousIndentationLevel() {
        if (currentIndent.length() > 0)
            currentIndent.setLength(currentIndent.length() - indent.length());
    }


    @Override
    public void visitObjNode(Obj objToVisit) {
        switch (objToVisit.getKind()) {
            case Obj.Con:
                output.append("Con ");
                break;
            case Obj.Var:
                output.append("Var ");
                break;
            case Obj.Type:
                output.append("Type ");
                break;
            case Obj.Meth:
                output.append("Meth ");
                break;
            case Obj.Fld:
                output.append("Fld ");
                break;
            case Obj.Prog:
                output.append("Prog ");
                break;
        }

        output.append(objToVisit.getName());
        output.append(": ");

        if ((Obj.Var == objToVisit.getKind()) && "this".equalsIgnoreCase(objToVisit.getName()))
            output.append("");
        else
            objToVisit.getType().accept(this);

        output.append(", ");
        output.append(objToVisit.getAdr());
        output.append(", ");
        output.append(objToVisit.getLevel() + " ");

        if (objToVisit.getKind() == Obj.Prog || objToVisit.getKind() == Obj.Meth) {
            output.append("\n");
            nextIndentationLevel();
        }


        for (Obj o : objToVisit.getLocalSymbols()) {
            output.append(currentIndent.toString());
            o.accept(this);
            output.append("\n");
        }

        if (objToVisit.getKind() == Obj.Prog || objToVisit.getKind() == Obj.Meth)
            previousIndentationLevel();


    }


    @Override
    public void visitScopeNode(Scope scope) {
        for (Obj o : scope.values()) {
            o.accept(this);
            output.append("\n");
        }
    }


    @Override
    public void visitStructNode(Struct structToVisit) {
        switch (structToVisit.getKind()) {
            case Struct.None:
                output.append("notype");
                break;
            case Struct.Int:
                output.append("int");
                break;
            case Struct.Char:
                output.append("char");
                break;
            case Struct.Array:
                output.append("Arr of ");
                switch (structToVisit.getElemType().getKind()) {
                    case Struct.None:
                        output.append("notype");
                        break;
                    case Struct.Int:
                        output.append("int");
                        break;
                    case Struct.Char:
                        output.append("char");
                        break;
                    case Struct.Class:
                        output.append("Class");
                        break;
                    /*default:
                    	output.append("boolean");
                        break;*/
                }
                break;
            case 8:
                output.append("Matrix of ");
                    	switch(structToVisit.getElemType().getElemType().getKind()) {
                    	case Struct.None:
                            output.append("notype");
                            break;
                        case Struct.Int:
                            output.append("int");
                            break;
                        case Struct.Char:
                            output.append("char");
                            break;
                        case Struct.Class:
                            output.append("Class");
                            break;
                        case Struct.Bool:
                        	output.append("boolean");
                    	}
                    	break;
            case Struct.Bool:
                output.append("boolean");
                break;
            case Struct.Class:
                output.append("Class [");
                for (Obj obj : structToVisit.getMembers()) {
                    obj.accept(this);
                }
                output.append("]");
                break;
            default:
                output.append("matrix");
        }
    }

    public String getOutput() {
        return output.toString();
    }
    
    public String getSingleObjNodeOutput(Obj objToVisit) {
        StringBuilder tempOutput = new StringBuilder();
        appendSingleObjNodeOutput(objToVisit, tempOutput);
        return tempOutput.toString();
    }

    public String getSingleStructNodeOutput(Struct structToVisit) {
        StringBuilder tempOutput = new StringBuilder();
        appendSingleStructNodeOutput(structToVisit, tempOutput);
        return tempOutput.toString();
    }

    private void appendSingleObjNodeOutput(Obj objToVisit, StringBuilder tempOutput) {
        switch (objToVisit.getKind()) {
            case Obj.Con:
                tempOutput.append("Con ");
                break;
            case Obj.Var:
                tempOutput.append("Var ");
                break;
            case Obj.Type:
                tempOutput.append("Type ");
                break;
            case Obj.Meth:
                tempOutput.append("Meth ");
                break;
            case Obj.Fld:
                tempOutput.append("Fld ");
                break;
            case Obj.Prog:
                tempOutput.append("Prog ");
                break;
            default:
            	output.append("Bool");
                break;
        }

        tempOutput.append(objToVisit.getName());
        tempOutput.append(": ");

        if ((Obj.Var == objToVisit.getKind()) && "this".equalsIgnoreCase(objToVisit.getName()))
            tempOutput.append("");
        else
            tempOutput.append(getSingleStructNodeOutput(objToVisit.getType()));

        tempOutput.append(", ");
        tempOutput.append(objToVisit.getAdr());
        tempOutput.append(", ");
        tempOutput.append(objToVisit.getLevel());
    }

    private void appendSingleStructNodeOutput(Struct structToVisit, StringBuilder tempOutput) {
        switch (structToVisit.getKind()) {
            case Struct.None:
                tempOutput.append("notype");
                break;
            case Struct.Int:
                tempOutput.append("int");
                break;
            case Struct.Char:
                tempOutput.append("char");
                break;
            case Struct.Array:
                tempOutput.append("Arr of ");
                switch (structToVisit.getElemType().getKind()) {
                    case Struct.None:
                        tempOutput.append("notype");
                        break;
                    case Struct.Int:
                        tempOutput.append("int");
                        break;
                    case Struct.Char:
                        tempOutput.append("char");
                        break;
                    case Struct.Class:
                        tempOutput.append("Class");
                        break;
                }
                break;
            case 8:
                tempOutput.append("Matrix of ");
                    	switch(structToVisit.getElemType().getElemType().getKind()) {
                    	case Struct.None:
                    		tempOutput.append("notype");
                            break;
                        case Struct.Int:
                        	tempOutput.append("int");
                            break;
                        case Struct.Char:
                        	tempOutput.append("char");
                            break;
                        case Struct.Class:
                        	tempOutput.append("Class");
                            break;
                        case Struct.Bool:
                        	tempOutput.append("boolean");
                    	}
                    	break;
            case Struct.Bool:
                tempOutput.append("boolean");
                break;
            case Struct.Class:
                tempOutput.append("Class");
                break;
            default:
                tempOutput.append("matrix");
                break;
        }
    }
}