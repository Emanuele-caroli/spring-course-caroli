package com.example.spring_course.service.impl;

import com.example.spring_course.service.dto.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserServiceImpl {
   UserResponseDto getUserById(UUID id);
}
