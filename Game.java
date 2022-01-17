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
    char[][] answerKey;
    char[][] board;
    Word[] words;
    Square focused;
    int counter;
    char input;
    boolean reloop;
    boolean win;
	 
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
    }
    
    public void input (){ //takes user input and stores in board
	input = c.getChar();
	board[focused.row][focused.col] = input;
    }
    
    public void update(){
	//erasing
	c.setColor(Color.white);
	c.fillRect(20+focused.col*30, 20+focused.row*30, 15, 15);
	c.drawRect(11+focused.col*30, 11+focused.row*30, 28, 28);
	c.setColor(Color.black);
	c.drawString(input+"", 22+focused.col*30, 30+focused.row*30); //printing character
	if(words[counter].direction == 'a'){ //changing focus to next square
	    focused.col++;
	}
	else{
	    focused.row++;
	}
	//printing focused square
	if(focused.row < 5 && focused.col < 5 && answerKey[focused.row][focused.col] != '#'){ //only prints focus square if it's a valid blank square
	    c.drawRect(11+focused.col*30, 11+focused.row*30, 28, 28);
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
    
    public boolean checkWin (){
	for(int row = 0 ; row < answerKey.length; row++){
	    for(int col = 0; col < answerKey[0].length; col++){
		if(answerKey[row][col] != board[row][col]){
		    return false;
		}
	    }
	}
	return true;
    }
    
    public void complete (){ //complete screen
	c.clear();
	c.println("yay ur done tm");
	c.println("press any key to return to main menu");
	c.getChar();
    }
    
    public void start () throws IOException{
	readFile();
	counter = 0;
	focused = new Square(0, 0);
	display();
	c.setCursor(10, 5);
	while(!win){ //keep going until win
	    do{
		focused.row = words[counter].start.row;
		focused.col = words[counter].start.col; 
		for(int i = 0; i < words[counter].word.length(); i++){ //each letter in word
		    input();
		    update();
		    win = checkWin();
		}
		counter++;
	    }
	    while(counter < words.length && !win); //rotating through word list
	    counter = 0;
	}
	complete();
    }
} // Game class