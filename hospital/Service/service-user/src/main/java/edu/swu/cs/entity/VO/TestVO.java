package edu.swu.cs.entity.VO;

import edu.swu.cs.entity.TestEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestVO {
    private TestEnum userName;
    private String pass;
}
