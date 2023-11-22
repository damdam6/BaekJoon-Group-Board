package com.ssafypjt.bboard.model.repository.sqlprovider;


import com.ssafypjt.bboard.model.domain.solvedacAPI.ProblemAndAlgoObjectDomain;
import com.ssafypjt.bboard.model.dto.ProblemAlgorithm;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;


// 어노테이션 지저분해서 따로 뺄지 고민
// 추후 코드 리팩토링할 시간이 있으면 정리해볼께용
// 현재는 안씀
public class AlgorithmInsertSqlProvider {
    public String insertAlgorithms(Map<String, List<ProblemAlgorithm>> paramMap) {
        List<ProblemAlgorithm> list = paramMap.get("list");
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT IGNORE INTO problem_algorithm (problem_num, algorithm) VALUES ");
        sql.append("<foreach collection='list' item='item' separator=','>");
        sql.append("(#{item.problemNum}, #{item.algorithm})");
        sql.append("</foreach>");
        return sql.toString();
    }
}


