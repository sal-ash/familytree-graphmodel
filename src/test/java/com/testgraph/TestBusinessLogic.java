package com.testgraph;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;


import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import com.graph.BusinessLogic;
import com.graph.Person;
import com.graph.Relation;


public class TestBusinessLogic{
	BusinessLogic controller = new BusinessLogic();
	List<CSVRecord> relationsRecords = new ArrayList<>();
	List<CSVRecord> peopleRecords = new ArrayList<>();
	List<Person> allPeople = new ArrayList<>();
	List<Relation> allRelations = new ArrayList<>();

	MutableValueGraph<Person, String> graph = ValueGraphBuilder.undirected().build();
	
	@Before
	public void setup() throws FileNotFoundException, IOException, URISyntaxException{
		File persons = new File(this.getClass().getResource("/people.csv").toURI().getPath());
		File relations = new File(this.getClass().getResource("/relationships.csv").toURI().getPath());
		
		relationsRecords = controller.parseFile(relations);
		peopleRecords = controller.parseFile(persons);
		
		allPeople = controller.modelPersons(peopleRecords);
		allRelations = controller.modelRelations(relationsRecords);
		
		graph = controller.createRelationsGraph(allPeople, allRelations);
		

	}
	
	@Test
	public void testModelPersons() throws IOException {
		assertEquals(12, controller.modelPersons(peopleRecords).size());
		
	}
	
	@Test
	public void testFindRelations() throws IOException {
		Person bob = controller.findPersonByEmail("bob@bob.com", allPeople);
		Person jenny = controller.findPersonByEmail("jenny@toys.com", allPeople);
		Person nigel = controller.findPersonByEmail("nigel@marketing.com", allPeople);
		Person alan = controller.findPersonByEmail("alan@lonely.org", allPeople);
		
		assertEquals(4, graph.adjacentNodes(bob).size());
		assertEquals(3, graph.adjacentNodes(jenny).size());
		assertEquals(2, graph.adjacentNodes(nigel).size());
		assertEquals(0, graph.adjacentNodes(alan).size());
		
	}
	
	@Test
	public void testFindExtendedFamily() throws IOException {
		Person bob = controller.findPersonByEmail("bob@bob.com", allPeople);
		Person jenny = controller.findPersonByEmail("jenny@toys.com", allPeople);
		
		assertEquals(4, controller.extendedFamilySize(bob, graph));
		assertEquals(4, controller.extendedFamilySize(jenny, graph));
		
	}
}
