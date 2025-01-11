package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowGroupInformationListResponseDto {
    private String internShipGroupId;
    private String groupName;
    private Integer numberOfStudent;
}
