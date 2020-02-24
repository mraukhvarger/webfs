package com.raukhvarger.ms.webfs.service;

import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewerItem;

import java.nio.file.Path;
import java.util.List;

public interface FilesService {

    List<FileViewerItem> getFilesByFolder(Path path);

    List<FileViewerItem> getFoldersByFolder(Path path);

    FileViewerItem getFileViewer(Path path);

}
