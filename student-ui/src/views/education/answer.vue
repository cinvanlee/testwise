<template>
  <!--@contextmenu.prevent @selectstart.prevent @mousedown.prevent-->
  <div style="background-color: #f0f3f7">
    <el-container style="height: 100%;">
      <el-aside width="22%" class="no-horizontal-scroll">
        <el-row>
          <el-col :span="24">
            <h2>{{ paperInfo.examinationPaperName }}</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            总分：{{ paperInfo.score }} 分
          </el-col>
          <el-col :span="12">
            时长：{{ paperInfo.suggestionsMinutes }} 分钟
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="24">
            <el-row>
              <el-col :span="4" v-for="(question, index) in questionIndexList" :key="index">
                <el-button
                  :style="{ backgroundColor: answeredQuestions.includes(question.titleIndex + '-' + question.questionIndex) ? '#66c13a' : '#f3f3f3' }"
                  @click="navigateToQuestion(question.titleIndex + '-' + question.questionIndex)">
                  {{computedSubscript(question.titleIndex,question.questionIndex)}}
                </el-button>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="24">
            <avue-video
              @data-change="dataChange"
              ref="video"
            ></avue-video>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-card shadow="hover" style="width: 100%;" class="custom-countdown">
              <div style="width: 100%; display: inline-block; ">
                <el-statistic
                  :value="deadline"
                  time-indices
                  title="剩余时间"
                  id="myCountdown"
                  format="HH时mm分ss秒"
                  @finish="finish"
                  class="without-border"
                />
              </div>
            </el-card>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" class="text-center">
            <el-button type="primary" @click="submitPaperConfirm">提交试卷</el-button>
          </el-col>
        </el-row>
      </el-aside>

      <el-main>
        <basic-container>
          <el-descriptions class="margin-top" title="考生信息" :column="3" size="" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                用户名
              </template>
              {{ user.nickName }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                手机号
              </template>
              {{ user.phonenumber == null || user.phonenumber == undefined || user.phonenumber == '' ? '11111111111' : user.phonenumber }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-data-analysis"></i>
                邮箱
              </template>
              {{ user.email == null || user.email == undefined || user.email == '' ? 'young_zya@163.com' : user.email }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-home"></i>
                所在年级
              </template>
              <el-tag size="small">{{ className }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                联系
              </template>
              学习星考试系统
            </el-descriptions-item>
          </el-descriptions>
        </basic-container>
        <basic-container>
          <el-row>
            <el-col :span="24" v-for="(column, titleIndex) in paperInfo.paperTopicList" :key="titleIndex">
              <h2>{{column.title}}</h2>
              <div class="question-item"
                   v-for="(question, paperQuestionsIndex) in column.paperQuestionsList"
                   :key="paperQuestionsIndex">
                <div class="question-title-content" :id="'question-' + titleIndex + '-' + paperQuestionsIndex">
                  {{computedSubscript(titleIndex,paperQuestionsIndex)}}.
                  <span v-html="question.title"></span>
                </div>
                <span v-if="question.questionsType == 1">
                  <el-radio-group v-model="question.correctOptionKey" class="radio-group" @input="changeRadio(titleIndex,paperQuestionsIndex)">
                    <el-radio style="margin-top: 10px;" v-for="(bankColumn, bankIndex) in question.bankOptionsList"
                              :label="bankColumn.optionKey" :key="bankIndex">
                      {{ bankColumn.optionKey }}
                      <span style="float: right;margin-left: 5px;margin-top: -14px" v-html="bankColumn.optionName"></span>
                    </el-radio>
                  </el-radio-group>
                </span>
                <span v-if="question.questionsType == 2">
                   <el-checkbox-group v-model="question.correctOptionKey" @change="changeCheckbox(titleIndex,paperQuestionsIndex)">
                      <el-checkbox v-for="(bankColumn,bankIndex) in question.selectList" :label="bankColumn"
                                   :key="bankIndex">{{ bankColumn }}
                        <span style="float: right;margin-left: 5px;margin-top: -14px" v-html="bankColumn.optionName"></span>
                      </el-checkbox>
                   </el-checkbox-group>
                </span>
                <span v-if="question.questionsType == 3">
                   <el-radio-group v-model="question.correctOptionKey" class="radio-group" @input="changeRadio(titleIndex,paperQuestionsIndex)">
                    <el-radio style="margin-top: 10px;" v-for="(bankColumn, bankIndex) in question.bankOptionsList"
                              :label="bankColumn.optionKey" :key="bankIndex">
                      {{ bankColumn.optionKey }}
                      <span style="float: right;margin-left: 5px;margin-top: -14px" v-html="bankColumn.optionName"></span>
                    </el-radio>
                  </el-radio-group>
                </span>
                <span v-if="question.questionsType == 4">
                  <div>
                    <el-row>
                      <el-col :span="24">
                        <span style="float: left;margin-left: 10px;margin-top: -14px;background-color: #f3f3f3" v-html="question.correctOptionKey"></span>
                      </el-col>
                    </el-row>
                     <el-row>
                      <el-col :span="24">
                        <el-input @focus="openOptionDialog(titleIndex,paperQuestionsIndex)" style="width: 300px; margin-left: 10px;margin-top: 10px"
                                  v-model="question.correctOptionKey"
                                  :rows="4"
                                  type="textarea"
                        ></el-input>
                      </el-col>
                    </el-row>

                  </div>
                </span>
                <span v-if="question.questionsType == 5">
                  <div v-for="(bankColumn, bankIndex) in question.bankOptionsList" :key="bankIndex" class="option-item"
                       style="margin-top: 5px;margin-left: 20px">
                    <span>({{ bankColumn.optionKey }}). </span>
                    <el-row>
                      <el-col :span="12">
                        <el-input @focus="openOptionBankDialog(titleIndex,paperQuestionsIndex,bankIndex)" style="width: 300px; margin-left: 10px;margin-top: 10px"
                                  v-model="bankColumn.answer"
                                  :rows="3"
                                  type="textarea"
                        ></el-input>
                      </el-col>
                      <el-col :span="12">
                        <span class="answer" style="margin-left: 5px; display: inline-block;float: right" v-html="bankColumn.answer"></span>
                      </el-col>
                    </el-row>
                  </div>
                </span>
              </div>
            </el-col>
          </el-row>
        </basic-container>
      </el-main>
    </el-container>

    <el-dialog
      title=""
      :visible.sync="dialogVisible"
      width="80%"
      center
    >
      <tinymce v-model="editingModelName" :height="300" placeholder="请输入答案"/>
      <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="confirmEdit">确 定</el-button>
    </span>
    </el-dialog>

    <el-dialog
      title=""
      :visible.sync="dialogBankVisible"
      width="80%"
      center
    >
      <tinymce v-model="editingBankModelName" :height="300" placeholder="请输入答案"/>
      <span slot="footer" class="dialog-footer">
      <el-button @click="dialogBankVisible = false">取 消</el-button>
      <el-button type="primary" @click="confirmBankEdit">确 定</el-button>
    </span>
    </el-dialog>
  </div>
</template>

<script>

import BasicContainer from '@/components/basic-container/main.vue'
import { studentPaperInfo, studentPaperPage } from '@/api/education/paper'
import { studentDisciplineList } from '@/api/education/discipline'
import { getUserProfile } from '@/api/system/user'
import { getClass } from '@/api/education/class'
import { addPaperRecord, editPaperRecord } from '@/api/education/record'

export default {
  name: 'Answer',
  components: {
    BasicContainer
  },
  data() {
    return {
      id: '',
      paperInfo: {},
      deadline: null,
      user: {},
      className: '',
      form: {},
      selectList:[],
      dialogVisible:false,
      editingModelName:'',
      titleIndex:'',
      paperQuestionsIndex:'',
      bankIndex:'',
      dialogBankVisible:false,
      editingBankModelName:'',
      startTime: new Date(), // 存储开始时间
      elapsedTime: 0,   // 存储经过的时间
      answeredQuestions:[],
      questionIndexList:[],
    }
  },
  computed: {},
  created() {
    this.getUser()
  },
  mounted() {
    this.id = this.$route.params.id
    this.studentPaperInfo()
    this.startRecord()
    // 记录开始时间
    this.startTime = new Date();
  },
  beforeDestroy() {
    this.stopRecord()
  },
  destroyed() {
    this.stopRecord()
  },
  methods: {
    openOptionBankDialog(titleIndex, paperQuestionsIndex, bankIndex) {
      this.editingBankModelName = this.paperInfo.paperTopicList[titleIndex].paperQuestionsList[paperQuestionsIndex].bankOptionsList[bankIndex].answer;
      this.titleIndex = titleIndex;
      this.paperQuestionsIndex = paperQuestionsIndex;
      this.bankIndex = bankIndex;
      this.dialogBankVisible = true
    },
    confirmBankEdit() {
      this.paperInfo.paperTopicList[this.titleIndex].paperQuestionsList[this.paperQuestionsIndex].bankOptionsList[this.bankIndex].answer = this.editingBankModelName;
      if (this.editingBankModelName != '' || this.editingBankModelName != undefined) {
        this.updateAnsweredStatus(this.titleIndex, this.paperQuestionsIndex)
      }
      this.titleIndex = '';
      this.paperQuestionsIndex = '';
      this.editingModelName = ''
      this.editingBankModelName = '';
      this.dialogBankVisible = false
    },
    confirmEdit() {
      this.paperInfo.paperTopicList[this.titleIndex].paperQuestionsList[this.paperQuestionsIndex].correctOptionKey = this.editingModelName;
      if (this.editingModelName != '' || this.editingModelName != undefined) {
        this.updateAnsweredStatus(this.titleIndex, this.paperQuestionsIndex)
      }
      this.titleIndex = '';
      this.paperQuestionsIndex = '';
      this.editingModelName = ''
      this.dialogVisible = false
    },
    openOptionDialog(titleIndex, paperQuestionsIndex) {
      this.editingModelName = this.paperInfo.paperTopicList[titleIndex].paperQuestionsList[paperQuestionsIndex].correctOptionKey
      this.titleIndex = titleIndex;
      this.paperQuestionsIndex = paperQuestionsIndex;
      this.dialogVisible = true
    },
    getUser() {
      getUserProfile().then(response => {
        this.user = response.data
        if (this.user.gradeClassId != null && this.user.gradeClassId != '' && this.user.gradeClassId != undefined) {
          getClass(this.user.gradeClassId).then(res => {
            this.className = res.data.gradeClassName
          })
        }
      })
    },
    finish() {
      console.log('倒计时完成');
      this.$modal.msgSuccess("倒计时完成，即将自动提交!");
      this.submitPaper();
    },
    studentPaperInfo() {
      studentPaperInfo(this.id).then(res => {
        this.paperInfo = res.data
        this.dataConversion();
        this.deadline = Date.now() + Number(this.paperInfo.suggestionsMinutes) * 1000 * 60
      })
    },
    dataConversion() {
      this.paperInfo.paperTopicList.forEach(topic => {
        topic.paperQuestionsList.forEach(item => {
          if (item.questionsType == 2) {
            item.correctOptionKey = []
            item.selectList = item.bankOptionsList.map(bank => bank.optionKey);
          }
          item.questionsBankId = item.questionBankId;
          item.questionBankId = item.questionBankId;
          item.optionKey = item.correctOptionKey;
        });
      });
      // 题目下标转换
      let index = 1;
      for (let i = 0; i < this.paperInfo.paperTopicList.length; i++) {
        for (let j = 0; j < this.paperInfo.paperTopicList[i].paperQuestionsList.length; j++) {
          let questionIndexObj = {
            titleIndex : i,
            questionIndex: j,
          };
          this.questionIndexList.push(questionIndexObj);
        }
      }
    },
    submitPaperConfirm() {
      this.$confirm('确认提交?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.submitPaper();
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    },
    submitPaper() {
      const endTime = new Date();
      const diffTime = endTime - this.startTime; // 时间差以毫秒为单位
      const minutes = Math.floor(diffTime / 60000); // 转换为分钟
      const seconds = ((diffTime % 60000) / 1000).toFixed(0); // 剩余的秒数
      let timeStr = `${minutes} 分钟 ${seconds} 秒`;
      console.log(this.startTime,endTime)
      console.log(`完成试卷用时: ${timeStr}`);
      this.paperInfo.timeConsuming = timeStr;
      this.paperInfo.paperTopicList.forEach(topic => {
        topic.paperQuestionsList.forEach(question => {
          if (question.questionsType ==2 ) {
            question.correctOptionKey = question.correctOptionKey.join(",");
          }
        })
      })
      addPaperRecord(this.paperInfo).then(response => {
        this.$modal.msgSuccess("提交完成");
      });
      console.log('提交')
      this.$router.push({path: '/records'});
    },
    startRecord() {
      this.$refs.video.startRecord()
    },
    stopRecord() {
      this.$refs.video.stopRecord()
    },
    dataChange(data) {
      console.log(data)
    },
    updateAnsweredStatus(titleIndex,questionIndex) {
      if (!this.answeredQuestions.includes(titleIndex + '-' + questionIndex)) {
        this.answeredQuestions.push(titleIndex + '-' + questionIndex);
      }
    },
    computedSubscript(titleIndex,questionsIndex) {
      let index = 1;
      for (let i = 0; i < this.paperInfo.paperTopicList.length; i++) {
        for (let j = 0; j < this.paperInfo.paperTopicList[i].paperQuestionsList.length; j++) {
          if (titleIndex == i && questionsIndex == j) {
            return index;
          } else {
            index += 1;
          }
        }
      }
    },
    changeRadio(titleIndex,questionsIndex){
      this.updateAnsweredStatus(titleIndex,questionsIndex);
    },
    changeCheckbox(titleIndex,questionsIndex){
      this.updateAnsweredStatus(titleIndex,questionsIndex);
    },
    changeInput(value, titleIndex,questionsIndex){
      console.log(value, titleIndex);
    },
    navigateToQuestion(index){
      // 使用模板字符串构造ID
      const elementId = `question-${index}`;
      const element = document.getElementById(elementId);
      if (element) {
        // 平滑滚动到指定元素
        element.scrollIntoView({ behavior: 'smooth' });
      } else {
        console.warn(`Element with id ${elementId} not found.`);
      }
    },
  }
}
</script>

<style scoped>
.el-container {
  position: relative; /* 确保 el-container 是相对定位 */
  min-height: 100vh; /* 设置容器的最小高度为视口高度 */
}

.el-aside {
  position: fixed; /* 将 el-aside 固定定位 */
  top: 0; /* 从顶部开始 */
  left: 8%; /* 左侧对齐 */
  bottom: 0; /* 底部对齐 */
  width: 22%; /* 设置宽度为 200px */
  overflow-y: auto; /* 如果内容超出，显示滚动条 */
  background-color: #ffffff;
}

.el-main {
  padding-left: 32%; /* 在左侧预留 200px 的空间给 el-aside */
  overflow-y: auto; /* 如果内容超出，显示滚动条 */
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
</style>
