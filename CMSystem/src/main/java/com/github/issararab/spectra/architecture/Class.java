package com.github.issararab.spectra.architecture;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class Class extends Scope 
{
	public Vector<Method> methods = new Vector<Method>();
	
	
	private int totalOfOperators;
	private int maximumOfOperators;
	
	private int totalOfMaximumOperators;
	private int maximumOfTotalOperators;
	
	private int totalOfLevels;
	private int maximumOfLevels;
	
	private int totalOfMaximumLevels;
	private int maximumOfTotalLevels;
	
	private int totalDataFlow;
	private int maximumDataFlow;
	
	private int totalOfMaximumDataFlow;
	private int maximumOfTotalDataFlow;
	
	private int totalDataUsage;
	private int maximulDataUsage;
	
	private int totalOfMaximumDataUsage;
	private int maximumOfTotalDataUsage;
	
	private int totalOfMethodCalls;
	private int maximumOfMethodCalls;
	
	private int inOutDegree;
	private int numberOfPublicMembers;


	private List<String> declaredMethods;
	public List<String> getDeclaredMethods() {
		return this.declaredMethods;
	}

	public void setDeclaredMethods(List<String> declaredMethods) {
		this.declaredMethods = declaredMethods;
	}
	public void addMethodName(String name){
		this.declaredMethods.add(name);
	}

		public Class(String codeString)
	{
		super(codeString);
		
		totalOfOperators = 0;
		maximumOfOperators = 0;
		
		totalOfMaximumOperators = 0;
		maximumOfTotalOperators = 0;
		
		totalOfLevels = 0;
		maximumOfLevels = 0;
		
		totalOfMaximumLevels = 0;
		maximumOfTotalLevels = 0;
		
		totalDataFlow = 0;
		maximumDataFlow = 0;
		
		totalOfMaximumDataFlow = 0;
		maximumOfTotalDataFlow = 0;
		
		totalDataUsage = 0;
		maximulDataUsage = 0;
		
		totalOfMaximumDataUsage = 0;
		maximumOfTotalDataUsage = 0;
		
		totalOfMethodCalls = 0;
		maximumOfMethodCalls = 0;
		
		inOutDegree = 0;
		numberOfPublicMembers = 0;
		
		
	}

	public Collection<Method> getMethods()
	{
		return methods;
	}

	public int getTotalOfOperators() {
		return totalOfOperators;
	}

	public int getMaximumOfOperators() {
		return maximumOfOperators;
	}

	public int getTotalOfMaximumOperators() {
		return totalOfMaximumOperators;
	}

	public int getMaximumOfTotalOperators() {
		return maximumOfTotalOperators;
	}

	public int getTotalOfLevels() {
		return totalOfLevels;
	}

	public int getMaximumOfLevels() {
		return maximumOfLevels;
	}

	public int getTotalOfMaximumLevels() {
		return totalOfMaximumLevels;
	}

	public int getMaximumOfTotalLevels() {
		return maximumOfTotalLevels;
	}

	public int getTotalDataFlow() {
		return totalDataFlow;
	}

	public int getMaximumDataFlow() {
		return maximumDataFlow;
	}

	public int getTotalOfMaximumDataFlow() {
		return totalOfMaximumDataFlow;
	}

	public int getMaximumOfTotalDataFlow() {
		return maximumOfTotalDataFlow;
	}

	public int getTotalDataUsage() {
		return totalDataUsage;
	}

	public int getMaximulDataUsage() {
		return maximulDataUsage;
	}

	public int getTotalOfMaximumDataUsage() {
		return totalOfMaximumDataUsage;
	}

	public int getMaximumOfTotalDataUsage() {
		return maximumOfTotalDataUsage;
	}

	public int getTotalOfMethodCalls() {
		return totalOfMethodCalls;
	}

	public int getMaximumOfMethodCalls() {
		return maximumOfMethodCalls;
	}

	public int getInOutDegree() {
		return inOutDegree;
	}

	public int getNumberOfPublicMembers() {
		return numberOfPublicMembers;
	}

	public void setMethods(Vector<Method> methods) {
		this.methods = methods;
	}

	public void setTotalOfOperators(int totalOfOperators) {
		this.totalOfOperators = totalOfOperators;
	}

	public void setMaximumOfOperators(int maximumOfOperators) {
		this.maximumOfOperators = maximumOfOperators;
	}

	public void setTotalOfMaximumOperators(int totalOfMaximumOperators) {
		this.totalOfMaximumOperators = totalOfMaximumOperators;
	}

	public void setMaximumOfTotalOperators(int maximumOfTotalOperators) {
		this.maximumOfTotalOperators = maximumOfTotalOperators;
	}

	public void setTotalOfLevels(int totalOfLevels) {
		this.totalOfLevels = totalOfLevels;
	}

	public void setMaximumOfLevels(int maximumOfLevels) {
		this.maximumOfLevels = maximumOfLevels;
	}

	public void setTotalOfMaximumLevels(int totalOfMaximumLevels) {
		this.totalOfMaximumLevels = totalOfMaximumLevels;
	}

	public void setMaximumOfTotalLevels(int maximumOfTotalLevels) {
		this.maximumOfTotalLevels = maximumOfTotalLevels;
	}

	public void setTotalDataFlow(int totalDataFlow) {
		this.totalDataFlow = totalDataFlow;
	}

	public void setMaximumDataFlow(int maximumDataFlow) {
		this.maximumDataFlow = maximumDataFlow;
	}

	public void setTotalOfMaximumDataFlow(int totalOfMaximumDataFlow) {
		this.totalOfMaximumDataFlow = totalOfMaximumDataFlow;
	}

	public void setMaximumOfTotalDataFlow(int maximumOfTotalDataFlow) {
		this.maximumOfTotalDataFlow = maximumOfTotalDataFlow;
	}

	public void setTotalDataUsage(int totalDataUsage) {
		this.totalDataUsage = totalDataUsage;
	}

	public void setMaximulDataUsage(int maximulDataUsage) {
		this.maximulDataUsage = maximulDataUsage;
	}

	public void setTotalOfMaximumDataUsage(int totalOfMaximumDataUsage) {
		this.totalOfMaximumDataUsage = totalOfMaximumDataUsage;
	}

	public void setMaximumOfTotalDataUsage(int maximumOfTotalDataUsage) {
		this.maximumOfTotalDataUsage = maximumOfTotalDataUsage;
	}

	public void setTotalOfMethodCalls(int totalOfMethodCalls) {
		this.totalOfMethodCalls = totalOfMethodCalls;
	}

	public void setMaximumOfMethodCalls(int maximumOfMethodCalls) {
		this.maximumOfMethodCalls = maximumOfMethodCalls;
	}

	public void setInOutDegree(int inOutDegree) {
		this.inOutDegree = inOutDegree;
	}

	public void setNumberOfPublicMembers(int numberOfPublicMembers) {
		this.numberOfPublicMembers = numberOfPublicMembers;
	}
	
	@Override
	public void print()
	{
		System.out.print("\t");
		super.print();
		for(Scope s: methods)
		{
			s.print();
		}
	}
}
