package ru.clevertec.ecl.service;

import ru.clevertec.ecl.model.GiftCertificate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CertificateService {

    List<GiftCertificate> findAll(Map<String, String> filterParams);

    Optional<GiftCertificate> findById(Long id);
//
    Long save(GiftCertificate certificate);
//
//    Integer update(GiftCertificate certificate);

    void deleteById(Integer certificateId);
}
