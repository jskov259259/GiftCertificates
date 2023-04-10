package ru.clevertec.ecl.service;

import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.dto.SearchCriteria;

import java.util.List;

public interface CertificateService {

    List<GiftCertificate> findAll(List<SearchCriteria> params);

    GiftCertificate findById(Long id);

    Long create(GiftCertificate certificate);

    Integer update(GiftCertificate certificate);

    void delete(Integer certificateId);
}
