# Session 5: Jakarta XML Binding (JAXB)

The August 2024 manual maps WEBSRV–TL5 to book Session 5: JAXB, marshalling/unmarshalling, XML schemas, binding best practices, API comparisons and tradeoffs. Read [the full HTML lesson](../website/index.html#s5-roadmap), which explains the source, predicted results, guided changes and independent work.

Work in a personal project copy. Read, predict, type, run and change one stage at a time. Then close the completed example and write the similar task without AI. A working reference or a quiz score does not establish independent competence.

## Build and first conversion

Use JDK 17+ and Maven, with PowerShell in session5-jaxb:

```powershell
java -version
javac -version
mvn -version
javac beginner/PlainStudentPractice.java
java -cp beginner PlainStudentPractice
mvn package
java -jar target/session5-jaxb-1.0.jar
Get-Content target/student_output.xml
```

The JDK-only warm-up places an object and XML string beside each other. JAXB conversion begins in the Maven project. The build supplies Jakarta XML Binding API 4.0.1 and JAXB implementation 4.0.4; JAXB is not bundled in JDK 17+. The first build may download dependencies. Wait for BUILD SUCCESS.

The marshalling demo writes a fictional Student to XML text and a real file. Expect student id 101; full_name Amina &amp; Tunde; email learner@example.com; course ADSE; GPA 3.5. The Java ampersand becomes &amp;amp; in XML. The fictional DEMO_ONLY password property is excluded. The XML declaration/indentation may differ from the reference; compare parsed values.

The main JAR manifest starts JaxbMarshallingDemo. To run another class, use -cp and its fully qualified name. Each program prints and exits; this lesson starts no web server or database.

## Read and prove a round-trip

```powershell
java -cp target/session5-jaxb-1.0.jar com.aptech.jaxb.JaxbUnmarshallingDemo
java -cp target/session5-jaxb-1.0.jar com.aptech.jaxb.JaxbUnmarshallingDemo examples/missing-course.xml
java -cp target/session5-jaxb-1.0.jar com.aptech.jaxb.RoundTripDemo
```

The first reader performs mapping without attaching XSD or checking StudentRules. A missing course can map to null despite required=true metadata. It is a comparison demo, not an acceptance reader.

RoundTripDemo attaches the chosen schema to both workers and checks application rules. Expected: mapped values preserved true; same Java object false; excluded password after read is null true. Read target/roundtrip.xml and verify the angle brackets are escaped while the decoded Java name remains Ada <Coder>.

## Enable validation explicitly

```powershell
java -cp target/session5-jaxb-1.0.jar com.aptech.jaxb.ValidatedStudentReader
java -cp target/session5-jaxb-1.0.jar com.aptech.jaxb.ValidatedStudentReader examples/missing-course.xml
$LASTEXITCODE
java -cp target/session5-jaxb-1.0.jar com.aptech.jaxb.ValidatedStudentReader examples/invalid-gpa.xml
java -cp target/session5-jaxb-1.0.jar com.aptech.jaxb.ValidatedStudentReader examples/blank-name.xml
```

Valid input prints ACCEPTED and exits 0. Invalid input prints REJECTED with a useful clue and exits 2. The accepted Student is used only after the chosen XSD and StudentRules pass.

student_schema.xsd requires the unqualified root/children in the stated order, positive integer id, name length 1–60, email length 1–80, course ADSE/DISM and GPA 0–4. A spaces-only name passes this XSD length check but fails StudentRules. The email rule checks length/nonblank text; it does not validate email delivery. Practise ordinary letters/spaces for length boundaries. The application also checks finite GPA.

SecureXml caps local inputs at 262144 bytes (256 KiB), rejects DOCTYPE, disables external resources/entities, checks trailing XML and closes the stream/parser. Schema validation and parser controls have separate jobs. The javax.xml.stream/validation/transform imports are JDK APIs; JAXB imports use jakarta.xml.bind. XmlTransient omits a JAXB property; it is not encryption or access control for every serializer/log.

## Generate a schema and model classes

From session5-jaxb:

```powershell
java -cp target/session5-jaxb-1.0.jar com.aptech.jaxb.SchemaExportDemo
Get-Content target/generated-schema/schema1.xsd
mvn -f tools/pom.xml package
New-Item -ItemType Directory -Force schema-client/target/generated-sources
java -cp tools/target/session5-xml-tools-1.0.jar com.sun.tools.xjc.XJCFacade -d schema-client/target/generated-sources -p com.aptech.jaxb.generated student_schema.xsd
Get-ChildItem schema-client/target/generated-sources/com/aptech/jaxb/generated
New-Item -ItemType Directory -Force schema-client/target/classes
$s5GeneratedSources = @(Get-ChildItem schema-client/target/generated-sources/com/aptech/jaxb/generated/*.java | ForEach-Object FullName)
javac -cp target/session5-jaxb-1.0.jar -d schema-client/target/classes @s5GeneratedSources schema-client/GeneratedStudentDemo.java
java -cp "schema-client/target/classes;target/session5-jaxb-1.0.jar" GeneratedStudentDemo
```

The inferred schema has the mapping names/order/types but does not infer the domain enumeration/ranges from plain Java fields. Compare it with the deliberately chosen acceptance schema.

XJC 4.0.4 generates Student, CourseCode and ObjectFactory. The generated model uses FIELD access and a course enum; the handwritten model uses annotated getters and a String course. The caller imports the generated Student and reads a fixed fictional XML literal with the chosen schema attached. Expected: generated model package, Chidi & Ada, DISM, GPA 3.0. The existing JAR supplies runtime libraries; this caller does not use its handwritten Student class. Retain parser controls when adapting the caller for arbitrary input files.

The PowerShell array supplies generated source paths to javac. Quote the Windows classpath containing a semicolon. JDK 17+ does not bundle an xjc executable; the tools build packages it. Generation is ordinary tooling, not AI. Change the schema and regenerate rather than editing generated files. Use a fresh generated output folder when changing a contract so obsolete generated classes cannot remain.

## Independent assignment and evidence

The HTML provides a loan-ticket assignment with root loan_ticket, ticket_id attribute, item_name/borrower_label/days/charge_ngn children and excluded internalNote. Days 0–14 cost 25 illustrative NGN each. Write the mapped class, XSD, application rule checking charge equals days × 25, safe typed reader and round-trip proof. A range-valid charge can still violate that equality rule.

Write your classes in com.aptech.jaxb.loan, adapt the parser helper to return LoanTicket, attach loan-ticket.xsd and run your own LoanTicketDemo by its fully qualified name after building. Do not keep the Student cast or its schema. The HTML includes milestones, boundary tests, hints and a 100-mark rubric; it supplies no completed loan-ticket solution.

Submit source/schema/input files, exact run instructions, predictions and actual exit codes/clues, output/round-trip evidence, one debug note, API comparison justification and the learning log. Save evidence outside target before Maven clean. The instructor should observe an unseen variation and a smaller changed task the next day. The beginner trial remains pending actual learner observations.
