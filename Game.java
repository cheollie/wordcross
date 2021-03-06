/* 
Name: Chelsea Wong & Annie Wong
Teacher: Ms. Basaraba
Date: January 18th, 2022
Description: This program is the Game class for our Crossword game. 
             This is composed of three main screens, the timed/untimed screen, 
             the actual puzzle, and the completed screen.
ISP: Crossword
*/

import java.awt.*; //gives access to java graphic related commands (ie. fonts and colors)
import java.io.*; //gives access to the java input and output commands
import hsa.*; //gives access to hsa commands

public class Game
{   
    class Square //subclass
    {
        int row;
        int col;
        
        public Square(int row, int col){
            this.row = row;
            this.col = col;
        }
    }
    
    
    class Word //subclass
    {
        //class variables
        int num;
        String word;
        Square start;
        char direction;

        public Word(int num, String word, Square start, char direction){ //constructor
            this.num = num;
            this.word = word;
            this.start = start;
            this.direction = direction;
        }
    }
    
    
    //class variables
    static Console c; 
    int counter, size, numWords, posX, posY, numAcross, numDown, score, squareSize;
    char input, choice, puzzle;
    boolean win, forward, backward, giveUp, hint, error, backspace;
    Square focused;
    Word[] words;
    char[][] answerKey, board;
    String[] clues;
    Timer t;
        
    // imports the font files 
    public void loadFonts() throws java.awt.FontFormatException, java.io.IOException {
        Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-Regular.ttf"));
        Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-Bold.ttf"));
        Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-Italic.ttf"));
        Font.createFont(Font.TRUETYPE_FONT, (InputStream) new FileInputStream("Fonts\\Montserrat-BoldItalic.ttf"));  
    }
            
    //graphics variables
    final Font TITLE = new Font ("Montserrat Bold", Font.PLAIN, 48);
    final Font BODY = new Font ("Montserrat Regular", Font.PLAIN, 30);
    final Font SMALL = new Font ("Montserrat Regular", Font.PLAIN, 12);
    final Font HEADER1 = new Font ("Montserrat Regular", Font.PLAIN, 22);
    final Font HEADER2 = new Font ("Montserrat Regular", Font.PLAIN, 20);
    final Font BODY2 = new Font ("Montserrat Regular", Font.PLAIN, 20);
    final Font MEDIUM = new Font ("Montserrat Regular", Font.PLAIN, 14);
    final Font TINY = new Font ("Montserrat Regular", Font.PLAIN, 9);
    final Font CLUES = new Font ("Montserrat Regular", Font.PLAIN, 13);
        
    final Color LPURPLE = new Color(232,231,252);
    final Color MPURPLE = new Color(138, 138, 223);
    final Color DPURPLE = new Color(87,88,208); 
    final Color WHITE = new Color(255,255,255); 
    final Color PWHITE = new Color(246, 245, 254);
    
    public Game (Console con) throws java.awt.FontFormatException, java.io.IOException { //constructor
        c = con;
        loadFonts();
    }

