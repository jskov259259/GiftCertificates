package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.exceptions.TagNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.model.Tag;
import ru.clevertec.ecl.repository.TagDao;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.clevertec.ecl.util.Constants.TEST_ID;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_NO;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_SIZE;
import static ru.clevertec.ecl.util.Constants.TEST_SORT_BY;
import static ru.clevertec.ecl.util.TestData.getTag;
import static ru.clevertec.ecl.util.TestData.getTagDto;
import static ru.clevertec.ecl.util.TestData.getTags;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDao tagDao;

    @Mock
    private TagMapper tagMapper;

    @Captor
    ArgumentCaptor<Tag> tagCaptor;

    @Test
    void checkFindAll() {
        Tag tag = getTag();
        TagDto tagDto = getTagDto();

        doReturn(new PageImpl<>(getTags()))
                .when(tagDao).findAll(PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE, Sort.by(TEST_SORT_BY)));
        doReturn(tagDto)
                .when(tagMapper).tagToDto(tag);

        List<TagDto> tags = tagService.findAll(TEST_PAGE_NO, TEST_PAGE_SIZE, TEST_SORT_BY);

        verify(tagDao).findAll(PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE, Sort.by(TEST_SORT_BY)));
        verify(tagMapper, times(2)).tagToDto(any());

        assertThat(tags.get(0)).isEqualTo(tagDto);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L})
    void checkFindById(Long id) {
        Tag tag = getTag();
        TagDto tagDto = getTagDto();

        doReturn(Optional.of(tag))
                .when(tagDao).findById(id);
        doReturn(tagDto)
                .when(tagMapper).tagToDto(tag);

        TagDto result = tagService.findById(id);

        verify(tagDao).findById(anyLong());
        verify(tagMapper).tagToDto(any());
        assertThat(result).isEqualTo(tagDto);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L})
    void checkFindByIdShouldThrowTagNotFoundException(Long id) {
        doThrow(TagNotFoundException.class)
                .when(tagDao).findById(anyLong());
        assertThrows(TagNotFoundException.class, () -> tagService.findById(id));
        verify(tagDao).findById(anyLong());
    }

    @Test
    void checkSave() {
        Tag tag = getTag();
        TagDto tagDto = getTagDto();

        doReturn(tag)
                .when(tagDao).save(tagCaptor.capture());
        doReturn(tag)
                .when(tagMapper).dtoToTag(tagDto);
        doReturn(tagDto)
                .when(tagMapper).tagToDto(tag);

        TagDto result = tagService.save(tagDto);
        verify(tagDao).save(tag);
        verify(tagMapper).dtoToTag(tagDto);
        verify(tagMapper).tagToDto(tag);
        assertThat(result).isEqualTo(tagDto);
        assertThat(tagCaptor.getValue()).isEqualTo(tag);
    }

    @Test
    void checkUpdate() {
        Tag tag = getTag();
        TagDto tagDto = getTagDto();

        doReturn(Optional.of(tag))
                .when(tagDao).findById(TEST_ID);
        doReturn(tag)
                .when(tagDao).save(tagCaptor.capture());
        doReturn(tag)
                .when(tagMapper).dtoToTag(tagDto);
        doReturn(tagDto)
                .when(tagMapper).tagToDto(tag);

        TagDto result = tagService.update(tagDto);

        verify(tagDao).findById(anyLong());
        verify(tagDao).save(tag);
        verify(tagMapper).dtoToTag(tagDto);
        verify(tagMapper).tagToDto(tag);
        assertThat(result).isEqualTo(tagDto);
        assertThat(tagCaptor.getValue()).isEqualTo(tag);
    }

    @Test
    void checkUpdateShouldThrowTagNotFoundException() {
        doThrow(TagNotFoundException.class)
                .when(tagDao).findById(anyLong());
        assertThrows(TagNotFoundException.class, () -> tagService.update(getTagDto()));
        verify(tagDao).findById(anyLong());
    }

    @Test
    void checkDelete() {
        doNothing()
                .when(tagDao).deleteById(TEST_ID);
        tagService.deleteById(TEST_ID);
        verify(tagDao).deleteById(anyLong());
    }
}