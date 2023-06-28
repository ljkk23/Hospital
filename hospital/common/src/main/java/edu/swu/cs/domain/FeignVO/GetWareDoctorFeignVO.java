package edu.swu.cs.domain.FeignVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetWareDoctorFeignVO implements Serializable {
    private long id;
    private String title;
    private Integer amount;
    private String realName;
    private String introduce;
    private String sex;
    private String avatar;
}
