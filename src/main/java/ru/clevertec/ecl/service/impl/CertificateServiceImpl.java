package ru.clevertec.ecl.service.impl;

import com.google.common.base.Joiner;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.criteria.SearchOperation;
import ru.clevertec.ecl.exceptions.CertificateNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.repository.CertificateDao;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.service.CertificateService;
import ru.clevertec.ecl.util.GiftCertificateSpecificationsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDao certificateDao;
    private final GiftCertificateMapper certificateMapper;

    @Override
    public List<GiftCertificateDto> findAll(String search, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Specification<GiftCertificate> specification = buildSpecification(search);

        Page<GiftCertificate> pagedResult = certificateDao.findAll(specification, paging);

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
        if (newCertificate.getTags() != null)
        newCertificate.getTags().stream().forEach(currentCertificate::addTag);
    }

    private Specification<GiftCertificate> buildSpecification(String search) {
        GiftCertificateSpecificationsBuilder builder = new GiftCertificateSpecificationsBuilder();
        String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(4),
                    matcher.group(3),
                    matcher.group(5));
        }
        return builder.build();
    }

}
