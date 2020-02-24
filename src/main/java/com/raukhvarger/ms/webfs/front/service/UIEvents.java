package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewerItem;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

import java.util.function.Supplier;

public interface UIEvents {

    ComponentEventListener getLogoutClickEvent(LoginOverlay login);

    ComponentEventListener getOpenFolderEvent(Supplier<String> getValue);

    ComponentEventListener getOpenFolderParentEvent(Supplier<String> getValue);

    ComponentEventListener getUploadFileEvent(MemoryBuffer buffer);

    ComponentEventListener getFolderClickEvent();

    ComponentEventListener getCreateFolderClickEvent();

    ComponentEventListener getCreateFileClickEvent();

    ComponentEventListener getOpenFileWithViewerEvent(FileViewerItem fileItem);

}
