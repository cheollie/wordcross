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
	
	int n = 60; //size of grid
	int x = 172, y = 132;
	boolean[][] filled = new boolean[][] { {true, false, true, true, true}, {true, false, true, true, true}, {false, false, false, false, false}, {true, false, true, true, true}};        
	for (int i = 0; i < 4; i++){
	    for (int j = 0; j < 5; j++){
		if (filled[i][j]){ 
		    c.setColor(Color.GRAY);
		    c.fillRect(j*(n-1)+x,i*(n-1)+y,n,n);
		    c.setColor(Color.BLACK);
		}
		c.drawRect(j*(n-1)+x,i*(n-1)+y,n,n);                
		//System.out.println((j*(n-1)+x)+" "+(i*(n-1)+y));
	    }
	}
	c.setFont( new Font ("Monospaced", Font.PLAIN, 30) );
	
	String alteredTitle = "WordCoss";
	int[][] coords = new int[][] {{246+5,147+20},{246+5,206+20},{246+5,265+20},{246+5,324+20}, {187+5,265+20}, {305+5,265+20}, {364+5,265+20}, {423+5,265+20}};
	for (int i = 0; i < 8; i++){
	    c.drawString(alteredTitle.charAt(i)+"", coords[i][0], coords[i][1]);
	    try {
		Thread.sleep(500);
	    } catch (Exception e){}            
	}
	
	c.setColor(new Color(255,255,255,10));
	for (int i = 0; i < 50; i++){
	    c.fillRect(0,0,640,500);
	    try {
		Thread.sleep(50);
	    } catch (Exception e){}            
	}
	c.clear();
	c.setColor(Color.BLACK);
	c.setFont(new Font ("Serif", Font.PLAIN, 12));
    }
} // SplashScreen class