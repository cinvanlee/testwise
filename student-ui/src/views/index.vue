<template>
  <div>
    <el-carousel indicator-position="outside" height="400px">
      <el-carousel-item v-for="(item,index) in imgs" :key="index+1">
        <img :src="item" alt="">
      </el-carousel-item>
    </el-carousel>
    <basic-container>
      <h2>任务考试</h2>
      <el-collapse v-model="activeName" accordion>
        <el-collapse-item :name="index+1" v-for="(item,index) in taskList" :key="index+1">
          <template slot="title">
            <h3>{{item.title}}</h3>
            <el-badge :value="item.examinationPaperList.length" class="item">
            </el-badge>
          </template>
          <div style="margin-top: 10px;margin-left: 20px">
            <h4 v-for="(paper, pindex) in item.examinationPaperList" :key="pindex" class="exam-paper-info">
              {{paper.examinationPaperName}}
              <el-link :href="'/do/'+paper.id" target="_blank" type="primary" class="start-exam-link">开始考试</el-link>
            </h4>
          </div>
        </el-collapse-item>
      </el-collapse>
    </basic-container>
  </div>
</template>

<script>
import BasicContainer from '@/components/basic-container/main.vue'
import {selectList as taskSelectList} from '@/api/education/task'

export default {
  name: "Index",
  components: { BasicContainer },
  data() {
    return {
      // 版本号
      version: "3.8.7",
      imgs:['http://img.astarcloud.top/202502101522480.png','http://img.astarcloud.top/202502101518754.png','http://img.astarcloud.top/202502101522480.png'],
      taskList:[],
      activeName:1
    };
  },
  mounted() {
    this.taskSelectList();
  },
  methods: {
    taskSelectList() {
      taskSelectList().then(res => {
        this.taskList = res.data;
      })
    },
    goDo(id) {
      this.$router.push({path:'/education/do/'+id})
    },
  },
};
</script>

<style>
.exam-paper-info {
  display: flex; /* 使用 Flexbox 布局 */
  align-items: center; /* 垂直居中对齐 */
  justify-content: space-between; /* 在标题和链接间分配空间，使链接靠右 */
}

.start-exam-link {
  margin-right: 30px; /* 移除链接的默认右边距 */
}


</style>
