package ru.clevertec.ecl.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.config.SpringTestDBConfig;
import ru.clevertec.ecl.model.Tag;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = SpringTestDBConfig.class)
@Transactional
@Rollback
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
class TagDaoJdbcTestIT {

    private TagDaoJdbc tagDao;

    @Autowired
    public TagDaoJdbcTestIT(TagDaoJdbc tagDao) {
        this.tagDao = tagDao;
    }

    @Test
    void checkTagDaoNotNull() {

        assertThat(tagDao).isNotNull();
    }

    @Test
    void checkFindAll() {

        List<Tag> tags = tagDao.findAll();
        assertThat(tags).hasSize(7);
    }

    @Test
    void checkFindById() {

        List<Tag> tags = tagDao.findAll();

        Tag tagSrc = tags.get(0);
        Tag tagDst = tagDao.findById(tagSrc.getId());
        assertThat(tagSrc).isEqualTo(tagDst);
    }

    @Test
    void checkUpdate() {

        List<Tag> tags = tagDao.findAll();

        Tag tagSrc = tags.get(0);
        tagSrc.setName(tagSrc.getName() + "_TEST");
        tagDao.update(tagSrc);

        Tag tagDst = tagDao.findById(tagSrc.getId());
        assertThat(tagSrc.getName()).isEqualTo(tagDst.getName());
    }

    @Test
    void checkDelete() {

        int tagSizeBefore = tagDao.findAll().size();

        tagDao.delete(7);
        int tagSizeAfter = tagDao.findAll().size();
        assertThat(tagSizeBefore).isEqualTo(tagSizeAfter + 1);
    }

    @Test
    void checkGetTagByName() {

        List<Tag> tags = tagDao.findAll();

        Tag tagSrc = tags.get(0);
        Tag tagDst = tagDao.getTagByName(tagSrc.getName());
        assertThat(tagSrc).isEqualTo(tagDst);
    }

    @Test
    void checkIsTagExistsReturnTrue() {

        Tag tag = tagDao.findAll().get(0);
        assertThat(tagDao.isTagExists(tag)).isTrue();
    }

    @Test
    void checkIsTagExistsReturnFalse() {

        Tag tag = new Tag(50L, "Non existent tag");
        assertThat(tagDao.isTagExists(tag)).isFalse();
    }

    @Test
    void checkFindAllByCertificateId() {

        List<Tag> tags = tagDao.findAllByCertificateId(3L);
        assertThat(tags.size()).isEqualTo(2);
    }
}