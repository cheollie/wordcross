/* 
Name: Chelsea Wong & Annie Wong
Teacher: Ms. Basaraba
Date: January 18th, 2022
Description: Timer thread class used to create and display a timer object for the timed
             game mode. 
ISP: Crossword
*/

import java.awt.*; //gives access to java graphic related commands (ie. fonts and colors)
import hsa.Console; //gives access to Console class of the hsa library
import java.io.*; //gives access to the input output commands

class Timer extends Thread //Timer class
{ 
    //declaring class variables
    Console c;
    int mins, secs;
    boolean stop;
        
    // imports the font files 
    public void loadFonts() throws java.awt.FontFormatException, java.io.IOException {
        Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-Regular.ttf"));
        Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-Bold.ttf"));
        Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-Italic.ttf"));
        Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-BoldItalic.ttf"));  
    }

    // declaration of font constants [variables are in full uppercase since they are constants are do not change throughout the program]  
    final Font BODY = new Font("Montserrat Regular", Font.PLAIN, 20);
    
    // declaration of color constants [variables are in full uppercase since they are constants are do not change throughout the program]   
    final Color LPURPLE = new Color(232,231,252);
    final Color DPURPLE = new Color(87,88,208); 

    public Timer(Console con) throws java.awt.FontFormatException, java.io.IOException { //class constructor
        mins = 0;
        secs = 0;
        stop = false;
        c = con;
        loadFonts();        
    }
        
    public void run(){ //run method
        String secsStr, minsStr;
        while(!stop){ //while the timer isn't stopped
            //converting seconds to minutes
            if(secs == 59){
                mins++;
                secs = 0;
            }
            else{
                secs++;
            }
            //displaying the timer
            c.setColor(DPURPLE);
            c.setFont(BODY);
            
            secsStr = (secs < 10) ? "0"+secs : ""+secs; // if seconds is less than 10, add a 0 in front of it
            minsStr = (mins < 10) ? "0"+mins : ""+secs; // if minutes is less than 10, add a 0 in front of it (not necessary but easier for formatting!)
            c.drawString(minsStr + ":" + secsStr, 335, 91);
            try{
                Thread.sleep(1000); //1 second delay between incrementing
            } catch(InterruptedException e){}
            //erasing
            c.setColor(LPURPLE);
            c.fillRect(335, 74, 70, 24);
        }
    }
}