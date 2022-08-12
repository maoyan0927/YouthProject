package com.youth.Entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Curve {
    private Double bp;
    private Double bp2;
    private Double target;

    private List<List<Float>> points;

}
