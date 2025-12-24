<template>
  <div class="container">
    <div class="top-healder">
      <div class="nav-left"></div>
      <div class="nav-right">
        <!-- 搜索框 用来搜索宠物类别 -->
        <div>
          <AutoInput placeholder="搜索宠物类别" @listener="listener" />
        </div>
        <div class="primary-bt" @click="dialogVisible = true">
          <i class="el-icon-plus"></i>
          新增宠物类别
        </div>
      </div>
    </div>

    <!-- 表格及分页信息 -->
    <div>
      <el-table :data="apiResult.data">
        <!-- 宠物类别序号列 -->
        <el-table-column width="100" :sortable="true" label="序号">
          <template #default="scope">
            <div class="over-text">
              {{ scope.row.id }}
            </div>
          </template>
        </el-table-column>
        <!-- 宠物类别名称列 -->
        <el-table-column width="300" :sortable="true" label="宠物类别名">
          <template #default="scope">
            <div class="over-text">
              {{ scope.row.name }}
            </div>
          </template>
        </el-table-column>
        <!-- 操作列 -->
        <el-table-column label="" align="center">
          <template #default="scope">
            <!-- 操作按钮容器 -->
            <div class="operate-buttons">
              <!-- 操作下拉菜单 -->
              <el-dropdown trigger="click" placement="bottom-end">
                <span class="el-dropdown-link">
                  <i class="el-icon-more"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <!-- 修改宠物类别信息按钮 -->
                  <el-dropdown-item
                    @click.native="handleEdit(scope.row)"
                    icon="el-icon-edit"
                  >
                    修改信息
                  </el-dropdown-item>
                  <!-- 删除宠物类别信息按钮 -->
                  <el-dropdown-item
                    @click.native="handleDelete(scope.row)"
                    icon="el-icon-delete"
                    >删除宠物类别
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页组件区域 -->
      <div class="pager">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="PetTypeQueryDto.current"
          :page-sizes="[10, 20]"
          :page-size="PetTypeQueryDto.size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="apiResult.total"
        ></el-pagination>
      </div>
    </div>

    <!-- 宠物类别信息操作弹窗 -->
    <el-dialog
      :title="dialogControlOperation ? '新增宠物类别信息' : '修改宠物类别信息'"
      :show-close="false"
      :visible.sync="dialogVisible"
      :closeOnClickModal="false"
      width="45%"
    >
      <!-- 宠物类别输入框 -->
      <el-input
        placeholder="宠物类别名"
        v-model="apiParam.name"
        clearable
      ></el-input>
      <span slot="footer" class="dialog-footer">
        <!-- 取消操作 -->
        <span class="primary-bt" @click="cancelOperation">取消</span>
        <!-- 确认操作 -->
        <span class="info-bt" @click="handleConfirm">
          {{ dialogControlOperation ? "确定新增" : "确定修改" }}
        </span>
      </span>
    </el-dialog>

    <!-- 删除确认弹窗 -->
    <el-dialog
      title="删除宠物类别"
      :show-close="false"
      :visible.sync="dialogDeletedVisible"
      width="20%"
    >
      <span>确定删除宠物类别数据？</span>
      <span slot="footer" class="dialog-footer">
        <!-- 取消删除宠物类别数据按钮 -->
        <el-button size="mini" @click="dialogDeletedVisible = false"
          >取消</el-button
        >
        <!-- 确认删除宠物类别数据按钮 -->
        <el-button size="mini" type="primary" @click="confirmDeleted"
          >确定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import AutoInput from "@/components/AutoInput.vue"; // 导入封装好的输入框组件
