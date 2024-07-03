package com.project.reservationservice.service;

import com.project.reservationservice.domain.Store;
import com.project.reservationservice.DTO.StoreDTO;
import com.project.reservationservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreDTO createStore(StoreDTO storeDTO) {
        Store store = convertToEntity(storeDTO);
        Store savedStore = storeRepository.save(store);
        return convertToDTO(savedStore);
    }

    public StoreDTO getStore(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found"));
        return convertToDTO(store);
    }
    public List<StoreDTO> searchStoresByName(String name) {
        List<Store> stores = storeRepository.findByStoreNameContainingIgnoreCase(name);
        return stores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public StoreDTO updateStore(Long id, StoreDTO storeDTO) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found"));
        updateStoreFromDTO(store, storeDTO);
        Store updatedStore = storeRepository.save(store);
        return convertToDTO(updatedStore);
    }

    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }

    private Store convertToEntity(StoreDTO storeDTO) {
        Store store = new Store();
        store.setStoreName(storeDTO.getStoreName());
        store.setStoreDesc(storeDTO.getStoreDesc());
        store.setStoreAddress(storeDTO.getStoreAddress());
        store.setCategory(storeDTO.getCategory());
        store.setPhoneNumber(storeDTO.getPhoneNumber());
        store.setOpenTime(storeDTO.getOpenTime());
        store.setCloseTime(storeDTO.getCloseTime());
        return store;
    }

    private StoreDTO convertToDTO(Store store) {
        return new StoreDTO(
                store.getId(),
                store.getStoreName(),
                store.getStoreDesc(),
                store.getStoreAddress(),
                store.getCategory(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getPhoneNumber(),
                store.getAmenities(),
                store.getCreatedAt(),
                store.getUpdatedAt()
        );
    }

    private void updateStoreFromDTO(Store store, StoreDTO storeDTO) {
        store.setStoreName(storeDTO.getStoreName());
        store.setStoreDesc(storeDTO.getStoreDesc());
        store.setStoreAddress(storeDTO.getStoreAddress());
        store.setCategory(storeDTO.getCategory());
        store.setOpenTime(storeDTO.getOpenTime());
        store.setCloseTime(storeDTO.getCloseTime());
    }
}