# SCMS
SCMS stands for Spectra Complexity Metric System.

The main two functionalities of the tool are:
- Giving a path to a java project, Spectra tool compiles a taxonomy of all class level complexity metrics based on method and statement level metrics. 

- Given a path to a java file and appending the method_level flag in the commad, Spectra tool compiles a taxonomy of all method level complexity metrics based on statement level metrics. (see the set of metrics below).

The tool can compute the set of complexity metrics for any java project and outputs the result to a specified .csv file. For project processing, Each line consists of: the path to the java file, name of the class, type of the class, and then followed by the set of comma separated class level metric values. For java file processing, Each line consists of: the name of the class, the name of the method, and then followed by the set of comma separated method level metric values.

# UML Class Diagram
![](SCMS_UML.png?style=centerme)
# Prerequisites
For the tool to be compiled and run succefully, the user needs to make sure the following are installed:
- JDK 8 or higher
- Maven 3 or higher

# Compile and run

        git clone https://github.com/issararab/SPECTRA.git
        cd SPECTRA
        cd CMSystem
        mvn clean install
        
The Jar file of the tool, named 'spectra.jar', will be generated and available in 'target' folder of the project. You can move it to any desired folder and run it as follows:
        
        #for class level metrics of a given java project
        java -jar spectra.jar <path_to_java_project> <path_to_output_csv_file>     

OR

        #for method level metrics of a given java file
        java -jar spectra.jar <path_to_java_file> <path_to_output_csv_file> -method_level
        
 # Metrics
 The software follows a botttum-up approach to compile method and class level metrics based on statement level metrics. The tables bellow gives an overview of the metrics measured at each granularity level:
 
 ## Statement level metrics
| Metric Short Name	| Short Description |
| --- | --- |
| NumOp	| Number of Operators |
| NumLev |	Number of Levels |
| DF | Data Flow |
| DU | Data Usage |

 ## Method level metrics
| Metric Short Name	| Short Description |
| --- | --- |
| MaxOp | Maximum of number of operators of each statement in the method. Metric based on the statements results |
| TotOp | Total of number of operators of each statement in the method. Metric based on the statements results |
| MaxLev | Maximum of number of levels at each statement in the method. Metric based on the statements results |
| TotLev | Total of number of levels at each statement in the method. Metric based on the statements results|
| MaxDF | Maximum of number of data flow of each statement in the method. Metric based on the statements results |
| TotDF | Total of number of data flow of each statement in the method. Metric based on the statements results |
| MaxDU | Maximum of number of data usage of each statement in the method. Metric based on the statements results |
| TotDU | Total of number of data usage of each statement in the method. Metric based on the statements results |
| InMetCall |	Number of within class method calls of the method in question |

 
 ## Class level metrics
| Metric Short Name	| Short Description |
| --- | --- |
| Type | Type of the class |
| Tot2Op | Counts the total number of operators. (method output based) |
| TotMaxOp | Counts the total of the max operators. (method output based) |
| Max2Op | Counts the max of max operators. (method output based) |
| MaxTotOp | Counts the max of the total number of operators. (method output based) |
| Tot2Lev | Counts the total number of levels in the whole class code. (method output based) |
| TotMaxLev | Counts the sum of the maximum level in each method |
| MaxTotLev | Counts the max of the total number of levels in each method |
| Max2Lev | Counts the max level in the whole class, i.e. the deepest branch. (method output based) |
| Tot2DU | Counts the total number of data usage in the class. (method output based) |
| TotMaxDU | Counts the total number of the max data usage in the class. (method output based) |
| MaxTotDU | Counts the max of the total number of data usage in each method. (method output based) |
| Max2DU | Counts the max of max data usage. (method output based) |
| Tot2DF | Counts the total number of data flows in a class. (method output based) |
| TotMaxDF | Counts the total of the max data flows in each method of the class. (method output based) |
| Max2DF | Counts the max of max data flows in each method of the class. (method output based) |
| MaxTotDF |  Counts the max of the total data flows in each method of the class. (method output based) |
| TotInMetCall | Counts the total number of within class method calls. (method output based) |
| MaxInMetCall | Counts the max number of within class method calls. (method output based) |
| inOutDeg | Counts the number of in class call of external methods. Similar to out degrees of a dynamic call graph |
| pubMembers |	Counts the number of members in a class |

