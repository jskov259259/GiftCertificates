package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.model.User;

public interface UserDao extends JpaRepository<User, Long> {

    @Query(value = "SELECT u.id, u.name, u.age FROM users u " +
            "INNER JOIN orders o on u.id = o.user_id " +
            "GROUP BY u.id " +
            "ORDER BY sum(o.price) DESC " +
            "LIMIT 1",
            nativeQuery = true)
    User findUserByHighestCostOfAllOrders();
}
