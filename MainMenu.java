/* 
Name: Chelsea Wong & Annie Wong
Teacher: Ms. Basaraba
Date: January 18th, 2022
Description: This program draws the MainMenu screen for our Crossword game. 
             This screen is where the user navigates between options to go
             to other parts/screens of the game. From here, the user can go
             to the instructions screen, play the game, leaderboard screen,
             and exit screen.
ISP: Crossword
*/

import java.awt.*; //gives access to java graphic related commands (ie. fonts and colors)
import hsa.*; //gives access to hsa commands

public class MainMenu //displays screen where the user navigates between options to go to the instructions, game, leaderboard, and exit
{
    Console c; //declaration of instance variable of the Console class; the output console
    char status; //character variable to store the current status 
    
    // imports the font files   
    Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-Regular.ttf"));
    Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-Bold.ttf"));
    Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-Italic.ttf"));
    Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-BoldItalic.ttf"));  

    // declaration of font constants [variables are in full uppercase since they are constants are do not change throughout the program]
    final Font TITLE = new Font ("Montserrat Bold", Font.PLAIN, 48); // font for title text for this screen
    final Font BODY = new Font ("Montserrat Regular", Font.PLAIN, 30); // font for body text for this screen
    final Font SMALL = new Font ("Montserrat Regular", Font.PLAIN, 12); // font for small text for this screen
    
    // declaration of color constants [variables are in full uppercase since they are constants are do not change throughout the program]        
    final Color LPURPLE = new Color(232,231,252);
    final Color MPURPLE = new Color(138, 138, 223);
    final Color DPURPLE = new Color(87,88,208); 
    final Color WHITE = new Color(255,255,255); 
    final Color PWHITE = new Color(246, 245, 254);
        
    public MainMenu (Console con){ //constructor for the class MainMenu
        c = con; //to use the Console object passed as a parameter when creating an instance of this class
        status = 'i';
    }
    
    //methods to draw the brackets and options so it is easier when recoloring and covering when navigating between options
    public void arrowI(){
        c.drawString("<", 257, 428);  
        c.drawString(">", 465, 428);           
    }
    public void arrowP(){
        c.drawString("<", 315, 464); 
        c.drawString(">", 406, 464);            
    }
    public void arrowL(){
        c.drawString("<", 255, 500);    
        c.drawString(">", 467, 500);         
    }
    public void arrowE(){
        c.drawString("<", 318, 535); 
        c.drawString(">", 402, 535);            
    }
    public void textI(){
        c.drawString("instructions", 280, 428);    
    }
    public void textP(){
        c.drawString("play", 338, 463);    
    }
    public void textL(){
        c.drawString("leaderboard", 278, 499);    
    }
    public void textE(){
        c.drawString("exit", 341, 534);    
    }
    
    public void mainMenu (){
        c.clear();
        
        //drawing the background [used across all screens]
        c.setColor(DPURPLE); 
        c.fillRoundRect(50,50,632,572,20,20);
        c.setColor(LPURPLE);        
        c.fillRoundRect(54,54,624,564,16,16);  
        
        //the title        
        c.setColor(PWHITE);
        c.fillRoundRect(222,362,292,25,2,2); //the highlight/underline for the title
        c.setColor(DPURPLE);
        c.setFont(TITLE);
        c.drawString("word cross", 230, 375);

        //the options
        c.setFont(BODY);
        c.setColor(MPURPLE);
        c.drawString("instructions", 280, 428);
        c.drawString("play", 338, 463);
        c.drawString("leaderboard", 278, 499);
        c.drawString("exit", 341, 534);
        
        //instructions on keys        
        c.setFont(SMALL);  
        c.setColor(DPURPLE);
        c.drawString("press [<] and [>] to navigate between the options, then press [enter] to select", 139, 591);
      
        c.setFont(BODY);
       
        while(true){
            if(status == 'i'){ //if status at instructions
                c.setColor(LPURPLE);            
                arrowP(); //cover p
                arrowE(); //cover e
                c.setColor(MPURPLE);
                textP(); //lighten p   
                textE(); //lighten e             
                c.setColor(DPURPLE);
                arrowI(); //draw i
                textI(); //darken i              
            }
            else if(status == 'p'){ //if status at play
                c.setColor(LPURPLE);   
                arrowI(); //cover i
                arrowL(); //cover l
                c.setColor(MPURPLE);
                textI(); //lighten i   
                textL(); //lighten l                 
                c.setColor(DPURPLE);  
                arrowP(); //draw p
                textP(); //darken p
            }
            else if(status == 'l'){ //if status at leaderboard
                c.setColor(LPURPLE);            
                arrowP(); //cover p            
                arrowE(); //cover e
                c.setColor(MPURPLE);
                textP(); //lighten p   
                textE(); //lighten e                 
                c.setColor(DPURPLE);            
                arrowL(); //draw l
                textL(); //darken l                
            }
            else if(status == 'e'){ //if status at exit
                c.setColor(LPURPLE);  
                arrowI(); //cover i
                arrowL(); //cover l
                c.setColor(MPURPLE);
                textI(); //lighten i   
                textL(); //lighten l                   
                c.setColor(DPURPLE);            
                arrowE(); //draw e
                textE(); //darken e                
            }
            int input = c.getChar(); //user input key, gets keycode as integer
            if(input == 10){ //if enter is pressed
                break;
            }
            else if (input == 60 || input == 62){ //if either the [<] or [>] is pressed
                if(status == 'i' && input == 60){ //if status is currently at instructions, and [<] is pressed, move status to exit
                    status = 'e';
                    continue;
                }
                if(status == 'i' && input == 62){ //if status is currently at instructions, and [>] is pressed, move status to play
                    status = 'p';
                    continue;
                }
                if(status == 'p' && input == 60){ //if status is currently at play, and [<] is pressed, move status to instructions
                    status = 'i';
                    continue;
                }
                if(status == 'p' && input == 62){ //if status is currently at play, and [>] is pressed, move status to leaderboard
                    status = 'l';
                    continue;
                }
                if(status == 'l' && input == 60){ //if status is currently at leaderboard, and [<] is pressed, move status to play
                    status = 'p';
                    continue;
                }
                if(status == 'l' && input == 62){ //if status is currently at leaderboard, and [>] is pressed, move status to exit
                    status = 'e';
                    continue;
                }
                if(status == 'e' && input == 60){ //if status is currently at exit, and [<] is pressed, move status to leaderboard 
                    status = 'l';
                    continue;
                }
                if(status == 'e' && input == 62){ //if status is currently at exit, and [>] is pressed, move status to instructions
                    status = 'i';
                    continue;
                }
            } else { 
                new Message ("Invalid input. Please only use [<] and [>] to navigate between options and [enter] to select the option.");
            }
        }
    }
} // MainMenu class
