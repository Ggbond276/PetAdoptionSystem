package com.kmbeast.service;


import com.kmbeast.mapper.PetTypeMapper;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.api.ResultCode;
import com.kmbeast.pojo.dto.PetTypeQueryDto;
import com.kmbeast.pojo.entity.PetType;
import com.kmbeast.service.impl.PetTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
class PetTypeServiceImplTest {

    @Mock
    private PetTypeMapper petTypeMapper;

    @InjectMocks
    private PetTypeServiceImpl petTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        PetType petType = new PetType();
        petType.setName("狗狗");

        when(petTypeMapper.queryByName("狗狗")).thenReturn(null); // 模拟不存在

        Result<String> result = petTypeService.save(petType);

        verify(petTypeMapper, times(1)).save(petType);
        assertEquals(ResultCode.REQUEST_SUCCESS.getCode(), result.getCode());
        assertEquals("宠物类别新增成功", result.getmessage());
    }

    @Test
    void testUpdate() {
        PetType petType = new PetType();
        petType.setName("狗狗");

        when(petTypeMapper.queryByName("狗狗")).thenReturn(new PetType()); // 模拟存在

        Result<String> result = petTypeService.update(petType);

        verify(petTypeMapper, times(1)).update(petType);
        assertEquals(ResultCode.REQUEST_SUCCESS.getCode(), result.getCode());
        assertEquals("宠物类别更新成功", result.getmessage());
    }

    @Test
    void testDelete() {
        Integer id = 1;

        Result<String> result = petTypeService.delete(id);

        verify(petTypeMapper, times(1)).deleteById(id);
        assertEquals(ResultCode.REQUEST_SUCCESS.getCode(), result.getCode());
        assertEquals("宠物类别删除成功", result.getmessage());
    }

    @Test
    void testQuery() {
        PetTypeQueryDto dto = new PetTypeQueryDto();
        List<PetType> list = Arrays.asList(new PetType(), new PetType());

        when(petTypeMapper.queryCount(dto)).thenReturn(2);
        when(petTypeMapper.query(dto)).thenReturn(list);

        Result<List<PetType>> result = petTypeService.query(dto);

        verify(petTypeMapper, times(1)).queryCount(dto);
        verify(petTypeMapper, times(1)).query(dto);

        // 强转成 ApiResult 获取 data 和 total
        com.kmbeast.pojo.api.ApiResult<List<PetType>> apiResult =
                (com.kmbeast.pojo.api.ApiResult<List<PetType>>) result;

        assertEquals(ResultCode.REQUEST_SUCCESS.getCode(), result.getCode());
        assertEquals(list, apiResult.getData());
        assertEquals(2, apiResult.getTotal());
    }
}
