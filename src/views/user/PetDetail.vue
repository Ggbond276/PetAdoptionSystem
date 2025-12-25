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
      userInfo: {}
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
  },
  methods: {
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

<style scoped>
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
  border-top: 1px solid #eee;
  border-bottom: 1px solid #eee;
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
