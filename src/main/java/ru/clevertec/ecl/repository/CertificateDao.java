package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.clevertec.ecl.model.GiftCertificate;

import java.math.BigDecimal;

public interface CertificateDao extends JpaRepository<GiftCertificate, Long>, JpaSpecificationExecutor<GiftCertificate> {

    @Modifying
    @Query("update GiftCertificate g set g.price = :price where g.id = :id")
    void updatePrice(@Param(value = "id") long id,
                     @Param(value = "price") BigDecimal price);
}
