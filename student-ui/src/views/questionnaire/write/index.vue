<template>
  <div class="app-container" style="background-color: white">



  </div>
</template>

<script>
import Parser from 'form-gen-parser'
export default {
  name: "QuestionnaireWrite",
  components: {
    Parser
  },
  data() {
    return {
      url : null,
      title:'',
      data: {
      },
      key2: +new Date(),
      formConf: null,
      remark:null,
      list:[
        {index:1, href:'https://gitee.com/wy521a',text:'Gitee开源仓库地址'},
        {index:2, href:'https://qm.qq.com/cgi-bin/qm/qr?k=X56d9Mldla4qZQ6cQT_aRwNhbC2jypaT&jump_from=webapi&authKey=v3nb6/vrsRtmLID7D//v7tAVCVBG6YDG0bYYL3xJYEzj6QIZPw6S/knMZ/g8WwG2',text:'官方Q群：258695438'},
        {index:3, href:'',text:'微信公众号：一颗星宇宙'}
      ],
      qsId:null,
      endFlag: false,
    };
  },
  mounted() {

  },
  methods: {
  }
};
</script>
<style lang="scss" scoped>
.test-form {
  margin: 15px auto;
  width: 800px;
  padding: 15px;
}
</style>
<style>
.bordered-text {
  border-left: 4px solid lightgrey;
  padding-left: 10px;
}
.el-header, .el-footer {
  background-color: white;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: white;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: white;
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 100px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}



@media screen and (max-width: 500px) {
  .el-main .test-form {
    margin: 15px -33px auto;
    width: 317px;
    /* padding: 15px; */
  }
}


</style>
