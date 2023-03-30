package ru.clevertec.ecl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "gift_certificate")
@Getter
@Setter
@ToString(includeFieldNames=true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "duration")
    private Duration duration;
    @Column(name = "create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;
    @Column(name = "last_update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdateDate;
    @Column(name = "tags")
    @ManyToMany
    @JoinTable(name = "certificates_tags",
            joinColumns = @JoinColumn(name = "certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    public GiftCertificate(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GiftCertificate(Long id, String name, String description, BigDecimal price, Duration duration, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    @JsonGetter("duration")
    public String getDurationValue() {
        return duration.toDays() + " days";
    }

    @JsonSetter
    public void setDurationValue(String value) {
        this.duration = Duration.ofDays(DurationDayParser.parse(value));
    }

}
