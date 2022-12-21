package co.g2academy.tokobagus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemFulfillmentDto {
    private Integer id;
    private Integer productId;
    private Integer storeFrontOrderItemId;
    private String productName;
    private Integer price;
    private Integer quantity;
    private OrderFulfillmentDto order;
}
