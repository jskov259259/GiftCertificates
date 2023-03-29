package ru.clevertec.ecl.service;

import ru.clevertec.ecl.model.GiftCertificate;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class CertificateBuilder {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Duration duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    public CertificateBuilder withId(Long certificateId) {
        this.id = certificateId;
        return this;
    }

    public CertificateBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CertificateBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CertificateBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public CertificateBuilder withDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public CertificateBuilder withCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public CertificateBuilder withLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public GiftCertificate build() {
        GiftCertificate certificate = new GiftCertificate(id, name, description, price, duration, createDate, lastUpdateDate);
        return certificate;
    }
}

