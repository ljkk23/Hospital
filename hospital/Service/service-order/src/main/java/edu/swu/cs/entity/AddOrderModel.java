package edu.swu.cs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderModel {
    private Long userId;
    private Long patientId;
    private Long productId;
    private String type;
}
