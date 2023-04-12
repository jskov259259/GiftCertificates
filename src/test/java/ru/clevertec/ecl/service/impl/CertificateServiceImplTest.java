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
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.exceptions.CertificateNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.repository.CertificateDao;

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
import static ru.clevertec.ecl.util.Constants.TEST_PRICE;
import static ru.clevertec.ecl.util.Constants.TEST_SEARCH;
import static ru.clevertec.ecl.util.Constants.TEST_SORT_BY;
import static ru.clevertec.ecl.util.TestData.getCertificate;
import static ru.clevertec.ecl.util.TestData.getCertificateDto;
import static ru.clevertec.ecl.util.TestData.getCertificates;
import static ru.clevertec.ecl.util.TestData.getSpecification;
import static ru.clevertec.ecl.util.TestData.getTagNames;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Mock
    private CertificateDao certificateDao;

    @Mock
    private GiftCertificateMapper certificateMapper;

    @Captor
    ArgumentCaptor<GiftCertificate> certificateCaptor;

    @Test
    void checkFindAll() {
        GiftCertificate certificate = getCertificate();
        GiftCertificateDto certificateDto = getCertificateDto();
        Specification<GiftCertificate> specification = getSpecification(TEST_SEARCH);

        doReturn(new PageImpl<>(getCertificates()))
                .when(certificateDao).findAll(specification, PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE, Sort.by(TEST_SORT_BY)));
        doReturn(certificateDto)
                .when(certificateMapper).certificateToDto(certificate);

        List<GiftCertificateDto> certificates = certificateService.findAll(TEST_SEARCH, TEST_PAGE_NO, TEST_PAGE_SIZE, TEST_SORT_BY);

        verify(certificateDao).findAll(specification, PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE, Sort.by(TEST_SORT_BY)));
        verify(certificateMapper, times(3)).certificateToDto(any());

        assertThat(certificates.get(0)).isEqualTo(certificateDto);
    }

    @Test
    void checkFindAllByTagNames() {
        GiftCertificate certificate = getCertificate();
        GiftCertificateDto certificateDto = getCertificateDto();
        List<String> tagNames = getTagNames();

        doReturn(new PageImpl<>(getCertificates()))
                .when(certificateDao).findAllByTagNames(PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE, Sort.by(TEST_SORT_BY)), tagNames);
        doReturn(certificateDto)
                .when(certificateMapper).certificateToDto(certificate);

        List<GiftCertificateDto> certificates = certificateService.
                findAllByTagNames(TEST_PAGE_NO, TEST_PAGE_SIZE, TEST_SORT_BY, getTagNames());

        verify(certificateDao).findAllByTagNames(PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE, Sort.by(TEST_SORT_BY)), tagNames);
        verify(certificateMapper, times(3)).certificateToDto(any());

        assertThat(certificates.get(0)).isEqualTo(certificateDto);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L})
    void checkFindById(Long id) {
        GiftCertificate certificate = getCertificate();
        GiftCertificateDto certificateDto = getCertificateDto();

        doReturn(Optional.of(certificate))
                .when(certificateDao).findById(id);
        doReturn(certificateDto)
                .when(certificateMapper).certificateToDto(certificate);

        GiftCertificateDto result = certificateService.findById(id);

        verify(certificateDao).findById(anyLong());
        verify(certificateMapper).certificateToDto(any());
        assertThat(result).isEqualTo(certificateDto);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L})
    void checkFindByIdShouldThrowCertificateNotFoundException(Long id) {
        doThrow(CertificateNotFoundException.class)
                .when(certificateDao).findById(anyLong());
        assertThrows(CertificateNotFoundException.class, () -> certificateService.findById(id));
        verify(certificateDao).findById(anyLong());
    }

    @Test
    void checkSave() {
        GiftCertificate certificate = getCertificate();
        GiftCertificateDto certificateDto = getCertificateDto();

        doReturn(certificate)
                .when(certificateDao).save(certificateCaptor.capture());
        doReturn(certificate)
                .when(certificateMapper).dtoToCertificate(certificateDto);
        doReturn(certificateDto)
                .when(certificateMapper).certificateToDto(certificate);

        GiftCertificateDto result = certificateService.save(certificateDto);
        verify(certificateDao).save(certificate);
        verify(certificateMapper).dtoToCertificate(certificateDto);
        verify(certificateMapper).certificateToDto(certificate);
        assertThat(result).isEqualTo(certificateDto);
        assertThat(certificateCaptor.getValue()).isEqualTo(certificate);
    }

    @Test
    void checkUpdate() {
        GiftCertificate certificate = getCertificate();
        GiftCertificateDto certificateDto = getCertificateDto();

        doReturn(Optional.of(certificate))
                .when(certificateDao).findById(TEST_ID);
        doReturn(certificate)
                .when(certificateDao).save(certificateCaptor.capture());
        doReturn(certificate)
                .when(certificateMapper).dtoToCertificate(certificateDto);
        doReturn(certificateDto)
                .when(certificateMapper).certificateToDto(certificate);

        GiftCertificateDto result = certificateService.update(certificateDto);

        verify(certificateDao).findById(anyLong());
        verify(certificateDao).save(certificate);
        verify(certificateMapper).dtoToCertificate(certificateDto);
        verify(certificateMapper).certificateToDto(certificate);
        assertThat(result).isEqualTo(certificateDto);
        assertThat(certificateCaptor.getValue()).isEqualTo(certificate);
    }

    @Test
    void checkUpdateShouldThrowCertificateNotFoundException() {
        doThrow(CertificateNotFoundException.class)
                .when(certificateDao).findById(anyLong());
        assertThrows(CertificateNotFoundException.class, () -> certificateService.update(getCertificateDto()));
        verify(certificateDao).findById(anyLong());
    }

    @Test
    void checkUpdatePrice() {
        doNothing()
                .when(certificateDao).updatePrice(TEST_ID, TEST_PRICE);
        certificateService.updatePrice(TEST_ID, TEST_PRICE);
        verify(certificateDao).updatePrice(anyLong(), any());
    }

    @Test
    void checkDelete() {
        doNothing()
                .when(certificateDao).deleteById(TEST_ID);
        certificateService.deleteById(TEST_ID);
        verify(certificateDao).deleteById(anyLong());
    }
}