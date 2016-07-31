package team.weird.texteditor.astnode;

import team.weird.texteditor.codegen.Function;
import team.weird.texteditor.semantic.Type;

public class LiteralExpression extends Expression {
	private Object number = null;
	public LiteralExpression(Object number, int line){
		this.number = number;
		super.setLine(line);
	}
	public Object getNumber() {
		return number;
	}
	public void setNumber(Object number) {
		this.number = number;
	}
	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println(tab + "LiteralExpression: " + number.toString());
	}
	@Override
	public Type generateIntermediateCode(Function fun) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
