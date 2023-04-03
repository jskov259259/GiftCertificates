package ru.clevertec.ecl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString(includeFieldNames=true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gift_certificate")
@NamedQueries({
        @NamedQuery(name="GiftCertificate.findById",
                query="select distinct g from GiftCertificate g "
                        + "left join fetch g.tags t "
                        + "where g.id = :id")})
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Duration duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @Cascade({
            org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.MERGE,
            org.hibernate.annotations.CascadeType.PERSIST
    })
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "certificates_tags",
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

    public void addTag(Tag tag) {
        tags.add(tag);
    }

}
