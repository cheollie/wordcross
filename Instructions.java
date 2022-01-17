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
	c.println("instructions here");
	c.println("press any character to return to main menu");
	c.getChar();
    }
} // Instructions class