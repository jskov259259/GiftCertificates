package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.exceptions.TagNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagDao;
import ru.clevertec.ecl.model.Tag;
import ru.clevertec.ecl.service.TagService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final TagMapper tagMapper;

    @Override
    public List<TagDto> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Tag> pagedResult = tagDao.findAll(paging);

        return pagedResult.getContent().stream()
                .map(tagMapper::tagToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findById(Long id) {
        Tag tag = tagDao.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id));
        return tagMapper.tagToDto(tag);
    }

    @Override
    @Transactional
    public TagDto save(TagDto tagDto) {
        Tag tag = tagMapper.dtoToTag(tagDto);
        Tag createdTag = tagDao.save(tag);
        return tagMapper.tagToDto(createdTag);
    }

    @Override
    @Transactional
    public TagDto update(TagDto tagDto) {
        Tag currentTag = tagDao.findById(tagDto.getId())
                .orElseThrow(() -> new TagNotFoundException(tagDto.getId()));

        Tag newTag = tagMapper.dtoToTag(tagDto);
        currentTag.setName(newTag.getName());
        Tag updatedTag = tagDao.save(currentTag);
        return tagMapper.tagToDto(updatedTag);
    }

    @Override
    @Transactional
    public void deleteById(Long tagId) {
        tagDao.deleteById(tagId);
    }
}
