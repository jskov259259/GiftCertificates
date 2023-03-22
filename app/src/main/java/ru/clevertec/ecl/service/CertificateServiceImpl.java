package ru.clevertec.ecl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.CertificateDao;
import ru.clevertec.ecl.model.GiftCertificate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {

    private CertificateDao certificateDao;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificate> findAll() {

        return certificateDao.findAll();
    }

    @Override
    @Transactional
    public Long create(GiftCertificate certificate) {

        LocalDateTime currentTime = LocalDateTime.now();
        certificate.setCreateDate(currentTime);
        if (certificate.getTags() != null) {

        }
        return certificateDao.create(certificate);
    }
}
