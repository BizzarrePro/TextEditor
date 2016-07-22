package team.weird.texteditor.astnode;

import team.weird.texteditor.semantic.SymbolAttr.Type;

public class ArrayDeclaration extends VariableDeclaration{
	private int length = 0;
	public ArrayDeclaration(String id, Type type, int length){
		super(id, type);
		this.length = length;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
}
