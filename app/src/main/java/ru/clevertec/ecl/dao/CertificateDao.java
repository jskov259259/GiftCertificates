package ru.clevertec.ecl.dao;

import ru.clevertec.ecl.model.GiftCertificate;

import java.util.List;

public interface CertificateDao {

    List<GiftCertificate> findAll(Integer pageNumber, Integer pageSize);

    GiftCertificate findById(Long id);

    List<GiftCertificate> findAllWithFilter(String query);

    Long create(GiftCertificate certificate);

    Integer update(GiftCertificate certificate);

    void delete(Integer certificateId);
}
