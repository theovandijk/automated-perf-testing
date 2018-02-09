package nl.theovandijk.backendservice.persistence.books;

import nl.theovandijk.backendservice.representations.books.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Long> {
}
