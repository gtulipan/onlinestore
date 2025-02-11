package com.onlinestore.locale_bundles.service;

import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

public interface MessageService {
    Mono<Resource> getMessagesByLanguage(String lang);
}
