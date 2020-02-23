package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.entity.FileEntity;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

import java.util.function.Supplier;

public interface UIEvents {

    ComponentEventListener getLogoutClickEvent(LoginOverlay login);

    ComponentEventListener getOpenFolderEvent(Supplier<String> getValue);

    ComponentEventListener getUploadFileEvent(MemoryBuffer buffer);

    ComponentEventListener getFolderClickEvent();

}
