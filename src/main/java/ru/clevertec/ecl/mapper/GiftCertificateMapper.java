package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.GiftCertificate;

@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {

    @Mapping(target = "duration", expression = "java(java.time.Duration.ofDays(certificateDto.getDuration()))")
    GiftCertificate dtoToCertificate(GiftCertificateDto certificateDto);

    @Mapping(target = "duration", expression = "java(certificate.getDuration().toDays())")
    GiftCertificateDto certificateToDto(GiftCertificate certificate);

}
