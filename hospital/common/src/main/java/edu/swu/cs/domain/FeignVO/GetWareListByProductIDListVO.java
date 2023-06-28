package edu.swu.cs.domain.FeignVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetWareListByProductIDListVO {
    private long productId;
    private Integer count;
}
