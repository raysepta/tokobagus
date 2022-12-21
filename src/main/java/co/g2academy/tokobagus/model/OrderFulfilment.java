package co.g2academy.tokobagus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_order_fulfillment")
public class OrderFulfilment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "id_user", nullable = false)
    private Integer userId;
    @Column(name = "id_order", nullable = false)
    private Integer orderId;
    @Column(name = "order_date", nullable = false)
    private Date orderDate = new Date();
    @Column(name = "trx_date", nullable = false)
    private Date trxDate;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;
    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;
    @OneToMany(mappedBy = "orderFulfillment", cascade = CascadeType.ALL)
    private List<OrderItemFulfillment> orderItemFulfillmentList;

}
