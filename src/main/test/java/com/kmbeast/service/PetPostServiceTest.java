package com.kmbeast.service;

import com.kmbeast.context.LocalThreadHolder;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetPostQueryDto;
import com.kmbeast.pojo.entity.PetPost;
import com.kmbeast.pojo.vo.PetPostListItemVO;
import com.kmbeast.pojo.vo.PetPostVO;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PetPostServiceTest {

    @Autowired
    private PetPostService petPostService;



    @Test
    void testPetPostFullLogic() {
        LocalThreadHolder.setUserId(1, 1);

        String testTitle = "统一的测试标题"; // 定义一个变量，确保前后一致

        // 1. 新增
        PetPost post = new PetPost();
        post.setPetTypeId(1);
        post.setTitle(testTitle); // 使用变量
        post.setCover("test.jpg");
        post.setSummary("摘要测试");
        post.setContent("内容测试");
        petPostService.saveEntity(post);

        // 2. 查询
        PetPostQueryDto queryDto = new PetPostQueryDto();
        queryDto.setTitle(testTitle); // 必须传一模一样的 "统一的测试标题"

        // 执行查询
        ApiResult<List<PetPostListItemVO>> listRes = (ApiResult<List<PetPostListItemVO>>) petPostService.list(queryDto);

        // 打印结果
        System.out.println("查询总条数: " + listRes.getTotal());
    }
}
