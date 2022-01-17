// The "Crossword" class.
import java.awt.*;
import hsa.Console;
import java.io.*;

public class Crossword{ //Crossword class
    Console c;
    SplashScreen s;
    MainMenu m;
    Instructions i;
    Leaderboard l;  
    Game g;
    Goodbye e;

    public Crossword () { //class constructor
	c = new Console(33, 90);
    }

    public void run () throws IOException{
	s = new SplashScreen(c);
	//s.splashScreen();
	m = new MainMenu(c);
	while(true){
	    m.mainMenu();
	    if(m.status == 'i'){
		i = new Instructions(c);
		i.instructions();
	    }
	    else if(m.status == 'l'){
		l = new Leaderboard(c);
		l.leaderboard();
	    }
	    else if(m.status == 'p'){
		g = new Game(c);
		g.start();
	    }
	    else if(m.status == 'e'){
		e = new Goodbye(c);
		e.goodbye();
		break;
	    }
	}
    }
	
    public static void main (String args[]) throws IOException{ //main method
	Crossword c = new Crossword();
	c.run(); 
    }
}