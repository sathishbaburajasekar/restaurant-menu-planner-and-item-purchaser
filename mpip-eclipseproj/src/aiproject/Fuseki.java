package aiproject;

import java.util.ArrayList;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class Fuseki {
	
	
	
	public void query(String DishType,String Taste,String Cooked_for,String Cooked_in,String Ingridient_form,ArrayList<ArrayList<Node>> possibleNodes,Graph grp,ArrayList<String> Ingredients){
		
		
		String queryString ="PREFIX ab:<http://www.semanticweb.org/sathishbabu/ontologies/project/planner.owl#> \n"
				+ "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#> \n"
				+ "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
				+ "SELECT (str(?name) as ?dishname) WHERE { \n"
				+ "       ?dish ab:name ?name. \n";
		
		if(!DishType.equals("")){
			
			if(DishType.equals("Dish")){
				queryString += "?class rdfs:subClassOf* ab:Dish. \n"
						+ "?dish rdf:type ?class. \n";
				
			}else{
			
				String split[] = DishType.split(" ");
				queryString += "?dish a ?class. \n"
						+ "FILTER("; 
				int y =0 ;
				for(String s: split){
					if(y==0){
						queryString += "(?class=ab:"+s+")";
						y++;
					}else{
						queryString += "||(?class=ab:"+s+")";
					}
				}
				queryString += "). \n";
			}
		}
		
		if(!Taste.equals("")){
			
			String split[] = Taste.split(" ");
				queryString += "?dish ab:taste ?taste. \n"
						+ "FILTER("; 
				int y =0 ;
				for(String s: split){
					if(y==0){
						queryString += "(?taste=\""+s+"\")";
						y++;
					}else{
						queryString += "||(?taste=\""+s+"\")";
					}
				}
				queryString += "). \n";
			
		}
		
		
		if(!Cooked_for.equals("")){
			
			String split[] = Cooked_for.split(" ");
				queryString += "?dish ab:cooked_for ?cooked_for. \n"
						+ "FILTER("; 
				int y =0 ;
				for(String s: split){
					if(y==0){
						queryString += "(?cooked_for=\""+s+"\")";
						y++;
					}else{
						queryString += "||(?cooked_for=\""+s+"\")";
					}
				}
				queryString += "). \n";
			
		}
		if(!Cooked_in.equals("")){
			
			String split[] = Cooked_in.split(" ");
				queryString += "?dish ab:cooked_in ?cooked_in. \n"
						+ "FILTER("; 
				int y =0 ;
				for(String s: split){
					if(y==0){
						queryString += "(?cooked_in=\""+s+"\")";
						y++;
					}else{
						queryString += "||(?cooked_in=\""+s+"\")";
					}
				}
				queryString += "). \n";
			
		}
		if((!Ingridient_form.equals(""))&&(!Ingridient_form.equals(" "))){
			
			String split[] = Ingridient_form.split(" ");
				queryString += "?dish ab:use ?ingredients. \n"
						+ "?ingredients ab:form ?form. \n"
						+ "FILTER("; 
				int y =0 ;
				for(String s: split){
					if(y==0){
						queryString += "(?form=\""+s+"\")";
						y++;
					}else{
						queryString += "||(?form=\""+s+"\")";
					}
				}
				queryString += "). \n";
			
		}
		
		
		
		
		queryString += "} \n";
		
		
		
		
		QueryExecution queryExec = QueryExecutionFactory.sparqlService(
	 			"http://localhost:3030/ds/query", queryString);
		
		ResultSet rs = queryExec.execSelect();
		
		if(rs.hasNext()){			
			QuerySolution row=rs.next();
			String dishname = row.get("dishname").toString();
			System.out.println(dishname);
			queryString ="PREFIX ab:<http://www.semanticweb.org/sathishbabu/ontologies/project/planner.owl#> \n"
					+ "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#> \n"
					+ "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
					+ "SELECT (str(?ing) as ?ingredients ) WHERE { \n"
					+ "?dish ab:name \""+dishname+"\" . \n"
							+ "?dish ab:use ?ingredient. \n"
							+ " ?ingredient ab:name ?ing. \n"
							+ "} \n";
			
			queryExec = QueryExecutionFactory.sparqlService(
		 			"http://localhost:3030/ds/query", queryString);
			
			rs = queryExec.execSelect();
			
			while(rs.hasNext()){
				row=rs.next();
				String ingrediantName = row.get("ingredients").toString();
				Ingredients.add(ingrediantName);
				String queryString2 ="PREFIX ab:<http://www.semanticweb.org/sathishbabu/ontologies/project/planner.owl#> \n"
						+ "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#> \n"
						+ "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
						+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
						+ "SELECT (str(?y) as ?manufacturers ) WHERE { \n"
						+ " ?manifacturer ab:produce ab:"+ingrediantName+". \n"
								+ "?manifacturer ab:name ?y \n"
								+ "} \n";
				
				QueryExecution queryExec2= QueryExecutionFactory.sparqlService(
			 			"http://localhost:3030/ds/query", queryString2);
				ResultSet rs2 = queryExec2.execSelect();
				if(rs2.hasNext()){
					ArrayList<Node> forThisIngredient = new ArrayList<Node>();
					while(rs2.hasNext()){
						QuerySolution row2=rs2.next();
						String manufacturerName = row2.get("manufacturers").toString();
						forThisIngredient.add(grp.get(manufacturerName));
					}
					possibleNodes.add(forThisIngredient);
//					System.out.println("Added!");
				}
			}
			
			
			
		}
		
	}
	
	
}
