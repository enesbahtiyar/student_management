package com.project.schoolmanagement.service.business;

import com.project.schoolmanagement.repository.business.StudentInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentInfoService
{
    private final StudentInfoRepository studentInfoRepository;
}
