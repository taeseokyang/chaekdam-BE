package eggis0.baram.domain.voice.application;

import eggis0.baram.domain.count.application.CountService;
import eggis0.baram.domain.voice.domain.Voice;
import eggis0.baram.domain.voice.dto.req.VoiceRequest;
import eggis0.baram.domain.voice.dto.res.VoicesResponse;
import eggis0.baram.domain.voice.repository.VoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VoiceService {
    private final VoiceRepository voiceRepository;
    private final CountService countService;

    public void save(VoiceRequest request) throws Exception {

        Voice voice = Voice.builder()
                .phoneNumber(request.getPhoneNumber())
                .opinion(request.getOpinion())
                .build();

        voiceRepository.save(voice);
    }

    public List<VoicesResponse> get() {
        List<VoicesResponse> voicesDTO = new ArrayList<>();
        List<Voice> voices = voiceRepository.findAllByOrderByVoiceIdDesc();
        for (Voice voice : voices) {
            VoicesResponse dto = new VoicesResponse();
            dto.setVoiceId(voice.getVoiceId());
            dto.setOpinion(voice.getOpinion());
            voicesDTO.add(dto);
        }
        countService.count("home");
        return voicesDTO;
    }
}