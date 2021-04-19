let stompClient = null;
let roomId = null;

$(document).ready(function () {
    getRoomId();
    connect();
    $(document).on("submit", "#form-send-message", function (e) {
        sendMessage();
        $('#message').val(``);
        e.preventDefault();
    })
})

function getRoomId() {
    $.ajax({
        url: "/chat/get-roomId",
        type: "GET",
        success: function (data) {
            roomId = data;
            console.log('roomId got:');
            console.log(roomId);
            getAllMessage();
        }
    })
    return roomId;
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

function connect() {
    let socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    stompClient.subscribe('/topic/' + roomId, onReceiveMessage);
}

function onReceiveMessage(data) {
    let message = JSON.parse(data.body);
    $('#for-show-chat').append(`<p>${message['appUser']['name']}: ${message['content']}</p>`);
    document.getElementById('for-show-chat').scrollTop = document.getElementById('for-show-chat').scrollHeight;
}

function onError() {
    alert("Can't connect!");
}

function sendMessage() {
    let content = $('#message').val().trim();
    if (content === '') return;
    let message = {
        "content": content
    }
    stompClient.send('/app/chat/' + roomId, {}, JSON.stringify(message));
}