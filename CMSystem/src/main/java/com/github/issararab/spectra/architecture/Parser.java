package com.github.issararab.spectra.architecture;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllBytes;

import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;



public class Parser
{

	private AstClassExplorer astClassExplorer;
	private ClassType classType;
	
	public Parser(AstClassExplorer astClassExplorer, ClassType classType) {
		super();
		this.astClassExplorer = astClassExplorer;
		this.classType = classType;
	}

	public AstClassExplorer getAstClassExplorer() {
		return astClassExplorer;
	}

	public void setAstClassExplorer(AstClassExplorer astClassExplorer) {
		this.astClassExplorer = astClassExplorer;
	}

	public ClassType getClassType() {
		return classType;
	}

	public void setClassType(ClassType classType) {
		this.classType = classType;
	}

	public AST parseFile(String srcFilePath) throws IOException
	{
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setResolveBindings(true);
			parser.setBindingsRecovery(true);
			Map<?, ?> options = JavaCore.getOptions();
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
			parser.setCompilerOptions(options);

			String source = new String(readAllBytes(Paths.get(srcFilePath)), UTF_8);
			parser.setSource(source.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);

			CompilationUnit unit = (CompilationUnit) parser.createAST(null);

			unit.recordModifications();
			AST ast = unit.getAST();

			unit.accept(this.classType);
			unit.accept(this.astClassExplorer);

			return ast;
	}



}
