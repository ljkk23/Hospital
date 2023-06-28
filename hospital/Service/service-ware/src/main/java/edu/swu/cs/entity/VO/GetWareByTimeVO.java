package edu.swu.cs.entity.VO;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetWareByTimeVO {
    private long offset;
    private Integer count;
    private long productId;
}
