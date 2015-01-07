import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;


public class AccessFuseki {
	public static void main(String args[]){
		
		String queryString ="PREFIX ab:<http://www.semanticweb.org/sathishbabu/ontologies/project/planner.owl#> \n"
				+ "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#> \n"
				+ "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
				+ " SELECT ?class WHERE { ?class rdf:type ab:Indian ."
				+ "						 MINUS {?class ab:name ab:Roti} "
				+ "	}";
//
//		String queryString ="PREFIX ab:<http://www.semanticweb.org/sathishbabu/ontologies/project/planner.owl#> \n"
//				+ " SELECT ?name WHERE {?dish ab:use ab:Wheat."
//				+ " ?dish ab:name ?name .}";
		QueryExecution queryExec = QueryExecutionFactory.sparqlService(
		 			"http://localhost:3030/ds/query", queryString);

		ResultSet rs = queryExec.execSelect();
		
		
		while(rs.hasNext()){
			
			QuerySolution row=rs.nextSolution();
			System.out.println(row.get("class"));
			//System.out.println(row.getLiteral("name").getString());
		}
		System.out.println("Done!");
	}
}
