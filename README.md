# Aptech Course: Creating Services for the Web
## Start here: beginner Sessions 1–8

The HTML now includes **1.8B**, a runnable Hibernate/H2 database lab with transactions, restart persistence, queries, updates, rollback, deletion and an independent Book task; **2.13B** connects it to a persistent Jersey REST service. See [session1-jpa-lab](session1-jpa-lab/README.md). Its setup is separate from the five JDK-only examples.

A meeting-by-meeting practice schedule and a trial protocol for 2–3 actual beginners are included in the HTML. The instructor materials are in [teaching-support](teaching-support/BEGINNER_TRIAL.md). The trial is prepared and awaits real observations; no learner outcome is claimed yet.

Open `index.html` to enter the HTML learning portal. Session 1 now starts with Java setup and first-program explanations, then develops request logic, a real HTTP service, SOAP/REST concepts, and introductory JPA mapping and JPQL.

The supplied August 2024 presenter's manual maps teaching Session 1 (WEBSRV–TL1) to **book Sessions 1 and 2**: Web Services and Jakarta Persistence. The HTML includes both, with guided changes, independent practice, a campus-service assignment, troubleshooting, quizzes, and a downloadable learning log. Students should complete and explain their own work before moving on.

The five JDK-only examples are in [session1-intro/beginner](session1-intro/beginner). Read [Session 1 notes](session1-intro/README.md) for run commands and scope.

**Session 2 follows the manual's JAX-RS chapter.** The HTML progresses from a greeting resource to path/query parameters, JSON reads and validated POST requests, then independent practice and a library API assignment. Its runnable Jersey project is in [session2-jaxrs](session2-jaxrs/README.md). It includes a Maven build that creates a self-contained JAR for a JDK 17+ runtime. The previous XML lesson remains an optional supplement; Session 3 covers JAX-WS with a beginner SOAP service, WSDL/types/faults, real clients, stub generation and independent work. See [Session 3 notes](session3-soap-wsdl/README.md).

**Teaching Session 4 is integrated practice**, as WEBSRV–TL4 assigns the Try It Yourself questions from book chapters 1–4. The HTML provides a staged Java → H2 → REST → SOAP → generated-client library project, then an independent equipment catalogue with boundary tests, restart proof, a rubric and next-day recall. See [Session 4 practice](session4-practice/README.md). These are original companion exercises; the instructor must also assign the exact questions from the learner's book, which the supplied manual does not reproduce. **Session 5 now teaches JAXB** with explicit mapping, XML file conversion, round-trip proof, chosen-schema validation, separate application rules and XJC-generated model classes. Its HTML lesson includes API comparisons, independent loan-ticket work, a rubric and recall checks. See [Session 5 notes](session5-jaxb/README.md). **Session 6 now introduces Jersey and Gradle** with a pinned wrapper, plain Java warm-up, greeting-first service, JSON GET catalogue, boundary tests and an independent room API assignment. See [Session 6 notes](session6-jersey-gradle/README.md) and the [course progress review](teaching-support/COURSE_PROGRESS.md). **Session 7 now adds validated CRUD** in a Gradle-managed Jersey project, with separate request/response models, strict JSON mapping, POST/Location, full PUT replacement, DELETE/204, unchanged-state rejection tests and independent Room CRUD work. See [Session 7 notes](session7-jersey-crud/README.md). **Session 8 now consolidates JAXB and Jersey** with a validated XML-to-JSON companion project, actual HTTP/file evidence, textbook-task tracking and independent Room conversion/CRUD work. See [Session 8 practice](session8-practice/README.md).

## Session 6: Jersey and Gradle

