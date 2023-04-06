package ru.clevertec.ecl.repository;

import ru.clevertec.ecl.model.Tag;
import java.util.List;

public interface TagDao {

    List<Tag> findAll();

    Tag findById(Long id);

    Long create(Tag tag);

    Integer update(Tag tag);

    void delete(Integer tagId);

    Tag getTagByName(String name);

}
