package ru.clevertec.ecl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.clevertec.ecl.util.DurationDayParser;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "name")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GiftCertificate {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Duration duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdateDate;

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
