package com.doo9104.project.Shop.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Address {

    private String address1;
    private String address2;
    private String postcode;
}
