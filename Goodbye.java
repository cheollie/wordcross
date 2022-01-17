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
	c.println("thanks bye");
	try{
	    Thread.sleep(1000);
	}
	catch(Exception e){
	    
	}
	c.close();
    }
} // Goodbye class