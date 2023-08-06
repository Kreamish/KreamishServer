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
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public List<BrandDto> getAllBrand() {
        List<Brand> allBrand = brandRepository.findAll();

        return allBrand.stream()
            .map(BrandDto::of)
            .collect(Collectors.toList());
    }
}
