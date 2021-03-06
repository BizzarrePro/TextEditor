package team.weird.compiler.cminus.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map.Entry;

import team.weird.compiler.cminus.parser.Symbol.RightProduction;

public class PredictAnalyticalTable {
	public HashMap<String, Symbol> UntermSymbolMap;
	public Set<String> TermSymbolSet;
	public static final int BLANKWIDTH = 9;

	public PredictAnalyticalTable(HashMap<String, Symbol> UntermSymbolMap,
			HashSet<String> TermSymbolSet) throws OverlappedSyntaxException {
		this.UntermSymbolMap = UntermSymbolMap;
		this.TermSymbolSet = TermSymbolSet;
		TermSymbolSet.add("$");
		TermSymbolSet.remove("empty");
		initialPredictAnalyticalTable();
		createPredictAnalyticalTable();
	}

	public void createPredictAnalyticalTable() throws OverlappedSyntaxException {
		Iterator<Entry<String, Symbol>> symIter = UntermSymbolMap.entrySet()
				.iterator();
		while (symIter.hasNext()) {

			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			Iterator<RightProduction> proIter = temp.rightList.iterator();
			while (proIter.hasNext()) {
				RightProduction proTemp = proIter.next();
				String firstRightSymbol = proTemp.getFirstRightSymbol();
				if (TermSymbolSet.contains(firstRightSymbol))
					temp.predictiveMap.put(firstRightSymbol,
							proTemp.getRightSymbolList());
				else if (firstRightSymbol.equals("empty")) {
					Iterator<String> followSymbolIter = temp.followSet
							.iterator();
					while (followSymbolIter.hasNext()) {
						String key = followSymbolIter.next();
						temp.predictiveMap.put(key,
								proTemp.getRightSymbolList());
					}
				} else {
					Iterator<String> firstSymbolIter = UntermSymbolMap
							.get(firstRightSymbol).selectSet.iterator();
					while (firstSymbolIter.hasNext()) {
						String key = firstSymbolIter.next();
						temp.predictiveMap.put(key,
								proTemp.getRightSymbolList());
					}

					if (UntermSymbolMap.get(firstRightSymbol).firstSet
							.contains("empty")) {
						Iterator<String> followSymbolIter = temp.followSet
								.iterator();
						while (followSymbolIter.hasNext()) {
							String key = followSymbolIter.next();
							temp.predictiveMap.put(key,
									proTemp.getRightSymbolList());
						}
					}

				}
			}
		}
	}

	public void initialPredictAnalyticalTable() {
		Iterator<Entry<String, Symbol>> symIter = UntermSymbolMap.entrySet()
				.iterator();
		while (symIter.hasNext()) {
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			Iterator<String> termSet = TermSymbolSet.iterator();
			while (termSet.hasNext()) {
				String key = termSet.next();
				temp.predictiveMap.put(key, null);
			}
		}
	}

	public void print() {
		FileWriter fw = null;
		File fol = new File("./compile/temp.pre");
		System.out.println();
		System.out
				.println("----------------------Predictive Table----------------------");
		System.out.println();
		try{
			fw = new FileWriter(fol);
			fw.write(" --------------------------------------------------\r\n");
			fw.write("|           Construct Predictive Table             |\r\n");
			fw.write(" --------------------------------------------------\r\n");
			Iterator<Entry<String, Symbol>> symIter = UntermSymbolMap.entrySet()
					.iterator();
			Iterator<Entry<String, Symbol>> termIter = UntermSymbolMap.entrySet()
					.iterator();
			Symbol sym = termIter.next().getValue();
			Iterator<String> setIter = sym.predictiveMap.keySet().iterator();
			for (int i = 0; i < 16; i++){
				System.out.print(" ");
				fw.write(" ");
			}
			while (setIter.hasNext()) {
				String termSymbol = setIter.next();
				System.out.print(termSymbol);
				fw.write(termSymbol);
				if (termSymbol.length() < BLANKWIDTH)
					for (int i = 0; i < BLANKWIDTH - termSymbol.length(); i++){
						System.out.print(" ");
						System.out.print(" ");
						fw.write("  ");
					}
			}
			fw.write("\r\n");
			System.out.println();
			while (symIter.hasNext()) {
				Entry<String, Symbol> entry = symIter.next();
				Symbol temp = entry.getValue();
				String blankStr = temp.getUnterminatingString();
				System.out.print(blankStr + ":");
				fw.write(blankStr + ":");
				if (blankStr.length() < 15)
					for (int i = 0; i < 15 - blankStr.length(); i++){
						System.out.print(" ");
						fw.write(" ");
					}
				Iterator<Entry<String, LinkedList<String>>> preIter = temp.predictiveMap
						.entrySet().iterator();
				while (preIter.hasNext()) {
					// Iterator<String> termSetIter = TermSymbolSet.iterator();
					Entry<String, LinkedList<String>> preEntry = preIter.next();
					if (preEntry.getValue() != null) {
						int blankCnt = 0;
						for (String str : preEntry.getValue()) {
							System.out.print(str);
							fw.write(str);
							blankCnt += str.length();
						}
						if (blankCnt < BLANKWIDTH)
							for (int i = 0; i < BLANKWIDTH - blankCnt; i++){
								System.out.print(" ");
								fw.write(" ");
							}
					} 
					else{
						fw.write("error    ");
						System.out.print("error    ");
					}
	
				}
				System.out.println();
				fw.write("\r\n");
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
