package com.raukhvarger.ms.webfs.front.view.fileviewer;

import com.raukhvarger.ms.webfs.front.view.fileviewer.ext.ViewerExtension;

public interface FileViewerExtensionsFactory {

    ViewerExtension createViewer(String ext);

}
