package ru.clevertec.ecl.dao;

import ru.clevertec.ecl.model.Tag;
import java.util.List;
import java.util.Optional;

public interface TagDao {

    List<Tag> findAll();

    Long create(Tag tag);

    Integer update(Tag tag);

    Integer delete(Integer tagId);

    Tag getTagByName(String name);

    boolean isTagExists(Tag tag);

    List<Tag> findAllByCertificateId(Long certificateId);
}
