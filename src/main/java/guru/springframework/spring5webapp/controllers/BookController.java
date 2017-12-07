package guru.springframework.spring5webapp.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import guru.springframework.spring5webapp.model.Author;
import guru.springframework.spring5webapp.model.Book;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import jdk.nashorn.internal.parser.JSONParser;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class BookController {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/books")
    public String getBooks(Model model){
        model.addAttribute("books", bookRepository.findAll());
        return "books";
    }

    @GetMapping("/books/{id}")
    public String getBook(@PathVariable("id") Long id, Model model){
        Optional<Book> book = bookRepository.findById(id);

        if(book.isPresent()) {
            Book foundBook = book.get();
            model.addAttribute("book", foundBook);
            return "book";
        }
        return "bookNotFound";
    }

    @GetMapping("/addbook")
    public String getAddBookPage(Model model){
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "addbook";
    }

    @PostMapping(path = "/book")
    public ResponseEntity addBook(@RequestBody Book book){
        bookRepository.save(book);
        return new ResponseEntity("response from the server!", HttpStatus.OK);
    }

}
