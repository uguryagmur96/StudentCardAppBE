package com.bilgeadam.dto.request;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupStudentAttendanceRequestDto {
    private String groupId;
    private Date currentDate;
}
