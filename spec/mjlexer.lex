package rs.ac.bg.etf.pp1;
import java_cup.runtime.Symbol;
%%
%{
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}
%}
%cup
%line
%column
%xstate COMMENT
%eofval{
	return new_symbol(sym.EOF);
%eofval}
%%
" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }
"program"       { return new_symbol(sym.PROGRAM, yytext()); }
"break"         { return new_symbol(sym.BREAK, yytext()); }
"class"         { return new_symbol(sym.CLASS, yytext()); }
"else"          { return new_symbol(sym.ELSE, yytext()); }
"const"         { return new_symbol(sym.CONST, yytext()); }
"if"            { return new_symbol(sym.IF, yytext()); }
"new"           { return new_symbol(sym.NEW, yytext()); }
"print"         { return new_symbol(sym.PRINT, yytext()); }
"read"          { return new_symbol(sym.READ, yytext()); }
"return"        { return new_symbol(sym.RETURN, yytext()); }
"void"          { return new_symbol(sym.VOID, yytext()); }
"extends"       { return new_symbol(sym.EXTENDS, yytext()); }
"continue"      { return new_symbol(sym.CONTINUE, yytext()); }
"for"           { return new_symbol(sym.FOR, yytext()); }
"static"        { return new_symbol(sym.STATIC, yytext()); }
"namespace"     { return new_symbol(sym.NAMESPACE, yytext()); }
"range"			{return new_symbol(sym.RANGE, yytext());}
"max"			{return new_symbol(sym.MAX, yytext());}
"+"     { return new_symbol(sym.PLUS, yytext()); }
"-"     { return new_symbol(sym.MINUS, yytext()); }
"*"     { return new_symbol(sym.MULTIPLY, yytext()); }
"/"     { return new_symbol(sym.DIVIDE, yytext()); }
"%"     { return new_symbol(sym.MODULO, yytext()); }
"=="    { return new_symbol(sym.EQUALS, yytext()); }
"!="    { return new_symbol(sym.NOT_EQUALS, yytext()); }
">"     { return new_symbol(sym.GREATER_THAN, yytext()); }
">="    { return new_symbol(sym.GREATER_EQUAL, yytext()); }
"<"     { return new_symbol(sym.LESS_THAN, yytext()); }
"<="    { return new_symbol(sym.LESS_EQUAL, yytext()); }
"&&"    { return new_symbol(sym.AND, yytext()); }
"||"    { return new_symbol(sym.OR, yytext()); }
"="     { return new_symbol(sym.ASSIGN, yytext()); }
"++"    { return new_symbol(sym.INCREMENT, yytext()); }
"--"    { return new_symbol(sym.DECREMENT, yytext()); }
";"     { return new_symbol(sym.SEMICOLON, yytext()); }
":"     { return new_symbol(sym.COLON, yytext()); }
","     { return new_symbol(sym.COMMA, yytext()); }
"."     { return new_symbol(sym.DOT, yytext()); }
"("     { return new_symbol(sym.LPAREN, yytext()); }
")"     { return new_symbol(sym.RPAREN, yytext()); }
"["     { return new_symbol(sym.LBRACKET, yytext()); }
"]"     { return new_symbol(sym.RBRACKET, yytext()); }
"{"     { return new_symbol(sym.LBRACE, yytext()); }
"}"     { return new_symbol(sym.RBRACE, yytext()); }
"=>"    { return new_symbol(sym.ARROW, yytext()); }


<YYINITIAL> "//" 		     { yybegin(COMMENT); }
<COMMENT> .      { yybegin(COMMENT); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

"true"|"false" { return new_symbol(sym.BOOL_CONST, yytext()); }
'.' { return new_symbol(sym.CHAR_CONST, yytext().charAt(1)); }

[0-9]+  { return new_symbol(sym.NUMBER, Integer.parseInt(yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }


. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)); }
