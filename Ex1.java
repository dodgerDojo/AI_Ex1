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
		if (this.alg_type.equals("IDS"))
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
    
	/*
	 * Runs IDS algorithm
	 */
    public String runIDS()
    {
        int current_depth = 0;
    	Node iteration_result;

    	// Keep increasing depth until a solution is found
        while(true && current_depth < this.max_depth)
        {
            iteration_result = limitedDFS(this.board[0][0], current_depth);
            
            if(iteration_result != null)
            {
                String path = iteration_result.getPath() + " " + Integer.toString(iteration_result.getCost());
                return path;
            }
            
            current_depth++;
        }
        
        return "no path";
    }
    
	/*
	 * Runs limited by depth DFS algorithm
	 */
    public Node limitedDFS(Node node, int depth)
    {	
        if((this.isTargetNode(node)) && (depth == 0))
        {
        	return node;
        }
        
        if(depth > 0)
        {
            List<Node> neighbours = this.getNeighbours(node);
            
            for(Node neighbour : neighbours)
            {	
				if (node.getPath() != "")
				{
					neighbour.setPath(node.getPath() + "-" + neighbour.direction);
				}
				else
				{
					neighbour.setPath(neighbour.direction);
				}
				
				if(neighbour.getPath().split("-").length > this.max_depth)
				{
					continue;
				}
				
				neighbour.setCost(node.getCost() + neighbour.getCost());
				
                Node node_from_next_iteration = limitedDFS(neighbour, depth-1);
                
                if(node_from_next_iteration != null)
                {
                   return node_from_next_iteration;
                }
            }
        }
        
        return null;
    }
    
	/*
	 * Runs UCS
	 */
	public String runUCS()
	{
		// Create a priority queue which sorts according to the exercise rules
		PriorityQueue<Node> algorithm_queue = new PriorityQueue<Node>(1000, new ExerciseComparator());
		
		algorithm_queue.add(this.board[0][0]);
		
		while(!algorithm_queue.isEmpty())
		{						
			Node node = algorithm_queue.poll();
			
			// If target --> done!
			if (isTargetNode(node))
			{
				String result = node.getPath() + " " + node.getCost(); 
				return result; 
			}
			
			// Check that the node's length isn't too big
			if(node.getPath().split("-").length > this.max_depth)
			{
				continue;
			}
			
			// Get all the neighbours
			List<Node> neighbours = getNeighbours(node);
			
			int size = neighbours.size();
			
			for(int i = 0; i < size; i++)
			{				
				Node neighbour = neighbours.get(i);

				// Update the neighbour's path and cost
				if (!node.getPath().equals(""))
				{
					neighbour.setPath(node.getPath() + "-" + neighbour.direction);
				}
				
				else
				{
					neighbour.setPath(neighbour.direction);
				}
				
				neighbour.setCost(node.getCost() + neighbour.getCost());
					
				algorithm_queue.add(neighbour);
			}		
		}
		
		return "no path";
	}

	/*
	 * Runs the exercise.
	 */
    public static void main(String[] args)
	{
		try
		{
			Ex1 ex1 = new Ex1("input.txt");
			ex1.run();
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
