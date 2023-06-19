package com.kreamish.kream.brand.service;

import com.kreamish.kream.brand.dto.BrandDto;
import com.kreamish.kream.brand.entity.Brand;
import com.kreamish.kream.brand.repository.BrandRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    @Transactional(readOnly = true)
    public List<BrandDto> getAllBrand() {
        List<Brand> allBrand = brandRepository.findAll();

        return allBrand.stream()
            .map(brand -> BrandDto.of(brand))
            .collect(Collectors.toList());
    }
}
