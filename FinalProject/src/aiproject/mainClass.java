package aiproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class mainClass {

	public static void main (String args[]){
		
		
		
		//Get user input of the form { DishType,Taste,Cooked_for,Cooked_in,Ingrident_form }
		
		System.out.println("Please enter the number of Dishes you want to cook:");
		Scanner in = new Scanner(System.in);
		int num = in.nextInt();
		System.out.println("Please choose from one of the following:\n1.Buy Ingredents in the same order the dishes are specified.\n2.Buy Ingredients in any order that gives minimum value.");
		Scanner readOption = new Scanner(System.in);
		int option = in.nextInt();
        if((option!=1)&&(option!=2)){
        	System.out.println("Invalid Option!");
        	return;
        }
//		int num =0;
		Fuseki fuseki = new Fuseki();
		Graph grp = new Graph();
		grp.createGraph();
		ArrayList<ArrayList<Node>> possibleNodes = new ArrayList<ArrayList<Node>>();
		ArrayList<String> Ingredients = new ArrayList<String>();
//		fuseki.query("American Indian", "sour sweet", "lunch breakfast", "summer winter", "liquid cut seeds",possibleNodes,grp);
		
		for(int i =0 ; i < num;i++){
			System.out.println("Enter dish details of the form - <DishType>,<Taste>,<Cooked_for>,<Cooked_in>,<Ingrident_form>");
			Scanner in2 = new Scanner(System.in);
			String input = in2.nextLine();
			String split[] = input.split(",");		
			
			if(split.length<5){
				System.out.println("Invalid Input");
				i--;
			}else{
				System.out.println("Choosen Dish for the given details:");
				fuseki.query(split[0],split[1],split[2],split[3],split[4],possibleNodes,grp,Ingredients);
			}
		}
		
		PathFinder pathfinder = new PathFinder();
		PermutationBuilder permutaionBuilder = new PermutationBuilder(possibleNodes);
		ArrayList<ArrayList<Node>> possiblePermutation = null;
		if(option==1){
			possiblePermutation= permutaionBuilder.buildPossibleNodes();
		}else if(option==2){
			possiblePermutation = permutaionBuilder.buildAllPossibleNodes();;
		}
		
		//ArrayList<ArrayList<Node>> possiblePermutation = permutaionBuilder.buildAllPossibleNodes();
		//ArrayList<ArrayList<Node>> possiblePermutation2 = permutaionBuilder.buildPossibleNodes();
		String finalpath= pathfinder.findMinPath(0, possiblePermutation, 1);
		String[] split = finalpath.split(" ");
		HashMap<String, ArrayList<String>> hp =new HashMap<String, ArrayList<String>>();
		buildPossibleItems(hp);
		System.out.println("\nUsing Minimum Path as Heuristic:");
		System.out.println("Complete Path:");
		int counter=0;
		String printStr="";
		for(String s:split ){
			if(counter==0){
				printStr+=s;
				counter++;
			}else{
				printStr+="->"+s;
			}
			
		}
		HashMap<String, Integer> BoughtIngredients =new HashMap<String,Integer>();
		System.out.println(printStr);
		System.out.println("\nDetailed Path:");
		for(String s:split ){
			
			if(hp.get(s)!=null){
				String print= "Go to:"+s+" and buy:";
				for(String y:Ingredients){
					if(BoughtIngredients.get(y)==null){
						if(hp.get(s).contains(y)){
							print += " "+y;
							BoughtIngredients.put(y,1);
						}	
					}
					
				}
				System.out.println(print);
				
			}else{
				
				System.out.println("Go to:"+s);
				
			}
			
			
		}
		
		
		
		finalpath= pathfinder.findMinPath(0, possiblePermutation, 2);
		split = finalpath.split(" ");
		System.out.println("\nUsing Minimum Time as Heuristic:");
		System.out.println("Complete Path:");
		counter=0;
		printStr="";
		for(String s:split ){
			if(counter==0){
				printStr+=s;
				counter++;
			}else{
				printStr+="->"+s;
			}
			
		}
		HashMap<String, Integer> BoughtIngredients2 =new HashMap<String,Integer>();
		System.out.println(printStr);
		System.out.println("\nDetailed Path:");
		for(String s:split ){
			
			if(hp.get(s)!=null){
				String print= "Go to:"+s+" and buy:";
				for(String y:Ingredients){
					if(BoughtIngredients2.get(y)==null){
						if(hp.get(s).contains(y)){
							print += " "+y;
							BoughtIngredients2.put(y,1);
						}	
					}
					
				}
				System.out.println(print);
				
			}else{
				
				System.out.println("Go to:"+s);
				
			}
			
			
		}
		
		
	}

	public static void buildPossibleItems(HashMap<String, ArrayList<String>> hp) {
		hp.put("Kellogs",new ArrayList<String>());
		hp.put("Cargil",new ArrayList<String>());
		hp.put("Profood",new ArrayList<String>());
		hp.put("Precise_food",new ArrayList<String>());
		hp.put("Kraft_food",new ArrayList<String>());
		
		
		hp.get("Kellogs").add("Carrot");
		hp.get("Kellogs").add("Tofu");
		hp.get("Kellogs").add("Pasta");
		hp.get("Kellogs").add("Bread");
		hp.get("Kellogs").add("Noodle");
		hp.get("Kellogs").add("Flour");
		hp.get("Kellogs").add("Onion");
		hp.get("Kellogs").add("Mushroom");
		hp.get("Kellogs").add("Beans");
		hp.get("Kellogs").add("Cheese");
		hp.get("Kellogs").add("Yogurt");
		hp.get("Kellogs").add("Milk");
		
		
		hp.get("Cargil").add("Flour");
		hp.get("Cargil").add("Pasta");
		hp.get("Cargil").add("Bread");
		hp.get("Cargil").add("Tofu");
		hp.get("Cargil").add("Noodle");
		hp.get("Cargil").add("Cheese");
		hp.get("Cargil").add("Yogurt");
		hp.get("Cargil").add("Milk");
		
		hp.get("Kraft_food").add("Whiterice");
		hp.get("Kraft_food").add("Egg");
		hp.get("Kraft_food").add("Chicken");
		hp.get("Kraft_food").add("Brownrice");
		hp.get("Kraft_food").add("Fish");
		
		hp.get("Precise_food").add("Cinnamon");
		hp.get("Precise_food").add("Chicken");
		hp.get("Precise_food").add("Fish");
		hp.get("Precise_food").add("Oregano");
		hp.get("Precise_food").add("Egg");
		hp.get("Precise_food").add("Cumin");
		hp.get("Precise_food").add("Garlic");
		
		hp.get("Profood").add("Beans");
		hp.get("Profood").add("Onion");
		hp.get("Profood").add("Carrot");
		hp.get("Profood").add("Mushroom");
		hp.get("Profood").add("Pasta");
		hp.get("Profood").add("Bread");
		hp.get("Profood").add("Flour");
		hp.get("Profood").add("Brownrice");
		
		
	}
}
