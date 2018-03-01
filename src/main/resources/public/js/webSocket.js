
let socket = new SockJS("/dashboard");
let stompClient = Stomp.over(socket);

function connect() {
    $.ajax({
        type: "GET",
        url: "/app/current_chatbox",
        success: function (response) {
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('dashboard/' + response, function() {
                    console.log("You are succesfully connected to chatbox: " + getData())
                });
            });
        },
        error: function (response) {
            console.log(response);
        }
    });
}

function sendMessage() {
    stompClient.send("/message", {}, JSON.stringify({'message': $("#message").val()}));
}


window.onload = function () {
    connect();
    $("#message").click(sendMessage());
};