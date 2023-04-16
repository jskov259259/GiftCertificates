package ru.clevertec.ecl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.ecl.model.GiftCertificate;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.clevertec.ecl.util.Constants.TEST_ID;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_NO;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_SIZE;
import static ru.clevertec.ecl.util.Constants.TEST_PRICE;
import static ru.clevertec.ecl.util.Constants.TEST_SEARCH;
import static ru.clevertec.ecl.util.Constants.TEST_SORT_BY;
import static ru.clevertec.ecl.util.TestData.getCertificate;
import static ru.clevertec.ecl.util.TestData.getSpecification;
import static ru.clevertec.ecl.util.TestData.getTagNames;

@DataJpaTest
class CertificateDaoTestIT {

    @Autowired
    private CertificateDao certificateDao;

    @Test
    void checkFindAll() {
        Specification<GiftCertificate> specification = getSpecification(TEST_SEARCH);
        Page<GiftCertificate> pagedResult = certificateDao.findAll(specification,
                PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE, Sort.by(TEST_SORT_BY)));
        assertThat(pagedResult.getContent().size()).isEqualTo(8);
    }

    @Test
    void checkFindAllByTagNames() {
        List<String> tagNames = getTagNames();
        Page<GiftCertificate> pagedResult = certificateDao.findAllByTagNames(
                PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE, Sort.by(TEST_SORT_BY)), tagNames);
        assertThat(pagedResult.getContent().size()).isEqualTo(2);
        assertThat(pagedResult.getContent().get(0).getName()).isEqualTo("Restaurant");
        assertThat(pagedResult.getContent().get(1).getName()).isEqualTo("Supermarket");
    }

    @Test
    void checkFindById() {
        Optional<GiftCertificate> certificateData = certificateDao.findById(TEST_ID);
        assertThat(certificateData.get().getId()).isEqualTo(TEST_ID);
        assertThat(certificateData.get().getName()).isEqualTo("Beauty Saloon");
    }

    @Test
    void checkSave() {
        GiftCertificate certificate = getCertificate();
        GiftCertificate createdCertificate = certificateDao.save(certificate);
        assertThat(createdCertificate.getName()).isEqualTo("Certificate1");
        assertThat(createdCertificate.getPrice()).isEqualTo(BigDecimal.valueOf(1));
        assertThat(createdCertificate.getDuration()).isEqualTo(Duration.ofDays(1));
    }

    @Test
    void checkUpdate() {
        GiftCertificate certificate = getCertificate();
        certificateDao.save(certificate);

        certificate.setName("New Certificate");
        certificate.setPrice(TEST_PRICE);
        GiftCertificate updatedGiftCertificate = certificateDao.save(certificate);
        assertThat(updatedGiftCertificate.getName()).isEqualTo("New Certificate");
        assertThat(updatedGiftCertificate.getPrice()).isEqualTo(BigDecimal.valueOf(5));
    }

//    @Test
//    void checkUpdatePrice() {
//        GiftCertificate certificate = certificateDao.findById(1L).get();
//        certificateDao.updatePrice(1L, TEST_PRICE);
//        GiftCertificate updatedCertificate = certificateDao.findById(1L).get();
//        System.out.println(updatedCertificate.getPrice());
//        assertThat(certificate.getPrice()).isNotEqualTo(updatedCertificate.getPrice());
//        assertThat(updatedCertificate.getPrice()).isEqualTo(BigDecimal.valueOf(5));
//    }

    @Test
    void deleteById() {
        Specification<GiftCertificate> specification = getSpecification(TEST_SEARCH);
        Integer sizeBefore = certificateDao.findAll(specification,
                PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE, Sort.by(TEST_SORT_BY))).getContent().size();

        certificateDao.deleteById(1L);

        Integer sizeAfter = certificateDao.findAll(specification,
                PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE, Sort.by(TEST_SORT_BY))).getContent().size();
        assertThat(sizeAfter).isLessThan(sizeBefore);
    }
}