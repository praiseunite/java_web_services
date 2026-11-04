# TEACHING MANUAL & COURSE GUIDE
## Course: Creating Services for the Web (Sessions 4, 5, 6, 7, 8)
### Target Audience: First-Time Learners & Absolute Beginners

For the current teaching sequence and manual mapping, read [COURSE_PROGRESS.md](teaching-support/COURSE_PROGRESS.md). **Teaching Session 6** now has its own [Jersey/Gradle HTML lesson](website/index.html#s6-roadmap) and [reference project](session6-jersey-gradle/README.md). Teach the wrapper and build tasks explicitly, start greeting-only mode, then add the read-only JSON catalogue. Assess the independent room API with real requests, a live changed requirement and next-day recall. **Teaching Session 7** now has a [Jersey CRUD lesson](website/index.html#s7-roadmap) and [pinned-wrapper reference project](session7-jersey-crud/README.md). Start read-only, then teach POST/Location, complete PUT replacement and DELETE/204. For every rejected write compare before/after GET state. Assess the independent Room CRUD work with source, real requests, an unseen change and next-day recall. **Teaching Session 8** now has a [JAXB/Jersey practice lesson](website/index.html#s8-roadmap) and [validated XML-to-JSON reference](session8-practice/README.md). Assign the exact Try It Yourself questions from book chapters 5 and 6 separately and record identifiers/attempts in BOOK_TASKS.md. Rehearse binding/schema/business checks, round-trip values and JSON preparation; prove real HTTP changes with following GETs. Observe the independent Room task, including the conditional Studio capacity rule on both conversion and direct JSON API paths. Record help, unseen changes and next-day recall. The subsequent S1–S3 Onlinevarsity assignment brief is not included in the supplied manual. The user selected original web-services companion assignment workshops. Start [S1’s persistent equipment API task](website/assignment-s1.html#ws1-roadmap), use [its brief/evidence/starter pack](assignment-workshops/s1/README.md), and assess actual learner code, JPA commit/rollback/restart, CRUD/filter/rejection evidence and an unseen change. Build/database/parser support may be reused; the Asset application is learner-written. S2/S3 are later companion stages. The older combined mini project below is legacy enrichment with separate limitations.

For the updated first teaching session, start with [Revised Session 1: teaching from zero](#revised-session-1-teaching-from-zero) and the HTML portal. The older session labels below still require syllabus review.

For teaching Session 2, use the new JAX-RS lesson in `website/index.html` and the reference project in `session2-jaxrs`. It progresses through greeting-only mode, annotations and request routing, data-transfer objects/JSON, temporary storage, GETs and validated POSTs. Complete Maven setup before class when possible. Ask students to predict status and body, then run the commands and explain which work Jersey performs versus their resource method.

The former XML parsing lesson is now supplementary. Assess Session 2 with its independent tasks and 25-point library API rubric, including a small live change. Checklist ticks are self-reported; verify code, test logs and explanations. The reference store resets on restart and is not JPA persistence. Full CRUD and production concerns are separate later work.

---

# Pedagogical Philosophy: Teaching Complete Beginners
When students see programming and enterprise Java for the first time, words like *"marshalling"*, *"interoperability"*, *"WSDL"*, and *"serialization"* sound like alien languages. 

To help them understand and succeed, use **The 3-Step Anchor Method**:
1. **The Real-World Analogy (Mental Model)**: Connect the concept to everyday physical objects (e.g., postal letters, restaurant orders, luggage packing).
2. **The "Why Does It Exist?" (Problem First, Solution Second)**: Show what headache we had before this tool existed.
3. **The Minimal Working Code**: Strip away confusing enterprise boilerplate to show the core idea in action, with line-by-line commentary.

---

# TABLE OF CONTENTS
1. [Session 4: Jakarta Web Services with JAX-WS (SOAP)](#session-4-jakarta-web-services-with-jax-ws-soap)
   - What is a Web Service?
   - What is SOAP? (The Wax-Sealed Official Letter)
   - What is JAX-WS?
   - Types Supported by JAX-WS
   - Building a SOAP Web Service (`@WebService`, `@WebMethod`)
   - How Clients Consume SOAP & What Stubs Are
   - Web Service Interoperability (How Java talks to Python/C#)
2. [Session 5: Java Architecture for XML Binding (JAXB)](#session-5-java-architecture-for-xml-binding-jaxb)
   - What is XML & Why Does Java Need It?
   - What is JAXB? (The Translator & Suitcase Packer)
   - Marshalling vs. Unmarshalling
   - XML Schemas (XSD) - The Quality Control Inspector
   - Best Practices for XML/Schema Binding
   - Comprehensive Comparison: JAX-RS vs. JAX-WS vs. JAXB
3. [Session 6 & 7: Mini-Project - Developing REST Web Services with Jersey & Gradle](#session-6--7-mini-project-developing-rest-web-services-with-jersey--gradle)
   - What is REST? (The Waiter at a Restaurant)
   - What is Jersey?
   - What is Gradle? (The Automated Kitchen Manager)
   - Complete Architecture of the Mini-Project
   - Creating HTTP Methods in the Resource Class (`GET`, `POST`, `PUT`, `DELETE`)
   - Step-by-Step Execution & Testing Guide for Students

---

# SESSION 4: Jakarta Web Services with JAX-WS (SOAP)

## 1. What is a Web Service?
### Real-World Analogy:
Imagine you walk into a bank. You want to know the current exchange rate between US Dollars and Euros. You do not walk behind the bank teller's desk, open their computer filing cabinets, and search their private database. 
Instead:
- You speak to the **bank teller** across a counter.
- You ask: *"What is the USD to EUR rate today?"*
- The teller looks up their private system and answers: *"1 USD = 0.92 EUR"*.

> **Definition**: A **Web Service** is that "bank teller counter". It is a program running on a server that allows other programs (running on phones, websites, or other computers) to ask questions or submit tasks across the internet using standard messages.

---

## 2. What is SOAP? (Simple Object Access Protocol)
### Real-World Analogy:
Think of **SOAP** like sending a **formal, certified postal mail letter**:
- **Envelope (`<soap:Envelope>`)**: The physical paper envelope that protects what is inside.
- **Header (`<soap:Header>`)**: Optional stamps, certified tracking numbers, or security badges.
- **Body (`<soap:Body>`)**: The actual letter containing your message (e.g., "Transfer $50 from Account A to Account B").
- **Fault (`<soap:Fault>`)**: The return-to-sender sticker explaining why the delivery failed.

```xml
<?xml version="1.0"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
    <soap:Header>
        <!-- Security token or authentication -->
    </soap:Header>
    <soap:Body>
        <m:GetAccountBalance>
            <m:AccountNumber>123456</m:AccountNumber>
        </m:GetAccountBalance>
    </soap:Body>
</soap:Envelope>
```

**Key Characteristic**: SOAP is strict, highly structured, typed, and uses XML exclusively.

---

## 3. What is JAX-WS?
- **JAX-WS** stands for **Jakarta XML Web Services** (formerly Java API for XML Web Services).
- **The Problem It Solves**: In the early days, programmers had to manually write and parse hundreds of lines of raw XML strings. 
- **The Solution**: JAX-WS allows Java developers to write standard Java methods, and simply add annotations like `@WebService` and `@WebMethod`. JAX-WS automatically handles creating the XML, wrapping it into SOAP envelopes, and sending it over the network.

---

## 4. Types Supported by JAX-WS
When students write a method in a web service, what parameters can they accept, and what can they return?
1. **Java Primitives and Wrappers**: `int`, `double`, `boolean`, `String`, `Integer`, `Double`, etc.
2. **JavaBeans / POJOs**: Standard classes that have private variables, a no-argument default constructor, and `getXXX`/`setXXX` methods.
3. **Arrays and Collections**: `List<String>`, `Student[]`, `Map<String, Object>`.
4. **XML Source Types**: `javax.xml.transform.Source` for direct XML document processing.

---

## 5. Web Service Interoperability & WSDL
### What is Interoperability?
- Suppose your server is written in **Java**.
- But the client is an iPhone app written in **Swift**, or a data analytics dashboard written in **Python**, or a desktop app in **C# .NET**.
- **Interoperability** means they can all seamlessly communicate with each other because they both speak standard **XML** over **HTTP**.

### What is WSDL? (Web Services Description Language)
- Pronounced *"Wiz-dul"*.
- **Analogy**: WSDL is the **printed restaurant menu and contract**. It tells any client in the world:
  1. What services are available (e.g., `calculateInterest`, `getStudentDetails`).
  2. What inputs are required (e.g., an `int` studentId).
  3. What output will be returned (e.g., a `Student` object).
  4. The web address (URL) where the service lives.

---

## 6. What is a Stub? (Client Support for JAX-WS)
### Real-World Analogy:
If an American president visits Japan, the president cannot speak Japanese directly to the Japanese Prime Minister. They speak through an **interpreter (translator)** who stands right next to them.

- **A Stub** is that local translator on the client machine.
- To your client code, the stub looks and acts like a regular local Java object.
- When you call `stub.getStudent(101)`, the stub quietly:
  1. Converts `101` into a SOAP XML request.
  2. Sends it over the network to the server.
  3. Waits for the server's XML reply.
  4. Converts the XML reply back into a Java object.
  5. Returns it to your client program.

### Generating Stubs:
Java tools (like the classic `wsimport` command or modern Gradle/Maven plugins) read the server's WSDL file and automatically generate these client stub classes without you having to write them by hand:
```bash
wsimport -keep -s ./src http://localhost:8080/CalculatorService?wsdl
```

---

# SESSION 5: Java Architecture for XML Binding (JAXB)

## 1. What is XML?
**XML** (eXtensible Markup Language) looks like HTML with tags:
```xml
<student>
    <id>101</id>
    <name>Sarah Connor</name>
    <score>95.5</score>
</student>
```
Computers love XML because it is organized, self-describing, and human-readable.

---

## 2. What is JAXB and Why Do We Need It?
In Java, we have memory objects:
```java
Student s = new Student(101, "Sarah Connor", 95.5);
```
In files or network messages, we need XML text.
- If we had to manually convert Java objects to XML using string concatenation (`"<student><id>" + s.getId() + ...`), it would be tedious, slow, and full of bugs.
- **JAXB** is an automated engine that converts Java Objects $\longleftrightarrow$ XML documents.

---

## 3. The Core Operations: Marshalling & Unmarshalling
Teach students this mental model:
- **Marshalling (Object $\rightarrow$ XML)**: Think of **packing a suitcase**. You take your physical clothes (Java objects in memory) and pack them into a flat, travel-ready suitcase (XML file or network stream).
- **Unmarshalling (XML $\rightarrow$ Object)**: Think of **unpacking the suitcase**. You take the packed suitcase (XML document) and unpack it back into wearable clothes (live Java objects in RAM).

```
   [ Java Object in Memory ]
             |
             |   Marshalling (Pack)
             v
        <xml> ... </xml>
             ^
             |   Unmarshalling (Unpack)
             |
   [ Java Object in Memory ]
```

---

## 4. Key JAXB Annotations
Teach these four essential annotations:
1. **`@XmlRootElement(name = "student")`**: Tells JAXB that this class is the outer container / root tag of the XML.
2. **`@XmlElement(name = "full_name")`**: Maps a field or getter to a child tag `<full_name>`.
3. **`@XmlAttribute(name = "id")`**: Maps a field to an XML attribute inside the tag (e.g. `<student id="101">`).
4. **`@XmlTransient`**: Tells JAXB to **ignore** this field completely (do not put it in the XML, e.g., passwords or secret keys).

---

## 5. XML Schema (XSD) - The Quality Control Inspector
- **XSD** (XML Schema Definition) is a blueprint or grammar rulebook for XML.
- It defines:
  - Is `<id>` required or optional?
  - Must `<score>` be a number between 0 and 100?
  - Can `<student>` have more than one `<email>`?
- **Importance in JAXB**: JAXB can validate XML against an XSD during unmarshalling to ensure corrupted or invalid data is rejected before it harms your application.

---

## 6. Comprehensive Comparison: JAX-RS vs. JAX-WS vs. JAXB

| Feature | **JAX-WS** | **JAX-RS** | **JAXB** |
| :--- | :--- | :--- | :--- |
| **Full Form** | Jakarta XML Web Services | Jakarta RESTful Web Services | Jakarta XML Binding |
| **Primary Purpose** | Building **SOAP** web services | Building **REST** web services | Converting **Java $\longleftrightarrow$ XML** |
| **Protocol** | SOAP over HTTP, SMTP, JMS | HTTP (standard web verbs: GET, POST...) | Not a protocol (Data binding utility) |
| **Data Format** | Exclusively XML | JSON, XML, Plain Text, HTML | Exclusively XML |
| **Design Style** | RPC (Remote Procedure Call) - calling remote methods | Resource-Oriented - working with URLs & resources | Data Serialization |
| **Key Annotations** | `@WebService`, `@WebMethod` | `@Path`, `@GET`, `@POST`, `@Produces` | `@XmlRootElement`, `@XmlElement` |
| **Relationship** | Uses JAXB internally to handle XML! | Can use JAXB for XML and Jackson for JSON | Standalone tool used by other libraries |

### Advantages and Disadvantages:
- **JAX-WS (SOAP)**:
  - *Pros*: Extremely strict contract (WSDL), built-in WS-Security, great for banking, enterprise transactions, and telecommunications.
  - *Cons*: Heavyweight, verbose XML messages, harder for front-end web apps or mobile apps to consume.
- **JAX-RS (REST)**:
  - *Pros*: Lightweight, fast, supports friendly JSON, works directly with standard browser HTTP, standard for modern web and mobile apps.
  - *Cons*: Looser standards compared to SOAP; security and validation must be explicitly designed.
- **JAXB**:
  - *Pros*: Fast, zero manual string parsing, works seamlessly with Java classes.
  - *Cons*: Only handles XML (not JSON natively); learning curve for complex nested schemas.

---

# SESSIONS 6 & 7: Mini-Project (Jersey REST Web Services with Gradle)

## 1. What is REST?
### Real-World Analogy: The Restaurant Menu
In a restaurant, the **Waiter** is the HTTP protocol:
- **`GET`**: You ask the waiter: *"Please show me the menu or bring my order status."* (You are reading/fetching data. You do not change anything in the kitchen).
- **`POST`**: You give the waiter a new slip: *"Here is my order for a Margherita Pizza."* (You are creating new data in the kitchen).
- **`PUT`**: You tell the waiter: *"Change my pizza from Small to Large."* (You are updating existing data).
- **`DELETE`**: You tell the waiter: *"Cancel my dessert order."* (You are removing data).

---

## 2. What is Jersey?
- **JAX-RS** is just an official specification (a list of rules and interfaces defined by Jakarta EE).
- **Jersey** is the actual working engine (the "reference implementation") that implements those JAX-RS rules. It provides the servlet and HTTP server connectors to turn your Java code into a live REST API.

---

## 3. What is Gradle?
### Analogy: The Head Chef & Inventory Assistant
If you bake a cake, you could manually drive to 5 different supermarkets to find flour, sugar, vanilla extract, and frosting. 
Or, you can write a shopping list, and an assistant fetches the exact right ingredients and bakes it for you.
- **Gradle** is that automated assistant for Java developers.
- In a single file called `build.gradle`, you write:
  - What version of Java you want.
  - What external libraries (dependencies) you need (e.g. Jersey, Jackson).
- Gradle downloads them automatically, compiles your code, and runs your server with one command: `gradle run`.

---

## 4. Student Mini-Project Architecture: "Product Catalog Service"
Students will build a complete, runnable **Product Inventory REST API**:
- **Entity**: `Product` (ID, Name, Price, Category).
- **Storage**: In-memory repository (mock database) so students don't have to troubleshoot database connection strings on day one.
- **Controller/Resource**: `ProductResource` providing:
  - `GET /api/products` (Fetch all products)
  - `GET /api/products/{id}` (Fetch a single product by ID)
  - `POST /api/products` (Add a new product with JSON body)
  - `PUT /api/products/{id}` (Update an existing product)
  - `DELETE /api/products/{id}` (Delete a product)
- **Server**: Standalone lightweight embedded Grizzly HTTP server (launches in 1 second, zero external installation needed).
# Revised Session 1: teaching from zero

Use the Session 1 section of `website/index.html` as the primary student lesson. The supplied manual combines book chapters 1 (web services) and 2 (JPA) in teaching Session 1. Its teaching Sessions 2 and 3 are JAX-RS and JAX-WS; teaching Session 4 is practice for book Sessions 1–4. Some older folder and heading numbers in this package refer to a different grouping and still need review.

Before the two-hour core session, give first-time Java students time for JDK setup, printing, variables, if/else, return values and object creation. Verify that they can compile a small program and explain it. Do not count setup and all independent work as a realistic two-hour first encounter with Java.

Session 1 now defines vocabulary alongside its first examples and has section 1.13, a grouped term reference with meanings and examples. Use the relevant group as students encounter it rather than assigning the whole reference to memorise. Check words such as bytecode, scope, parameter/argument, resource/representation, namespace, provider, managed entity and commit by asking students to point to their own code or an observed HTTP exchange. The same word can have different meanings in Java, HTTP, XML and JPA; ask which context is being discussed. Four additional recall questions and the “My terms, my examples” exercise check these distinctions. Have students record their own explanations and remaining unfamiliar words in the learning log.

Teach with the repeated sequence in the HTML: predict, type, run, change one thing, recall without the model, then explain. Five reference programs in `session1-intro/beginner` build toward a minimal HTTP service. The earlier socket demonstration is optional TCP enrichment; explicitly distinguish it from HTTP.

The lesson covers JPA through table/object mappings and parameterised JPQL design. These are labelled conceptual; a provider and configured database are required for real persistence. Avoid presenting annotations or `new Student()` as proof that a record has been stored.

Assess the independent campus-service assignment using the HTML's 20-point rubric, test log and explanation requirements. Ask students to add one new route during review. Browser checklist ticks are self-reported and quizzes are learning aids, not evidence of coding mastery. Revisit the relevant section whenever a student cannot explain a branch or reproduce a small change independently.

---

