package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.repository.TagDao;
import ru.clevertec.ecl.model.Tag;
import ru.clevertec.ecl.service.impl.TagServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static ru.clevertec.ecl.util.TestData.getTags;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagDao tagDao;

    @Test
    void checkFindAll() {
        doReturn(getTags())
                .when(tagDao).findAll();
        List<Tag> resultList = tagService.findAll();
        assertThat(resultList).hasSize(2);
    }

    @Test
    void checkCreateTag() {
        Tag tag = getTags().get(0);
        doReturn(tag.getId())
                .when(tagDao).create(tag);
        Long resultId = tagService.create(tag);
        verify(tagDao).create(any());
        assertThat(resultId).isEqualTo(1L);
    }

    @Test
    void checkUpdateTag() {
        Tag tag = getTags().get(0);
        doReturn(1)
                .when(tagDao).update(tag);
        Integer resultRows = tagService.update(tag);
        verify(tagDao).update(any());
        assertThat(resultRows).isEqualTo(1);
    }

    @Test
    void checkDeleteTag() {
        doReturn(1)
                .when(tagDao).delete(any());
        Integer resultRows = tagService.delete(1);
        verify(tagDao).delete(any());
        assertThat(resultRows).isEqualTo(1);
    }

}