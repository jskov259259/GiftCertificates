package ru.clevertec.ecl.util;

import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.ecl.dto.criteria.SearchCriteria;
import ru.clevertec.ecl.dto.criteria.SearchOperation;
import ru.clevertec.ecl.model.GiftCertificate;

import java.util.ArrayList;
import java.util.List;

public class GiftCertificateSpecificationsBuilder {

    private List<SearchCriteria> params = new ArrayList<>();

    public GiftCertificateSpecificationsBuilder with(
            String key, String operation, Object value, String prefix, String suffix) {

        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) {
                boolean startWithAsterisk = prefix.contains("*");
                boolean endWithAsterisk = suffix.contains("*");

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SearchCriteria(key, op, value));
        }
        return this;
    }

    public Specification<GiftCertificate> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification result = new GiftCertificateSpecification(params.get(0));

        Specification finalResult = result;
        params.stream().forEach(criteria -> {
            if (criteria.isOrPredicate()) {
                Specification.where(finalResult).or(new GiftCertificateSpecification(criteria));
            } else
                Specification.where(finalResult).and(new GiftCertificateSpecification(criteria));
        });

        return result;
    }
}
