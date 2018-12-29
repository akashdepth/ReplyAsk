package com.bs.social;

import com.bs.social.models.Answer;
import com.bs.social.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AnswerRepository extends CrudRepository<Answer, Integer> {
    Answer findFirstByQuestionIdOrderByTimestampDesc(Long id);
    List<Answer> findByQuestionId(Long id);
}