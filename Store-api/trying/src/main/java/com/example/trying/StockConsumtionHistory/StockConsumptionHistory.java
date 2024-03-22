package com.example.trying.StockConsumtionHistory;

import com.example.trying.stock.Stock;
import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "stock_consumption_history")
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

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public LocalDateTime getUpdatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.createdAt = updatedAt;
    }

    @Column(name = "quantity_before_update")
    private int quantityBeforeUpdate;

    @Column(name = "quantity_after_update")
    private int quantityAfterUpdate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getProductCode() {
        return productCode;
    }

    public void setProductCode(Long productCode) {
        this.productCode = productCode;
    }

    public int getQuantityBeforeUpdate() {
        return quantityBeforeUpdate;
    }

    public void setQuantityBeforeUpdate(int quantityBeforeUpdate) {
        this.quantityBeforeUpdate = quantityBeforeUpdate;
    }

    public int getQuantityAfterUpdate() {
        return quantityAfterUpdate;
    }

    public void setQuantityAfterUpdate(int quantityAfterUpdate) {
        this.quantityAfterUpdate = quantityAfterUpdate;
    }

    public LocalDateTime getUpdateTime() {
        return createdAt;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.createdAt = updateTime;
    }
}
