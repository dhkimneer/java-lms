package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageCovers {

    private final List<ImageCover> imageCovers;

    public ImageCovers(List<ImageCover> imageCovers) {
        this.imageCovers = new ArrayList<>(imageCovers);
    }

    public List<ImageCover> getImageCovers() {
        return Collections.unmodifiableList(imageCovers);
    }
}
