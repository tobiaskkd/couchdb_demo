//package couchdb;
import java.net.MalformedURLException;
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
//--------------- Creating Document----------------------------//
        DesignDocument dd = new DesignDocument("tis");
        //db.create(dd);

        System.out.println("Choose an option below");
        System.out.println("1. Read all documents");
        System.out.println("2. Read single document");
        System.out.println("3. Create new document");
        System.out.println("4. Edit existing document");
        System.out.println("5. Delete document");
        // Scanner picks up user inputs
        Scanner scanner = new Scanner(System.in);

        // Cases based on users input
        switch (scanner.nextInt()) {
            case 1:

                break;
            case 2:
                System.out.println("Type the ID of the student you wish you see");
                String inputId = scanner.next();
                readDocument(inputId,db);
                break;
            case 3:
                System.out.println("3");
                break;
            case 4:
                System.out.println("4");
                break;
            case 5:
                System.out.println("5");
                break;
        }
    }
    public static void readDocument(String id, CouchDbConnector db) {
        Student student = db.get(Student.class, id);
        System.out.println(student.getFirstname() + student.getLastname());
    }

    public void createDocument() {

    }
}