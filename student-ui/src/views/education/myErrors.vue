<template>
  <div>
    <div>
      <div style="margin: 20px;"></div>
      <div>
        <basic-container v-for="(record,index) in recordList">
          <div class="question-header">
            <div class="header-content">
              <span>题型：{{ questionsTypeList[record.paperQuestions.questionsType] }}</span>
              <span>学科：{{ record.disciplineName }}</span>
            </div>
          </div>
          <el-descriptions title="" column="1" :colon="false">
            <el-descriptions-item label=""><span v-html="record.paperQuestions.title"></span></el-descriptions-item>
                <el-descriptions-item v-if="record.paperQuestions.questionsType == 1">
                  <el-radio-group v-model="record.paperQuestions.userCorrectOptionKey" class="radio-group">
                    <el-radio disabled style="margin-top: 10px;" v-for="(bankColumn, bankIndex) in record.paperQuestions.bankOptionsList"
                              :label="bankColumn.optionKey" :key="bankIndex">
                      {{ bankColumn.optionKey }}
                      <span style="float: right;margin-left: 5px;margin-top: -14px" v-html="bankColumn.optionName"></span>
                    </el-radio>
                  </el-radio-group>
                </el-descriptions-item>
                <el-descriptions-item v-if="record.paperQuestions.questionsType == 2">
                   <el-checkbox-group disabled v-model="record.paperQuestions.userCorrectOptionKey">
                      <el-checkbox v-for="(bankColumn,bankIndex) in record.paperQuestions.selectList" :label="bankColumn"
                                   :key="bankIndex">{{ bankColumn }}
                        <span style="float: right;margin-left: 5px;margin-top: -14px" v-html="bankColumn.optionName"></span>
                      </el-checkbox>
                   </el-checkbox-group>
                </el-descriptions-item>
                <el-descriptions-item v-if="record.paperQuestions.questionsType == 3">
                   <el-radio-group disabled v-model="record.paperQuestions.userCorrectOptionKey" class="radio-group">
                    <el-radio style="margin-top: 10px;" v-for="(bankColumn, bankIndex) in record.paperQuestions.bankOptionsList"
                              :label="bankColumn.optionKey" :key="bankIndex">
                      {{ bankColumn.optionKey }}
                      <span style="float: right;margin-left: 5px;margin-top: -14px" v-html="bankColumn.optionName"></span>
                    </el-radio>
                  </el-radio-group>
                </el-descriptions-item>
                <el-descriptions-item v-if="record.paperQuestions.questionsType == 4">
                  <div>
                    <el-row>
                      <el-col :span="24">
                        <span style="float: left;margin-left: 10px;margin-top: -14px;background-color: #f3f3f3" v-html="record.userAnswer"></span>
                      </el-col>
                    </el-row>
                     <el-row>
                      <el-col :span="24">
                      </el-col>
                    </el-row>
                  </div>
                </el-descriptions-item>
                <el-descriptions-item v-if="record.paperQuestions.questionsType == 5">
                  <div v-for="(bankColumn, bankIndex) in record.paperQuestions.bankOptionsList" :key="bankIndex" class="option-item"
                       style="margin-top: 5px;margin-left: 20px">
                    <span>({{ bankColumn.optionKey }}). </span>
                    <el-row>
                      <el-col :span="24">
                        <span class="answer" style="margin-left: 5px; display: inline-block;float: right" v-html="bankColumn.userAnswer"></span>
                      </el-col>
                    </el-row>
                  </div>
                </el-descriptions-item>
            <el-descriptions-item label="答案：" v-if="record.paperQuestions.questionsType == 1"><span v-html="record.paperQuestions.correctOptionKey"></span></el-descriptions-item>
            <el-descriptions-item label="答案：" v-if="record.paperQuestions.questionsType == 2"><span  v-html="record.paperQuestions.correctOptionKey"></span></el-descriptions-item>
            <el-descriptions-item label="答案：" v-if="record.paperQuestions.questionsType == 3"><span  v-html="record.paperQuestions.correctOptionKey"></span></el-descriptions-item>
            <el-descriptions-item label="答案：" v-if="record.paperQuestions.questionsType == 4"><span class="analysis-content" v-html="record.paperQuestions.correctOptionKey"></span></el-descriptions-item>
            <el-descriptions-item label="答案：" v-if="record.paperQuestions.questionsType == 5">
              <div v-for="(bankColumn, bankIndex) in record.paperQuestions.bankOptionsList" :key="bankIndex" class="option-item"
                   style="margin-top: -14px;margin-left: 7px">
                <span>({{ bankColumn.optionKey }}). </span>
                <el-row>
                  <el-col :span="24">
                    <span class="answer" style="margin-left: 5px; display: inline-block;float: right" v-html="bankColumn.answer"></span>
                  </el-col>
                </el-row>
              </div>
            </el-descriptions-item>
            <el-descriptions-item label="分数：">{{record.paperQuestions.score}}</el-descriptions-item>
            <el-descriptions-item label="难度：">
              <el-rate
                disabled
                v-model="record.paperQuestions.difficulty"
                :colors="colors">
              </el-rate>
            </el-descriptions-item>
            <el-descriptions-item label="解析：">
              <span class="analysis-content" v-html="record.paperQuestions.questionsAnalyze"></span>
            </el-descriptions-item>
          </el-descriptions>
        </basic-container>
      </div>
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParam.pageNum"
        :limit.sync="queryParam.pageSize"
        @pagination="errorListRecord"
      />
    </div>
  </div>
