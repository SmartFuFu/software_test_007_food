<template>
    <nut-config-provider :theme-vars="themeVars">
    <div class="background"></div>
    <nut-navbar title="顾客注册" left-show @click-back="goback">
    </nut-navbar>
    <img src="../../assets/cat.svg" style="position:absolute;right: 20%;top:6%;transform: translateY(-100%);z-index: 1000;" />
    <div style="position:relative;margin-top:3vh;opacity: 70%;">
        <nut-form>
            <nut-form-item label="请上传头像">
                <nut-uploader
                    style="border-radius: 30px;"
                    size="small"
                    v-model:file-list="formData.logo"
                    accept="image/*"
                    :auto-upload="false"
                    maximum="1">
                </nut-uploader>
            </nut-form-item>
            <nut-form-item label="请输入电话号码">
                <nut-input v-model="formData.user_phone" @blur="validatePhone" placeholder="请输入电话号码" type="number" />
            </nut-form-item>
            <nut-form-item label="请输入密码">
                <nut-input v-model="formData.user_password" placeholder="请输入密码" type="text" />
            </nut-form-item>
            <nut-form-item label="请输入昵称">
                <nut-input v-model="formData.cus_nickname" placeholder="请输入昵称" type="text" />
            </nut-form-item>
            <nut-form-item label="请选择性别">
                <nut-radio-group v-model="formData.user_gender" direction="horizontal">
                    <nut-radio label="0" shape="button" size="small" style="width:50px;">男</nut-radio>
                    <nut-radio label="1" shape="button" size="small" style="width:50px;">女</nut-radio>
                </nut-radio-group>
            </nut-form-item>
            <nut-form-item label="请选择生日">
                <nut-button @click="show = true" style="width:200px;">{{'生日：'+birth_str}}</nut-button>
            </nut-form-item>
            <nut-form-item label="请输入支付密码">
                <nut-input v-model="formData.cus_payPassword" placeholder="请输入支付密码" type="password" />
            </nut-form-item>
            <nut-form-item label="请再次输入支付密码">
                <nut-input v-model="cus_payPassword_val" placeholder="请再次输入支付密码" type="password" />
            </nut-form-item>
            <nut-form-item label="请选择常用地址">
                <nut-input type="textarea" v-model="addressInput" style="width: 100%;" placeholder="请输入地址" rows="2"></nut-input>
                <div style="margin-top: 10px;">
                    <nut-button @click="searchLocation" color="#748865a8" type="primary">搜索</nut-button>
                  </div>
                <!-- 搜索按钮放在新的一行，并添加上边距以增加间距 -->
            </nut-form-item>
              <!-- 地址的地图显示 -->
              <div id="baiduMap" style="width:80%; height: 200px; margin-top:10px;margin-left:10%;"></div>
        </nut-form>
        <nut-popup v-model:visible="show" position="bottom">
            <nut-date-picker
            v-model="birth"
            :min-date="new Date(1900, 0, 1)"
            :three-dimensional="false"
            @confirm="confirm"
            ></nut-date-picker>
        </nut-popup>
        <nut-popup v-model:visible="showBottom" round position="bottom" style="justify-content: center;align-items: center;" :style="{ height: '20%' }">
            <div style="position:absolute;top:30%;left:30%;">{{ mess }}</div>
        </nut-popup>
        <nut-cell style="position:absolute;margin-top:0vh;left:10%;width:305px;height:30px;opacity: 70%;">
            <nut-checkbox v-model="agree">我已阅读同意《临期食品守则》
                <template #checkedIcon> <Checklist color="green" /> </template>
            </nut-checkbox>
        </nut-cell>
        <nut-button class="confirm-button" :disabled="!agree" @click="submit" primary>
            确认
        </nut-button>
    </div>
