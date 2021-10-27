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
import java.io.FileReader; 

import static org.junit.Assert.*;

import org.junit.Test; 

public class test {
    public static void main(String[] args) throws FileNotFoundException
    {
        File loginInvalidFile = new File("testCases/inputFiles/login/loginInvalid.txt");
        loginInvalid(loginInvalidFile);
    }

    // @Test
    // public static void loginInvalid(File file)
    // {
    //     String inputLine = "";        
        
    //     try
    //     {
    //         Scanner scan = new Scanner(file);
    //         while(scan.hasNext())
    //         {
    //             inputLine = scan.nextLine();
    //             System.out.println(inputLine);
    //         }
    //         scan.close();
    //     }
    //     catch(Exception e)
    //     {
    //         System.out.println("Error");
    //     }
    // }

    @Test
    public static void loginInvalid(File file) throws FileNotFoundException
    {
        System.out.println("Test #1: loginInvalid");
        PrintStream originalOut = System.out;
        PrintStream fileOut = new PrintStream("testCases/actualOutputFiles/login/loginInvalid/actualTO.txt");
        System.setOut(fileOut);
        String[] args = null;
        final InputStream originalIn = System.in;
        final FileInputStream fips = new FileInputStream(file);
        System.setIn(fips);
        main.main(args);
        System.setIn(originalIn);
        System.setOut(originalOut);
    }
}
