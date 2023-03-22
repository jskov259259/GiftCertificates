package ru.clevertec.ecl.service;

import ru.clevertec.ecl.model.Tag;
import java.util.List;

public interface TagService {

    List<Tag> findAll();

    Long create(Tag tag);

    Integer update(Tag tag);

    Integer delete(Integer tagId);
}
