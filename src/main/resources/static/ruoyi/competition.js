let prefix = ctx + "system/competition"

$(function () {
    //  调试的用的,不会提交
    $.validator.setDefaults({
        debug: true
    })

    if ($.validator) {
        $.validator.prototype.elements = function () {
            var validator = this,
                rulesCache = {};

            // select all valid inputs inside the form (no submit or reset buttons)
            return $(this.currentForm)
                .find("input, select, textarea")
                .not(":submit, :reset, :image, [disabled]")
                .not(this.settings.ignore)
                .filter(function () {
                    if (!this.name && validator.settings.debug && window.console) {
                        console.error("%o has no name assigned", this);
                    }
                    //注释这行代码
                    // select only the first element for each name, and only those with rules specified
                    //if ( this.name in rulesCache || !validator.objectLength($(this).rules()) ) {
                    //    return false;
                    //}
                    rulesCache[this.name] = true;
                    return true;
                });
        }
    }
})


$("#form-competition-add").validate({
        rules: {
            title:{
                required: true,
            },
            startTime: {
                required: true,
            },
            endTime: {
                required: true,
            },
            min:{
                required: true,
                digits: true,
            },
            max:{
                required: true,
                digits: true,
            },
            groupNum:{
                required: true,
                digits: true,
            }
        },
        messages: {
            // "endTime": {
            //     required: "结束时间必须填写"
            // },
            // "startTime": {
            //     required: "开始时间必须填写"
            // }
        }
    });

$("#form-competition-edit").validate({
    rules: {
        title:{
            required: true,
        },
        startTime: {
            required: true,
        },
        endTime: {
            required: true,
        },
        min:{
            required: true,
            digits: true,
        },
        max:{
            required: true,
            digits: true,
        },
        groupNum:{
            required: true,
            digits: true,
        }
    },
    messages: {
        // "endTime": {
        //     required: "结束时间必须填写"
        // },
        // "startTime": {
        //     required: "开始时间必须填写"
        // }
    }
});
function reset(obj) {
    console.log( '清除开始' );
    obj = obj.parent();
    obj.find('.min').val('');
    obj.find('.max').val('');
    obj.find('.groupNum').val('');
}

function del(obj) {
    obj.parent().remove();
}

function addKV() {
    $('.form-group').last().after(
        `
            <div class="form-group">
                <div class="col-sm-3">
                    <input name="k" class="form-control" type="text" placeholder="请添加相应标题" >
                </div>
                <div class="col-sm-8">
                    <textarea  name="v" class="form-control" type="text" placeholder="请添加相应内容" />
                </div>
                <a class="btn btn-danger btn-del btn-del col-sm-1" onclick="del($(this))">
                    <i class="fa fa-remove"></i> 删除
                </a>
            </div>
            `
    );
}

function addGroup() {
    $('.group').last().after(
        `
            <div class="form-group group">
                <div class="col-sm-3">
                    <input name="groupInfo" class="form-control groupInfo" type="text" placeholder="请输入简要说明">
                </div>
                <div class="col-sm-2">
                    <input name="min" class="form-control min" type="text" placeholder="请输入最少人数" oninput="value=value.replace(/[^\\d]/g,'')">
                </div>
                <div class="col-sm-2">
                    <input name="max" class="form-control max" type="text" placeholder="请输入最多人数" oninput="value=value.replace(/[^\\d]/g,'')">
                </div>
                <div class="col-sm-2">
                    <input name="groupNum" class="form-control groupNum" type="text" placeholder="请输入组数" oninput="value=value.replace(/[^\\d]/g,'')">
                </div>
                <a class="btn btn-danger btn-del btn-del col-sm-1" onclick="del($(this))">
                    <i class="fa fa-remove"></i> 删除
                </a>
            </div>
            `
    );
}