</template>

<script>

import BasicContainer from '@/components/basic-container/main.vue'
import { errorListRecord, listRecord } from '@/api/education/record'

export default {
  name: 'MyErrors',
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
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
      questionsTypeList:['','单选题','多选题','判断题','简答题','填空题']
    }
  },
  computed: {

  },
  mounted() {
    this.errorListRecord();
  },
  methods: {
    errorListRecord(){
      this.loading = true;
      errorListRecord(this.queryParam).then(res => {
        this.recordList = res.rows;
        this.total = res.total;
        this.recordList.forEach(record => {
          let item = record.paperQuestions;
          if (item.questionsType == 2) {
            if (item.userCorrectOptionKey != undefined && item.userCorrectOptionKey != '') {
              item.userCorrectOptionKey = item.userCorrectOptionKey.split(',');
            }
            item.selectList = item.bankOptionsList.map(bank => bank.optionKey);
          }
          item.questionsBankId = item.questionBankId;
          item.questionBankId = item.questionBankId;
          item.optionKey = item.correctOptionKey;
        });
        console.log(this.recordList)
        this.loading = false;
      });
    },
  }
}
</script>

<style lang="scss" scoped>
.analysis-content {
  padding-left: 10px; /* 调整内边距 */
  margin-top: -14px;
}
.custom-countdown .number {
  color: red !important;
}

.el-card {
  border: none;
  box-shadow: none;
}

.avue-video__main {
  width: 58%;
}

.avue-video__border span {
  border-style: none;
}


.question-list {
  /* 其他样式 */
  width: 88%;
}

.question-item {
  border: 1px solid #ffffff; /* 边框颜色 */
  margin-top: 5px; /* 顶部外边距 */
  margin-bottom: 5px; /* 底部外边距 */
  padding: 10px; /* 内边距 */
  background-color: #ffffff; /* 显眼但不突兀的背景色，例如浅黄色（beige） */
  /* 您可以选择其他颜色，例如浅蓝 #e0f7fa，浅绿 #e8f5e9 等 */
}

.question-title-content {
  display: flex; /* 使用Flexbox布局 */
  align-items: center; /* 垂直居中对齐 */
  white-space: nowrap; /* 防止内容换行 */
}

.question-title-content span {
  margin-left: 5px; /* 与标题内容保持一定间距 */
}

.question-options span {
  margin-right: 5px;
}

.question-actions {
  text-align: right;
  margin-top: 2px; /* 操作按钮的顶部外边距 */
}


.option-item {
  display: flex;          /* 使用Flexbox布局 */
  align-items: center;    /* 垂直居中对齐 */
}

.option-item span:first-child {
  /* 如果需要，可以在这里添加其他样式 */
}

.answer {
  /* 答案样式，可以根据需要添加 */
}
.no-horizontal-scroll {
  overflow-x: hidden;
  overflow-y: scroll; /* 允许垂直滚动 */
  -ms-overflow-style: none;  /* Internet Explorer 10+ */
  scrollbar-width: none;  /* Firefox */
}
.scrollable-content::-webkit-scrollbar {
  display: none;  /* Safari 和 Chrome */
}
.analysis-content {
  padding-left: 10px; /* 调整内边距 */
  margin-top: -14px;
}
.el-descriptions-item__content {
  display: flex;
  align-items: center; /* 垂直居中对齐内容 */
}
.question-header {
  background-color: #f37745; /* 绿色背景 */
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 4px;
}

.header-content {
  color: white;
  font-weight: bold;
  span {
    margin-right: 10px;
  }
}
</style>
