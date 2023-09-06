package com.kreamish.kream.favorite.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberFavoriteListResponseDto {
    List<FavoriteDetailDto> favoriteDetailList;
}
