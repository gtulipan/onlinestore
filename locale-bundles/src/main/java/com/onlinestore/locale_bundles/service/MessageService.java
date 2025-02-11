package com.onlinestore.locale_bundles.service;

import org.springframework.core.io.Resource;

public interface MessageService {
    public Resource getMessagesByLanguage(String lang);
}
