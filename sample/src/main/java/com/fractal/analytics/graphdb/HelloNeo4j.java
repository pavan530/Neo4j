package com.fractal.analytics.graphdb;
import org.neo4j.driver.v1.*;
import static org.neo4j.driver.v1.Values.parameters;

public class HelloNeo4j {

    void create() {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "Change@530"));
        Session session = driver.session();

        session.run("CREATE (a:Person {name: {name}, title: {title}})",
                parameters("name", "Arthur", "title", "King"));
//
        StatementResult result = session.run("MATCH (a:Person) WHERE a.name = {name} " +
                        "RETURN a.name AS name, a.title AS title",
                parameters("name", "Arthur"));
        while (result.hasNext()) {
//            session.run("MATCH (a:Person) DETACH DELETE a");
            Record record = result.next();
            System.out.println(record.get("title").asString() + " " + record.get("name").asString());

//            System.out.println("deleted");;
        }

        session.close();
        driver.close();
    }

    public static void main(String[] args) {
        HelloNeo4j helloNeo4j = new HelloNeo4j();
        helloNeo4j.create();
    }
}