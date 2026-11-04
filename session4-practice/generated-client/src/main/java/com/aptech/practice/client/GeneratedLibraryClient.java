package com.aptech.practice.client;

import java.net.URI;
import com.aptech.practice.generated.AptechLibraryService;
import com.aptech.practice.generated.LibraryService;

public class GeneratedLibraryClient {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) throw new IllegalArgumentException("Supply the actual book id returned by REST or the console.");
        long bookId = Long.parseLong(args[0]);
        int port = Integer.getInteger("practice.soap.port", 8099);
        AptechLibraryService service = new AptechLibraryService(
            URI.create("http://127.0.0.1:" + port + "/library?wsdl").toURL());
        LibraryService proxy = service.getLibraryPort();
        System.out.println("Hours: " + proxy.getLibraryHours());
        System.out.println("Book " + bookId + ": " + proxy.getBookTitle(bookId));
        System.out.println("3 pages: " + proxy.calculatePrintCharge(3) + " NGN");
    }
}
