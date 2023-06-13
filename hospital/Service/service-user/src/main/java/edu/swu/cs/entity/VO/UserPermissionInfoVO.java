package edu.swu.cs.entity.VO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserPermissionInfoVO {
    List<String> permissions;
    List<String> roles;
}