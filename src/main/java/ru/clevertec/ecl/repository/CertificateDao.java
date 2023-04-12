package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.clevertec.ecl.model.GiftCertificate;

import java.math.BigDecimal;
import java.util.List;

public interface CertificateDao extends JpaRepository<GiftCertificate, Long>, JpaSpecificationExecutor<GiftCertificate> {

    @Modifying
    @Query("update GiftCertificate g set g.price = :price where g.id = :id")
    void updatePrice(@Param(value = "id") long id,
                     @Param(value = "price") BigDecimal price);

    @Query(value = "SELECT DISTINCT g.id, g.name, g.description, g.price, g.duration, g.create_date, g.last_update_date " +
            "FROM gift_certificate g " +
            "INNER JOIN certificates_tags ct " +
            "ON g.id=ct.certificate_id " +
            "INNER JOIN tag t ON ct.tag_id=t.id " +
            "WHERE t.name IN(:tagNames)",
            nativeQuery = true)
    Page<GiftCertificate> findAllByTagNames(Pageable pageable, @Param(value = "tagNames") List<String> tags);

}
