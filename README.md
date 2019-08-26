# SPECTRA
Two functionalities:
- Giving a path to a java project, Spectra tool compiles a taxonomy of all class level complexity metrics based on method and statement level metrics. 

- Given a path to a java file and appending the method_level flag in the commad, Spectra tool compiles a taxonomy of all method level complexity metrics based on statement level metrics. (see the set of metrics below).

The tool can compute the set of complexity metrics for any java project and outputs the result to a specified .csv file. For project processing, Each line consists of: the path to the java file, name of the class, type of the class, and then followed by the set of comma separated class level metric values. For java file processing, Each line consists of: the name of the class, the name of the method, and then followed by the set of comma separated method level metric values.

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
 The method and class level metrics are based on statement level metrics described bellow.
 
 ## Statement level metrics
 
 ## Method level metrics
 
 ## Class level metrics
