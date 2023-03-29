package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dao.*;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.model.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    @InjectMocks
    private CertificateServiceImpl certificateService;
    @Mock
    private CertificateDao certificateDao;
    @Mock
    private TagDao tagDao;
    @Mock
    private CertificateTagDao certificateTagDao;

    @Captor
    private ArgumentCaptor<String> queryCaptor;

    @Test
    void checkFindAllWithoutFilterParams() {

        Map<String, String> filterParams = new HashMap<>();
        doReturn(getCertificates()).when(certificateDao).findAll();
        List<GiftCertificate> resultList = certificateService.findAll(filterParams);
        assertThat(resultList).hasSize(3);
    }

    @Test
    void checkFindAllWithFilterParams() {

        Map<String, String> filterParams = new HashMap<>();
        filterParams.put("tagName", "food");
        doReturn(getCertificates()).when(certificateDao).findAllWithFilter(ArgumentMatchers.any());
        List<GiftCertificate> resultList = certificateService.findAll(filterParams);
        verify(certificateDao).findAllWithFilter(queryCaptor.capture());
        assertThat(resultList).hasSize(3);
        assertThat(queryCaptor.getValue()).isNotNull();
    }

    @Test
    void checkCreateWithoutTags() {

        GiftCertificate certificate = getCertificates().get(0);
        doReturn(certificate.getId()).when(certificateDao).create(certificate);
        Long resultId = certificateService.create(certificate);
        verify(certificateDao).create(any());
        assertThat(resultId).isEqualTo(1L);
    }

    @Test
    void checkCreateWithTags() {

        GiftCertificate certificate = getCertificates().get(0);
        certificate.setTags(getTags());
        doReturn(certificate.getId()).when(certificateDao).create(certificate);

        Long resultId = certificateService.create(certificate);

        verify(tagDao, Mockito.times(2)).create(any());
        verify(certificateTagDao, Mockito.times(2)).create(any(), any());
        assertThat(resultId).isEqualTo(1L);
    }

    @Test
    void checkUpdateWithoutTags() {

        GiftCertificate certificate = getCertificates().get(0);
        doReturn(1).when(certificateDao).update(certificate);
        Integer resultRows = certificateService.update(certificate);
        verify(certificateDao).update(any());
        assertThat(resultRows).isEqualTo(1);
    }

    @Test
    void checkUpdateWithTags() {

        GiftCertificate certificate = getCertificates().get(0);
        certificate.setTags(getTags());
        doReturn(1).when(certificateDao).update(certificate);

        Integer resultRows = certificateService.update(certificate);

        verify(tagDao, Mockito.times(2)).create(any());
        verify(certificateTagDao, Mockito.times(2)).create(any(), any());
        assertThat(resultRows).isEqualTo(1);
    }

    @Test
    void checkDelete() {

        doReturn(1).when(certificateDao).delete(any());
        Integer resultRows = certificateService.delete(1);
        verify(certificateDao).delete(any());
        assertThat(resultRows).isEqualTo(1);
    }

    private List<GiftCertificate> getCertificates() {

        List<GiftCertificate> certificates = new ArrayList<>();
        certificates.add(new CertificateBuilder().withId(1L).withName("Certificate1").build());
        certificates.add(new CertificateBuilder().withId(2L).withName("Certificate2").build());
        certificates.add(new CertificateBuilder().withId(3L).withName("Certificate3").build());
        return certificates;
    }

    private List<Tag> getTags() {

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "Tag1"));
        tags.add(new Tag(2L, "Tag2"));
        return tags;
    }

}