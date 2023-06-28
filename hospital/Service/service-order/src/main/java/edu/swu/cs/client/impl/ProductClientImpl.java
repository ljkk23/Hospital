package edu.swu.cs.client.impl;

import edu.swu.cs.client.ProductClient;
import edu.swu.cs.domain.FeignVO.ProductVO;

import java.util.List;

public class ProductClientImpl implements ProductClient {
    @Override
    public ProductVO FeignGetProductInfo(Long id) {

        return null;
    }

    @Override
    public List<Long> getProductIdByDoctorAndDate(String userName, Long date) {
        return null;
    }


}
