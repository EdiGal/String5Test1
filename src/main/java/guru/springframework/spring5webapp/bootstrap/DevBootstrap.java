package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.model.Author;
import guru.springframework.spring5webapp.model.Book;
import guru.springframework.spring5webapp.model.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent>{

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    public DevBootstrap(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    private void initData() {

        Publisher yediot = new Publisher();
        yediot.setName("Yediot Aharonot");

        publisherRepository.save(yediot);

        Author edi = new Author("Eduard", "Galstyan");
        Book theBook = new Book("The Book", "123", yediot);
        edi.getBooks().add(theBook);
        theBook.getAuthors().add(edi);
        
        authorRepository.save(edi);
        bookRepository.save(theBook);

        Author rupi = new Author("Rupi", "Kaur");
        Book milkAndHoney = new Book("Milk and Honey", "144947425X", yediot);
        rupi.getBooks().add(milkAndHoney);

        authorRepository.save(rupi);
        bookRepository.save(milkAndHoney);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }
}
