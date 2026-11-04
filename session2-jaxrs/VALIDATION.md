# Session 2 reference-project verification

The project was built with Maven 3.9.16 on JDK 25, compiling all seven Java files with release 17. The bundled runnable JAR was tested rather than a separate mock implementation.

34 HTTP checks passed across greeting-only and full modes: default/named/encoded/blank greeting inputs, registration differences, collection and lookup, case-insensitive query filtering, empty results, missing/nonpositive/non-integer ids, unmatched routes, 405/406/415, missing/null/blank/malformed/overlong create bodies, valid and duplicate creation, 80-character boundary, Location followed by GET, unique ids under concurrent requests, rejected-request non-mutation and fresh data after restart.

All seven Java listings in the HTML were compared with the source files. Browser review checks the lesson's links, quizzes, request traces, progress/log controls and responsive layout. This validates the reference example; students must still complete and explain their own independent submissions.
