var ws;

function connect() {
    var username = document.getElementById("username").value;
    var host = document.location.host;
    var pathname = document.location.pathname;

    ws = new WebSocket("ws://" + host + pathname + "chat/" + username + "/" );
    ws.onmessage = function (event) {
        var log = document.getElementById("log");

        var message = JSON.parse(event.data);
        log.innerHTML += message.from + " : " + message.content + "\n";
        document.getElementById("username").disabled = true;
        document.getElementById("connectBtn").disabled = true;
    };
}

function leave() {
    var content = "/leave";
    var json = JSON.stringify({
        "content": content
    });
    ws.send(json);
}

function send() {
    var content = document.getElementById("msg").value;
    var json = JSON.stringify({
        "content": content
    });
    ws.send(json);
}
