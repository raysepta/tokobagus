package co.g2academy.tokobagus.dto;

import co.g2academy.tokobagus.model.OrderItemFulfillment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderFulfillmentDto {
    private Integer id;
    private Integer userId;
    private Integer storeFrontOrderId;
    private Date orderDate;
    private Date trxDate;
    private String status;
    private Integer totalPrice;
    private Integer totalQuantity;
    private List<OrderItemFulfillmentDto> items;
}
