/* 
Name: Chelsea Wong & Annie Wong
Teacher: Ms. Basaraba
Date: January 18th, 2022
Description: This program draws the Leaderboard screen for our Crossword game. 
             This screen is accessible from the main menu and displays the top 10 
             highest scores for each puzzle, all puzzles, and allows for the user 
             to clear the scores. 
ISP: Crossword
*/

import java.awt.*; //gives access to java graphic related commands (ie. fonts and colors)
import java.util.*; //gives access to the java utility commands
import java.io.*; //gives access to the java input and output commands
import hsa.*; //gives access to hsa commands

public class Leaderboard{ //draws the leaderboard screen and displays the highest scores for each individual puzzle, all puzzles, and allows the user to clear the scores

    Console c;  //declaration of instance variable of the Console class; the output console
    
    // declaration of font constants [variables are in full uppercase since they are constants are do not change throughout the program]    
    final Font TITLE = new Font ("Montserrat", Font.BOLD, 42); // font for title text for this screen
    final Font HEADER = new Font ("Montserrat", Font.PLAIN, 22); // font for header text for this screen
    final Font BODY = new Font ("Montserrat", Font.PLAIN, 20); // font for body text for this screen
    final Font SMALL = new Font ("Montserrat", Font.PLAIN, 12); // font for small text for this screen
    
    // declaration of color constants [variables are in full uppercase since they are constants are do not change throughout the program]        
    final Color LPURPLE = new Color(232,231,252);
    final Color MPURPLE = new Color(138, 138, 223);
    final Color MDPURPLE = new Color(116, 117, 217);
    final Color DPURPLE = new Color(87,88,208); 
    final Color WHITE = new Color(255,255,255); 
    final Color PWHITE = new Color(246, 245, 254);    
    
    String[][][] tempScores = new String[5][200][3]; //temporary when length of each inner array is unknown
    String[][][] scores; //scores of categorized scores organized in nested arrays 
    String[][] tempAllLevels = new String[200][3]; //temporary when amount of total scores is
    String[][] allLevels; //scores of scores of all the levels
    int[] lengths = new int[5]; //lengths of each of the level specific score arrays
    boolean error = false; //boolean for when there are errors with files and is used to prevents things that would be affected by the error to not run and also gives the user a message that there is an error
    boolean leave = false; //boolean used to break out of the while loop to exit the leaderboard screen to the main menu screen
        
    public void displayFixedGraphics(){ //display the graphics that don't change (eg. the background, titles, subtitles, the chart part of the leaderboard chart, and the instructions on keys)

        //drawing the background [used across all screens]
        c.setColor(DPURPLE); 
        c.fillRoundRect(50,50,632,572,20,20);
        c.setColor(LPURPLE);        
        c.fillRoundRect(54,54,624,564,16,16);  
        
        //the title        
        c.setColor(PWHITE);
        c.fillRoundRect(213,145,307,27,2,2); //the highlight/underline for the title
        c.setColor(DPURPLE);
        c.setFont(TITLE);
        c.drawString("leaderboard",235,159);
        
        //the subtitle under the title (gives instructions on how to use the leaderboard)
        c.setFont(SMALL);
        c.drawString("press [space]  to view the top 10 scores across all puzzles and press",169,198);
        c.drawString("[a], [b], [c], [d], or [e] to see the top scores specific to that puzzle",180,213);
        
        //headers for the leaderboard chart
        c.setFont(HEADER);
        c.drawString("rank",148,255);
        c.drawString("player",240,255);
        c.drawString("puzzle",407,255);
        c.drawString("score",527,255);
        
        //horizontal line for the leaderboard chart
        c.drawLine(132,268,600,268);
        c.drawLine(132,269,600,269); //extra line to thicken
        
        //vertical line for the leaderboard chart
        c.drawLine(211,233,211,574);
        c.drawLine(212,233,212,574); //extra line to thicken
        
        //instructions on keys
        c.setFont(SMALL);
        c.drawString("press [x] to clear, and press [<] to return to the main menu",309,597); //instructions on bottom right
        
        //reset styling for the displaying the scores that proceed this
        c.setFont(BODY); 
        c.setColor(MDPURPLE);
    }
    
    public String[][] sort(String arr[][]){ //sort method using bubble sort
        int n = arr.length;
        for (int i = 0; i < n-1; i++){
            for (int j = 0; j < n-i-1; j++){
                if (Integer.parseInt(arr[j][2]) < Integer.parseInt(arr[j+1][2])){ //to change sorting (high to low/low to high) 
                    String[] t = arr[j]; //temp variable to store the one being replaced when the two indices get swapped
                    arr[j] = arr[j+1];
                    arr[j+1] = t;
                }
            }
        } 
        return arr; 
    }
    public void errorMessage(){ //method to display error message when error encountered when dealing with files
        c.drawString("There has been an error with",239,297);
        c.drawString("retrieving the scores.",239,297+24*1);
        c.drawString("Make sure scoresDatabase.txt is in",239,297+24*2);
        c.drawString("the same folder as Crossword.java",239,297+24*3);
        c.drawString("and Leaderboard.java.",239,297+24*4);      
    }
    public void cacheScores(){
        //caches the scores since the leaderboard won't update while the leaderboard screen is open and so whenever 
        //the user requests for a specific filtering of the info it wouldn't need to go through the whole file again
        try{
            File myFile = new File ("scoresDatabase.txt"); 
            if (myFile.exists()){
                String line;
                String[] splitLine;
                BufferedReader fRead = new BufferedReader (new FileReader (myFile));
                int ai = 0, bi = 0, ci = 0, di = 0, ei = 0, alli = 0; //array could be used but there was only 5/6 counters
               
                //stores all the scores and some by difficulty as well
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
                
                //trims and sorts the array of difficulty categorized scores (trimming is necessary since we cannot use arraylists and commands we didn't learn) 
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
                
                //trims and sorts the array of all the scores (trimming is necessary since we cannot use arraylists and commands we didn't learn) 
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
                error = true; //sets error to true [which prevents things that would be affected by the error to not run and also gives the user a message that there is an error]
            }
        }
        catch (IOException e){ //error!
            //System.out.println ("Error! " + e.getMessage()); //can be uncommented if the user wants to see the error
            error = true; //sets error to true [which prevents things that would be affected by the error to not run and also gives the user a message that there is an error]
        }            
    }

