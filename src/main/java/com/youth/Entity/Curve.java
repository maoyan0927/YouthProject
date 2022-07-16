package com.youth.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Curve {
    private Float bp;
    private Float bp2;
    private Float target;
    private String heightEvaluate;

    private List<List<Float>> points;

    public Curve(){
        bp = null;
        bp2 = null;
        heightEvaluate = null;
        target = null;
        points = null;
    }
}
