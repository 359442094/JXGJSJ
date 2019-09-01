// +----------------------------------------------------------------------
// | 表单提交页面通用表单监听（如有特殊需求可参照此文件写一个单独的js文件，如:sys_config.js）
// +----------------------------------------------------------------------
layui.use(['element','form'], function(){
    var element = layui.element;
    var form = layui.form;
    //监听提交
    form.on('submit(service_update_submit)', function(){
        var action ='/serviceAdmin/info';//表单提交URL地址
        var cid = $("input[name='cid']").val(); //表单数据
        var ctype = $("input[name='ctype']").val(); //表单数据
        var cvalue = $("input[name='cvalue']").val(); //表单数据
        $.ajax({
            url:action+"?cid="+cid+"&ctype="+ctype+"&cvalue="+cvalue,
            method:"PUT",
            success:function(result){
                if(result == true){
                    layer.msg('修改成功', {
                        icon: 1,//提示的样式
                        time: 3000, //2秒关闭（如果不配置，默认是3秒）//设置后不需要自己写定时关闭了，单位是毫秒
                        end:function(){
                            location.href='/serviceAdmin';
                        }
                    });
                }else{
                    layer.msg('修改失败', {
                        icon: 2,//提示的样式
                        time: 3000, //2秒关闭（如果不配置，默认是3秒）//设置后不需要自己写定时关闭了，单位是毫秒
                        end:function(){
                            location.href='/serviceAdmin';
                        }
                    });
                }
            }
        });
        return false;//注释掉这行代码后，表单将会以普通方式提交表单，否则以ajax方式提交表单
    });
});