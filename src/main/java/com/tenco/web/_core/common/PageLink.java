package com.tenco.web._core.common;

import lombok.Data;

@Data
public class PageLink {
    private int innerPageNumber;
    private int displayNumber;
    private boolean active;

    public PageLink(int innerPageNumber, int displayNumber, boolean active) {
        this.innerPageNumber = innerPageNumber;
        this.displayNumber = displayNumber;
        this.active = active;
    }
}
