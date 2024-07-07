package com.project.reservationservice.service;

import com.project.reservationservice.domain.MemberEntity;
import com.project.reservationservice.domain.Store;
import com.project.reservationservice.DTO.StoreDTO;
import com.project.reservationservice.repository.MemberRepository;
import com.project.reservationservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                    log.error("사용자를 찾을 수 없음: {}", email);
                    return new RuntimeException("Member not found");
                });
        log.info("사용자 확인 완료: {}, 역할: {}", member.getEmail(), member.getRole());

        if (member.getRole() != MemberEntity.MemberRole.ROLE_PARTNER) {
            log.error("권한 없음: {}, 현재 역할: {}", email, member.getRole());
            throw new RuntimeException("Only partners can create stores");
        }

        Store store = Store.builder()
                .storeName(storeDTO.getStoreName())
                .storeDesc(storeDTO.getStoreDesc())
                .storeAddress(storeDTO.getStoreAddress())
                .category(storeDTO.getCategory())
                .openTime(storeDTO.getOpenTime())
                .closeTime(storeDTO.getCloseTime())
                .phoneNumber(storeDTO.getPhoneNumber())
                .amenities(storeDTO.getAmenities())
                .build();
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

    private StoreDTO convertToDTO(Store store) {
        return StoreDTO.builder()
                .id(store.getId())
                .storeName(store.getStoreName())
                .storeDesc(store.getStoreDesc())
                .storeAddress(store.getStoreAddress())
                .category(store.getCategory())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .phoneNumber(store.getPhoneNumber())
                .amenities(store.getAmenities())
                .createdAt(store.getCreatedAt())
                .updatedAt(store.getUpdatedAt())
                .kioskId(store.getKioskId())
                .build();
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