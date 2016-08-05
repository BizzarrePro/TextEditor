package team.weird.compiler.cminus.astnode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import team.weird.compiler.cminus.codegen.BasicBlock;
import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.codegen.Instruction;
import team.weird.compiler.cminus.codegen.IntermediateCodeGen;
import team.weird.compiler.cminus.codegen.Parameter;
import team.weird.compiler.cminus.semantic.ErrorList;
import team.weird.compiler.cminus.semantic.Semantic;
import team.weird.compiler.cminus.semantic.SemanticException;
import team.weird.compiler.cminus.semantic.Type;

public class FunctionDeclaration extends Declaration implements PrintASTree, IntermediateCodeGen{
	private List<Variable> parameters = new ArrayList<Variable>();
	private CompoundStatement statement;
	public FunctionDeclaration(String id, Type type, int line){
		super(id, type, line);
	}
	public List<Variable> getParameters() {
		return parameters;
	}
	public void setParameters(List<Variable> parameters) {
		this.parameters = parameters;
	}
	public CompoundStatement getStatement() {
		return statement;
	}
	public void setStatement(CompoundStatement statement) {
		this.statement = statement;
	}
	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println("FunctionDeclaration: " + getType() + " " + getId() + " ( ");
		for(Variable v : parameters) {
			v.print(tab);
		}
		System.out.println(")");
		statement.print(tab);
		System.out.println();
	}
	@Override
	public Instruction generateIntermediateCode() {
		// TODO Auto-generated method stub
		Function fun = new Function(type, id);
		fun.createBlock();
		Parameter param = null;
		for(Variable temp : parameters){
			if(temp == null){
				param = new Parameter(temp.getType(), temp.getId(), temp.isArray());
				fun.setFirstParam(param);
			} 
			else {
				Parameter inter = new Parameter(temp.getType(), temp.getId(), temp.isArray());
				param.setNextParam(inter);
				param = inter;
			}
		}
		BasicBlock basic = new BasicBlock(fun);
		BasicBlock ret = fun.constructRetBlock();
		fun.setCurrBlock(basic);
		statement.generateIntermediateCode(fun);
		fun.appendBlock(basic);
		fun.appendBlock(ret);
		return fun;
	}
	@Override
	public void declare() {
		// TODO Auto-generated method stub
		ErrorList err = ErrorList.getInstance();
		Parameter param = null;
		Variable temp = null;
		Set<String> paramsPool = new HashSet<String>();
		Iterator<Variable> iter = parameters.iterator();
		while(iter.hasNext()){
			if(param != null){
				temp = iter.next();
				if(!paramsPool.contains(temp.getId()))
					paramsPool.add(temp.getId());
				else
					err.addException(new SemanticException(temp.getId(), getLine(), 2));
			}
			else {
				temp = iter.next();
				if(!paramsPool.contains(temp.getId()))
					paramsPool.add(temp.getId());
				else
					err.addException(new SemanticException(temp.getId(), getLine(), 2));
			}
		}
		Semantic.globalFuntionTable.put(getId(), this);
			
	}

}