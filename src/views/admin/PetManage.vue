<template>
  <!-- ======================= 页面容器 ======================= -->
  <div class="container">
    <!-- ======================= 顶部操作栏（筛选 + 搜索 + 新增） ======================= -->
    <div class="top-healder">
      <!-- 左侧：推荐状态筛选 -->
      <div class="nav-left">
        <Tab
          :buttons="[
            { label: '全部', value: 'null' },
            { label: '未推荐', value: '0' },
            { label: '推荐', value: '1' }
          ]"
          initialActive="null"
          @change="handleChange"
        />
      </div>

      <!-- 右侧：搜索 + 新增按钮 -->
      <div class="nav-right">
        <!-- 搜索宠物 -->
        <div>
          <AutoInput placeholder="搜索宠物" @listener="listener" />
        </div>

        <!-- 新增宠物 -->
        <div class="primary-bt" @click="dialogVisible = true">
          <i class="el-icon-plus"></i>
          新增宠物
        </div>
      </div>
    </div>

    <!-- ======================= 表格区域 ======================= -->
    <div>
      <!-- ---------- 宠物列表表格 ---------- -->
      <el-table :data="apiResult.data">
        <!-- ====== 基本信息列（封面 + 名称 + 类型 + 地址） ====== -->
        <el-table-column prop="name" label="基本信息">
          <template #default="scope">
            <div style="display: flex; gap: 10px; align-items: center;">
              <!-- 宠物封面 + 类型(这里的图片路径是需要修改的 因为没有图片所有我先写死了) -->
              <div class="cover-block">
                <img
                  width="90px"
                  height="60px"
                  style="border-radius: 5px;"
                  src="@/assets/image/default-cover.jpg"
                />
                <span class="pet-type">
                  {{ scope.row.petTypeName }}
                </span>
              </div>

              <!-- 宠物名称 + 所在地 -->
              <div>
                <div style="font-size: 18px;">
                  {{ scope.row.name }}
                </div>
                <el-tag type="info" size="mini">
                  宠物所在地 - {{ scope.row.address }}
                </el-tag>
              </div>
            </div>
          </template>
        </el-table-column>

        <!-- ====== 状态信息列 ====== -->
        <el-table-column prop="status" width="250" label="宠物状态">
          <template #default="scope">
            <div>
              接种疫苗状态：
              {{ scope.row.isVaccine ? "已接种疫苗" : "未接种疫苗" }}
            </div>
            <div>
              是否推荐：
              {{ scope.row.isRecommend ? "已推荐" : "未推荐" }}
            </div>
            <div>
              领养状态：
              {{ scope.row.isAdopt ? "已领养" : "未领养" }}
            </div>
          </template>
        </el-table-column>

        <!-- ====== 属性列（性别 + 年龄） ====== -->
        <el-table-column prop="age" sortable width="150" label="属性">
          <template #default="scope">
            <div>性别：{{ scope.row.gender === 1 ? "母" : "公" }}</div>
            <div>年龄：{{ scope.row.age }} 个月</div>
          </template>
        </el-table-column>

        <!-- ====== 操作列（修改 / 删除） ====== -->
        <el-table-column width="200" align="center">
          <template #default="scope">
            <el-dropdown trigger="click" placement="bottom-end">
              <span class="el-dropdown-link">
                <i class="el-icon-more"></i>
              </span>

              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item
                  icon="el-icon-edit"
                  @click.native="handleEdit(scope.row)"
                >
                  修改信息
                </el-dropdown-item>

                <el-dropdown-item
                  icon="el-icon-delete"
                  @click.native="handleDelete(scope.row)"
                >
                  删除宠物
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>

      <!-- ======================= 分页组件 ======================= -->
      <div class="pager">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="petQueryDto.current"
          :page-sizes="[10, 20]"
          :page-size="petQueryDto.size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="apiResult.total"
        />
      </div>
    </div>

    <!-- ======================= 新增 / 编辑宠物弹窗 ======================= -->
    <el-dialog
      :title="dialogControlOperation ? '新增宠物信息' : '修改宠物信息'"
      :visible.sync="dialogVisible"
      :show-close="false"
      :close-on-click-modal="false"
      width="45%"
    >
      <!-- ---------- Tab 切换 ---------- -->
      <el-tabs v-model="tabActiveName" style="min-height: 450px;">
        <!-- ====== Tab1：基本信息 ====== -->
        <el-tab-pane label="基本信息" name="first">
          <!-- 封面上传 -->
          <div class="user-avatar">
            <p>点击📷处即可上传封面</p>

            <img
              v-if="cover"
              :src="cover"
              style="width: 160px;height: 90px;border-radius: 5px;"
            />

            <el-upload
              class="avatar-uploader"
              action="api/v1.0/pet-adopt-api/file/upload"
              :show-file-list="false"
              :on-success="handleImageSuccess"
            >
              <i class="el-icon-camera-solid"></i>
            </el-upload>
          </div>

          <!-- 基本输入项 -->
          <div>
            <p>*宠物名称</p>
            <el-input v-model="apiParam.name" clearable />
          </div>

          <div>
            <p>*宠物所在地</p>
            <el-input v-model="apiParam.address" clearable />
          </div>

          <div>
            <p>*宠物类型</p>
            <el-select v-model="apiParam.petTypeId" style="width: 100%;">
              <el-option
                v-for="item in petTypeList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </div>

          <div>
            <p>*宠物年龄</p>
            <el-slider v-model="apiParam.age" :max="360" />
          </div>
        </el-tab-pane>

        <!-- ====== Tab2：状态信息 ====== -->
        <el-tab-pane label="状态信息" name="second">
          <!-- 接种疫苗 -->
          <div>
            <p>*是否接种疫苗</p>
            <Tab
              :buttons="[
                { label: '未接种', value: '0' },
                { label: '已接种', value: '1' }
              ]"
              :initialActive="String(apiParam.isVaccine)"
              @change="handleVaccineChange"
            />
          </div>

          <!-- 是否推荐 -->
          <div>
            <p>*是否推荐</p>
            <Tab
              :buttons="[
                { label: '未推荐', value: '0' },
                { label: '已推荐', value: '1' }
              ]"
              :initialActive="String(apiParam.isRecommend)"
              @change="handleRecommendChange"
            />
          </div>

          <!-- 性别 -->
          <div>
            <p>*性别</p>
            <Tab
              :buttons="[
                { label: '公', value: '0' },
                { label: '母', value: '1' }
              ]"
              :initialActive="String(apiParam.gender)"
              @change="handleGenderChange"
            />
          </div>

          <!-- 仅编辑时显示 -->
          <div v-if="!dialogControlOperation">
            <p>*是否已领养</p>
            <Tab
              :buttons="[
                { label: '未领养', value: '0' },
                { label: '已领养', value: '1' }
              ]"
              :initialActive="String(apiParam.isAdopt)"
              @change="handleAdoptChange"
            />
          </div>
        </el-tab-pane>

        <!-- ====== Tab3：描述信息 ====== -->
        <el-tab-pane label="描述信息" name="third">
          <Editor
            :receiveContent="detail"
            @on-listener="onListener"
            api="api/v1.0/pet-adopt-api/file/upload"
          />
        </el-tab-pane>
      </el-tabs>

      <!-- 弹窗底部按钮 -->
      <span slot="footer" class="dialog-footer">
        <span class="primary-bt" @click="cancelOperation">取消</span>
        <span class="info-bt" @click="handleConfirm">
          {{ dialogControlOperation ? "确定新增" : "确定修改" }}
        </span>
      </span>
    </el-dialog>

    <!-- ======================= 删除确认弹窗 ======================= -->
    <el-dialog
      title="删除宠物"
      :visible.sync="dialogDeletedVisible"
      :show-close="false"
      width="20%"
    >
      <span>确定删除宠物数据？</span>

      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogDeletedVisible = false"
          >取消</el-button
        >
        <el-button size="mini" type="primary" @click="confirmDeleted"
          >确定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import AutoInput from "@/components/AutoInput.vue"; // 导入封装好的输入框组件
