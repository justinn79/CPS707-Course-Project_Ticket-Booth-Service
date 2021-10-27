import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.FileReader;

import static org.junit.Assert.*;

import org.junit.Test; 

public class test {
    public static void main(String[] args) throws IOException
    {
        File loginInvalidFile = new File("testCases/inputFiles/login/loginInvalid.txt");
        loginInvalid(loginInvalidFile);
    }

    @Test
    public static void loginInvalid(File file) throws IOException
    {
        System.out.println("Test #1: loginInvalid");
        
        // Change System.out to our actualTO.txt file
        PrintStream originalOut = System.out;
        PrintStream fileOut = new PrintStream("testCases/actualOutputFiles/login/loginInvalid/actualTO.txt");
        System.setOut(fileOut);
        
        // Run main and change System.in to our input file
        String[] args = null;
        final InputStream originalIn = System.in;
        final FileInputStream inputFile = new FileInputStream(file);
        System.setIn(inputFile);
        main.main(args);
        
        // Set System.in and System.out back
        System.setIn(originalIn);
        System.setOut(originalOut);
        
        // Copy the files from the txtfiles directory into the actualOutputFiles directory
        Path originalCUAF = Paths.get("txtfiles/currentUsersAccountsFile.txt");
        Path actualCUAF = Paths.get("testCases/actualOutputFiles/login/loginInvalid/actualCUAF.txt");
        Files.copy(originalCUAF, actualCUAF, StandardCopyOption.REPLACE_EXISTING);

        Path originalATF = Paths.get("txtfiles/availableTicketsFile.txt");
        Path actualATF = Paths.get("testCases/actualOutputFiles/login/loginInvalid/actualATF.txt");
        Files.copy(originalATF, actualATF, StandardCopyOption.REPLACE_EXISTING);

        Path originalDTF = Paths.get("txtfiles/dailyTransactionFile.txt");
        Path actualDTF = Paths.get("testCases/actualOutputFiles/login/loginInvalid/actualDTF.txt");
        Files.copy(originalDTF, actualDTF, StandardCopyOption.REPLACE_EXISTING);

        // Compare the expected output files with the actual output files
    }
}
