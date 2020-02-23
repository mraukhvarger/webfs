package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.entity.FileEntity;
import com.raukhvarger.ms.webfs.front.model.MainFormModel;
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
import java.nio.file.Files;
import java.nio.file.Path;
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
    public ComponentEventListener getOpenFolderParentEvent(Supplier<String> getValue) {
        return e -> {
            uiControls.openFolder(new File(getValue.get()).toPath().getParent());
        };
    }

    @Override
    public ComponentEventListener getUploadFileEvent(MemoryBuffer buffer) {
        return e -> {
            Path currentFolder = dataProviders.getCurrentFolder();
            logger.info(String.format("Upload '%s' to '%s'", buffer.getFileName(), currentFolder.toString()));
            try {
                Files.write(currentFolder.resolve(buffer.getFileName()), Streams.readAll(buffer.getInputStream()));
            } catch (IOException ex) {
                logger.error("Upload file '%s' is failed", ex);
            }
            dataProviders.updateFilesInFolderData();
        };
    }

    @Override
    public ComponentEventListener<ItemClickEvent<FileEntity>> getFolderClickEvent() {
        return e -> {
            dataProviders.updateFilesInFolderData(filesService.getFilesByFolder(e.getItem().getPath()));
            dataProviders.getMainFormBinder().readBean(MainFormModel.builder().pathFieldValue(e.getItem().getPath().toAbsolutePath().toString()).build());
        };
    }

}
