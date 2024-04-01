package com.example.storeapi.entity;

import com.example.storeapi.entity.Stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Table(name = "stock_consumption_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockConsumptionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "productCode")
    private Long productCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "quantity_before_update")
    private int quantityBeforeUpdate;

    @Column(name = "quantity_after_update")
    private int quantityAfterUpdate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
