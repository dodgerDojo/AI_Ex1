import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ex1
{
	public String alg_type;
	public int board_size;
	public Node board[][];
	
	static class Node
	{ 
		public char data; 
		public boolean visited; 
		public Point point;
		
		Node(char data, Point p) 
		{ 
			this.data=data;
			this.visited=false;
			this.point = p;
		}
		
		@Override
		public String toString()
		{
			return "Data: " + this.data + " visited: " + this.visited + " point: " + this.point.toString() + "\n";
		}
	}
	
	public Ex1(String input_file_path) throws IOException
	{
		String[] input_lines = readLines(input_file_path);
		
		this.alg_type = input_lines[0];
		this.board_size = Integer.parseInt(input_lines[1]);
		
		
		String[] board_lines = Arrays.copyOfRange(input_lines, 2, this.board_size + 2);
		
		System.out.println("alg type:" + this.alg_type);
		System.out.println("alg type:" + this.board_size);
		System.out.println("board:\n" + Arrays.toString(board_lines));
		
		this.board = new Node[this.board_size][this.board_size];
		
		for(int i = 0; i < this.board.length; i++)
		{
			for(int j = 0; j < this.board.length; j++)
			{
				this.board[i][j] = new Node(board_lines[i].charAt(j), new Point(i, j));
			}
		}
	}
	
	public List<Node> getNeighbours(Node node)
	{
		List<Point> all_possible_moves = getAllPossibleMoves();
		List<Node> neighbours = new ArrayList<Node>();
		
		for (int i = 0; i < all_possible_moves.size(); i++) 
		{
			Point current_move = all_possible_moves.get(i);
			
			System.out.println(current_move);
			
			if(!isMoveValid(node.point, current_move))
			{
//				System.out.println("bad!");
				continue;
			}
			
			// odd moves are diagonals.
			if((i % 2 == 1) && !isDiagonalMoveValid(node.point, current_move))
			{
//				System.out.println("bad diagonal!");
				continue;
			}
			
//			System.out.println("good!");
			Point point_after_move = getPointAfterMove(node.point, current_move);
			neighbours.add(getNodeByPoint(point_after_move));
		}
		
		return neighbours;
	}
	
	private Node getNodeByPoint(Point p)
	{
		return this.board[p.x][p.y];
	}
	
	private static boolean isPointInTable(Point p, int table_size)
	{
		return (p.x >= 0) && (p.y >= 0) && (p.x <= table_size - 1) && (p.y <= table_size - 1);
	}
	
	private char getPointType(Point p)
	{
		return getNodeByPoint(p).data;
	}

	private Point getPointAfterMove(Point original_point, Point move)
	{
		return new Point(original_point.x + move.x, original_point.y + move.y);
	}
	
	
	private boolean isMoveValid(Point original_point, Point move)
	{
		Point p = getPointAfterMove(original_point, move);
		
		if(!isPointInTable(p, this.board_size))
		{
			return false;
		}
		
		System.out.println(p);
		
		char point_data = getPointType(p);
		
		if(point_data == 'W')
		{
			return false;
		}
		
		return true;
	}
	
	private boolean isDiagonalMoveValid(Point original_point, Point move)
	{
		Point point_after_move_x_only = new Point(original_point.x + move.x, original_point.y);
		Point point_after_move_y_only = new Point(original_point.x, original_point.y + move.y);
		
		char point_type_x = getPointType(point_after_move_x_only);
		char point_type_y = getPointType(point_after_move_y_only);
		
		return (point_type_x != 'W') && (point_type_y != 'W');
	}
	
	private static List<Point> getAllPossibleMoves()
	{
		List<Point> all_moves = new ArrayList<Point>();
		
		all_moves.add(new Point(0, 1)); // R
		all_moves.add(new Point(1, 1)); // RD
		all_moves.add(new Point(1, 0)); // D
		all_moves.add(new Point(1, -1)); // LD
		
		all_moves.add(new Point(0, -1)); // L
		all_moves.add(new Point(-1, -1)); // LU
		all_moves.add(new Point(-1, 0)); // U
		all_moves.add(new Point(-1, 1)); // RU
		
		return all_moves;
	}

    public String[] readLines(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
        	System.out.println(line);
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }

    
    public static void main(String[] args)
	{
		try
		{
			Ex1 ex1 = new Ex1("C:\\dan\\AI\\HW\\HW1\\input.txt");
			
			for(int i = 0; i < ex1.board.length; i++)
			{
				for(int j = 0; j < ex1.board.length; j++)
				{
					System.out.print(ex1.board[i][j].data + "  ");
				}
				
				System.out.println();
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
