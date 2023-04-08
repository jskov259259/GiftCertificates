package ru.clevertec.ecl.service;

import org.springframework.web.bind.annotation.RequestBody;
import ru.clevertec.ecl.dto.GiftCertificateDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CertificateService {

    List<GiftCertificateDto> findAll(Integer pageNo, Integer pageSize, String sortBy);

    GiftCertificateDto findById(Long id);

    GiftCertificateDto save(GiftCertificateDto certificateDto);

    GiftCertificateDto update(GiftCertificateDto certificateDto);

    void updatePrice(Long id, BigDecimal price);

    void deleteById(Long certificateId);
}
