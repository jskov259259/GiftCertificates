package ru.clevertec.ecl.service;

import ru.clevertec.ecl.model.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface CertificateService {

    List<GiftCertificate> findAll(Map<String, String> filterParams);

    GiftCertificate findById(Long id);

    Long create(GiftCertificate certificate);

    Integer update(GiftCertificate certificate);

    void delete(Integer certificateId);
}
