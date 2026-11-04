# Teaching Session 7: Jersey CRUD in a Gradle project

Use [the beginner HTML lesson](../website/index.html#s7-roadmap). WEBSRV–TL7 in the supplied August 2024 manual continues the book’s Jersey mini project chapter with Gradle-managed implementation and HTTP methods in resource classes. This is an original companion exercise; assign the exact learner-book activities separately.

Session 6 taught tools and a GET catalogue. This project adds validated POST, complete PUT replacement and DELETE, with before/after GET evidence. Use this new folder for the commands below. The older `session6-7-jersey-mini-project` is separate legacy reference.

## Setup and baseline

Use JDK 17–25 and the included Gradle 9.2.1 wrapper. Java compilation targets 17. Keep both wrapper scripts and `gradle/wrapper` when copying the project. First-time setup needs network access; cached offline builds need all libraries already present.

```powershell
Set-Location "C:\Projects\Aptech\creating service\session7-jersey-crud"
java -version
javac -version
.\gradlew.bat --version
.\gradlew.bat lessonInfo
.\gradlew.bat javaBridge
.\gradlew.bat build
.\gradlew.bat run --args="--read-only"
```

Use your copy’s path if different. In terminal A wait for `Session 7 server: http://127.0.0.1:8099/api/products` and `Read-only mode.` In terminal B:

```powershell
curl.exe -i http://127.0.0.1:8099/api/products
curl.exe -i -X POST http://127.0.0.1:8099/api/products
```

GET returns the three fictional Session 6 products; POST returns 405 because write methods are not registered. Gradle compiles all supplied Java files in either mode. `build` does not contain an automated endpoint test task; `test NO-SOURCE` is not endpoint verification.

`javaBridge` prints initial count 3, creates id 4 USB Hub, replaces price with 8500, deletes it, prints final count 3 and rejects a blank name. Each run has a fresh store and does not change a separate HTTP process.

## Full write sequence

Stop A with Ctrl+C, then run without `--read-only`:

```powershell
.\gradlew.bat run
```

In B enter the same project folder for file bodies:

```powershell
Set-Location "C:\Projects\Aptech\creating service\session7-jersey-crud"
Get-Content examples/new-product.json
curl.exe -i -X POST http://127.0.0.1:8099/api/products -H "Content-Type: application/json" --data-binary "@examples/new-product.json"
curl.exe -i http://127.0.0.1:8099/api/products/4
curl.exe -i -X PUT http://127.0.0.1:8099/api/products/4 -H "Content-Type: application/json" --data-binary "@examples/updated-product.json"
curl.exe -i http://127.0.0.1:8099/api/products/4
curl.exe -i -X DELETE http://127.0.0.1:8099/api/products/4
curl.exe -i http://127.0.0.1:8099/api/products/4
curl.exe -i -X DELETE http://127.0.0.1:8099/api/products/4
```

On a fresh full server the first POST returns 201, body id 4/USB Hub/7500/Accessories, and absolute Location `http://127.0.0.1:8099/api/products/4`. Use the actual returned id/Location if you have made earlier creates. PUT returns 200 with id 4/USB Hub Plus/8500/Accessories. DELETE returns 204 with an empty body; the following GET and repeated DELETE return 404 JSON. JSON property order/formatting and headers may differ.

PUT requires all writable fields and replaces an existing positive id only. Valid data for absent id 999 returns 404 without creating it. Identical repeated PUT preserves the same values/count. Repeated POST creates distinct ids. Deleted ids are not reused in the same process. Idempotency concerns intended state, so DELETE’s 204 then 404 still leaves the item absent.

## Request contract and rejection

`ProductInput` contains name, nullable Integer priceNgn and category. Jackson maps incoming JSON; `ProductRules` checks it before the store is called. `Product` is immutable stored/response data. The server assigns its id separately.

- Name and category must be JSON text, nonblank after trim, with maximum lengths 60 and 30 respectively.
- Prices are fictional whole-naira integers 0–1000000 inclusive. Missing/null prices are invalid; zero is deliberately allowed. String/decimal/boolean prices are rejected.
- `JsonSupport` rejects unknown properties (including id), incompatible scalar types and a second trailing JSON value. Numeric/boolean name/category values are not silently converted to text.
- ProductRules checks Java String.length (UTF-16 code units); use ordinary letters/spaces for beginner text boundaries.
- POST and PUT consume JSON. Unsupported Content-Type gives 415; unsupported XML Accept gives 406. Framework rejection bodies may differ from our JSON domain errors.
- Zero/negative numeric path ids give our 400. Letters/int overflow in a path give Jersey’s 404 conversion rejection before the method runs.
- After mapping, PUT checks positive id, then body rules, then existence. Thus valid body/absent positive id gives 404, while invalid body/absent positive id gives 400.

From B:

```powershell
curl.exe -i -X POST http://127.0.0.1:8099/api/products -H "Content-Type: application/json" --data-binary "@examples/invalid-name.json"
curl.exe -i -X PUT http://127.0.0.1:8099/api/products/2 -H "Content-Type: application/json" --data-binary "@examples/invalid-price.json"
curl.exe -i -X POST http://127.0.0.1:8099/api/products -H "Content-Type: application/json" --data-binary "@examples/malformed.json"
curl.exe -i http://127.0.0.1:8099/api/products/2
```

These bad writes give 400. Compare GET before/after: id, count and fields should remain unchanged. Rejected POSTs do not advance the id generator. Curl can receive a 400 successfully; its exit code is not the HTTP status. Use `-i` and inspect the response.

## Storage and installation

The synchronized LinkedHashMap makes each store operation atomic with respect to other store calls. Products are immutable and list reads return copies. This is shared local memory, not JPA or durable database persistence. Restart restores only ids 1–3 and their original values; request mutations are lost and nextId resets to 4. This teaching project includes no accounts or production deployment. Optional WADL XML documentation is disabled.

Save, stop, rebuild/restart after source changes. Account for the reset when repeating a request sequence. Stop a prior server before switching modes; if using a different free port, update every URL consistently.

```powershell
.\gradlew.bat installDist
.\build\install\session7-jersey-crud\bin\session7-jersey-crud.bat --read-only
```

Omit `--read-only` for CRUD. The installed launcher includes dependency JARs and the HTTP module option and starts Java directly. The ordinary small jar is not self-contained. `installDist` creates local output, not an internet deployment.

## Independent work

The HTML includes changed request sequences, price-limit boundaries, a summary resource and a deliberately broken replacement exercise. Its independent Room CRUD assignment continues Session 6’s own Room model/read/filter work, adding separate input data, validated creation, full replacement and deletion while preserving filters. It includes milestone hints, boundary/evidence requirements and a 100-mark rubric without a completed Room solution.

Submit source, exact commands, request files, predicted/actual status/Location/body, following GET evidence, one debug note and the downloaded learning log. Keep evidence outside build. Quizzes and browser checkboxes are aids and self-reports. Observe an unseen changed requirement and next-day recall before claiming independent proficiency. Continue with [the Session 8 practice lesson](../website/index.html#s8-roadmap) and [its XML-to-JSON project](../session8-practice/README.md), which consolidates the learner-book chapters 5–6 activities through original companion work. Assign the actual book questions separately.
