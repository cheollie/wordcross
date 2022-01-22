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
            
    public Timer(Console con) throws java.awt.FontFormatException, java.io.IOException { //class constructor
        mins = 0;
        secs = 0;
        stop = false;
        c = con;
        loadFonts();        
    }
        
    public void run(){ //run method
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
            c.setColor(new Color(87,88,208));
            c.setFont(new Font("Montserrat Regular", Font.PLAIN, 20));
            if (secs < 10) c.drawString(mins + ":0" + secs, 355, 74); // if seconds is less than 10, add a 0 in front of it
            else c.drawString(mins + ":" + secs, 355, 74);
            try{
                Thread.sleep(1000); //1 second delay between incrementing
            } catch(InterruptedException e){}
            //erasing
            c.setColor(new Color(232,231,252));
            c.fillRect(350, 55, 60, 20);
        }
    }
}
