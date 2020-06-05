package com.doo9104.project.Shop.domain.entity.Item;


import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/*@Getter
@Entity
@DiscriminatorValue("F")*/
public class Food  extends Item {
    private String a;
    private String b;
}