</nut-config-provider>
</template>
<script lang="js" setup>

    let map={};
    let geoc={};
    let BMap={};
    let BMAP_STATUS_SUCCESS;
    const addressInput = ref(''); 
    import {ref,onMounted} from 'vue';
    import { Checklist } from '@nutui/icons-vue';
  
    // const BaseUrl = "http://100.80.74.33:8000"
    // const BaseUrl = "http://119.8.11.44:8002"
    const formData = ref({
        user_phone: '',
        user_password: '',
        user_gender: '',
        user_address: '请输入地址',
        cus_nickname: '',
        cus_payPassword:'',
        logo:[],

        });
    const birth=ref(new Date(2023,9,30))
    const birth_str=ref(birth.value.getFullYear().toString()+'-'+(birth.value.getMonth()+1).toString()+'-'+birth.value.getDate().toString())
    // 对弹窗组件是否弹出进行监控
    const show=ref(false)


    const agree=ref(false);
    const phoneReg = /^1[3-9]\d{9}$/;
    const phoneStatus=ref('')
    const phoneError=ref('')
    const showBottom=ref(false)
    const cus_payPassword_val=ref('')
    const mess=ref('')
    import axios from 'axios';
    import { useRouter } from 'vue-router';
    import globalData from "../../global.js"
    const BaseUrl = globalData.BaseUrl
    const router = useRouter();
    
    const themeVars = ref({
        formItemLabelWidth:'120px',
        formItemLabelMarginRight:'20px',
        uploaderPictureWidth:'60px',
        uploaderPictureHeight:'60px',
    });

    onMounted(() => {
        BMap = window.BMapGL;
        BMAP_STATUS_SUCCESS=window.BMapGL.BMAP_STATUS_SUCCESS;
        map = new BMap.Map("baiduMap"); 
        geoc = new BMap.Geocoder();
        //const map = new BMapGL.Map("baiduMap"); 
        const point = new BMap.Point(116.404, 39.915);  // 创建点坐标
        map.centerAndZoom(point, 15);                     // 初始化地图，设置中心点坐标和地图级别
        map.enableScrollWheelZoom(true);                  // 开启鼠标滚轮缩放

        //const geoc = new BMapGL.Geocoder();  

        // 添加地图点击事件监听
        map.addEventListener("click", function (e) {
            const pt = e.latlng;
            //alert('点击的经纬度：' + e.latlng.lng + ', ' + e.latlng.lat);
            geoc.getLocation(pt, function (rs) {
                console.log('Complete geocoding result:', rs);
                const addComp = rs.addressComponents;
                formData.value.user_address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                // alert(form.user_address);
                // formData.sto_lng = pt.lng.toString();
                // form.sto_lat = pt.lat.toString();
                // alert('点击的经纬度：' + pt.lng.toString() + ', ' + pt.lat.toString());
                addressInput.value = formData.value.user_address;   // 更新输入框的值
                var marker = new BMap.Marker(pt);  // 创建标注
                map.clearOverlays(); // 清除所有覆盖物
                map.addOverlay(marker); // 将标注添加到地图中
                formData.value.sto_longitude = pt.lng.toString();
                formData.value.sto_latitude = pt.lat.toString();
                console.log(formData.value.sto_longitude)
                console.log(formData.value.sto_latitude)
                //marker.enableDragging(); // 可拖拽
            });
        });
    });

    const searchLocation = () => { // 搜索地点
        const local = new BMap.LocalSearch(map, {
            renderOptions: { map: map, autoViewport: true, selectFirstResult: false },
            pageCapacity: 8,
        });

        // 搜索回调
        local.setSearchCompleteCallback(results => {
            console.log(local.getStatus())
            console.log(map)
            console.log(geoc)
            if (local.getStatus() === BMAP_STATUS_SUCCESS) {
            const pt = results.getPoi(0).point;
            //form.sto_locationPoint = `${pt.lng},${pt.lat}`;

            // 更新输入框地址
            geoc.getLocation(pt, rs => {
                const addComp = rs.addressComponents;
                formData.value.user_address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                addressInput.value = formData.value.user_address;
                formData.value.sto_longitude = pt.lng.toString();
                formData.value.sto_latitude = pt.lat.toString();
                console.log(formData.value.sto_longitude)
                console.log(formData.value.sto_latitude)
            });

            } else {
                console.log('未找到相关地址！');
            }
        });

        local.search(addressInput.value);
    };

    // 弹窗组件的确认事件
    const confirm = ({ selectedValue }) => {
        console.log(selectedValue);
        console.log(birth.value.getMonth())
        birth_str.value=birth.value.getFullYear().toString()+'-'+(birth.value.getMonth()+1).toString()+'-'+birth.value.getDate().toString()
        show.value = false;
    };

    const submit=()=>{
        if(phoneStatus.value=='error'){
            baseClick(phoneError.value);
        }else if(formData.value.user_password===''){
            baseClick('请输入密码');
        }else if(formData.value.cus_nickname===''){
            baseClick('请输入昵称');
        }else if(formData.value.user_gender===''){
            baseClick('请选择性别');
        }else if(formData.value.cus_payPassword!=cus_payPassword_val.value){
            baseClick('两次输入的支付密码不一致！')
        }else if(formData.value.user_address===''){
            baseClick('请输入地址')
        }else{
            axios.post(BaseUrl+'/api/pub/register/customer',  JSON.stringify({ 
                user_phone:formData.value.user_phone,
                user_password:formData.value.user_password,
                user_address:formData.value.user_address,
                user_gender:formData.value.user_gender,
                cus_nickname:formData.value.cus_nickname,
                cus_payPassword:formData.value.cus_payPassword
            }), {
            headers: {
                'Content-Type': 'application/json'
            }
            })
            .then(response => {
                console.log('Login submitted successfully.');
                console.log(response.data);
                if(response.data.msg==='顾客成功注册') {

                    upLoadLogo(response.data.user_id)
                    
                } else{
                    baseClick(response.data.msg);
                    console.log(response.data.msg);
                }
            })
            .catch((error) => {
                console.log('An error occurred:', error);
            });
        }
    }
    const validatePhone = () => {
        if (!phoneReg.test(formData.value.user_phone)) {
            phoneStatus.value = 'error';
            phoneError.value = '电话号码格式不正确';
        } else {
            phoneStatus.value = 'success';
            phoneError.value = '';
        }
    };

    const baseClick = (message) => {

        showBottom.value=true;
        mess.value=message
    };

    function dataURLtoFile(dataurl, filename) {
        // 获取到base64编码
        const arr = dataurl.split(',')
        // 将base64编码转为字符串
        const bstr = window.atob(arr[1])
        let n = bstr.length
        const u8arr = new Uint8Array(n) // 创建初始化为0的，包含length个元素的无符号整型数组
        while (n--) {
            u8arr[n] = bstr.charCodeAt(n)
        }
        return new File([u8arr], filename, {
            type: 'image/jpeg',
        })
    }

    function upLoadLogo(user_id){
        const formData_Logo = new FormData();
        var count_logo=0;
        formData.value.logo.forEach((file) => {
        console.log(file.url);
        const pic=dataURLtoFile(file.url,'pic'+count_logo.toString()+'.jpg')
        formData_Logo.append('image', pic); // 将文件添加到FormData中
        count_logo++;
        });
        formData_Logo.append('user_id',user_id)
        axios.post(BaseUrl+'/api/sto/uploadLogoImage',  formData_Logo, {
        headers: {
        'Content-Type': 'multipart/form-data'
        }
        })
        .then(response_logo => {
        console.log(response_logo.data)
        if(response_logo.data.msg==='success'){
            baseClick('注册成功！前往登录界面！')
                    /*------------------------*/
                    /*登录成功后编辑此处跳转界面*/
                    /*------------------------*/
                    router.push({
                        path: '/login',
                    });
        }else{
        baseClick("未成功上传头像！")
        }
        })
    }
    const goback=()=>{
    router.push({
        path: '/login',
    });
}
</script>
<style scoped>
    .background{
        position:absolute;
        top:0%;
        left:0%;
        flex-shrink: 0;
        background: linear-gradient(180deg,#FFFFFF 0%, rgba(116, 136, 101, 0.66) 100%),url('../../assets/images/image-49.png'), rgba(255, 255, 255, 1) 0px -126.926px / 100% 139.564% no-repeat;
        opacity: 60%;
        background-blend-mode: darken;
        background-size: cover;
        background-position:40,center;
        height: 150vh;
        width: 100vw;
        padding-top: 70px; /* 添加顶部填充以避免标题栏遮挡内容 */
        -webkit-user-drag: none;
    }
    .login-word{
        position:absolute;
        top:30px;
        left:0vw;
        width: 133px;
        
        flex-shrink: 0;
        color: #93B090;
        font-family: "Abhaya Libre";
        font-size: 30px;
        font-style: normal;
        font-weight: 700;
        line-height: 21px;
        letter-spacing: 2px;
    }
    .login-pic{
        position: absolute;
        left:34vw;
        top:5px;
        width: 68px;
        height: 55px;
        background: url('../../assets/images/image-50.png'),rgb(246,247,244) 0px -147.456px / 100% 139.564% no-repeat;

        background-blend-mode: darken;
        
        flex-shrink: 0;
    }
    .confirm-button{
        position: relative;
        margin-top:5vh;
        left:10%;
        width: 305px;
        height: 50px;
        border-radius: 4px;
        background: #93B090;
        font-family: "Source Han Sans SC";
        font-size: 18px;
        font-style: normal;
        font-weight: 700;
        color: #FFF;
        line-height: normal;
        letter-spacing: 2px;
    }
</style>