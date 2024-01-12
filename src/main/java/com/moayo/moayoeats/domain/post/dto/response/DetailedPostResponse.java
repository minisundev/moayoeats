package com.moayo.moayoeats.domain.post.dto.response;

import com.moayo.moayoeats.domain.menu.dto.response.NickMenusResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record DetailedPostResponse (
    String address,
    String store,
    Integer minPrice,
    Integer deliveryCost,
    List<NickMenusResponse> menus,
    Integer sumPrice,
    LocalDateTime deadline
){
}
