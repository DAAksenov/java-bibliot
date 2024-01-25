package ru.fa.books;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    @Query("select b from Book b " +
            "where (b.name = :name or :name is null) and " +
            "(b.publishing = :publishing or :publishing is null) and " +
            "(b.studentName = :student or :student is null)")
    Iterable<Book> filter(@Param("name") String name,
                          @Param("publishing") String publishing,
                          @Param("student") String studentName,
                          Sort sort);
}
