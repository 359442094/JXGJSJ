// +----------------------------------------------------------------------
// | 表单提交页面通用表单监听（如有特殊需求可参照此文件写一个单独的js文件，如:sys_config.js）
// +----------------------------------------------------------------------
layui.use(['element','form'], function(){
    var element = layui.element;
    var form = layui.form;
    //监听提交
    form.on('submit(formDemo)', function(){
        var action ='/menu_Insert';//表单提交URL地址
        var mname = $("input[name='mname']").val(); //表单数据
        var mpath = $("input[name='mpath']").val(); //表单数据
        $.post(action,{'mname':mname,'mpath':mpath},function(obj){
            if(obj == true){
                layer.msg('新增成功', {
                    icon: 1,//提示的样式
                    time: 3000, //2秒关闭（如果不配置，默认是3秒）//设置后不需要自己写定时关闭了，单位是毫秒
                    end:function(){
                        location.href='/toMenu';
                    }
                });
            }else{
                layer.msg('新增失败', {
                    icon: 2,//提示的样式
                    time: 3000, //2秒关闭（如果不配置，默认是3秒）//设置后不需要自己写定时关闭了，单位是毫秒
                    end:function(){
                        location.href='/toMenu';
                    }
                });
            }
        });
        return false;//注释掉这行代码后，表单将会以普通方式提交表单，否则以ajax方式提交表单
    });
});