Start at [the Session 6 roadmap](website/index.html#s6-roadmap). The new [session6-jersey-gradle](session6-jersey-gradle/README.md) is separate from the older combined Sessions 6–7 example. It teaches Gradle tasks/dependencies and real GET requests before Session 7 adds write methods. Read the [course progress record](teaching-support/COURSE_PROGRESS.md) for the manual mapping and outstanding learner validation.

## Session 7: Jersey CRUD

Continue with [the Session 7 roadmap](website/index.html#s7-roadmap) and [its reference project](session7-jersey-crud/README.md). Start GET-only mode, then create/read/replace/delete with file-body requests and following GET evidence. The [validation record](session7-jersey-crud/VALIDATION.md) distinguishes software checks from learner proficiency.

## Session 8: JAXB and Jersey practice

Use [the Session 8 roadmap](website/index.html#s8-roadmap) and [its conversion project](session8-practice/README.md). Validate XML, prove a round-trip, prepare JSON, then POST/GET/PUT/DELETE through the separate Session 7 API. Assign the exact book chapters 5–6 questions and record them in [BOOK_TASKS.md](session8-practice/BOOK_TASKS.md). The independent Room task includes matching converter/server rules, boundary tests and next-day recall. Subsequent S1–S3 assignment meetings require the actual instructor/Onlinevarsity brief.

## S1: first companion assignment workshop

Continue after Session 8 with [the dedicated S1 lesson](website/assignment-s1.html#ws1-roadmap) and [the assignment handouts/starter](assignment-workshops/s1/README.md). Learners plan and implement a persistent equipment REST API, prove transactions/rollback/restart, test strict input and complete CRUD, and explain an unseen change. The pack includes a Java readiness exercise, configuration support, fixtures, milestones and a 100-mark companion rubric. These are original web-services workshops; S2/S3 are the next stages.

## Additional instructor and student materials

Welcome to the comprehensive teaching and implementation repository for **"Creating Services for the Web"** (Aptech Ver 1.0, August 2024 syllabus).

The HTML beginner sequence uses explanations, guided coding, independent variations and runnable projects. The older supplementary folders below retain their original names; the folder number in `session4-jaxws` is a book-chapter label, not the teaching Session 4 practice lesson.

---

## 📂 Repository Directory Layout

```
creating service/
│
├── TEACHING_GUIDE_FOR_INSTRUCTORS.md  <-- Master pedagogical manual & lecture notes
│
├── session6-jersey-gradle/            <-- Teaching Session 6: wrapper, build tasks and GET catalogue
├── session7-jersey-crud/              <-- Teaching Session 7: validated POST/PUT/DELETE and independent work
├── session8-practice/                 <-- Teaching Session 8: JAXB/Jersey practice and book-task tracking
├── assignment-workshops/              <-- Original S1–S3 companion assignment stage; S1 prepared
│
├── session4-practice/                 <-- Teaching Session 4: Java/JPA/REST/SOAP practice
│   ├── README.md                      <-- Stages, commands, evidence and independent work
│   └── generated-client/              <-- Separate WSDL-generated library client
│
├── session4-jaxws/                    <-- Legacy SOAP enrichment (book chapter 4)
│   ├── README.md                      <-- Detailed concepts, WSDL, Stubs & Interoperability
│   └── src/main/java/com/aptech/jaxws/
│       ├── CalculatorService.java     <-- Service Endpoint Interface (@WebService, @WebMethod)
│       ├── CalculatorServiceImpl.java <-- Service Implementation Bean
│       ├── ServerPublisher.java       <-- Publishes SOAP service & WSDL on localhost
│       └── SoapClientExample.java     <-- Client calling SOAP service via dynamic stub/proxy
│
├── session5-jaxb/                     <-- Session 5: Java Architecture for XML Binding
│   ├── README.md                      <-- Marshalling, Unmarshalling, XSD, JAX-RS vs JAX-WS vs JAXB
│   ├── student_schema.xsd             <-- XML Schema Definition (XSD)
│   └── src/main/java/com/aptech/jaxb/
│       ├── Student.java               <-- XML-mapped data class with @XmlRootElement, @XmlElement, @XmlTransient
│       ├── JaxbMarshallingDemo.java   <-- Java Object -> XML (Formatted output + file save)
│       └── JaxbUnmarshallingDemo.java <-- XML -> Java Object
│
└── session6-7-jersey-mini-project/    <-- Sessions 6 & 7: Mini-Project (Jersey REST API & Gradle)
    ├── build.gradle                   <-- Gradle automated build script with dependencies
    ├── settings.gradle
    ├── STUDENT_LAB_MANUAL.md          <-- Lab exercises for GET, POST, PUT, DELETE with cURL & Postman
    └── src/main/java/com/aptech/webservice/
        ├── Main.java                  <-- Embedded Grizzly HTTP server (1-second startup)
        ├── model/Product.java         <-- Product POJO with JSON/XML binding
        ├── dao/ProductDatabase.java   <-- In-memory repository (mock database)
        └── resource/ProductResource.java <-- JAX-RS Controller with full CRUD operations
```

---

## 🎓 Syllabus Deliverables Mapping

| Session No. | Title | Deliverables Covered | Location in Repository |
| :---: | :--- | :--- | :--- |
| **Teaching Session 4** | **Integrated practice** | Original companion exercises for book chapters 1–4: Java, JPA persistence, REST, SOAP, generated clients, testing and independent transfer. Assign the actual textbook questions separately. | [session4-practice](session4-practice/README.md) |
| **Book chapter 4 enrichment** | **Jakarta Web Services with JAX-WS** | Older SOAP examples supplement teaching Session 3. | [session4-jaxws](session4-jaxws/README.md) |
| **Session 5** | **Java Architecture for XML Binding (JAXB)** | - Explain JAXB<br>- Describe marshalling and unmarshalling in JAXB<br>- Explain the importance of JAXB for XML schemas<br>- Discuss best practices for XML/Schema Data Binding<br>- Distinguish between JAX-RS, JAX-WS, and JAXB<br>- Identify advantages and disadvantages | [session5-jaxb/](file:///c:/Projects/Aptech/creating%20service/session5-jaxb/README.md) |
| **Session 6** | **Jersey and Gradle foundations** | Wrapper/setup, tasks/dependencies, greeting-first GET service, JSON catalogue, independent room API and recall | [session6-jersey-gradle](session6-jersey-gradle/README.md) |
| **Session 7** | **Jersey CRUD in Gradle** | Validated request models, POST/Location, full PUT replacement, DELETE/204, failure-state proof and Room assignment | [session7-jersey-crud](session7-jersey-crud/README.md) |
| **Session 8** | **JAXB/Jersey practice** | Validated XML → Java → JSON, round-trip, actual CRUD evidence, book-task record and independent Room assignment | [session8-practice](session8-practice/README.md) |
| **Older Sessions 6 & 7 reference** | **Mini Project: Developing Web Services Using Jersey** | - Explain Jersey RESTful Web services<br>- Explain Gradle<br>- Describe the implementation of Jersey in Gradle-managed project<br>- Explain creating different HTTP requests in Resource class (`GET`, `POST`, `PUT`, `DELETE`) | [session6-7-jersey-mini-project/](file:///c:/Projects/Aptech/creating%20service/session6-7-jersey-mini-project/STUDENT_LAB_MANUAL.md) |

---

## 🚀 Quick Start for Instructors
1. Read the pedagogical strategy and mental models in [TEACHING_GUIDE_FOR_INSTRUCTORS.md](file:///c:/Projects/Aptech/creating%20service/TEACHING_GUIDE_FOR_INSTRUCTORS.md).
2. For teaching Session 4, use [the practice lesson](website/index.html#s4-roadmap): verify prerequisites, work through staged library exercises and assess the independent equipment assignment. Use the older SOAP folder only as supplementary material.
3. For Session 5, run `JaxbMarshallingDemo.java` and `JaxbUnmarshallingDemo.java` to show the suitcase packing/unpacking concept.
4. For teaching Session 6, use the [beginner HTML lesson](website/index.html#s6-roadmap) and [pinned-wrapper project](session6-jersey-gradle/README.md). For the older Sessions 6 & 7 reference, distribute [STUDENT_LAB_MANUAL.md](file:///c:/Projects/Aptech/creating%20service/session6-7-jersey-mini-project/STUDENT_LAB_MANUAL.md) and have students execute the HTTP requests using their browser, PowerShell, and Postman.
