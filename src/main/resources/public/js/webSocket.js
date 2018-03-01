
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
                console.log('Connected');
                current_id = response;
                stompClient.subscribe('/dashboard/' + response + "/process", function(message) {
                    console.log("message " + message);
                    console.log("messagebody " + message.body);
                    console.log("messagen " + message.body.n);

                    showMessage(message.body);
                    // showMessage(JSON.parse(message.body).message);

                });
            });
        },
        error: function (response) {
            console.log(response);
        }
    });
}

function sendMessage() {
    stompClient.send("/app/dashboard/" + current_id , {}, JSON.stringify({'message': $(".message").val(), 'userId': $(".userId").val()}));
    console.log("Message sent");

}

function showMessage(message) {
    console.log("current_id " + current_id);
    $(".chat").append("<li>" + message + "</li>")


    /*    $.ajax({
        url: "/dashboard/" + current_id + "/process",
        type: "GET",
        success: function(response) {
            $(".chat").append("<li>" + response["message"] + "</li>")
        }
    })*/
}


window.onload = function () {
    connect();
    $("#message").click(sendMessage);
};