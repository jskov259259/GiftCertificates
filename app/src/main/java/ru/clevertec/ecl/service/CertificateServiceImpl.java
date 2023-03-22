package ru.clevertec.ecl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dao.CertificateDao;
import ru.clevertec.ecl.model.GiftCertificate;

import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {

    private CertificateDao certificateDao;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return certificateDao.findAll();
    }
}
