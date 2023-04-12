package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.exceptions.CertificateNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.repository.CertificateDao;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.service.CertificateService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDao certificateDao;
    private final GiftCertificateMapper certificateMapper;

    @Override
    public List<GiftCertificateDto> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<GiftCertificate> pagedResult = certificateDao.findAll(paging);

        return pagedResult.getContent().stream()
                .map(certificateMapper::certificateToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        GiftCertificate certificate = certificateDao.findById(id)
                .orElseThrow(() -> new CertificateNotFoundException(id));
        return certificateMapper.certificateToDto(certificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto save(GiftCertificateDto certificateDto) {
        LocalDateTime currentTime = LocalDateTime.now();
        certificateDto.setCreateDate(currentTime);
        GiftCertificate certificate = certificateMapper.dtoToCertificate(certificateDto);

        GiftCertificate createdCertificate = certificateDao.save(certificate);
        return certificateMapper.certificateToDto(createdCertificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(GiftCertificateDto certificateDto) {
        GiftCertificate currentCertificate = certificateDao.findById(certificateDto.getId())
                .orElseThrow(() -> new CertificateNotFoundException(certificateDto.getId()));

        GiftCertificate newCertificate = certificateMapper.dtoToCertificate(certificateDto);
        updateCertificate(currentCertificate, newCertificate);
        GiftCertificate updatedCertificate = certificateDao.save(currentCertificate);
        return certificateMapper.certificateToDto(updatedCertificate);
    }

    @Override
    @Transactional
    public void updatePrice(Long id, BigDecimal price) {
        certificateDao.updatePrice(id, price);
    }

    @Override
    @Transactional
    public void deleteById(Long certificateId) {
        certificateDao.deleteById(certificateId);
    }

    private void updateCertificate(GiftCertificate currentCertificate, GiftCertificate newCertificate) {
        currentCertificate.setLastUpdateDate(LocalDateTime.now());
        currentCertificate.setName(newCertificate.getName());
        currentCertificate.setDescription(newCertificate.getDescription());
        currentCertificate.setPrice(newCertificate.getPrice());
        currentCertificate.setDuration(newCertificate.getDuration());
        currentCertificate.setLastUpdateDate(LocalDateTime.now());
        if (newCertificate.getTags() != null)
        newCertificate.getTags().stream().forEach(currentCertificate::addTag);
    }

}
