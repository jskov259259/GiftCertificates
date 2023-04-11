package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.model.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag dtoToTag(TagDto tagDto);

    TagDto tagToDto(Tag tag);
}
