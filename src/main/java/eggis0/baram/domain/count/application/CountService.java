package eggis0.baram.domain.count.application;

import eggis0.baram.domain.council.domain.Council;
import eggis0.baram.domain.council.exception.CouncilNotFoundException;
import eggis0.baram.domain.council.repository.CouncilRepository;
import eggis0.baram.domain.count.domain.Count;
import eggis0.baram.domain.count.repository.CountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class CountService {
    private final CouncilRepository councilRepository;
    private final CountRepository countRepository;

    public void count(String name) {
        LocalDate today = LocalDate.now();
        if (countRepository.existsByDayAndName(today, name)) {
            Count count = countRepository.findByDayAndName(today, name);
            count.setCount(count.getCount() + 1);
            countRepository.save(count);
            return;
        }
        Count count = Count.builder()
                .name(name)
                .count(1L)
                .day(today)
                .build();
        countRepository.save(count);
    }

    public Long get(Integer id) {
        if (!councilRepository.existsById(id)) {
            throw new CouncilNotFoundException();
        }
        Council council = councilRepository.findById(id).get();

        if (!countRepository.existsByDayAndName(LocalDate.now(), council.getName())) {
            return 0L;
        }
        return countRepository.findByDayAndName(LocalDate.now(), council.getName()).getCount();
    }
}