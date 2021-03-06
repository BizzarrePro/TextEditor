package team.weird.compiler.cminus.astnode;

import java.io.FileWriter;
import java.io.IOException;

import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.codegen.Operand;
import team.weird.compiler.cminus.codegen.OperandType;
import team.weird.compiler.cminus.codegen.Operation;
import team.weird.compiler.cminus.lexer.Number;
import team.weird.compiler.cminus.semantic.ErrorList;
import team.weird.compiler.cminus.semantic.Semantic;
import team.weird.compiler.cminus.semantic.SemanticException;
import team.weird.compiler.cminus.semantic.Type;


public class AssignExpression extends Expression{
	private Expression lex;
	private Expression rex;
	public AssignExpression(Expression lex, Expression rex){
		this.lex = lex;
		this.rex = rex;
	}
	public AssignExpression(){
		
	}
	public Expression getLex() {
		return lex;
	}
	public void setLex(Expression lex) {
		this.lex = lex;
	}
	public Expression getRex() {
		return rex;
	}
	public void setRex(Expression rex) {
		this.rex = rex;
	}
	@Override
	public void print(String tab, FileWriter fw) {
		// TODO Auto-generated method stub
		try{
			System.out.println(tab+"AssignExpression: ");
			fw.write(tab+"AssignExpression: \r\n");
			lex.print(tab+"\t", fw);
			System.out.println("\n" + tab +  "\t=");
			fw.write("\n" + tab +  "\t=\r\n");
			rex.print(tab + "\t", fw);
			System.out.println();
			fw.write("\r\n");
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	@Override
	public Type generateIntermediateCode(Function fun) {
		// TODO Auto-generated method stub
		ErrorList err = ErrorList.getInstance();
		Type leftType = lex.generateIntermediateCode(fun);
		Type rightType = rex.generateIntermediateCode(fun);
		if(lex instanceof CallExpression)
			err.addException(new SemanticException(lex.getId(), lex.getLine(), 18));
		if(leftType != rightType)
			err.addException(new SemanticException(leftType, rightType, lex.getLine()));
		Operation op = new Operation(OperandType.MOV, fun.getCurrBlock());
		fun.getCurrBlock().appendOperation(op);
		Operand dest = new Operand(OperandType.REG, lex.getRegNum());
		op.setDestOperand(0, dest);
		Operand src = null;
		if(rex.getClass() != LiteralExpression.class)
			src = new Operand(OperandType.REG, rex.getRegNum());
		else{
			Object obj = ((LiteralExpression)rex).getNumber();
			if(obj instanceof Integer)
				src = new Operand(OperandType.INT, (int)obj);
			else
				src = new Operand(OperandType.FLOAT, (double)obj);

		}
		op.setSrcOperand(0, src);	
		if(Semantic.globalSymbolTable.containsKey(lex.getId())) {
			Operation st = new Operation(OperandType.STORE, fun.getCurrBlock());
			fun.getCurrBlock().appendOperation(st);
			
			st.setSrcOperand(0, new Operand(OperandType.REG, lex.getRegNum()));
			st.setSrcOperand(1, new Operand(OperandType.VAR_NAME, lex.getId()));
		}
		return leftType;
	}
	
}
