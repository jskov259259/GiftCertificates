package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.model.Tag;
import java.util.List;
import java.util.Optional;

public interface TagDao extends JpaRepository<Tag, Long> {

    List<Tag> findAll();

//
//    Long create(Tag tag);
//
//    Integer update(Tag tag);

    void deleteById(Integer tagId);

}
