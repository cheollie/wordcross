import java.awt.*;
import java.util.*;
import java.io.*;
import hsa.*;

public class Leaderboard{

    static Console c;           // The output console
    String[][][] tempScores = new String[5][200][3];
    String[][][] scores;
    String[][] tempAllLevels = new String[200][3];    
    String[][] allLevels;
    int[] lengths = new int[5];
    boolean error = false;
    boolean leave = false;
    
    public void displayTitle(){
	c.println("Leaderboard");
	c.println("uh press whatever to toggle whatever");
    }
    
    public String[][] sort(String arr[][]){ //bubble sort!
	int n = arr.length;
	for (int i = 0; i < n-1; i++){
	    for (int j = 0; j < n-i-1; j++){
		if (Integer.parseInt(arr[j][2]) < Integer.parseInt(arr[j+1][2])){
		    String[] t = arr[j];
		    arr[j] = arr[j+1];
		    arr[j+1] = t;
		}
	    }
	} 
	return arr; 
    }

    public void cacheScores(){
	//caches the scores since the leaderboard won't update while the leaderboard screen is open and so whenever 
	//the user requests for a specific filtering of the info it wouldn't need to go through the whole file again
	try{
	    File myFile = new File ("scoresDatabase.txt"); //should exist since it existed in the first method ( getAndStoreInput(); )         
	    if (myFile.exists()){
		String line;
		String[] splitLine;
		BufferedReader fRead = new BufferedReader (new FileReader (myFile));
		int ai = 0, bi = 0, ci = 0, di = 0, ei = 0, alli = 0;   
	       
		//stores all the scores
		while ((line = fRead.readLine ()) != null){ //while not end of file
		    splitLine = line.split(" ");
		    if (splitLine[1].equals("a")){
			tempScores[0][ai] = splitLine;
			ai++;
		    } else if (splitLine[1].equals("b")){
			tempScores[1][bi] = splitLine;
			bi++;
		    } else if (splitLine[1].equals("c")){
			tempScores[2][ci] = splitLine;
			ci++;
		    } else if (splitLine[1].equals("d")){
			tempScores[3][di] = splitLine;
			di++;
		    } else if (splitLine[1].equals("e")){
			tempScores[4][ei] = splitLine;
			ei++;
		    }
		    tempAllLevels[alli] = splitLine;
		    alli++;
		}
		
		lengths = new int[] {ai, bi, ci, di, ei};               
		
		//trims and sorts the array of difficulty categorized scores 
		scores = new String[5][][];
		for (int i = 0; i < scores.length; i++){
		    String[][] tempLevelArray = new String[lengths[i]][3]; 
		    for (int j = 0; j < lengths[i]; j++){  //trims the array
			tempLevelArray[j][0] = tempScores[i][j][0]; 
			tempLevelArray[j][1] = tempScores[i][j][1];
			tempLevelArray[j][2] = tempScores[i][j][2];                    
		    } 
		    scores[i] = sort(tempLevelArray); //sorts the array
		}
		
		//trimes and sorts the array of all the scores
		allLevels = new String[alli][3]; 
		for (int i = 0; i < alli; i++){ //trims the array
		    allLevels[i][0] = tempAllLevels[i][0]; 
		    allLevels[i][1] = tempAllLevels[i][1]; 
		    allLevels[i][2] = tempAllLevels[i][2];                   
		}                 
		allLevels = sort(allLevels); //sorts the array
		
		fRead.close(); //closing BufferedReader object fRead
	    } else {
		new Message("The scores file could not be found.");
		error = true;
	    }
	}
	catch (IOException e){ //error!
	    c.println ("Error! " + e.getMessage());
	    error = true;
	}            
    }
    
    public void showAllDifficulty(){
	//c.clear();    
	c.println("- all levels lol ---------------------------");         
	for (int i = 0; i < allLevels.length; i++){ //for top 10 just do min(allLevels.length, 10) instead of allLevels.length
	    c.println(allLevels[i][0]+"      \t"+allLevels[i][1]+"\t"+allLevels[i][2]); 
	}  
	c.println("-------------------------------");         
	
    }
    
    public void showSpecificDifficulty( char ch ){
	//c.clear();
	String level = ch+""; 
	String[][] currentLevel = scores[level.compareTo("a")]; 
	c.println(level+" lol ---------------------------");         
	for (int i = 0; i < currentLevel.length; i++){ //for top 10 just do min(currentLevel.length, 10) instead of currentLevel.length
	    c.println(currentLevel[i][0]+"      \t"+currentLevel[i][1]+"\t"+currentLevel[i][2]); 
	}  
	c.println("-----------------------------------------");
    }
    
    public Leaderboard(Console con){ //Leaderboard class constructor
	c = con;
    }       
    public void leaderboard (){
    
	displayTitle();
	cacheScores();
	showAllDifficulty();
	
	while (true){
	    char next = c.getChar();
	    switch (next){
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		    showSpecificDifficulty(next);
		    break;
		case ' ':
		    showAllDifficulty();
		    break;
		case '<':
		    leave = true;
		    break;
	    }
	    if (leave) break;
	    
	}
    } // main method close
} // Leaderboard class close

/*
note:
- assuming theres is 5 levels of the same difficulty rather so it filters 5 sep ones

TODO:
- for options to not work if file errors
- message if no scores lol/empty
- top 10
- add comments
- graphic-ize it

???:
- scope of methods
- should i make the show difficulty thing an overloaded method with the show all/categorized
- do we have to error trap when they press something else that is not (a,b,c,d,e,space)?
- higher scores -> better
- stored using scores or using time?
- lb.showSpecificDifficulty(next); is this ok bc next isnt probably a good idea for that since its used a different purpose and coincidently also used in the leaderboard key

cite:
https://www.geeksforgeeks.org/bubble-sort/
*/