package com.lightspace.util;


import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

public class CriteriaUtils {

    CriteriaUtils(){

    }
    public static void gteAndLte(Object min, Object max, String field, Consumer<Criteria> addCriteria) {

        var criteria = new ArrayList<Criteria>();
        if(!isNull(min)) {
            criteria.add(Criteria.where(field).gte(min));
        }
        if(!isNull(max)) {
            criteria.add(Criteria.where(field).lte(max));
        }

        if(!CollectionUtils.isEmpty(criteria)) {
            addCriteria.accept(new Criteria()
                    .andOperator(
                            criteria.toArray(Criteria[]::new)
                    ));
        }
    }

    public static Query toQuery(Collection<Criteria> criteriaList) {
        var query= new Query();
        for (var criteria:criteriaList) {
            query= query.addCriteria(criteria);
        }
        return query;
    }
}
