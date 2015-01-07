package aiproject;

import java.util.ArrayList;

public class Graph {

	Node resturant;
	ArrayList<Node> manufacturers;
	int[][] straightLineDistance;
	int[][] actualLineDistance;
	int[][] shortestTime;
	int[][] actualTime;
	public Graph() {

		resturant = new Node("resturant", 0);
		this.straightLineDistance = new int[][] { 
				{ 0, 7, 4, 6, 8, 9 },
				{ 7, 0, 8, 11, 16, 14 },
				{ 4, 8, 0, 5, 8, 9 },
				{ 6, 11, 5, 0, 10, 6 }, 
				{ 8, 16, 8, 10, 0, 12 },
				{ 9, 14, 9, 6, 12, 0 } };
		this.actualLineDistance = new int[][] { 
				{ 0, 11, 8, 0, 0, 40 },
				{ 11, 0, 13, 0, 17, 23 }, 
				{ 8, 13, 0, 49, 8, 0 },
				{ 0, 0, 49, 0, 14, 8 },
				{ 0, 17, 8, 14, 0, 20 },
				{ 40, 23, 0, 8, 20, 0 } };
		
		
		this.shortestTime = new int[][] { 
				{ 0, 7,21, 17, 10, 4 },
				{ 7, 0, 15, 5, 14, 11 },
				{ 21, 15, 0, 6, 11, 40 },
				{ 17,5, 6, 0, 21,11 }, 
				{ 10,14,11, 21, 0, 9 },
				{ 4, 11, 40, 11,9, 0 } };
		this.actualTime = new int[][] { 
				{ 0, 17, 30, 0, 0, 4 },
				{ 17, 0, 18, 0, 17, 23 }, 
				{ 30, 18, 0, 6, 18, 0 },
				{ 0,  0, 6, 0, 24, 12 },
				{ 0, 17, 18,24, 0, 9 },
				{ 4, 23, 0, 12, 9, 0 } };
		
		
		
		this.manufacturers = new ArrayList<Node>();
		manufacturers.add(this.resturant);
	}

	public void createGraph() {

		Node kellogs = new Node("Kellogs", 1);
		manufacturers.add(kellogs);
		Node Cargil = new Node("Cargil", 2);
		manufacturers.add(Cargil);
		Node Profood = new Node("Profood", 3);
		manufacturers.add(Profood);
		Node Precise_food = new Node("Precise_food", 4);
		manufacturers.add(Precise_food);
		Node Kraft_food = new Node("Kraft_food", 5);
		manufacturers.add(Kraft_food);

		kellogs.addProduce("Diary", 1);
		kellogs.addProduce("Spices", 2);

		Cargil.addProduce("Fruits", 5);
		Cargil.addProduce("Nuts", 1);

		Profood.addProduce("Vegetables", 11);

		Kraft_food.addProduce("Wheat", 3);
		Kraft_food.addProduce("Rice", 2);

		Precise_food.addProduce("Spice", 4);
		Precise_food.addProduce("Rice", 1);

		this.resturant.addEdge(kellogs);
		this.resturant.addEdge(Cargil);
		this.resturant.addEdge(Kraft_food);

		kellogs.addEdge(this.resturant);
		kellogs.addEdge(Cargil);
		kellogs.addEdge(Precise_food);
		kellogs.addEdge(Kraft_food);
		
		Cargil.addEdge(this.resturant);
		Cargil.addEdge(kellogs);
		Cargil.addEdge(Profood);
		Cargil.addEdge(Precise_food);

		Profood.addEdge(Cargil);
		Profood.addEdge(Precise_food);
		Profood.addEdge(Kraft_food);

		Precise_food.addEdge(kellogs);
		Precise_food.addEdge(Cargil);
		Precise_food.addEdge(Profood);
		Precise_food.addEdge(Kraft_food);

		Kraft_food.addEdge(this.resturant);
		Kraft_food.addEdge(kellogs);
		Kraft_food.addEdge(Profood);
		Kraft_food.addEdge(Precise_food);

	}

	public void IntialiseAStar(int start) {
		for (Node x : manufacturers) {
			x.g_n = Integer.MAX_VALUE;
			x.parent = null;
		}
		manufacturers.get(start).g_n = 0;

	}
	
	public void RelaxAStar(Node u, Node v,int goalID) {
		if(v.g_n > u.g_n+actualLineDistance[u.id][v.id]){
			v.g_n = u.g_n+actualLineDistance[u.id][v.id];
			v.f_n = v.g_n+straightLineDistance[v.id][goalID];
			v.parent = u;
		}
				
		
	}
	public void RelaxAStarTime(Node u, Node v,int goalID) {
		if(v.g_n > u.g_n+actualTime[u.id][v.id]){
			v.g_n = u.g_n+actualTime[u.id][v.id];
			v.f_n = v.g_n+shortestTime[v.id][goalID];
			v.parent = u;
		}
				
		
	}

	public String getPath(int end) {
		return buildString(manufacturers.get(end));

	}

	private String buildString(Node node) {
		if (node == null) {
			return "";
		}
		String y = buildString(node.parent);
		if(y.equals("")){
			return node.name;
		}else{
			return  y+ " " + node.name;	
		}
		
	}
	
	public Node get(String name){
		
		for(Node x: manufacturers){
			if(x.name.equals(name)){
				return x;
			}
		}
		
		return null;
	}

}
