package com.example.trying.stock;
import com.example.trying.StockConsumtionHistory.StockConsumptionHistory;
import com.example.trying.dto.StockDTO;
import com.example.trying.mapper.StockMapper;
import com.example.trying.store.Store;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private Long productCode;

    @JsonIgnoreProperties("store")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    private List<StockConsumptionHistory> updateHistory;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public StockDTO toStockDTO() {
        StockMapper mapper = Mappers.getMapper(StockMapper.class);
        return mapper.toStockDTO(this);
    }
}
