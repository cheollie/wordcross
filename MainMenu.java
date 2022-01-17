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
	
	final Font TITLE = new Font ("Montserrat", Font.BOLD, 48);
	final Font BODY = new Font ("Montserrat", Font.PLAIN, 30);
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
	c.fillRoundRect(222,362,292,25,2,2);
	c.setColor(DPURPLE);
	c.setFont(TITLE);
	c.drawString("word cross", 230, 375);

	c.setFont(BODY);
	c.setColor(MPURPLE);
	c.drawString("instructions", 280, 428);
	c.drawString("play", 338, 463);
	c.drawString("leaderboard", 278, 499);
	c.drawString("exit", 341, 534);
	
	c.setFont(SMALL);  
	c.setColor(DPURPLE);
	c.drawString("press [<] and [>] to navigate between the options, then press [enter] to select", 139, 591);
	
	c.setFont(BODY);
       
	while(true){
	    if(status == 'i'){
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
	    else if(status == 'p'){
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
	    else if(status == 'l'){
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
	    else if(status == 'e'){
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
		    status = 'p';
		    continue;
		}
		if(status == 'p' && input == 60){
		    status = 'i';
		    continue;
		}
		if(status == 'p' && input == 62){
		    status = 'l';
		    continue;
		}
		if(status == 'l' && input == 60){
		    status = 'p';
		    continue;
		}
		if(status == 'l' && input == 62){
		    status = 'e';
		    continue;
		}
		if(status == 'e' && input == 60){
		    status = 'l';
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