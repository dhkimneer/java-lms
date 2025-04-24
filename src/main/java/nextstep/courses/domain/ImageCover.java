package nextstep.courses.domain;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ImageCover {

    private Long id;

    private static final int MB = 1024 * 1024;

    private static final int MIN_WIDTH = 300;

    private static final int MIN_HEIGHT = 200;

    private static final int WIDTH_RATIO_NUMERATOR = 3;

    private static final int HEIGHT_RATIO_DENOMINATOR = 2;

    private final String url;

    private final int size;

    private final ImageType type;

    private final int width;

    private final int height;

    private final Long sessionId;

    public ImageCover(Long id, int size, String url, int width, int height, Long sessionId) {
        validateSize(size);
        validatePixel(width, height);
        this.type = getImageType(url);
        this.url = url;
        this.id = id;
        this.size = size;
        this.width = width;
        this.height = height;
        this.sessionId = sessionId;
    }

    public ImageCover(int size, String url, int width, int height, Long sessionId) {
        this(null, size, url, width, height, sessionId);
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

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getSize() {
        return size;
    }

    public ImageType getType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Long getSessionId() {
        return sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageCover that = (ImageCover) o;
        return size == that.size && width == that.width && height == that.height && Objects.equals(id, that.id) && Objects.equals(url, that.url) && type == that.type && Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, size, type, width, height, sessionId);
    }
}
