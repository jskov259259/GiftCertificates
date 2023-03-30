//package ru.clevertec.ecl.dao.jdbc;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//import ru.clevertec.ecl.config.SpringTestDBConfig;
//import ru.clevertec.ecl.dao.exceptions.CertificateNameNotUniqueException;
//import ru.clevertec.ecl.dao.jdbc.CertificateDaoJdbc;
//import ru.clevertec.ecl.model.GiftCertificate;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//
//@ContextConfiguration(classes = SpringTestDBConfig.class)
//@Transactional
//@Rollback
//@ActiveProfiles("dev")
//@ExtendWith(SpringExtension.class)
//class CertificateDaoJdbcTestIT {
//
//    private CertificateDaoJdbc certificateDao;
//
//    @Autowired
//    public CertificateDaoJdbcTestIT(CertificateDaoJdbc certificateDao) {
//        this.certificateDao = certificateDao;
//    }
//
//    @Test
//    void checkCertificateDaoNotNull() {
//
//      assertThat(certificateDao).isNotNull();
//    }
//
//    @Test
//    void checkFindAll() {
//
//        List<GiftCertificate> certificates = certificateDao.findAll();
//        assertThat(certificates).hasSize(8);
//    }
//
//    @Test
//    void checkFindAllWithFilter() {
//
//        String customSql = "SELECT id, name, description, price, duration, create_date, last_update_date " +
//                "FROM gift_certificate WHERE name LIKE '%Spa%'";
//        List<GiftCertificate> certificates = certificateDao.findAllWithFilter(customSql);
//        assertThat(certificates).hasSize(1);
//    }
//
//    @Test
//    void checkFindById() {
//
//        List<GiftCertificate> certificates = certificateDao.findAll();
//
//        GiftCertificate certificateSrc = certificates.get(0);
//        GiftCertificate certificateDst = certificateDao.findById(certificateSrc.getId());
//        assertThat(certificateSrc).isEqualTo(certificateDst);
//    }
//
//    @Test
//    void tryToCreateNotUniqueCertificate() {
//
//        GiftCertificate certificate = certificateDao.findAll().get(0);
//        assertThatExceptionOfType(CertificateNameNotUniqueException.class)
//                .isThrownBy(() -> {
//                    certificateDao.create(certificate);
//                });
//    }
//
//    @Test
//    void checkUpdate() {
//
//        List<GiftCertificate> certificates = certificateDao.findAll();
//
//        GiftCertificate certificateSrc = certificates.get(0);
//        certificateSrc.setName(certificateSrc.getName() + "_TEST");
//        certificateDao.update(certificateSrc);
//
//        GiftCertificate certificateDst = certificateDao.findById(certificateSrc.getId());
//        assertThat(certificateSrc.getName()).isEqualTo(certificateDst.getName());
//    }
//
//    @Test
//    void checkDelete() {
//
//        int certificateSizeBefore = certificateDao.findAll().size();
//
//        certificateDao.delete(8);
//        int certificateSizeAfter = certificateDao.findAll().size();
//        assertThat(certificateSizeBefore).isEqualTo(certificateSizeAfter + 1);
//    }
//
//    @Test
//    void checkIsCertificateUniqueReturnTrue() {
//
//        GiftCertificate certificate = new GiftCertificate(50L, "Non existent certificate");
//        assertThat(certificateDao.isCertificateUnique(certificate.getName())).isTrue();
//    }
//
//    @Test
//    void checkIsCertificateUniqueReturnFalse() {
//
//        GiftCertificate certificate = certificateDao.findAll().get(0);
//        assertThat(certificateDao.isCertificateUnique(certificate.getName())).isFalse();
//    }
//
//}