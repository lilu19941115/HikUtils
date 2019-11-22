package com.cn.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class HiKCard implements Serializable {
    private String cardId;
    private String cardNo;
    private String personId;
    private String personName;
    private Integer useStatus;
    private String startDate;
    private String endDate;
    private String lossDate;
    private String unlossDate;
}
