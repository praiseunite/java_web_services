# Session 2: Jakarta RESTful Web Services (JAX-RS)

Start with [the HTML lesson](../website/index.html#session2). It includes Java prerequisites, explanations of every new concept, guided coding, independent challenges, ten quizzes, a learning log and the library API assignment. The supplied presenter's manual maps teaching Session 2 to book Session 3: JAX-RS, its uses and its distinction from JAX-WS.

This is a local Jersey 3.1.5 application with a JDK HTTP-server adapter and Jackson JSON conversion. Use JDK 17+ and Maven 3.9.x. Maven downloads dependencies/build plugins on the first build, so internet access is needed unless everything is already cached. The generated JAR includes the runtime libraries; it can subsequently run without Maven or a network connection.

In PowerShell:

```powershell
Set-Location "C:\Projects\Aptech\creating service\session2-jaxrs"
mvn -version
mvn package
java --add-modules jdk.httpserver -jar target/session2-jaxrs-1.0.jar --hello-only
```

Greeting-only mode registers the greeting resource and lets beginners practise one endpoint. In a second terminal:

```powershell
curl.exe -i http://127.0.0.1:8093/api/greeting
curl.exe -i "http://127.0.0.1:8093/api/greeting?name=Amina"
```

Expected: 200 with `Hello, Student!` or `Hello, Amina!`. Stop with Ctrl+C in the server terminal. For the course API, start without `--hello-only`:

```powershell
java --add-modules jdk.httpserver -jar target/session2-jaxrs-1.0.jar
```

Enter the project folder in the request terminal when using JSON files:

```powershell
Set-Location "C:\Projects\Aptech\creating service\session2-jaxrs"
curl.exe -i http://127.0.0.1:8093/api/courses
curl.exe -i http://127.0.0.1:8093/api/courses/1
curl.exe -i "http://127.0.0.1:8093/api/courses?search=java"
curl.exe -i -X POST http://127.0.0.1:8093/api/courses -H "Content-Type: application/json" --data-binary "@new-course.json"
```

The collection starts with two courses. A valid POST returns 201, the generated id/object and a Location. Read that actual Location to verify creation. `invalid-course.json` supplies a blank title and should return 400 without changing the store.

| Method and path | Result |
| --- | --- |
| GET /api/greeting | 200, plain-text greeting; optional name query |
| GET /api/courses | 200, JSON array; optional search query |
| GET /api/courses/{id} | 200, one course; 400 for nonpositive id; 404 for a missing positive id |
| POST /api/courses | 201 with Location for valid JSON title; 400 for null/blank/over-80 trimmed title |

Non-integer path ids produce a framework 404. An unmatched route produces 404. Unsupported PUT produces 405; unsupported response/request media types produce 406/415. Framework error bodies may differ from the explicit `ApiError` bodies.

The store is a temporary Java map, not a database. Added courses disappear after restart and the two sample courses return. JSON conversion is not persistence. The API deliberately offers GET and POST examples; it is not the complete later CRUD mini-project.

If another program owns port 8093, use an alternate port without changing the source:

```powershell
java "-Dcourse.port=8094" --add-modules jdk.httpserver -jar target/session2-jaxrs-1.0.jar
```

Use the same port in every request. After editing source: save, stop, build successfully, start the new JAR and retest. The independent assignment uses a `/books` resource on port 8094 and adds author validation and a count route. Its completed solution is intentionally not provided.

Before writing your own store, complete the JDK-only collection exercise in HTML section 2.2. From the session2-jaxrs folder run `javac beginner/CourseCollectionPractice.java`, then `java -cp beginner CourseCollectionPractice`. Predict and explain lookups, loops, counts and id increments before using them inside the HTTP resource.
