# Session 6 validation — 4 November 2026

Verified on Windows with Oracle JDK 25.0.3, Gradle 9.2.1 and Java 17 compilation target.

- Official Gradle binary archive matched the published SHA256 pinned in the wrapper properties. The wrapper bootstrapped from that verified archive cached locally. First online dependency preparation completed against Maven Central.
- Wrapper-driven lessonInfo, javaBridge, build and installDist passed. The warm-up printed the documented Ada greeting and Notebook price.
- Cached/offline tasks --all and runtimeClasspath dependency report passed.
- The documented Gradle run --args="--hello-only" started the service; curl.exe returned the documented 200 text greeting.
- The installed Windows application script also started greeting-only mode and returned that same 200 text greeting. Verification server processes were stopped after checks.
- 33 real HTTP checks passed across greeting-only, full-mode and restarted Java servers.
- Page checks passed for unique ids, all internal anchors, local assets, copy targets/commands, 9 exact Java/build source excerpts, 16 sections, 9 quizzes and 12 milestones.
- Interaction checks passed for quiz feedback/retry, all three six-step request traces, log save/restore/export and blocked-local-storage fallback. Live browser checks confirmed quiz/trace controls and the narrow-window reading layout with no document overflow.

## HTTP evidence

| Check | Status |
| --- | --- |
| Greeting default | 200 |
| Greeting Ada | 200 |
| Unregistered product resource | 404 |
| Hello-only missing route | 404 |
| Full-mode greeting | 200 |
| Trimmed greeting | 200 |
| Blank name | 400 |
| Spaces-only name | 400 |
| One letter name | 200 |
| Maximum name boundary | 200 |
| Name exceeds boundary | 400 |
| All JSON products | 200 |
| Explicit JSON Accept | 200 |
| Stationery filter | 200 |
| Case-insensitive filter | 200 |
| Trimmed category filter | 200 |
| Accessories filter | 200 |
| No matches | 200 |
| Blank category means all | 200 |
| Spaces category means all | 200 |
| Single item | 200 |
| Missing positive id | 404 |
| Zero id | 400 |
| Negative id | 400 |
| Letters path conversion | 404 |
| Integer overflow path conversion | 404 |
| Unsupported product XML | 406 |
| Unsupported greeting JSON | 406 |
| GET-only rejects POST | 405 |
| GET-only rejects PUT | 405 |
| GET-only rejects DELETE | 405 |
| Unknown full-mode route | 404 |
| Restart recreates fixed seed list | 200 |

The fixed catalogue is recreated from source on restart; this is not a database persistence test. WADL/JAXB startup warnings do not prevent the tested JSON/text responses. The GET-only service rejects write methods; request-body deserialization is outside this session. Browser milestones are self-reported. A trial with actual beginners and observed independent transfer/next-day recall is still required.
