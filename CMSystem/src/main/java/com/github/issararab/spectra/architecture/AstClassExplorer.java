package com.github.issararab.spectra.architecture;


import java.util.*;


import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.ASTVisitor;

public class AstClassExplorer extends ASTVisitor
{
	private Vector<com.github.issararab.spectra.architecture.Statement> statementsList;
	private Vector<com.github.issararab.spectra.architecture.Method> methodsList;
	private Vector<com.github.issararab.spectra.architecture.Class> classesList;

	private Integer nbOfPublicMembers = 0;

	public AstClassExplorer()
	{
		super();

		statementsList = new Vector<com.github.issararab.spectra.architecture.Statement>();
		methodsList    = new Vector<com.github.issararab.spectra.architecture.Method>();
		classesList    = new Vector<com.github.issararab.spectra.architecture.Class>();

		nbOfPublicMembers = 0;
	}

	/**
	 * Getters and setters
	 */

	public Vector<com.github.issararab.spectra.architecture.Method> getMethodsList()
	{
		return methodsList;
	}

	public void setMethodsList(Vector<com.github.issararab.spectra.architecture.Method> methodsList)
	{
		this.methodsList = methodsList;
	}

	public Vector<com.github.issararab.spectra.architecture.Class> getClassesList()
	{
		return classesList;
	}

	public void setClassesList(Vector<com.github.issararab.spectra.architecture.Class> classesList)
	{
		this.classesList = classesList;
	}

	public Vector<com.github.issararab.spectra.architecture.Statement> getStatementsList()
	{
		return statementsList;
	}

	public void setStatementsList(Vector<com.github.issararab.spectra.architecture.Statement> statementsList)
	{
		this.statementsList = statementsList;
	}


	@Override
	public boolean visit(MethodInvocation node)
	{
		
		Expression exp = node.getExpression();
		ITypeBinding invocationType = null;
		IMember declaringMember = null;
		if (exp instanceof SimpleName) {
			SimpleName name = (SimpleName) exp;
			IBinding binding = name.resolveBinding();
			if (binding != null && binding instanceof IVariableBinding) {
				IVariableBinding varBinding = (IVariableBinding) binding;
				if (varBinding.getDeclaringClass() != null) {
					declaringMember = (IMember) varBinding.
					getDeclaringClass().getJavaElement();
				}
				if (declaringMember == null) {
					if (varBinding.getDeclaringMethod() != null) {
						if (varBinding.getDeclaringMethod().getDeclaringClass() != null) {
							declaringMember = (IMember)	
							varBinding.getDeclaringMethod().
							getDeclaringClass().getJavaElement();	
						}
					}
				}
			}
		}

		
		return super.visit(node);
	}


	@Override
	public boolean visit(Assignment node)
	{
		return false;
	}

	@Override
	public boolean visit(ArrayAccess node)
	{
		return false;
	}

