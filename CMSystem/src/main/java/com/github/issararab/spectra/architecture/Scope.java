package com.github.issararab.spectra.architecture;

import org.eclipse.jdt.core.dom.ASTNode;



public abstract class Scope
{
	protected String codeString;
	protected ASTNode node;
	protected String name;

	public Scope(String name)
	{
		super();
		this.name = name;
	}
	
	public Scope(){
		super();
	}

	public String getCodeString()
	{
		return codeString;
	}

	public void setCodeString(String codeString)
	{
		this.codeString = codeString;
	}

	public ASTNode getNode() {
		return node;
	}

	public void setNode(ASTNode node) {
		this.node = node;
	}

	public String getName() 
	{
		return name;
	}
	
	public void print()
	{
		System.out.println(name);
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
