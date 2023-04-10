package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dto.SearchCriteria;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.repository.CertificateDao;
import ru.clevertec.ecl.service.impl.CertificateServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static ru.clevertec.ecl.util.TestData.getCertificates;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    @InjectMocks
    private CertificateServiceImpl certificateService;
    @Mock
    private CertificateDao certificateDao;

    @Captor
    private ArgumentCaptor<String> queryCaptor;

    @Test
    void checkFindAll() {
        List<SearchCriteria> params = new ArrayList<>();
        doReturn(getCertificates())
                .when(certificateDao).findAll(any());
        List<GiftCertificate> resultList = certificateService.findAll(params);
        assertThat(resultList).hasSize(3);
    }

    @Test
    void checkCreate() {
        GiftCertificate certificate = getCertificates().get(0);
        doReturn(certificate.getId())
                .when(certificateDao).create(certificate);
        Long resultId = certificateService.create(certificate);
        verify(certificateDao).create(any());
        assertThat(resultId).isEqualTo(1L);
    }


    @Test
    void checkUpdate() {
        GiftCertificate certificate = getCertificates().get(0);
        doReturn(1)
                .when(certificateDao).update(certificate);
        Integer resultRows = certificateService.update(certificate);
        verify(certificateDao).update(any());
        assertThat(resultRows).isEqualTo(1);
    }

    @Test
    void checkDelete() {
        doNothing()
                .when(certificateDao).delete(any());
        certificateService.delete(any());
        verify(certificateDao).delete(any());
    }
}