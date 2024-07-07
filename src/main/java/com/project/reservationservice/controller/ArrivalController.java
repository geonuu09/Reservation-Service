package com.project.reservationservice.controller;

import com.project.reservationservice.service.ArrivalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/arrival")
@RequiredArgsConstructor
@Tag(name = "Kiosk", description = "키오스크 API")
public class ArrivalController {

    private final ArrivalService arrivalService;

    /**
     * 키오스크에서 고객 도착을 확인하는 엔드포인트
     * POST /api/arrival/confirm?kioskId={kioskId}&reservationId={reservationId}
     *
     * @param kioskId 키오스크 ID (매장 ID와 동일)
     * @param reservationId 예약 ID
     * @return 도착 확인 성공 여부에 대한 응답
     */
    @Operation(
            summary = "Arrived CheckIn",
            description = "매장도착 체크인 (키오스크)"
    )
    @PostMapping("/confirm")
    public ResponseEntity<String> confirmArrival(
            @RequestParam Long kioskId,
            @RequestParam Long reservationId) {
        try {
            // ArrivalService를 통해 도착 확인 로직 실행
            boolean confirmed = arrivalService.comfirmArrival(kioskId, reservationId);

            if (confirmed) {
                // 도착 확인 성공 시 200 OK 응답
                return ResponseEntity.ok("Arrival confirmed successfully");
            } else {
                // 도착 확인 실패 시 400 Bad Request 응답
                return ResponseEntity.badRequest().body("Failed to confirm arrival");
            }
        } catch (RuntimeException e) {
            // 예외 발생 시 400 Bad Request 응답과 함께 에러 메시지 반환
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}