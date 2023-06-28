package edu.swu.cs.entity.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDoctorsByDateAndArrangeVO {
    private Long Id;
    private String realName;
    private Integer isArrange;
    private String title;
    private Long productId;

}
