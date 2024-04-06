package com.newage.wms.service.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Getter
@Setter
@Component
@NoArgsConstructor
public class GrnCalculation {

    public double getUomRatioConvertedQty(Double fromUomRatio, Double toRUomRatio, Double qty) {
        double totalExpQty=0;
        if (fromUomRatio != null && toRUomRatio != null && qty != null && fromUomRatio > 0 && toRUomRatio > 0 && qty > 0){
            totalExpQty = ((qty * fromUomRatio) / toRUomRatio);
            return totalExpQty;
        }
        return 0;
    }
    public double getRoundedValue(double rounding,double qty){
        double factor = Math.pow(10,rounding);
        qty = Optional.ofNullable(qty).orElse(0.0);
        qty =  Math.round(qty * factor) / factor;
        return qty;
    }

}
