import { reactive } from 'vue'
import BaiduMap from 'BaiduMap'
// Class for items in shopping cart
function item(id,quantity,price){
    this.id=id;
    this.quantity=quantity;
    this.price=price
}
// shopping cart
const shoppingCart=reactive({
    items:[],
    distribution_fee:0, // 配送费
    push(id,quantity,price){
        var item_new=new item(id,quantity,price)
        this.items.push(item_new)
    },
    clear(){
        this.items=[]
    },
    modify(id,quantity,price){
    // Modify one item's quantity and price; if it's not in cart, then add it; if it's zero now, then remove it.
        var in_flag=false
        this.items.forEach((element)=>{
            if(element.id==id){
                element.quantity=quantity
                element.price=price
                in_flag=true
            }
        })
        if(in_flag&&quantity==0){
            this.remove(id)
        }
        else if(in_flag==false){
            this.push(id, quantity, price)
        }
    },
    remove(id){
        this.items=this.items.filter(ele=>ele.id!=id)
    },
    getTotalQuantity(){
        var total=0
        this.items.forEach((element)=>{
            total+=element.quantity
        })
        let numStr = total.toString();
        if (numStr.length > 1) {
            // Remove the first character
            numStr = numStr.slice(1);
          }
        return numStr
    },
    getTotalPrice(){
        var total=0
        this.items.forEach((element)=>{
            total+=element.price
        })
        return total+this.distribution_fee
    },
    getItemById(id){
        var item={
            id:0,
            quantity:0,
            price:0
        }
        this.items.forEach((element)=>{
            if(element.id==id){
                item=element
            }
        })
        return item
    },
    modifyDistributionFee(fee){
        this.distribution_fee=fee
    }
})
// user position object
const userPosition={
    address:"",
    latitude:0,
    longitude:0,
    setCoordination(lat,lng){
        this.latitude=lat;
        this.longitude=lng
    }
}
const userInfo={
    user_id:0
}
const mapObj={
    map:null,
    // 直接传经纬度即可
    walkingRoute(begin_lat,begin_lng,end_lat,end_lng,flag=false, callback){
        let start = new BaiduMap.Point(begin_lng, begin_lat); 
        let end = new BaiduMap.Point(end_lng, end_lat); 
        var distance=null;
        var duration=null;
        if(flag==false){
            var walking = new BaiduMap.WalkingRoute(this.map, { 
                renderOptions: { 
                    map: null, 
                    autoViewport: false 
                },
                onSearchComplete: function (results){
                     /* eslint-disable */ 
                    if (walking.getStatus() != BMAP_STATUS_SUCCESS){console.log('测距失败');return;}
                    var plan = results.getPlan(0);
                    distance=plan.getDistance(true);
                    duration=plan.getDuration(true);
                    
                    // console.log('步行距离'+plan.getDistance(true)+'步行时间'+plan.getDuration(true)); 
                     // 调用回调函数，传递distance和duration
                    if (callback && typeof callback === 'function') {
                        callback(distance, duration);
                    }
                },
                onPolylinesSet: function(){setTimeout(function(){},"1000");		
            }});
            walking.search(start, end);
        }
    },
    ridingRoute(begin_lat,begin_lng,end_lat,end_lng,flag=false, callback){
        let start = new BaiduMap.Point(begin_lng, begin_lat); 
        let end = new BaiduMap.Point(end_lng, end_lat); 
        var distance=null;
        var duration=null;
        if(flag==false){
            var riding = new BaiduMap.RidingRoute(this.map, { 
                renderOptions: { 
                    map: null, 
                    autoViewport: false 
                },
                onSearchComplete: function (results){
                    if (riding.getStatus() != BMAP_STATUS_SUCCESS){console.log('测距失败');return;}
                    var plan = results.getPlan(0);
                    distance=plan.getDistance(true);
                    duration=plan.getDuration(true);
                    //console.log('骑行距离'+plan.getDistance(true)+'骑行时间'+plan.getDuration(true)); 
                    if (callback && typeof callback === 'function') {
                        callback(distance, duration);
                    }
                },
                onPolylinesSet: function(){setTimeout(function(){},"1000");		
                }});
            riding.search(start, end);
        }
    }
}
// const BaseUrl = "http://124.70.156.23:8002"
const BaseUrl = "http://localhost:8002"
// const BaseUrl = "http://100.80.74.33:8002"
export default{shoppingCart,userPosition,userInfo,mapObj,BaseUrl}