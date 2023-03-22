package ru.clevertec.ecl.dao;

import ru.clevertec.ecl.model.Tag;
import java.util.List;

public interface TagDao {

    List<Tag> findAll();

    Long create(Tag tag);

    Integer update(Tag tag);

    Integer delete(Integer tagId);
}
