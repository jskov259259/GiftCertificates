package ru.clevertec.ecl.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tag")
@Getter
@Setter
@ToString(includeFieldNames=true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "tags")
    private List<GiftCertificate> certificates;

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
