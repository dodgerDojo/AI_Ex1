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
	public char board[][];
	
	public Ex1(String input_file_path) throws IOException
	{
		String[] input_lines = readLines(input_file_path);
		
		this.alg_type = input_lines[0];
		this.board_size = Integer.parseInt(input_lines[1]);
		String[] board_lines = Arrays.copyOfRange(input_lines, 2, this.board_size + 2);
		
		System.out.println("alg type:" + this.alg_type);
		System.out.println("alg type:" + this.board_size);
		System.out.println("board:\n" + Arrays.toString(board_lines));
		
		this.board = new char[this.board_size][this.board_size];
		
		for(int i = 0; i < this.board.length; i++)
		{
			for(int j = 0; j < this.board.length; j++)
			{
				this.board[i][j] = board_lines[i].charAt(j);
			}
		}
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
			Ex1 ex1 = new Ex1("C:\\dan\\AI\\HW\\HW1\\input2.txt");
			
			for(int i = 0; i < ex1.board.length; i++)
			{
				for(int j = 0; j < ex1.board.length; j++)
				{
					System.out.print(ex1.board[i][j] + " ");
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
