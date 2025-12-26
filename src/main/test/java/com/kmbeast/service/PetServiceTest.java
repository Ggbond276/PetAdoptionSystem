package com.kmbeast.service;

import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetQueryDto;
import com.kmbeast.pojo.entity.Pet;
import com.kmbeast.pojo.vo.PetListItemVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class PetServiceTest {

    @Resource
    private PetService petService;

    @Test
    public void testPetFullFlow() {
        // 1. 创建一个 Pet 对象，字段名严格对应你给我的 Pet.java
        Pet pet = new Pet();
        pet.setName("测试旺财");
        pet.setCover("test_cover.jpg");
        pet.setDetail("这是一只非常可爱的测试犬");
        pet.setAddress("测试基地1号");
        pet.setAge(2);
        pet.setPetTypeId(1); // 必须确保数据库 pet_type 表里有 id=1 的数据
        pet.setIsVaccine(true);
        pet.setIsRecommend(false);

        // 2. 执行新增操作
        System.out.println("=== 开始测试新增 ===");
        Result<String> saveResult = petService.save(pet);
        System.out.println("结果消息: " + saveResult.getmessage());

        // 3. 执行查询列表操作
        System.out.println("\n=== 开始测试列表查询 ===");
        PetQueryDto queryDto = new PetQueryDto();
        queryDto.setCurrent(0); // 对应 LIMIT 的起始位置
        queryDto.setSize(10);   // 对应 LIMIT 的长度
        // 如果你想搜刚才建的旺财，可以加上：queryDto.setName("旺财");

        Result<List<PetListItemVO>> listResult = petService.list(queryDto);

        // 4. 这里的强转是为了拿到 ApiResult 里的 data 和 total
        if (listResult instanceof ApiResult) {
            ApiResult<List<PetListItemVO>> apiResult = (ApiResult<List<PetListItemVO>>) listResult;

            System.out.println("数据总数: " + apiResult.getTotal());

            List<PetListItemVO> voList = apiResult.getData();
            if (voList != null && !voList.isEmpty()) {
                // 取出第一条
                PetListItemVO vo = voList.get(0);

                // 这里的 get 方法是 VO 类上的 @Data 自动生成的
                System.out.println("查询成功，第一条宠物信息：");
                System.out.println("ID: " + vo.getId());
                System.out.println("宠物名: " + vo.getName());
                System.out.println("宠物类型名 (SQL联表结果): " + vo.getPetTypeName());
                System.out.println("所在地: " + vo.getAddress());
                System.out.println("创建时间: " + vo.getCreateTime());
            } else {
                System.out.println("警告：列表为空，请检查数据库是否有数据或 SQL 是否正确。");
            }
        }
    }
}