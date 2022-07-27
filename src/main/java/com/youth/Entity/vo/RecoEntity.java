package com.youth.Entity.vo;

import lombok.Data;

/**
 * @author maoyan
 * @date 2022/7/23 15:46
 */
@Data
public class RecoEntity {
    private String code;
    private String message;
    private RecoRankEntity data;

    public RecoEntity() {}
}
