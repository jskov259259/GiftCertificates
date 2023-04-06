package ru.clevertec.ecl.service;

import ru.clevertec.ecl.model.Tag;
import java.util.List;

public interface TagService {

    List<Tag> findAll();

    Tag findById(Long id);

    Long create(Tag tag);

    Integer update(Tag tag);

    void delete(Integer tagId);
}
