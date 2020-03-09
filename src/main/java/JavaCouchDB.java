//package couchdb;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.support.DesignDocument;
public class JavaCouchDB {
    public static void main(String[] args) throws MalformedURLException {
//--------------- Creating Connection--------------------------//
        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .username("admin")
                .password("admin")
                .build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
//--------------- Creating database----------------------------//
        CouchDbConnector db = new StdCouchDbConnector("couchdb_demo", dbInstance);
        db.createDatabaseIfNotExists();

        // Scanner picks up user inputs
        Scanner scanner = new Scanner(System.in);

        boolean run = true;
        // Cases based on users input

        while (run) {

            System.out.println("Choose an option below");
            System.out.println("1. Read all documents");
            System.out.println("2. Read single document");
            System.out.println("3. Create new document");
            System.out.println("4. Edit existing document");
            System.out.println("5. Delete document");
            System.out.println("0. Exit");

            switch (scanner.nextInt()) {
                case 1:
                    readAllDocuments(db);
                    break;
                case 2:
                    System.out.println("Type the ID of the student you wish you see");
                    String inputId = scanner.next();
                    readDocument(inputId, db);
                    break;
                case 3:
                    createDocument(db);
                    break;
                case 4:
                    System.out.println("Type the ID of the student you wish to update");
                    updateDocument(scanner.next(), db);
                    break;
                case 5:
                    System.out.println("5");
                    break;
                case 0:
                    run = false;
                    System.out.println("Exiting!");
                    break;
            }
        }
    }
    public static Student readDocument(String id, CouchDbConnector db) {
        Student student = db.get(Student.class, id);
        System.out.println(student.getFirstname() + student.getLastname());
        return student;
    }
    
    public static List<Student> readAllDocuments(CouchDbConnector db) {

        List<Student> studentList = new ArrayList<>();
        List<String> docIDs = db.getAllDocIds();
        
        if (!docIDs.isEmpty()) {
            for (String docID : docIDs) {
                Student student = db.get(Student.class, docID);
                studentList.add(student);
                System.out.println(student.getFirstname() + " " + student.getLastname());
            }
        }
        
        return studentList;
    }

    public static void updateDocument(String id, CouchDbConnector db) {
        //Gets the student with the id passed in as argument
        Student student = readDocument(id, db);

        //Reads user input and sets new user data
        Scanner scanner = new Scanner(System.in);

        System.out.println("Type new firstname");
        student.setFirstname(scanner.next());

        System.out.println("Type new lastname");
        student.setLastname(scanner.next());

        //Updates user
        db.update(student);
        System.out.println("Updated student with id: " + student.getId());
    }

    public static void createDocument(CouchDbConnector db) {
        Scanner scanner = new Scanner(System.in);
        Student student = new Student();
        System.out.print("Type in first name: ");
        student.setFirstname(scanner.next());
        System.out.print("Type in last name: ");
        student.setLastname(scanner.next());
        db.create(student);
        System.out.println("Created " + student.getFirstname() + ' ' + student.getLastname());
    }
}