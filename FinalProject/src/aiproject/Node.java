package aiproject;
import java.util.ArrayList;


public class Node {
		String name ;
		int id;
		int d;
		int f_n;
		int g_n;
		Node parent;
		ArrayList<Ingredient> produce ;
		ArrayList<Node> adjacentNodes;
		
		public Node(String name,int id){
			this.name =name; 
			this.produce = new ArrayList<Ingredient>();
			this.adjacentNodes = new ArrayList<Node>();
			this.id = id;
			
			
			
		}
		
		public void addProduce(String Iname, int Iprice){
			
			this.produce.add(new Ingredient(Iname, Iprice));
			
		}
		public void addEdge(Node end){
			
			this.adjacentNodes.add(end);
			
		}
		
		
}
