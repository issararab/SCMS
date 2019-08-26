package com.github.issararab.spectra.architecture;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class Method extends Scope 
{
	
    private Vector<Statement> statements = new Vector<Statement>();
	private int totalOfLevels;
	private int maximumOfLevels;
	
	private int totalOfOperators;
	private int maximumOfOperators;
	
	private int totalDataFlow;
	private int maximumDataFlow;
	
	private int totalDataUsage;
	private int maximumDataUsage;
	
	private int totalOfMethodCalls;

	//<AI>
	private List<String> calledMethods;
	public List<String> getCalledMethods() {
		return this.calledMethods;
	}

	public void setCalledMethods(List<String> calledMethods) {
		this.calledMethods = calledMethods;
	}
	public void addMethodName(String name){
		if(!this.calledMethods.contains(name))
			this.calledMethods.add(name);
	}
	//</AI>
	public int getTotalOfMethodCalls() {
		return totalOfMethodCalls;
	}

	public void setTotalOfMethodCalls(int totalOfMethodCalls) {
		this.totalOfMethodCalls = totalOfMethodCalls;
	}

	public int getTotalOfLevels() {
		return totalOfLevels;
	}

	public int getMaximumOfLevels() {
		return maximumOfLevels;
	}

	public int getTotalOfOperators() {
		return totalOfOperators;
	}

	public int getMaximumOfOperators() {
		return maximumOfOperators;
	}

	public int getTotalDataFlow() {
		return totalDataFlow;
	}

	public int getMaximumDataFlow() {
		return maximumDataFlow;
	}

	public int getTotalDataUsage() {
		return totalDataUsage;
	}

	public int getMaximumDataUsage() {
		return maximumDataUsage;
	}

	public void setTotalOfLevels(int totalOfLevels) {
		this.totalOfLevels = totalOfLevels;
	}

	public void setMaximumOfLevels(int maximumOfLevels) {
		this.maximumOfLevels = maximumOfLevels;
	}

	public void setTotalOfOperators(int totalOfOperators) {
		this.totalOfOperators = totalOfOperators;
	}

	public void setMaximumOfOperators(int maximumOfOperators) {
		this.maximumOfOperators = maximumOfOperators;
	}

	public void setTotalDataFlow(int totalDataFlow) {
		this.totalDataFlow = totalDataFlow;
	}

	public void setMaximumDataFlow(int maximumDataFlow) {
		this.maximumDataFlow = maximumDataFlow;
	}

	public void setTotalDataUsage(int totalDataUsage) {
		this.totalDataUsage = totalDataUsage;
	}

	public void setMaximumDataUsage(int maximumDataUsage) {
		this.maximumDataUsage = maximumDataUsage;
	}

	

	public Collection<Statement> getStatements() {
		return statements;
	}

	public void setStatements(Vector<Statement> statements) {
		this.statements = statements;
	}
	
	public Method(String codeString)
	{
		super(codeString);
		
		totalOfLevels =  0;
		maximumOfLevels = 0;
		
		totalOfOperators = 0;
		maximumOfOperators = 0;
		
		totalDataFlow = 0;
		maximumDataFlow =  0;
		
		totalDataUsage = 0;
        maximumDataUsage = 0;
        
        totalOfMethodCalls = 0;
	}

	@Override
	public void print()
	{
		System.out.print("\t\t");
		super.print();
		for(Scope s: statements)
		{
			s.print();
		}
			
	}
}