	@Override
	public boolean visit(TypeDeclaration node)
	{

		
		FieldDeclaration[] classFields =  node.getFields();
		MethodDeclaration[] methodDeclarations = node.getMethods();
		Class cl = new Class(node.getName().toString());

		this.classesList.addElement(cl);
		for (FieldDeclaration classField :classFields)
		{
			int modifiers = classField.getModifiers();

			if (Modifier.isPublic(modifiers))
			{

				nbOfPublicMembers += classField.fragments().size();
			}
		}
		int TotOp = 0, MaxOp=0, TotMaxOp=0, MaxTotOp=0, TotLev=0, MaxLev=0, TotMaxLev=0, MaxTotLev=0, TotDF=0, MaxDF=0, TotMaxDF=0, MaxTotDF=0, TotDU=0, MaxDU=0, TotMaxDU=0, MaxTotDU=0, TotMetCal=0, MaxMetCal=0, InOutDeg=0, PubMem=0;
		List<String> inOutList = new ArrayList<String>();
		cl.setDeclaredMethods(new ArrayList<String>());
		for(MethodDeclaration methodDeclaration: methodDeclarations) {
			cl.addMethodName(methodDeclaration.getName().toString());
		}
		for(MethodDeclaration methodDeclaration: methodDeclarations)
		{
			int modifiers = methodDeclaration.getModifiers();
			if (Modifier.isPublic(modifiers))
			{
				nbOfPublicMembers += 1;
			}
			Method mt = new Method(methodDeclaration.getName().toString());
			mt.setCalledMethods(new ArrayList<String>());
			((Class)this.classesList.get(this.classesList.size()-1)).getMethods().add(mt);
			int lastMethod = ((Class)this.classesList.get(this.classesList.size()-1)).getMethods().size()-1;
			MST(methodDeclaration.getBody(), 0,lastMethod,mt);
			
			mt = ((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod);
			int totOp = 0,maxOp = 0, totLev = 0, maxLev = 0, totDf = 0, maxDf = 0, totDU = 0,maxDU = 0;
			for(int i=0; i< mt.getStatements().size();i++){
				Statement st = ((Vector<Statement>)mt.getStatements()).elementAt(i);
				int dfMetric = st.getUses().size();
				for(String d : st.getDefs()){
					for(int j=i+1; j< mt.getStatements().size();j++){
						if(((Vector<Statement>)mt.getStatements()).elementAt(j).getUses().contains(d)){
							dfMetric += j-i;
							break;
						}
					}
				}
				st.setDataFlow(dfMetric);
				totOp += st.getNumberOfOperators();
				totLev += st.getNumberOfLevels();
				totDf += st.getDataFlow();
				totDU += st.getDataUsage();
				if(st.getNumberOfOperators() > maxOp)
					maxOp = st.getNumberOfOperators();
				if(st.getNumberOfLevels() > maxLev)
					maxLev = st.getNumberOfLevels();
				if(st.getDataUsage() > maxDU)
					maxDU = st.getDataUsage();
				if(st.getDataFlow() > maxDf)
					maxDf = st.getDataFlow();
			}
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setTotalOfOperators(totOp);
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setTotalOfLevels(totLev);
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setTotalDataFlow(totDf);
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setTotalDataUsage(totDU);
			
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setMaximumOfOperators(maxOp);
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setMaximumOfLevels(maxLev);
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setMaximumDataFlow(maxDf);
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setMaximumDataUsage(maxDU);
			List<String> common = new ArrayList<String>(mt.getCalledMethods());
			common.retainAll(cl.getDeclaredMethods());
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setTotalOfMethodCalls(common.size());
			
			TotOp += totOp;
			TotMaxOp += maxOp;
			TotLev += totLev;
			TotMaxLev += maxLev;
			TotDF += totDf;
			TotMaxDF += maxDf;
			TotDU += totDU;
			TotMaxDU += maxDU;

			if(maxOp > MaxOp)
				MaxOp = maxOp;
			if(totOp > MaxTotOp)
				MaxTotOp = totOp; 
			if(maxLev > MaxLev)
				MaxLev = maxLev;
			if(totLev > MaxTotLev)
				MaxTotLev = totLev;
			if(maxDf > MaxDF)
				MaxDF =maxDf;
			if(totDf > MaxTotDF)
				MaxTotDF = totDf;
			if(maxDU > MaxDU)
				MaxDU = maxDU;
			if(totDU > MaxTotDU)
				MaxTotDU = totDU;
			
			//TotMetCal
			TotMetCal += common.size();
			//MaxMetCal
			if(common.size() > MaxMetCal)
				MaxMetCal = common.size();
			//InOutDeg
			for(String name : mt.getCalledMethods())
				if(!inOutList.contains(name) && !common.contains(name))
					inOutList.add(name);

		}
		this.classesList.elementAt(this.classesList.size()-1).setTotalOfOperators(TotOp);
		this.classesList.elementAt(this.classesList.size()-1).setTotalOfMaximumOperators(TotMaxOp);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumOfOperators(MaxOp);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumOfTotalOperators(MaxTotOp);
		this.classesList.elementAt(this.classesList.size()-1).setTotalOfLevels(TotLev);
		this.classesList.elementAt(this.classesList.size()-1).setTotalOfMaximumLevels(TotMaxLev);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumOfLevels(MaxLev);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumOfTotalLevels(MaxTotLev);
		this.classesList.elementAt(this.classesList.size()-1).setTotalDataFlow(TotDF);
		this.classesList.elementAt(this.classesList.size()-1).setTotalOfMaximumDataFlow(TotMaxDF);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumDataFlow(MaxDF);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumOfTotalDataFlow(MaxTotDF);
		this.classesList.elementAt(this.classesList.size()-1).setTotalDataUsage(TotDU);
		this.classesList.elementAt(this.classesList.size()-1).setTotalOfMaximumDataUsage(TotMaxDU);
		this.classesList.elementAt(this.classesList.size()-1).setMaximulDataUsage(MaxDU);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumOfTotalDataUsage(MaxTotDU);
		this.classesList.elementAt(this.classesList.size()-1).setTotalOfMethodCalls(TotMetCal);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumOfMethodCalls(MaxMetCal);
		this.classesList.elementAt(this.classesList.size()-1).setInOutDegree(inOutList.size());
		this.classesList.elementAt(this.classesList.size()-1).setNumberOfPublicMembers(nbOfPublicMembers);

		
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayCreation node)
	{	
		return false;
	}

	@Override
	public boolean visit(ArrayInitializer node)
	{
		return false;
	}

	@Override
	public boolean visit(ClassInstanceCreation node)
	{

		return false;
	}

	@Override
	public boolean visit(ConditionalExpression node)
	{	
		return false;
	}

	@Override
	public boolean visit(FieldAccess node)
	{
		return false;
	}

	@Override
	public boolean visit(InfixExpression node) 
	{	
		return false;
	}

	@Override
	public boolean visit(ParenthesizedExpression node)
	{	
		return false;
	}

	@Override
	public boolean visit(PostfixExpression node)
	{	
		return false;
	}

	@Override
	public boolean visit(PrefixExpression node)
	{	
		return false;
	}

	@Override
	public boolean visit(VariableDeclarationExpression node)
	{	
		return false;
	}

	@Override
	public boolean visit(VariableDeclarationFragment node)
	{
		return false;
	}

	/**
	 * Helper method for calculating the number of operators in a the given statement node
	 * Works by recursively processing nodes in the subtree for this statement
	 */
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
			if (exp != null) opcount = 1 + calculateNumberOfOperators(exp);

			break;

		default: break;
		}

