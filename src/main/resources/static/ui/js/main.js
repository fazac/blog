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

function isnotnull(data) {
    return !("" === data || "undefined" === data || "null" === data || null == data || typeof data == "undefined");

}

/**
 *调用某个url参数
 *使用方法:
 *GetQueryString(name)
 **/
function getQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    let r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null)
        return unescape(r[2]);
    return null; //返回参数值
}

function getQueryStringByEncode(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    let r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null)
        return unescape(decodeURI(r[2]));
    return null; //返回参数值
}

/**
 *调用某个url参数
 *使用方法:
 *GetQueryString(name)
 **/
function setQueryString(data) {
    let params = "";
    if (isnotnull(data) && (typeof (data) == "object" && Object.keys(data).length > 0)) {
        params += "?";
        let num = 0;
        for (let x in data) {
            num++;
            params += x + "=" + data[x];
            if (num < Object.keys(data).length) {
                params += "&";
            }
        }
    }
    return params; //返回参数值
}


//long转时间戳
function timeFormat(time, type) {
    let _date;
    if (time instanceof Date) {
        _date = time;
    } else if (time instanceof String || "string" == typeof (time)) {
        _date = new Date(time.replace(/-/g, "/"));
    } else {
        _date = new Date(time);
    }
    let year = _date.getFullYear();
    let month = _date.getMonth() + 1;
    let day = _date.getDate();
    let hours = _date.getHours();
    let minutes = _date.getMinutes();
    let seconds = _date.getSeconds();
    month = month < 10 ? "0" + month : month;
    day = day < 10 ? "0" + day : day;
    hours = hours < 10 ? "0" + hours : hours;
    minutes = minutes < 10 ? "0" + minutes : minutes;
    seconds = seconds < 10 ? "0" + seconds : seconds;
    if (type === undefined) {
        return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    } else if (type === "YMDHM") {
        return year + "-" + month + "-" + day + " " + hours + ":" + minutes;
    } else if (type === "MMDDHH_CN") {
        return month + "月" + day + "日 " + hours + "点";
    } else if (type === "MMDD_CN") {
        return month + "-" + day;
    } else if (type === "YYMMDD") {
        return year + "-" + month + "-" + day;
    } else if (type === "YYMMDD_CN") {
        return year + "年" + month + "月" + day + "日";
    } else {
        return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    }
}


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

