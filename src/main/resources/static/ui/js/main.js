function saveCache(name, value) {
    window.localStorage.setItem(name, value);
}

function getCache(name) {
    return window.localStorage.getItem(name);
}

function ajaxGet(url, param, successFunc, failureFunc, async) {
    return _ajax("GET", url, param, successFunc, failureFunc, async);
}

function ajaxPost(url, param, successFunc, failureFunc, async) {
    _ajax("POST", url, param, successFunc, failureFunc, async);
}

function _ajax(type, url, param, successFunc, failureFunc, async) {
    if (typeof async == "undefined") {
        async = true;
    } else {
        async = false;
    }
    return $.ajax({
        type: type,
        url: url,
        data: param,
        async: async, // 是否异步
        success: successFunc,
        error: failureFunc
        // success: function (ret) {
        //     _ajaxCallback(ret, successFunc, failureFunc);
        // },
        // error: function (ret) {
        //     _ajaxCallback(ret, successFunc, failureFunc);
        // }
    });
}

//
// function _ajaxCallback(ret, successFunc, failureFunc) {
//     // 总会执行
//     if (ret === true || ret == "true" || typeof ret == "object") {
//         // 是否是NOTELOGIN
//         if (ret && typeof ret == "object") {
//             if (ret.Msg == "NOTLOGIN") {
//                 alert(getMsg("Please sign in firstly!"));
//                 return;
//             }
//         }
//         if (typeof successFunc == "function") {
//             successFunc(ret);
//         }
//     } else {
//         if (typeof failureFunc == "function") {
//             failureFunc(ret);
//         } else {
//             alert("error!")
//         }
//     }
// }
