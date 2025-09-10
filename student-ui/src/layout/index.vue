<template>
  <div>
    <el-container>
      <el-header>
        <!-- HTML -->
        <div class="header">
          <img v-if="logo" :src="logo" class="sidebar-logo"/>
          <div class="menu-container">
            <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
              <el-menu-item index="1">首页</el-menu-item>
              <el-menu-item index="2">考试中心</el-menu-item>
              <el-menu-item index="3">考试记录</el-menu-item>
              <el-menu-item index="4">错题集</el-menu-item>
            </el-menu>
            <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="hover" @command="handleCommand">
              <div class="avatar-wrapper">
                <img :src="avatar" class="user-avatar">
              </div>
              <el-dropdown-menu slot="dropdown">
                <router-link to="/my">
                  <el-dropdown-item>个人中心</el-dropdown-item>
                </router-link>
                <el-dropdown-item divided @click.native="logout">
                  <span>退出登录</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
      <el-footer>
        <!-- 页面底部 -->
        <footer class="footer">
          <div class="container">
            <el-row>
              <el-col :span="6">
                <h4>商务联系</h4>
                <ul>
                  <li><a href="#">Q：2754522801</a></li>
                  <li><a href="#">W：wu2754522801</a></li>
                </ul>
              </el-col>
              <el-col :span="6">
                <h4>项目仓库中心</h4>
                <ul>
                  <li><a href="https://gitee.com/wy521a/astar-education-ui">教师系统</a></li>
                  <li><a href="https://gitee.com/wy521a/astar-education-student-ui">学生系统</a></li>
                  <li><a href="https://gitee.com/wy521a/astar-education">后台系统</a></li>
                </ul>
              </el-col>
              <el-col :span="6">
                <h4>关于我们</h4>
                <ul>
                  <li><el-link type="primary">学习星考试系统</el-link></li>
                </ul>
              </el-col>
              <el-col :span="6">
                <h4>微信公众号</h4>
                <ul>
                  <li><img src="http://img.astarcloud.top/202408151728307.png" alt="astar" style="width: 50px; height: 50px;"></li>
                  <li><img src="http://img.astarcloud.top/202408151728307.png" alt="astar" style="width: 50px; height: 50px;"></li>
                  <li><img src="http://img.astarcloud.top/202408151728307.png" alt="astar" style="width: 50px; height: 50px;"></li>
                </ul>
              </el-col>
            </el-row>
          </div>

          <div class="copyright">

          </div>
        </footer>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import logoImg from '@/assets/logo/logo.png'
import { mapGetters } from 'vuex'

export default {
  name: 'Layout',
  components: {
  },
  data() {
    return {
      logo: logoImg,
      activeIndex: ''
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device'
    ]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'showSettings',
          value: val
        })
      }
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav
      }
    }
  },
  mounted() {
    if (this.avatar == null || this.avatar == '' || this.avatar == undefined) {
      this.avatar = this.logo;
    }
    let path = this.$route.path;
    let index = '1';
    if (path.includes('/index')) {
      index = '1';
    } else if (path.includes('/center')) {
      index = '2';
    } else if (path.includes('/records')) {
      index = '3';
    } else if (path.includes('/myErrors')) {
      index = '4';
    }
    this.activeIndex = index;
  },
  methods: {
    handleCommand(command) {
      this.activeIndex = '';
    },
    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    },
    handleSelect(key, keyPath) {
      let index = key;
      if (index == 1) {
        this.$router.push({path:'/index'});
      }
      if (index == 2) {
        this.$router.push({path: '/center'});
      }
      if (index == 3) {
        this.$router.push({path: '/records'});
      }
      if (index == 4) {
        this.$router.push({path: '/myErrors'});
      }
      if (index == 5) {
        // this.$router.push({path: '/my'});
      }
    },
    async logout() {
      this.$store.dispatch('LogOut').then(() => {
        location.href = '/index';
      })
    }
  }
}
</script>

<style lang="scss" scoped>

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.menu-container {
  /* 添加以下样式来确保菜单完全靠右对齐 */
  display: flex;
  justify-content: flex-end;
}

.sidebar-logo {
  width: 80px;
  height: 80px;
  margin-right: 12px;
  display: inline-block;
}

.menu-wrapper {
  flex-grow: 1; /* 让这个 div 占据剩余的空间 */
  text-align: right; /* 文本或子元素靠右对齐 */
}


.footer {
  background-color: #f4f4f4;
  padding: 20px 0;
  text-align: center;
  color: #666;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

.footer h4 {
  font-size: 16px;
  margin-bottom: 10px;
}

.footer ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.footer li {
  line-height: 24px;
}

.footer a {
  color: inherit;
  text-decoration: none;
}

.footer img {
  width: 50px;
  height: 50px;
}
.right-menu-item {
  display: inline-block;
  padding: 0 8px;
  height: 100%;
  font-size: 18px;
  color: #5a5e66;
  vertical-align: text-bottom;

  &.hover-effect {
    cursor: pointer;
    transition: background .3s;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }
}

.avatar-container {
  margin-right: 30px;

  .avatar-wrapper {
    margin-top: 5px;
    position: relative;

    .user-avatar {
      cursor: pointer;
      width: 40px;
      height: 40px;
      border-radius: 10px;
    }

    .el-icon-caret-bottom {
      cursor: pointer;
      position: absolute;
      right: -20px;
      top: 25px;
      font-size: 12px;
    }
  }
}
.el-menu.el-menu--horizontal {
  border-bottom: solid 0px #e6e6e6;
}
</style>
