package ru.clevertec.ecl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.TagDao;
import ru.clevertec.ecl.model.Tag;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> findAll() {

        return tagDao.findAll();
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
}
