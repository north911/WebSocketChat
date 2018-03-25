var ws;
var panel;


function connectAgent() {
    let usersMap = new Map();

    var username = document.getElementById("username").value;
    var host = document.location.host;
    var pathname = document.location.pathname;

    ws = new WebSocket("ws://" + host + pathname + "chat/" + username + "/");
    var millisecondsToWait = 100;
    setTimeout(function () {
        sendSlots();
    }, millisecondsToWait);
    alert("connected to the system");
    document.getElementById("username").disabled = true;
    document.getElementById("connectBtn").disabled = true;
    usersMap.set(username, "");
    ws.onmessage = function (event) {
        var message = JSON.parse(event.data);
        if (!usersMap.has(message.from)) {
            usersMap.set(message.from, message.from);
            createNewTab(message.from + "");
        }

        if ("Disconnected!" == message.content) {
            usersMap.delete(message.from);
            removeTab("" + message.from);
        }
        var log = document.getElementById("ta" + message.from);
        log.innerHTML += message.from + " : " + message.content + "\n";

    };
}

function sendSlots() {
    var content = document.getElementById("slots").value;
    var json = JSON.stringify({
        "content": content
    });
    ws.send(json);
}

function leave() {
    var content = "/leave";
    var json = JSON.stringify({
        "content": content
    });
    ws.send(json);
}

function sendA(to) {
    var content = document.getElementById("msg" + to).value;
    var username = document.getElementById("username").value;
    var json = JSON.stringify({
        "to": to + "",
        "content": content
    });
    var log = document.getElementById("ta" + to);
    log.innerHTML += username + " : " + content + "\n";

    ws.send(json);
}

function send() {
    var content = document.getElementById("msg").value;
    var json = JSON.stringify({
        "content": content
    });
    ws.send(json);
}

function createNewTab(name) {

    var nm = "\'" + name + "\'";
    panel = $("#accordion");
    var htmlcode = " <div class=\"panel panel-default\" id=\'pandef" + name + "\'" + ">\n" +
        " <div class=\"panel-heading\">\n" +
        " <h4 class=\"panel-title\">\n" +
        " <a data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#collapse" + name + "\"\n" +
        " class=\"collapsed\" aria-expanded=\"false\">" + name + " </a>\n" +
        " </h4>\n" +
        " </div>\n" +
        " <div id=\"collapse" + name + "\" class=\"panel-collapse collapse\" aria-expanded=\"false\"\n" +
        " style=\"height: 0px;\">\n" +
        " <div class=\"panel-body\">\n" +
        "   <div class=\"row\" style=\"padding-top: 20px\">\n" +
        "                                            <div class=\"col-lg-5\">\n" +
        "                                                <textarea class=\"form-control\" rows=\"15\" id=\"ta" + name + "\"></textarea>\n" +
        "                                            </div>\n" +
        "                                            <!-- /.col-lg-12 -->\n" +
        "                                        </div>\n" +
        "                                        <div class=\"row\" style=\"padding-top: 20px\">\n" +
        "                                            <div class=\"col-lg-5\">\n" +
        "                                                <div class=\"form-group input-group\">\n" +
        "                                                    <input type=\"text\" class=\"form-control\" id=\"msg" + name + "" + "\">\n" +
        "                                                    <span class=\"input-group-btn\">\n" +
        "                                                <button class=\"btn btn-default\" type=\"button\" onclick=\"sendA(" + nm + ");\"><i\n" +
        "                                                        class=\"fa fa-send\"></i>\n" +
        "                                                </button>\n" +
        "                                            </span>\n" +
        "                                                </div>\n" +
        "                                            </div>\n" +
        "\n" +
        "                                            <!-- /.col-lg-12 -->\n" +
        "                                        </div>" +
        " </div>\n" +
        " </div>\n" +
        " </div>";

    panel.append(htmlcode);
}

function removeTab(name) {
    var panel1 = document.getElementById("accordion");
    var child = document.getElementById("pandef" + name);
    panel1.removeChild(child);
}

function connectClient() {
    var username = document.getElementById("username").value;
    var host = document.location.host;
    var pathname = document.location.pathname;

    ws = new WebSocket("ws://" + host + pathname + "chat/" + username + "/");
    document.getElementById("username").disabled = true;
    document.getElementById("connectBtn").disabled = true;
    ws.onmessage = function (event) {
        var log = document.getElementById("log");
        var message = JSON.parse(event.data);
        log.innerHTML += message.from + " : " + message.content + "\n";

    };
}