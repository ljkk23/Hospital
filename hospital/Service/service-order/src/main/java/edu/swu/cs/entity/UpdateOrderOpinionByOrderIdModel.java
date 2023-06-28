package edu.swu.cs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderOpinionByOrderIdModel {
    private String orderId;
    private String opinions;
}
