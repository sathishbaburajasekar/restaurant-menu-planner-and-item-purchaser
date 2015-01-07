package aiproject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

public class PathFinder {

	public String findMinPath(int start,ArrayList<ArrayList<Node>> possibleNodes,int heuristicID){
		
		if (heuristicID==1){
			
			ValuePathPair[][] storedValuePath = new ValuePathPair[6][6];
			for(int i =0;i<6;i++){
				for(int j=0;j<6;j++){
					storedValuePath[i][j] = null;
				}
			}
			
			ValuePathPair localMin = new ValuePathPair() ;
			localMin.value = Integer.MAX_VALUE;
			for(ArrayList<Node> arr:possibleNodes ){
				HashMap<String, Integer> hp = new HashMap<String,Integer>();
				int begin = start;
				ValuePathPair accumulateRow = new ValuePathPair();
				accumulateRow.path="";
				accumulateRow.value=0;
				for(Node c : arr){
					if(hp.get(c.name)==null){
					
					ValuePathPair temp;
					if(storedValuePath[begin][c.id]!=null){
//						System.out.println("Using Saved Value!!");
						temp = storedValuePath[begin][c.id];
					}else{
						
						temp=aStarSearch(begin,c.id);
						storedValuePath[begin][c.id] = temp;
					}
					 
					begin = c.id;
					if(!accumulateRow.path.equals("")){
						
						String split[] = temp.path.split(" ");
						for(int i=1;i<split.length;i++){
							accumulateRow.path = accumulateRow.path+" "+split[i];
							hp.put(split[i], 1);
						}
						
					}else{
						String split[] = temp.path.split(" ");
						accumulateRow.path = split[0];
						for(int i=1;i<split.length;i++){
							accumulateRow.path = accumulateRow.path+" "+split[i];
							hp.put(split[i], 1);
						}
					}
//					accumulateRow.path = accumulateRow.path+temp.path;
					accumulateRow.value = accumulateRow.value+temp.value;
//					System.out.print(accumulateRow.path);
//					System.out.println("----> "+accumulateRow.value);
					
						if(accumulateRow.value > localMin.value){
	//						System.out.println("Good!!");
							break; // if at any stage accumulateRow crosses local min , we no longer need that combination.
						}
					
					}
					
				}
				if(accumulateRow.value<localMin.value){

					
					localMin = accumulateRow;
//					System.out.print("Local:"+localMin.path);
//					System.out.println("----> "+localMin.value);
				}
//				System.out.println();
			}
			
			
			return  localMin.path;// use heuristic 1
		}else{

			
			ValuePathPair[][] storedValuePath = new ValuePathPair[6][6];
			for(int i =0;i<6;i++){
				for(int j=0;j<6;j++){
					storedValuePath[i][j] = null;
				}
			}
			
			ValuePathPair localMin = new ValuePathPair() ;
			localMin.value = Integer.MAX_VALUE;
			for(ArrayList<Node> arr:possibleNodes ){
				HashMap<String, Integer> hp = new HashMap<String,Integer>();
				int begin = start;
				ValuePathPair accumulateRow = new ValuePathPair();
				accumulateRow.path="";
				accumulateRow.value=0;
				for(Node c : arr){
					if(hp.get(c.name)==null){
					
					ValuePathPair temp;
					if(storedValuePath[begin][c.id]!=null){
//						System.out.println("Using Saved Value!!");
						temp = storedValuePath[begin][c.id];
					}else{
						
						temp=aStarSearchTime(begin,c.id);
						storedValuePath[begin][c.id] = temp;
					}
					 
					begin = c.id;
					if(!accumulateRow.path.equals("")){
						
						String split[] = temp.path.split(" ");
						for(int i=1;i<split.length;i++){
							accumulateRow.path = accumulateRow.path+" "+split[i];
							hp.put(split[i], 1);
						}
						
					}else{
						String split[] = temp.path.split(" ");
						accumulateRow.path = split[0];
						for(int i=1;i<split.length;i++){
							accumulateRow.path = accumulateRow.path+" "+split[i];
							hp.put(split[i], 1);
						}
					}
//					accumulateRow.path = accumulateRow.path+temp.path;
					accumulateRow.value = accumulateRow.value+temp.value;
//					System.out.print(accumulateRow.path);
//					System.out.println("----> "+accumulateRow.value);
					
						if(accumulateRow.value > localMin.value){
	//						System.out.println("Good!!");
							break; // if at any stage accumulateRow crosses local min , we no longer need that combination.
						}
					
					}
					
				}
				if(accumulateRow.value<localMin.value){

					
					localMin = accumulateRow;
//					System.out.print("Local:"+localMin.path);
//					System.out.println("----> "+localMin.value);
				}
//				System.out.println();
			}
			
			
			return  localMin.path;// use heuristic 1
		
		}
		
	}
	
	
	public ValuePathPair aStarSearchTime(int start,int end){
		String path = "";
		Graph grp = new Graph();
		grp.createGraph();
		grp.IntialiseAStar(start);		
		ArrayList<Node> UsedNodes = new ArrayList<Node>();
		Comparator<Node> cmp = new Comparator<Node>() {
			
			@Override
			public int compare(Node first, Node second) {
				// TODO Auto-generated method stub
				
				if( first.f_n > second.f_n){
					return 1; 
				}else{ 
					return -1;
				}
			}
		};
		
		
		
		Queue<Node> pr = new PriorityQueue<Node>(6, cmp);
		
		pr.add(grp.manufacturers.get(start));
		while( (!pr.isEmpty()) && (pr.peek().id != end)){
			
			Node u = pr.poll();
			
			UsedNodes.add(u);
			System.out.print("");
			for(Node adj:u.adjacentNodes){
				if(!UsedNodes.contains(adj)){
					
					grp.RelaxAStarTime(u, adj,end);
					if(!pr.contains(adj))
						pr.add(adj);
				}
			}
			System.out.print("");
			
		}
		
		path = path+grp.getPath(end);
		ValuePathPair v = new ValuePathPair();
		v.path = path;
		v.value = grp.manufacturers.get(end).f_n;
		
		return  v;
	}
	
	public ValuePathPair aStarSearch(int start,int end){
		String path = "";
		Graph grp = new Graph();
		grp.createGraph();
		grp.IntialiseAStar(start);		
		ArrayList<Node> UsedNodes = new ArrayList<Node>();
		Comparator<Node> cmp = new Comparator<Node>() {
			
			@Override
			public int compare(Node first, Node second) {
				// TODO Auto-generated method stub
				
				if( first.f_n > second.f_n){
					return 1; 
				}else{ 
					return -1;
				}
			}
		};
		
		
		
		Queue<Node> pr = new PriorityQueue<Node>(6, cmp);
		
		pr.add(grp.manufacturers.get(start));
		while( (!pr.isEmpty()) && (pr.peek().id != end)){
			
			Node u = pr.poll();
			
			UsedNodes.add(u);
			System.out.print("");
			for(Node adj:u.adjacentNodes){
				if(!UsedNodes.contains(adj)){
					
					grp.RelaxAStar(u, adj,end);
					if(!pr.contains(adj))
						pr.add(adj);
				}
			}
			System.out.print("");
			
		}
		
		path = path+grp.getPath(end);
		ValuePathPair v = new ValuePathPair();
		v.path = path;
		v.value = grp.manufacturers.get(end).f_n;
		
		return  v;
	}

	
	
}
