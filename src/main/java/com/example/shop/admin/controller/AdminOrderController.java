package com.example.shop.admin.controller;

import com.example.shop.admin.dto.AdminOrderListResponse;
import com.example.shop.admin.dto.AdminOrderResponse;
import com.example.shop.admin.dto.OrderDeliveryListRequest;
import com.example.shop.admin.dto.OrderSearchRequest;
import com.example.shop.admin.service.AdminOrderService;
import com.example.shop.common.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Admin-Order")
@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @Operation(summary = "관리자 주문 배송 일괄 처리")
    @PatchMapping("/delivery")
    public ResponseEntity<Void> updateOrderStatusDelivery(@RequestBody @Valid OrderDeliveryListRequest orderDeliveryListRequest) {
        adminOrderService.updateOrderStatusDelivery(orderDeliveryListRequest.getOrderDeliveryRequests());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "관리자 주문 상세 조회")
    @GetMapping("/{orderNumber}")
    public ResponseEntity<AdminOrderResponse> getOrder(@PathVariable String orderNumber) {
        return ResponseEntity.ok(adminOrderService.getOrder(orderNumber));
    }

    @Operation(summary = "관리자 주문 목록 조회")
    @GetMapping
    public ResponseEntity<PageResponse<AdminOrderListResponse>> getOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @ModelAttribute OrderSearchRequest request) {
        return ResponseEntity.ok(adminOrderService.searchOrders(request, page, size));
    }
}
