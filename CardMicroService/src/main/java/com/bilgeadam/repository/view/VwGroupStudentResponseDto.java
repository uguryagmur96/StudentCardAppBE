package com.bilgeadam.repository.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VwGroupStudentResponseDto {
    private String groupStudentId;
    private String groupId;
    private String name;
    private String surname;
}
