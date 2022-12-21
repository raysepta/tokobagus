package co.g2academy.tokobagus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_order_item_fulfillment")
public class OrderItemFulfillment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "id_product", nullable = false)
    private Integer productId;
    @Column(name = "id_order_item", nullable = false)
    private Integer orderItemId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "price", nullable = false)
    private Integer price;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "id_order_fulfillment", nullable = false)
    private OrderFulfilment orderFulfillment;

}