    public void clear(){ //clears the leaderboard (by clearing the leaderboard file)
        try{
            File myFile = new File ("scoresDatabase.txt");      
            if (myFile.exists()){
                PrintWriter pWrite = new PrintWriter (new FileWriter (myFile, false));    //overload with extra parameter (false) overwrites the file rather than appends to the file  
                pWrite.print("");
                new Message("Leaderboard cleared!");
                pWrite.close(); //closes PrintWriter
                
            } else {
                new Message("The scores file could not be found.");
                error = true; //sets error to true [which prevents things that would be affected by the error to not run and also gives the user a message that there is an error]
            }
        }
        catch (IOException e){ //error!
            //System.out.println ("Error! " + e.getMessage()); //can be uncommented if the user wants to see the error
            error = true; //sets error to true [which prevents things that would be affected by the error to not run and also gives the user a message that there is an error]
        }   
        cacheScores();
        displayScores();
    }  
    public void displayScores(){ //overloaded method: displayScores() shows scores of ALL PUZZLES regardless of which puzzle
        c.setColor(LPURPLE);    //covers the previous frame's scores
        c.fillRect(132,273,74,301);  
        c.fillRect(219,273,382,300);  
        c.setColor(DPURPLE);        
        if (allLevels.length == 0){ //message if there are no scores yet
            c.drawString("--",146,297);
            c.drawString("There are no scores yet, play a",239,297);
            c.drawString("timed game to be the first one!",239,297+24);
        } else {
            for (int i = 0; i < Math.min(allLevels.length,10); i++){ //so it doesn't display more than 10 scores
                c.drawString(i+1+"",146,297+i*29);
                c.drawString(allLevels[i][0],239,297+i*29);
                c.drawString(allLevels[i][1],407,297+i*29);
                c.drawString(allLevels[i][2],526,297+i*29);
            }  
        }
    }    
    public void displayScores( char ch ){ //overloaded method: displayScores(char) shows top scores of the plays for puzzle 'char'
        c.setColor(LPURPLE); //covers the previous frame's scores
        c.fillRect(132,273,74,301);  
        c.fillRect(219,273,382,300);      
        c.setColor(DPURPLE);
        String level = ch+""; 
        String[][] currentLevel = scores[level.compareTo("a")]; // level.compareTo("a") is to get the index as it maps "a" to index 0 (first index) "b" to index 1 (second index) etc
        if (currentLevel.length == 0){ //message if there are no scores yet
            c.drawString("--",146,297);
            c.drawString("There are no scores yet for this",239,297);
            c.drawString("puzzle.",239,297+24);
        } else {        
            for (int i = 0; i < Math.min(currentLevel.length, 10); i++){ //so it doesn't display more than 10 scores
                c.drawString(i+1+"",146,297+i*29);
                c.drawString(currentLevel[i][0],239,297+i*29);
                c.drawString(currentLevel[i][1],407,297+i*29);
                c.drawString(currentLevel[i][2],526,297+i*29);
            }  
        }
    }
    
    public Leaderboard(Console con){ //constructor for Leaderboard class
        c = con; //to use the Console object passed as a parameter when creating an instance of this class
    }       
    public void leaderboard (){
    
        displayFixedGraphics();
        cacheScores(); //caches the scores upon opening leaderboard screen
        if (!error){
            displayScores(); //shows top 10 scores of all puzzles upon opening unless error with file
        } else {
            errorMessage(); //display error message if error
        }
        
        while (true){
            char next = c.getChar(); //get next char
            switch (next){ //switch statement for actions that the user wants to do
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                    if (!error){
                        displayScores(next); //display scores for only puzzle next
                    } else {
                        errorMessage(); //error message if error
                    }
                    break;                    
                case ' ':
                    if (!error){
                        displayScores(); //display all scores
                    } else {
                        errorMessage(); //error message if error
                    }
                    break;  
                case 'x':
                    if (!error){
                        clear(); //clears the leaderboard
                    } else {
                        new Message("The scores file could not be found, therefore the leaderboard cannot be cleared");
                    }
                    break;
                case '<':
                    leave = true; //variable to break the while loop and leave the screen to go back to the main menu
                    break;
                default: //if user presses none of the intended keys
                    new Message("Invalid input. Press [space] to view top scores across all puzzles, [a] for puzzle A's top scores, [b] for puzzle B's top scores, [c] for puzzle C's top scores, [d] for puzzle D's top scores, [e] for puzzle E's top scoers, [x] to clear the scores, or [<] to return to main menu.");
                    break;
            }
            if (leave) break; //break when leave is true (when set to true from '<')
        }
    }
} // Leaderboard class close
