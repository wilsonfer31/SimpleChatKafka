import { Component } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Message } from './_Models/message';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'WebSocketChatRoom';

  chatMessages: Message[] = [];
  disabled = true;
  newmessage: string | undefined;
  private stompClient : Stomp.Client | null = null;

  constructor(){}

  ngOnInit() {
    this.connect();
  }

  setConnected(connected: boolean) {
    this.disabled = !connected;

    if (connected) {
      this.chatMessages = [];
    }
  }

  connect() {
    const socket = new SockJS('http://localhost:8080/testchat');
    this.stompClient = Stomp.over(socket);

    const that = this;
    this.stompClient.connect({}, function (frame : any) {
      if(that.stompClient != null){
        that.stompClient.subscribe('/start/initial', (message : any)  => {
          that.showMessage(message.body);
      });
      }
     
    });
  }

  sendMessage() {
    let message = {} as Message;
    message.content = this.newmessage? this.newmessage : "";
    message.sender = "User-Name";

    this.stompClient?.send(
      '/current/resume',
      {},
      JSON.stringify(message)
    );
    this.newmessage = undefined;
  }

  showMessage(message : Message) {
    this.chatMessages.push(message);
  }
}