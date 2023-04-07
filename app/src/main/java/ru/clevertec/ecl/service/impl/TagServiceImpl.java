package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.repository.TagDao;
import ru.clevertec.ecl.model.Tag;
import ru.clevertec.ecl.service.TagService;

import java.util.List;
import java.util.Optional;

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
    public Optional<Tag> findById(Long id) {
        return tagDao.findById(id);
    }

    @Override
    @Transactional
    public Long save(Tag tag) {
        return tagDao.save(tag).getId();
    }
//
//    @Override
//    @Transactional
//    public Integer update(Tag tag) {
//        return tagDao.update(tag);
//    }

    @Override
    @Transactional
    public void deleteById(Integer tagId) {
        tagDao.deleteById(tagId);
    }
}
