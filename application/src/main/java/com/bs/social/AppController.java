package com.bs.social;

import com.bs.social.models.Answer;
import com.bs.social.models.Question;
import com.bs.social.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.core.io.FileSystemResource;
import java.util.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping(path="/api")
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private  AnswerRepository answerRepository;


    @PostMapping(path = "/register_user")
    public @ResponseBody
    ResponseEntity<Map> addNewUser(@RequestBody User user) {
        Map<String, Object> response = new LinkedHashMap<>();
        user.setTimestamp(System.currentTimeMillis());
        User userData = userRepository.findByMobileNumber(user.getMobileNumber());
        if(userData!=null){
            response.put("id", userData.getId());
            response.put("status", "Already Created user");
        }
        else{

            if(user.getMobileNumber()==null){
                return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
            }

            if(user.getGender()==null){
                user.setGender("Male");
            }

            if(user.getUserImageUrl()==null) {
                user.setUserImageUrl("https://1.img-dpreview.com/files/p/TS1200x900~sample_galleries/4465348876/2284269311.jpg");
            }

            if(user.getLanguage()==null) {
                user.setLanguage("English");
            }

            user.setTimestamp(System.currentTimeMillis());

            userRepository.save(user);

            response.put("id", user.getId());
            response.put("status", "New user creatd");
        }
        return new ResponseEntity<Map>(response, HttpStatus.CREATED);
    }


    @PostMapping(path = "/add_question")
    public @ResponseBody
    ResponseEntity<Map> addQuestion(@RequestBody Question question) {
        Map<String, Object> response = new LinkedHashMap<>();
        question.setTimestamp(System.currentTimeMillis());
        if(question.getUserId() == null) {
            question.setUserId(1L);
        }
        if(question.getLanguage()==null){
            question.setLanguage("English");
        }
        questionRepository.save(question);
        response.put("id", question.getId());
        return new ResponseEntity<Map>(response, HttpStatus.CREATED);
    }


    @PostMapping(path = "/add_answer")
    public @ResponseBody
    ResponseEntity<Map> addAnswer(@RequestBody Answer answer) {
        Map<String, Object> response = new LinkedHashMap<>();
        answer.setTimestamp(System.currentTimeMillis());
        if(answer.getUserId()==null){
            answer.setUserId(1L);
        }
        if(answer.getLanguage()==null){
            answer.setLanguage("English");
        }
        answerRepository.save(answer);
        response.put("id", answer.getId());
        return new ResponseEntity<Map>(response, HttpStatus.CREATED);
    }



    @RequestMapping(value = "/get_questions/{someID}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Map> getQuestions(@PathVariable(value = "someID") Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        List<Question> contentList = questionRepository.findAllByOrderByTimestampDesc();
        List<Map<String, Object> > contentMapList = new ArrayList<>();
        for(Question question: contentList){

            Map<String,Object> contentMap = new LinkedHashMap<>();
            User user = userRepository.findById(question.getUserId());
            if(user==null){
                return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
            }
            Answer answer = answerRepository.findFirstByQuestionIdOrderByTimestampDesc(question.getId());

            if(answer!=null) {
                String ans = answer.getAnswer();
                contentMap.put("answer", ans.substring(0,Math.min(120, ans.length())));
            }else{
                contentMap.put("answer", "No Answer for this question yet.");
            }

            contentMap.put("id", question.getId());
            contentMap.put("question", question.getQuestion());
            contentMap.put("userName", user.getName());
            contentMap.put("userProfile", user.getUserImageUrl());
            contentMap.put("timestamp", question.getTimestamp());

            contentMapList.add(contentMap);
        }
        response.put("data",contentMapList);
        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/get_question/{someID}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Map> getQuestion(@PathVariable(value = "someID") Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        Question question = questionRepository.findById(id);

        Map<String,Object> contentMap = new LinkedHashMap<>();
        User user = userRepository.findById(question.getUserId());
        if(user==null){
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
        contentMap.put("id", question.getId());
        contentMap.put("question", question.getQuestion());
        contentMap.put("userName", user.getName());
        contentMap.put("userProfile", user.getUserImageUrl());
        contentMap.put("timestamp", question.getTimestamp());

        List<Answer> answers = answerRepository.findByQuestionId(question.getId());
        List<Map<String, Object> > answerList = new ArrayList<>();
        for(Answer answer: answers){
            Map<String,Object> answerMap = new LinkedHashMap<>();
            User ansUser = userRepository.findById(answer.getUserId());
            if(ansUser==null){
                return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
            }
            answerMap.put("id", answer.getId());
            answerMap.put("userName", ansUser.getName());
            answerMap.put("userProfile", ansUser.getUserImageUrl());
            answerMap.put("timestamp", answer.getTimestamp());
            answerMap.put("answer", answer.getAnswer());
            answerList.add(answerMap);
        }
        contentMap.put("answers", answerList);
        response.put("data",contentMap);
        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }
    
}