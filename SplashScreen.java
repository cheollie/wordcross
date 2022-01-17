// The "SplashScreen" class.
import java.awt.*;
import hsa.Console;

public class SplashScreen
{
    static Console c;           // The output console
    
    public SplashScreen(Console con){
	c = con;
    }
    public void cross(int x, int y, int n){
	for (int i = 0; i < n; i+=9){
	    c.drawLine(x+i,y,x,y+i); 
	    c.drawLine(x+i,y+n,x+n,y+i);
	    c.drawLine(x+i+1,y,x,y+i+1); 
	    c.drawLine(x+i+1,y+n,x+n,y+i+1);            
	    //System.out.println((Math.min(i,n))+" "+y+" "+x+" "+(Math.min(i,n))); 
	    //c.drawLine(x+i+1,y+n,x+n,y+i+1);   
	}
	//c.println(x+" "+y+" "+n);
    }
    public void splashScreen(){
	c.clear();
	c.println(c.getHeight()+" "+c.getWidth());
	final Color LPURPLE = new Color(232,231,252);
	final Color DPURPLE = new Color(87,88,208); 
	final Color WHITE = new Color(255,255,255);         
	final Font SPLASHSCREEN = new Font ("Barlow Condensed", Font.BOLD, 50);
	
	//bg
	c.setColor(DPURPLE); 
	c.fillRoundRect(50,50,632,572,20,20);
	c.setColor(LPURPLE);        
	c.fillRoundRect(54,54,624,564,16,16); 
	
	c.setColor(DPURPLE); //shadow
	c.fillRoundRect(183,189,371,298,6,6);   
	c.fillRoundRect(179,185,371,298,6,6); 
	  
	c.setColor(WHITE); //bg of the board
	c.fillRoundRect(183,189,363,290,2,2);
	
	//drawing the grid on the board
	c.setColor(DPURPLE);
	c.setFont(SPLASHSCREEN);
	int n = 75; //size of each grid/square
	int x = 181, y = 187;        
	boolean[][] filled = new boolean[][] { {true, false, true, true, true}, {true, false, true, true, true}, {false, false, false, false, false}, {true, false, true, true, true}};         
	for (int i = 0; i < 4; i++){
	    for (int j = 0; j < 5; j++){
		if (filled[i][j]){ 
		    cross(j*(n-1)+x,i*(n-1)+y,n-3); //drawing the diagonal patterns on the filled ones
		} 
		c.drawRect(j*(n-1)+x,i*(n-1)+y,n,n);  
		c.drawRect(j*(n-1)+x+1,i*(n-1)+y+1,n-2,n-2);  //thickening the line               
		//System.out.println((j*(n-1)+x)+" "+(i*(n-1)+y));
	    }
	}        
	String alteredTitle = "WORDCOSS";        
	int[][] coords = new int[][] {{275,244},{280,317},{280,390},{280,463}, {205,390}, {354,390}, {429,390}, {502,390}};
	for (int i = 0; i < 8; i++){
	    c.drawString(alteredTitle.charAt(i)+"", coords[i][0], coords[i][1]);
	    try {
		Thread.sleep(400);
	    } catch (Exception e){}            
	} 
	
	try {
	    Thread.sleep(500);
	} catch (Exception e){}          
	
	
	/*
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
	*/
	c.setColor(new Color(255,255,255,20));
	for (int i = 0; i < 50; i++){ //gradient
	    c.fillRect(0,0,720,660);
	    try {
		Thread.sleep(20);
	    } catch (Exception e){}            
	}
	c.clear();
	c.setColor(Color.BLACK);
	c.setFont(new Font ("Serif", Font.PLAIN, 12));
	
    }
} // SplashScreen class