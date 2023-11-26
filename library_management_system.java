//package library_management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean available;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

class Patron {
    private int patronId;
    private String name;

    public Patron(int patronId, String name) {
        this.patronId = patronId;
        this.name = name;
    }

    public int getPatronId() {
        return patronId;
    }

    public String getName() {
        return name;
    }
}

class Transaction {
    private Book book;
    private Patron patron;
    private Date borrowDate;
    private Date returnDate;

    public Transaction(Book book, Patron patron) {
        this.book = book;
        this.patron = patron;
        this.borrowDate = new Date();
    }

    public Book getBook() {
        return book;
    }

    public Patron getPatron() {
        return patron;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate() {
        this.returnDate = new Date();
    }
}

class Library {
    private List<Book> books;
    private List<Patron> patrons;
    private List<Transaction> transactions;

    public Library() {
        this.books = new ArrayList<>();
        this.patrons = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addPatron(Patron patron) {
        patrons.add(patron);
    }

    public void borrowBook(int bookId, int patronId) {
        Book book = findBook(bookId);
        Patron patron = findPatron(patronId);

        if (book != null && patron != null && book.isAvailable()) {
            book.setAvailable(false);
            transactions.add(new Transaction(book, patron));
            System.out.println(patron.getName() + " borrowed '" + book.getTitle() + "'.");
        } else {
            System.out.println("Book not available or invalid book/patron ID.");
        }
    }

    public void returnBook(int bookId) {
        Book book = findBook(bookId);

        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            for (Transaction transaction : transactions) {
                if (transaction.getBook().getBookId() == bookId && transaction.getReturnDate() == null) {
                    transaction.setReturnDate();
                    System.out.println("Book '" + book.getTitle() + "' returned by " + transaction.getPatron().getName());
                    return;
                }
            }
            System.out.println("No active transaction found for this book.");
        } else {
            System.out.println("Invalid book ID or book already available.");
        }
    }

    public void displayBooks() {
        System.out.println("Library Inventory:");
        for (Book book : books) {
            System.out.println("Book ID: " + book.getBookId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Available: " + book.isAvailable());
        }
        System.out.println("-------------------------");
    }

    private Book findBook(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                return book;
            }
        }
        return null;
    }

    private Patron findPatron(int patronId) {
        for (Patron patron : patrons) {
            if (patron.getPatronId() == patronId) {
                return patron;
            }
        }
        return null;
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        Book book1 = new Book(1, "The India Story", "Bimal Jalal");
        Book book2 = new Book(2, "Listen to Your Heart: The London Adventure", "Ruskin Bond");
        Book book3 = new Book(3, "Business of Sports: The Winning Formula for Success", "Vinit Karnik");

        Patron patron1 = new Patron(101, "Prajwal");
        Patron patron2 = new Patron(102, "Adithya");
        Patron patron3 = new Patron(103, "Varsha");
        

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        library.addPatron(patron1);
        library.addPatron(patron2);
        library.addPatron(patron3);


        library.displayBooks();

        library.borrowBook(1, 101);
        library.borrowBook(2, 102);
        library.borrowBook(3, 103);

        library.displayBooks();

        library.returnBook(1);
        library.returnBook(2);

        library.displayBooks();
    }
}


