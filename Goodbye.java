// The "Goodbye" class.
import java.awt.*;
import hsa.Console;

public class Goodbye
{
    static Console c;           // The output console
    
    public Goodbye (Console con){
	c = con;
    }
    public void goodbye (){
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
	c.fillRoundRect(114,312,505,27,2,2);
	c.setColor(DPURPLE);
	c.setFont(TITLE);
	c.drawString("thank you for playing!",124,324);
	c.setFont(BODY);
	c.drawString("Word Cross programmed by:",223,372);
	c.drawString("Annie Wong & Chelsea Wong", 220,396);
	c.setFont(SMALL);
	c.drawString("press any key to close the game",464,597);
      
	c.println("thanks bye");
	c.getChar();
	try{
	    Thread.sleep(1000);
	}
	catch(Exception e){
	    
	}
	c.close();
    }
} // Goodbye class