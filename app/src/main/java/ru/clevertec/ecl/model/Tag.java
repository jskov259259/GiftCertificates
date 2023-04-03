package ru.clevertec.ecl.model;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(includeFieldNames=true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
