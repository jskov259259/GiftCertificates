package ru.clevertec.ecl.dao;

import ru.clevertec.ecl.model.GiftCertificate;

import java.util.List;

public interface CertificateDao {

    List<GiftCertificate> findAll();
}
