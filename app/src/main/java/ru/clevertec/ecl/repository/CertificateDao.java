package ru.clevertec.ecl.repository;

import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.dto.SearchCriteria;

import java.util.List;

public interface CertificateDao {

    List<GiftCertificate> findAll(List<SearchCriteria> params);

    GiftCertificate findById(Long id);

    List<GiftCertificate> findAllWithFilter(String query);

    Long create(GiftCertificate certificate);

    Integer update(GiftCertificate certificate);

    void delete(Integer certificateId);
}
