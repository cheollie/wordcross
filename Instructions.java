/* 
Name: Chelsea Wong & Annie Wong
Teacher: Ms. Basaraba
Date: January 18th, 2022
Description: This program draws the Instructions screen for the Crossword game. 
             This screen displays the instructions for the game for the user.
             This screen can be accessed from the main menu.
ISP: Crossword
*/

import java.awt.*; //gives access to java graphic related commands (ie. fonts and colors)
import hsa.Console; //gives access to Console class of the hsa library

public class Instructions //displays the instructions for the game for the user
{
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
    
    public Instructions (Console con){ //constructor for the class Instructions
        c = con; //to use the Console object passed as a parameter when creating an instance of this class
    }
    
    public void instructions (){
        c.clear();

        //drawing the background [used across all screens]
        c.setColor(DPURPLE); 
        c.fillRoundRect(50,50,632,572,20,20);
        c.setColor(LPURPLE);        
        c.fillRoundRect(54,54,624,564,16,16);  
        
        //the title         
        c.setColor(PWHITE);
        c.fillRoundRect(203,159,327,27,2,2);  //the highlight/underline for the title
        c.setColor(DPURPLE);
        c.setFont(TITLE);
        c.drawString("instructions",237,174);
        
        //the body text (the actual instructions)
        c.setFont(BODY);
        c.drawString("Welcome to Word Cross!",242,231);
        c.drawString("This is a game where you can test your", 173,265);
        c.drawString("vocabulary and quick thinking.",212,289);
        
        //instructions on keys        
        c.setFont(SMALL);
        c.drawString("press any key to return to the main menu",406,597);

        c.getChar(); //waits for key press to know when to go back to the main menu
    }
} // Instructions class
