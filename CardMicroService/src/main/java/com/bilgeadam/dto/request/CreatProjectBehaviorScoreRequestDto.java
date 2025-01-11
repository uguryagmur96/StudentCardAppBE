package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatProjectBehaviorScoreRequestDto {

    @NotNull
    private String studentToken;
    private Long rapportScore;
    private Long interestScore;
    private Long presentationScore;
    private Long retroScore;
    private Long rapportScorePercentage ;
    private Long interestScorePercentage ;
    private Long presentationScorePercentage ;
    private Long retroScorePercentage ;
}
