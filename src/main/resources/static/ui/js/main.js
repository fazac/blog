function saveCache(name, value) {
    window.localStorage.setItem(name, value);
}

function getCache(name) {
    return window.localStorage.getItem(name);
}

$.postJSON = function (url, data, callback) {
    return jQuery.ajax({
        'type': 'POST',
        'url': url,
        'contentType': 'application/json',
        'data': JSON.stringify(data),
        'dataType': 'json',
        'success': callback
    });
};

//
// function ajaxGet(url, param, successFunc, failureFunc, async) {
//     return _ajax("GET", url, param, successFunc, failureFunc, async);
// }
//
// function ajaxPost(url, param, successFunc, failureFunc, async) {
//     _ajax("POST", url, param, successFunc, failureFunc, async);
// }
//
//
//
// function _ajax(type, url, param, successFunc, failureFunc, async) {
//     if (typeof async == "undefined") {
//         async = true;
//     } else {
//         async = false;
//     }
//     return $.ajax({
//         type: type,
//         url: url,
//         data: param,
//         async: async, // 是否异步
//         success: successFunc,
//         error: failureFunc
//     });
// }

