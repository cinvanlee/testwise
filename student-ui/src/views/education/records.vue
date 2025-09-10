<template>
  <div>
    <div>
      <div style="margin: 20px;"></div>
      <el-form label-position="left" :model="queryParam" label-width="80px" style="margin-left: 20px">
        <el-form-item label="学科:">
          <el-radio-group v-model="queryParam.disciplineId" size="medium" @change="changeQuery">
            <el-radio-button label="">全部</el-radio-button>
            <el-radio-button v-for="item in classList" :label="item.id" :key="index+1">{{item.disciplineName}}</el-radio-button>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div>
        <basic-container v-for="(record,index) in recordList">
          <el-descriptions :title="record.examinationPaperName">
            <template slot="extra">
              <el-button type="primary" v-if="record.status==1" size="small" @click="viewRecord(record.id)">查看试卷</el-button>
              <el-button type="primary" v-else size="small" @click="editRecord(record.id)">去批改</el-button>
            </template>
            <el-descriptions-item label="试卷状态">
              <el-tag type="success" v-if="record.status==1">完成</el-tag>
              <el-tag type="danger" v-else>待批改</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="学科">{{record.disciplineName}}</el-descriptions-item>
            <el-descriptions-item label="提交时间">{{record.createdTime}}</el-descriptions-item>
            <el-descriptions-item label="耗时">{{record.timeConsuming}}</el-descriptions-item>
            <el-descriptions-item label="试卷总分">{{record.score}}</el-descriptions-item>
            <el-descriptions-item label="最终得分">{{record.finalScore}}</el-descriptions-item>
<!--            <el-descriptions-item label="批改分数">{{record.finalScore}}</el-descriptions-item>-->
          </el-descriptions>
        </basic-container>
      </div>
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParam.pageNum"
        :limit.sync="queryParam.pageSize"
        @pagination="listRecord"
      />
    </div>
  </div>
</template>

<script>

import { studentDisciplineList } from '@/api/education/discipline'
import BasicContainer from '@/components/basic-container/main.vue'
import { listRecord } from '@/api/education/record'

export default {
  name: 'Records',
  components: {
    BasicContainer
  },
  data() {
    return {
      classList:[],
      queryParam:{
        pageNum:1,
        pageSize:10,
      },
      recordList:[],
      loading:false,
      total:0,
    }
  },
  computed: {

  },
  mounted() {
    this.studentDisciplineList();
    this.listRecord();
  },
  watch: {
    '$route'(to, from) {
      if (to.path === '/records') {
        // 重新执行初始化逻辑
        this.studentDisciplineList();
        this.listRecord();
        // 强制更新样式（如果有需要）
        this.$forceUpdate()
      }
    },
  },
  methods: {
    studentDisciplineList(){
      studentDisciplineList().then(res => {
        this.classList = res.data;
      })
    },
    listRecord(){
      this.loading = true;
      listRecord(this.queryParam).then(res => {
        this.recordList = res.rows;
        this.total = res.total;
        this.loading = false;
      });
    },
    changeQuery() {
      this.queryParam.pageNum = 1;
      this.listRecord();
    },
    viewRecord(id) {
      window.open('/view/'+id, '_blank');
    },
    editRecord(id) {
      window.open('/edit/'+id, '_blank');
    },
  }
}
</script>

<style lang="scss" scoped>

</style>
