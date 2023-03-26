package ru.clevertec.ecl.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(includeFieldNames=true)
@EqualsAndHashCode
public class Tag {

    private Long id;
    private String name;
    private List<GiftCertificate> certificates;

    public Tag() {
    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
