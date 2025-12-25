<template>
  <div class="pet-detail-container">
    <!-- 宠物基本信息 -->
    <div class="pet-basic-info">
      <h1 class="pet-name">{{ petInfo.name }}</h1>
      <div class="meta-info">
        <span class="pet-type">{{ petInfo.petTypeName }}</span>
        <span class="pet-gender">{{ petInfo.gender | genderFilter }}</span>
        <span class="pet-age">{{ petInfo.age }}个月</span>
      </div>

      <!-- 疫苗状态 -->
      <div class="vaccine-status" :class="{ vaccined: petInfo.isVaccine }">
        {{ petInfo.isVaccine ? "已接种疫苗" : "未接种疫苗" }}
      </div>
    </div>

    <!-- 宠物封面图片 -->
    <div class="pet-cover">
      <img :src="petInfo.cover" :alt="petInfo.name" class="cover-image" />
    </div>

    <!-- 宠物详情描述 -->
    <div class="pet-detail" v-html="petInfo.detail"></div>

    <!-- 点赞及收藏区域 -->
    <div class="operation-area">
      <div class="box">
        <!-- 收藏区域 -->
        <div class="save" @click="saveOperation">
          <div
            style="display: flex;justify-content: center;align-items: center;"
          >
            <i
              :style="{
                color: saveStatus ? 'rgb(216, 30, 6)' : 'rgb(0, 0, 0)'
              }"
              class="el-icon-star-on"
            ></i>
          </div>
          <div class="text">
            {{ saveStatus ? "取消收藏" : "收藏" }}
          </div>
        </div>
        <!-- 点赞区域 -->
        <div class="like" @click="upvoteOperation">
          <div
            style="display: flex;justify-content: center;align-items: center;"
          >
            <img
              :src="upvoteStatus ? '/update-default.png' : '/update-active.png'"
              alt=""
              srcset=""
            />
          </div>
          <div class="text">
            {{ upvoteStatus ? "取消点赞" : "点赞" }}
          </div>
        </div>
      </div>
    </div>

    <!-- 领养信息 -->
    <div class="adopt-info">
      <div class="address">
        <i class="el-icon-location-outline"></i>
        <span>{{ petInfo.address }}</span>
      </div>

      <!-- 领养按钮 -->
      <el-button
        v-if="!petInfo.isAdopt"
        type="primary"
        class="adopt-btn"
        @click="handleAdopt"
      >
        申请领养
      </el-button>
      <div v-else class="adopted-tag">
        <el-tag type="info">已被领养</el-tag>
      </div>
    </div>

    <!-- 评论区 -->
    <div class="evaluations-info">
      <Evaluations
        :avatar="userInfo.avatar"
        :userId="userInfo.id"
        :contentId="Number(queryDto.id)"
        contentType="PET"
      />
    </div>
  </div>
</template>

