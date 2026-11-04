# Session 7 verification — 4 November 2026

Tested on Windows with Oracle JDK 25.0.3, Gradle 9.2.1 and Java 17 compilation target.

- The Session 6 wrapper and published distribution SHA256 were reused; the build used the already prepared project cache. No new dependency versions were added.
- Wrapper-driven lessonInfo, javaBridge, build and installDist passed offline. The Java warm-up printed all documented create/replace/delete/rejection results.
- 117 HTTP checks passed across read-only mode, full CRUD and a fresh restarted server.
- 79 rejected writes were each followed by a complete state comparison proving no catalogue mutation.
- Checks include POST/absolute Location/following GET, repeated POST, full PUT/repeated PUT/category changes, DELETE/empty 204/following 404, complete-field/type/range rejection, missing ids/no upsert, mapping/media errors, and restart/id-generator reset.
- Page checks passed for all ids/internal anchors/local assets/copy targets, command equality and 18 exact Java/build/fixture excerpts. The lesson has 17 sections, 10 quizzes, 13 milestones and four request traces.
- Interaction checks passed for quiz feedback/retry, four six-step traces, saved/restored/exported learning logs and unavailable-local-storage fallback.
- The actual Windows Gradle `run --args="--read-only"` launch was checked with `curl.exe`: GET returned 200 with the three seeds and POST returned 405.
- The installed Windows launcher started full CRUD. The documented `curl.exe --data-binary "@examples/..."` file-body sequence returned POST 201 with absolute Location/id 4, following GET 200, PUT 200 with updated fields, following GET 200, DELETE 204 with no body, then GET/repeated DELETE 404. Both verification Java servers were stopped afterwards; port 8099 was free.
- Live in-app-browser checks passed at `http://127.0.0.1:18767/index.html#s7-roadmap`: quiz feedback and retry, the six-step DELETE trace, and return to Session 7's starting point. No captured console warnings/errors or horizontal document overflow occurred at the actual 600px viewport. The lesson remains open, with screenshot proof saved as `.build-cache/session7-preview.png` in the course workspace.

## HTTP checks

