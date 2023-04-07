package ru.clevertec.ecl.service;

import ru.clevertec.ecl.model.Tag;
import java.util.List;
import java.util.Optional;

public interface TagService {

    List<Tag> findAll();

    Optional<Tag> findById(Long id);

    Long save(Tag tag);
//
//    Integer update(Tag tag);

    void deleteById(Integer tagId);
}
