package com.github.issararab.spectra.architecture;
import java.util.Vector;

public class Statement extends Scope
{	
	private int numberOfOperators;
	private int numberOfLevels;
	private int dataFlow;
	private int dataUsage;
	
	private Vector<String> Defs;
	private Vector<String> Uses;

	public Statement(String codeString) {
		super(codeString);
		numberOfOperators = 0;
		numberOfLevels = 0;
		dataFlow = 0;
		dataUsage = 0;
		Defs = new Vector<String>();
		Uses = new Vector<String>();
	}
	
	public Statement(){
		
	}
		
	public Vector<String> getDefs() {
		return Defs;
	}

	public Vector<String> getUses() {
		return Uses;
	}
	
	public void setDefs(Vector<String> dataFlow) {
		this.Defs.addAll(dataFlow);
	}

	public void setUses(Vector<String> dataUsage) {
		this.Uses.addAll(dataUsage);
	}
	
	public int getNumberOfOperators() {
		return numberOfOperators;
	}

	public int getNumberOfLevels() {
		return numberOfLevels;
	}

	public int getDataFlow() {
		return dataFlow;
	}

	public int getDataUsage() {
		return dataUsage;
	}

	public void setNumberOfOperators(int numberOfOperators) {
		this.numberOfOperators = numberOfOperators;
	}

	public void setNumberOfLevels(int numberOfLevels) {
		this.numberOfLevels = numberOfLevels;
	}

	public void setDataFlow(int dataFlow) {
		this.dataFlow = dataFlow;
	}

	public void setDataUsage(int dataUsage) {
		this.dataUsage = dataUsage;
	}
	
	@Override
	public void print()
	{
		System.out.print("\t\t\t");
		System.out.println(codeString);
	}

}
