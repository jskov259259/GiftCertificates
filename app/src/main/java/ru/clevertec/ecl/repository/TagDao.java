package ru.clevertec.ecl.repository;

import ru.clevertec.ecl.model.Tag;
import java.util.List;

public interface TagDao {

    List<Tag> findAll();

    Tag findById(Long id);

    Long create(Tag tag);

    Integer update(Tag tag);

    Integer delete(Integer tagId);

    Tag getTagByName(String name);

    boolean isTagExists(Tag tag);

    List<Tag> findAllByCertificateId(Long certificateId);
}
