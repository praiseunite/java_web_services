# Teaching Session 8: JAXB and Jersey practice

The August 2024 presenter’s manual maps WEBSRV–TL8 to the actual Try It Yourself questions from book Sessions 5 and 6. Use [the beginner HTML lesson](../website/index.html#s8-roadmap) and assign the exact learner-book questions separately. This project is an original companion rehearsal; the supplied manual does not reproduce those questions. Use BOOK_TASKS.md to record their real identifiers and your attempts.

The converter reads one XML product, validates with a chosen XSD and Java rules, normalizes text, proves an XML round-trip, and prepares the three-field JSON body consumed by Session 7. It sends no HTTP and starts no server. JAXB handles XML, Jackson writes JSON, and the separate Jersey service handles requests. The project is not a batch importer or durable API database.

## Build and accepted conversion

Use JDK 17–25, the included Gradle 9.2.1 wrapper and Java 17 compilation target. Keep both wrapper scripts and gradle/wrapper when copying. JAXB implementation 4.0.4 and Jackson 2.15.3 reuse the course library versions. First ordinary setup needs network access; offline mode needs complete dependencies.

```powershell
Set-Location "C:\Projects\Aptech\creating service\session8-practice"
java -version
javac -version
.\gradlew.bat --version
.\gradlew.bat lessonInfo
.\gradlew.bat build
.\gradlew.bat installDist
.\gradlew.bat run --args="examples/new-product.xml evidence/product-a"
$LASTEXITCODE
Get-Content evidence/product-a/report.txt
Get-Content evidence/product-a/normalized.xml
Get-Content evidence/product-a/product.json
```

Use your copy’s actual path. Output directories must not already exist; select a new name for each run. The parent can be created automatically. An accepted run exits 0 and writes normalized.xml, product.json and report.txt. Expected: decoded name Cable & Adapter (trimmed), priceNgn 7500, category Accessories, round-trip values true, same Java object false, and no id in JSON. Formatting may vary; compare mapped values. The XML ampersand is escaped, and Jackson handles JSON escaping without string concatenation.

The optional property points at the prepared instructor Maven repository. It is not required in ordinary setups. Quote the complete -P argument in PowerShell:

```powershell
.\gradlew.bat --gradle-user-home "..\.build-cache\gradle-home" "-PcourseMavenCache=../.build-cache/maven" --offline --no-daemon build installDist
```

The installed launcher runs Java directly with its dependency libraries. Gradle packages product-input.xsd from src/main/resources as a classpath resource; it does not depend on an absolute local schema file.

```powershell
.\build\install\session8-practice\bin\session8-practice.bat examples/updated-product.xml evidence/product-b
$LASTEXITCODE
```

Expect name Cable & Adapter Plus, price 8500 and category Devices. After changing source/resources, save, rebuild and regenerate installDist before using its launcher. build/test NO-SOURCE is not runtime verification.

## Reject and repair

```powershell
.\build\install\session8-practice\bin\session8-practice.bat examples/missing-price.xml evidence/rejected-missing
$LASTEXITCODE
Test-Path evidence/rejected-missing
.\build\install\session8-practice\bin\session8-practice.bat examples/blank-name.xml evidence/rejected-blank
$LASTEXITCODE
Test-Path evidence/rejected-blank
```

Each rejected conversion exits 2 and creates no new output directory. Missing/negative/decimal prices, wrong root/order/namespace, extra XML properties and malformed syntax reject before application use. The XSD uses string typing for text; ProductRules checks nonblank and trimmed lengths 1–60 for name and 1–30 for category, in Java UTF-16 code units. Use ordinary letters for boundary exercises. Price is an Integer from 0 to 1000000 inclusive. XML integer lexical syntax differs from JSON numeric syntax; the bridge emits a proper numeric JSON value.

XmlSupport uses a bounded 262144-byte input, rejects DOCTYPE and external resources, consumes trailing XML and closes parser/streams. It adapts Session 5’s helper to the new typed model. Required JAXB metadata alone does not validate input: ProductBridge explicitly attaches the schema to both workers, rejects validation events, and checks application rules.

An existing output folder is rejected and preserved. Do not use earlier files as evidence of a failed conversion. Input/schema/rule/conversion failures happen before publication; a later filesystem error could leave partial output. Use only inspected files from a run that exited 0. File writes and later HTTP actions are separate operations.

## Actual Jersey sequence

In terminal A start the separate Session 7 full service:

```powershell
Set-Location "C:\Projects\Aptech\creating service\session7-jersey-crud"
.\gradlew.bat run
```

Wait for the server URL on port 8099 and CRUD mode. Stop a prior server you own before switching. In B, from session8-practice, use the accepted output files:

```powershell
curl.exe -i http://127.0.0.1:8099/api/products
curl.exe -i -X POST http://127.0.0.1:8099/api/products -H "Content-Type: application/json" --data-binary "@evidence/product-a/product.json"
curl.exe -i http://127.0.0.1:8099/api/products/4
curl.exe -i -X PUT http://127.0.0.1:8099/api/products/4 -H "Content-Type: application/json" --data-binary "@evidence/product-b/product.json"
curl.exe -i http://127.0.0.1:8099/api/products/4
curl.exe -i "http://127.0.0.1:8099/api/products?category=Accessories"
curl.exe -i "http://127.0.0.1:8099/api/products?category=Devices"
curl.exe -i -X DELETE http://127.0.0.1:8099/api/products/4
curl.exe -i http://127.0.0.1:8099/api/products/4
curl.exe -i -X DELETE http://127.0.0.1:8099/api/products/4
```

Fresh server: POST 201 with absolute Location/id 4, then GET 200. Use the actual returned id/Location after earlier creates. PUT 200 preserves id/count and changes the fields/category filters. DELETE 204 has no body; following GET and repeated DELETE give 404. A direct XML body marked application/xml gives 415, and those XML bytes marked application/json give 400; prove unchanged state in both cases. Client Content-Type does not translate formats. Curl’s exit is not the HTTP status.

Server validation remains necessary when a client bypasses/edits converter output. Test incomplete/invalid JSON PUT, valid PUT to absent positive id, and unchanged full state. Restarting Session 7 restores seeds and nextId; local output files may remain while created server records disappear.

## Independent work and assessment

The HTML includes readiness, source explanations, four traces, ten recall quizzes, guided changes, boundary/evidence planning, textbook-task tracking and an independent Room conversion/CRUD assignment. Continue your own Session 7 Room API and Session 5 binding work. The Room assignment adds a conditional rule: Studio permits at most 40 seats inside the general seats range 1–120. Both converter and direct JSON API must enforce it. The page includes milestones, hints, tests and a 100-mark rubric without a completed Room solution.

Submit source/build/schema/examples, exact commands, output/HTTP evidence, BOOK_TASKS.md, one debug note and learning log. Independent work uses notes/editor/compiler/instructor hints without AI-generated solutions. Observe an unseen change and next-day recall before claiming proficiency. The subsequent S1–S3 assignment meetings require the actual instructor/Onlinevarsity brief; the manual’s reference to Jakarta Enterprise Beans in Action is recorded rather than replaced with invented assignments.


Continue with [the original S1 companion assignment workshop](../website/assignment-s1.html#ws1-roadmap) and [its handouts/starter](../assignment-workshops/s1/README.md). The user selected original web-services workshops; the official-brief record remains separate.
