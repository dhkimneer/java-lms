package nextstep.courses.domain;

import java.util.Optional;

public interface ImageCoverRepository {

    ImageCover save(ImageCover imageCover);

    Optional<ImageCover> findById(Long id);

    Optional<ImageCovers> findBySessionId(Long sessionId);
}