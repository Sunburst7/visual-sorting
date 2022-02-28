<template>
  <div class="main">
    <el-container>
        <el-aside width="1000px">
          <div id="myCharts" style="width: 1000px;height: 720px">
          </div>
        </el-aside>
        <el-container>
            <el-main style="height:100vh">
              <el-card class="box-card">
                  <template #header>
                    <div class="card-header">
                      <span>排序选项(每个数据用英文逗号隔开)</span>
                      <el-button class="button" type="text" @click="clean">清空</el-button>
                    </div>
                  </template>
                  <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item label="排序数据">
                      <el-input type="textarea" v-model="form.initialData" resize="none" rows="5"></el-input>
                    </el-form-item>

                    <el-form-item label="数据类型">
                      <el-select v-model="form.dataType" placeholder="请选择数据类型">
                        <el-option
                          v-for="item in this.form.options2"
                          :key="item.dataType"
                          :label="item.label"
                          :value="item.dataType">
                        </el-option>
                      </el-select>
                    </el-form-item>

                    <el-form-item label="排序顺序">
                      <el-radio-group v-model="form.order">
                        <el-radio label="升序" value="1"></el-radio>
                        <el-radio label="降序" value="2"></el-radio>
                        <el-radio label="字典排序" value="3"></el-radio>
                      </el-radio-group>
                    </el-form-item>

                    <el-form-item label="排序方法">
                      <el-select v-model="form.sortMethod" placeholder="请选择">
                        <el-option
                          v-for="item in this.form.options"
                          :key="item.sortMethod"
                          :label="item.label"
                          :value="item.sortMethod">
                        </el-option>
                      </el-select>
                    </el-form-item>

                    <el-form-item label="演示间隔">
                      <el-slider
                        v-model="form.speed"
                        :step="0.5"
                        :max="10"
                        :format-tooltip="formatTooltip"
                        show-input>
                      </el-slider>
                    </el-form-item>
                    <el-form-item label="演示模式">
                      <el-select v-model="form.model" placeholder="请选择演示模式">
                        <el-option label="单步" value="one-step"></el-option>
                        <el-option label="连续" value="continuous"></el-option>
                      </el-select>
                    </el-form-item>

                    <el-form-item label="演示操作">
                      <el-button :disabled="isDisabled(form.model)" @click="next">forward</el-button>
                      <el-button :disabled="isDisabled(form.model)" @click="before">backward</el-button>
                      <el-button :disabled="!isDisabled(form.model)" @click="pause">pause</el-button>
                    </el-form-item>

                    <el-form-item>
                      <el-button type="primary" @click="onSubmit">开始排序</el-button>
                      <el-button @click="pause">取消</el-button>
                    </el-form-item>
                  </el-form>
              </el-card>

            </el-main>
        </el-container>
    </el-container>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import axios from 'axios';

