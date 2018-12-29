package com.bs.social;

import com.bs.social.models.Question;
import com.bs.social.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface QuestionRepository extends CrudRepository<Question, Integer> {
    List<Question> findAllByOrderByTimestampDesc();
    Question findById(Long id);


}