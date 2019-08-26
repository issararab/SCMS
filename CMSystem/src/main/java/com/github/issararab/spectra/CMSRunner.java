package com.github.issararab.spectra;
import com.github.issararab.spectra.architecture.ClassType;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

import com.github.issararab.spectra.architecture.AstClassExplorer;
import com.github.issararab.spectra.architecture.FileUtils;
import com.github.issararab.spectra.architecture.Parser;

public class CMSRunner {


	private Parser parser;
	private String classPath;
    private static Logger log = Logger.getLogger(CMSRunner.class);

    public CMSRunner() {
    }


    public String[] CMS(String srcFilePath,char level) {
        this.classPath = srcFilePath;
        parser = new Parser(new AstClassExplorer(),new ClassType());
        try {
            parser.parseFile(this.classPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(level == 'C'){
            String[] fileResult = new String[parser.getAstClassExplorer().getClassesList().size()];
            int[] classResult = new int[20];
            int count = 0;
            for(com.github.issararab.spectra.architecture.Class cl:((Vector<com.github.issararab.spectra.architecture.Class>)parser.getAstClassExplorer().getClassesList())){
                classResult[0] = cl.getTotalOfOperators();
                classResult[1] = cl.getMaximumOfOperators();
                classResult[2] = cl.getTotalOfMaximumOperators();
                classResult[3] = cl.getMaximumOfTotalOperators();
                classResult[4] = cl.getTotalOfLevels();
                classResult[5] = cl.getMaximumOfLevels();
                classResult[6] = cl.getTotalOfMaximumLevels();
                classResult[7] = cl.getMaximumOfTotalLevels();
                classResult[8] = cl.getTotalDataFlow();
                classResult[9] = cl.getMaximumDataFlow();
                classResult[10] = cl.getTotalOfMaximumDataFlow();
                classResult[11] = cl.getMaximumOfTotalDataFlow();
                classResult[12] = cl.getTotalDataUsage();
                classResult[13] = cl.getMaximulDataUsage();
                classResult[14] = cl.getTotalOfMaximumDataUsage();
                classResult[15] = cl.getMaximumOfTotalDataUsage();
                classResult[16] = cl.getTotalOfMethodCalls();
                classResult[17] = cl.getMaximumOfMethodCalls();
                classResult[18] = cl.getInOutDegree();
                classResult[19] = cl.getNumberOfPublicMembers();
                fileResult[count++] = cl.getName()+","+this.parser.getClassType().getType()+","+java.util.Arrays.toString(classResult).replaceAll("\\s+","").replace("[","").replace("]","");
            }
            return fileResult;
        }else{
            int numOfMethods = 0;
            for(com.github.issararab.spectra.architecture.Class cl:((Vector<com.github.issararab.spectra.architecture.Class>)parser.getAstClassExplorer().getClassesList()))
                numOfMethods += cl.getMethods().size();
            String[] fileResult = new String[numOfMethods];
            int count = 0;
            for(com.github.issararab.spectra.architecture.Class cl:((Vector<com.github.issararab.spectra.architecture.Class>)parser.getAstClassExplorer().getClassesList())){
                int[] methodResult = new int[9];
                for(com.github.issararab.spectra.architecture.Method mt:cl.getMethods()){
                    methodResult[0] = mt.getTotalOfOperators();
                    methodResult[1] = mt.getMaximumOfOperators();
                    methodResult[2] = mt.getTotalOfLevels();
                    methodResult[3] = mt.getMaximumOfLevels();
                    methodResult[4] = mt.getTotalDataUsage();
                    methodResult[5] = mt.getMaximumDataUsage();
                    methodResult[6] = mt.getTotalDataFlow();
                    methodResult[7] = mt.getMaximumDataFlow();
                    methodResult[8] = mt.getTotalOfMethodCalls();
                    fileResult[count++] = cl.getName()+","+mt.getName()+","+java.util.Arrays.toString(methodResult).replaceAll("\\s+","").replace("[","").replace("]","");
                }
            }
            return fileResult;
        }


    }

    public static void main(String[] args) throws FileNotFoundException {

        String projectPath = "";
        String csvPath = "";
        CMSRunner metricGenerator = new CMSRunner();

        try{

            if(args==null || args.length < 2) {
                help();
                System.exit(1);
            }

            if(args.length == 2){
                projectPath = args[0];
                csvPath = args[1];
                String[] javaFiles = FileUtils.getAllJavaFiles(projectPath);
                FileWriter pw = new FileWriter(csvPath,true);
                pw.append("classPath,className,type,Tot2Op,Max2Op,TotMaxOp,MaxTotOp,Tot2Lev,Max2Lev,TotMaxLev,MaxTotLev,Tot2DF,Max2DF,TotMaxDF,MaxTotDF,Tot2DU,Max2DU,TotMaxDU,MaxTotDU,TotInMetCall,MaxInMetCall,inOutDeg,pubMembers\n");
                for (String classPath: javaFiles) {
                    log.info("Processing java file: "+classPath + "\n");
                    String[] outputArray = metricGenerator.CMS(classPath, 'C');
                    for(String out : outputArray)
                        pw.append(classPath+","+out+"\n");

                }
                pw.flush();
                pw.close();
            }else if (args.length == 3){
                if(!args[0].endsWith(".java") || !args[2].endsWith("-method_level")){
                    help();
                    System.exit(1);
                }else{
                    String classPath = args[0];
                    csvPath = args[1];
                    FileWriter pw = new FileWriter(csvPath,true);
                    pw.append("className,methodName,TotOp,MaxOp,TotLev,MaxLev,TotDU,MaxDU,TotDF,MaxDF,InMetCall\n");
                    log.info("Processing java file: "+classPath + "\n");
                    String[] outputArray = metricGenerator.CMS(classPath, 'M');
                    for(String out : outputArray)
                        pw.append(out+"\n");

                    pw.flush();
                    pw.close();
                }


            }else{
                help();
                System.exit(1);
            }



        }catch(IOException e){
            System.out.println("Read file exception!");
        }

    }
    public static void help(){
        System.out.println("Two command usages:\n");
        System.out.println("Command for class level metrics for a java project: \n\tjava -jar spectra.jar <path to java project> <path to csv>\n");
        System.out.println("Command for method level metrics for a java file: \n\tjava -jar spectra.jar <path to java file> <path to csv> -method_level");
    }

}
