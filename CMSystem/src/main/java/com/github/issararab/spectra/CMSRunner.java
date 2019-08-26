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


    public String[] CMS(String srcFilePath) {
        this.classPath = srcFilePath;
        parser = new Parser(new AstClassExplorer(),new ClassType());
        try {
            parser.parseFile(this.classPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
    }

    public static void main(String[] args) throws FileNotFoundException {

        String projectPath = "";
        String csvPath = "";
        CMSRunner metricGenerator = new CMSRunner();

        try{

            if(args==null || args.length < 2) {
                log.info("Usage: java -jar ck.jar <path to project> <path to csv>");
                System.exit(1);
            }

            projectPath = args[0];
            csvPath = args[1];
            String[] javaFiles = FileUtils.getAllJavaFiles(projectPath);
            FileWriter pw = new FileWriter(csvPath,true);
            pw.append("classPath,className,type,Tot2Op,Max2Op,TotMaxOp,MaxTotOp,Tot2Lev,Max2Lev,TotMaxLev,MaxTotLev,Tot2DF,Max2DF,TotMaxDF,MaxTotDF,Tot2DU,Max2DU,TotMaxDU,MaxTotDU,TotInMetCall,MaxInMetCall,inOutDeg,pubMembers\n");
            for (String classPath: javaFiles) {

                log.info("Processing java file: "+projectPath + "\n");

                String[] outputArray = metricGenerator.CMS(classPath);
                for(String out : outputArray)
                    pw.append(classPath+","+out+"\n");


            }
            pw.flush();
            pw.close();


        }catch(IOException e){
            System.out.println("Read file exception!");
        }

    }

}
