package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.front.view.fileviewer.ext.ViewerExtension;

import java.util.Optional;

public interface FileViewerExtensions {

    Optional<ViewerExtension> createExtension(String ext);

}
