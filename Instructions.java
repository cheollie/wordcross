// The "Instructions" class.
import java.awt.*;
import hsa.Console;

public class Instructions
{
    static Console c;           // The output console
    
    public Instructions (Console con){
	c = con;
    }
    
    public void instructions (){
	c.clear();
	final Font TITLE = new Font ("Montserrat", Font.BOLD, 42);
	final Font BODY = new Font ("Montserrat", Font.PLAIN, 20);
	final Font SMALL = new Font ("Montserrat", Font.PLAIN, 12);
	
	final Color LPURPLE = new Color(232,231,252);
	final Color MPURPLE = new Color(138, 138, 223);
	final Color DPURPLE = new Color(87,88,208); 
	final Color WHITE = new Color(255,255,255); 
	final Color PWHITE = new Color(246, 245, 254);
	
	//bg
	c.setColor(DPURPLE); 
	c.fillRoundRect(50,50,632,572,20,20);
	c.setColor(LPURPLE);        
	c.fillRoundRect(54,54,624,564,16,16);  
	
	c.setColor(PWHITE);
	c.fillRoundRect(203,159,327,27,2,2);
	c.setColor(DPURPLE);
	c.setFont(TITLE);
	c.drawString("instructions",237,174);
	c.setFont(BODY);
	c.drawString("Welcome to Word Cross!",242,231);
	c.drawString("This is a game where you can test your", 173,265);
	c.drawString("vocabulary and quick thinking.",212,289);
	c.setFont(SMALL);
	c.drawString("press any key to return to the main menu",406,597);
	
	c.println("instructions here");
	c.println("press any character to return to main menu");
	c.getChar();
    }
} // Instructions class