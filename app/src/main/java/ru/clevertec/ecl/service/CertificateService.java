package ru.clevertec.ecl.service;

import ru.clevertec.ecl.model.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface CertificateService {

    List<GiftCertificate> findAll(Map<String, String> filterParams);

    Long create(GiftCertificate certificate);

    Integer update(GiftCertificate certificate);

    Integer delete(Integer certificateId);
}
