package com.raukhvarger.ms.webfs.front.service;

import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewerExtensionsFactory;
import com.raukhvarger.ms.webfs.front.view.fileviewer.ext.ViewerExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileViewerExtensionsImpl implements FileViewerExtensions {

    private final FileViewerExtensionsFactory fileViewerExtensionsFactory;

    @Autowired
    public FileViewerExtensionsImpl(FileViewerExtensionsFactory fileViewerExtensionsFactory) {
        this.fileViewerExtensionsFactory = fileViewerExtensionsFactory;
    }

    @Override
    public Optional<ViewerExtension> createExtension(String ext) {
        try {
            return Optional.of(fileViewerExtensionsFactory.createViewer(ext));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
