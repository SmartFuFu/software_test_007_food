<template>
  <nut-config-provider :theme-vars="themeVars">
    <nut-navbar left-show @click-back="clickBack">
      <template #content> 我的订单 </template>
    </nut-navbar>
    <nut-tabs v-model="state" :animated-time="0" @click="changeType">
      <nut-tab-pane title="全部" pane-key="1">
        <nut-row v-for="indent in indentData" :key="indent.ind_id" >
          <nut-cell style="background-color: #FBFCFA;">
          <nut-row>
            <!-- <nut-col :span="24"> -->
            <!-- <div> -->
              <div v-for="(commodity,index) in indent.commodity" :key="index" >
                <span style="font-family: Source Han Sans SC;
                  font-size: 14px;
                  font-weight: 400;
                  line-height: 16px;
                  letter-spacing: 1px;
                  text-align: center;
                ">
                  {{ commodity.com_name }}
                </span>

              <span style="font-family: Source Han Sans SC;
                font-size: 12px;
                font-weight: 400;
                line-height: 16px;
                letter-spacing: 0px;
                text-align: left;
                margin-left: 50px;
              ">
              {{ "x"+commodity.com_quantity }}
               </span>
            </div>
            <div>
            {{ indent.com_position }}
          </div>
          <nut-col :span="16">
            <nut-tag color="#EBF5EA" style="color: black;" round> {{ indent.ind_state }} </nut-tag>
          </nut-col>
          <nut-col :span="8">
            <nut-tag color="#EBF5EA" style="color: black;" round plain @click="goDetail(indent.ind_id)"> 查看详情 </nut-tag>
            <nut-tag color="#EBF5EA" style="color: black;" round plain @click="gradeIndent(indent.ind_id)" v-if="indent.ind_state==='确认收货' || indent.ind_state==='已核销'"> 评价 </nut-tag>
            <nut-tag color="#EBF5EA" style="color: black;" round plain @click="newIndent(indent.sto_id)"> 再来一单 </nut-tag>
          </nut-col>
          </nut-row>
        </nut-cell>
      </nut-row>
    </nut-tab-pane>

      <nut-tab-pane title="已评价" pane-key="2">
        <nut-row v-for="indent in indentData" :key="indent.ind_id" >
          <nut-cell style="background-color: #FBFCFA;">
          <nut-row>
            <!-- <nut-col :span="24"> -->
            <!-- <div> -->
              <div v-for="(commodity,index) in indent.commodity" :key="index">
                <span style="font-family: Source Han Sans SC;
                  font-size: 14px;
                  font-weight: 400;
                  line-height: 16px;
                  letter-spacing: 1px;
                  text-align: center;
                ">
                  {{ commodity.com_name }}
                </span>

                <span style="font-family: Source Han Sans SC;
                  font-size: 12px;
                  font-weight: 400;
                  line-height: 16px;
                  letter-spacing: 0px;
                  text-align: left;
                  margin-left: 50px;
                ">
                {{ "x"+commodity.com_quantity }}
                </span>
              </div>
              <div>
              {{ indent.com_position }}
            </div>
            <nut-col :span="16">
              <nut-tag color="#EBF5EA" style="color: black;" round> {{ indent.ind_state }} </nut-tag>
            </nut-col>
            <nut-col :span="8">
<!--              <nut-tag color="#EBF5EA" style="color: black;" round plain @click="gradeIndent" > 评价 </nut-tag>-->
              <nut-tag color="#EBF5EA" style="color: black;" round plain @click="newIndent(indent.sto_id)"> 再来一单 </nut-tag>
            </nut-col>
            </nut-row>
          </nut-cell>
        </nut-row>
      </nut-tab-pane>
    </nut-tabs>
  </nut-config-provider>
</template>


<script setup>
import axios from 'axios';
import {onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import globalData from "../../global.js"

const BaseUrl = globalData.BaseUrl
const state = ref('1');
const router=useRouter();
// const route=useRoute();
// const cus_ID=route.query.cus_ID;
// import globalData from"../../global.js"
// import {stat} from "@babel/core/lib/gensync-utils/fs";
const cus_ID=sessionStorage.getItem("user_id");

const themeVars = ref({
    primaryColor: '#99af93',
    categoryListLeftBgColor: '#f9fbf7'
});

const indentData=ref([
{
  ind_id:"1",
  commodity:[{
    com_name:"PEANUTS奶油贝果",
    com_quantity:"2"
  }],
  com_position:"吉事花生鞍山新村二店",
  ind_state:"1",
  ind_money:"1"
}
])

onMounted(()=>{
  console.log("!!!"+cus_ID)
  indentData.value.pop();
  searchIndent(state.value);
})
const indState=["未收货","确认收货","待取货","已核销","过期未取","已评价"]
const convert=(data)=>{
  for(let i=0;i<data.length;++i){
    var now=data[i];
    var tempData={
      ind_id:"1",
      commodity:[{
        com_name:"PEANUTS奶油贝果",
        com_quantity:"2"
      }],
      com_position:"吉事花生鞍山新村二店",
      ind_state:"1",
      ind_money:"1",
      sto_id:""
    };

    tempData.ind_id=now.ind_ID;
    tempData.com_position=now.com_position;
    tempData.ind_state=indState[now.ind_state];
    tempData.sto_id=now.sto_ID;
    tempData.ind_money=now.ind_money;
    tempData.commodity.pop();
    console.log(now);
    for(let j=0;j<now.commodities.length;++j){
      tempData.commodity.push({
        com_name:now.commodities[j].com_name,
        com_quantity:now.commodities[j].ind_quantity
      })
    }

    indentData.value.push(tempData);
  }
}

const searchIndent=(state)=>{
  console.log(state.toString())
  axios.get(BaseUrl+'/api/cus/getInd',{
    params:{
      cus_ID:parseInt(cus_ID),
      state:(state.paneKey=='1'||state=='1'?null:5)
    }
  }).then(response=>{
    var data=response.data;

    data.sort((a, b) => a.ind_creationTime > b.ind_creationTime ? -1 : 1);

    console.log(data)

    convert(data);
  })
}


const clickBack=()=>{
  router.go(-1);
}

const changeType=(state)=>{
  console.log(state.paneKey);
  indentData.value=[];
  searchIndent(state);
}

const gradeIndent=(ind_id)=>{
  console.log(ind_id)
  router.push({
    path:'/comment',
    query:{
      ind_id:ind_id
    }

  })

}
const newIndent=(sto_id)=>{
  router.push({
    path:'/storeDetail',
    query:{
      sto_id:sto_id
    }
  })
}

const goDetail=(ind_id)=>{
  router.push({
    path:'/indentdetail',
    query:{
      ind_id:ind_id
    }
  })
}
</script>