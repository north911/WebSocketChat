var ws;

function connect() {
    var username = document.getElementById("username").value;
    var host = document.location.host;
    var pathname = document.location.pathname;

    ws = new WebSocket("ws://" + host + pathname + "chat/" + username + "/");
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

function createNewTab(name) {

    var panel = $("#accordion");
    var htmlcode = " <div class=\"panel panel-default\">\n" +
        " <div class=\"panel-heading\">\n" +
        " <h4 class=\"panel-title\">\n" +
        " <a data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#collapseTwo\"\n" +
        " class=\"collapsed\" aria-expanded=\"false\">" + name + " </a>\n" +
        " </h4>\n" +
        " </div>\n" +
        " <div id=\"collapseTwo\" class=\"panel-collapse collapse\" aria-expanded=\"false\"\n" +
        " style=\"height: 0px;\">\n" +
        " <div class=\"panel-body\">\n" +
        "   <div class=\"row\" style=\"padding-top: 20px\">\n" +
        "                                            <div class=\"col-lg-5\">\n" +
        "                                                <textarea class=\"form-control\" rows=\"15\" id=\"log\"></textarea>\n" +
        "                                            </div>\n" +
        "                                            <!-- /.col-lg-12 -->\n" +
        "                                        </div>\n" +
        "                                        <div class=\"row\" style=\"padding-top: 20px\">\n" +
        "                                            <div class=\"col-lg-5\">\n" +
        "                                                <div class=\"form-group input-group\">\n" +
        "                                                    <input type=\"text\" class=\"form-control\" id=\"msg\">\n" +
        "                                                    <span class=\"input-group-btn\">\n" +
        "                                                <button class=\"btn btn-default\" type=\"button\" onclick=\"send();\"><i\n" +
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