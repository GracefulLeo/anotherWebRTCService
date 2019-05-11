'use strict';
const constraints = window.constraints = {
    audio: true,
    video: false
};

//variable for credentials releasing
var id;
let rawurl = window.location.href;
var url = new URL(rawurl);
id = url.searchParams.get("id");
console.log(id);

// Creating new get request instance
var x = new XMLHttpRequest();
let config = null;

// Sending GET request with promise to url , to get a free credentials
    request('/demo/api/get/' + id)
    .then(response => {
        id = response.id;
        // Instantiating configs for authentification on a freeswitch server
    config = {
        uri: response.login + '@fusionpbx.comapptech.net',
        transportOptions: {
            wsServers: ['wss://fusionpbx.comapptech.net:7443'],
            traceSip: true,
            usePreloadedRoute: true,
        },
        realm: "fusionpbx.comapptech.net",
        contact_uri: "sip:" + response.login + "@fusionpbx.comapptech.net",
        authorizationUser: response.login,
        registrarServer: "sip:" + response.login + "@fusionpbx.comapptech.net",
        userAgentString: "sip:" + response.login + "@fusionpbx.comapptech.net",
        displayName: response.login,
        password: response.secret,
        register: true,
        usePreloadedRoute: true,
        hackWssInTransport: true,
        stunServers: "stun:stun.l.google.com:19302",
    };
    })
    .then(() => {
        phone.init(config)
    });

    // Request function with promise
function request(url){
    return new Promise(function(resolve, reject){
        let xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.onload = function() {
            if (this.status == 200) {
                resolve(JSON.parse(this.responseText));
            } else {
                var error = new Error(this.statusText);
                error.code = this.status;
                reject(error);
            }
        };
        xhr.onerror = function() {
            reject(new Error("Network Error"));
        };
        xhr.send();
    });
}

var session;

// Registrating the phone on the FREESWITCH
setTimeout(() => {
    var options = {
        'extraHeaders': ['X-Foo: foo', 'X-Bar: bar', 'Contact: <sip:7816651997@fusionpbx.comapptech.net;transport=wss>;expires=600']
    };
    phone.ua.register(options);

    setTimeout(() => {
        console.log(phone.ua.isRegistered());
        // my code
        myCall();
    }, 1000);
}, 2000);

// Listener of hangup button to hangup the call
var endButton = document.getElementById('hangupbtn');
endButton.addEventListener("click", function () {
    session.terminate();
    // alert("Call Ended");
}, false);

// phone.ua.on('registrationFailed', e => {
//     console.log("My register", e);
// });

// Here we sending another request PUT to release our credentials during the browser tab closing for further usage
window.onbeforeunload = function () {
    var x = new XMLHttpRequest();
    var long = +(id);
    x.open("PUT", "/api/put/" + long, true);
    x.send();
};


// DTMF initialization
document.getElementById('1').addEventListener("click", function () {
    session.dtmf(1);
}, false);
document.getElementById('2').addEventListener("click", function () {
    session.dtmf(2);
}, false);
document.getElementById('3').addEventListener("click", function () {
    session.dtmf(3);
}, false);
document.getElementById('4').addEventListener("click", function () {
    session.dtmf(4);
}, false);
document.getElementById('5').addEventListener("click", function () {
    session.dtmf(5);
}, false);
document.getElementById('6').addEventListener("click", function () {
    session.dtmf(6);
}, false);
document.getElementById('7').addEventListener("click", function () {
    session.dtmf(7);
}, false);
document.getElementById('8').addEventListener("click", function () {
    session.dtmf(8);
}, false);
document.getElementById('9').addEventListener("click", function () {
    session.dtmf(9);
}, false);
document.getElementById('*').addEventListener("click", function () {
    session.dtmf("*");
}, false);
document.getElementById('0').addEventListener("click", function () {
    session.dtmf(0);
}, false);
document.getElementById('#').addEventListener("click", function () {
    session.dtmf("#");
}, false);
var trackAdded = 0;

// document.getElementById('callbtn').addEventListener("click", function () {

 // Call function for instant call after page loaded , and connection to server suceeded
function myCall () {
    // my code start
    btnCall.setAttribute('disabled', true);
    range.setAttribute('disabled', true);
    waitTitle.style.display = 'block';
    // my code end
    
    session = phone.ua.invite('sip:1999@fusionpbx.comapptech.net', {
        // session = phone.ua.invite('sip:1234@127.0.0.1:5061', {
        sessionDescriptionHandlerOptions: {
            constraints: {
                audio: true,
                video: false
            },
        }
    });

    // Here we are getting audio stream and reproducing it in the audio device (e.g. headset)
    let remoteVideo = document.getElementById('remoteVideo');
    let localVideo = document.getElementById('localVideo');

    session.on('trackAdded', function () {
        // We need to check the peer connection to determine which track was added
        var pc = session.sessionDescriptionHandler.peerConnection;

        // Gets remote tracks
        var remoteStream = new MediaStream();
        pc.getReceivers().forEach(function (receiver) {
            remoteStream.addTrack(receiver.track);
        });
        remoteVideo.srcObject = remoteStream;
        remoteVideo.play();

        // Gets local tracks
        var localStream = new MediaStream();
        pc.getSenders().forEach(function (sender) {
            // my code start
            buttons.forEach(btn => btn.removeAttribute('disabled'));
            btnHangup.removeAttribute('disabled');
            range.removeAttribute('disabled');
            waitTitle.style.display = 'none';
            // my code end

            localStream.addTrack(sender.track);
        });
        localVideo.srcObject = localStream;
        localVideo.play();
    });
}

var callBtn = document.getElementById('callbtn');
callBtn.addEventListener("click", myCall);





