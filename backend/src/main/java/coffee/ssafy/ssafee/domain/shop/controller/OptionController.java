package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionResponse;
import coffee.ssafy.ssafee.domain.shop.service.OptionService;
import coffee.ssafy.ssafee.domain.user.service.ManagerService;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shops/{shop_id}/option-categories/{option_category_id}/options")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;
    private final ManagerService managerService;

    @GetMapping
    @Operation(summary = "옵션 목록 조회")
    public ResponseEntity<List<OptionResponse>> getOptionsByCategory(@PathVariable("shop_id") Long shopId,
                                                                     @PathVariable("option_category_id") Long optionCategoryId) {
        return ResponseEntity.ok().body(optionService.getOptionsByCategory(shopId, optionCategoryId));
    }

    @PostMapping
    @Operation(summary = "옵션 생성", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> createOption(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                             @PathVariable("shop_id") Long shopId,
                                             @PathVariable("option_category_id") Long optionCategoryId,
                                             @RequestBody OptionRequest optionRequest) {
        managerService.validateShop(principal, shopId);
        Long optionId = optionService.createOption(shopId, optionCategoryId, optionRequest);
        URI location = URI.create("/api/v1/shops/" + shopId + "/option-categories/" + optionCategoryId + "/options/" + optionId);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{option_id}")
    @Operation(summary = "옵션 수정", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> updateOption(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                             @PathVariable("shop_id") Long shopId,
                                             @PathVariable("option_category_id") Long optionCategoryId,
                                             @PathVariable("option_id") Long id,
                                             @RequestBody OptionRequest optionRequest) {
        managerService.validateShop(principal, shopId);
        optionService.updateOption(id, shopId, optionCategoryId, optionRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{option_id}")
    @Operation(summary = "옵션 삭제", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> deleteOption(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                             @PathVariable("shop_id") Long shopId,
                                             @PathVariable("option_category_id") Long optionCategoryId,
                                             @PathVariable("option_id") Long id) {
        managerService.validateShop(principal, shopId);
        optionService.deleteOption(id, shopId, optionCategoryId);
        return ResponseEntity.noContent().build();
    }

}
