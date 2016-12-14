import java.util.Comparator;

/*
 * Compares nodes according to exercise rules
 */
public class ExerciseComparator implements Comparator<Node>
{
	//	Initialize the comparator object
	public ExerciseComparator() 
	{
	}

	@Override
	/*
	 * Compare order:
	 * 	- cost
	 * 	- birth time [timestamp]
	 * - priority [R > RD > ... ]
	 */
	public int compare(Node node1, Node node2)
	{
		if(node1.getCost() < node2.getCost())
		{
			return -1;
		}

		else if(node1.getCost() > node2.getCost())
		{
			return 1;
		}

		else if(node1.getTimestamp() < node2.getTimestamp())
		{
			return -1;
		}

		else if(node1.getTimestamp() > node2.getTimestamp())
		{
			return 1;
		}

		else if(node1.getPriority() > node2.getPriority())
		{
			return -1;
		}

		else
		{
			return 1;
		}
	}
}