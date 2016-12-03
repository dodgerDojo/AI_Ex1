import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ex1
{
	private static void readFile(File fin) throws IOException {
		FileInputStream fis = new FileInputStream(fin);
	 
		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
	 
		br.close();
	}
	public static void main(String[] args)
	{
		// wooho!
		File input_file = new File("C:\\dan\\AI\\HW\\HW1\\input.txt");
		try
		{
			readFile(input_file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
