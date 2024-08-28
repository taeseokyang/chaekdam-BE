package eggis0.baram.domain.voice.repository;

import eggis0.baram.domain.voice.domain.Voice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface VoiceRepository extends JpaRepository<Voice, Long> {
    List<Voice> findAllByOrderByVoiceIdDesc();

}