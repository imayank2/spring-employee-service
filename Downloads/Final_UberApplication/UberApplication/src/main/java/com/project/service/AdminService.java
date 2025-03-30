package com.project.service;

import com.project.dto.DriverDto;
import com.project.dto.UserDto;

public interface AdminService {
    UserDto getAllDetails(Long userId);

    DriverDto getUpdateStatus(Long userId);
}
