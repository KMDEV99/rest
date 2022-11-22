package com.enigma.rest.util;

import com.enigma.rest.exception.InvalidSearchQueryException;
import com.enigma.rest.model.Employee;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeSpecification implements Specification<Employee> {

    private SearchCriteria criteria;

    public EmployeeSpecification(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    return criteriaBuilder.like(
                            root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
                } else {
                    return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
            }
        return null;
    }

    public static EmployeeSpecification validateSpecification(String searchCriteria) {
        Pattern pattern = Pattern.compile("(\\w+)(:|<|>)(\\w+)", Pattern.UNICODE_CHARACTER_CLASS);
        if (searchCriteria != null) {
            Matcher matcher = pattern.matcher(searchCriteria);

            if (!matcher.find()) {
                throw new InvalidSearchQueryException(String.format("Proper query syntax: %s", "?q=name:Konrad"));
            }
            return new EmployeeSpecification(
                    new SearchCriteria(
                            matcher.group(1),matcher.group(2),matcher.group(3)
                    ));
        }
        return null;
    }
}