export default {
  components: { AutoInput }, // 注册组件
  data() {
    return {
      id: null, // 页面即将删除的数据ID
      apiResult: {
        // 后端返回的查询数据的响应数据
        data: [], // 数据项
        total: 0 // 符合条件的数据总想 - 初始赋值为0
      },
      PetTypeQueryDto: {
        // 搜索条件
        current: 1, // 当前页 - 初始是第一页
        size: 10, // 页面显示大小 - 初始是10条
        id: null, // 宠物类别ID搜索条件 - 初始是空
        name: "" // 宠物类别名称搜索条件 - 初始是空
      },
      dialogDeletedVisible: false, // 删除弹窗控制开关变量 - 初始是关（false）
      dialogVisible: false, // 操作弹窗控制开关变量 - 初始是关（false）
      dialogControlOperation: true, // 弹窗当前操作标识 - true：新增；false：修改
      apiParam: {} // 传送进后端的数据 - 用于新增与修改场景
    };
  },
  created() {
    this.fetchFreshData();
  },
  methods: {
    // 新增宠物类别信息(已实现)
    async savePetTypeOperation() {
      try {
        const { message, code } = await this.$axios.post(
          "/pet-type/save",
          this.apiParam
        );
        if (code == 200) {
          this.$message.success(message);
          this.cancelOperation(); // 状态置位
          this.fetchFreshData(); // 重新加载列表数据
        } else {
          this.$message.info(message);
        }
      } catch (error) {
        this.$message.warning(error.message);
      }
    },
    // 宠物类别删除(已实现)
    async confirmDeleted() {
      try {
        const { code } = await this.$axios.delete(`/pet-type/${this.id}`);
        if (code === 200) {
          // 删除成功通知提示
          this.$notify.success({
            title: "宠物类别删除",
            message: "删除成功",
            position: "buttom-right",
            duration: 1000
          });
          this.dialogDeletedVisible = false; // 关闭删除确认弹窗
          this.id = null; // 将标识ID置位
          this.fetchFreshData(); // 删除宠物类别数据之后，重新加载宠物类别数据
        }
      } catch (error) {
        console.log("删除宠物类别数据异常：", error);
      }
    },
    // 修改宠物类别信息操作(已实现)
    async updatePetTypeOperation() {
      try {
        const { message, code } = await this.$axios.put(
          "/pet-type/update",
          // 这里为什么只需要拿到一个名字就可以了？因为前端传递给后端的数据对象apiParam里面已经包含了id属性
          this.apiParam
        );
        if (code == 200) {
          this.$message.success(message);
          this.cancelOperation(); // 状态置位
          this.fetchFreshData(); // 重新加载列表数据
        } else {
          this.$message.info(message);
        }
      } catch (error) {
        this.$message.warning(error.message);
      }
    },
    // 查询宠物类别数据(已实现)
    async fetchFreshData() {
      try {
        // 后端返回查询数据和分页总数
        const { data, total } = await this.$axios.post(
          "/pet-type/query",
          this.PetTypeQueryDto
        );
        // 赋值响应数据
        this.apiResult.data = data;
        this.apiResult.total = total;
        // console.log(this.apiResult.data);
        // console.log(total);
      } catch (error) {
        console.error("查询宠物类别信息异常:", error);
      }
    },
    // 分页 - 处理页面页数切换
    handleSizeChange(size) {
      this.PetTypeQueryDto.size = size; // 当前页面大小重置
      this.PetTypeQueryDto.currrent = 1; // 当前页设置为第一页
      this.fetchFreshData(); // 重新加载页面数据
    },
    // 分页 - 处理页面当前页切换
    handleCurrentChange(current) {
      this.PetTypeQueryDto.current = current; // 当前页选中
      this.fetchFreshData(); // 重新加载页面数据
    },
    // 表格点击修改宠物类别
    handleEdit(row) {
      this.cancelOperation();
      this.apiParam = { ...row }; // 当前操作宠物类别信息
      this.dialogControlOperation = false; // 标识为修改操作
      this.dialogVisible = true; // 打开操作面板
    },
    // 表格点击删除宠物类别
    handleDelete(row) {
      this.dialogDeletedVisible = true; // 开启删除弹窗确认
      this.id = row.id;
    },
    // 判断行为类型 - 判断是新增操作还是修改操作
    handleConfirm() {
      // 判断行为类型 - 判断是新增操作还是修改操作
      this.dialogControlOperation
        ? this.savePetTypeOperation()
        : this.updatePetTypeOperation();
    },
    // 状态置位
    cancelOperation() {
      this.apiParam = {}; // 将参数对象清置
      this.dialogVisible = false; // 关闭宠物类别新增弹窗
      this.dialogControlOperation = true; // 操作标识变量设置为最初的状态，即true，为新增
    },
    // 输入框组件输入回传
    listener(text) {
      this.PetTypeQueryDto.name = text; // 赋值查询条件的内容
      this.fetchFreshData(); // 重新加载数据
    }
  }
};
</script>
<style scoped lang="scss">
.pager {
  margin-block: 20px;
}

/* 默认隐藏操作按钮 */
.operate-buttons {
  opacity: 0;
  transition: opacity 0.3s;
  /* 添加过渡效果 */
  cursor: pointer;

  i {
    padding: 8px;
    border-radius: 6px;
    transition: all 0.5s ease;

    &:hover {
      background-color: rgb(236, 237, 238);
    }
  }
}

/* 行悬停时显示操作按钮 */
.el-table__body tr:hover .operate-buttons {
  opacity: 1;
}

.container {
  margin: 10px 20px;
}

.top-healder {
  margin-block: 10px;
  padding-inline: 10px;
  border-radius: 5px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .nav-left,
  .nav-right {
    display: flex;
    justify-content: left;
    align-items: center;
    gap: 10px;
  }

  .nav-left {
    display: flex;
  }
}
</style>
