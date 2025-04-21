package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ImageCoverTest {

    public static final ImageCover DEFAULT_IMAGE_COVER = new ImageCover(
            1024 * 1024,
            "https://d25nznepghf50.cloudfront.net/practice/105/144/3/337f46dc-0d46-4213-9ffe-3cfbc94c15c2.png",
            600,
            400
    );

    @Test
    @DisplayName("이미지 크기는 1MB 이하일 경우, 정상 등록된다.")
    void validIfimageSizeIsEqualOrLowerThan1MB() {

        assertThatCode(() ->
                new ImageCover(
                        1024 * 1024,
                        "https://d25nznepghf50.cloudfront.net/practice/105/144/3/337f46dc-0d46-4213-9ffe-3cfbc94c15c2.png",
                        300,
                        200
                )).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("이미지 크기가 1MB를 초과할 경우, 에러를 뱉는다.")
    void invalidIfimageSizeIsGreaterThan1MB() {

        assertThatThrownBy(() ->
            new ImageCover(
                    1024 * 1024 + 1,
                    "https://d25nznepghf50.cloudfront.net/practice/105/144/3/337f46dc-0d46-4213-9ffe-3cfbc94c15c2.png",
                    300,
                    200
            ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미지 크기는 1MB를 초과할 수 없습니다.");
    }

    @Test
    @DisplayName("이미지 타입은 gif, jpg(jpeg), png, svg 유형인 경우에만 정상 등록된다.")
    void validIfimageTypeIsInRightType() {

        assertThatCode(() ->
                        new ImageCover(
                                1024 * 1024,
                                "https://d25nznepghf50.cloudfront.net/practice/105/144/3/337f46dc-0d46-4213-9ffe-3cfbc94c15c2.png",
                                300,
                                200
                        )
                ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("이미지 타입이 gif, jpg(jpeg), png, svg 유형이 아닐 경우 에러를 뱉는다.")
    void invalidIfimageTypeIsWrongType() {

        assertThatThrownBy(() ->
                        new ImageCover(
                                1024 * 1024,
                                "https://d25nznepghf50.cloudfront.net/practice/105/144/3/337f46dc-0d46-4213-9ffe-3cfbc94c15c2.xlsx",
                                300,
                                200
                        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미지 타입은 gif, jpg(jpeg), png, svg 중 하나여야 합니다.");
    }

    @Test
    @DisplayName("이미지 해상도 조건이 올바른 경우")
    void validIfimagePxIsRight() {

        assertThatCode(() ->
                new ImageCover(
                        1024 * 1024,
                        "https://d25nznepghf50.cloudfront.net/practice/105/144/3/337f46dc-0d46-4213-9ffe-3cfbc94c15c2.png",
                        300,
                        200
                )).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("이미지 해상도 조건이 틀린 경우")
    void invalidIfimagePxIsWrong() {

        assertThatThrownBy(() ->
                new ImageCover(
                        1024 * 1024,
                        "https://d25nznepghf50.cloudfront.net/practice/105/144/3/337f46dc-0d46-4213-9ffe-3cfbc94c15c2.xlsx",
                        350,
                        200
                ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("width, height 비율이 3:2여야 합니다.");

        assertThatThrownBy(() ->
                new ImageCover(
                        1024 * 1024,
                        "https://d25nznepghf50.cloudfront.net/practice/105/144/3/337f46dc-0d46-4213-9ffe-3cfbc94c15c2.xlsx",
                        100,
                        150
                ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("width, height는 각각 300, 200픽셀 이상이어야 합니다.");
    }
}
