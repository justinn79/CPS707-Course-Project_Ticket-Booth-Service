import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.FileReader;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class test {
    public static void main(String[] args) throws Exception
    {
        // loginInvalid();
        // loginValid();
        logoutNoTransaction();
        // logoutValidLogout();
        // createStoreUserCredentials();
        // createAdminPrivilege();
        // createUniqueUser();
        // deleteTransactionFile();
        // deleteAdmin();
        // deleteCurrentUser();
    }

    // A helper function that creates the actual output files and compares them with the expected output files
    public static void testHelper(String inputFilePath, String actualTOPath, String actualCUAFPath, String actualATFPath, String actualDTFPath, 
    String expectedTOPath, String expectedCUAFPath, String expectedATFPath, String expectedDTFPath) throws IOException, Exception
    {
        // Change System.out to our actualTO.txt file
        PrintStream originalOut = System.out;
        PrintStream fileOut = new PrintStream(actualTOPath);
        System.setOut(fileOut);
        
        // Run main and change System.in to our input file
        String[] args = null;
        InputStream originalIn = System.in;
        FileInputStream inputFile = new FileInputStream(new File(inputFilePath));
        System.setIn(inputFile);
        main.main(args);
        
        //Set System.in and System.out back to normal
        System.setIn(originalIn);
        System.setOut(originalOut);
        
        // Copy the files from the txtfiles directory into the actualOutputFiles directory
        Path originalCUAF = Paths.get("txtfiles/currentUsersAccountsFile.txt");
        Path actualCUAF = Paths.get(actualCUAFPath);
        Files.copy(originalCUAF, actualCUAF, StandardCopyOption.REPLACE_EXISTING);

        Path originalATF = Paths.get("txtfiles/availableTicketsFile.txt");
        Path actualATF = Paths.get(actualATFPath);
        Files.copy(originalATF, actualATF, StandardCopyOption.REPLACE_EXISTING);

        Path originalDTF = Paths.get("txtfiles/dailyTransactionFile.txt");
        Path actualDTF = Paths.get(actualDTFPath);
        Files.copy(originalDTF, actualDTF, StandardCopyOption.REPLACE_EXISTING);

        // Making Readers to compare the expected output files with the actual output files
        Path actualTO = Paths.get(actualTOPath);
        Reader actualTOReader = new BufferedReader(new FileReader(actualTO.toFile()));
        Reader actualCUAFReader = new BufferedReader(new FileReader(actualCUAF.toFile()));
        Reader actualATFReader = new BufferedReader(new FileReader(actualATF.toFile()));
        Reader actualDTFReader = new BufferedReader(new FileReader(actualDTF.toFile()));

        Path expectedTO = Paths.get(expectedTOPath);
        Reader expectedTOReader = new BufferedReader(new FileReader(expectedTO.toFile()));
        Path expectedCUAF = Paths.get(expectedCUAFPath);
        Reader expectedCUAFReader = new BufferedReader(new FileReader(expectedCUAF.toFile()));
        Path expectedATF = Paths.get(expectedATFPath);
        Reader expectedATFReader = new BufferedReader(new FileReader(expectedATF.toFile()));
        Path expectedDTF = Paths.get(expectedDTFPath);
        Reader expectedDTFReader = new BufferedReader(new FileReader(expectedDTF.toFile()));

        // Compare the expected output files with the actual output files
        try
        {
            Assert.assertTrue(IOUtils.contentEqualsIgnoreEOL(expectedTOReader, actualTOReader));
            System.out.println("Success: TO");
        }
        catch(AssertionError e)
        {
            System.out.println("Failed: TO");
        }
        try
        {
            Assert.assertTrue(IOUtils.contentEqualsIgnoreEOL(expectedCUAFReader, actualCUAFReader));
            System.out.println("Success: CUAF");
        }
        catch(AssertionError e)
        {
            System.out.println("Failed: CUAF");
        }
        try
        {
            Assert.assertTrue(IOUtils.contentEqualsIgnoreEOL(expectedATFReader, actualATFReader));
            System.out.println("Success: ATF");
        }
        catch(AssertionError e)
        {
            System.out.println("Failed: ATF");
        }
        try
        {
            Assert.assertTrue(IOUtils.contentEqualsIgnoreEOL(expectedDTFReader, actualDTFReader));
            System.out.println("Success: DTF");
        }
        catch(AssertionError e)
        {
            System.out.println("Failed: DTF");
        }
    }

    @Test
    public static void loginInvalid() throws Exception
    {
        System.out.println("Test #1: loginInvalid");
        
        testHelper("testCases/inputFiles/login/loginInvalid.txt", 
        "testCases/actualOutputFiles/login/loginInvalid/actualTO.txt", 
        "testCases/actualOutputFiles/login/loginInvalid/actualCUAF.txt", 
        "testCases/actualOutputFiles/login/loginInvalid/actualATF.txt", 
        "testCases/actualOutputFiles/login/loginInvalid/actualDTF.txt", 
        "testCases/expectedOutputFiles/login/loginInvalid/expectedTO.txt", 
        "testCases/expectedOutputFiles/login/loginInvalid/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/login/loginInvalid/expectedATF.txt", 
        "testCases/expectedOutputFiles/login/loginInvalid/expectedDTF.txt");
    }

    @Test
    public static void loginValid() throws Exception
    {
        System.out.println("\nTest #2: loginValid");
        
        testHelper("testCases/inputFiles/login/loginValid.txt", 
        "testCases/actualOutputFiles/login/loginValid/actualTO.txt", 
        "testCases/actualOutputFiles/login/loginValid/actualCUAF.txt", 
        "testCases/actualOutputFiles/login/loginValid/actualATF.txt", 
        "testCases/actualOutputFiles/login/loginValid/actualDTF.txt", 
        "testCases/expectedOutputFiles/login/loginValid/expectedTO.txt", 
        "testCases/expectedOutputFiles/login/loginValid/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/login/loginValid/expectedATF.txt", 
        "testCases/expectedOutputFiles/login/loginValid/expectedDTF.txt");
    }

    @Test
    public static void logoutNoTransaction() throws Exception
    {
        System.out.println("\nTest #3: logoutNoTransaction");
        
        testHelper("testCases/inputFiles/logout/logoutNoTransaction.txt", 
        "testCases/actualOutputFiles/logout/logoutNoTransaction/actualTO.txt", 
        "testCases/actualOutputFiles/logout/logoutNoTransaction/actualCUAF.txt", 
        "testCases/actualOutputFiles/logout/logoutNoTransaction/actualATF.txt", 
        "testCases/actualOutputFiles/logout/logoutNoTransaction/actualDTF.txt", 
        "testCases/expectedOutputFiles/logout/logoutNoTransaction/expectedTO.txt", 
        "testCases/expectedOutputFiles/logout/logoutNoTransaction/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/logout/logoutNoTransaction/expectedATF.txt", 
        "testCases/expectedOutputFiles/logout/logoutNoTransaction/expectedDTF.txt");
    }

    @Test
    public static void logoutValidLogout() throws Exception
    {
        System.out.println("\nTest #4: logoutValidLogout");
        
        testHelper("testCases/inputFiles/logout/logoutValidLogout.txt", 
        "testCases/actualOutputFiles/logout/logoutValidLogout/actualTO.txt", 
        "testCases/actualOutputFiles/logout/logoutValidLogout/actualCUAF.txt", 
        "testCases/actualOutputFiles/logout/logoutValidLogout/actualATF.txt", 
        "testCases/actualOutputFiles/logout/logoutValidLogout/actualDTF.txt", 
        "testCases/expectedOutputFiles/logout/logoutValidLogout/expectedTO.txt", 
        "testCases/expectedOutputFiles/logout/logoutValidLogout/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/logout/logoutValidLogout/expectedATF.txt", 
        "testCases/expectedOutputFiles/logout/logoutValidLogout/expectedDTF.txt");
    }

    @Test
    public static void createStoreUserCredentials() throws Exception
    {
        System.out.println("\nTest #5: createStoreUserCredentials");
        
        testHelper("testCases/inputFiles/create/createStoreUserCredentials.txt", 
        "testCases/actualOutputFiles/create/createStoreUserCredentials/actualTO.txt", 
        "testCases/actualOutputFiles/create/createStoreUserCredentials/actualCUAF.txt", 
        "testCases/actualOutputFiles/create/createStoreUserCredentials/actualATF.txt", 
        "testCases/actualOutputFiles/create/createStoreUserCredentials/actualDTF.txt", 
        "testCases/expectedOutputFiles/create/createStoreUserCredentials/expectedTO.txt", 
        "testCases/expectedOutputFiles/create/createStoreUserCredentials/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/create/createStoreUserCredentials/expectedATF.txt", 
        "testCases/expectedOutputFiles/create/createStoreUserCredentials/expectedDTF.txt");
    }

    @Test
    public static void createAdminPrivilege() throws Exception
    {
        System.out.println("\nTest #6: createAdminPrivilege");
        
        testHelper("testCases/inputFiles/create/createAdminPrivilege.txt", 
        "testCases/actualOutputFiles/create/createAdminPrivilege/actualTO.txt", 
        "testCases/actualOutputFiles/create/createAdminPrivilege/actualCUAF.txt", 
        "testCases/actualOutputFiles/create/createAdminPrivilege/actualATF.txt", 
        "testCases/actualOutputFiles/create/createAdminPrivilege/actualDTF.txt", 
        "testCases/expectedOutputFiles/create/createAdminPrivilege/expectedTO.txt", 
        "testCases/expectedOutputFiles/create/createAdminPrivilege/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/create/createAdminPrivilege/expectedATF.txt", 
        "testCases/expectedOutputFiles/create/createAdminPrivilege/expectedDTF.txt");
    }

    @Test
    public static void createUniqueUser() throws Exception
    {
        System.out.println("\nTest #7: createUniqueUser");
        
        testHelper("testCases/inputFiles/create/createUniqueUser.txt", 
        "testCases/actualOutputFiles/create/createUniqueUser/actualTO.txt", 
        "testCases/actualOutputFiles/create/createUniqueUser/actualCUAF.txt", 
        "testCases/actualOutputFiles/create/createUniqueUser/actualATF.txt", 
        "testCases/actualOutputFiles/create/createUniqueUser/actualDTF.txt", 
        "testCases/expectedOutputFiles/create/createUniqueUser/expectedTO.txt", 
        "testCases/expectedOutputFiles/create/createUniqueUser/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/create/createUniqueUser/expectedATF.txt", 
        "testCases/expectedOutputFiles/create/createUniqueUser/expectedDTF.txt");
    }

    @Test
    public static void deleteTransactionFile() throws Exception
    {
        System.out.println("\nTest #8: deleteTransactionFile");
        
        testHelper("testCases/inputFiles/delete/deleteTransactionFile.txt", 
        "testCases/actualOutputFiles/delete/deleteTransactionFile/actualTO.txt", 
        "testCases/actualOutputFiles/delete/deleteTransactionFile/actualCUAF.txt", 
        "testCases/actualOutputFiles/delete/deleteTransactionFile/actualATF.txt", 
        "testCases/actualOutputFiles/delete/deleteTransactionFile/actualDTF.txt", 
        "testCases/expectedOutputFiles/delete/deleteTransactionFile/expectedTO.txt", 
        "testCases/expectedOutputFiles/delete/deleteTransactionFile/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/delete/deleteTransactionFile/expectedATF.txt", 
        "testCases/expectedOutputFiles/delete/deleteTransactionFile/expectedDTF.txt");
    }

    @Test
    public static void deleteAdmin() throws Exception
    {
        System.out.println("\nTest #9: deleteAdmin");
        
        testHelper("testCases/inputFiles/delete/deleteAdmin.txt",
        "testCases/actualOutputFiles/delete/deleteAdmin/actualTO.txt", 
        "testCases/actualOutputFiles/delete/deleteAdmin/actualCUAF.txt", 
        "testCases/actualOutputFiles/delete/deleteAdmin/actualATF.txt", 
        "testCases/actualOutputFiles/delete/deleteAdmin/actualDTF.txt", 
        "testCases/expectedOutputFiles/delete/deleteAdmin/expectedTO.txt", 
        "testCases/expectedOutputFiles/delete/deleteAdmin/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/delete/deleteAdmin/expectedATF.txt", 
        "testCases/expectedOutputFiles/delete/deleteAdmin/expectedDTF.txt");
    }

    @Test
    public static void deleteCurrentUser() throws Exception
    {
        System.out.println("\nTest #10: deleteCurrentUser");
        
        testHelper("testCases/inputFiles/delete/deleteCurrentUser.txt",
        "testCases/actualOutputFiles/delete/deleteCurrentUser/actualTO.txt", 
        "testCases/actualOutputFiles/delete/deleteCurrentUser/actualCUAF.txt", 
        "testCases/actualOutputFiles/delete/deleteCurrentUser/actualATF.txt", 
        "testCases/actualOutputFiles/delete/deleteCurrentUser/actualDTF.txt", 
        "testCases/expectedOutputFiles/delete/deleteCurrentUser/expectedTO.txt", 
        "testCases/expectedOutputFiles/delete/deleteCurrentUser/expectedCUAF.txt", 
        "testCases/expectedOutputFiles/delete/deleteCurrentUser/expectedATF.txt", 
        "testCases/expectedOutputFiles/delete/deleteCurrentUser/expectedDTF.txt");
    }
}
