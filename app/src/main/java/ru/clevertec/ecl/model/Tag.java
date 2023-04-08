package ru.clevertec.ecl.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "name")
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    private Long id;
    private String name;
}
