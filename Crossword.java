/* 
Name: Chelsea Wong & Annie Wong
Teacher: Ms. Basaraba
Date: January 18th, 2022
Description: Main class for the ISP, which utilizes all the other classes. 
ISP: Crossword
ISP description: Word Cross is our take on the classic crossword puzzle! It is a crossword 
                 puzzle game with 5 puzzles randomly chosen. Our game has two modes, timed 
                 and untimed, where a successful timed attempt ends up on the leaderboard.
                 Our leaderboard displays the top 10 highest scores for all the puzzles and
                 for each individual puzzle (a, b, c, d, and e). 
                 There are also instructions that can be read to understand more about how 
                 to play our game.
*/

import java.awt.*; //gives access to java graphic related commands (ie. fonts and colors)
import java.io.*; //gives access to the java input and output commands
import hsa.Console; //gives access to Console class of the hsa library

public class Crossword{ //Crossword class

    //instance variable declarations
    Console c;
    SplashScreen s;
    MainMenu m;
    Instructions i;
    Leaderboard l;  
    Game g;
    Goodbye e;

    public Crossword () { //class constructor
        c = new Console(33, 90, "Word Cross"); //creates new instance of the Console object with the title "Word Cross" and the specified size - 33 rows by 90 columns (660px by 720px)
    }

    public void run () throws IOException{ //run method
        s = new SplashScreen(c); //creates new SplashScreen object and runs it
        s.splashScreen();
        m = new MainMenu(c); //creates new MainMenu object
        while(true){ //while loop that continues until the user wishes to exit
            m.mainMenu(); //runs main menu
            if(m.status == 'i'){ //if instructions is selected
                i = new Instructions(c); ////creates new Instructions object and runs it
                i.instructions();
            }
            else if(m.status == 'l'){ //if leaderboard is selected
                l = new Leaderboard(c); //creates new Leaderboard object and runs it
                l.leaderboard();
            }
            else if(m.status == 'p'){ //if play is selected
                g = new Game(c); //creates new Game object and runs it
                g.start();
            }
            else if(m.status == 'e'){ //if exit is selected
                e = new Goodbye(c); //creates new Goodbye object and runs it
                e.goodbye();
                break; //breaks out of while loop, program ends
            }
        }
    }
        
    public static void main (String args[]) throws IOException{ //main method
        Crossword c = new Crossword(); //declares and creates new instance of the Crossword object
        c.run(); 
    }
} //Crossword class close
