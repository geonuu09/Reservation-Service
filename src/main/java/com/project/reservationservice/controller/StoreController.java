package com.project.reservationservice.controller;

import com.project.reservationservice.DTO.StoreDTO;
import com.project.reservationservice.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    /**
     * 매장 생성 API
     * @param storeDTO 매장 생성에 필요한 정보
     * @return 생성된 매장 정보와 HTTP 상태 코드 201 (CREATED)
     */
    @PostMapping
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) {
        StoreDTO createdStore = storeService.createStore(storeDTO);
        return new ResponseEntity<>(createdStore, HttpStatus.CREATED);
    }

    /**
     * 매장 상세 정보 조회 API
     * @param id 조회할 매장의 ID
     * @return 매장 상세 정보와 HTTP 상태 코드 200 (OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> getStore(@PathVariable Long id) {
        StoreDTO store = storeService.getStore(id);
        return ResponseEntity.ok(store);
    }

    /**
     * 매장 이름으로 검색 API
     *
     * @param name 검색할 매장 이름
     * @return 검색된 매장 목록과 HTTP 상태 코드 200 (OK)
     */
//    @PreAuthorize("hasRole('USER')")
    @GetMapping("/search")
    public ResponseEntity<List<StoreDTO>> searchStoresByName(@RequestParam(name = "name") String name) {
        List<StoreDTO> stores = storeService.searchStoresByName(name);
        return ResponseEntity.ok(stores);
    }
    /**
     * 매장 수정 API
     * @param id 수정할 매장의 ID
     * @param storeDTO 매장 수정에 필요한 정보
     * @return 수정된 매장 정보와 HTTP 상태 코드 200 (OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<StoreDTO> updateStore(@PathVariable Long id, @RequestBody StoreDTO storeDTO) {
        StoreDTO updatedStore = storeService.updateStore(id, storeDTO);
        return ResponseEntity.ok(updatedStore);
    }

    /**
     * 매장 삭제 API
     * @param id 삭제할 매장의 ID
     * @return HTTP 상태 코드 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }


}