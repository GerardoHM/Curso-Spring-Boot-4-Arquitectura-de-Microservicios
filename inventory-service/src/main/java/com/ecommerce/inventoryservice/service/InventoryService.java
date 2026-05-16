package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.dto.InventoryRequestDTO;
import com.ecommerce.inventoryservice.dto.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {
    boolean isInStock(String sku, Integer quantity);
    InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequestDTO);
    List<InventoryResponseDTO> getAllInventory();
    InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO inventoryRequestDTO);
    void deleteInventoryById(Long id);
}
