package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.model.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface CertificateDao extends JpaRepository<GiftCertificate, Long> {

//
//    List<GiftCertificate> findAllWithFilter(String query);
//
//    Long create(GiftCertificate certificate);
//
//    Integer update(GiftCertificate certificate);

    void deleteById(Integer certificateId);
}
