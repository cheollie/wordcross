// The "SplashScreen" class.
import java.awt.*;
import hsa.Console;

public class SplashScreen
{
    static Console c;           // The output console
    
    public SplashScreen(Console con){
	c = con;
    }
    public void splashScreen(){
	c.clear();
	c.println("splashscreen here");
	try{
	    Thread.sleep(2000);
	}
	catch(Exception e){
	}
    }
} // SplashScreen class