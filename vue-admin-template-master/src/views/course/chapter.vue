<template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>

    <el-steps
      :active="2"
      process-status="wait"
      align-center
      style="margin-bottom: 40px"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="提交审核" />
    </el-steps>

    <el-button type="text" @click="openAddChapter()">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chanpterList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
            <el-button type="text" @click="openAddVideo(chapter.id)"
              >添加小节</el-button
            >
            <el-button style type="text" @click="openUpdateChapter(chapter.id)"
              >编辑</el-button
            >
            <el-button type="text" @click="delChapterInfo(chapter.id)"
              >删除</el-button
            >
          </span>
        </p>

        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>
              {{ video.title }}
              <span class="acts">
                <el-button type="text" @click="openUpdateVideo(video.id)"
                  >编辑</el-button
                >
                <el-button type="text" @click="delVideoInfo(video.id)"
                  >删除</el-button
                >
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>
    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next"
        >下一步</el-button
      >
    </div>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="eduChapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="eduChapter.title" />
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number
            v-model="eduChapter.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="eduVideo" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="eduVideo.title" />
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number
            v-model="eduVideo.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="eduVideo.isFree">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API + '/eduvod/vod/uploadVideo'"
            :limit="1"
            class="upload-demo"
          >
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">
                最大支持1G，
                <br />支持3GP、ASF、AVI、DAT、DV、FLV、F4V、
                <br />GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、
                <br />MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、
                <br />SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question" />
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button
          :disabled="saveVideoBtnDisabled"
          type="primary"
          @click="saveOrUpdateVideo"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import chapter from "@/api/chapter";
import video from "@/api/video";
export default {
  data() {
    return {
      handleVodRemove: null,
      beforeVodRemove: null,
      courseId: "",
      saveBtnDisabled: false,
      chapterVideoList: [], // 大纲列表数据
      dialogChapterFormVisible: false, //章节对话框是否展示
      eduChapter: {}, //章节表单
      saveVideoBtnDisabled: false,
      dialogVideoFormVisible: false,
      eduVideo: {},
      fileList: [], //上传文件列表
      BASE_API: process.env.BASE_API, // 接口API地址
    };
  },
  created() {
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id;
      this.getChapterVideoList();
    }
  },
  methods: {
    // 根据课程id查询章节信息、小节信息
    getChapterVideoList() {
      chapter.getChapterVideoByCourseId(this.courseId).then((response) => {
        this.chapterVideoList = response.data.chapterVideoList;
      });
    },
    // 打开章节对话框
    openAddChapter() {
      this.eduChapter = {};
      this.dialogChapterFormVisible = true;
    },
    saveOrUpdate() {
      if (this.eduChapter.id) {
        //修改
        this.updateChapterInfo();
      } else {
        this.saveChapter();
      }
    },

    //添加章节信息
    saveChapter() {
      // 把课程id存入eduChapter
      this.eduChapter.courseId = this.courseId;
      chapter.addChapter(this.eduChapter).then((response) => {
        this.$message({
          type: "success",
          message: "添加成功",
        });
        // 关闭对话框
        this.dialogChapterFormVisible = false;
        // 刷新页面
        this.getChapterVideoList();
      });
    },

    // 删除章节信息
    delChapterInfo(chapterId) {
      this.$confirm("此操作将永久删除该章节, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          chapter.deleteChapter(chapterId).then((response) => {
            //删除成功
            console.log("删除成功");
            this.$message({
              type: "success",
              message: "删除成功!",
            });
            //刷新大纲
            this.getChapterVideoList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },

    // 打开对话框修改章节
    openUpdateChapter(chapterId) {
      chapter.getChapterById(chapterId).then((response) => {
        this.eduChapter = response.data.eduChapter;
        this.dialogChapterFormVisible = true;
      });
    },

    // 修改章节信息
    updateChapterInfo() {
      chapter.updateChapter(this.eduChapter).then((response) => {
        this.$message({
          type: "success",
          message: "修改成功",
        });
        this.dialogChapterFormVisible = false;
        // 刷新
        this.getChapterVideoList();
      });
    },
    //打开添加小节对话框
    openAddVideo(chapterId) {
      this.eduVideo = {};
      this.eduVideo.chapterId = chapterId;
      this.dialogVideoFormVisible = true;
    },
    //修改小节回显
    openUpdateVideo(videoId) {
      video.getVideoById(videoId).then((response) => {
        this.eduVideo = response.data.eduVideo;
        this.dialogVideoFormVisible = true;
      });
    },
    //添加或修改小节信息
    saveOrUpdateVideo() {
      if (this.eduVideo.id) {
        //修改
        this.updateVideoInfo();
      } else {
        //添加
        this.saveVideo();
      }
    },
    //添加小节
    saveVideo() {
      //1把课程id存入eduVideo
      this.eduVideo.courseId = this.courseId;
      //2调用api添加章节
      console.log(this.eduVideo);
      video.addVideo(this.eduVideo).then((response) => {
        this.$message({
          type: "success",
          message: "添加成功!",
        });
        //3关闭对话框
        this.dialogVideoFormVisible = false;
        //4刷新大纲页面
        this.getChapterVideoList();
      });
    },
    //修改小节
    updateVideoInfo() {
      //1调用api修改章节
      video.updateVideo(this.eduVideo).then((response) => {
        this.$message({
          type: "success",
          message: "修改成功!",
        });
        //2关闭对话框
        this.dialogVideoFormVisible = false;
        //3刷新大纲页面
        this.getChapterVideoList();
      });
    },

    //删除小节
    delVideoInfo(videoId) {
      this.$confirm("此操作将永久删除该小节, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          video.delVideo(videoId).then((response) => {
            this.$message({
              type: "success",
              message: "删除成功!",
            });
            this.getChapterVideoList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    //成功回调
    handleVodUploadSuccess(response, file, fileList) {
      this.eduVideo.videoSourceId = response.data.videoId;
    },
    //视图上传多于一个视频
    handleUploadExceed(files, fileList) {
      this.$message.warning("想要重新上传视频，请先删除已上传的视频");
    },

    previous() {
      console.log("previous");
      this.$router.push({ path: `/course/add/${this.courseId}` });
    },

    next() {
      console.log("next");
      this.$router.push({ path: `/course/publish/${this.courseId}` });
    },
  },
};
</script>

<style>
.chanpterList {
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
}
.chanpterList li {
  position: relative;
}
.chanpterList p {
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #ddd;
}
.chanpterList .acts {
  float: right;
  font-size: 14px;
}

.videoList {
  padding-left: 50px;
}
.videoList p {
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #ddd;
}
</style>