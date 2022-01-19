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
    final Font BODY = new Font ("Montserrat", Font.PLAIN, 16); // font for body text for this screen
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
        c.drawString("Welcome to Word Cross!",266,222); //paragraph 1
        c.drawString("This is a game where you can test your vocabulary and quick", 123,252); //paragraph 2
        c.drawString("thinking. The objective of the game is to fill a board up with",128,272);
        c.drawString("words given clues for those words. The given board is used to",122,292);
        c.drawString("input the words you guess for the clues.",206,312);
        c.drawString("There are two game modes, timed and untimed. For the timed",114,342); //paragraph 3
        c.drawString("mode, you are timed, which will reflect your score that will",135,362);
        c.drawString("show up in the leaderboard if you complete it. Additionally, you",115,382);
        c.drawString("are given an option for hints if you get stuck, however, it adds",122,402);
        c.drawString("60 secs to your time. For the untimed mode, you are not timed",116,422);
        c.drawString("and will not show up on the leaderboard even if you finish it.",124,442);
        c.drawString("To use the board, [<] and [>] can be used to navigate between",121,472); //paragraph 4
        c.drawString("words, [?] is used to show a hint (for timed mode) and [*] is to",123,492);
        c.drawString("give up.",336,512);
        c.drawString("Have fun and enjoy the game!",247,542); //paragraph 5
        
        //instructions on keys        
        c.setFont(SMALL);
        c.drawString("press any key to return to the main menu",406,597);

        c.getChar(); //waits for key press to know when to go back to the main menu
    }
} // Instructions class
