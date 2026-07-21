package com.upendra.portfolio.port.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.port.dto.response.AdminDashboardResponse;
import com.upendra.portfolio.port.service.AdminDashboardService;


import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class AdminDashboardController {


    private final AdminDashboardService adminDashboardService;



    @GetMapping("/admin/dashboard")
    public ApiResponse<AdminDashboardResponse> dashboard(){


        return ApiResponse.<AdminDashboardResponse>builder()

                .success(true)

                .message(
                    "Dashboard data fetched successfully"
                )

                .data(
                    adminDashboardService.getDashboard()
                )

                .build();

    }

}