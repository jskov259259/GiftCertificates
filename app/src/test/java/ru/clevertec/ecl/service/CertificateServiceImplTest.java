package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dao.*;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.model.Tag;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;

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
        Mockito.when(certificateDao.findAll()).thenReturn(getCertificates());
        List<GiftCertificate> resultList = certificateService.findAll(filterParams);
        assertThat(resultList).hasSize(3);
    }

    @Test
    void checkFindAllWithFilterParams() {

        Map<String, String> filterParams = new HashMap<>();
        filterParams.put("tagName", "food");
        Mockito.when(certificateDao.findAllWithFilter(ArgumentMatchers.any())).thenReturn(getCertificates());
        List<GiftCertificate> resultList = certificateService.findAll(filterParams);
        Mockito.verify(certificateDao).findAllWithFilter(queryCaptor.capture());
        assertThat(resultList).hasSize(3);
        assertThat(queryCaptor.getValue()).isNotNull();
    }

    @Test
    void checkCreateWithoutTags() throws SQLException {

        GiftCertificate certificate = getCertificates().get(0);
        Mockito.when(certificateDao.create(certificate)).thenReturn(certificate.getId());
        Long resultId = certificateService.create(certificate);
        Mockito.verify(certificateDao).create(any());
        assertThat(resultId).isEqualTo(1L);
    }

    @Test
    void checkCreateWithTags() throws SQLException {

        GiftCertificate certificate = getCertificates().get(0);
        certificate.setTags(getTags());
        Mockito.when(certificateDao.create(certificate)).thenReturn(certificate.getId());

        Long resultId = certificateService.create(certificate);

        Mockito.verify(tagDao, Mockito.times(2)).create(any());
        Mockito.verify(certificateTagDao, Mockito.times(2)).create(any(), any());
        assertThat(resultId).isEqualTo(1L);
    }

    @Test
    void checkUpdateWithoutTags() {

        GiftCertificate certificate = getCertificates().get(0);
        Mockito.when(certificateDao.update(certificate)).thenReturn(1);
        Integer resultRows = certificateService.update(certificate);
        Mockito.verify(certificateDao).update(any());
        assertThat(resultRows).isEqualTo(1);
    }

    @Test
    void checkUpdateWithTags() {

        GiftCertificate certificate = getCertificates().get(0);
        certificate.setTags(getTags());
        Mockito.when(certificateDao.update(certificate)).thenReturn(1);

        Integer resultRows = certificateService.update(certificate);

        Mockito.verify(tagDao, Mockito.times(2)).create(any());
        Mockito.verify(certificateTagDao, Mockito.times(2)).create(any(), any());
        assertThat(resultRows).isEqualTo(1);
    }

    @Test
    void checkDelete() {

        Mockito.when(certificateDao.delete(any())).thenReturn(1);
        Integer resultRows = certificateService.delete(1);
        Mockito.verify(certificateDao).delete(any());
        assertThat(resultRows).isEqualTo(1);
    }

    private List<GiftCertificate> getCertificates() {

        List<GiftCertificate> certificates = new ArrayList<>();
        certificates.add(new GiftCertificate(1L, "Certificate1"));
        certificates.add(new GiftCertificate(2L, "Certificate2"));
        certificates.add(new GiftCertificate(3L, "Certificate3"));
        return certificates;
    }

    private List<Tag> getTags() {

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "Tag1"));
        tags.add(new Tag(2L, "Tag2"));
        return tags;
    }

}