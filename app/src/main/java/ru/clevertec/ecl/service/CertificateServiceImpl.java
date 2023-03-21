package ru.clevertec.ecl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dao.CertificateDao;

@Service
public class CertificateServiceImpl implements CertificateService {

    private CertificateDao certificateDao;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    public String getHello() {
        String hello = certificateDao.getHello().get(1).getHello();
        return hello;
    }
}
