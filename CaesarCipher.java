import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CaesarCipher
{
    private static boolean encrypt;
    private static boolean decrypt;
    private static boolean fromFile;
    private static boolean textMessage;
    
    private static int shiftValue;
    
    private static Scanner fileReader;
    private static Scanner input;
    static String decryptedMessage;
    static String encryptedMessage;
    
  public static void main(String[] args) throws FileNotFoundException
  {
      encrypt = false;
      decrypt = false;
      fromFile = false;
      textMessage = false;
      shiftValue = 0;
      input = new Scanner(System.in);
      
      getInput();
      if(encrypt) EncryptionMode();
      if(decrypt) DecryptionMode(); 
      System.out.println("The plain text message is: " + decryptedMessage);
      System.out.println("The encrypted message is: " + encryptedMessage);
       
      input.close();
       
  }

/****
   *  Gets input from the use and set global booleans to true if that option
   *  was passed
   * 
   */
  private static void getInput()
  {
      String words = null;
      
      //gets encrypt or decrypt input from user
      System.out.println("Do you want to encrypt (E) or decrypt (D)?");
      
      words = input.next();
      while(words.length() != 1 || !(words.equals("E") || words.equals("D")))
      {
          input.nextLine();
          System.out.println("Do you want to encrypt (E) or decrypt (D)?");
          words = input.next();
      }
      
      if(words.equals("E")) encrypt = true;
      else if(words.equals("D")) decrypt = true;
      
      //gets source of input and sets either boolean variable textMessage or fromFile to true
      System.out.println("Do you want to type in the message (T) or read from a text file (F)?");
      String e_input = input.next();
      while(e_input.length() != 1 || (!(e_input.equals("T")) && !(e_input.equals("F"))))
      {
          input.nextLine();
          System.out.println("Do you want to type in the message (T) or read from a text file (F)?");
          e_input = input.next();
      }
      
      if(e_input.equals("T")) textMessage = true;
      if(e_input.equals("F")) fromFile = true;
      
      System.out.println("Please enter shift value");
      if(input.hasNextInt()) shiftValue = input.nextInt();
      else 
      {
          System.out.println("you did not enter a valid number");
      }
      
  }
  
  /****
   *   This is called if Encryption is passed as input, reads in file and set
   *   encryption and decryption string values.
   * @throws FileNotFoundException
   */
  private static void EncryptionMode() throws FileNotFoundException
  {
      File file;
          //if want to get info from file
          if(fromFile == true)
          {
              System.out.println("What is the name of the file?");
              String fileName = input.next();
              file = new File(fileName);
              fileReader = new Scanner(file);
              
              while(fileReader.hasNext())
              {
                  decryptedMessage += fileReader.nextLine();
              }
              
          }
          
          //if want to get message by entering in text
          if(textMessage == true)
          {
              System.out.println("What is the text?");
              input.nextLine();
              decryptedMessage = input.nextLine();
          }
          
          //sets decrypted message to encrypted without spaces and in all caps
          encryptedMessage = decryptedMessage;
          encryptedMessage = encryptedMessage.replaceAll(" ", "");
          encryptedMessage = encryptedMessage.toUpperCase();
  }
  
  
  /****
   *   This is called if Decryption is passed as input, reads in file and set
   *   encryption and decryption string values.
   * @throws FileNotFoundException
   */
  private static void DecryptionMode()
  {
    
    
  }
  
}
