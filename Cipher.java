import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Cipher
{
    private static boolean encrypt;
    private static boolean decrypt;
    private static boolean fromFile;
    private static boolean textMessage;
    private static boolean validText;
    
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
      encryptedMessage = "";
      decryptedMessage = "";
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
      String words = "";
      validText = false;
      
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
      
      if(textMessage)
      {
          System.out.println("Please enter shift value");
          int tempNumber = -999;
          int count = 0;
          while(tempNumber == -999)
          {
              if(count == 0) input.nextLine();
              count++;
              if(input.hasNextInt())
              {
                  tempNumber = input.nextInt();
                  if(tempNumber >= -26 && tempNumber <= 26)
                  {
                      shiftValue = tempNumber;
                  }
                  else
                  {
                      tempNumber = -999;
                      input.nextLine();
                      System.out.println("bad input\nPlease enter shift value");
                  }
                 
              }
              else
              {
                  tempNumber = -999;
                  input.nextLine();
                  System.out.println("bad input\nPlease enter shift value");
              }
              
          }
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
      validText = false;
          //if want to get info from file
          if(fromFile)
          {
              while(!validText)
              {
                  System.out.println("What is the name of the file?");
                  String fileName = input.next();
                  file = new File(fileName);
                  fileReader = new Scanner(file);
                  while(fileReader.hasNext() && !fileReader.hasNextInt())
                  {
                      decryptedMessage += fileReader.nextLine();
                  }
                  validText = true;
                  
                  if(decryptedMessage == null)
                  {
                      System.out.println("You did not enter a valid message");
                      return;
                  }
                  char[] messageArray = decryptedMessage.toCharArray();
                  for(int i = 0; i < messageArray.length; i++)
                  {
                      if(!((messageArray[i] >= 'A' && messageArray[i] <= 'Z') || (messageArray[i] >= 'a' && messageArray[i] <= 'z') || messageArray[i] == ' '))
                      {
                          System.out.println("you did not enter a valid message. There was an unexpected character at location: " + i);
                          validText = false;
                          System.exit(0);
                          break;
                      }
                      if(validText == false) break;
                  }//end for loop
                  
                  if(validText == false) break;
                  
                  if(fileReader.hasNextInt())
                  {
                      
                      shiftValue = fileReader.nextInt();
                      if(!(shiftValue >= 26 && shiftValue <= 26))
                      {
                          System.out.println("You did not enter a vlid shift value.");
                          validText = false;
                          System.exit(0);
                          break;
                      }
                  }
                  
                  if(validText == false)
                  {
                      System.out.println("The file is not formatted properly. The next line does not contain a number");
                      System.exit(0);
                      break;
                  }
              }//end while
          }//end if from file
          
          //if want to get message by entering in text
          if(textMessage)
          {
              int count = 0;
              while(!validText)
              {
                  System.out.println("What is the text?");
                  if(count == 0) input.nextLine();
                  count++;
                  decryptedMessage = input.nextLine();
                  validText = true;
                  char[] messageArray = decryptedMessage.toCharArray();
                  
                  for(int i = 0; i < messageArray.length; i++)
                  {
                      if(!((messageArray[i] >= 'A' && messageArray[i] <= 'Z') || (messageArray[i] >= 'a' && messageArray[i] <= 'z') || messageArray[i] == ' '))
                      {
                          System.out.println("you did not enter a valid message. There was an unexpected character at location: " + i);
                          validText = false;
                          break;
                      }
                  }
              }
          }
          
          //sets decrypted message to encrypted without spaces and in all caps
         // encryptedMessage = decryptedMessage;
          encryptedMessage = decryptedMessage.replaceAll(" ", "");
          encryptedMessage = encrypt(encryptedMessage, shiftValue);
  }
  
  
  /****
   *   This is called if Decryption is passed as input, reads in file and set
   *   encryption and decryption string values.
   * @throws FileNotFoundException
   */
  private static void DecryptionMode() throws FileNotFoundException
  {
      File file;
      validText = false;
      //if want to get info from file
      if(fromFile)
      {
          while(validText == false)
          {
              System.out.println("What is the name of the file?");
              String fileName = input.next();
              file = new File(fileName);
              fileReader = new Scanner(file);
              
              while(fileReader.hasNext())
              {
                  encryptedMessage += fileReader.nextLine();
              }
              
              validText = true;           
              char[] messageArray = encryptedMessage.toCharArray();
              for(int i = 0; i < messageArray.length ; i++)
              {
                  if(messageArray[i] == ' ')
                  {
                      System.out.println("you did not enter a valid message. There was a blank at location: " + i);
                      validText = false;
                      break;
                  }
                  
                  if(!((messageArray[i] >= 'A' && messageArray[i] <= 'Z') || (messageArray[i] >= 'a' && messageArray[i] <= 'z')))
                  {
                      System.out.println("you did not enter a valid message. There was an unexpected character at location: " + i);
                      validText = false;
                      break;
                  }
              }
          }
          
      }
      
      
      
      //if want to get message by entering in text
      if(textMessage)
      {
          int count = 0;
          while(validText == false)
          {
              System.out.println("What is the text?");
              if(count == 0) input.nextLine();
              count++;
              encryptedMessage = input.nextLine();
              validText = true;
              
              char[] messageArray = encryptedMessage.toCharArray();
              for(int i = 0; i < messageArray.length ; i++)
              {
                  if(messageArray[i] == ' ')
                  {
                      System.out.println("you did not enter a valid message. There was a blank at location: " + i);
                      validText = false;
                      break;
                  }
                  
                  if(!((messageArray[i] >= 'A' && messageArray[i] <= 'Z') || (messageArray[i] >= 'a' && messageArray[i] <= 'z')))
                  {
                      System.out.println("you did not enter a valid message. There was an unexpected character at location: " + i);
                      validText = false;
                      break;
                  }
              }
          }
      }
      
      decryptedMessage = decrypt(encryptedMessage, shiftValue);  
  }
  
  /*
   * Dncrypts the message based on user input.
   * Only does this  if the input is valid.
   */
  private static String encrypt(String message, int shiftValue)
  {
      char temp = ' ';
      int tempVal = 0;
      message = message.toUpperCase();
      String encryptedMessage = "";
      
      for(int place = 0; place < message.length(); place++)
      {
      //    System.out.println(message.charAt(place));
          if(((char)message.charAt(place) + (shiftValue%26)) < 'A')
          {
            //  System.out.println("in first if");
              tempVal ='A' -  (message.charAt(place) + (shiftValue%26));
              //System.out.println((char)tempVal);
              temp = (char) ('Z' - tempVal + 1);
          }
          
          else if(((char)message.charAt(place) + (shiftValue%26)) > 'Z')
          {
            //  System.out.println("in second if");
              tempVal = (message.charAt(place) + (shiftValue%26)) - 'Z';
              temp = (char)('A' + tempVal - 1);
          }
          else
          {
            //  System.out.println("in else");
              temp = (char) (message.charAt(place)+ (shiftValue%26));
          }
          
          encryptedMessage += temp;
      }
      
      encryptedMessage = encryptedMessage.toUpperCase();
      
      return encryptedMessage;
  }
  
  /*
   * Decrypts the message based on user input.
   * Only does this if the input is valid.
   */
  private static String decrypt(String message, int shiftValue)
  {
      message = message.toUpperCase();
      String decryptedMessage = "";
      int tempVal = 0;
      char temp = '-';
      
      for(int place = 0; place < message.length(); place++)
      {
          if(((char)message.charAt(place) - (shiftValue%26)) < 'A')
          {
              tempVal ='A' -  (message.charAt(place) - (shiftValue%26));
              System.out.println((char)tempVal);
              temp = (char) ('Z' - tempVal + 1);
          }
          
          else if(((char)message.charAt(place) - (shiftValue%26)) > 'Z')
          {
              tempVal = (message.charAt(place) - (shiftValue%26)) - 'Z';
              temp = (char)('A' + tempVal - 1);
          }
          else
          {
              temp = (char) (message.charAt(place) - (shiftValue%26));
          }
          
          decryptedMessage += temp;
      }
      
      return decryptedMessage;  
  }
  
}
