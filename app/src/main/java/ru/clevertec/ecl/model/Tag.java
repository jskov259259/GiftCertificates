package ru.clevertec.ecl.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(includeFieldNames=true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    private Long id;
    private String name;
    private List<GiftCertificate> certificates;

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
