package com.raukhvarger.ms.webfs.service;

import com.raukhvarger.ms.webfs.entity.FileEntity;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface FilesService {

    List<FileEntity> getFilesByFolder(Path path);

    List<FileEntity> getFoldersByFolder(Path path);

}
