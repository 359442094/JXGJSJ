// +----------------------------------------------------------------------
// | 表单提交页面通用表单监听（如有特殊需求可参照此文件写一个单独的js文件，如:sys_config.js）
// +----------------------------------------------------------------------
layui.use(['element','form'], function(){
    var element = layui.element;
    var form = layui.form;
    //监听提交
    form.on('submit(user_update_submit)', function(){
        var action ="/user_update";//表单提交URL地址
        var uid = $("input[name='uid']").val(); //表单数据
        var name = $("input[name='name']").val(); //表单数据
        var pwd = $("input[name='pwd']").val(); //表单数据
        var designValue = $("input[name='designValue']").val(); //表单数据
        var designInfo = $("input[name='designInfo']").val(); //表单数据
        var info = $("input[name='info']").val(); //表单数据
        var designNian = $("input[name='designNian']").val(); //表单数据
        var iphone = $("input[name='iphone']").val(); //表单数据
        var studentName = $("input[name='studentName']").val(); //表单数据
        var studentType = $("input[name='studentType']").val(); //表单数据
        var rote = $("input[name='rote']").val(); //表单数据
        var type = $("input[name='type']").val(); //表单数据
        var upath=$("#demo1").attr("src");
        var bpath=$("#demo11").attr("src");

        $.post(action,{
            'uid':uid,
            'name':name,
            'pwd':pwd,
            'designValue':designValue,
            'designInfo':designInfo,

            'info':info,
            'designNian':designNian,
            'iphone':iphone,
            'studentName':studentName,
            'studentType':studentType,

            'rote':rote,
            'type':type,
            'upath':upath,
            'bpath':bpath
        },function(obj){
            if(obj == true){
                layer.msg('修改成功', {
                    icon: 1,//提示的样式
                    time: 3000, //2秒关闭（如果不配置，默认是3秒）//设置后不需要自己写定时关闭了，单位是毫秒
                    end:function(){
                        location.href='/toUserAdmin';
                    }
                });
            }else{
                layer.msg('修改失败', {
                    icon: 2,//提示的样式
                    time: 3000, //2秒关闭（如果不配置，默认是3秒）//设置后不需要自己写定时关闭了，单位是毫秒
                    end:function(){
                        location.href='/toUserAdmin';
                    }
                });
            }
        });
        return false;//注释掉这行代码后，表单将会以普通方式提交表单，否则以ajax方式提交表单
    });
});