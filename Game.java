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
    int counter;
    char input;
    boolean win, forward, backward, giveUp;
	 
    public Game (Console con){ //constructor
	c = con;
    }
    
    public void readFile () throws IOException{ 
	BufferedReader br = new BufferedReader(new FileReader("testpuzzle.txt"));
	answerKey = new char[5][5];
	board = new char[5][5];
	words = new Word[6];
	//reads puzzle file for answerkey board
	for(int row = 0; row < 5; row++){
	    String line = br.readLine();
	    for(int col = 0; col < 5; col++){
		answerKey[row][col] = line.charAt(col);
	    }
	}
	//replicates blank board from answerkey
	for(int row = 0; row < 5; row++){
	    for(int col = 0; col < 5; col++){
		if(answerKey[row][col] == '#'){
		    board[row][col] = '#';
		}
		else{
		    board[row][col] = ' ';
		}
	    }
	}
	//reads puzzle file for words
	int temp = Integer.parseInt(br.readLine());
	for(int i = 0; i < temp; i++){
	    String[] line = br.readLine().split(" ");
	    words[i] = new Word(Integer.parseInt(line[0]), line[1], new Square(Integer.parseInt(line[2].split(",")[0]), Integer.parseInt(line[2].split(",")[1])), line[3].charAt(0));
	} 
    }
    
    public void display (){
	c.clear();
	for(int row = 0; row < 5; row++){ //printing the table
	    for(int col = 0; col < 5; col++){
		if(board[row][col] == '#'){ //black squares
		    c.fillRect(10+col*30, 10+row*30, 31, 31);
		}
		else{ 
		    c.drawRect(10+col*30, 10+row*30, 30, 30); //blank square
		}
	    }
	}
	for(int i = 0; i < words.length; i++){ //printing the little numbers
	    c.setFont(new Font("Serif", Font.PLAIN, 9));
	    c.drawString(words[i].num + "", 15+(words[i].start.col)*30, 20+(words[i].start.row)*30);
	    c.setFont(new Font("Serif", Font.PLAIN, 12));
	}
	c.drawRect(11+focused.col*30, 11+focused.row*30, 28, 28); //focused square
	c.setCursor(10, 3);
	c.println("press * to give up");
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
    
    public void update(){
	//erasing
	c.setColor(Color.white);
	c.fillRect(20+focused.col*30, 20+focused.row*30, 15, 15);
	c.drawRect(11+focused.col*30, 11+focused.row*30, 28, 28);
	/*if(checkWord()){ //if word is correct
	    c.setColor(new Color(200, 240, 190, 10));
	    for(int i = 0; i < words[counter].word.length(); i++){
		c.fillRect(10+words[counter].start.col*30, 10+words[counter].start.col*30, 30, 30);
	    }
	    c.println("correct word!");
	}
	else{
	    c.setColor(Color.yellow);
	    c.fillRect(11+words[counter].start.col*30, 10+words[counter].start.row*30, words[counter].word.length()*30, 30);
	}*/
	c.setColor(Color.black);
	if(!forward && !backward){
	    c.drawString(input+"", 22+focused.col*30, 30+focused.row*30); //printing character
	    if(words[counter].direction == 'a'){ //changing focus to next square
		focused.col++;
	    }
	    else{
		focused.row++;
	    }
	}
	else{
	   counter++; 
	}
	//printing focused square
	if(focused.row < 5 && focused.col < 5 && answerKey[focused.row][focused.col] != '#'){ //only prints focus square if it's a valid blank square
	    //nothing changes here
	}
	else if(counter < 5){ //move to next word
	    focused.row = words[counter+1].start.row;
	    focused.col = words[counter+1].start.col;
	}
	else{ //counter is 5
	    focused.row = 0;
	    focused.col = 0;
	}
	c.drawRect(11+focused.col*30, 11+focused.row*30, 28, 28);
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
    
    public boolean checkWord (){ //check if single word matches answerKey
	for(int i = 0; i < words[counter].word.length(); i++){
	    if(words[counter].direction == 'a'){
		if(board[words[counter].start.row][words[counter].start.col + i] != words[counter].word.charAt(i)){
		    return false;
		}
	    }
	    else{
		if(board[words[counter].start.row + i][words[counter].start.col] != words[counter].word.charAt(i)){
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
	readFile();
	counter = 0;
	focused = new Square(0, 0);
	display();
	c.setCursor(10, 5);
	while(!win && !giveUp){ //keep going until win
	    do{
		focused.row = words[counter].start.row;
		focused.col = words[counter].start.col; 
		for(int i = 0; i < words[counter].word.length(); i++){ //each letter in word
		    if(giveUp){
			break;
		    }
		    input();
		    update();
		    if(forward){
			break;
		    }
		    win = checkWin();
		    if(win){
			break;
		    }
		}
		if(!forward){
		    counter++;
		}
	    }
	    while(counter < words.length && !win && !giveUp); //rotating through word list
	    counter = 0;
	}
	complete();
    }
} // Game class