package com.tenco.web.announce;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnnounceRepositoryCustom {
    Page<Announce> search(AnnounceRequest.SearchDTO condition, Pageable pageable);
}
