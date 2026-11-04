package com.aptech.session2;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * SESSION 2: DOM (Document Object Model) Parser in Java.
 * 
 * For Absolute Beginners:
 * - What is DOM?
 *   Think of DOM like loading an entire 500-page book into your brain all at once.
 *   The computer reads the ENTIRE XML file, builds a tree of nodes in RAM,
 *   and lets you navigate backwards, forwards, up to parents, and down to children.
 * 
 * - When to use DOM?
 *   When the XML file is small to medium (a few megabytes), and you need to edit,
 *   add, delete, or search nodes randomly.
 * 
 * Steps to Parse with DOM:
 * 1. Create DocumentBuilderFactory
 * 2. Create DocumentBuilder
 * 3. Parse file into a Document tree
 * 4. Normalize the XML structure
 * 5. Get elements by tag name (<student>) and iterate!
 */
public class DomParserDemo {

    public static void main(String[] args) {
        try {
            System.out.println("==================================================");
            System.out.println(" [SESSION 2] Java DOM Parser Demonstration");
            System.out.println("==================================================");

            File xmlFile = new File("students.xml");
            if (!xmlFile.exists()) {
                // Check if file is in parent or current working folder
                xmlFile = new File("session2-xml-processing/students.xml");
            }

            System.out.println("Loading XML File: " + xmlFile.getAbsolutePath() + "\n");

            // Step 1: Create DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // Recognize XML namespaces

            // Step 2: Create DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Step 3: Parse the XML file into a Document Object (the in-memory tree)
            Document doc = builder.parse(xmlFile);

            // Step 4: Normalize XML structure (combines adjacent text nodes, removes empty spaces)
            doc.getDocumentElement().normalize();

            // Print Root Element Name
            System.out.println("Root Element Name: <" + doc.getDocumentElement().getNodeName() + ">");
            System.out.println("--------------------------------------------------");

            // Step 5: Extract all <student> elements
            NodeList studentList = doc.getElementsByTagName("student");
            System.out.println("Total Students Found: " + studentList.getLength() + "\n");

            // Step 6: Loop through each student node
            for (int i = 0; i < studentList.getLength(); i++) {
                Node node = studentList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element studentElement = (Element) node;

                    // Extract Attributes
                    String id = studentElement.getAttribute("id");
                    String course = studentElement.getAttribute("course");

                    // Extract Child Elements text content
                    String name = studentElement.getElementsByTagName("name").item(0).getTextContent();
                    String email = studentElement.getElementsByTagName("email").item(0).getTextContent();
                    String gpa = studentElement.getElementsByTagName("gpa").item(0).getTextContent();

                    System.out.println("Student #" + (i + 1) + ":");
                    System.out.println("  • ID:     " + id);
                    System.out.println("  • Course: " + course);
                    System.out.println("  • Name:   " + name);
                    System.out.println("  • Email:  " + email);
                    System.out.println("  • GPA:    " + gpa);
                    System.out.println("--------------------------------------------------");
                }
            }

            System.out.println("SUCCESS: Entire XML document parsed into memory and read via DOM!");

        } catch (Exception e) {
            System.err.println("DOM Parsing Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
