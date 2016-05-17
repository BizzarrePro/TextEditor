package team.weird.texteditor.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import team.weird.texteditor.parser.Symbol.RightProduction;

public class FollowSet {
	public HashMap<String, Symbol> symbolMap;
	public static int cnt1 = 0;
	public static int cnt2 = 0;
	public FollowSet(HashMap<String, Symbol> symbolMap){
		this.symbolMap = symbolMap;
	}
	public int cnt = 1;
	public void createFollowSet(){
		Iterator<Entry<String, Symbol>> symIter = symbolMap.entrySet().iterator();
		while(symIter.hasNext()){
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			temp.followSet.add("$");
			if(!temp.hasRecursion)
				disposeOfFollowSet(temp.followSet, temp.getUnterminatingString());
		}
	}
	public void disposeOfFollowSet(Set<String> followSet, String symbol){
		symbolMap.get(symbol).hasRecursion = true;
		Iterator<Entry<String, Symbol>> symIter = symbolMap.entrySet().iterator();
		while(symIter.hasNext()){
			
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			Iterator<RightProduction> proIter = temp.rightList.iterator();
			while(proIter.hasNext()){
				
				LinkedList<String> list = proIter.next().getRightSymbolList();
				Iterator<String> listIter = list.iterator();
				String tempSym ;
				while(listIter.hasNext()){
					tempSym = listIter.next();
					
					if(symbolMap.containsKey(tempSym) && tempSym.equals(symbol)){
						//System.out.println("Left: "+temp.getUnterminatingString()+" Right: "+tempSym + "Symbol: "+symbol);
						if(listIter.hasNext()){
							String followSymbol = listIter.next();
							if(symbolMap.containsKey(followSymbol) && symbolMap.get(followSymbol).hasEpsilon && !temp.hasRecursion){
								System.out.println(symbol+" "+tempSym+" "+followSymbol);
								if(cnt++ > 100)
									System.exit(0);
								Symbol elementSym = symbolMap.get(followSymbol);
								//System.out.println("'"+(cnt2++)+"'");
								disposeOfFollowSet(elementSym.followSet, followSymbol);
								followSet.addAll(elementSym.followSet);
								followSet.addAll(elementSym.firstSet);
								followSet.remove("empty");
							}
							else if(symbolMap.containsKey(followSymbol) && !symbolMap.get(followSymbol).hasEpsilon){
								followSet.addAll(symbolMap.get(followSymbol).firstSet);
							}
							else if(!symbolMap.containsKey(followSymbol))
								followSet.add(followSymbol);
						}
						else if(!temp.getUnterminatingString().equals(tempSym) && !temp.hasRecursion){
							disposeOfFollowSet(temp.followSet, temp.getUnterminatingString());
							followSet.addAll(temp.followSet);
						}
						else
							followSet.addAll(temp.followSet);
					}
				}
			}
		}
		cnt = 1;
	}
	
	public void display(){
		System.out.println();
		System.out.println("-------------------------Follow Set-------------------------");
		System.out.println();
		Iterator<Entry<String, Symbol>> symIter = symbolMap.entrySet().iterator();
		while(symIter.hasNext()){
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			Iterator<String> setIter = temp.followSet.iterator();
			System.out.print(temp.getUnterminatingString()+" Size: "+temp.followSet.size()+" $$ {");
			while(setIter.hasNext()){
				String print = setIter.next();
				System.out.print(print);
				if(setIter.hasNext())
					System.out.print(" ");
			}
			System.out.print("}");
			System.out.println();
		}
	}
}
