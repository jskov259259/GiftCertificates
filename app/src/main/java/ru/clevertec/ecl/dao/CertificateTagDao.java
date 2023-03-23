package ru.clevertec.ecl.dao;

public interface CertificateTagDao {

    boolean isCertificateTagExists(Long certificateId, Long tagId);

    void create(Long certificateId, Long tagId);
}
