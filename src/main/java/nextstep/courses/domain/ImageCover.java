package nextstep.courses.domain;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ImageCover {

    private static final int MB = 1024 * 1024;

    private static final int MIN_WIDTH = 300;

    private static final int MIN_HEIGHT = 200;

    private static final int WIDTH_RATIO_NUMERATOR = 3;

    private static final int HEIGHT_RATIO_DENOMINATOR = 2;

    private final int byteSize; // validation logic

    private final ImageType type;

    private final int width;

    private final int height;

    public ImageCover(int byteSize, String url, int width, int height) {
        validateSize(byteSize);
        validatePixel(width, height);
        this.type = getImageType(url);
        this.byteSize = byteSize;
        this.width = width;
        this.height = height;
    }

    private void validatePixel(int width, int height) {
        validateMinCondition(width, height);
        validateRatioCondition(width, height);
    }

    private void validateRatioCondition(int width, int height) {
        if (WIDTH_RATIO_NUMERATOR * height != HEIGHT_RATIO_DENOMINATOR * width) {
            throw new IllegalArgumentException("width, height 비율이 3:2여야 합니다.");
        }
    }

    private void validateMinCondition(int width, int height) {
        if (width < MIN_WIDTH || height < MIN_HEIGHT) {
            throw new IllegalArgumentException("width, height는 각각 300, 200픽셀 이상이어야 합니다.");
        }
    }

    private void validateSize(int byteSize) {
        if (byteSize > MB) {
            throw new IllegalArgumentException("이미지 크기는 1MB를 초과할 수 없습니다.");
        }
    }

    private ImageType getImageType(String url) {
        if (url == null || !url.contains(".")) {
            throw new IllegalArgumentException("Invalid image type: " + url);
        }

        int lastSlashIndex = Math.max(url.lastIndexOf('/'), url.lastIndexOf('\\'));
        String fileName = url.substring(lastSlashIndex + 1);

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            String extension = fileName.substring(dotIndex + 1).toUpperCase();

            Set<String> allowedTypes = Arrays.stream(ImageType.values())
                    .map(Enum::name)
                    .collect(Collectors.toSet());

            if (!allowedTypes.contains(extension)) {
                throw new IllegalArgumentException("이미지 타입은 gif, jpg(jpeg), png, svg 중 하나여야 합니다.");
            }

            return ImageType.valueOf(extension);
        }

        throw new IllegalArgumentException("Invalid image type: " + url);
    }
}
