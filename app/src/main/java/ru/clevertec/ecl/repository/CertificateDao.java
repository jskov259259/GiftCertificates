package ru.clevertec.ecl.repository;

import ru.clevertec.ecl.model.GiftCertificate;

import java.util.List;

public interface CertificateDao {

    List<GiftCertificate> findAll();

    GiftCertificate findById(Long id);

    List<GiftCertificate> findAllWithFilter(String query);

    Long create(GiftCertificate certificate);

    Integer update(GiftCertificate certificate);

    Integer delete(Integer certificateId);
}
