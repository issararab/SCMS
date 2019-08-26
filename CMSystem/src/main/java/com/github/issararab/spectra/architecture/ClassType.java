package com.github.issararab.spectra.architecture;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;


public class ClassType extends ASTVisitor {

    private String type;

    @Override
    public boolean visit(TypeDeclaration node) {
        if(node.isInterface()) type = "interface";
        else type = "class";
        return false;
    }

    @Override
    public boolean visit(EnumDeclaration node) {
        type = "enum";
        return false;
    }



    public String getType() {
        return type;
    }


}
