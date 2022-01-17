// The "Game" class.
import java.awt.*;
import hsa.Console;
import java.io.*;

public class Game
{   
    class Square
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
    char[][] answerKey, board;
    Word[] words;
    Square focused;
    int counter, size, numWords;
    char input;
    boolean win, forward, backward, giveUp;
    boolean[] status;
         
    public Game (Console con){ //constructor
        c = con;
    }
    
    public void readFile () throws IOException{ 
        BufferedReader br = new BufferedReader(new FileReader("testpuzzle.txt"));
        //reads puzzle file for board size
        size = Integer.parseInt(br.readLine());
        answerKey = new char[size][size];
        board = new char[size][size];
        //reads puzzle file for answerkey board
        for(int row = 0; row < size; row++){
            String line = br.readLine();
            for(int col = 0; col < size; col++){
                answerKey[row][col] = line.charAt(col);
            }
        }
        //replicates blank board from answerkey
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
    }
    
    public void preDisplay(){ //default board before input
        //background
        c.setColor(Color.orange);
        c.fillRect(5, 5, 160, 160);
        c.setColor(Color.white);
        c.fillRect(10, 10, 150, 150);
        //printing the table
        c.setColor(Color.black);
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(board[row][col] == '#'){ //black squares
                    c.fillRect(10+col*30, 10+row*30, 31, 31);
                }
                else{ 
                    c.drawRect(10+col*30, 10+row*30, 30, 30); //blank squares
                }
            }
        }
        for(int i = 0; i < words.length; i++){ //printing the little numbers
            c.setFont(new Font("Serif", Font.PLAIN, 9));
            
            c.drawString(words[i].num + "", 15+(words[i].start.col)*30, 20+(words[i].start.row)*30);
            c.setFont(new Font("Serif", Font.PLAIN, 12));
        }
        //default focused word
        c.setColor(Color.blue);
        if(words[counter].direction == 'a'){
            c.drawRect(10+words[counter].start.col*30, 10+words[counter].start.row*30, words[counter].word.length()*30, 30); 
            c.drawRect(10-1+words[counter].start.col*30, 10-1+words[counter].start.row*30, words[counter].word.length()*30+2, 32); 
        }
        else if(words[counter].direction == 'd'){
            c.drawRect(10+words[counter].start.col*30, 10+words[counter].start.row*30, 30, words[counter].word.length()*30); 
            c.drawRect(10-1+words[counter].start.col*30, 10-1+words[counter].start.row*30, 32, words[counter].word.length()*30+2); 
        }
        //default focused square
        c.setColor(Color.black);
        c.drawRect(11+focused.col*30, 11+focused.row*30, 28, 28); 
        c.drawRect(11-1+focused.col*30, 11-1+focused.row*30, 30, 30);
        c.drawRect(11-2+focused.col*30, 11-2+focused.row*30, 32, 32);
        //give up message
        c.setCursor(10, 3);
        c.println("press * to give up");
    }
    
    public void display (){
        //clear the board background
        c.setColor(Color.orange);
        c.fillRect(5, 5, 160, 160);
        c.setColor(Color.white);
        c.fillRect(10, 10, 150, 150);
        
        //draw the correct green bgs
        c.setColor(new Color(190, 255, 210));
        for(int i = 0; i < words.length; i++){
            if(checkWord(i) == true){
                if(words[i].direction == 'a'){
                    c.fillRect(10+words[i].start.col*30, 10+words[i].start.row*30, 30*words[i].word.length(), 30);
                }
                else{
                   c.fillRect(10+words[i].start.col*30, 10+words[i].start.row*30, 30, 30*words[i].word.length()); 
                }
            }
        }
        
        //print the board
        c.setColor(Color.black);
        for(int row = 0; row < size; row++){ 
            for(int col = 0; col < size; col++){
                if(board[row][col] == '#'){ //black squares
                    c.fillRect(10+col*30, 10+row*30, 31, 31);
                }
                else{ 
                    c.drawRect(10+col*30, 10+row*30, 30, 30); //blank square
                }
            }
        }
        
        //print the numbers
        for(int i = 0; i < words.length; i++){ 
            c.setFont(new Font("Serif", Font.PLAIN, 9));
            c.drawString(words[i].num + "", 15+(words[i].start.col)*30, 20+(words[i].start.row)*30);
            c.setFont(new Font("Serif", Font.PLAIN, 12));
        }
        
        //print the letters
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                c.drawString(board[row][col]+"", 23+col*30, 30+row*30);
            }
        }
        
        //change focus
        if(words[counter].direction == 'a' && focused.col < size){ //changing focus to next square
            focused.col++;
        }
        else if(words[counter].direction == 'd' && focused.row < size){
            focused.row++;
        }
        
        //print the focuses
        //drawing word focus
        c.setColor(Color.blue);
        if(!forward && focused.row < size && focused.col < size && answerKey[focused.row][focused.col] != '#'){
            if(words[counter].direction == 'a'){
                c.drawRect(10+words[counter].start.col*30, 10+words[counter].start.row*30, words[counter].word.length()*30, 30);
                c.drawRect(10-1+words[counter].start.col*30, 10-1+words[counter].start.row*30, words[counter].word.length()*30+2, 32);
            }
            else{
                c.drawRect(10+words[counter].start.col*30, 10+words[counter].start.row*30, 30, words[counter].word.length()*30);
                c.drawRect(10-1+words[counter].start.col*30, 10-1+words[counter].start.row*30, 32, words[counter].word.length()*30+2);
            }
        }
        else if(counter < words.length-1){ 
            if(words[counter+1].direction == 'a'){
                c.drawRect(10+words[counter+1].start.col*30, 10+words[counter+1].start.row*30, words[counter+1].word.length()*30, 30);
                c.drawRect(10-1+words[counter+1].start.col*30, 10-1+words[counter+1].start.row*30, words[counter+1].word.length()*30+2, 32);
            }
            else{
                c.drawRect(10+words[counter+1].start.col*30, 10+words[counter+1].start.row*30, 30, words[counter+1].word.length()*30);
                c.drawRect(10-1+words[counter+1].start.col*30, 10-1+words[counter+1].start.row*30, 32, words[counter+1].word.length()*30+2);
            }
        }
        else{ //counter is at last index, restart
            if(words[0].direction == 'a'){
                c.drawRect(10+words[0].start.col*30, 10+words[0].start.row*30, words[0].word.length()*30, 30);
                c.drawRect(10-1+words[0].start.col*30, 10-1+words[0].start.row*30, words[0].word.length()*30+2, 32);
            }
            else{
                c.drawRect(10+words[0].start.col*30, 10+words[0].start.row*30, 30, words[0].word.length()*30);
                c.drawRect(10-1+words[0].start.col*30, 10+words[0].start.row*30, 32, words[0].word.length()*30+2);
            }
        }
        //setting square focus
        c.setColor(Color.black);
        if(!forward && focused.row < size && focused.col < size && answerKey[focused.row][focused.col] != '#'){ //next square is valid blank square
            
        }
        else if(counter < words.length-1){ //move to next word
            //setting square focus
            focused.row = words[counter+1].start.row;
            focused.col = words[counter+1].start.col;
        }
        else{ //counter is at last index, restart
            focused.row = words[0].start.row;
            focused.col = words[0].start.col;
        }
        c.drawRect(11+focused.col*30, 11+focused.row*30, 28, 28); //printing focused square
        c.drawRect(11-1+focused.col*30, 11-1+focused.row*30, 30, 30);
        c.drawRect(11-2+focused.col*30, 11-2+focused.row*30, 32, 32);
    }
    
    public void input (){ //takes user input and stores in board
        forward = false;
        backward = false;
        input = c.getChar();
        if(input == 60){
            backward = true;
        }
        else if(input == 62){
            forward = true;
        }
        else if(input == 42){
            giveUp = true;
        }
        else{
            board[focused.row][focused.col] = input;
        }
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
    
    public void complete (){ //complete screen
        c.clear();
        if(win){
            c.println("yay ur done tm");
            c.println("press any key to return to main menu");
            c.getChar();
        }
        else{
            c.println("better luck next time :(");
            c.println("press any key to return to main menu");
            c.getChar();
        }
    }
    
    public void start () throws IOException{
        c.clear();
        readFile();
        counter = 0;
        focused = new Square(0, 0);
        c.setCursor(10, 5);
        preDisplay();
        status = new boolean[words.length];
        outer: //break out of two loops
        while(counter < words.length){
            focused.row = words[counter].start.row;
            focused.col = words[counter].start.col;
            for(int i = 0; i < words[counter].word.length(); i++){ //each letter in word
                input();
                if(forward){
                    break;
                }
                if(backward){
                    counter -= 2;
                    break;
                }
                display();
                win = checkWin();
                if(win || giveUp){
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