<script>
import Evaluations from "@/components/Evaluations.vue";
import { getUserInfo } from "@/utils/storage";
export default {
  components: { Evaluations },
  data() {
    return {
      petInfo: {},
      queryDto: {},
      userInfo: {},
      saveActiveNetId: null,
      upvoteActiveNetId: null,
      saveStatus: false, // 收藏状态，初始值false，表示未收藏
      upvoteStatus: false // 点赞状态，初始值false，表示未点赞
    };
  },
  filters: {
    genderFilter(val) {
      return val === 1 ? "公" : "母";
    }
  },
  created() {
    this.queryDto = this.$router.currentRoute.query;
    this.fetchPetInfo(this.queryDto.id);
    this.userInfo = getUserInfo();

    this.fetchSaveStatus();
    this.fetchUpvoteStatus();
  },
  methods: {
    // 查询用户与宠物信息的点赞关系
    async fetchUpvoteStatus() {
      try {
        const activeNetQueryDto = {
          contentId: this.queryDto.id,
          contentType: "PET",
          type: 2 // 行为类型，2是点赞
        };
        const { data } = await this.$axios.post(
          "/active-net/queryUser",
          activeNetQueryDto
        );
        this.upvoteStatus = data.length != 0;
        if (data.length != 0) {
          this.upvoteActiveNetId = data[0].id;
        }
      } catch (error) {
        console.log("查询宠物点赞信息异常：", error);
      }
    },
    // 查询用户与宠物信息的收藏关系
    async fetchSaveStatus() {
      try {
        const activeNetQueryDto = {
          contentId: this.queryDto.id,
          contentType: "PET",
          type: 3 // 行为类型，3是收藏
        };
        const { data } = await this.$axios.post(
          "/active-net/queryUser",
          activeNetQueryDto
        );
        this.saveStatus = data.length != 0;
        if (data.length != 0) {
          this.saveActiveNetId = data[0].id;
        }
      } catch (error) {
        console.log("查询宠物收藏信息异常：", error);
      }
    },
    // 点赞处理方法
    upvoteOperation() {
      if (!this.upvoteStatus) {
        // 新增用户与宠物信息之间的点赞关系建立 - 新增
        const activeNet = {
          contentId: this.queryDto.id,
          contentType: "PET",
          type: 2
        };
        this.$axios
          .post("/active-net/save", activeNet)
          .then(response => {
            if (response.code === 200) {
              this.fetchUpvoteStatus();
              this.$notify({
                duration: 1000,
                title: "点赞操作",
                message: "点赞成功",
                type: "success"
              });
            }
          })
          .catch(error => {
            console.log("用户与宠物信息之间的点赞关系建立异常：", error);
          });
      } else {
        if (this.upvoteActiveNetId === null) {
          console.log("点赞行为互动类型Id为null");
          return;
        }
        // 删除用户与宠物信息之间建立的收藏关系 - 删除
        this.$axios
          .delete(`/active-net/${this.upvoteActiveNetId}`)
          .then(response => {
            if (response.code === 200) {
              this.fetchUpvoteStatus();
              this.$notify({
                duration: 1000,
                title: "点赞操作",
                message: "取消点赞成功",
                type: "success"
              });
            }
          })
          .catch(error => {
            console.log("用户与宠物信息之间的点赞关系删除异常：", error);
          });
      }
    },
    // 收藏处理方法
    saveOperation() {
      if (!this.saveStatus) {
        // 新增用户与宠物信息之间的收藏关系建立 - 新增
        const activeNet = {
          contentId: this.queryDto.id,
          contentType: "PET",
          type: 3
        };
        this.$axios
          .post("/active-net/save", activeNet)
          .then(response => {
            if (response.code === 200) {
              this.fetchSaveStatus();
              this.$notify({
                duration: 1000,
                title: "收藏操作",
                message: "收藏成功",
                type: "success"
              });
            }
          })
          .catch(error => {
            console.log("用户与宠物信息之间的收藏关系建立异常：", error);
          });
      } else {
        if (this.saveActiveNetId === null) {
          console.log("收藏行为互动类型Id为null");
          return;
        }
        // 删除用户与宠物信息之间建立的收藏关系 - 删除
        this.$axios
          .delete(`/active-net/${this.saveActiveNetId}`)
          .then(response => {
            if (response.code === 200) {
              this.fetchSaveStatus();
              this.$notify({
                duration: 1000,
                title: "收藏操作",
                message: "取消收藏成功",
                type: "success"
              });
            }
          })
          .catch(error => {
            console.log("用户与宠物信息之间的收藏关系删除异常：", error);
          });
      }
    },
    // 查询宠物信息
    async fetchPetInfo(id) {
      try {
        const { data } = await this.$axios.get(`/pet/${id}`);
        this.petInfo = data;
      } catch (error) {
        console.log("查询宠物详情信息异常：", error);
      }
    },
    handleAdopt() {
      this.$confirm(`确定要申请领养${this.petInfo.name}吗?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          // 这里调用领养API
          this.$message.success("领养申请已提交，请等待审核!");
        })
        .catch(() => {
          this.$message.info("已取消领养申请");
        });
    }
  }
};
</script>

<style land="scss" scoped>
.operation-area {
  display: flex;
  justify-content: center;
  align-items: center;

  .box {
    display: flex;
    gap: 20px;

    .text {
      font-size: 14px;
      font-weight: 600;
      color: rgb(81, 91, 101);
    }

    .like {
      padding: 20px;
      border-radius: 5px;
      cursor: pointer;

      img {
        width: 25px;
        height: 25px;
      }

      &:hover {
        background-color: rgb(230, 248, 248);
      }
    }

    .save {
      padding: 20px;
      border-radius: 5px;
      cursor: pointer;

      i {
        font-size: 25px;
      }

      &:hover {
        background-color: rgb(230, 248, 248);
      }
    }
  }
}

.active-net {
  display: flex;
  justify-content: center;
  align-items: center;

  .box {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 20px;

    .icon {
      display: flex;
      justify-content: center;
    }

    img {
      width: 22px;
      height: 22px;
    }

    i {
      font-size: 22px;
    }
  }
}

.pet-detail-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  /* box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); */
}

.cover-image {
  width: 100%;
  max-height: 400px;
  object-fit: cover;
  border-radius: 8px;
}

.pet-basic-info {
  margin: 20px 0;
  padding-bottom: 15px;
  border-bottom: 1px solid #eee;
}

.pet-name {
  font-size: 42px;
  color: #333;
  margin-bottom: 10px;
}

.meta-info {
  display: flex;
  gap: 15px;
  color: #666;
  font-size: 14px;
  margin-bottom: 10px;
}

.vaccine-status {
  display: inline-block;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  background: #fef0f0;
  color: #f56c6c;
}

.vaccine-status.vaccined {
  background: #f0f9eb;
  color: #67c23a;
}

.pet-detail {
  margin: 20px 0;
  line-height: 1.6;
  color: #666;
}

.pet-detail >>> ul {
  padding-left: 20px;
}

.pet-detail >>> li {
  margin-bottom: 8px;
}

.adopt-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 30px;
  padding-top: 15px;
  padding-bottom: 15px;
  background-color: rgb(230, 248, 248);
  padding-inline: 20px;
}

.address {
  color: #666;
  font-size: 14px;
}

.address i {
  margin-right: 5px;
}

.adopt-btn {
  width: 120px;
}

.adopted-tag {
  padding: 8px 0;
}
</style>
