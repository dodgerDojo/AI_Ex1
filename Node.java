import java.awt.Point;

public class Node
	{ 
		public char data; 
		public int cost;
		public int timestamp; 
		public Point point;
		String direction;
		String path;
		int priority;
		
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
		
		Node(Node n) 
		{ 
			this.data = n.data;
			this.cost = n.cost;
			this.timestamp = n.timestamp;
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

		public int getTimestamp()
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