# Teaching Session 6: Jersey and Gradle

Start at [the HTML lesson](../website/index.html#s6-roadmap). This original companion lesson follows WEBSRV–TL6 in the supplied August 2024 presenter’s manual: explain Jersey RESTful services and Gradle. Teaching Session 7 continues the same mini project chapter with resource methods. Assign the exact learner-book questions separately.

The new reference project starts with a text greeting and then adds a fixed, read-only JSON catalogue. It revisits GET, paths, query inputs and response status codes while teaching Gradle explicitly. The older `session6-7-jersey-mini-project` is separate legacy reference. Continue with the revised [Session 7 lesson](../website/index.html#s7-roadmap) and [CRUD project](../session7-jersey-crud/README.md).

## Requirements and setup

Use JDK 17–25 with the pinned Gradle 9.2.1 wrapper. Both `java` and `javac` must be available. A newer JDK requires checking Gradle compatibility first. The source targets Java 17. Internet access is needed initially for Gradle and dependencies; download duration varies. The wrapper checks the official distribution SHA256. Include `gradlew`, `gradlew.bat` and all of `gradle/wrapper` when distributing the project.

From Windows PowerShell:

```powershell
Set-Location "C:\Projects\Aptech\creating service\session6-jersey-gradle"
java -version
javac -version
.\gradlew.bat --version
.\gradlew.bat tasks --all
.\gradlew.bat lessonInfo
.\gradlew.bat dependencies --configuration runtimeClasspath
.\gradlew.bat javaBridge
.\gradlew.bat build
```

Use your copy’s path if different. On macOS/Linux use `./gradlew`. The wrapper avoids requiring a globally installed Gradle. Maven Central is a dependency repository; Gradle is the build tool here.

`javaBridge` prints the welcome sentence for Ada and `1: Notebook costs NGN 1500`, then exits. Gradle also prints task output. `build` checks compilation and configured build tasks; this reference has no automated endpoint test task. `test NO-SOURCE` is not proof that the HTTP behaviour is correct.

## Greeting first

In terminal A:

```powershell
.\gradlew.bat run --args="--hello-only"
```

Wait for the server line and leave A running. In terminal B:

```powershell
curl.exe -i "http://127.0.0.1:8098/api/greeting?name=Ada"
curl.exe -i "http://127.0.0.1:8098/api/greeting?name="
curl.exe -i http://127.0.0.1:8098/api/products
```

Expect 200 text `Welcome, Ada! You are learning Jersey with Gradle.`; 400 text explaining the name rule; and 404 because products are not registered in this mode. An absent name defaults to learner; explicitly blank input is rejected. Names are trimmed and must have 1–40 UTF-16 code units; use ordinary letters/spaces for beginner length boundaries.

Stop A with Ctrl+C. Save/build/restart after Java changes.

## Full JSON catalogue

In A:

```powershell
.\gradlew.bat run
```

In B:

```powershell
curl.exe -i -H "Accept: application/json" http://127.0.0.1:8098/api/products
curl.exe -i "http://127.0.0.1:8098/api/products?category=Stationery"
curl.exe -i http://127.0.0.1:8098/api/products/2
curl.exe -i http://127.0.0.1:8098/api/products/999
curl.exe -i http://127.0.0.1:8098/api/products/0
curl.exe -i http://127.0.0.1:8098/api/products/abc
curl.exe -i -H "Accept: application/xml" http://127.0.0.1:8098/api/products
curl.exe -i -X POST http://127.0.0.1:8098/api/products
```

Reference products: 1 Notebook / 1500 NGN / Stationery; 2 USB Mouse / 6500 NGN / Accessories; 3 Pen Set / 1000 NGN / Stationery. Prices are fictional whole-naira integers. The list order is fixed; JSON property order/whitespace can differ. Stationery matches ids 1 and 3 case-insensitively. Unknown categories return 200 `[]`; blank means all.

Lookup 2 returns 200 JSON. Missing positive id 999 returns 404 JSON. Zero/negative ids return our 400 JSON rule. Letters or integer overflow in the path return Jersey’s 404 conversion rejection before the method runs; its body is not promised. XML Accept returns 406; POST returns 405. Jackson handles JSON output. There is no XML provider or POST body model here.

Product is immutable and output-only. Jackson uses its getters; no empty constructor or JAXB annotation is required for this serialization example. These values live in a fixed Java list; they are recreated from source on restart. The service binds to this computer at 127.0.0.1. This is a teaching example, with no persistent database, accounts or deployment configuration.

## Assemble an application distribution

Stop the running server before launching another:

```powershell
.\gradlew.bat installDist
.\build\install\session6-jersey-gradle\bin\session6-jersey-gradle.bat --hello-only
```

Omit `--hello-only` for full mode. The installed script starts Java with the required dependency JARs and JDK HTTP module. The ordinary small JAR is not self-contained. `installDist` creates files locally; it does not deploy them. Cache-aware `--offline` builds work only when the wrapper and required dependencies are already present.

## Independent practice and evidence

The HTML includes custom Gradle task work, a new text resource, an extra product/filter variation and a deliberate debug task. The independent Room API assignment asks students to create their own model, fixed catalogue, JSON list/lookup, building and minimum-seat filters, custom build task, boundary tests and restart explanation. It supplies milestones/hints and a 100-mark rubric without a completed Room solution.

Submit source, exact commands, predictions and actual statuses/bodies, a debug note and the downloaded learning log. Keep evidence outside `build`; `clean` removes generated output. Self-reported checks and quizzes do not demonstrate independence. Observe an unseen changed requirement and next-day recall using the existing beginner-trial protocol. Actual learner observations remain pending.

Jersey may warn that JAXBContext is unavailable and WADL is disabled. WADL is optional XML API documentation; this project does not enable it. The warning does not prevent greeting/JSON requests. Confirm the server line and real responses.
