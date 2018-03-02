
var socket;
var stompClient;
let current_id;

function connect() {
    socket = new SockJS("/socket");
    stompClient = Stomp.over(socket);
    $.ajax({
        type: "GET",
        url: "/app/current_chatbox",
        success: function (response) {
            stompClient.connect({}, function () {
                current_id = response;
                stompClient.subscribe('/dashboard/' + response + "/process", function(message) {
                    showMessage(message);
                });
            });
        },
        error: function (response) {
            console.log(response);
        }
    });
}

function sendMessage() {

    let messageArea = $('.message');

    if(messageArea.val() === ''){
        return;
    }

    stompClient.send("/app/dashboard/" + current_id , {}, JSON.stringify({'message': $(".message").val(), 'userId': $(".userId").val()}));
    messageArea.val('');
}

function showMessage(message) {
    $(".chat").append(`<li class=\"clearfix\">
    <div class=\"chat-body clearfix\"> 
        <div class=\"header\">
            <strong class=\"primary-font\">` + JSON.parse(message.body)["name"] + `</strong>
            <small class=\"pull-right text-muted\">
            <span class=\"glyphicon glyphicon-time">` + JSON.parse(message.body)["date"] + `></span></small>
        </div>
        <p>` + (JSON.parse(message.body)["message"]) + `</p>
    </div>
</li>`);
}


window.onload = function () {
    connect();
    $("#message").click(sendMessage);
};