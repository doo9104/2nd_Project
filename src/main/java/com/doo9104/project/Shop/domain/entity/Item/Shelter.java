package com.doo9104.project.Shop.domain.entity.Item;

import com.doo9104.project.Shop.domain.entity.Item.Item;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/*@Getter
@Entity
@DiscriminatorValue("S")*/
public class Shelter extends Item {
    private String a;
    private String b;
}
