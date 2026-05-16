package com.ecommerce.inventoryservice.service.impl;

import com.ecommerce.inventoryservice.dto.InventoryRequestDTO;
import com.ecommerce.inventoryservice.dto.InventoryResponseDTO;
import com.ecommerce.inventoryservice.exception.ResourceNotFoundException;
import com.ecommerce.inventoryservice.mapper.InventoryMapper;
import com.ecommerce.inventoryservice.model.Inventory;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import com.ecommerce.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(String sku, Integer quantity) {
        return inventoryRepository.findBySku(sku)
                .map(inventory -> inventory.getQuantity() >= quantity)
                .orElse(false);
    }

    @Override
    public InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequestDTO) {
        boolean exist = inventoryRepository.existsBySku(inventoryRequestDTO.getSku());
        if (exist) {
            throw new RuntimeException("El inventario para el SKU " + inventoryRequestDTO.getSku() + " ya existe");
        }

        Inventory inventory = inventoryMapper.toModel(inventoryRequestDTO);
        Inventory inventorySaved = inventoryRepository.save(inventory);

        log.info("Inventario creado para el SKU: {}", inventorySaved.getSku());

        return inventoryMapper.toResponse(inventorySaved);
    }

    @Override
    @Transactional
    public List<InventoryResponseDTO> getAllInventory() {
        return this.inventoryRepository.findAll().stream()
                .map(this.inventoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO inventoryRequestDTO) {
        Inventory inventory = this.inventoryRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Inventario", "id", id)
                );

        inventory.setSku(inventoryRequestDTO.getSku());
        inventory.setQuantity(inventoryRequestDTO.getQuantity());

        Inventory inventoryUpdate = this.inventoryRepository.save(inventory);

        log.info("Inventario actualizado para el ID: {}", id);

        return this.inventoryMapper.toResponse(inventoryUpdate);
    }

    @Override
    @Transactional
    public void deleteInventoryById(Long id) {
        if (inventoryRepository.existsById(id))
            throw new ResourceNotFoundException("Inventario", "id", id);

        this.inventoryRepository.deleteById(id);
        log.info("Inventario eliminado para el ID: {}", id);
    }
}
