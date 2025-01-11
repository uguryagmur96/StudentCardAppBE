package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Date;
import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardResponseDto {
    private Map<String,Integer> notes;
    private String profilePicture;
    private Double absence;
    private Integer totalNote;
    private List<String> groupName;
    private String masterTrainer;
    private String assistantTrainer;
    private Date startDate;
    private Date endDate;
}
