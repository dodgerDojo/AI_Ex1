import java.awt.Point;
import java.util.concurrent.atomic.AtomicLong;

/*
 *	Resembles a cell in the game's board.
 */
public class Node
{ 
		public char data; 
		public int cost;
		public long timestamp; 
		public Point point;
		String direction;
		String path;
		int priority;
		final static AtomicLong atomic_long = new AtomicLong();
		
		// builds node object from scratch
		Node(char data, Point p) 
		{ 
			this.data = data;
			this.cost = dataToCost(this.data);
			this.timestamp = 0;
			this.point = p;
			this.direction = "";
			this.path = "";
			this.priority = 0;
		}
		
		/*
		 *	Returns each data type's cost according to exercise
		 */
		private int dataToCost(int data)
		{
			if(data == 'R')
			{
				return 1;
			}
			else if(data == 'D')
			{
				return 3;
			}
			else if(data == 'H')
			{
				return 10;
			}
			
			return 0;
		}
		
		/*
		 *	Copy constructor.
		 *  Each time a node is created, update timestamp.
		 */
		Node(Node n) 
		{ 
			this.data = n.data;
			this.cost = n.cost;
			this.timestamp = atomic_long.getAndIncrement();
			this.point = n.point;
			this.direction = n.direction;
			this.path = n.path;
			this.priority = n.priority;
		}
		
		@Override
		public String toString()
		{
			return "Direction: " + this.direction + " point: " + this.point.toString() + "\n";
		}

		public String getPath()
		{
			return this.path;
		}

		public void setPath(String path)
		{
			this.path = path;
		}

		public int getCost()
		{
			return this.cost;
		}

		public void setCost(int cost)
		{
			this.cost = cost;
		}

		public long getTimestamp()
		{
			return this.timestamp;
		}

		public void setTimestamp(int timestamp)
		{
			this.timestamp = timestamp;
		}

		public int getPriority()
		{
			return this.priority;
		}

		public void setPriority(int priority)
		{
			this.priority = priority;
		}
	}