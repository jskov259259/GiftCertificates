package ru.clevertec.ecl.util;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.ecl.dto.criteria.SearchCriteria;
import ru.clevertec.ecl.model.GiftCertificate;

@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateSpecification implements Specification<GiftCertificate> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(
            Root<GiftCertificate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        switch (criteria.getOperation()) {
            case EQUALITY:
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            case NEGATION:
                return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN:
                return builder.greaterThan(root.<String> get(
                        criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN:
                return builder.lessThan(root.<String> get(
                        criteria.getKey()), criteria.getValue().toString());
            case LIKE:
                return builder.like(root.<String> get(
                        criteria.getKey()), criteria.getValue().toString());
            case STARTS_WITH:
                return builder.like(root.<String> get(criteria.getKey()), criteria.getValue() + "%");
            case ENDS_WITH:
                return builder.like(root.<String> get(criteria.getKey()), "%" + criteria.getValue());
            case CONTAINS:
                return builder.like(root.<String> get(
                        criteria.getKey()), "%" + criteria.getValue() + "%");
            default:
                return null;
        }
    }
}