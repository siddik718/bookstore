package co.library.bookstore.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploaderService {
    String upload(MultipartFile multipartFile);
}
