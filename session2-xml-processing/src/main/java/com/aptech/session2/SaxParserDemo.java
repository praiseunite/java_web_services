package com.aptech.session2;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SESSION 2: SAX (Simple API for XML) Parser in Java.
 * 
 * For Absolute Beginners:
 * - What is SAX?
 *   Unlike DOM (which loads the whole book into memory), SAX is like listening to an audio book.
 *   The parser reads line by line from top to bottom. It DOES NOT store anything in RAM!
 *   Instead, whenever it bumps into an opening tag `<student>` or text content,
 *   it fires an "Event" to your code: "Hey, I just saw <student>!"
 * 
 * - When to use SAX?
 *   When the XML file is massive (e.g. 500 Megabytes or 5 Gigabytes), because SAX uses almost
 *   ZERO memory (RAM).
 * 
 * Key Handler Callbacks:
 * - startElement(): Called when an opening tag `<name>` begins.
 * - characters():   Called with the text inside the tag ("Amina Bello").
 * - endElement():   Called when a closing tag `</name>` ends.
 */
public class SaxParserDemo {

    public static void main(String[] args) {
        try {
            System.out.println("==================================================");
            System.out.println(" [SESSION 2] Java SAX Parser Demonstration");
            System.out.println("==================================================");

            File xmlFile = new File("students.xml");
            if (!xmlFile.exists()) {
                xmlFile = new File("session2-xml-processing/students.xml");
            }

            System.out.println("Streaming XML File: " + xmlFile.getAbsolutePath() + "\n");

            // Step 1: Create SAXParserFactory
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // Step 2: Create SAXParser
            SAXParser saxParser = factory.newSAXParser();

            // Step 3: Define the Event Handler that reacts as XML tags are read
            DefaultHandler handler = new DefaultHandler() {

                boolean bName = false;
                boolean bEmail = false;
                boolean bGpa = false;

                // Event 1: Triggered when an opening tag is encountered
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("student")) {
                        String id = attributes.getValue("id");
                        String course = attributes.getValue("course");
                        System.out.println("[EVENT: Start Student Tag] ID: " + id + ", Course: " + course);
                    } else if (qName.equalsIgnoreCase("name")) {
                        bName = true;
                    } else if (qName.equalsIgnoreCase("email")) {
                        bEmail = true;
                    } else if (qName.equalsIgnoreCase("gpa")) {
                        bGpa = true;
                    }
                }

                // Event 2: Triggered when text content inside tags is encountered
                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    String content = new String(ch, start, length).trim();
                    if (content.isEmpty()) return;

                    if (bName) {
                        System.out.println("   • Name:  " + content);
                        bName = false;
                    } else if (bEmail) {
                        System.out.println("   • Email: " + content);
                        bEmail = false;
                    } else if (bGpa) {
                        System.out.println("   • GPA:   " + content);
                        bGpa = false;
                    }
                }

                // Event 3: Triggered when a closing tag is encountered
                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("student")) {
                        System.out.println("[EVENT: End Student Tag]\n--------------------------------------------------");
                    }
                }
            };

            // Step 4: Stream and parse!
            saxParser.parse(xmlFile, handler);

            System.out.println("SUCCESS: Streamed entire XML document with near-zero memory footprint!");

        } catch (Exception e) {
            System.err.println("SAX Parsing Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
