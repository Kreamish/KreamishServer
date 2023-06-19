package com.kreamish.kream.collection.service;

import com.kreamish.kream.collection.dto.CollectionDto;
import com.kreamish.kream.collection.entity.Collection;
import com.kreamish.kream.collection.repository.CollectionRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;

    @Transactional(readOnly = true)
    public List<CollectionDto> getAllCollections() {
        List<Collection> allCollections = collectionRepository.findAll();

        return allCollections.stream()
            .map(collection -> CollectionDto.of(collection))
            .collect(Collectors.toList());
    }

}
