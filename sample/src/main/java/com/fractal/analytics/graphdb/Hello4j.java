package com.fractal.analytics.graphdb;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import java.io.File;

public class Hello4j {
    private static final File DB_PATH = new File("/home/pavankumar/neo4j-db");
    GraphDatabaseService graphDatabaseService;
    Node node1;
    Node node2;
    Relationship relationship;
    String test_string;

    private static enum RelTypes implements RelationshipType
    {
        KNOWS
    };
    public static void main(String[] args) {
        Hello4j hello4j = new Hello4j();
        hello4j.createDb();
        hello4j.removeData();
        hello4j.shutdown();
    }
    void createDb(){
        graphDatabaseService = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        Transaction tx = graphDatabaseService.beginTx();
        try {
            node1 = graphDatabaseService.createNode();
            node2 = graphDatabaseService.createNode();
            node1.setProperty("name", "pavan");
            node2.setProperty("name", "pratik");
            relationship = node1.createRelationshipTo(node2, RelTypes.KNOWS);
            relationship.setProperty("relationship-type", "knows");
            test_string = (node1.getProperty("name").toString())
                    + " " + (relationship.getProperty("relationship-type").toString())
                    + " " + (node2.getProperty("name").toString());
            System.out.println(test_string);

            tx.success();
        }
        finally{
            tx.close();
        }

    }
    void removeData(){
        Transaction tx = graphDatabaseService.beginTx();
        try
        {
            node1.getSingleRelationship( RelTypes.KNOWS, Direction.OUTGOING ).delete();
            System.out.println("Removing nodes...");
            node1.delete();
            node2.delete();
            tx.success();
        }
        finally
        {
            tx.close();
        }

    }

    void shutdown(){
        graphDatabaseService.shutdown();
        System.out.println("graphDB shut down.");
    }


}
