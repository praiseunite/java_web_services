# S1 assignment workshop: persistent equipment API

Use [the dedicated HTML workshop](../../website/assignment-s1.html#ws1-roadmap) and [ASSIGNMENT_BRIEF.md](ASSIGNMENT_BRIEF.md). This is original companion practice after teaching Session 8. The supplied manual lists S1–S3 assignment meetings but omits the briefs and refers to a different course title. Record the instructor’s actual requirements in [BRIEF_RECORD.md](../BRIEF_RECORD.md); this workshop is not an official Onlinevarsity question.

The beginner workshop reviews Java decisions, helps plan a persistent REST service, provides small build/database/parser support, then requires learners to implement and explain the application. It supplies no completed Asset entity/store/resource/server. No learner server or database is created by opening the lesson.

## Runnable readiness rehearsal

```powershell
Set-Location "C:\Projects\Aptech\creating service\assignment-workshops\s1"
javac beginner/WorkshopWarmup.java
java -cp beginner WorkshopWarmup
```

Expected: null and -1 rejected; 0/1/14 days produce 0/25/350 NGN; 15 rejected. The Map is a prediction record, not JSON or an actual HTTP test. Change rate/limit in a personal copy and predict new boundaries before running. This JDK-only exercise opens no database or server.

## Prepare a new personal project

From this s1 folder, choose a destination that does not exist:

```powershell
New-Item -ItemType Directory ../my-s1-assets
Copy-Item -LiteralPath starter-config/pom.xml,starter-config/src,examples -Destination ../my-s1-assets -Recurse
Set-Location ../my-s1-assets
java -version
javac -version
mvn -version
mvn validate
```

Use JDK 17+ and Maven. The build configuration reuses Jakarta Persistence 3.1.0, Hibernate 6.6.5.Final, H2 2.3.232 and Jersey 3.1.5 with Java 17 compilation. First complete build may need network access. validate checks Maven configuration; it does not prove application behavior. The starter’s Database helper compiles, but its persistence.xml references the Asset you must write, and the JAR manifest names the AssetServer you must implement.

Write application files under src/main/java/com/aptech/assignment/asset. Reuse the strict JsonSupport helper from Session 7, changing its package to match this project and registering it with JacksonFeature. Follow the brief’s mappings, input/response separation, parameterized category query and transaction rules. Use the Session 1 JPA and Session 7 REST explanations as references, with completed application methods closed for independent work.

## Launch after implementing your application

```powershell
mvn package
java --add-modules jdk.httpserver -jar target/s1-assets-1.0.jar
```

Your AssetServer must start loopback port 8100 /api/assets, print its actual H2 file and startup URL, and implement Enter-to-stop cleanup adapted from the earlier persistence server. Wait for those lines before sending requests from terminal B in the same personal project folder. Stop A before console rollback work or rebuilding. Use the same folder/database file on restart. Set an alternate file by adapting the provided assets.db property consistently; use a separate file per learner.

```powershell
curl.exe -i http://127.0.0.1:8100/api/assets
curl.exe -i -X POST http://127.0.0.1:8100/api/assets -H "Content-Type: application/json" --data-binary "@examples/new-asset.json"
$ws1ItemUrl = Read-Host "Paste the Location from your successful POST"
curl.exe -i "$ws1ItemUrl"
curl.exe -i -X PUT "$ws1ItemUrl" -H "Content-Type: application/json" --data-binary "@examples/updated-asset.json"
curl.exe -i "$ws1ItemUrl"
curl.exe -i "http://127.0.0.1:8100/api/assets?category=Media"
curl.exe -i "http://127.0.0.1:8100/api/assets?category=peripherals"
```

These are acceptance commands for your future implementation, not a supplied running endpoint. Expect creation 201 with absolute Location, GET 200 and complete replacement 200 with stable id/count, name HD Webcam, units 0 and category Peripherals. Use the actual Location, not an assumed id. Record before/after lists for rejection tests.

Preserve one record for restart/rollback proof. Create a second fixture/record, record its Location, delete only that second record and follow with GET/repeated DELETE. Expect 204 with empty body then 404/404. Restart with the same H2 file and prove the first record’s changed values remain and the second remains absent. Existing names can be duplicates; ids need not be consecutive.

Use ASSIGNMENT_EVIDENCE.md for boundaries, strict mapping, absent ids, direct incomplete PUT, explicit flush/rollback, debug notes and observed unseen/next-day changes. The companion rubric totals 100 marks. S2/S3 are later companion workshop stages; their full lessons are not implemented in this step.
