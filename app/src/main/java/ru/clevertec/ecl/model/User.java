package ru.clevertec.ecl.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "name")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private List<Order> orders;

    public boolean addOrder(Order order) {
        order.setUser(this);
        return getOrders().add(order);
    }

    public void removeOrder(Order order) {
        getOrders().remove(order);
    }

}
