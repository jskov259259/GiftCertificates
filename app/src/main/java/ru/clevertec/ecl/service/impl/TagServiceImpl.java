package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.repository.TagDao;
import ru.clevertec.ecl.model.Tag;
import ru.clevertec.ecl.service.TagService;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public Tag findById(Long id) {
        return tagDao.findById(id);
    }

    @Override
    @Transactional
    public Long create(Tag tag) {
        return tagDao.create(tag);
    }

    @Override
    @Transactional
    public Integer update(Tag tag) {
        return tagDao.update(tag);
    }

    @Override
    @Transactional
    public void delete(Integer tagId) {
        tagDao.delete(tagId);
    }
}
