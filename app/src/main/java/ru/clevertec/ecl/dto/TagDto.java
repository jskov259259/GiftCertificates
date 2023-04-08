package ru.clevertec.ecl.dto;

import lombok.*;
import ru.clevertec.ecl.model.GiftCertificate;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {

    private Long id;
    private String name;
    private List<GiftCertificate> certificates = new ArrayList<>();
}
