package com.raukhvarger.ms.webfs.service.repository;

import com.raukhvarger.ms.webfs.entity.FileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<FileEntity, Long> {



}
