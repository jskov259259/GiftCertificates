package ru.clevertec.ecl.service;

import ru.clevertec.ecl.dto.TagDto;
import java.util.List;

public interface TagService {

    List<TagDto> findAll(Integer pageNo, Integer pageSize, String sortBy);

    TagDto findById(Long id);

    TagDto save(TagDto tagDto);

    TagDto update(TagDto tag);

    void deleteById(Long tagId);
}
