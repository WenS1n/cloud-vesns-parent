<template>
  <div class="app-container">
    讲师表单
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="eduTeacher.name" />
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number
          v-model="eduTeacher.sort"
          controls-position="right"
          :min="0"
        />
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="eduTeacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="eduTeacher.career" />
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="eduTeacher.intro" :rows="10" type="textarea" />
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="String(eduTeacher.avatar)" />
        <!-- 文件上传按钮 -->
        <el-button
          type="primary"
          icon="el-icon-upload"
          @click="imagecropperShow = true"
          >更换头像</el-button
        >

        <!--
v-show：是否显示上传组件
:key：类似于id，如果一个页面多个图片上传控件，可以做区分
:url：后台上传的url地址
@close：关闭上传组件
        @crop-upload-success：上传成功后的回调-->
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API + '/eduoss/fileoss/uploadFile'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"
        />
      </el-form-item>

      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate"
          >保存</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import ImageCropper from "@/components/ImageCropper";
import PanThumb from "@/components/PanThumb";
import teacher from "@/api/teacher";

export default {
  components: {
    ImageCropper,
    PanThumb,
  },
  data() {
    return {  
      eduTeacher: {}, //表单数据
      saveBtnDisabled: false, //按钮是否不可操作
      BASE_API: process.env.BASE_API, // 接口API地址
      imagecropperShow: false, // 是否显示上传组件
      imagecropperKey: 0, // 上传组件id
    };
  },
  created() {
    console.log("created!!!");
    this.init();
  },
  watch: {
    $route(to, from) {
      console.log("watch $route!!!");
      this.init();
    },
  },
  methods: {
    init() {
      if (this.$route.params && this.$route.params.id) {
        console.log("$route.params.id=" + this.$route.params.id);
        teacher.getTeacherById(this.$route.params.id).then((response) => {
          this.eduTeacher = response.data.eduTeacher;
        });
      } else {
        console.log("清空数据！！！！！");
        this.eduTeacher = {};
      }
    },

    saveOrUpdate() {
      if (this.eduTeacher.id) {
        //id存在、执行修改
        this.updateTeacherInfo();
      } else {
        //id不存在、执行添加
        this.saveTeacher();
      }
    },
    //添加讲师
    saveTeacher() {
      teacher.addTeacher(this.eduTeacher).then((response) => {
        //提示成功
        this.$message({
          type: "success",
          message: "添加成功!",
        });

        //路由跳转
        this.$router.push({ path: "/teacher/list" });
      });
    },
    //修改讲师
    updateTeacherInfo() {
      teacher.updateTeacher(this.eduTeacher).then((response) => {
        //提示成功
        this.$message({
          type: "success",
          message: "修改成功!",
        });

        //路由跳转
        this.$router.push({ path: "/teacher/list" });
      });
    },

    close() {
      this.imagecropperShow = false;
      // 上传失败后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
      this.imagecropperKey = this.imagecropperKey + 1;
    },
    cropSuccess(data) {
      //拿url，进行赋值
      this.eduTeacher.avatar = data.url;
      //关闭窗口
      this.close();
    },
  },
};
</script>
