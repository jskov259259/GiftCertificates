package ru.clevertec.ecl.service;

import ru.clevertec.ecl.model.GiftCertificate;

import java.util.List;

public interface CertificateService {

    List<GiftCertificate> findAll();
}
