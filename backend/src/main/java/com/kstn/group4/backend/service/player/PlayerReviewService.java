package com.kstn.group4.backend.service.player;

import com.kstn.group4.backend.dto.player.CreatePitchReviewRequest;
import com.kstn.group4.backend.dto.player.PitchReviewResponse;
import com.kstn.group4.backend.entity.Pitch;
import com.kstn.group4.backend.entity.PitchReview;
import com.kstn.group4.backend.entity.User;
import com.kstn.group4.backend.exception.BadRequestException;
import com.kstn.group4.backend.exception.ResourceNotFoundException;
import com.kstn.group4.backend.repository.BookingRepository;
import com.kstn.group4.backend.repository.PitchRepository;
import com.kstn.group4.backend.repository.PitchReviewRepository;
import com.kstn.group4.backend.service.common.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerReviewService {

    private final AuthenticatedUserService authenticatedUserService;
    private final PitchRepository pitchRepository;
    private final BookingRepository bookingRepository;
    private final PitchReviewRepository pitchReviewRepository;

    @Transactional
    public PitchReviewResponse createReview(Integer pitchId, CreatePitchReviewRequest request) {
        User player = authenticatedUserService.getCurrentUser();

        Pitch pitch = pitchRepository.findById(pitchId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sân với id: " + pitchId));

        boolean eligible = bookingRepository.existsCompletedBookingByPlayerAndPitch(player.getId(), pitchId);
        if (!eligible) {
            throw new BadRequestException("Bạn chỉ có thể đánh giá sân sau khi đã hoàn thành booking tại sân này");
        }

        PitchReview review = new PitchReview();
        review.setPitch(pitch);
        review.setPlayer(player);
        review.setRating(request.rating());
        review.setContent(request.content().trim());

        PitchReview saved = pitchReviewRepository.save(review);
        return new PitchReviewResponse(
                saved.getId(),
                saved.getPitch().getId(),
                saved.getPlayer().getId(),
                saved.getPlayer().getUsername(),
                saved.getRating(),
                saved.getContent(),
                saved.getCreatedAt()
        );
    }
}


