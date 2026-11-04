# Session 1: begin with the HTML lesson

Open [the learning portal](../website/index.html). Session 1 contains the student-facing explanations, exercises, assignment requirements and rubric. Work through it in order; the Java files are reference examples, not completed independent submissions.

The presenter's teaching Session 1 covers book Session 1 (web services, SOAP and REST) and book Session 2 (Jakarta Persistence, JPQL, mapping metadata, Hibernate, Spring Data JPA and version recognition). JPA mapping snippets in the HTML are conceptual and need a configured provider/database project to execute.

Use a JDK version of 17 or newer. In PowerShell:

```powershell
Set-Location "C:\Projects\Aptech\creating service\session1-intro\beginner"
java -version
javac -version
javac HelloJava.java
java HelloJava
```

The remaining local examples use the same pattern: compile and run `GreetingPractice`, `RequestPractice`, and `StudentCardDemo` by their respective filenames/class names.

For the HTTP lab, terminal A:

```powershell
javac --add-modules jdk.httpserver FirstWebService.java
java --add-modules jdk.httpserver FirstWebService
```

Leave that terminal running. Open `http://127.0.0.1:8081/greeting` in a browser, or use terminal B:

```powershell
curl.exe -i http://127.0.0.1:8081/greeting
curl.exe -i http://127.0.0.1:8081/missing
curl.exe -i -X POST http://127.0.0.1:8081/greeting
```

Expected statuses: 200, 404, 405. The 405 reply advertises `Allow: GET`. Press Ctrl+C in terminal A to stop. Save, recompile, and restart to apply edits. If 8081 is occupied by another application, change the code and every request URL to an unused port. Do not stop another application's process to free a port.

The original packaged `SimpleServiceServer` and `SimpleServiceClient` are optional raw TCP networking examples. They exchange lines on port 5000 and do not speak HTTP, SOAP, or REST. Their optional commands and limitations are explained in the HTML troubleshooting section.

For learners who have never used Java, allow extra preparation time before the manual's two-hour teaching session. Independent practice, next-day recall and the assignment also need additional time. Use the HTML exit checks and a short live code change to assess understanding.
# Real persistence follow-up

After the JPA mapping explanation, complete HTML section 1.8B using the separate [session1-jpa-lab](../session1-jpa-lab/README.md). It supplies Hibernate, H2 file storage and transaction exercises. The five beginner programs here remain JDK-only. After Session 2, section 2.13B connects the database to a real REST service.

