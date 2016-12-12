import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Ex1
{
	public String alg_type;
	public int board_size;
	public int max_depth;
	public Node board[][];
	public List<Point>  all_possible_moves;
	
	public Ex1(String input_file_path) throws IOException
	{
		// read the input file
		String[] input_lines = readLines(input_file_path);
		
		// fetch all the data from the input file
		this.alg_type = input_lines[0];
		this.board_size = Integer.parseInt(input_lines[1]);
		this.max_depth = ((this.board_size*this.board_size) / 2) + this.board_size; 
		
		// pre-calculate all the 8 possible directions
		this.all_possible_moves = getAllPossibleMoves();
		
		String[] board_lines = Arrays.copyOfRange(input_lines, 2, this.board_size + 2);
		
		// initialize the board's nodes
		this.board = new Node[this.board_size][this.board_size];
		
		for(int i = 0; i < this.board.length; i++)
		{
			for(int j = 0; j < this.board.length; j++)
			{
				this.board[i][j] = new Node(board_lines[i].charAt(j), new Point(i, j));
			}
		}
	}
	
	public void run()
	{
		// run an algorithm according to input
		
		if (this.alg_type == "IDS")
		{
			this.writeStringToFile(this.runIDS(), "output.txt");
		}
		else
		{
			this.writeStringToFile(this.runUCS(), "output.txt");
		}
	}
	
	public boolean isTargetNode(Node node)
	{
		// Target node is the bottom right node
		return (node.point.x == (this.board_size - 1)) && (node.point.y == (this.board_size - 1));
	}
	
	/*
	 *	Returns all the possible neighbours of a specific node.
	 */
	public List<Node> getNeighbours(Node node)
	{	
		List<String> directions = Arrays.asList("R", "RD", "D", "LD", "L", "LU", "U", "RU");
		
		List<Node> neighbours = new ArrayList<Node>();
		
		// For each direction:
		for (int i = 0; i < this.all_possible_moves.size(); i++) 
		{
			Point current_move = this.all_possible_moves.get(i);
			
			// Check if the direction is valid, according to the exercise rules
			if(!isMoveValid(node.point, current_move))
			{
				continue;
			}
			
			// Odd moves are diagonals. Diagonals have special rules (according to the exercise rules)
			if((i % 2 == 1) && !isDiagonalMoveValid(node.point, current_move))
			{
				continue;
			}
			
			// Get the point's coordinates after executing the move
			Point point_after_move = getPointAfterMove(node.point, current_move);
			
			// Get a cloned object of the board's corresponding node
			Node corresponding_node = getNodeByPoint(point_after_move);
			Node cloned_node = new Node(corresponding_node); 
			
			// Update direction and priority (according to the exercise priority definiton)
			cloned_node.direction = directions.get(i);
			cloned_node.priority = i;
			
			// Add to the returned neighbours list
			neighbours.add(cloned_node);
		}
		
		return neighbours;
	}
	
	/*
	 * Returns the board's corresponding node object according to the given coordinates 
	 */
	private Node getNodeByPoint(Point p)
	{
		return this.board[p.x][p.y];
	}
	
	/*
	 * Checks if a given point's coordinates are within the board's borders.
	 */	
	private static boolean isPointInTable(Point p, int table_size)
	{
		return (p.x >= 0) && (p.y >= 0) && (p.x <= table_size - 1) && (p.y <= table_size - 1);
	}
	
	/*
	 * Returns the board's corresponding type (S/D/W/R/G) according to the given coordinates 
	 */
	private char getPointType(Point p)
	{
		return getNodeByPoint(p).data;
	}

	/*
	 * Returns new coordinates according to a given move (right, right down, down, etc...) 
	 */
	private Point getPointAfterMove(Point original_point, Point move)
	{
		return new Point(original_point.x + move.x, original_point.y + move.y);
	}
	
	
	/*
	 * Returns for a given point whether the given move is valid according to the exercise rules
	 */
	private boolean isMoveValid(Point original_point, Point move)
	{
		Point p = getPointAfterMove(original_point, move);
		
		// Check if the point is within board's limits
		if(!isPointInTable(p, this.board_size))
		{
			return false;
		}
		
		// Get the area type (water, hill, etc...)
		char point_data = getPointType(p);
		
		// According to the exercise, no moves to water are allowed
		if(point_data == 'W')
		{
			return false;
		}
		
		return true;
	}
	
	/*
	 * Checks if a diagonal move is valid. According to the rule, you can't execute a diagonal if one of it's directions leads to water
	 */
	private boolean isDiagonalMoveValid(Point original_point, Point move)
	{
		Point point_after_move_x_only = new Point(original_point.x + move.x, original_point.y);
		Point point_after_move_y_only = new Point(original_point.x, original_point.y + move.y);
		
		char point_type_x = getPointType(point_after_move_x_only);
		char point_type_y = getPointType(point_after_move_y_only);
		
		return (point_type_x != 'W') && (point_type_y != 'W');
	}
	
	/*
	 * Returns a constant list of all the possible directions (up, down, etc...)
	 */
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

	/*
	 * Reads text lines from a file
	 */
    public String[] readLines(String filename) throws IOException 
    {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while((line = bufferedReader.readLine()) != null) {
        	System.out.println(line);
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }
    
	/*
	 * Writes a string to a file
	 */
    public void writeStringToFile(String str, String file_path)
    {
        try
        {
            File newTextFile = new File(file_path);
            FileWriter fw = new FileWriter(newTextFile);
            fw.write(str);
            fw.close();
        }
        
        catch (IOException iox) 
        {
            iox.printStackTrace();
        }	
    }

	/*
	 * Clears the board's nodes objects data
	 */
    public void clearBoard()
    {
    	for(int i = 0; i < this.board_size; i++)
    	{
    		for(int j = 0; j < this.board_size; j++)
    		{
    			this.board[i][j].setPath("");
    			this.board[i][j].timestamp = 0;
    			this.board[i][j].cost = 0;
    		}
    	}
    }
    
    public String runIDS()
    {
    	Node result;
        String path;
        int i=0;
        Node begin=this.board[0][0];
        while(true && i<this.max_depth){
            result=depthLimitedSearch(begin,i);
            if(result!=null){
                path=result.getPath()+" "+Integer.toString(result.getCost());
                return path;
            }
            i++;
            System.out.println("depth: " + i);
        }
        return "no path";
    }
    
    public Node depthLimitedSearch(Node temp, int depth)
    {	
        if((depth == 0) && (this.isTargetNode(temp)))
        {
            	System.out.println(temp.getPath() + " " + temp.cost);
                return temp;
        }
        
        if(depth > 0)
        {
            List<Node> tempList;
            
            tempList = this.getNeighbours(temp);
            
            for(Node current_node : tempList)
            {	
				if (temp.getPath() != "")
				{
					current_node.setPath(temp.getPath() + "-" + current_node.direction);
				}
				else
				{
					current_node.setPath(current_node.direction);
				}
				
				if(current_node.getPath().split("-").length > this.max_depth)
				{
					continue;
				}
				
				current_node.setCost(temp.getCost() + current_node.getCost());
				
                Node recNode = depthLimitedSearch(current_node, depth-1);
                
                if(recNode != null)
                {
                   return recNode;
                }
            }
        }
        
        return null;
    }
    
	public String runUCS()
	{
		
		// start with the first cell 0,0
		Node startCell = this.board[0][0];
		
		PriorityQueue<Node> boardQueue = new PriorityQueue<Node>(1000, new UcsComparator());
		
		boardQueue.add(startCell);
		
		// main loop, as long as the tableQueue is not empty:
		while(!boardQueue.isEmpty())
		{						
			// pop the queue
			Node curCell = boardQueue.poll();
			
			// if its the finish cell, just return the solution
			if (isTargetNode(curCell))
			{
				String result = curCell.getPath() + " " + curCell.getCost(); 
				System.out.println(result);
				return result; 
			}
			
			// if the length of the path is greater than the board size, continue
			if(curCell.getPath().split("-").length > this.max_depth)
			{
				continue;
			}
			
			List<Node> neighbours = getNeighbours(curCell);
			
			int size = neighbours.size();
			
			for(int i = 0; i < size; i++)
			{				
				Node node = neighbours.get(i);

				if (!curCell.getPath().equals(""))
				{
					node.setPath(curCell.getPath() + "-" + node.direction);
				}
				
				else
				{
					node.setPath(node.direction);
				}
				
				node.setCost(curCell.getCost() + node.getCost());
					
				boardQueue.add(node);
			}		
		}
		return "no path";
	}

    public static void main(String[] args)
	{
		try
		{
			Ex1 ex1 = new Ex1("C:\\dan\\AI\\HW\\HW1\\AI_Ex1\\Input\\in1.txt");
			
			for(int i = 0; i < ex1.board.length; i++)
			{
				for(int j = 0; j < ex1.board.length; j++)
				{
					System.out.print(ex1.board[i][j].data + "  ");
				}
				
				System.out.println();
			}
			
			ex1.run();
			
		    
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
