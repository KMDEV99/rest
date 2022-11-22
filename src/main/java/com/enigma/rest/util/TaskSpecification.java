package com.enigma.rest.util;

import com.enigma.rest.exception.InvalidSearchQueryException;
import com.enigma.rest.model.Task;
import com.enigma.rest.model.TaskStatusEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskSpecification implements Specification<Task> {

    private SearchCriteria criteria;

    public TaskSpecification(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (criteria.getKey().equalsIgnoreCase("taskStatus")) {
            try {
                criteria.setValue(TaskStatusEnum.valueOf(criteria.getValue().toString()));
            } catch (Exception e) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, String.format("`%s` status does not exist", criteria.getValue().toString()), e);
            }
        }

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

    public static TaskSpecification validateSpecification(String searchCriteria) {
        Pattern pattern = Pattern.compile("(\\w+)(:|<|>)(\\w+)", Pattern.UNICODE_CHARACTER_CLASS);
        if (searchCriteria != null) {
            Matcher matcher = pattern.matcher(searchCriteria);

            if (!matcher.find()) {
                throw new InvalidSearchQueryException(String.format("Proper query syntax: %s", "?q=name:Konrad"));
            }
            return new TaskSpecification(
                    new SearchCriteria(
                            matcher.group(1),matcher.group(2),matcher.group(3)
                    ));
        }
        return null;
    }
}
