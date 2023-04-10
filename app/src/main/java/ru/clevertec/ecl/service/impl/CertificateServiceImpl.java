package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.repository.CertificateDao;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.dto.SearchCriteria;
import ru.clevertec.ecl.service.CertificateService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDao certificateDao;

    @Override
    public List<GiftCertificate> findAll(List<SearchCriteria> params) {
        return certificateDao.findAll(params);
    }

    @Override
    public GiftCertificate findById(Long id) {
        return certificateDao.findById(id);
    }

    @Override
    @Transactional
    public Long create(GiftCertificate certificate) {
        LocalDateTime currentTime = LocalDateTime.now();
        certificate.setCreateDate(currentTime);
        return certificateDao.create(certificate);
    }

    @Override
    @Transactional
    public Integer update(GiftCertificate certificate) {
        LocalDateTime currentTime = LocalDateTime.now();
        certificate.setLastUpdateDate(currentTime);
        return certificateDao.update(certificate);
    }

    @Override
    @Transactional
    public void delete(Integer certificateId) {
        certificateDao.delete(certificateId);
    }

}
