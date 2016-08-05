package team.weird.compiler.cminus.astnode;

import team.weird.compiler.cminus.codegen.Function;

public class ExpressionStatement extends Statement{
	private Expression exp = null;
	public ExpressionStatement(Expression exp){
		this.exp = exp;
	}
	public ExpressionStatement(){
		
	}
	public Expression getExp() {
		return exp;
	}
	public void setExp(Expression exp) {
		this.exp = exp;
	}
	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		if(exp != null) {
			System.out.println(tab + "ExpressionStatement: ");
			exp.print(tab + "\t");
			System.out.println();
		}
	}
	@Override
	public void generateIntermediateCode(Function fun) {
		// TODO Auto-generated method stub
		
	}
	
}