import Tab from "@/components/Tab"; // 导入封装好的Tab组件
import Editor from "@/components/Editor";
export default {
  components: { AutoInput, Tab, Editor }, // 注册组件
  data() {
    return {
      tabActiveName: "first", // 操作弹窗初始tab页选中第一项
      tabPosition: "right", // 操作弹窗排布靠右
      id: null, // 页面即将删除的数据ID
      apiResult: {
        // 后端返回的查询数据的响应数据
        data: [], // 数据项
        total: 0 // 符合条件的数据总想 - 初始赋值为0
      },
      petQueryDto: {
        current: 1,
        size: 10,
        name: null,
        isRecommend: null,
        petTypeId: null
      },
      dialogDeletedVisible: false, // 删除弹窗控制开关变量 - 初始是关（false）
      dialogVisible: false, // 操作弹窗控制开关变量 - 初始是关（false）
      dialogControlOperation: true, // 弹窗当前操作标识 - true：新增；false：修改
      apiParam: {
        isVaccine: 0,
        isRecommend: 0,
        gender: 0,
        age: 12 // 初始年龄是一年
      }, // 传送进后端的数据 - 用于新增与修改场景
      cover: "", // 宠物封面
      petTypeList: [], // 宠物类型数据列表
      detail: "" // 宠物描述
    };
  },
  created() {
    this.fetchFreshData(); // 页面创建时，先去加载宠物数据
    this.fetchPetTypeData(); // 页面创建时，直接加载宠物类型数据
  },
  methods: {
    // 富文本数据回调
    onListener(text) {
      this.apiParam.detail = text;
    },
    // 查询宠物类型 - 做「新增 - 修改」下拉选择器使用
    async fetchPetTypeData() {
      try {
        const { data } = await this.$axios.post("/pet-type/query", {});
        this.petTypeList = data;
      } catch (error) {
        console.error("查询宠物类型异常：", error.message);
      }
    },
    handleConfirm() {
      // 判断行为类型 - 判断是新增操作还是修改操作
      this.dialogControlOperation
        ? this.savePetOperation()
        : this.updateUserOperation();
    },
    // 是否接种疫苗切换
    handleVaccineChange(obj) {
      this.apiParam.isVaccine = Number(obj.value);
    },
    // 是否推荐
    handleRecommendChange(obj) {
      this.apiParam.isRecommend = Number(obj.value);
    },
    // 宠物性别选择切换
    handleGenderChange(obj) {
      this.apiParam.gender = Number(obj.value);
    },
    // 宠物领养状态切换
    handleAdoptChange(obj) {
      this.apiParam.isAdopt = Number(obj.value);
    },
    // 修改宠物信息操作
    async updateUserOperation() {
      try {
        const { message } = await this.$axios.put("/pet/update", this.apiParam);
        this.cancelOperation(); // 状态置位
        this.$message.success(message); // 消息提示
        this.fetchFreshData(); // 重新加载列表数据
      } catch (error) {
        this.$message.warning(error.message);
      }
    },
    // 保存宠物信息操作
    async savePetOperation() {
      try {
        this.apiParam.cover = this.cover; // 设置上宠物图片信息
        const { message } = await this.$axios.post("/pet/save", this.apiParam);
        this.cancelOperation(); // 状态置位
        this.$message.success(message); // 消息提示
        this.fetchFreshData(); // 重新加载列表数据
      } catch (error) {
        this.$message.warning(error.message);
      }
    },
    // 取消处理 -- 置位函数
    cancelOperation() {
      this.apiParam = {}; // 将参数对象清置
      this.cover = ""; // 宠物封面清置
      this.detail = ""; // 宠物描述
      this.tabActiveName = "first"; // 操作列表恢复默认选中第一项
      this.dialogVisible = false; // 关闭宠物新增弹窗
      this.dialogControlOperation = true; // 操作标识变量设置为最初的状态，即true，为新增
    },
    // 封面上传响应
    handleImageSuccess(res) {
      // 通知提示
      this.$notify({
        title: "封面上传",
        type: res.code === 200 ? "success" : "error",
        message: res.code === 200 ? "上传成功" : res.data,
        position: "buttom-right",
        suration: 1000
      });
      if (res.code === 200) {
        this.cover = res.data; // 响应里面的data，即后端返回的上传后的图片链接
      }
    },
    // 宠物角色状态选中事件
    handleChange(obj) {
      this.petQueryDto.isRecommend = Number(obj.value);
      // console.log(this.petQueryDto); // 转成数值类型，再赋值
      this.fetchFreshData(); // 重新加载宠物数据
    },
    // 输入框组件输入回传
    listener(text) {
      this.petQueryDto.name = text; // 赋值查询条件的内容
      this.fetchFreshData(); // 重新加载数据
    },
    // 查询宠物数据
    async fetchFreshData() {
      try {
        const { data, total } = await this.$axios.post(
          "/pet/list",
          this.petQueryDto
        );
        this.apiResult.data = data;
        this.apiResult.total = total;
        // console.log(this.apiResult.data);
        // console.log(this.apiResult.total);
      } catch (error) {
        console.error("查询宠物信息异常:", error);
      }
    },
    // 分页 - 处理页面页数切换
    handleSizeChange(size) {
      this.petQueryDto.size = size; // 当前页面大小重置
      this.petQueryDto.current = 1; // 当前页设置为第一页
      this.fetchFreshData(); // 重新加载页面数据
    },
    // 分页 - 处理页面当前页切换
    handleCurrentChange(current) {
      this.petQueryDto.current = current; // 当前页选中
      this.fetchFreshData(); // 重新加载页面数据
    },
    // 表格点击修改宠物
    async handleEdit(row) {
      try {
        const { data } = await this.$axios.get(`/pet/${row.id}`);
        this.cancelOperation();
        this.apiParam = { ...data }; // 当前操作宠物信息
        this.dialogControlOperation = false; // 标识为修改操作
        this.dialogVisible = true; // 打开操作面板
        this.cover = data.cover; // 头像手动赋值
        this.detail = data.detail;
        this.apiParam.isVaccine = Number(this.apiParam.isVaccine);
        this.apiParam.isRecommend = Number(this.apiParam.isRecommend);
        this.apiParam.isAdopt = Number(this.apiParam.isAdopt);
      } catch (error) {
        console.log("宠物修改：", error);
        this.$message.error(error.message);
      }
    },
    // 表格点击删除宠物
    handleDelete(row) {
      this.dialogDeletedVisible = true; // 开启删除弹窗确认
      this.id = row.id;
    },
    // 宠物删除
    async confirmDeleted() {
      try {
        const { code } = await this.$axios.delete(`/pet/${this.id}`);
        if (code === 200) {
          this.$notify.success({
            title: "宠物删除",
            message: "删除成功",
            position: "buttom-right",
            suration: 1000
          });
          this.dialogDeletedVisible = false; // 关闭删除确认弹窗
          this.id = null; // 将标识ID置位
          this.fetchFreshData(); // 删除宠物数据之后，重新加载宠物数据
        }
      } catch (error) {
        console.log("删除宠物数据异常：", error);
      }
    }
  }
};
</script>
<style scoped lang="scss">
.cover-block {
  // 父相子绝
  position: relative;

  .pet-type {
    position: absolute;
    top: 0;
    left: 0;
    color: rgb(255, 255, 255);
    font-size: 10px;
    padding: 0 4px;
    border-top-left-radius: 5px;
    background-color: rgba(9, 164, 48, 0.4);
  }
}

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
