"use strict"

function upload(success) {
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.click();
    input.onchange = function () {
        const file = input.files[0];
        if (/^image\//.test(file.type)) {
            upload_file(file, success, function () {
                alert("上传失败")
            })
        } else {
            alert("只支持图片文件");
        }
    };
}


function upload_file(file, success, failure, progress) {
    let xhr, formData;
    xhr = new XMLHttpRequest();
    xhr.withCredentials = false;
    xhr.open('POST', '/blog/upload');

    if (progress != null && progress instanceof Function) {
        xhr.upload.onprogress = function (e) {
            progress(e.loaded / e.total * 100);
        }
    }

    xhr.onload = function () {
        let json;
        if (xhr.status === 403) {
            failure('HTTP Error: ' + xhr.status, {remove: true});
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            failure('HTTP Error: ' + xhr.status);
            return;
        }
        json = JSON.parse(xhr.responseText);
        if (!json || typeof json.location != 'string') {
            failure('Invalid JSON: ' + xhr.responseText);
            return;
        }
        success(json.location);
    };

    xhr.onerror = function () {
        failure('Image upload failed due to a XHR Transport error. Code: ' + xhr.status);
    }

    formData = new FormData();
    formData.append('file', file, file.name);

    xhr.send(formData);
}
