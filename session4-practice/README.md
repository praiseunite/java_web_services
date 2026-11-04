# Session 4: integrated practice

The August 2024 presenter's manual maps WEBSRV–TL4 to the **Try It Yourself questions in book chapters 1–4**. The HTML teaches those chapters across Sessions 1–3. This project supplies original companion practice in Java, JPA, REST and SOAP; the instructor must also assign the actual textbook questions, whose wording is absent from the supplied manual.

Read [the HTML lesson](../website/index.html#s4-roadmap). Work through it in a personal project copy, one stage at a time. Predict the result, type and explain the code, test it, then close the completed example before writing the similar independent task. Reading and quizzes alone do not establish independent competence.

## Before starting

Use JDK 17+ and Maven. From your personal `session4-practice` folder:

```powershell
java -version
javac -version
mvn -version
mvn package
```

Wait for `BUILD SUCCESS`. The first build downloads dependencies. The supplied build packages Jersey 3.1.5, Metro 4.0.3, Jakarta Persistence 3.1.0, Hibernate 6.6.5.Final and H2 2.3.232 into one JAR. Metro provides the JAXB runtime used by both stacks.

Terminal A runs the provider; Terminal B sends requests and runs the separate client. Both terminals should start in your personal project folder. Stop A before rebuilding its JAR or running console database practice. The H2 file must be opened by one Java process at a time.

## 1. Plain Java

```powershell
javac beginner/PrintChargePractice.java
java -cp beginner PrintChargePractice
```

Predict 1, 3, 20, 0 and 21 pages. Valid charges are 10, 30 and 200 illustrative NGN; 0 and 21 print the rejection reason. Change the rate to 12, predict and rerun, then restore 10. Independently write a badge-cost method for 1–15 badges at 7 each, testing both boundaries and invalid counts.

## 2. Database, with the provider stopped

```powershell
java -cp target/session4-practice-1.0.jar com.aptech.practice.DatabasePractice
java -cp target/session4-practice-1.0.jar com.aptech.practice.DatabasePractice "Java Foundations"
java -cp target/session4-practice-1.0.jar com.aptech.practice.DatabasePractice
```

The first command lists; the second inserts and commits; the third starts a fresh process and must read the saved row. Record the printed absolute database path and actual generated id. The default file is `data/library.mv.db`, relative to this working folder. An existing file may contain rows already, and ids need not start at 1 or match the row count.

Read the `Book` entity and `BookStore` methods. JPQL uses `Book`, `b.id` and `b.title`; mapping uses the physical table `library_books` and columns `book_id`/`book_title`. Each store call creates and closes its own EntityManager. Inserts return only after commit; an active failed transaction is rolled back.

## 3. REST first

In A:

```powershell
java --add-modules jdk.httpserver -jar target/session4-practice-1.0.jar --rest-only
```

Wait for the REST startup line. In B, from the project folder:

```powershell
curl.exe -i http://127.0.0.1:8098/api/books
curl.exe -i -X POST http://127.0.0.1:8098/api/books -H "Content-Type: application/json" --data-binary "@new-book.json"
curl.exe -i "http://127.0.0.1:8098/api/books?title=Java%20Foundations"
curl.exe -i -X POST http://127.0.0.1:8098/api/books -H "Content-Type: application/json" --data-binary "@blank-book.json"
```

POST success returns 201, JSON id/title and `Location`. GET the **actual complete Location returned by your POST**. Blank/null/missing/overlong titles return 400. Trimming happens before the 1–80-character check; duplicate titles are allowed. The exact-title query is case sensitive and uses parameter binding. No match returns 200 and `[]`.

Test title lengths 1/80/81, spaces only, null input, malformed JSON, unsupported text/plain, missing positive id (404) and id zero (400). Compare the collection before/after rejected inserts. Write and register a separate `/hours` text resource as the smaller independent variation.

**Press Enter in A and wait for the prompt** to stop safely. Do this after requests finish; the supplied launcher stops endpoints/workers and closes the database.

## 4. Add SOAP

With REST-only stopped, in A:

```powershell
java --add-modules jdk.httpserver -jar target/session4-practice-1.0.jar
```

Wait for both startup lines. Open `http://127.0.0.1:8099/library?wsdl`. Find namespace `urn:aptech:session4`, service `AptechLibraryService`, port `LibraryPort`, operation names and imported XSD. The operation wrapper is namespaced; the parameter children in these request files are unqualified.

In B:

```powershell
curl.exe -i -X POST http://127.0.0.1:8099/library -H "@soap-headers.txt" --data-binary "@hours.xml"
curl.exe -i -X POST http://127.0.0.1:8099/library -H "@soap-headers.txt" --data-binary "@book-title.xml"
curl.exe -i -X POST http://127.0.0.1:8099/library -H "@soap-headers.txt" --data-binary "@print-charge.xml"
curl.exe -i -X POST http://127.0.0.1:8099/library -H "@soap-headers.txt" --data-binary "@invalid-print.xml"
```

**Edit book-title.xml before sending:** replace example id 1 with the actual id you saved through REST. Expected results: hours text; the stored title; `chargeNGN` 30; HTTP 500 with `LibraryFault` detail/reason for zero pages. Also test 1/20/21 pages and invalid/missing ids. A SOAP fault body tells you more than status 500 alone.

Both endpoints share one BookStore and H2 file. REST does not call SOAP. Independently add a JPA-backed `getBookCount()` operation, publish its updated contract and test zero/one/two stored rows.

## 5. Generate a separate client

Keep the full provider running in A. Starting with B in `session4-practice`:

```powershell
Set-Location generated-client
mvn package
Get-ChildItem target/generated-sources/wsimport/com/aptech/practice/generated
java -jar target/session4-generated-client-1.0.jar 1
Set-Location ..
```

Replace the final example `1` with your actual saved id. Expected output is hours, that book's id/title, and `3 pages: 30 NGN`. wsimport generates the contract types; you write the caller. If you added getBookCount, rebuild/restart the provider, inspect its current WSDL, run `mvn clean package` in the client project, and add a handwritten count call. Do not edit generated files.

## 6. Record proof, then work independently

Record each input, predicted/actual status and value, database path, id and explanation. Stop A using Enter, restart it in the same folder, and retrieve the original id/title through **both** REST and SOAP. Test a broken namespace in a duplicate request file, identify the fault and repair it.

The HTML's equipment assignment reduces the help: create a persistent Item catalogue, name length 1–60, GET/POST routes, SOAP lookup and loan charges for 0–14 days at 25 illustrative NGN, a separate generated client and restart evidence. Use its stated ports 8090/8091 and namespace `urn:aptech:equipment`. Use your own equipment database base. The HTML supplies milestones, boundary tests, submission requirements and a 100-mark rubric, without a completed equipment solution.

The instructor should ask for an unseen variation and explanation with solutions closed, then a smaller changed task on the following day. The prepared beginner trial awaits observations from actual learners.

## Alternate ports or database

In A, from the provider project:

```powershell
java "-Dpractice.rest.port=18098" "-Dpractice.soap.port=18099" --add-modules jdk.httpserver -jar target/session4-practice-1.0.jar
```

Update all request URLs. In B's `generated-client` folder:

```powershell
mvn "-Dlibrary.wsdl=http://127.0.0.1:18099/library?wsdl" clean package
java "-Dpractice.soap.port=18099" -jar target/session4-generated-client-1.0.jar 1
```

Again use the actual saved id. A different database base is supplied as a quoted JVM property, for example `"-Dpractice.db=./data/my-library"`, **before** `-jar` or `-cp`. Use the same base/folder for console and provider starts; H2 appends `.mv.db`. Quote dotted `-D` arguments in PowerShell.
