let roomId = null;
let stompClient = null;

$(document).ready(function () {
    $(document).on("click", ".button-show-message", function (e) {
        roomId = $(e.target).attr("id");
        connect();
        getAllMessage();
        document.getElementById('for-show-chat').scrollTop = document.getElementById('for-show-chat').scrollHeight;
    })
    $(document).on("submit", "#form-send-message", function (e) {
        sendMessage();
        e.preventDefault();
    })
})

function connect() {
    let socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, error)
}

function error() {
    alert("Can't connect, please try again");
}

function onConnected() {
    stompClient.subscribe("/topic/" + roomId, onReceiveMessage);
}

function onReceiveMessage(data) {
    let message = JSON.parse(data.body);
    $('#for-show-chat').append(`<p>${message['appUser']['name']}: ${message['content']}</p>`);
    document.getElementById('for-show-chat').scrollTop = document.getElementById('for-show-chat').scrollHeight;
}

function sendMessage() {
    let content = $('#message').val().trim();
    if (content === '') return;
    let message = {
        "content": content
    }
    stompClient.send("/app/chat/" + roomId, {}, JSON.stringify(message));
    $('#message').val(``);
}

function getAllMessage() {
    $.ajax({
        url: "/chat/get-all-message/" + roomId,
        type: "GET",
        success: function (messages) {
            jQuery.each(messages, function (index, message) {
                $('#for-show-chat').append(`<p>${message['appUser']['name']}: ${message['content']}</p>`);
            })
            document.getElementById('for-show-chat').scrollTop = document.getElementById('for-show-chat').scrollHeight;
        }
    })
}