package com.onlinestore.locale_bundles.service.impl;

import com.onlinestore.locale_bundles.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final ResourceLoader resourceLoader;

    @Override
    public Resource getMessagesByLanguage(String lang) {
        //If the i18n is in the multimodule project root, and not in module resources folder
        //Resource resource = resourceLoader.getResource("file:" + System.getenv("PROJECT_ROOT") + "/i18n/messages_" + lang + ".json");
        try {
            return resourceLoader.getResource(String.join("","classpath:i18n/messages_", lang, ".json"));
        }catch(Exception e){
            log.debug("An error occurred while retrieving i18n messages for language {}. The error message is: {}", lang, e.getMessage());
            return new ByteArrayResource(new byte[0]);
        }
    }
}
