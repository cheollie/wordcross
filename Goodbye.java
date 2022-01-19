/* 
Name: Chelsea Wong & Annie Wong
Teacher: Ms. Basaraba
Date: January 18th, 2022
Description: This program draws the Goodbye screen for the Crossword game. 
             This screen is the screen right before the user presses a key 
             to close the game/window. 
ISP: Crossword
*/

import java.awt.*; //gives access to java command library
import hsa.Console; //gives access to the Console class from the hsa library

public class Goodbye{ //the Goodbye class, draws the screen right before the user presses a key to close the game/window

    Console c; //declaration of instance variable of the Console class; the output console
          
    // declaration of font constants [variables are in full uppercase since they are constants are do not change throughout the program]
    final Font TITLE = new Font ("Montserrat", Font.BOLD, 42); // font for title text for this screen
    final Font BODY = new Font ("Montserrat", Font.PLAIN, 20); // font for body text for this screen
    final Font SMALL = new Font ("Montserrat", Font.PLAIN, 12); // font for small text for this screen 
    
    // declaration of color constants [variables are in full uppercase since they are constants are do not change throughout the program]
    final Color LPURPLE = new Color(232,231,252);
    final Color MPURPLE = new Color(138, 138, 223);
    final Color DPURPLE = new Color(87,88,208); 
    final Color WHITE = new Color(255,255,255); 
    final Color PWHITE = new Color(246, 245, 254);    
    
    public Goodbye (Console con){ //constructor for the class Goodbye
        c = con; //to use the Console object passed as a parameter when creating an instance of this class
    }
    
    public void goodbye(){
        c.clear(); //clear the screen
        
        //drawing the background [used across all screens]
        c.setColor(DPURPLE); 
        c.fillRoundRect(50,50,632,572,20,20);
        c.setColor(LPURPLE);        
        c.fillRoundRect(54,54,624,564,16,16);  
        
        //the title
        c.setColor(PWHITE);
        c.fillRoundRect(114,312,505,27,2,2); //the highlight/underline for the title
        c.setColor(DPURPLE);
        c.setFont(TITLE);
        c.drawString("thank you for playing!",124,324);
        
        //main text
        c.setFont(BODY);
        c.drawString("Word Cross programmed by:",223,372);
        c.drawString("Annie Wong & Chelsea Wong", 220,396);
        
        //instructions on keys
        c.setFont(SMALL);
        c.drawString("press any key to close the game",464,597); //instructions on bottom right
      
        c.getChar(); //waits for key press to know when to close the window
        
        try{ 
            Thread.sleep(1000); // 1 second delay before closing the window
        }
        catch(Exception e){}
        
        c.close(); //close the window
    } 
} // Goodbye class