let basePost = axios.create({
  method: 'post',
  headers:{
    "Access-Control-Allow-Origin":"*",
  },
  baseURL:'http://localhost:8090',
})
export default {
  name: "mainWindow",
  data() {
    return {
      form: {
        initialData: '',
        order:'',
        options: [{
          sortMethod: 'bubbleSort',
          label: '冒泡排序'
        }, {
          sortMethod: 'insertSort',
          label: '插入排序'
        }, {
          sortMethod: 'quickSort',
          label: '快速排序'
        }],
        options2: [{
          dataType: 'String',
          label: '字符串'
        }, {
          dataType: 'Integer',
          label: '整数'
        }],
        sortMethod:'',
        dataType:'',
        speed:2,
        model:'',
      },
      datalist:'',
      myCharts:null,
      stepTimer:null,
      jsonGroups:null,
      stepNum:0,
      Timers:null,
    }
  },
  computed:{
    int_order:function (){
      if(this.form.order === '升序')
        return 1
      else if(this.form.order === '降序')
        return 2
      else if(this.form.order === '字典排序')
        return 1
    }
  },
  watch:{
    datalist:{
      handler(newVal,oldVal){
        console.log(oldVal+'->'+newVal)
        //设置图标横坐标
        let xAxisData = []
        for(let i = 1;i<=newVal.length;i++){
          xAxisData.push('第'+i+'位')
        }
        // 绘制图表
        this.myCharts.setOption({
          title: {
            text: '排序演示图'
          },
          tooltip: {},
          xAxis:{
            data:xAxisData
          },
          yAxis: {},
          series: [{
            name: this.strData,
            type: 'bar',
            data: this.datalist
          }]
        });
      },
      deep:true
    }

  },
  methods: {
    //设置演示操作按钮的Disable
    isDisabled(val) {
      return val === 'continuous';
    },
    //清空按钮
    clean() {
      this.form.initialData = ''
      this.form.order = ''
      this.form.sortMethod = ''
      this.form.speed = 1
      this.form.model = ''
      this.datalist = ''
      this.myCharts = null
    },
    //速度进度标签格式化
    formatTooltip(val) {
      return val + '秒';
    },
    //表格配置初始化
    initCharts() {
      console.log('charts init start')
      // 基于准备好的dom，初始化echarts实例
      this.myCharts = echarts.init(document.getElementById('myCharts'), 'dark')
      this.datalist = this.form.initialData.split(',')
      let xAxisData = []
      for (let i = 1; i <= this.datalist.length; i++) {
        xAxisData.push('第' + i + '位')
      }
      // 绘制图表
      this.myCharts.setOption({
        title: {
          text: '排序演示图'
        },
        tooltip: {},
        xAxis: {
          data: xAxisData
        },
        yAxis: {},
        series: [{
          name: this.strData,
          type: 'bar',
          data: this.datalist
        }]
      });
      console.log('chart init end')
    },

    //判断一个值是否为数字
    checkNumber(theObj) {
      let reg = /^[0-9]*$/;
      return reg.test(theObj);
    },

    //对一个数组进行预处理
    analyze(data){
      for(let i = 0;i<data.length;i++){
        if(!this.checkNumber(data[i])){
          // let str = String(data[i])
          data[i] = data[i].charCodeAt(0)
        }
      }
      return data
    },

    //开始排序
    onSubmit() {
      if (!this.myCharts) {
        this.initCharts();
      }
      if (this.form.order === '字典排序')
        this.form.dataType = 'String'
      console.log(this.form.initialData + '\n' + this.form.order + '\n' + this.form.sortMethod + '\n' + this.form.speed + '\n' + this.form.dataType + '\n')
      // 判断是否查询到数据是否为空
      if (this.form.initialData === '') {
        this.$message({
          type: 'warning',
          message: `排序数据为空`
        });
        return
      }
      //根据不同的方法发送不同的请求
      if (this.form.sortMethod === 'bubbleSort') {
        // 使用 axios发送请求
        basePost.post('/BubbleSort', {
          'dataList': this.form.initialData,
          'dataType': this.form.dataType,
          'model': this.int_order
        }).then(successResponse => {
          if (successResponse.status === 200) {
            // 传入一个json数组
            this.jsonGroups = successResponse.data

            //持续进行排序
            if (this.form.model === 'continuous') {
              for (let i = 0; i < this.jsonGroups.length; i++) {
                this.Timers = []
                let Timer = setTimeout(() => {
                  this.datalist = this.jsonGroups[i].substring(1, this.jsonGroups[i].length - 1).split(',');
                }, 1000 * this.form.speed * i)
                this.Timers.push(Timer)
              }
            } else {
              //单步进行排序,初始化stepNum
              this.stepNum = 0
              this.datalist = this.jsonGroups[this.stepNum].substring(1, this.jsonGroups[this.stepNum].length - 1).split(',');
            }
          }
        }).catch(failResponse => {
          this.$message({
            type: 'error',
            message: '排序失败'
          })
        })
      } else if (this.form.sortMethod === 'insertSort') {
        // 使用 axios发送请求
        basePost.post('/InsertSort', {
          'dataList': this.form.initialData,
          'dataType': this.form.dataType,
          'model': this.int_order
        }).then(successResponse => {
          if (successResponse.status === 200) {
            // 传入一个json数组
            this.jsonGroups = successResponse.data

            //持续进行排序
            if (this.form.model === 'continuous') {
              for (let i = 0; i < this.jsonGroups.length; i++) {
                this.Timers = []
                //没有按下暂停
                let Timer = setTimeout(() => {
                  this.datalist = this.jsonGroups[i].substring(1, this.jsonGroups[i].length - 1).split(',');
                }, 1000 * this.form.speed * i)
                this.Timers.push(Timer)
              }
            } else {
              //单步进行排序,初始化stepNum
              this.stepNum = 0
              this.datalist = this.jsonGroups[this.stepNum].substring(1, this.jsonGroups[this.stepNum].length - 1).split(',');

            }

          }
        }).catch(failResponse => {
          this.$message({
            type: 'error',
            message: '排序失败'
          })
        })
      } else if (this.form.sortMethod === 'quickSort') {
        // 使用 axios发送请求
        basePost.post('/QuickSort', {
          'dataList': this.form.initialData,
          'dataType': this.form.dataType,
          'model': this.int_order
        }).then(successResponse => {
          if (successResponse.status === 200) {
            // 传入一个json数组
            this.jsonGroups = successResponse.data
            //持续进行排序
            if (this.form.model === 'continuous') {
              for (let i = 0; i < this.jsonGroups.length; i++) {
                this.Timers = []
                let Timer = setTimeout(() => {
                  this.datalist = this.jsonGroups[i].substring(1, this.jsonGroups[i].length - 1).split(',');
                }, 1000 * this.form.speed * i)
                this.Timers.push(Timer)
              }
            } else {
              //单步进行排序,初始化stepNum
              this.stepNum = 0
              this.datalist = this.jsonGroups[this.stepNum].substring(1, this.jsonGroups[this.stepNum].length - 1).split(',');
            }
          }
        }).catch(failResponse => {
          this.$message({
            type: 'error',
            message: '排序失败'
          })
        })
      }
    },

    //点击下一步
    next() {
      //保证下一次执行在正确的位置
      if (this.stepNum === this.jsonGroups.length - 1) {
        this.$message({
          type: 'warning',
          message: '演示已结束'
        })
      } else {
        this.stepNum++;
      }
      this.datalist = this.jsonGroups[this.stepNum].substring(1, this.jsonGroups[this.stepNum].length - 1).split(',');
    },

    //点击上一步
    before() {
      //保证下一次执行在正确的位置
      if (this.stepNum === 0) {
        this.$message({
          type: 'warning',
          message: '已回到初始状态'
        })
      } else {
        this.stepNum--;
      }
      this.datalist = this.jsonGroups[this.stepNum].substring(1, this.jsonGroups[this.stepNum].length - 1).split(',');
    },

    //点击暂停
    pause() {
      //按下暂停键
      for (let m = 0; m < this.Timers.length; m++) {
        clearTimeout(this.Timers[m])
      }
      this.$message({
        type: 'warning',
        message: '排序已暂停,请重新开始排序'
      })
    },
  },

}
</script>

<style scoped>
.main{
  display: flex;
  text-align: center;
  align-items: center;
  margin-top: 0;
  padding: 0;
}
.el-header, .el-footer {
  text-align: center;
  padding: 0;
}
.el-main {
  color: #333;
  padding: 0;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.box-card{
  margin: 8px;
  height: 704px;
}
</style>
