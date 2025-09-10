<template>
  <div>
    <div style="margin: 20px;"></div>
    <el-form label-position="left" :model="queryParam" label-width="80px" style="margin-left: 20px">
      <el-form-item label="试卷类型:">
        <el-radio-group v-model="queryParam.examinationPaperType" size="medium" @change="changeQuery">
          <el-radio-button label="">全部</el-radio-button>
          <el-radio-button label="1">固定试卷</el-radio-button>
          <el-radio-button label="2">时段试卷</el-radio-button>
          <el-radio-button label="3">任务试卷</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="学科:">
        <el-radio-group v-model="queryParam.disciplineId" size="medium" @change="changeQuery">
          <el-radio-button label="">全部</el-radio-button>
          <el-radio-button v-for="item in classList" :label="item.id" :key="index+1">{{item.disciplineName}}</el-radio-button>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <div>
      <el-row :gutter="20">
        <el-col :span="6" v-for="(item,index) in paperList" :key="index+1">
          <basic-container>
            <el-descriptions class="margin-top" :title="item.examinationPaperName" :column="1" :size="size">
              <template slot="extra">
                <el-button type="primary" size="small" @click="goDo(item.id)">开始答题</el-button>
              </template>
              <el-descriptions-item label="学科">{{item.disciplineName}}</el-descriptions-item>
              <el-descriptions-item label="题目数量">{{item.paperQuestionsNum}}</el-descriptions-item>
              <el-descriptions-item label="试卷总分">{{item.score}}</el-descriptions-item>
              <el-descriptions-item label="考试时长">{{item.suggestionsMinutes}}分钟</el-descriptions-item>
              <el-descriptions-item label="开始时间">{{item.startTime}}</el-descriptions-item>
              <el-descriptions-item label="结束时间">{{item.endTime}}</el-descriptions-item>
            </el-descriptions>
          </basic-container>
        </el-col>
      </el-row>
    </div>
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParam.pageNum"
      :limit.sync="queryParam.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>

import BasicContainer from '@/components/basic-container/main.vue'
import { studentPaperPage } from '@/api/education/paper'
import { studentDisciplineList } from '@/api/education/discipline'

export default {
  name: 'Center',
  components: {
    BasicContainer
  },
  data() {
    return {
      queryParam:{
        pageNum:1,
        pageSize:10,
      },
      size: '',
      classList:[],
      total:0,
      paperList:[],
    }
  },
  computed: {

  },
  mounted() {
    this.studentDisciplineList();
    this.getList();
  },
  methods: {
    goDo(id) {
      window.open('/do/'+id, '_blank');
    },
    studentDisciplineList(){
      studentDisciplineList().then(res => {
        this.classList = res.data;
      })
    },
    getList() {
      studentPaperPage(this.queryParam).then(res => {
        this.total = res.total;
        this.paperList = res.rows;
      })
    },
    changeQuery() {
      this.queryParam.pageNum = 1;
      this.getList();
    },
  }
}
</script>

<style lang="scss" scoped>
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  //background: #99a9bf;
}
.bg-purple {
  //background: #d3dce6;
}
.bg-purple-light {
  //background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>