		return opcount;
	}
	

	/* method that tackles the block inside the method */
	public void MST(org.eclipse.jdt.core.dom.Statement st, int level, int indexMethod,Method mt){
		if(st == null)
			return;
		if(st.getNodeType() == st.FOR_STATEMENT || st.getNodeType() == st.WHILE_STATEMENT || st.getNodeType() == st.DO_STATEMENT || st.getNodeType() == st.BLOCK || st.getNodeType() == st.IF_STATEMENT || st.getNodeType() == st.TRY_STATEMENT || st.getNodeType() == st.SWITCH_STATEMENT){
			if (st.getNodeType() == st.BLOCK){
				for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)(((Block)st).statements()))
					MST(stt, level,indexMethod,mt);
			}
			
			if (st.getNodeType() == st.FOR_STATEMENT || st.getNodeType() == st.WHILE_STATEMENT || st.getNodeType() == st.DO_STATEMENT){
				if(st.getNodeType() == st.FOR_STATEMENT ){
					int numOp = 0,lev = 0, DF = 0, DU = 0;
					//expression
					AST astSub = ((ForStatement)st).getExpression().getAST();

					AstStatementExplorer astStmExplorer = new AstStatementExplorer();
					((ForStatement)st).getExpression().accept(astStmExplorer);

					int nbOps = astStmExplorer.getNumberOfOperators();
					Statement s = new Statement(st.toString());
					s.setNumberOfOperators(nbOps);
					s.setNumberOfLevels(level);
					s.setDataUsage((astStmExplorer.getDataUsage()));
					s.setDefs(astStmExplorer.getDefinitions());
					s.setUses(adjust(astStmExplorer.getUses(),astStmExplorer.getDefinitions()));
					for(String name : astStmExplorer.getInvocMethodList())
						mt.addMethodName(name);

					//updaters
					((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).get(indexMethod).getStatements().add(s);

					for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)((Block)((ForStatement)st).getBody()).statements())
						MST(stt, level+1,indexMethod,mt);
				}else if(st.getNodeType() == st.WHILE_STATEMENT){
					AST astSub = ((WhileStatement)st).getExpression().getAST();

					AstStatementExplorer astStmExplorer = new AstStatementExplorer();
					((WhileStatement)st).getExpression().accept(astStmExplorer);
					Integer nbOps = astStmExplorer.getNumberOfOperators();


					Statement s = new Statement(st.toString());
					s.setNumberOfOperators(nbOps);
					s.setNumberOfLevels(level);
					s.setDataUsage((astStmExplorer.getDataUsage()));
					s.setDefs(astStmExplorer.getDefinitions());
					s.setUses(adjust(astStmExplorer.getUses(),astStmExplorer.getDefinitions()));
					for(String name : astStmExplorer.getInvocMethodList())
						mt.addMethodName(name);

					((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).get(indexMethod).getStatements().add(s);
					for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)((Block)((WhileStatement)st).getBody()).statements())
						MST(stt, level+1,indexMethod,mt);
				}else if (st.getNodeType() == st.DO_STATEMENT){
					AST astSub = ((DoStatement)st).getExpression().getAST();
					AstStatementExplorer astStmExplorer = new AstStatementExplorer();
					((DoStatement)st).getExpression().accept(astStmExplorer);
					Integer nbOps = astStmExplorer.getNumberOfOperators();

					Statement s = new Statement(st.toString());
					s.setNumberOfOperators(nbOps);
					s.setNumberOfLevels(level);
					s.setDataUsage((astStmExplorer.getDataUsage()));
					s.setDefs(astStmExplorer.getDefinitions());
					s.setUses(adjust(astStmExplorer.getUses(),astStmExplorer.getDefinitions()));
					for(String name : astStmExplorer.getInvocMethodList())
						mt.addMethodName(name);

					((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).get(indexMethod).getStatements().add(s);
					for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)((Block)((DoStatement)st).getBody()).statements())
						MST(stt, level+1,indexMethod,mt);
				}
				
			
			} else if (st.getNodeType() == st.IF_STATEMENT){
				AST astSub = ((IfStatement)st).getExpression().getAST();

				AstStatementExplorer astStmExplorer = new AstStatementExplorer();
				((IfStatement)st).getExpression().accept(astStmExplorer);
				Integer nbOps = astStmExplorer.getNumberOfOperators();

				Statement s = new Statement(st.toString());
				s.setNumberOfOperators(nbOps);
				s.setNumberOfLevels(level);
				s.setDataUsage((astStmExplorer.getDataUsage()));
				s.setDefs(astStmExplorer.getDefinitions());
				s.setUses(adjust(astStmExplorer.getUses(),astStmExplorer.getDefinitions()));
				for(String name : astStmExplorer.getInvocMethodList())
					mt.addMethodName(name);

				((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).get(indexMethod).getStatements().add(s);

                if ((((IfStatement)st).getThenStatement()).getNodeType() == st.IF_STATEMENT ) {
					MST((((IfStatement) st).getThenStatement()), level + 1, indexMethod,mt);
				}else {

                	if((((IfStatement)st).getThenStatement()).getNodeType() == ((IfStatement) st).RETURN_STATEMENT)
						MST((((IfStatement)st).getThenStatement()), level + 1, indexMethod,mt);
                	else {
						try{
							for (org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>) ((Block) ((IfStatement) st).getThenStatement()).statements())
								MST(stt, level + 1, indexMethod,mt);
						}catch (Exception e){
							MST(((IfStatement) st).getThenStatement(), level + 1, indexMethod,mt);
						}
					}

				}

				if((((IfStatement)st).getElseStatement()) != null){
				    if ((((IfStatement)st).getElseStatement()).getNodeType() == st.IF_STATEMENT )
                        MST((((IfStatement)st).getElseStatement()), level+1,indexMethod,mt);
				    else
                        for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)((Block)((IfStatement)st).getElseStatement()).statements())
                            MST(stt, level+1,indexMethod,mt);
                }

				
			}else if(st.getNodeType() == st.TRY_STATEMENT){
				for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)((Block)((TryStatement)st).getBody()).statements())
					MST(stt, level+1,indexMethod,mt);
				if((((TryStatement)st).getFinally()) != null)
					for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)((Block)((TryStatement)st).getFinally()).statements())
						MST(stt, level+1,indexMethod,mt);
			}else if(st.getNodeType() == st.SWITCH_STATEMENT){
				AST astSub = ((SwitchStatement)st).getExpression().getAST();

				AstStatementExplorer astStmExplorer = new AstStatementExplorer();
				((SwitchStatement)st).getExpression().accept(astStmExplorer);
				Integer nbOps = astStmExplorer.getNumberOfOperators();

				Statement s = new Statement(st.toString());
				s.setNumberOfOperators(nbOps);
				s.setNumberOfLevels(level);
				s.setDataUsage((astStmExplorer.getDataUsage()));
				s.setDefs(astStmExplorer.getDefinitions());
				s.setUses(adjust(astStmExplorer.getUses(),astStmExplorer.getDefinitions()));
				for(String name : astStmExplorer.getInvocMethodList())
					mt.addMethodName(name);

				((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).get(indexMethod).getStatements().add(s);
				for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)(((SwitchStatement)st).statements()))
					MST(stt, level+1,indexMethod,mt);
			}
		} else{
			AST astSub = st.getAST();

			AstStatementExplorer astStmExplorer = new AstStatementExplorer();
			st.accept(astStmExplorer);
			Integer nbOps = astStmExplorer.getNumberOfOperators();

			Statement s = new Statement(st.toString());
			s.setNumberOfOperators(nbOps);
			s.setNumberOfLevels(level);
			s.setDataUsage((astStmExplorer.getDataUsage()));
			s.setDefs(astStmExplorer.getDefinitions());
			s.setUses(adjust(astStmExplorer.getUses(),astStmExplorer.getDefinitions()));
			for(String name : astStmExplorer.getInvocMethodList())
				mt.addMethodName(name);

			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).get(indexMethod).getStatements().add(s);
		}

	}
	
	public Vector<String> adjust(Vector<String> U,Vector<String> D){
		for(String d : D){
			if(Collections.frequency(U, d) == 1){
				U.remove(d);
			}
			
		}
		HashSet hs = new HashSet();
		hs.addAll(U);
		U.clear();
		U.addAll(hs);
		return U;
	}
	
}
