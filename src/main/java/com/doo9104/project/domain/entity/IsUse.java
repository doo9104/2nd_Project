package com.doo9104.project.domain.entity;

public enum IsUse {
    Y,N;

    public IsUse inverse() {
        return this == Y ? N : Y;
    }
}
