package com.ssafypjt.bboard.model.service;

import ch.qos.logback.core.subst.Node;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
    private ObjectMapper objectMapper = new ObjectMapper(); // API 가져올 때 필요한 object Mapper 공통으로 사용


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
        int count = 0; // 변경된 라인 수
        for (User user : userList) {
            try {
                ArrayNode itemsNode = getUserProblemByAPI(user);
                for (JsonNode itemNode: itemsNode) {
                    for (JsonNode node: itemNode) {
                        count += getAlgorithmFromJson(node, user);
                    }
                }
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
        return count;
    }


    private int getAlgorithmFromJson(JsonNode itemNode, User user){
        int count = 0;
        try {
                Problem problem = objectMapper.convertValue(itemNode, Problem.class);
                // 알고리즘 가져오는 코드 (따로 빼자)
                JsonNode algorithmNode = itemNode.get("tags");
                StringBuilder algorithm = new StringBuilder();
                for (JsonNode aNode : algorithmNode) {
                    algorithm.append(aNode.get("key").asText()).append(" ");
                }
                // 여기까지
                problemRepository.insertAlgorithm(problem.getProblemNum(), algorithm.toString()); // 알고리즘 테이블 삽입
                problem.setUserId(user.getUserId());
                count += problemRepository.insertProblem(problem);

        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("알고리즘을 가져오는 과정에서 에러가 발생하였습니다.");
        }
        return count;
    }


    private ArrayNode getUserProblemByAPI(User user){
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode itemsNode = objectMapper.createArrayNode();
        itemsNode.add(getProblemJsonByAPIWithPage(user, 1));
        itemsNode.add(getProblemJsonByAPIWithPage(user, 2));
        return itemsNode;
    }

    private JsonNode getProblemJsonByAPIWithPage(User user, int page){
        String url = getProblemJsonUrl(user, page);
        System.out.println(url);
        return getJsonByAPI(url);
    }

    private String getProblemJsonUrl(User user, int page){
        return "https://solved.ac/api/v3/search/problem?query=@" + user.getUserName() + "&sort=level&direction=desc&page="+page;
    }

    // 공통, util로 따로 뺄까?
    private JsonNode getJsonByAPI(String url){
        // Request Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectNode requestBody = objectMapper.createObjectNode();

        HttpEntity entity = new HttpEntity(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        JsonNode itemsNode = null;
        try {
            itemsNode = objectMapper.readTree(response.getBody()).get("items");
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
            System.out.println("제이슨 데이터를 가져오는데 에러가 발생하였습니다.");
        }
        return itemsNode;
    }

}
