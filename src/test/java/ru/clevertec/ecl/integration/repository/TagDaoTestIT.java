package ru.clevertec.ecl.integration.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.clevertec.ecl.model.Tag;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.clevertec.ecl.util.Constants.TEST_ID;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_NO;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_SIZE;
import static ru.clevertec.ecl.util.Constants.TEST_SORT_BY;
import static ru.clevertec.ecl.util.TestData.getTag;

@DataJpaTest
class TagDaoTestIT {

    @Autowired
    private TagDao tagDao;

    @Test
    void checkFindAll() {
        Page<Tag> pagedResult = tagDao.findAll(PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE,
                Sort.by(TEST_SORT_BY)));
        assertThat(pagedResult.getContent().size()).isEqualTo(7);
    }

    @Test
    void checkFindById() {
        Optional<Tag> tagData = tagDao.findById(TEST_ID);
        assertThat(tagData.get().getId()).isEqualTo(TEST_ID);
        assertThat(tagData.get().getName()).isEqualTo("massage");
    }

    @Test
    void checkSave() {
        Tag tag = getTag();
        Tag createdTag = tagDao.save(tag);
        assertThat(createdTag.getName()).isEqualTo("food");
    }

    @Test
    void checkUpdate() {
        Tag tag = getTag();
        tagDao.save(tag);

        tag.setName("New Tag");
        Tag updatedTag = tagDao.save(tag);
        assertThat(updatedTag.getName()).isEqualTo("New Tag");
    }

    @Test
    void deleteById() {
        Integer sizeBefore = tagDao.findAll(PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE,
                Sort.by(TEST_SORT_BY))).getContent().size();

        tagDao.deleteById(7L);

        Integer sizeAfter = tagDao.findAll(PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE,
                Sort.by(TEST_SORT_BY))).getContent().size();
        assertThat(sizeAfter).isLessThan(sizeBefore);
    }
}