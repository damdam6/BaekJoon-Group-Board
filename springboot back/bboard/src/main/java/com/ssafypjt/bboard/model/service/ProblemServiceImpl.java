package com.ssafypjt.bboard.model.service;

import ch.qos.logback.core.subst.Node;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.RecomProblem;
import com.ssafypjt.bboard.model.dto.User;
import com.ssafypjt.bboard.model.repository.GroupRepository;
import com.ssafypjt.bboard.model.repository.ProblemRepository;
import com.ssafypjt.bboard.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    private ProblemRepository problemRepository;
    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private RestTemplate restTemplate;


    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository, UserRepository userRepository, GroupRepository groupRepository, RestTemplate restTemplate) {
        this.problemRepository = problemRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Problem> getAllProblems() {
        return null;
    }

    @Override
    public Problem getProblem(int id) {
        return null;
    }

    @Override
    public List<Problem> getTierProblems(int tier, int groupId) {
        return null;
    }

    @Override
    public List<RecomProblem> getRecomProblems(int groupId) {
        return null;
    }

    @Override
    public List<String> getProblemAlgorithm(int problemNum) {
        return null;
    }

    // 미완성
    @Override
    public int resetProblems() {

        // 기존 테이블 삭제
        problemRepository.deleteAllProblems();
        problemRepository.deleteAllAlgorithms();

        // 전체 유저 목록
        List<User> userList = userRepository.selectAllUser();
        int count = 0;
        for (User user : userList) {
            try {
                // Request Header 설정
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // ObjectMapper 생성
                ObjectMapper objectMapper = new ObjectMapper();
                // JSON 객체 생성
                ObjectNode requestBody = objectMapper.createObjectNode();

                // Request Entity 생성
                HttpEntity entity = new HttpEntity(requestBody.toString(), headers);

                // API 호출
                // 페이지 1개만 받아옴 (원래 2개 박아와야함)
                String url = "https://solved.ac/api/v3/search/problem?query=@" + user.getUserName() + "&sort=level&direction=desc&page=1";
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

                // 응답의 Content-Type을 확인하고, UTF-8로 디코딩하여 출력
                HttpHeaders responseHeaders = response.getHeaders();
                MediaType contentType = responseHeaders.getContentType();
                Charset charset = contentType.getCharset();
                System.out.println(responseHeaders);
                System.out.println(contentType);
                System.out.println(charset);
                System.out.println(response.getBody());

                try {
                    JsonNode itemsNode = objectMapper.readTree(response.getBody()).get("items");
                    System.out.println(itemsNode.getClass());
                    System.out.println(itemsNode);

                    for (JsonNode node : itemsNode) {
                        Problem problem = objectMapper.convertValue(node, Problem.class);
                        // 알고리즘 가져오는 코드 (따로 빼자)
                        JsonNode algorithmNode = node.get("tags");
                        StringBuilder algorithm = new StringBuilder();
                        for (JsonNode aNode : algorithmNode) {
                            algorithm.append(aNode.get("key").asText()).append(" ");
                        }
                        // 여기까지
                        problemRepository.insertAlgorithm(problem.getProblemNum(), algorithm.toString()); // 알고리즘 테이블 삽입

                        problem.setUserId(user.getUserId());
                        System.out.println(problem);
                        count += problemRepository.insertProblem(problem);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                System.out.println("error");
            }
        }

        return count;
    }
}
