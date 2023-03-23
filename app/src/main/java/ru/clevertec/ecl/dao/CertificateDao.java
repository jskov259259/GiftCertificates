package ru.clevertec.ecl.dao;

import ru.clevertec.ecl.model.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface CertificateDao {

    List<GiftCertificate> findAll();

    List<GiftCertificate> findAllWithFilter(String query);

    Long create(GiftCertificate certificate);

    Integer update(GiftCertificate certificate);

    Integer delete(Integer certificateId);
}
