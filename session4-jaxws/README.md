# Legacy SOAP enrichment: Jakarta Web Services with JAX-WS
## Book chapter 4 supplementary examples

This folder retains its original name. In the August 2024 presenter's manual, JAX-WS belongs to teaching Session 3; teaching Session 4 is practice for book chapters 1–4. Use [the beginner Session 3 lesson](../website/index.html#s3-roadmap) for the main SOAP sequence and [Session 4 practice](../session4-practice/README.md) for consolidation. The older examples below are supplementary and have not been revalidated as part of the Session 4 practice build.

---

## 1. Objectives of this Session
By the end of this session, students will be able to:
1. **Describe JAX-WS** (Jakarta XML Web Services) and its architectural role.
2. **Explain SOAP Web Services** using JAX-WS.
3. **Identify Data Types** supported by JAX-WS.
4. **Describe Client Support** for JAX-WS (Service Endpoint Interface, Port, Stubs).
5. **Explain Web Service Interoperability** (cross-platform, language-independent communication).
6. **Explain Generating Stubs** using tools like `wsimport` or dynamic proxies.

---

## 2. Key Terms Explained for First-Time Programmers

| Term | What it Means in Plain English | Real-World Analogy |
| :--- | :--- | :--- |
| **Web Service** | A program that answers questions or performs tasks for other programs over a network. | A bank teller desk or a customer service counter. |
| **SOAP** | *Simple Object Access Protocol*. A strict, standard way of packaging requests in XML format. | A formal registered letter inside an official wax-sealed envelope. |
| **WSDL** | *Web Services Description Language*. An XML document that describes every available method, parameter, and network address. | The restaurant menu and price list pinned outside the door. |
| **JAX-WS** | A standard Java library that automatically turns Java classes into SOAP Web Services and vice-versa. | An automatic translator and letter-mailer for Java. |
| **SEI** | *Service Endpoint Interface*. A Java interface with `@WebService` defining the service methods. | The signed agreement / contract between customer and service provider. |
| **SIB** | *Service Implementation Bean*. The Java class that contains the real business logic code. | The chef in the kitchen executing the food order. |
| **Stub (Proxy)** | A local Java object on the client machine that pretends to be the remote server. | An ambassador or phone representative who takes your message and calls headquarters. |
| **Interoperability** | The ability of two different systems (e.g. Java and Python) to communicate without care for what language was used. | Two people speaking English even though one was born in France and one in Japan. |

---

## 3. Supported Data Types in JAX-WS
Students often ask: *"Can I send any Java object through a web service?"*
Here is what JAX-WS natively supports:
1. **Standard Java Primitives and Wrappers**:
   - `int`, `long`, `float`, `double`, `boolean`, `byte`, `short`, `char`
   - `Integer`, `Long`, `Float`, `Double`, `Boolean`, `String`
2. **Standard Java Date/Time**:
   - `java.util.Date`, `java.util.Calendar`, `javax.xml.datatype.XMLGregorianCalendar`
3. **Standard JavaBeans (POJOs)**:
   - Any class with private fields, a default no-argument constructor `public MyClass() {}`, and standard getters/setters (`getId()`, `setId()`).
4. **Arrays and Collections**:
   - `String[]`, `int[]`, `List<Product>`, `Set<String>`.
5. **XML Data Sources**:
   - `javax.xml.transform.Source` for passing raw XML trees directly.

---

## 4. Code Walkthrough: How the Example Works

### Step 1: The Interface (`CalculatorService.java`)
```java
@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface CalculatorService {
    @WebMethod
    int add(int num1, int num2);
}
```
- `@WebService` tells the compiler: "This interface is not just an ordinary Java interface; it will be published to the web."
- `@SOAPBinding(style = Style.DOCUMENT)` means messages will be structured as clean XML documents.
- `@WebMethod` exposes that specific method as an operation in the WSDL.

### Step 2: The Implementation (`CalculatorServiceImpl.java`)
```java
@WebService(endpointInterface = "com.aptech.jaxws.CalculatorService")
public class CalculatorServiceImpl implements CalculatorService {
    public int add(int num1, int num2) {
        return num1 + num2;
    }
}
```
- Implements the interface. The server code runs here.

### Step 3: Publishing the Service (`ServerPublisher.java`)
```java
Endpoint.publish("http://localhost:8080/calculator", new CalculatorServiceImpl());
```
- When you run this file, Java starts a mini-web server in memory!
- If you open a browser at `http://localhost:8080/calculator?wsdl`, you will see the generated XML contract describing the service!

### Step 4: The Client (`SoapClientExample.java`)
```java
URL wsdlUrl = URI.create("http://localhost:8080/calculator?wsdl").toURL();
QName qname = new QName("http://jaxws.aptech.com/", "CalculatorServiceImplService");
Service service = Service.create(wsdlUrl, qname);
CalculatorService calculator = service.getPort(CalculatorService.class);

// Remote method invocation:
int sum = calculator.add(45, 15);
```
- The client never touches raw XML! JAX-WS builds the SOAP envelope, sends it via HTTP POST, and extracts the answer seamlessly.

---

## 5. What are Stubs and How Are They Generated?
In traditional enterprise environments, a client might not even have the Java source files of the server! 
All the client has is the URL to the **WSDL**.

### The Stub Generation Process:
1. The developer runs the stub-generation tool:
   ```bash
   wsimport -keep -s ./src http://localhost:8080/calculator?wsdl
   ```
2. The tool reads the WSDL XML, inspects every operation, and **automatically generates**:
   - The Java Interface
   - The Java Parameter Classes
   - The Service locator class
3. The client developer imports those generated classes and calls them directly.

---

## 6. Web Service Interoperability in Practice
Because SOAP uses standard XML over HTTP:
- A **Java** server can be called by a **Python** script using the `zeep` library:
  ```python
  from zeep import Client
  client = Client('http://localhost:8080/calculator?wsdl')
  print(client.service.add(10, 20))  # Output: 30
  ```
- Or from a **C# .NET** application using Visual Studio's "Add Connected Service / WSDL Reference".
- Neither side needs to know or care what programming language the other side is written in!
