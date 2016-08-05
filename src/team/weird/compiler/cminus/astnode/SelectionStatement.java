package team.weird.compiler.cminus.astnode;

import team.weird.compiler.cminus.codegen.Function;

public class SelectionStatement extends Statement{
	private Expression condition;
	private Statement ifStmt;
	private Statement elseStmt;
	public SelectionStatement(){
		
	}
	public Expression getCondition() {
		return condition;
	}
	public void setCondition(Expression condition) {
		this.condition = condition;
	}
	public Statement getIfStmt() {
		return ifStmt;
	}
	public void setIfStmt(Statement ifStmt) {
		this.ifStmt = ifStmt;
	}
	public Statement getElseStmt() {
		return elseStmt;
	}
	public void setElseStmt(Statement elseStmt) {
		this.elseStmt = elseStmt;
	}
	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println(tab + "SelectionStmt: if (");
		condition.print(tab + "\t");
		System.out.println(tab + " )");
		ifStmt.print(tab + "\t");
		if(elseStmt != null) {
			System.out.println("\n" + tab + "else");
			elseStmt.print(tab + "\t");
		}
	}
	@Override
	public void generateIntermediateCode(Function fun) {
		// TODO Auto-generated method stub
		
	}
	
}