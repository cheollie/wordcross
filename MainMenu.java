// The "MainMenu" class.
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import hsa.Console;

public class MainMenu
{
    Console c;           // The output console
    char status;
    
    public MainMenu (Console con){
	c = con;
	status = 'i';
    }
    
    public void mainMenu (){
	c.clear();
	c.drawString("Wordcross", 300, 200);
	c.drawString("instructions", 300, 300);
	c.drawString("leaderboard", 300, 350);
	c.drawString("play!", 300, 400);
	c.drawString("exit", 300, 450);
	c.drawString("press < and > to navigate the options, then press enter to select", 200, 600);
	while(true){
	    c.setColor(Color.white);
	    c.fillRect(280, 280, 10, 200);
	    c.setColor(Color.black);
	    if(status == 'i'){
		c.fillOval(280, 295, 5, 5);
	    }
	    else if(status == 'l'){
		c.fillOval(280, 345, 5, 5);
	    }
	    else if(status == 'p'){
		c.fillOval(280, 395, 5, 5);
	    }
	    else if(status == 'e'){
		c.fillOval(280, 445, 5, 5);
	    }
	    int input = c.getChar(); //user input key
	    if(input == 10){ //if enter is pressed
		break;
	    }
	    else{
		if(status == 'i' && input == 60){
		    status = 'e';
		    continue;
		}
		if(status == 'i' && input == 62){
		    status = 'l';
		    continue;
		}
		if(status == 'l' && input == 60){
		    status = 'i';
		    continue;
		}
		if(status == 'l' && input == 62){
		    status = 'p';
		    continue;
		}
		if(status == 'p' && input == 60){
		    status = 'l';
		    continue;
		}
		if(status == 'p' && input == 62){
		    status = 'e';
		    continue;
		}
		if(status == 'e' && input == 60){
		    status = 'p';
		    continue;
		}
		if(status == 'e' && input == 62){
		    status = 'i';
		    continue;
		}
	    }
	}
    }
} // MainMenu class