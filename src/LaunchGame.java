package tac;
import java.util.Random;
import java.util.Scanner;

class tac
{
    static char[][] board;
	public tac()
	{
		board = new char[3][3];
		initboard();
	}
	void initboard()
	{
		for(int i=0;i<board.length;i++)
		{
			for(int j=0;j<board[i].length;j++)
			{
				board[i][j]=' ';
			}
		}
	}
    static void disp_board()
    {
    	System.out.println();
    	System.out.println(" -------------");
    	for(int i=0;i<board.length;i++)
    	{
    		System.out.print(" | ");
			for(int j=0;j<board[i].length;j++)
			{
				System.out.print(board[i][j]+ " | ");
			}
			System.out.println();
			System.out.println(" -------------");
    	}	
    }
    static void statdisp()
    {
    	System.out.println("\n   0    1    2");
    	System.out.println("  -------------");
    	for(int i=0;i<board.length;i++)
    	{
    		System.out.print(i+" | ");
			for(int j=0;j<board[i].length;j++)
			{
				System.out.print(board[i][j]+ " | ");
			}
			System.out.println();
			System.out.println("  -------------");
    	}	
    }
    static void place_mark(int row,int col,char mark)
    {
    	if(row>=0 && row<=2 && col>=0 && col<=2)
    	{
    		board[row][col]=mark;
    	}
    	else
    	{
    		System.out.println("Invalid position!");
    	}
    }
    static boolean check_col_win()
    {
    	for(int j=0;j<=2;j++)
    	{
    		if(board[0][j]!=' ' && board[0][j]==board[1][j] && board[1][j]==board[2][j])
    		{
    			return true;
    		}
    	}
    	return false;
    }
    static boolean check_row_win()
    {
    	for(int i=0;i<=2;i++)
    	{
    		if(board[i][0]!=' ' && board[i][0]==board[i][1] && board[i][1]==board[i][2])
    		{
    			return true;
    		}
    	}
    	return false;
    }
    static boolean check_diagonal_win()
    {
    		if((board[0][0]!=' '&& board[0][0]==board[1][1] 
    			&& board[1][1]==board[2][2] )||
    			(board[0][2]!=' ' && board[0][2]==board[1][1] 
    			&& board[1][1]==board[2][0]))
    		{
    			return true;
    		}
    	return false;
    }
    static boolean checkDraw()
    {
    	for(int i=0;i<=2;i++)
    	{
    		for(int j=0;j<=2;j++)
    		{
    			if(board[i][j]== ' ')
    			{
    				return false;
    			}
    		}
    	}
    	return true;
    }
}
abstract class Player
{
	String name;
	char mark;
	abstract void makeMove();
	boolean isValidMove(int row,int col)
	{
		if(row>=0 && row <=2 && 
			col>=0 && col<=2)
		{
			if(tac.board[row][col] == ' ')//check if the board is equal to space
			{
				return true;//yes - valid move
			}
		}
		return false;
	}
}
class HumanPlayer extends Player
{
	HumanPlayer(String name,char mark)
	{
		this.name=name;
		this.mark=mark;
	}
	void makeMove()
	{ 
		int row,col;
		Scanner sc=new Scanner(System.in);
		do//keep taking row and column as long as it is invalid move
		{
			System.out.println("Enter the row and col:");
			row=sc.nextInt();
			col=sc.nextInt();
		}while(!isValidMove(row,col));
		/*validMove is true when the box is empty so if this loop will keep repeating 
		i,e validMove=true & !validMove=!true=false which means the box is not empty and 
		you cannot place any element there so it will again ask the user to enter at valid location
		suppose we already have 'X' at index : 2,1 - and we try to put element at the same index, so it will be
		(!isValidMove(2,1)=> isValidMove function will say false because it is not empty => (!false(2,1)) => (true(2,1)) 
		=> invalid move => repeat*/ 
		tac.place_mark(row, col, mark);//the moment it is a valid move (element is placed at empty location) it will be marked on the board
	}
}   
class AIPlayer extends Player
{
	AIPlayer(String name,char mark)
	{
		this.name=name;
		this.mark=mark;
	}
	void makeMove()
	{ 
		int row,col;
		Scanner sc=new Scanner(System.in);
		do//keep taking row and column as long as it is invalid move
		{
			Random r=new Random();
			row=r.nextInt(3);
			col=r.nextInt(3);
		}while(!isValidMove(row,col));
		tac.place_mark(row, col, mark);//the moment it is a valid move (element is placed at empty location) it will be marked on the board
	}
}   
public class LaunchGame 
{
	public static void main(String[] args) 
	{
		Scanner sc=new Scanner(System.in);
		tac t = new tac();
		System.out.println("*GAME INSTRUCTIONS TO PLAY:*\nPlace a mark.\n" +
				" - Rows are numbered 0–2 (on the left)\n" + 
				" - Columns are numbered 0–2 (on the top)\n" +
				"\n-:A sneak peek of YOUR board to help YOU go through the GAME :-");
		tac.statdisp();
	    	
		System.out.println("Example: 0 1 means row 0, column 1"+"\n" +
				"Example: 1 2 means row 1, column 2"+"\n");
		
		System.out.println("Choose game mode:");
		System.out.println("1. Human vs Human");
		System.out.println("2. Human vs AI");
		int choice=sc.nextInt();
		
		Player p1,p2;
		if(choice == 1)
		{
			p1 = new HumanPlayer("Player 1", 'X');
			p2 = new HumanPlayer("Player 2", 'O');
			System.out.println("\nLet's Begin!\n");
		}
		else
		{
			p1 = new HumanPlayer("Human Player", 'X');
	        p2 = new AIPlayer("AI", 'O');
	        System.out.println("\nLet's Begin!\n");
		}
        Player cp;
        cp = p1; // current player
        while (true) { // run infinite loop
            System.out.println(cp.name + "'s turn-");
            
            cp.makeMove();
            tac.disp_board();
            System.out.println("\n");
            if (tac.check_row_win() || tac.check_col_win() || tac.check_diagonal_win()) 
            {
                System.out.println(cp.name + " won!!!");
                break; 
            }
            else if(tac.checkDraw())
            {
            	 System.out.println("Game is Draw!");
            	 break;
            }
            else
            {
            
            	if (cp == p1) 
            	{
            		cp = p2;
            	} 
            	else 
            	{
            		cp = p1;
            	}
            }
        }
    }
}
