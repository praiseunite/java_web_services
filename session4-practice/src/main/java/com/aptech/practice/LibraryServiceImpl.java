package com.aptech.practice;

import jakarta.jws.WebService;

@WebService(endpointInterface = "com.aptech.practice.LibraryService",
    targetNamespace = "urn:aptech:session4", serviceName = "AptechLibraryService",
    portName = "LibraryPort")
public class LibraryServiceImpl implements LibraryService {
    private final BookStore store;

    public LibraryServiceImpl(BookStore store) {
        this.store = store;
    }

    @Override
    public String getLibraryHours() {
        return "Mon-Fri 09:00-17:00";
    }

    @Override
    public String getBookTitle(long bookId) throws LibraryFault {
        if (bookId < 1) throw new LibraryFault("Book id must be positive.");
        Book book = store.find(bookId);
        if (book == null) throw new LibraryFault("Book not found.");
        return book.getTitle();
    }

    @Override
    public int calculatePrintCharge(int pages) throws LibraryFault {
        if (pages < 1 || pages > 20) throw new LibraryFault("Pages must be between 1 and 20.");
        return pages * 10;
    }
}
