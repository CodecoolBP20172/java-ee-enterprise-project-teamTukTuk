window.onload = function() {

    function connect() {
        let socket = new SockJS("/dashboard");
        let stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('dashboard/' + getData(), function() {
                console.log("You are succesfully connected to chatbox: " + getData())
            });
        });
    }


    function getData() {
        let chatBoxId = null;

        $.ajax({
            type: "GET",
            url: "/app/current_chatbox",
            success: function (response) {
                chatBoxId = response["id"];
            },
            error: function (response) {
                console.log(response);
            }
        });
        return chatBoxId;
    }

    $("#start").click(function() {
        connect();
    });

    function sendMessage() {
        stompClient.send("/app/message", {}, JSON.stringify({'message': $("#message").val()}));
    }

};