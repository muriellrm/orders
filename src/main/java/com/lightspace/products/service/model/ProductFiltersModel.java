package com.lightspace.products.service.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder(toBuilder = true)
public class ProductFiltersModel {

    private Date createdDateMin;
    private Date createdDateMax;

}
