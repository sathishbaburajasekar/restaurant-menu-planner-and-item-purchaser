package aiproject;

import java.util.ArrayList;

public class PermutationBuilder {
	ArrayList<ArrayList<Node>> temporaryHolder;
	ArrayList<ArrayList<Node>> possibleNodes;
	ArrayList<ArrayList<Node>> possiblePermutations;
	int count =0;
	public PermutationBuilder(ArrayList<ArrayList<Node>> temporaryHolder){
		
		this.temporaryHolder = temporaryHolder;
		this.possiblePermutations = new ArrayList<ArrayList<Node>>();
		this.possibleNodes = new ArrayList<ArrayList<Node>>();
		
	}
	
	public ArrayList<ArrayList<Node>> buildPossibleNodes(){
		this.possibleNodes = this.temporaryHolder;
		this.possiblePermutations = new ArrayList<ArrayList<Node>>();
		this.buildPermutation();
		return possiblePermutations;
	}
	public ArrayList<ArrayList<Node>> buildAllPossibleNodes(){
		
		int[] selected = new int[temporaryHolder.size()];
		for(int i = 0 ; i<selected.length;i++){
			selected[i] =0 ;
		}
		this.possiblePermutations = new ArrayList<ArrayList<Node>>();
		permute(temporaryHolder,temporaryHolder.size(),selected);		
		return possiblePermutations;
	}
	public void permute(ArrayList<ArrayList<Node>> NodeCollection, int remainingLength,int[] selected) {
		if(remainingLength==0){
			// put things into possiple permuations.
			count++;
			this.buildPermutation();
			return;
		}
		
		for(int i =0;i<NodeCollection.size();i++){
			
			if(selected[i]!=1){
				selected[i]=1;
				possibleNodes.add(NodeCollection.get(i));
				permute(temporaryHolder,remainingLength-1,selected);	
				possibleNodes.remove(possibleNodes.size()-1);
				selected[i]=0;
			}
		}
		
	}


	public void buildPermutation(){
		
		for(int i =0 ; i <possibleNodes.get(0).size();i++ ){
			
			ArrayList<Node> temporary = new ArrayList<Node>();
			temporary.add(possibleNodes.get(0).get(i));
			addFurther(temporary,1,possibleNodes.size());
		}
		
		
		
//		return possiblePermutations;
	}
	
	public void addFurther(ArrayList<Node> currentNodes, int currentID, int size) {
		if(currentID==size){
			possiblePermutations.add(currentNodes);
			return;
		}
		
		for(int i =0 ; i <possibleNodes.get(currentID).size();i++ ){
			ArrayList<Node> temporary = new ArrayList<>();
			temporary.addAll(currentNodes);
			temporary.add(possibleNodes.get(currentID).get(i));
			addFurther(temporary,currentID+1,size);
		}
		
		
	}
	
	
	
}
