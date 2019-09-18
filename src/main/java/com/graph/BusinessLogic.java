package com.graph;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.google.common.graph.*;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class BusinessLogic {
	
	public List<CSVRecord> parseFile(File file) throws FileNotFoundException, IOException {
		try (
			Reader reader = Files.newBufferedReader(Paths.get(file.getPath()));
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withIgnoreEmptyLines(true));
		){
			List<CSVRecord> allRecords = new ArrayList<>();
			for(CSVRecord record: csvParser) {
				allRecords.add(record);
			}
			return allRecords;
		}
	}
	
	public List<Relation> modelRelations(List<CSVRecord> allRecords) throws IOException{
		List<Relation> allRelations = new ArrayList<>();

		for(CSVRecord record: allRecords) {
			
			String type = record.get(1);
			String email1 = record.get(0);
			String email2 = record.get(2);

			Relation relation = new Relation(email1, email2, type);
			allRelations.add(relation);
		}
		return allRelations;
	}
	
	public List<Relation> findRelationsByEmail(String email, List<Relation> allRelations){
		List<Relation> myRelations = new ArrayList<>();
		for(Relation relation: allRelations) {
			String email1 = relation.getEmailFirst();
			String email2 = relation.getEmailSecond();

			if(email.contentEquals(email1) | email.contentEquals(email2)) {
				myRelations.add(relation);
			}
		}
		return myRelations;
	}
	
	public List<Person> modelPersons(List<CSVRecord> personRecords) throws NumberFormatException{
		List<Person> people = new ArrayList<>();
		
		for(CSVRecord record: personRecords) {
			String name = record.get(0);
			String email = record.get(1);
			int age = Integer.parseInt(record.get(2));
			
			Person person = new Person(name, email, age);
			people.add(person);
		}
		return people;
	}
	
	public Person findPersonByEmail(String email, List<Person> allPersons) {
		Person thisPerson = null;
		for(Person person: allPersons) {
			if(person.getEmail().contentEquals(email)) {
				thisPerson = person;
			}
		}
		return thisPerson;
	}
	
	public MutableValueGraph<Person, String> createRelationsGraph(List<Person> allPeople, List<Relation> allRelations){
 		
		MutableValueGraph<Person, String> newGraph = ValueGraphBuilder.undirected().build();
 		//get each persons relationships
		
		for(Person person: allPeople) {
			List<Relation> myRelations = findRelationsByEmail(person.getEmail(), allRelations);
			//if no relations add person as a node
			if(myRelations.size() == 0) {
				newGraph.addNode(person);
			}
			else {
			//add persons as nodes with edges, edgevalue 0 for family, 1 for other
				for(Relation relation : myRelations) {
					Person one = findPersonByEmail(relation.getEmailFirst(), allPeople);
					Person two = findPersonByEmail(relation.getEmailSecond(), allPeople);
					if(relation.getRelationType().contentEquals("FAMILY")) {
						newGraph.putEdgeValue(one, two, "FAMILY");
					}
					else{
						newGraph.putEdgeValue(one, two, "NOT FAMILY");
					}
				}
			
			}
		}
		
		return newGraph;
		
 	}
	
	public Set<Person> adjacentFamilyNodes(Person current, MutableValueGraph<Person, String> graph){
		Set<Person> adjacentFamily = new HashSet<>();
		for(Person neighbour : graph.adjacentNodes(current)) {
			if(graph.edgeValue(current, neighbour).get().contentEquals("FAMILY")) {
			adjacentFamily.add(neighbour);
			}
		}
		return adjacentFamily;
	}
		
	public int extendedFamilySize(Person start,  MutableValueGraph<Person, String> graph) {
	    Stack<Person> stack = new Stack<Person>();
	    Set<Person> isVisited = new HashSet<>();
	    stack.push(start);
	    while (!stack.isEmpty()) {
	        Person current = stack.pop();
	        isVisited.add(current);
	        for(Person neighbour : adjacentFamilyNodes(current, graph)) {
	            if (!isVisited.contains(neighbour))
	                stack.push(neighbour);
	        }
	    }
	    return isVisited.size();
	}
}
 	