| Case | Status | Unchanged state checked |
| --- | --- | --- |
| Read-only seed GET | 200 | — |
| Read-only rejects POST | 405 | Yes |
| Read-only rejects PUT | 405 | Yes |
| Read-only rejects DELETE | 405 | Yes |
| Full-mode seed GET | 200 | — |
| Fixture POST | 201 | — |
| Fixture POST Location GET | 200 | — |
| Repeated POST creates another id | 201 | — |
| Repeated POST creates another id Location GET | 200 | — |
| Fixture PUT | 200 | — |
| PUT read proof | 200 | — |
| Identical repeated PUT | 200 | — |
| Category filter after writes | 200 | — |
| PUT changes category | 200 | — |
| Old filter excludes replacement | 200 | — |
| New filter includes replacement | 200 | — |
| Successful DELETE empty body | 204 | — |
| GET after DELETE | 404 | — |
| Repeated DELETE | 404 | Yes |
| Deleted id is not reused | 201 | — |
| Deleted id is not reused Location GET | 200 | — |
| POST rejects blank name | 400 | Yes |
| PUT rejects blank name | 400 | Yes |
| POST rejects missing name | 400 | Yes |
| PUT rejects missing name | 400 | Yes |
| POST rejects null name | 400 | Yes |
| PUT rejects null name | 400 | Yes |
| POST rejects name length 61 | 400 | Yes |
| PUT rejects name length 61 | 400 | Yes |
| POST rejects missing price | 400 | Yes |
| PUT rejects missing price | 400 | Yes |
| POST rejects null price | 400 | Yes |
| PUT rejects null price | 400 | Yes |
| POST rejects negative price | 400 | Yes |
| PUT rejects negative price | 400 | Yes |
| POST rejects excessive price | 400 | Yes |
| PUT rejects excessive price | 400 | Yes |
| POST rejects missing category | 400 | Yes |
| PUT rejects missing category | 400 | Yes |
| POST rejects null category | 400 | Yes |
| PUT rejects null category | 400 | Yes |
| POST rejects blank category | 400 | Yes |
| PUT rejects blank category | 400 | Yes |
| POST rejects category length 31 | 400 | Yes |
| PUT rejects category length 31 | 400 | Yes |
| POST rejects quoted price | 400 | Yes |
| PUT rejects quoted price | 400 | Yes |
| POST rejects boolean price | 400 | Yes |
| PUT rejects boolean price | 400 | Yes |
| POST rejects decimal price | 400 | Yes |
| PUT rejects decimal price | 400 | Yes |
| POST rejects numeric name | 400 | Yes |
| PUT rejects numeric name | 400 | Yes |
| POST rejects boolean name | 400 | Yes |
| PUT rejects boolean name | 400 | Yes |
| POST rejects numeric category | 400 | Yes |
| PUT rejects numeric category | 400 | Yes |
| POST rejects unknown id field | 400 | Yes |
| PUT rejects unknown id field | 400 | Yes |
| POST rejects misspelled field | 400 | Yes |
| PUT rejects misspelled field | 400 | Yes |
| POST rejects null object | 400 | Yes |
| PUT rejects null object | 400 | Yes |
| POST rejects array body | 400 | Yes |
| PUT rejects array body | 400 | Yes |
| POST rejects string body | 400 | Yes |
| PUT rejects string body | 400 | Yes |
| POST rejects empty body | 400 | Yes |
| PUT rejects empty body | 400 | Yes |
| POST rejects malformed fixture | 400 | Yes |
| PUT rejects malformed fixture | 400 | Yes |
| POST rejects trailing object | 400 | Yes |
| PUT rejects trailing object | 400 | Yes |
| POST rejects whole-value decimal token | 400 | Yes |
| PUT rejects whole-value decimal token | 400 | Yes |
| POST rejects exponent price | 400 | Yes |
| PUT rejects exponent price | 400 | Yes |
| POST rejects integer overflow | 400 | Yes |
| PUT rejects integer overflow | 400 | Yes |
| Valid PUT does not upsert absent id | 404 | Yes |
| Invalid PUT before absence lookup | 400 | Yes |
| GET rejects id 0 | 400 | — |
| PUT rejects id 0 | 400 | Yes |
| DELETE rejects id 0 | 400 | Yes |
| GET rejects id -1 | 400 | — |
| PUT rejects id -1 | 400 | Yes |
| DELETE rejects id -1 | 400 | Yes |
| GET rejects path conversion abc | 404 | — |
| PUT rejects path conversion abc | 404 | Yes |
| DELETE rejects path conversion abc | 404 | Yes |
| GET rejects path conversion 2147483648 | 404 | — |
| PUT rejects path conversion 2147483648 | 404 | Yes |
| DELETE rejects path conversion 2147483648 | 404 | Yes |
| POST rejects text body media type | 415 | Yes |
| POST rejects XML body media type | 415 | Yes |
| POST rejects XML Accept without mutation | 406 | Yes |
| PUT rejects text body media type | 415 | Yes |
| PUT rejects XML body media type | 415 | Yes |
| PUT rejects XML Accept without mutation | 406 | Yes |
| Unsupported PATCH | 405 | Yes |
| Unknown path | 404 | — |
| Name/category minimum and price zero | 201 | — |
| Name/category minimum and price zero Location GET | 200 | — |
| Name/category maximum and maximum price | 201 | — |
| Name/category maximum and maximum price Location GET | 200 | — |
| Trimmed input normalization | 201 | — |
| Trimmed input normalization Location GET | 200 | — |
| Valid PUT accepts price zero | 200 | — |
| Valid PUT accepts maximum boundaries | 200 | — |
| Blank category gives full list | 200 | — |
| Case-insensitive trimmed filter | 200 | — |
| Unknown category gives empty list | 200 | — |
| Delete seed for restart proof | 204 | — |
| Confirm deleted seed | 404 | — |
| Restart restores original seeds | 200 | — |
| Restart forgets created id | 404 | — |
| Restart resets id generator | 201 | — |

The storage test concerns local in-memory state, not durable persistence. Restart intentionally loses request mutations. Framework mapping/media rejection bodies differ from explicit JSON domain errors. These checks establish software behaviour; actual beginner trials, observed independent changes and next-day recall remain pending.
