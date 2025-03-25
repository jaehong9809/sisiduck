package com.a702.finafanbe.global.common.util;

import com.a702.finafanbe.core.s3.exception.S3Exception;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.a702.finafanbe.global.common.exception.ErrorCode.INVALID_FILE_REQUEST;
import static com.a702.finafanbe.global.common.exception.ErrorCode.UNSUPPORTED_EXTENSION;


public class FileUtil {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "png", "jpeg", "gif", "pdf");

    public static String createFileName(MultipartFile file) {
        String extension = validateExtension(file.getOriginalFilename());
        return generateRandomFileName(extension);
    }

    public static String createPdfFileName() {
        return generateRandomFileName("pdf");
    }

    private static String generateRandomFileName(String extension) {
        return UUID.randomUUID() + "_" + System.currentTimeMillis() + "." + extension;
    }

    public static String validateExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new S3Exception.S3UploadException(INVALID_FILE_REQUEST);
        }

        String extension = getFileExtension(fileName);

        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new S3Exception.S3UploadException(UNSUPPORTED_EXTENSION);
        }

        return extension;
    }

    private static String getFileExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) {
            return ""; // 확장자가 없으면 빈 문자열 반환
        }

        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

}
