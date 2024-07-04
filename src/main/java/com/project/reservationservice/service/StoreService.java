package com.project.reservationservice.service;

import com.project.reservationservice.domain.MemberEntity;
import com.project.reservationservice.domain.Store;
import com.project.reservationservice.DTO.StoreDTO;
import com.project.reservationservice.repository.MemberRepository;
import com.project.reservationservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public StoreDTO createStore(StoreDTO storeDTO, String email) {
        log.info("매장 생성 서비스 시작: {}, 요청자: {}", storeDTO.getStoreName(), email);

        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("회원을 찾을 수 없음: {}", email);
                    return new RuntimeException("Member not found");
                });

        if (member.getRole() != MemberEntity.MemberRole.ROLE_PARTNER) {
            log.warn("권한 없음: {}", email);
            throw new RuntimeException("Only partners can create stores");
        }

        Store store = convertToEntity(storeDTO);
        store.setOwner(member);
        Store savedStore = storeRepository.save(store);
        log.info("매장 생성 완료: {}", savedStore.getStoreName());
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
                store.getOwner().toString(),
                store.getStoreName(),
                store.getStoreDesc(),
                store.getStoreAddress(),
                store.getCategory(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getPhoneNumber(),
                store.getAmenities(),
                store.getCreatedAt(),
                store.getUpdatedAt(),
                store.getKioskId()
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