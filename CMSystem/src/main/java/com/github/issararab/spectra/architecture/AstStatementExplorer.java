package com.github.issararab.spectra.architecture;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;











import org.eclipse.jdt.core.dom.*;


public class AstStatementExplorer extends ASTVisitor{

	private int numberOfOperators;
	private int numberOfLevels;
	private int dataFlow;
	private int dataUsage;
	private List<String> varList;
	private List<String> invocMethodList = new ArrayList<String>();
	private Vector<String> definitions;
	private Vector<String> uses;
	

	public AstStatementExplorer()
	{
		super();
		this.numberOfOperators = 0;
		this.numberOfLevels = 0;
		this.dataFlow = 0;
		this.dataUsage = 0;
		this.varList = new ArrayList<String>();
		this.definitions = new Vector<String>();
		this.uses = new Vector<String>();
	
	}
	public  List<String> getInvocMethodList() {
		return this.invocMethodList;
	}

	public  void addInvocMethod(String name) {
		if (!this.invocMethodList.contains(name))
			this.invocMethodList.add(name);
	}
	public int getNumberOfOperators() {
		return numberOfOperators;
	}

	public int getNumberOfLevels() {
		return numberOfLevels;
	}

	public Vector<String> getUses() {
		return this.uses;
	}

	public Vector<String> getDefinitions() {
		return this.definitions;
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
	public void preVisit(ASTNode node)
	{
		super.preVisit(node);

		if(node instanceof Expression || node.getNodeType() == ASTNode.VARIABLE_DECLARATION_STATEMENT)
		{
			Integer nbOps = calculateNumberOfOperators(node);
			if(nbOps > numberOfOperators)
				numberOfOperators = nbOps; 

		}

	}
	
	private Integer calculateNumberOfOperators(ASTNode stmt)
	{
		Integer opcount = 0;

		switch(stmt.getNodeType())
		{
		case ASTNode.ARRAY_ACCESS:
			ArrayAccess arrAccess = (ArrayAccess) stmt;
			opcount = calculateNumberOfOperators(arrAccess.getArray()) +
					calculateNumberOfOperators(arrAccess.getIndex());
			break;

		case ASTNode.ARRAY_INITIALIZER:
			ArrayInitializer arrInit = (ArrayInitializer) stmt;
			@SuppressWarnings("unchecked")
			List<Expression> exprs = arrInit.expressions();
			for(Expression expr: exprs)
			{
				opcount += calculateNumberOfOperators(expr);
			}
			break;

		case ASTNode.INFIX_EXPRESSION:
			InfixExpression inExpr = (InfixExpression) stmt;
			opcount = 1 + calculateNumberOfOperators(inExpr.getLeftOperand())
					+ calculateNumberOfOperators(inExpr.getRightOperand());
			break;

		case ASTNode.POSTFIX_EXPRESSION:
			PostfixExpression postExpr = (PostfixExpression) stmt;
			opcount = 1 + calculateNumberOfOperators(postExpr.getOperand());
			break;

		case ASTNode.PREFIX_EXPRESSION:
			PrefixExpression preExpr = (PrefixExpression) stmt;
			opcount = 1 + calculateNumberOfOperators(preExpr.getOperand());
			break;

		case ASTNode.ASSIGNMENT :
			Assignment rhs = (Assignment) stmt;
			opcount = 1 + calculateNumberOfOperators(rhs.getLeftHandSide()) +
					calculateNumberOfOperators(rhs.getRightHandSide());
			if(rhs.getLeftHandSide().getNodeType() != ASTNode.FIELD_ACCESS){
				//System.out.print("right assign    1 -:  "+rhs.getLeftHandSide() + "\n");
				definitions.add(rhs.getLeftHandSide().toString());
				if(!varList.contains(rhs.getLeftHandSide().toString())){

						this.dataUsage++;
						varList.add(rhs.getLeftHandSide().toString());
					}
			}else{
				//System.out.print("right assign    2 -:  "+rhs.getLeftHandSide() + "\n");
				definitions.add(((FieldAccess)rhs.getLeftHandSide()).getName().toString());
				if(!varList.contains(((FieldAccess)rhs.getLeftHandSide()).getName().toString())){
						this.dataUsage++;
						varList.add(((FieldAccess)rhs.getLeftHandSide()).getName().toString());
					}
			}
			break;

		case ASTNode.VARIABLE_DECLARATION_STATEMENT:
			VariableDeclarationStatement vds = (VariableDeclarationStatement) stmt;
			@SuppressWarnings("unchecked")
			List<VariableDeclarationFragment> frags = vds.fragments();
			for(VariableDeclarationFragment frag: frags)
			{
				opcount += calculateNumberOfOperators(frag);
			}
			break;

		case ASTNode.VARIABLE_DECLARATION_FRAGMENT:
			VariableDeclarationFragment vdf = (VariableDeclarationFragment) stmt;
			Expression exp = vdf.getInitializer();


			if (exp != null){
				definitions.add(vdf.getName().toString());
				opcount = 1 + calculateNumberOfOperators(exp);
				this.dataUsage++;
				varList.add(vdf.getName().toString());
			}else{
				definitions.add(vdf.getName().toString());
				this.dataUsage++;
				varList.add(vdf.getName().toString());
			}
			break;

		default: break;
		}

		return opcount;
	}
	
	private Integer calculateDU(ASTNode stmt)
	{

		Integer opcount = 0;
		switch(stmt.getNodeType())
		{
		
		case ASTNode.METHOD_INVOCATION:
			MethodInvocation st = (MethodInvocation)stmt;
			
			
			if(st.getExpression() != null){
				String[] words = st.getExpression().toString().split(".");
				String ep; 
				if (words.length ==0 )
					ep = st.getExpression().toString();
				else
					ep = words[0];
				if(!varList.contains(ep) && !ep.equals("this")){
					this.dataUsage++;
					varList.add(ep);
					uses.add(ep);
				}
			}
			if(st.arguments().size() != 0)
				for(ASTNode n : (List<ASTNode>)st.arguments()){
					calculateDU(n);
				
			}
			break;
		case ASTNode.ARRAY_ACCESS:
			ArrayAccess arrAccess = (ArrayAccess) stmt;
			opcount = calculateNumberOfOperators(arrAccess.getArray()) +
					calculateNumberOfOperators(arrAccess.getIndex());
			calculateDU(arrAccess.getArray());
			calculateDU(arrAccess.getIndex());
			break;

		case ASTNode.ARRAY_INITIALIZER:
			ArrayInitializer arrInit = (ArrayInitializer) stmt;
			List<Expression> exprs = arrInit.expressions();
			for(Expression expr: exprs)
			{
				opcount += calculateNumberOfOperators(expr);
				calculateDU(expr);
			}
			break;

		case ASTNode.INFIX_EXPRESSION:
			InfixExpression inExpr = (InfixExpression) stmt;
			opcount = 1 + calculateNumberOfOperators(inExpr.getLeftOperand())
					+ calculateNumberOfOperators(inExpr.getRightOperand());
			calculateDU(inExpr.getLeftOperand());
			calculateDU(inExpr.getRightOperand());
			break;

		case ASTNode.POSTFIX_EXPRESSION:
			PostfixExpression postExpr = (PostfixExpression) stmt;
			opcount = 1 + calculateNumberOfOperators(postExpr.getOperand());
			calculateDU(postExpr.getOperand());
			break;

		case ASTNode.PREFIX_EXPRESSION:
			PrefixExpression preExpr = (PrefixExpression) stmt;
			opcount = 1 + calculateNumberOfOperators(preExpr.getOperand());
			calculateDU(preExpr.getOperand());
			break;

		case ASTNode.ASSIGNMENT :
			Assignment rhs = (Assignment) stmt;
			opcount = 1 + calculateNumberOfOperators(rhs.getLeftHandSide()) +
					calculateNumberOfOperators(rhs.getRightHandSide());
			calculateDU(rhs.getLeftHandSide());
			calculateDU(rhs.getRightHandSide());
			
			break;

		case ASTNode.VARIABLE_DECLARATION_STATEMENT:
			VariableDeclarationStatement vds = (VariableDeclarationStatement) stmt;
			List<VariableDeclarationFragment> frags = vds.fragments();
			for(VariableDeclarationFragment frag: frags)
			{
				opcount += calculateNumberOfOperators(frag);
				calculateDU(frag);
			}
			break;

		case ASTNode.VARIABLE_DECLARATION_FRAGMENT:
			VariableDeclarationFragment vdf = (VariableDeclarationFragment) stmt;
			Expression exp = vdf.getInitializer();
			if (exp != null){
				definitions.add(vdf.getName().toString());
				opcount = 1 + calculateNumberOfOperators(exp);
				calculateDU(exp);
			}else{
				definitions.add(vdf.toString());
			}
			

			break;
		case ASTNode.FIELD_ACCESS:
			calculateDU(((FieldAccess)stmt).getName());
			calculateDU(((FieldAccess)stmt).getExpression());
			break;
		case ASTNode.ARRAY_TYPE:
		case ASTNode.PARAMETERIZED_TYPE:
		case ASTNode.SIMPLE_TYPE:
		case ASTNode.PRIMITIVE_TYPE:
		case ASTNode.QUALIFIED_TYPE:
		case ASTNode.WILDCARD_TYPE:
			if(!varList.contains(stmt.toString()))
				varList.add(stmt.toString());
			break;

		case ASTNode.SIMPLE_NAME:
			if(!varList.contains(stmt.toString())){
				this.dataUsage++;
				varList.add(stmt.toString());
				uses.add(stmt.toString());
			}
			break;

		
		default: break;
		}

		return opcount;
	}
	
	
	@Override
	public boolean visit(SimpleName node)
	{

			if(definitions.contains(node.toString()))
				uses.add(node.toString());
			if(!varList.contains(node.toString())){
				this.dataUsage++;
				varList.add(node.toString());
				uses.add(node.toString());
			}
				
		
		return super.visit(node);
	}

	
	@Override
	public boolean visit(MethodInvocation node)
	{
		
		MethodInvocation st = (MethodInvocation)node;
		if(st.getExpression() != null){
			String[] words = st.getExpression().toString().split("\\.");
			//add the name of the invoked methods
			this.addInvocMethod(st.getName().toString());
			for(int i = 0;i<words.length;i++)
				if(words[i].indexOf('(')!=-1)
					this.addInvocMethod(words[i].substring(0,words[i].indexOf('(')));
			String ep;
			if (words.length == 0 )
				ep = st.getExpression().toString();
			else
				ep = words[0];
			if(!varList.contains(ep) && !ep.equals("this") && !ep.equals("System") && !ep.equals("super")){
				this.dataUsage++;
				varList.add(ep);
				uses.add(ep);
			}
		}else
			this.addInvocMethod(st.getName().toString());
		if(st.arguments().size() != 0)
			for(ASTNode n : (List<ASTNode>)st.arguments()){
				this.numberOfOperators += calculateDU(n);
			
		}

		return false;
	}


	@Override
	public boolean visit(ArrayType node)
	{
		if(!varList.contains(node.toString()))
			varList.add(node.toString());	
		return super.visit(node);
	}
	
	@Override
	public boolean visit(ParameterizedType node)
	{
		if(!varList.contains(node.toString()))
			varList.add(node.toString());	
		return super.visit(node);
	}
	
	@Override
	public boolean visit( PrimitiveType node)
	{
		if(!varList.contains(node.toString()))
			varList.add(node.toString());	
		return super.visit(node);
	}
	
	@Override
	public boolean visit( QualifiedType node)
	{
		if(!varList.contains(node.toString()))
			varList.add(node.toString());	
		return super.visit(node);
	}
	
	@Override
	public boolean visit( SimpleType node)
	{
		if(!varList.contains(node.toString())){
			varList.add(node.toString());
		}
		return super.visit(node);
	}
	
	
	
	@Override
	public boolean visit(WildcardType node)
	{
		if(!varList.contains(node.toString()))
			varList.add(node.toString());	
		return super.visit(node);
	}
	
	
}
