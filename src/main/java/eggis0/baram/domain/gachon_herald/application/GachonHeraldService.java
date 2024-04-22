package eggis0.baram.domain.gachon_herald.application;

import eggis0.baram.domain.gachon_herald.domain.GachonHerald;
import eggis0.baram.domain.gachon_herald.dto.GachonHeraldRequest;
import eggis0.baram.domain.gachon_herald.repository.GachonHeraldRepository;
import eggis0.baram.domain.user.domain.User;
import eggis0.baram.domain.user.exception.UserNotFountException;
import eggis0.baram.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GachonHeraldService {

    private final UserRepository userRepository;
    private final GachonHeraldRepository gachonHeraldRepository;

    public void save(GachonHeraldRequest request, String userId) throws Exception {
        if (!userRepository.existsUserByUserId(userId)) {
            throw new UserNotFountException();
        }
        User user = userRepository.findByUserId(userId).get();
        GachonHerald gachonHerald = GachonHerald.builder()
                .phoneNumber(user.getPhone())
                .opinion(request.getOpinion())
                .build();

        gachonHeraldRepository.save(gachonHerald);
    }
}