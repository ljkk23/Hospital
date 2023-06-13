package edu.swu.cs.entity;

import edu.swu.cs.domain.securityEntity.UserVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVO {
    String token;
    UserVO userVO;
}