<template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>

    <el-steps
      :active="1"
      process-status="wait"
      align-center
      style="margin-bottom: 40px"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="提交审核" />
    </el-steps>

    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input
          v-model="courseInfo.title"
          placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"
        />
      </el-form-item>

      <!-- 所属分类：级联下拉列表 -->
      <el-form-item label="课程类别">
        <!-- 一级分类 -->
        <el-select
          @change="getTwoByOneId(courseInfo.subjectParentId)"
          v-model="courseInfo.subjectParentId"
          placeholder="一级分类"
        >
          <el-option
            v-for="subject in oneSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
        <!-- 一级分类 -->
        <el-select
          @change="getTwoByOneId(courseInfo.subjectId)"
          v-model="courseInfo.subjectId"
          placeholder="二级分类"
        >
          <el-option
            v-for="subject in twoSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
      </el-form-item>

      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select v-model="courseInfo.teacherId" placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number
          :min="0"
          v-model="courseInfo.lessonNum"
          controls-position="right"
          placeholder="请填写课程的总课时数"
        />
      </el-form-item>

      <!-- 课程简介 TODO -->
      <el-form-item label="课程简介">
        <el-input v-model="courseInfo.description" />
      </el-form-item>

      <!-- 课程封面-->

      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API + '/eduoss/fileoss/uploadFile'"
          class="avatar-uploader"
        >
          <img :src="courseInfo.cover" style="width:500px;height:240px" />
        </el-upload>
      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number
          :min="0"
          v-model="courseInfo.price"
          controls-position="right"
          placeholder="免费课程请设置为0元"
        />元
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next"
          >保存并下一步</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import course from "@/api/course";
import teacher from "@/api/teacher";
import subject from "@/api/subject";
export default {
  data() {
    return {
      BASE_API: process.env.BASE_API, // 接口API地址
      saveBtnDisabled: false, // 保存按钮是否禁用
      courseInfo: {
        subjectId: "",
        cover: "/static/1.jpg",
      }, // 课程基本信息

      teacherList: [], // 讲师下拉选择框
      oneSubjectList: [], //一级分类
      twoSubjectList: [], // 二级分类
    };
  },
  created() {
    this.getTeacherList();
    this.getAllSubjectInfo();
  },
  methods: {
    //讲师初始化方法
    getTeacherList() {
      teacher.getAllTeacher().then((response) => {
        this.teacherList = response.data.list;
      });
    },
    // 初始化一级分类
    getAllSubjectInfo() {
      subject.getAllSubject().then((response) => {
        this.oneSubjectList = response.data.allSubject;
      });
    },
    // 根据一级id查询二级分类
    getTwoByOneId(oneId) {
      // console.log(oneId)
      for (let i = 0; i < this.oneSubjectList.length; i++) {
        let oneSubject = this.oneSubjectList[i];
        if (oneSubject.id === oneId) {
          this.twoSubjectList = oneSubject.children;
          this.courseInfo.subjectId = "";
        }
      }
    },
    //上传成功后方法
    handleAvatarSuccess(res) {
      this.courseInfo.cover = res.data.url;
    },
    //校验课程封面
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    next() {
      course.addCourseInfo(this.courseInfo).then((response) => {
        this.$message({
          type: "success",
          message: "添加成功!",
        });
        //this.$router.push({ path: '/edu/course/chapter/1' })
      });
    },
  },
};
</script>

<style>
</style>