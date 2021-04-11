let socket;

$(function () {
    regSocket();
});

function regSocket() {
    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }

    if (socket === undefined && window.WebSocket) {
        socket = new WebSocket("wss://fazac.top/websocket");

        socket.onmessage = function (event) {
            $("#tip").attr("autohide", false);
        };

        socket.onopen = function (event) {
            console.log("open", event);
        };

        socket.onclose = function (event) {
            console.log("close", event);
        };
    }
}

function sendMSG(message) {
    if (!window.WebSocket) {
        return;
    }
    if (socket.readyState === WebSocket.OPEN) {
        socket.send(message);
    } else {
        alert("WebSocket 连接没有建立成功！");
    }
}