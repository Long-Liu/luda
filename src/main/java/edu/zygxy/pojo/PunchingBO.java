package edu.zygxy.pojo;

import lombok.Data;

@Data
public class PunchingBO {
    private Long userId;
    private Double lat;
    private Double lng;
    private Integer type;
}
