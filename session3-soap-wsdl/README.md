# Session 3: Jakarta XML Web Services (JAX-WS)

Start with [the HTML lesson](../website/index.html#session3). It follows the August 2024 presenter's manual: teaching Session 3 covers JAX-WS from book Session 4, with a JAX-RS recap. The lesson includes a Java bridge, a three-file first service, SOAP/WSDL/schema explanations, supported types, faults, raw and typed clients, stub generation, interoperability, independent challenges, a library assignment, quizzes and a downloadable learning log.

Use JDK 17+ and Maven 3.9.x. The first build needs internet unless dependencies/plugins are cached. Metro 4.0.3 implements Jakarta XML Web Services; SOAP APIs/tools are external to modern JDKs. The Maven build targets Java 17 and packages dependencies in the JAR. Gradle is an alternative existing configuration; the beginner lesson uses Maven consistently.

## First lab: one greeting operation

Read and type the three files in `src/main/java/com/aptech/session3/beginner`. Terminal A:

```powershell
Set-Location "C:\Projects\Aptech\creating service\session3-soap-wsdl"
mvn package
java -cp target/session3-soap-wsdl-1.0.jar com.aptech.session3.beginner.GreetingPublisher
```

Keep A open. Browse `http://127.0.0.1:8095/beginner?wsdl`. Terminal B, also inside session3-soap-wsdl:

```powershell
curl.exe -i -X POST http://127.0.0.1:8095/beginner -H "@soap-headers.txt" --data-binary "@beginner-hello.xml"
```

Expected: HTTP 200 with `Hello, Amina!` in the `message` element. The beginner namespace is `urn:aptech:session3:beginner`. Changing XML request values needs no rebuild; changing service Java requires save, build and restart. Stop with Ctrl+C in A before starting the larger service on the same port.

## Larger course service

Terminal A:

```powershell
java -jar target/session3-soap-wsdl-1.0.jar
```

WSDL: `http://127.0.0.1:8095/greeting?wsdl`; namespace `urn:aptech:session3`; service `AptechGreetingService`; port `AptechGreetingPort`. Follow the WSDL's imported schema URL to inspect the XSD. This is SOAP 1.1, document/literal/wrapped, with empty SOAPAction. The header file contains literal empty-action quotes so PowerShell/curl preserves them.

In B, inside session3-soap-wsdl:

```powershell
curl.exe -i -X POST http://127.0.0.1:8095/greeting -H "@soap-headers.txt" --data-binary "@say-hello.xml"
curl.exe -i -X POST http://127.0.0.1:8095/greeting -H "@soap-headers.txt" --data-binary "@calculate-fees.xml"
curl.exe -i -X POST http://127.0.0.1:8095/greeting -H "@soap-headers.txt" --data-binary "@course-info.xml"
curl.exe -i -X POST http://127.0.0.1:8095/greeting -H "@soap-headers.txt" --data-binary "@invalid-fees.xml"
java -cp target/session3-soap-wsdl-1.0.jar com.aptech.session3.SoapRawRequestSender calculate-fees.xml
java -cp target/session3-soap-wsdl-1.0.jar com.aptech.session3.SoapClientExample
```

| Call | Result |
| --- | --- |
| sayHello(Amina) | 200, message `Hello, Amina! Welcome to Aptech Jakarta Web Services.` |
| calculateCourseFees(ADSE, 2) | 200, totalFees 1700 |
| getCourseInfo(CPISM) | 200, course properties CPISM / 500 / USD |
| calculateCourseFees(ADSE, 0) | 500, declared SOAP Fault with reason `Semesters must be between 1 and 8.` |

Course rates are illustrative whole USD amounts: ADSE 850, DISM 650, CPISM 500 per semester. They are not published Aptech prices. Counts must be 1–8; unknown courses produce CourseFault. Course input is trimmed and normalised. Null/blank greeting names use Student; prepared names longer than 60 characters produce CourseFault. These values are computed in memory; no database or payment is involved.

The typed shared-interface client prints the greeting, ADSE/2 total, CPISM data and a handled zero-semester fault. Its compatible Java interface/data types are available on its classpath. The raw sender prints HTTP status and XML rather than mapping faults to Java exceptions. Malformed XML/unknown operations can produce runtime faults with different detail from CourseFault.

## Generate an independent client from WSDL

Keep full /greeting A running. In B, from session3-soap-wsdl:

```powershell
Set-Location generated-client
mvn package
Get-ChildItem target/generated-sources/wsimport/com/aptech/session3/generated
java -jar target/session3-generated-client-1.0.jar
```

The Metro Maven plugin runs wsimport, reads live WSDL/XSD and generates client-side Java types. This project does not depend on provider source. The handwritten `GeneratedGreetingClient` uses the generated `AptechGreetingService`, port interface and CourseInfo. Expected output: greeting, `ADSE, 2 semesters: 1700 USD`, `CPISM: 500 USD per semester`.

Do not edit generated files under target. If the contract changes, rebuild/restart the provider and run `mvn clean package` in generated-client. The generator and its dependencies need their own initial download. Return B to the server folder with `Set-Location ..` before raw-file commands.

If an instructor selects another available port, quote Java system properties in PowerShell:

```powershell
# Server folder, terminal A:
java "-Dsoap.port=18095" -jar target/session3-soap-wsdl-1.0.jar
# Server folder, terminal B:
java "-Dsoap.port=18095" -cp target/session3-soap-wsdl-1.0.jar com.aptech.session3.SoapRawRequestSender say-hello.xml
# generated-client folder, terminal B:
mvn "-Dsoap.wsdl=http://127.0.0.1:18095/greeting?wsdl" clean package
java "-Dsoap.port=18095" -jar target/session3-generated-client-1.0.jar
```

The browser trace/simulator are teaching aids that compute local examples; they do not contact this service. Use actual requests/clients as evidence. The assignment intentionally has no completed solution. Student assessment requires source, boundary tests and an independent explanation, not only quiz answers or ticked milestones.
