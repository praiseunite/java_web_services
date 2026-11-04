# SESSIONS 6 & 7: MINI-PROJECT LAB MANUAL
## Developing RESTful Web Services Using Jersey & Gradle
### "Product Catalog Management API"

---

> For the revised **teaching Session 6**, start with [the beginner Jersey/Gradle lesson](../website/index.html#s6-roadmap) and [its wrapper project](../session6-jersey-gradle/README.md). For revised teaching Session 7, use [the CRUD lesson](../website/index.html#s7-roadmap) and [its project](../session7-jersey-crud/README.md). This older combined CRUD reference retains separate limitations.
> Its storage resets on restart, validation is incomplete, and XML behaviour needs provider review. Use JSON for the examples below.

## 1. Project Overview & Architecture
In this mini-project, students build a illustrative **RESTful Web Service** using:
- **Language**: Java 17+
- **Framework**: **Jersey** (The official Jakarta RESTful Web Services / JAX-RS engine)
- **Build Tool**: **Gradle** (Automates library downloads, compilation, and execution)
- **Server**: **Grizzly HTTP Server** (Embedded web server; runs inside the Java process)
- **Data Format**: **JSON** (JavaScript Object Notation) and **XML**

---

## 2. Understanding the Project Structure
Teach students this folder structure so they never feel lost:

```
session6-7-jersey-mini-project/
│
├── build.gradle            <-- "The Recipe": Lists our libraries (Jersey, Jackson)
├── settings.gradle         <-- Project name definition
│
└── src/
    └── main/
        └── java/
            └── com/
                └── aptech/
                    └── webservice/
                        ├── Main.java              <-- Starts the embedded web server
                        │
                        ├── model/
                        │   └── Product.java       <-- The Data (id, name, price, category)
                        │
                        ├── dao/
                        │   └── ProductDatabase.java <-- The in-memory storage (mock database)
                        │
                        └── resource/
                            └── ProductResource.java <-- The Controller: Handles GET, POST, PUT, DELETE
```

---

## 3. How to Run the Project

### Method A: Using Gradle from Command Line / Terminal
Open your terminal or PowerShell inside the `session6-7-jersey-mini-project` directory and type:
```powershell
gradle run
```
This older folder does not ship a wrapper; the command above requires a compatible globally installed Gradle. The new Session 6 project includes a pinned wrapper and verified setup instructions.

### Method B: From any Java IDE (IntelliJ IDEA / Eclipse / VS Code / NetBeans)
1. Open the folder `session6-7-jersey-mini-project` in your IDE.
2. Allow the IDE to import the Gradle project (initial library downloads require internet access and take a connection-dependent time).
3. Right-click on **`Main.java`** and select **Run 'Main.main()'**.

You will see this output in your console:
```text
==================================================================
   APTECH JERSEY RESTful WEB SERVICES SERVER STARTED!            
==================================================================
Base API Endpoint: http://localhost:8080/api/products
Test in your browser right now by clicking or visiting:
   -> http://localhost:8080/api/products
   -> http://localhost:8080/api/products/1
   -> http://localhost:8080/api/products?category=Accessories
==================================================================
Press Enter to stop the server...
```

---

## 4. Complete Step-by-Step Testing Guide

### LAB EXERCISE 1: Test HTTP GET (Fetch All Products)
- **What it does**: Requests the entire list of products from the server.
- **Browser**: Simply open your browser and visit:
  ```text
  http://localhost:8080/api/products
  ```
- **Terminal (cURL / PowerShell)**:
  ```powershell
  curl.exe http://localhost:8080/api/products
  ```
- **Expected JSON Response**:
  ```json
  [
    {
      "id": 1,
      "name": "ThinkPad T14 Laptop",
      "price": 1199.99,
      "category": "Computers"
    },
    {
      "id": 2,
      "name": "Wireless Ergonomic Mouse",
      "price": 49.5,
      "category": "Accessories"
    },
    {
      "id": 3,
      "name": "Mechanical Gaming Keyboard",
      "price": 89.0,
      "category": "Accessories"
    }
  ]
  ```

---

### LAB EXERCISE 2: Test HTTP GET with Path Parameter (Fetch Single Product)
- **What it does**: Uses `@PathParam("id")` to get only product with ID `1`.
- **Browser URL**:
  ```text
  http://localhost:8080/api/products/1
  ```
- **Terminal (cURL)**:
  ```powershell
  curl.exe http://localhost:8080/api/products/1
  ```
- **Expected JSON Response**:
  ```json
  {
    "id": 1,
    "name": "ThinkPad T14 Laptop",
    "price": 1199.99,
    "category": "Computers"
  }
  ```

- **Testing 404 Not Found**: Visit `http://localhost:8080/api/products/999`
  - Response Code: `404 Not Found`
  - Body: `{"error": "Product with ID 999 not found."}`

---

### LAB EXERCISE 3: Test HTTP GET with Query Parameter (Filter)
- **What it does**: Uses `@QueryParam("category")` to filter results.
- **Browser URL**:
  ```text
  http://localhost:8080/api/products?category=Accessories
  ```
- **Expected JSON Response**: Only the Mouse and Keyboard appear!

---

### LAB EXERCISE 4: Test HTTP POST (Create a New Product)
- **What it does**: Sends a JSON object in the body to create a brand-new product.
- **Using cURL / PowerShell**:
  ```powershell
  curl.exe -i -X POST http://localhost:8080/api/products -H "Content-Type: application/json" --data-binary "@new-product.json"
  ```
  Save the JSON body shown in the Postman steps as `new-product.json` in your current terminal folder first.
- **Using Postman**:
  1. Method: select **POST**
  2. URL: `http://localhost:8080/api/products`
  3. Go to the **Body** tab -> select **raw** -> choose **JSON** from dropdown.
  4. Paste:
     ```json
     {
       "name": "Ultra HD 4K Monitor",
       "price": 349.99,
       "category": "Monitors"
     }
     ```
  5. Click **Send**.
- **Expected Response**: Status `201 Created`
  ```json
  {
    "id": 4,
    "name": "Ultra HD 4K Monitor",
    "price": 349.99,
    "category": "Monitors"
  }
  ```

---

### LAB EXERCISE 5: Test HTTP PUT (Update an Existing Product)
- **What it does**: Modifies the price or details of Product #2.
- **Using cURL / PowerShell**:
  ```powershell
  curl.exe -i -X PUT http://localhost:8080/api/products/2 -H "Content-Type: application/json" --data-binary "@updated-product.json"
  ```
  Save the JSON body shown in the Postman steps as `updated-product.json` in your current terminal folder first.
- **Using Postman**:
  1. Method: **PUT**
  2. URL: `http://localhost:8080/api/products/2`
  3. Body (raw JSON):
     ```json
     {
       "name": "Wireless Ergonomic Mouse V2",
       "price": 59.99,
       "category": "Accessories"
     }
     ```
  4. Click **Send**.
- **Expected Response**: Status `200 OK` with updated values.

---

### LAB EXERCISE 6: Test HTTP DELETE (Remove a Product)
- **What it does**: Deletes Product #3.
- **Using cURL / PowerShell**:
  ```powershell
  curl.exe -i -X DELETE http://localhost:8080/api/products/3
  ```
- **Using Postman**:
  1. Method: **DELETE**
  2. URL: `http://localhost:8080/api/products/3`
  3. Click **Send**.
- **Expected Response**: Status `200 OK`
  ```json
  {
    "message": "Product with ID 3 was successfully deleted."
  }
  ```
- **Verification**: If you now visit `http://localhost:8080/api/products`, item #3 is gone!

---

## 5. Common Student Errors & Solutions (Instructor FAQ)

### 1. Error: `Address already in use: bind` (Port 8080 Conflict)
- **Cause**: Another program (like Tomcat, Jenkins, Oracle, or another running instance of Main) is already occupying port 8080.
- **Fix**: Open `Main.java` and change the port:
  ```java
  public static final String BASE_URI = "http://localhost:9090/api/";
  ```

### 2. Error: `415 Unsupported Media Type`
- **Cause**: The client sent a POST or PUT request without telling the server what format the body is in.
- **Fix**: Add the HTTP Header: `Content-Type: application/json`.

### 3. Error: Empty or Null fields when creating a Product
- **Cause**: Missing default no-arg constructor `public Product() {}` or property names in JSON do not match getters/setters in `Product.java`.
- **Fix**: Ensure `Product.java` has exact matching getter/setter names and a public default constructor.