    public void selection (){ //select either timed or untimed
        c.clear();
        
        //background
        c.setColor(DPURPLE); 
        c.fillRoundRect(50,50,632,572,20,20);
        c.setColor(LPURPLE);        
        c.fillRoundRect(54,54,624,564,16,16);  
        
        //title
        c.setColor(PWHITE);
        c.fillRoundRect(115,313,502,27,2,2);
        c.setColor(DPURPLE);
        c.setFont(TITLE);
        c.drawString("select game mode", 133, 325);
        
        //text
        c.setFont(BODY);
        c.setColor(MPURPLE);
        c.drawString("timed", 322, 400);
        c.drawString("untimed", 303, 450);
        
        c.setFont(SMALL);  
        c.setColor(DPURPLE);
        c.drawString("press [<] and [>] to navigate between the options, then press [enter] to select", 139, 591);

        choice = 't'; //defalt choice
        
        //selection for timed or untimed
        c.setFont(BODY);
        while(true){
            if(choice == 't'){
                c.setColor(LPURPLE); 
                c.drawString("<", 275, 450); //cover u arrow 
                c.drawString(">", 445, 450);  
                c.setColor(MPURPLE);
                c.drawString("untimed", 303, 450); //lighten u text 
                c.setColor(DPURPLE);
                c.drawString("<", 295, 400); //draw t arrow  
                c.drawString(">", 420, 400); 
                c.drawString("timed", 322, 400); //darken t text
            }
            else if(choice == 'u'){
                c.setColor(LPURPLE); 
                c.drawString("<", 295, 400); //cover t arrow   
                c.drawString(">", 420, 400); 
                c.setColor(MPURPLE);
                c.drawString("timed", 322, 400);  //lighten t text 
                c.setColor(DPURPLE);
                c.drawString("<", 275, 450); //draw u arrow 
                c.drawString(">", 445, 450);
                c.drawString("untimed", 303, 450);  //darken u text
            }
            int input = c.getChar(); //user input key
            if(input == 10){ //if enter is pressed
                break;
            }
            else if (input == 60 || input == 62) {
                if(choice == 't' && (input == 60 || input == 62)){
                    choice = 'u';
                    continue;
                }
                else if(choice == 'u' && (input == 60 || input == 62)){
                    choice = 't';
                    continue;
                }
            } else { //errortrapping
                new Message("Invalid input. Press [<] and [>] to navigate between the options and [enter] to select.");
            }
        }
    }
    
    
    public void readFile () throws IOException{ //reads puzzle file for all necessary info
        //selects random puzzle
        BufferedReader br = new BufferedReader(new FileReader("puzzleA.txt"));
        int random = (int) (Math.random()*5+1); //generates random int
        if(random == 1){
            br = new BufferedReader(new FileReader("puzzleA.txt"));
        }
        else if(random == 2){
            br = new BufferedReader(new FileReader("puzzleB.txt"));
        }
        else if(random == 3){
            br = new BufferedReader(new FileReader("puzzleC.txt"));
        }
        else if(random == 4){
            br = new BufferedReader(new FileReader("puzzleD.txt"));
        }
        else if(random == 5){
            br = new BufferedReader(new FileReader("puzzleE.txt"));
        }
        //read puzzle file for puzzle level
        puzzle = br.readLine().charAt(0);
        //reads puzzle file for board size
        size = Integer.parseInt(br.readLine());
        //reads puzzle file for answerkey board
        answerKey = new char[size][size];
        for(int row = 0; row < size; row++){
            String line = br.readLine();
            for(int col = 0; col < size; col++){
                answerKey[row][col] = line.charAt(col);
            }
        }
        //replicates blank board from answerkey
        board = new char[size][size];
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(answerKey[row][col] == '#'){
                    board[row][col] = '#';
                }
                else{
                    board[row][col] = ' ';
                }
            }
        }
        //reads puzzle file for words
        numWords = Integer.parseInt(br.readLine());
        words = new Word[numWords];
        for(int i = 0; i < numWords; i++){
            String[] line = br.readLine().split(" ");
            words[i] = new Word(Integer.parseInt(line[0]), line[1], new Square(Integer.parseInt(line[2].split(",")[0]), Integer.parseInt(line[2].split(",")[1])), line[3].charAt(0));
        } 
        //read puzzle file for num of across and down clues
        numAcross = Integer.parseInt(br.readLine());
        numDown = Integer.parseInt(br.readLine());
        //reads puzzle file for clues
        clues = new String[numWords];
        for(int i = 0; i < numWords; i++){
            clues[i] = br.readLine();        
        }
    }
    
    public void cross(int x, int y, int n){ //method used to draw diagonals on the "blocked" boxes on the board, x and y being the top left corner and n being the width
        for (int i = 6; i < n; i+=8){ //uses a for loop to draw diagonal lines with a given increment between each line
            c.drawLine(x+i,y,x,y+i); 
            c.drawLine(x+i,y+n,x+n,y+i);
            c.drawLine(x+i+1,y,x,y+i+1); 
            c.drawLine(x+i+1,y+n,x+n,y+i+1); 
            c.drawLine(x+i+2,y,x,y+i+2); 
            c.drawLine(x+i+2,y+n,x+n,y+i+2);
        }
    }
    
    public void preDisplay (){ //displays default board before input
        c.clear();
        
        //background
        c.setColor(DPURPLE); 
        c.fillRoundRect(40,40,652,590,20,20);
        c.setColor(LPURPLE);        
        c.fillRoundRect(44,44,644,582,16,16);
        
        //printing the clues
        c.setColor(PWHITE);
        c.fillRoundRect(500, 100, 83, 4, 2, 2); //underline for down
        c.fillRoundRect(328, 436, 83, 4, 2, 2); //underline for across
        c.setColor(DPURPLE);
        c.setFont(HEADER1);
        c.drawString("across", 335, 430); //header for across
        c.drawString("down", 510, 95); //header for down
        //across clues
        c.setFont(CLUES);
        for(int i = 0; i < (int)Math.ceil(numAcross*1.0/3); i++){ //each for loop for a column
            c.drawString(clues[i], 85, 467+i*20);
        }
        for(int i = (int)Math.ceil(numAcross*1.0/3); i < (int)Math.ceil(numAcross*1.0/3)*2; i++){
            c.drawString(clues[i], 285, 467+(i - (int)Math.ceil(numAcross*1.0/3))*20);
        }
        for(int i = (int)Math.ceil(numAcross*1.0/3)*2; i < numAcross; i++){
            c.drawString(clues[i], 485, 467+(i-(int)Math.ceil(numAcross*1.0/3)*2)*20);
        }
        //down clues
        for(int i = 0; i < numDown; i++){
            c.drawString(clues[i+numAcross], 430, 129+i*20);
        }
        
        //printing the puzzle level
        c.setFont(HEADER2);
        c.drawString("puzzle: " + puzzle, 95, 91);
        
        //printing the board
        c.setColor(DPURPLE);
        c.fillRoundRect(posX+1, posY+1, size*(squareSize+1)+8, size*(squareSize+1)+8, 5, 5);
        c.fillRoundRect(posX-5, posY-5, size*(squareSize+1)+1+8, size*(squareSize+1)+1+8, 5, 5);
        c.setColor(WHITE);
        c.fillRoundRect(posX, posY, size*squareSize+8, size*squareSize+8, 5, 5);
        c.setColor(DPURPLE);
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(board[row][col] == '#'){ //striped squares
                    cross(posX+col*squareSize+col, posY+row*squareSize+row, squareSize+1);
                }
                c.drawRect(posX+col*squareSize+col, posY+row*squareSize+row, squareSize, squareSize); //blank squares
            }
        }
        
        //printing the little numbers
        c.setColor(MPURPLE);
        for(int i = 0; i < words.length; i++){ 
            c.setFont(TINY);
            c.drawString(words[i].num + "", posX+4+(words[i].start.col)*(squareSize+1), posY+10+(words[i].start.row)*(squareSize+1));
        }
        
        //default focused word
        c.setColor(new Color(87, 88, 208, 30));
        if(words[counter].direction == 'a'){
            c.fillRect(posX+words[counter].start.col*(squareSize+1), posY+words[counter].start.row*(squareSize+1), words[counter].word.length()*squareSize+6, squareSize); 
            c.fillRect(posX-1+words[counter].start.col*(squareSize+1), posY-1+words[counter].start.row*(squareSize+1), words[counter].word.length()*squareSize+6, squareSize+2); 
        }
        else if(words[counter].direction == 'd'){
            c.fillRect(posX+words[counter].start.col*(squareSize+1), posY+words[counter].start.row*(squareSize+1), squareSize, words[counter].word.length()*squareSize); 
            c.fillRect(posX-1+words[counter].start.col*(squareSize+1), posY-1+words[counter].start.row*(squareSize+1), squareSize+2, words[counter].word.length()*squareSize+2); 
        }
        
        //default focused square
        c.setColor(DPURPLE);
        c.drawRect(posX+1+focused.col*(squareSize+1), posY+1+focused.row*(squareSize+1), squareSize-2, squareSize-2); 
        c.drawRect(posX+focused.col*(squareSize+1), posY+focused.row*(squareSize+1), squareSize, squareSize);
        c.drawRect(posX-1+focused.col*(squareSize+1), posY-1+focused.row*(squareSize+1), squareSize+2, squareSize+2);
        c.drawRect(posX-2+focused.col*(squareSize+1), posY-2+focused.row*(squareSize+1), squareSize+4, squareSize+4);
        
        //instructions message
        c.setFont(SMALL);
        c.setColor(DPURPLE);
        if(choice == 't'){
            c.drawString("press [*] to give up, press [?] for hints, and press [<] and [>] to navigate between words", 113, 597);
        }
        else{
            c.drawString("press [*] to give up and press [<] and [>] to navigate between words", 167, 597);
        }
    }
    
    
    public void input (){ //takes user input and stores in board
        forward = false;
        backward = false;
        hint = false;
        backspace = false;
        while(true){ //error trapping for valid keys
            input = c.getChar();
            if(input >= 97 && input <= 122 || input >= 65 && input <= 90 || input == 60 || input == 62 || input == 42 || input == 63 || input == 8){
                break;
            }
        }
        if(input == 60){ //backward <
            backward = true;
        }
        else if(input == 62){ //forward >
            forward = true;
        }
        else if(input == 42){ //give up *
            giveUp = true;
        }
        else if(input == 63 && choice == 't'){ //hint ?
            hint = true;
            for(int i = 0; i < words[counter].word.length(); i++){
                if(words[counter].direction == 'a'){
                    board[words[counter].start.row][words[counter].start.col+i] = words[counter].word.charAt(i);
                }
                else{
                   board[words[counter].start.row+i][words[counter].start.col] = words[counter].word.charAt(i); 
                }
            }  
        }
        else if(input == 8){
            backspace = true;
        }
        else{
            board[focused.row][focused.col] = input;
        }
    }
    
    public void update (){ //updates the display of the board
        //clear the board background
        c.setColor(DPURPLE);
        c.fillRoundRect(posX+1, posY+1, size*(squareSize+1)+8, size*(squareSize+1)+8, 5, 5);
        c.fillRoundRect(posX-5, posY-5, size*(squareSize+1)+1+8, size*(squareSize+1)+1+8, 5, 5);
        c.setColor(WHITE);
        c.fillRoundRect(posX, posY, size*squareSize+8, size*squareSize+8, 5, 5);
        
        //draw the correct green bgs
        c.setColor(new Color(190, 255, 210));
        for(int i = 0; i < words.length; i++){
            if(checkWord(i) == true){
                if(words[i].direction == 'a'){
                    c.fillRect(posX+words[i].start.col*(squareSize+1), posY+words[i].start.row*(squareSize+1), (squareSize+1)*words[i].word.length(), squareSize);
                }
                else{
                   c.fillRect(posX+words[i].start.col*(squareSize+1), posY+words[i].start.row*(squareSize+1), squareSize, (squareSize+1)*words[i].word.length()); 
                }
            }
        }
        
        //if backspace is pressed
        if(backspace){
            board[focused.row][focused.col] = ' ';
            if(words[counter].direction == 'a' && focused.col > words[counter].start.col){
                focused.col -= 2;
            }
            else if(words[counter].direction == 'd' && focused.row > words[counter].start.row){
                focused.row -= 2;
            }
            else if(words[counter].direction == 'a'){
                focused.col--;
            }
            else if(words[counter].direction == 'd'){
                focused.row--;
            }
            c.setColor(new Color(87, 88, 208, 30));
            if(words[counter].direction == 'a'){
                c.fillRect(posX+words[counter].start.col*(squareSize+1), posY+words[counter].start.row*(squareSize+1), words[counter].word.length()*(squareSize+1)-1, squareSize);
                c.fillRect(posX-1+words[counter].start.col*(squareSize+1), posY-1+words[counter].start.row*(squareSize+1), words[counter].word.length()*(squareSize+1)+1, squareSize+2);
            }
            else{
                c.fillRect(posX+words[counter].start.col*(squareSize+1), posY+words[counter].start.row*(squareSize+1), squareSize, words[counter].word.length()*(squareSize+1)-1);
                c.fillRect(posX-1+words[counter].start.col*(squareSize+1), posY-1+words[counter].start.row*(squareSize+1), squareSize+2, words[counter].word.length()*(squareSize+1)+1);
            }
        }
        
        //change focus
        if(!hint && words[counter].direction == 'a' && focused.col < size){ //changing focus to next square
            focused.col++;
        }
        else if(!hint && words[counter].direction == 'd' && focused.row < size){
            focused.row++;
        }
        
        //if < is pressed
        if(counter == 0 && backward){
            counter = words.length-2;
        }
        else if(backward){
            counter -= 2;
        }
        
        //setting focus + printing word focus
        c.setColor(new Color(87, 88, 208, 30));
        if(!backspace && !hint && !forward && !backward && focused.row < size && focused.col < size && answerKey[focused.row][focused.col] != '#'){
            if(words[counter].direction == 'a'){
                c.fillRect(posX+words[counter].start.col*(squareSize+1), posY+words[counter].start.row*(squareSize+1), words[counter].word.length()*(squareSize+1)-1, squareSize);
                c.fillRect(posX-1+words[counter].start.col*(squareSize+1), posY-1+words[counter].start.row*(squareSize+1), words[counter].word.length()*(squareSize+1)+1, squareSize+2);
            }
            else{
                c.fillRect(posX+words[counter].start.col*(squareSize+1), posY+words[counter].start.row*(squareSize+1), squareSize, words[counter].word.length()*(squareSize+1)-1);
                c.fillRect(posX-1+words[counter].start.col*(squareSize+1), posY-1+words[counter].start.row*(squareSize+1), squareSize+2, words[counter].word.length()*(squareSize+1)+1);
            }
        }
        else if(!backspace && !hint && counter < words.length-1){ //move to next word
            focused.row = words[counter+1].start.row;
            focused.col = words[counter+1].start.col;
            if(words[counter+1].direction == 'a'){
                c.fillRect(posX+words[counter+1].start.col*(squareSize+1), posY+words[counter+1].start.row*(squareSize+1), words[counter+1].word.length()*(squareSize+1)-1, squareSize);
                c.fillRect(posX-1+words[counter+1].start.col*(squareSize+1), posY-1+words[counter+1].start.row*(squareSize+1), words[counter+1].word.length()*(squareSize+1)+1, squareSize+2);
            }
            else{
                c.fillRect(posX+words[counter+1].start.col*(squareSize+1), posY+words[counter+1].start.row*(squareSize+1), squareSize, words[counter+1].word.length()*(squareSize+1)-1);
                c.fillRect(posX-1+words[counter+1].start.col*(squareSize+1), posY-1+words[counter+1].start.row*(squareSize+1), squareSize+2, words[counter+1].word.length()*(squareSize+1)+1);
            }
        }
        else if(!backspace && !hint){ //counter is at last index, restart
            focused.row = words[0].start.row;
            focused.col = words[0].start.col;
            if(words[0].direction == 'a'){
                c.fillRect(posX+words[0].start.col*(squareSize+1), posY+words[0].start.row*(squareSize+1), words[0].word.length()*(squareSize+1)-1, squareSize);
                c.fillRect(posX-1+words[0].start.col*(squareSize+1), posY-1+words[0].start.row*(squareSize+1), words[0].word.length()*(squareSize+1)+1, squareSize+2);
            }
            else{
                c.fillRect(posX+words[0].start.col*(squareSize+1), posY+words[0].start.row*(squareSize+1), squareSize, words[0].word.length()*(squareSize+1)-1);
                c.fillRect(posX-1+words[0].start.col*(squareSize+1), posY+words[0].start.row*(squareSize+1), squareSize+2, words[0].word.length()*(squareSize+1)+1);
            }
        }

        //printing the board
        c.setColor(DPURPLE);
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(board[row][col] == '#'){ //striped squares
                    cross(posX+col*squareSize+col, posY+row*squareSize+row, squareSize+1);
                }
                c.drawRect(posX+col*squareSize+col, posY+row*squareSize+row, squareSize, squareSize); //blank squares
            }
        }       
        
        //print the numbers
        for(int i = 0; i < words.length; i++){ 
            c.setColor(MPURPLE);
            c.setFont(TINY);
            c.drawString(words[i].num + "", posX+5+(words[i].start.col)*(squareSize+1), posY+10+(words[i].start.row)*(squareSize+1));
        }
        
        //print the letters
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(board[row][col] != '#'){
                    c.setColor(DPURPLE);
                    c.setFont(MEDIUM);
                    c.drawString(board[row][col]+"", posX+12+col*(squareSize+1), posY+22+row*(squareSize+1));
                }
            }
        }

        //printing focused square
        c.setColor(DPURPLE);
        c.drawRect(posX+1+focused.col*(squareSize+1), posY+1+focused.row*(squareSize+1), squareSize-2, squareSize-2); 
        c.drawRect(posX+focused.col*(squareSize+1), posY+focused.row*(squareSize+1), squareSize, squareSize);
        c.drawRect(posX-1+focused.col*(squareSize+1), posY-1+focused.row*(squareSize+1), squareSize+2, squareSize+2);
        c.drawRect(posX-2+focused.col*(squareSize+1), posY-2+focused.row*(squareSize+1), squareSize+4, squareSize+4);
    }
    
    public boolean checkWin (){ //check if whole board matches answerKey
        for(int row = 0 ; row < answerKey.length; row++){
            for(int col = 0; col < answerKey[0].length; col++){
                if(answerKey[row][col] != board[row][col]){
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean checkWord (int num){ //check if single word matches answerKey
        for(int i = 0; i < words[num].word.length(); i++){
            if(words[num].direction == 'a'){
                if(board[words[num].start.row][words[num].start.col + i] != words[num].word.charAt(i)){
                    return false;
                }
            }
            else{
                if(board[words[num].start.row + i][words[num].start.col] != words[num].word.charAt(i)){
                    return false;
                }
            }
        }
        return true;
    }
    
    public void showAnswer(){ //show answers for puzzle board
        c.clear();
        
        //background
        c.setColor(DPURPLE); 
        c.fillRoundRect(40,40,652,590,20,20);
        c.setColor(LPURPLE);        
        c.fillRoundRect(44,44,644,582,16,16);
        
        //printing the clues
        c.setColor(PWHITE);
        c.fillRoundRect(500, 100, 83, 4, 2, 2); //underline for down
        c.fillRoundRect(328, 436, 83, 4, 2, 2); //underline for across
        c.setColor(DPURPLE);
        c.setFont(HEADER1);
        c.drawString("across", 335, 430); //header for across
        c.drawString("down", 510, 95); //header for down
        //across clues
        c.setFont(CLUES);
        for(int i = 0; i < (int)Math.ceil(numAcross*1.0/3); i++){ //each for loop for a column
            c.drawString(clues[i], 85, 467+i*20);
        }
        for(int i = (int)Math.ceil(numAcross*1.0/3); i < (int)Math.ceil(numAcross*1.0/3)*2; i++){
            c.drawString(clues[i], 285, 467+(i - (int)Math.ceil(numAcross*1.0/3))*20);
        }
        for(int i = (int)Math.ceil(numAcross*1.0/3)*2; i < numAcross; i++){
            c.drawString(clues[i], 485, 467+(i-(int)Math.ceil(numAcross*1.0/3)*2)*20);
        }
        //down clues
        for(int i = 0; i < numDown; i++){
            c.drawString(clues[i+numAcross], 430, 129+i*20);
        }
        
        //printing the header
        c.setFont(HEADER2);
        c.setColor(DPURPLE);
        c.drawString("puzzle " + puzzle + " answer", 95, 91);
        
        //printing the footer
        c.setFont(SMALL);
        c.setColor(DPURPLE);
        c.drawString("press any key to return to the main menu", 241, 597);
        
        //printing the board bg
        c.setColor(DPURPLE);
        c.fillRoundRect(posX+1, posY+1, size*(squareSize+1)+8, size*(squareSize+1)+8, 5, 5);
        c.fillRoundRect(posX-5, posY-5, size*(squareSize+1)+1+8, size*(squareSize+1)+1+8, 5, 5);
        c.setColor(WHITE);
        c.fillRoundRect(posX, posY, size*squareSize+8, size*squareSize+8, 5, 5);
        
        //draw the correct green bgs
        c.setColor(new Color(190, 255, 210));
        for(int i = 0; i < words.length; i++){
            if(checkWord(i) == true){
                if(words[i].direction == 'a'){
                    c.fillRect(posX+words[i].start.col*(squareSize+1), posY+words[i].start.row*(squareSize+1), (squareSize+1)*words[i].word.length(), squareSize);
                }
                else{
                   c.fillRect(posX+words[i].start.col*(squareSize+1), posY+words[i].start.row*(squareSize+1), squareSize, (squareSize+1)*words[i].word.length()); 
                }
            }
        }
        
        //printing the board
        c.setColor(DPURPLE);
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(board[row][col] == '#'){ //striped squares
                    cross(posX+col*squareSize+col, posY+row*squareSize+row, squareSize+1);
                }
                c.drawRect(posX+col*squareSize+col, posY+row*squareSize+row, squareSize, squareSize); //blank squares
            }
        } 
        
        //print the numbers
        for(int i = 0; i < words.length; i++){ 
            c.setColor(MPURPLE);
            c.setFont(TINY);
            c.drawString(words[i].num + "", posX+5+(words[i].start.col)*(squareSize+1), posY+10+(words[i].start.row)*(squareSize+1));
        }
        
        //print the letters
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(board[row][col] != '#'){
                    if(board[row][col] != answerKey[row][col]){
                        c.setColor(new Color(36, 163, 91));
                    }
                    else{
                        c.setColor(DPURPLE);
                    }
                    c.setFont(MEDIUM);
                    c.drawString(answerKey[row][col]+"", posX+12+col*(squareSize+1), posY+22+row*(squareSize+1));
                }
            }
        }
        c.getChar();
    }
    
    public void addScores(String username,char puzzle,int score){ //adds scores to the leaderboard
        try{
            File myFile = new File ("scoresDatabase.txt");      
            if (myFile.exists()){
                PrintWriter pWrite = new PrintWriter (new FileWriter (myFile,true));    //overload with extra parameter (false) overwrites the file rather than appends to the file  
              
                pWrite.println(username+" "+(puzzle+"").toLowerCase()+" "+score);
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
    }
    
    
     public void complete (){ //complete screens
        c.clear();
        String name = "";
        boolean validName = false;
        
        //background
        c.setColor(DPURPLE); 
        c.fillRoundRect(50,50,632,572,20,20);
        c.setColor(LPURPLE);        
        c.fillRoundRect(54,54,624,564,16,16);
           
        c.setColor(PWHITE);
        c.fillRoundRect(291,313,151,27,2,2);  
        c.setColor(DPURPLE);
        
        if (win){
            c.setFont(TITLE);
            c.drawString("yay!",316,325);      
            c.setFont(BODY2);              
            if (choice == 't'){ //timed
                score -= t.mins*100-t.secs;
                
                c.drawString("Congrats! You finished board "+puzzle,212,372);
                c.drawString("in " + t.mins + " minutes and " + t.secs + " seconds",225,396);
                c.drawString("resulting in a score of " + score + "!",238,420);
                
                c.drawString("username:",240,464);
                c.setFont(SMALL);
                c.drawString("(to add you to the leaderboard, press [enter] to proceed)",200,481);
                c.setFont(TINY);
                c.drawString("alphanumeric characters only (no spaces), maximum 10 characters",217,495);
                c.setFont(BODY2);
                while (!validName){
                    c.setColor(DPURPLE);
                    char ch;
                    int i = 0;
                    name = "";                    
                    do{
                        ch = c.getChar();
                        c.setColor(DPURPLE);                        
                        c.drawString(ch+"",360+14*i,463);
                        if ((int) ch == 10){
                            break;
                        } else if ((int) ch == 8){
                            if (name.length()>0){
                                name = name.substring(0,name.length()-1);
                            }                            
                        } else {
                            name += ch;
                        }
                        i++;                         
                        c.setColor(LPURPLE);
                        c.fillRect(346,446,332,23);                         
                        c.setColor(DPURPLE);                         
                        c.drawString(name,360,463);                          
                    } while ((int) ch != 10);

                    if ((name.length() <= 10 && name.length() >= 1) && name.matches("^[a-zA-Z0-9]*$")){
                        validName = true;
                    } else {
                        try { Thread.sleep(500); } catch (Exception e){}
                        new Message("Invalid username, make sure to use a non empty and alphanumeric username that is less than 10 characters long.");
                        c.setColor(LPURPLE);
                        c.fillRect(346,446,332,23);                        
                        name = "";
                    }
                }
                addScores(name,puzzle,score);
                c.setColor(LPURPLE);
                c.fillRect(192,446,486,62);
                c.setColor(DPURPLE);                
                if (!error){
                    c.drawString(name+", your score has been",228,468);    
                    c.drawString("added to the leaderboard.",235,492); 
                } else {
                    c.setFont(MEDIUM);
                    c.drawString("There has been an error with accessing the",216,462);
                    c.drawString("scores file. Make sure scoresDatabase.txt is in the",195,479);
                    c.drawString("same folder as Crossword.java and Game.java.",207,496);
                }
                c.setFont(SMALL);
                c.drawString("press [a] to view the answers, or press [<] to return to the main menu",245,596);                 
                
            } else { //untimed
                c.drawString("Congrats! You finished board " + puzzle + "!",210,371);
                c.drawString("Good job!",320,395);
                c.setColor(DPURPLE);
                c.setFont(SMALL);
                c.drawString("press [a] to view the answers, or press [<] to return to the main menu",245,596);                    
            }
        }
        else{
            c.setFont(TITLE);        
            c.drawString("aw :(",308,325);
            c.setFont(BODY2);
            c.drawString("Better luck next time, play again",207,371);       
            c.drawString(" to give it another shot!",254,395);  
            c.setColor(DPURPLE);
            c.setFont(SMALL);
            c.drawString("press [a] to view the answers, or press [<] to return to the main menu",245,596);                
        }
        char next = '-';
        while (next != 'a' || next != '<'){
            next = c.getChar();
            if (next == 'a'){
                showAnswer();
                break;
            } else if (next == '<') {
                break;
            } else {
                new Message ("Invalid input. Please press [a] to see the answers or [<] to go back to the main menu.");
            }
        }    
    }
    
    public void start () throws java.awt.FontFormatException, java.io.IOException { //starts the game & calls all necessary methods
        c.clear();
        selection();
        readFile();
        counter = 0;
        focused = new Square(words[0].start.row, words[0].start.col);
        posX = 95;
        posY = 103;
        squareSize = 30;
        score = 3000;
        preDisplay();
        if(choice == 't'){
            t = new Timer(c);
            t.start();
        }
        outer: //break out of two loops
        while(counter < words.length){
            focused.row = words[counter].start.row;
            focused.col = words[counter].start.col;
            for(int i = 0; i < words[counter].word.length(); i++){ //each letter in word
                input();
                if(hint){
                    t.mins++;
                }
                update();
                if(backspace){
                    i -= 2;
                }
                if(forward || backward){
                    break;
                }
                win = checkWin();
                if(win || giveUp){
                    if(choice == 't'){
                        t.stop = true;
                    }
                    break outer;
                }
            }
            counter++;
            if(counter >= words.length){
                counter = 0;
            }
        }
        complete();
    }
} // Game class
