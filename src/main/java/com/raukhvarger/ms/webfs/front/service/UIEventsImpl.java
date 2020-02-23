package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.entity.FileEntity;
import com.raukhvarger.ms.webfs.front.spring.CustomRequestCache;
import com.raukhvarger.ms.webfs.service.FilesService;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.bouncycastle.util.io.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

@Service
@Scope("session")
public class UIEventsImpl implements UIEvents {

    private final Logger logger = LoggerFactory.getLogger(UIEventsImpl.class);


    private final AuthenticationManager authenticationManager;
    private final CustomRequestCache requestCache;
    private final UIControls uiControls;
    private final DataProviders dataProviders;
    private final FilesService filesService;

    public UIEventsImpl(AuthenticationManager authenticationManager,
                        CustomRequestCache requestCache,
                        UIControls uiControls,
                        DataProviders dataProviders,
                        FilesService filesService) {
        this.authenticationManager = authenticationManager;
        this.requestCache = requestCache;
        this.uiControls = uiControls;
        this.dataProviders = dataProviders;
        this.filesService = filesService;
    }

    @Override
    public ComponentEventListener<AbstractLogin.LoginEvent> getLogoutClickEvent(LoginOverlay login) {
        return e -> {
            try {
                final Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(e.getUsername(), e.getPassword()));

                if (authentication != null) {
                    login.close();
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    UI.getCurrent().navigate(requestCache.resolveRedirectUrl());
                }

            } catch (AuthenticationException ex) {
                login.setError(true);
            }
        };
    }

    @Override
    public ComponentEventListener getOpenFolderEvent(Supplier<String> getValue) {
        return e -> {
            uiControls.openFolder(new File(getValue.get()).toPath());
        };
    }

    @Override
    public ComponentEventListener getUploadFileEvent(MemoryBuffer buffer) {
        return e -> {
            logger.info(buffer.getFileName());
            try {
                logger.info(new String(Streams.readAll(buffer.getInputStream())));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }

    @Override
    public ComponentEventListener<ItemClickEvent<FileEntity>> getFolderClickEvent() {
        return e -> {
            // todo -> UIEvents
            dataProviders.updateFilesInFolderData(filesService.getFilesByFolder(e.getItem().getPath()));
        };
    }

}
