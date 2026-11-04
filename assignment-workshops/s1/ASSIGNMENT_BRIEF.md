# S1 companion assignment: persistent campus equipment API

This is an original Creating Services for the Web companion workshop after teaching Session 8. It is not a reproduction of the missing Onlinevarsity assignment brief. Record the actual course/assignment identifiers in ../BRIEF_RECORD.md when supplied.

Build a small equipment catalogue for a fictional campus. Each record describes an equipment type and the number of available units. Use fictional names. Quantity replacement is an inventory exercise; bookings, checkout/return workflows and accounts are outside this brief.

## Data and input

Stored/response Asset: generated positive id, name, units, category. Input AssetInput: name, nullable Integer units, category, with no id. Use the package com.aptech.assignment.asset.

- Name: required JSON text, nonblank, 1–60 Java UTF-16 code units after trim.
- Category: required JSON text, nonblank, 1–30 code units after trim.
- Units: required JSON integer from 0 to 50 inclusive. Zero means no units available and must remain distinguishable from missing/null.
- Reject extra JSON fields, numeric/boolean text, quoted/decimal/boolean units and trailing JSON values. Reuse/adapt the Session 7 JsonSupport provider; implement your own application checks.
- Duplicated equipment names are allowed. Id identifies the record; collection position and row count do not.

## HTTP contract

Use loopback port 8100 and /api/. The application main class is AssetServer; start it only after implementing and building it.

| Request | Required behavior |
| --- | --- |
| GET /api/assets | 200 JSON array; empty array when empty. Optional category filter is case-insensitive exact equality after trimming. Missing/blank category means all. |
| GET /api/assets/{id} | 200 JSON when found; 404 JSON when absent; nonpositive numeric id gives 400. |
| POST /api/assets | Validate, insert and commit; only then return 201 JSON with generated id and absolute Location. |
| PUT /api/assets/{id} | Validate complete writable data, replace an existing positive id and commit; return 200 with stable id. Valid input for absent id gives 404 and creates nothing. |
| DELETE /api/assets/{id} | Delete/commit if found; 204 with empty body. Absent id gives 404. Nonpositive numeric id gives 400. |
| Media/mapping rejection | JSON input/output; text/plain body gives 415, XML Accept gives 406, invalid JSON gives 400. Framework error bodies can differ from your explicit JSON errors. |

For mapped PUT data, check positive id, then application rules, then existence; invalid data for absent positive id gives 400. Unconvertible path text can be rejected by Jersey before the method is called. Register strict JsonSupport and JacksonFeature. Validate before starting a write operation; malformed/type errors occur during mapping.

## Persistence and transaction contract

Use Jakarta Persistence, Hibernate and a personal H2 file. The starter configuration uses persistence unit assets-workshop and explicitly lists your Asset entity. Write Asset, AssetInput, AssetView (response snapshot), AssetRules, AssetStore, AssetResource, AssetServer and a console rollback demonstration yourself.

Map Asset to campus_assets with generated asset_id and columns asset_name, available_units and asset_category. Use JPQL entity/property names, not physical table/column names. Bind query values as parameters. Apply normalized case-insensitive category equality consistently with the HTTP filter contract.

Use one EntityManagerFactory per process and one EntityManager per store operation. Create/update/delete run in an explicit transaction. Commit before reporting HTTP success; roll back active failed transactions and close contexts. Return response snapshots after store operations rather than retaining managed objects across requests.

No startup clearing or seeding. A new personal database begins empty; an existing database retains committed records. Print the absolute database path and use the same file on restart. One Java process at a time opens that file; stop the service before console database work.

Demonstrate rollback in a separate console run: find an actual record, begin a transaction, temporarily change units, flush, then roll back. A fresh read/process must retain the committed original value. Flush is not commit. Do not add a public rollback/debug HTTP endpoint.

## Milestones

1. Write the contract, class/file plan and predicted tests.
2. Implement the entity, input/view/rules and prove plain Java boundaries.
3. Implement JPA create/find/list/filter and commit/restart proof.
4. Implement replacement/deletion and explicit rollback proof.
5. Implement/register REST GET/POST and prove Location with a following GET.
6. Add full PUT/DELETE and demonstrate filters and rejected-state preservation.
7. Record the whole request sequence and restart persistence from an unchanged database file.
8. Explain an instructor-selected unseen change, then repeat a smaller change next day.

## Tests and submission

Test units -1, 0, 50, 51; missing/null/quoted/decimal/boolean units; text lengths 1/max/max+1; blank text; malformed/extra/trailing JSON; unsupported media; absent/nonpositive ids; and partial PUT. Compare complete stored state before/after rejected writes. Rejected requests must not insert/update/delete records. Database identity sequences can have gaps after rolled-back inserts; do not impose an in-memory nextId rule on database allocation.

Create two records, update only one (including category), delete only the other, and prove the survivor’s values/filter membership. Repeat PUT/DELETE and explain intended state. Restart and prove create/update/delete remained committed. Record rollback evidence separately.

Submit your source/build configuration, request files, exact run instructions, actual ids/Locations/database path, predictions and observed results, one debug note and ASSIGNMENT_EVIDENCE.md. Keep submission outside target. Use notes/editor/compiler and instructor hints without AI-generated application solutions. Build/parser helpers may be reused, with their origin stated. Actual brief requirements, when supplied, are recorded separately.

## Companion rubric

| Criterion | Marks |
| --- | --- |
| Contract and input/entity/view separation | 15 |
| JPA mappings, queries, transaction cleanup, rollback/restart proof | 25 |
| GET/POST and 201/Location/following GET | 15 |
| Complete PUT/DELETE, stable ids and filter membership | 15 |
| Boundaries, strict parsing, unchanged-state and debug evidence | 15 |
| Own explanation, unseen change and next-day recall | 15 |
| Total | 100 |

This rubric grades the companion task. Official assignments may have their own criteria. Software checks of the handout or starter helpers do not grade an unobserved learner implementation.
