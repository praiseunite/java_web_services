# Real JPA database lab and persistent REST bridge

Read sections **1.8B** and **2.13B** in `website/index.html` for the complete gradual lesson, source explanations, predictions, exercises, error help and independent Book assignment.

This project uses Java 17+, Jakarta Persistence 3.1.0, Hibernate 6.6.5.Final and H2 2.3.232. H2 runs inside Java and stores committed rows in a local file. Jersey 3.1.5 supplies the later HTTP bridge. Maven downloads the libraries on the first build; no separate database server installation is required.

## Build and console practice

In PowerShell:

```powershell
Set-Location "C:\Projects\Aptech\creating service\session1-jpa-lab"
mvn package
java -jar target/session1-jpa-lab-1.0.jar list
java -jar target/session1-jpa-lab-1.0.jar add "Amina"
java -jar target/session1-jpa-lab-1.0.jar list
```

For a fresh database the list starts empty, then add reports `Saved 1: Amina`, and the next process finds it. Hibernate also prints startup information. Record the actual generated id; substitute it for 1 in these examples:

```powershell
java -jar target/session1-jpa-lab-1.0.jar find 1
java -jar target/session1-jpa-lab-1.0.jar search "Amina"
java -jar target/session1-jpa-lab-1.0.jar rename 1 "Amina Bello"
java -jar target/session1-jpa-lab-1.0.jar find 1
java -jar target/session1-jpa-lab-1.0.jar rollback 1 "Temporary Name"
java -jar target/session1-jpa-lab-1.0.jar find 1
```

Both final reads must show Amina Bello. The rollback experiment flushes the update before cancelling it. Flush is not commit. Validation rejects blank names and names longer than 60 characters after trimming. Search is exact and case-sensitive and binds the value as a JPQL parameter.

To practise deletion, add a second fictional row, record its id, then run `delete <actual-id>` and `find <actual-id>` using that id. Delete affects only that row. A missing id is a normal not-found result. Bad command arguments or rejected values use exit code 2.

## File and transaction behaviour

- Default database base: `./data/students`, resolved from the terminal's working directory; H2 creates `students.mv.db`.
- Every startup prints the absolute database filename. Use the same project folder each time to avoid accidentally opening another database.
- No startup seeding or clearing; previously committed rows remain. Identity ids need not be consecutive or equal the row count.
- One EntityManagerFactory per process, one EntityManager per store operation; failed active transactions roll back and contexts close in finally.
- Use one Java process at a time against this file. Stop the REST bridge before console work or rebuilding its JAR.
- Maven `clean` removes target output, not data. Do not edit the H2 binary file in a text editor.

## Persistent REST bridge (after Session 2)

In terminal A, from this project:

```powershell
java --add-modules jdk.httpserver -cp target/session1-jpa-lab-1.0.jar com.aptech.persistence.PersistentServer
```

Leave A open. In B, from the same project folder:

```powershell
curl.exe -i http://127.0.0.1:8096/api/students
curl.exe -i -X POST http://127.0.0.1:8096/api/students -H "Content-Type: application/json" --data-binary "@new-student.json"
curl.exe -i -X POST http://127.0.0.1:8096/api/students -H "Content-Type: application/json" --data-binary "@blank-student.json"
```

Valid creation: 201 plus actual Location and generated id. Follow that Location with GET. Invalid input: 400 with no new row. Missing positive id: 404; zero id: 400; non-JSON media type: 415; malformed JSON: 400.

**Press Enter in A to stop safely**, wait for its prompt, then restart with the same command/folder and GET the recorded Location again. The same student must remain. The original Session 2 CourseStore remains an in-memory teaching example; this separate bridge demonstrates the persistence part.

For an instructor-selected alternate file or port, quote dotted PowerShell properties:

```powershell
java "-Dlab.db=./data/my-practice" -jar target/session1-jpa-lab-1.0.jar list
java "-Dlab.db=./data/my-practice" "-Dlab.port=18096" --add-modules jdk.httpserver -cp target/session1-jpa-lab-1.0.jar com.aptech.persistence.PersistentServer
```

Update the request port and use the same database base consistently. Each learner should use a personal copy/file.

## Independent Book work

Section 1.8B sets the mapping, CRUD, exact-title query, 1–80-character validation and commit/rollback acceptance requirements. Create Book, BookStore and BookLab in `com.aptech.persistence`; list Book in persistence.xml. A command parser is optional: begin with one operation in main, close the factory, rebuild and run:

```powershell
java -cp target/session1-jpa-lab-1.0.jar com.aptech.persistence.BookLab
```

After Session 2, implement and register BookResource with BookStore; test HTTP creation, rejected requests and restart persistence as required in 2.13B. No completed Book solution is supplied.

This is a local teaching application: automatic schema update, Hibernate's built-in pool and H2's local sa account with an empty password are practice settings. Production configuration, migrations, security and deployment are later work.
