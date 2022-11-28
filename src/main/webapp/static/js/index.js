var clientH = document.documentElement.clientHeight;
var clientW = document.documentElement.clientWidth;

//点击某些元素时
var content_title = document.getElementById('content_title');
var choose_in = document.getElementById('choose_in');
var choose_un = document.getElementById('choose_un');
var default_secret = document.getElementById('default_secret');
var create_secret = document.getElementById('create_secret');
var view_secret = document.getElementById('view_secret');
content_title.onclick = function(){
    default_secret.style.display = 'block';
    create_secret.style.display = 'none';
    view_secret.style.display = 'none';
}
choose_in.onclick = function(){
    default_secret.style.display = 'none';
    create_secret.style.display = 'block';
    view_secret.style.display = 'none';
}
choose_un.onclick = function(){
    default_secret.style.display = 'none';
    create_secret.style.display = 'none';
    view_secret.style.display = 'block';
}

//当鼠标移出html时
document.onmouseout = function(e){
    if(e.clientY < 0 || e.clientX < 0 || e.clientX > clientW || e.clientY > clientH){
        document.getElementById("secret_content_show").style.filter = "blur(5px)";
    }
    else{
        document.getElementById("secret_content_show").style.filter = "blur(0px)";
    }
}
document.onmouseout = function(){
    document.getElementById("secret_content_show").style.filter = "blur(5px)";
}
document.onmouseover = function(){
    document.getElementById("secret_content_show").style.filter = "blur(0px)";
}


// 当secret_content的内容改变时
var bg = document.getElementById('bg');
var create_secret_num = document.getElementById('create_secret_num');
var secret_content = document.getElementById('secret_content');
var bg_text = document.getElementById('bg_div');
secret_content.oninput = function(){
    // 替换文本中的换行为
    var secret_content_text = " > " + secret_content.value.replace(/\n/g, "\n > ");
    bg_text.innerText = secret_content_text;
    // #bg超出文字后始终显示最后一行
    bg.scrollBy(0,bg_text.scrollHeight);
    // #create_secret_num显示字数
    create_secret_num.innerText = secret_content.value.length;
}


// 玻璃
var content = document.getElementById('content');
VanillaTilt.init(content, {
    max: 1,//最大倾斜角度
    speed: 400,//速度
    glare: true,//是否有光晕
    "max-glare": 0.5//光晕的最大透明度
});


// 点击复制
// var copy = document.getElementById('create_secret_c_showurl');
// copy.onclick = function(){
//     var secret_content = document.getElementById('secret_content');
//     secret_content.select();
//     document.execCommand("Copy");
//     alert("复制成功");
// }

/* // RSA加密
var encrypt = new JSEncrypt();
encrypt.setPublicKey($.trim(document.getElementById('RSA').innerText));

// 发送post请求
var create_secret_c = document.getElementById('send_secret_button');
create_secret_c.onclick = function(){
    $.ajax({
        type: "POST",
        url: "updata",
        data: {
            word: window.btoa(encrypt.encrypt(document.getElementById('secret_content').value)),
            time: window.btoa(encrypt.encrypt(document.getElementById('secret_time').value)),
        },
        success: function(data){
            data_en = window.atob(encrypt.decrypt(data));
            if(data_en.length != 0){
                document.getElementById('create_secret_c_showurl').innerText = data;
            }
        }
    });
}
var view_secret_c = document.getElementById('view_secret_button');
view_secret_c.onclick = function(){
    $.ajax({
        type: "POST",
        url: "getdata",
        data: {
            url: window.btoa(encrypt.encrypt(document.getElementById('secret_id').value)),
        },
        success: function(data){
            if(data.length != 0){
                var text = window.atob(encrypt.decrypt(data[0]));
                document.getElementById('view_secret_c_showword').innerText = data;
            } else {
                document.getElementById('view_secret_c_showword').innerText = "获取失败";
            }
        }
    });
}
*/

// 倒计时num秒
var down_Time;
var se_time = document.getElementById('secret_time_down');
function down_time(){
    var time = setInterval(function(){
					if(down_Time >= 0){
        			down_Time--;
        			se_time.innerText=down_Time;
					}else{					
            secret_content_show.innerText="文本不存在";
        }
    } ,1000);
}

// 发送post请求
var create_secret_c = document.getElementById('send_secret_button');
create_secret_c.onclick = function(){
    $.ajax({
        type: "POST",
        url: "updata",
        data: {
            word: document.getElementById('secret_content').value,
            time: document.getElementById('secret_time').value
        },
        success: function(data){
            if(data.length != 0){
                document.getElementById('create_secret_c_showurl').innerText = data;
            }
        }
    });
}
var view_secret_c = document.getElementById('view_secret_button');
view_secret_c.onclick = function(){
    $.ajax({
        type: "POST",
        url: "getdata",
        data: {
            id: document.getElementById('secret_id').value,
        },
        success: function(data){
            if(data.length != 0){
                // 获取,的位置
                var index1 = data.indexOf(",");
                var index2 = data.indexOf(",", index1 + 1);
                // 截取字符串
                var time = data.substring(0, index1);
                var date = data.substring(index1 + 1, index2);
                var word = data.substring(index2 + 1, data.length);
                console.log("index1:"+index1);
                console.log("index2:"+index2);
                console.log("time:"+time);
                console.log("date:"+date);
                console.log("word:"+word);
                document.getElementById('secret_content_show').innerText = word;
                down_Time = time;
                down_time();
            } else {
                document.getElementById('secret_content_show').innerText = "获取失败";
            }
        }
    });
}




