// The "Leaderboard" class.
import java.awt.*;
import hsa.Console;

public class Leaderboard
{
    static Console c;
    
    public Leaderboard(Console con){
	c = con;
    }
    
    public void leaderboard(){
	c.clear();
	c.println("leaderboard here");
	c.println("press any character to return to main menu");
	c.getChar();
    }
} // Leaderboard class