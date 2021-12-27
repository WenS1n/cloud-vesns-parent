<template>
  <div class="cart py-container">
    <!--主内容-->
    <div class="checkout py-container pay">
      <div class="checkout-tit">
        <h4 class="fl tit-txt">
          <span class="success-icon"></span>
          <span class="success-info">订单提交成功，请您及时付款！订单号：{{payObj.out_trade_no}}</span>
        </h4>
        <span class="fr">
          <em class="sui-lead">应付金额：</em>
          <em class="orange money">￥{{payObj.total_fee}}</em>
        </span>
        <div class="clearfix"></div>
      </div>
      <div class="checkout-steps">
        <div class="fl weixin">微信支付</div>
        <div class="fl sao">
          <p class="red">请使用微信扫一扫。</p>
          <div class="fl code">
            <!-- <img id="qrious" src="~/assets/img/erweima.png" alt=""> -->
            <!-- <qriously value="weixin://wxpay/bizpayurl?pr=R7tnDpZ" :size="338"/> -->
            <qriously :value="payObj.code_url" :size="338"/>
            <div class="saosao">
              <p>请使用微信扫一扫</p>
              <p>扫描二维码支付</p>
            </div>
          </div>
        </div>
        <div class="clearfix"></div>
        <!-- <p><a href="pay.html" target="_blank">> 其他支付方式</a></p> -->
      </div>
    </div>
  </div>
</template>
<script>
import pay from "@/api/pay";

export default {
  //异步请求调用
  asyncData({ params, error }) {
    return pay.createNative(params.pid).then(response => {
      console.log(response.data.data);
      return {
        orderNo: params.pid,
        payObj: response.data.data
      };
    });
  },
  //每隔5秒钟，查询一下订单支付状态
  //需要创建定时器setIntervar(()=>{},5000)
  data() {
    return {
      timer1: ""
    };
  },
  mounted() {
    this.timer1 = setInterval(() => {
      this.queryPayStatusInfo();
    }, 5000);
  },
  // 页面加载完成后
  methods: {
    queryPayStatusInfo() {
      pay.queryPayStatus(this.orderNo).then(response => {
        if (response.data.success) {
          //如果支付成功，清除定时器
          clearInterval(this.timer1);
          //提示成功
          this.$message({
            type: "success",
            message: "支付成功!"
          });
          //跳转到课程详情页面观看视频
          this.$router.push({ path: "/course/" + this.payObj.course_id });
        }
      });
    }
  }
};
</script>
