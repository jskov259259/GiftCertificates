package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dao.TagDao;
import ru.clevertec.ecl.model.Tag;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagDao tagDao;

    @Test
    void checkFindAll() {

        doReturn(getTags()).when(tagDao).findAll();
        List<Tag> resultList = tagService.findAll();
        assertThat(resultList).hasSize(3);
    }

    @Test
    void checkCreateTag() {

        Tag tag = getTags().get(0);
        doReturn(tag.getId()).when(tagDao).create(tag);
        Long resultId = tagService.create(tag);
        verify(tagDao).create(any());
        assertThat(resultId).isEqualTo(1L);
    }

    @Test
    void checkUpdateTag() {

        Tag tag = getTags().get(0);
        doReturn(1).when(tagDao).update(tag);
        Integer resultRows = tagService.update(tag);
        verify(tagDao).update(any());
        assertThat(resultRows).isEqualTo(1);
    }

    @Test
    void checkDeleteTag() {

        doReturn(1).when(tagDao).delete(any());
        Integer resultRows = tagService.delete(1);
        verify(tagDao).delete(any());
        assertThat(resultRows).isEqualTo(1);
    }

    private List<Tag> getTags() {

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "Tag1"));
        tags.add(new Tag(2L, "Tag2"));
        tags.add(new Tag(3L, "Tag3"));
        return tags;
    }

}