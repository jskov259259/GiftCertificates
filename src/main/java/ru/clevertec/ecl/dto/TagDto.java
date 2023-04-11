package ru.clevertec.ecl.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.model.GiftCertificate;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private List<GiftCertificate> certificates = new ArrayList<>();

    public TagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
