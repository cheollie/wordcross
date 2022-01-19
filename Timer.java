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

class Timer extends Thread //Timer class
{ 
    //declaring class variables
    Console c;
    int mins, secs;
    boolean stop;
        
    public Timer(Console con){ //class constructor
        mins = 0;
        secs = 0;
        stop = false;
        c = con;
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
            c.setColor(Color.black);
            c.drawString(mins + ":" + secs, 617, 175);
            try{ //delay
                Thread.sleep(1000);
            } catch(InterruptedException e){}
            //erasing
            c.setColor(new Color(232,231,252));
            c.fillRect(610, 165, 30, 20);
        }
    }
}