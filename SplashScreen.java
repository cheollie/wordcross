/* 
Name: Chelsea Wong & Annie Wong
Teacher: Ms. Basaraba
Date: January 18th, 2022
Description: This program draws the SplashScreen screen for our Crossword game. 
             This screen is displayed before the main menu and upon the launch
             of the game. This screen draws the name of our game in a crossword
             style which is our ISP.
ISP: Crossword
*/

import java.awt.*; //gives access to java graphic related commands (ie. fonts and colors)
import hsa.Console; //gives access to the Console class of the hsa library

public class SplashScreen //draws the SplashScreen screen where it displays name of our game in a crossword style upon launch of the game
{
    Console c; //declaration of instance variable of the Console class; the output console    
    
    // imports the font files   
    Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-Regular.ttf"));
    Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-Bold.ttf"));
    Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-Italic.ttf"));
    Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-BoldItalic.ttf"));  

    // declaration of font constants [variables are in full uppercase since they are constants are do not change throughout the program]  
    final Font SPLASHSCREEN = new Font ("Montserrat Bold", Font.PLAIN, 42); // font for splash screen text for this screen
    
    // declaration of color constants [variables are in full uppercase since they are constants are do not change throughout the program]   
    final Color LPURPLE = new Color(232,231,252);
    final Color DPURPLE = new Color(87,88,208); 
    final Color WHITE = new Color(255,255,255);     
        
    public SplashScreen(Console con){ //constructor for the class SplashScreen
        c = con; //to use the Console object passed as a parameter when creating an instance of this class
    }
    public void cross(int x, int y, int n){ //method used to draw diagonals on the "blocked" boxes on the board
        //x is the x value of the top left point
        //y is the y value of the top left point
        //n is the width (and height)
        for (int i = 0; i < n; i+=9){ //uses a for loop to draw diagonal lines with a given increment between each line
            c.drawLine(x+i,y,x,y+i); 
            c.drawLine(x+i,y+n,x+n,y+i);
            c.drawLine(x+i+1,y,x,y+i+1); 
            c.drawLine(x+i+1,y+n,x+n,y+i+1); 
        }
    }
    public void splashScreen(){
        c.clear();
        
        //drawing the background [used across all screens]
        c.setColor(DPURPLE); 
        c.fillRoundRect(50,50,632,572,20,20);
        c.setColor(LPURPLE);        
        c.fillRoundRect(54,54,624,564,16,16); 
        
        //draws the shadow and outline of the board
        c.setColor(DPURPLE); 
        c.fillRoundRect(183,189,371,298,6,6);   
        c.fillRoundRect(179,185,371,298,6,6); 
          
        //draws the background of the board
        c.setColor(WHITE);
        c.fillRoundRect(183,189,363,290,2,2);
        
        //drawing the grid on the board
        c.setColor(DPURPLE);
        c.setFont(SPLASHSCREEN);
        int n = 75; //size of each grid/square
        int x = 181, y = 187;        
        boolean[][] filled = new boolean[][] { {true, false, true, true, true}, {true, false, true, true, true}, {false, false, false, false, false}, {true, false, true, true, true}};         
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                if (filled[i][j]){ 
                    cross(j*(n-1)+x,i*(n-1)+y,n-3); //drawing the diagonal patterns on the filled ones
                } 
                c.drawRect(j*(n-1)+x,i*(n-1)+y,n,n);  
                c.drawRect(j*(n-1)+x+1,i*(n-1)+y+1,n-2,n-2);  //thickening the line               
            }
        }     
        
        //animating the letters being typed   
        String alteredTitle = "WORDCOSS";        
        int[][] coords = new int[][] {{268,244},{275,317},{275,390},{274,461}, {203,390}, {349,390}, {428,390}, {499,390}};
        for (int i = 0; i < 8; i++){
            c.drawString(alteredTitle.charAt(i)+"", coords[i][0], coords[i][1]); //write each letter at a time given the coordinates from coords and the letters from alteredTitle
            try {
                Thread.sleep(400); //0.4 second delay between each letter (so it looks like it's typing!)
            } catch (Exception e){}            
        } 
        
        //delay after the letters being typed
        try {
            Thread.sleep(500); //half a second delay after writing the title before fading 
        } catch (Exception e){}          
        
        
        //fading the background by adding layers of translucent white
        c.setColor(new Color(255,255,255,25));
        for (int i = 0; i < 42; i++){
            c.fillRect(0,0,720,660); //draws the rectangle
            try {
                Thread.sleep(20); //20 ms delay between adding each layer of translucent white
            } catch (Exception e){}            
        }
        c.clear();        
    }
} // SplashScreen class
