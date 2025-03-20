package com.a702.finafanbe.core.auth.entity.infrastructure;

import com.a702.finafanbe.core.auth.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
