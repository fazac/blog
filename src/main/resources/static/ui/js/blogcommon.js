$(document).ajaxSend(function (event, jqxhr, settings) {
    if (getCache("token")) {
        jqxhr.setRequestHeader("Authorization", "Bearer " + getCache("token"));
    }
});

$(function () {
    let toast = $("#_tip");
    if ($(".toast-body").text().length > 0) {
        toast.toast("show");
        setTimeout(function () {
            toast.toast("hide");
        }, 2000);
    }

    toast.click(function () {
        $("#_tip").toast("hide");
    });

    $('#confirm_model').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget);
        let title = button.data('title');
        let desc = button.data('desc');
        let action = button.data('action');
        let modal = $(this)
        modal.find('.modal-title').text(title);
        modal.find('.modal-body').text(desc)
        $('#confirm_button').off("click").on("click", function () {
            confirmButtonClick(action)
        })
    });
    $(".nav-link").each(function () {
        // console.log($(this));
        // console.log(this);
        $(this).removeClass("active");
        console.log(this.href);
        if (this.href === String(window.location)) {
            $(this).addClass("active");
        }
    })
});

function tipMsg(msg) {
    $(".toast-body").text(msg);
    $("#_tip").toast("show");
}

function confirmButtonClick(callback) {
    if (callback) {
        let ids = [];
        let checkedIds = $(":checked:not(#_all_select)input[type='checkbox']");
        for (let id of checkedIds) {
            ids.push($(id).data('id'));
        }
        window[callback].call(this, ids);
        // let callbackFn = _.getFunction(callback, false);
        // callbackFn.fun.call(ids);
    }
}

// //模拟js 通过字符串执行函数
// _.mixin({
//     /**基于字符串获取 函数名称和参数对象 //不准持有无效的引号
//      * 示例  test(1,5,6,7);
//      * @params str  字符串函数
//      *
//      */
//     getFunctionObj: function (str) {
//
//         var functionObj = {
//             functionName: "",
//             functionParams: []
//         }
//         if (_.isString(str)) {
//             if (str.indexOf("(") != -1 && str.indexOf(")") != -1) {
//                 var arr = str.split("(");
//                 functionObj.functionName = arr[0];
//                 arr = arr[1].split(")");
//                 if (!_.isEmpty(arr[0])) {
//                     functionObj.functionParams = arr[0].split(",");
//                 }
//             } else { //兼容未命名() 的函数
//                 functionObj.functionName = str;
//             }
//         }
//         return functionObj;
//     },
//     /* 基于字符串获取当前window的对象函数对象
//      * @params str  字符串函数
//      * @parmas  falg   默认false,
//      * true 立即调用这个函数,false 返回函数对象及其函数参数
//      */
//     getFunction: function (str, falg) {
//         if (_.isString(str)) {
//             var functionObj = _.getFunctionObj(str);
//             var strFunction = functionObj.functionName;
//             if (!_.isEmpty(strFunction)) {
//                 var arr = strFunction.split(".");
//                 strFunction = window[arr[0]];
//                 for (var i = 1, len = arr.length; i < len; i++) {
//                     if (!strFunction && !_.isFunction(strFunction) && !_.isObject(strFunction)) {
//                         return null;
//                     }
//                     strFunction = strFunction[arr[i]];
//                 }
//                 if (_.isFunction(strFunction)) {
//                     if (falg) {
//                         strFunction.apply(strFunction, functionObj.functionParams); //测试是否是方法可以测试出来
//                     }
//                     functionObj.fun = strFunction;
//                     return functionObj;
//                 }
//             }
//             return null;
//         }
//         return str;
//     }
// });

