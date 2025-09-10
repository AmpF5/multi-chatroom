# Multi-Chatroom

A Java-based multi-user chatroom application built with **Netty** framework to demonstrate **TCP network communication** concepts and event-driven network programming patterns.

## Learning Objectives

This project was created as a learning exercise to explore:

- **TCP Socket Programming**: Understanding reliable, connection-oriented network communication
- **Netty Framework**: Learning modern, asynchronous, event-driven network application development
- **Client-Server Architecture**: Implementing a scalable multi-client server system
- **Channel Pipeline Pattern**: Using encoders, decoders, and handlers for message processing
- **Concurrent Programming**: Managing multiple client connections simultaneously

## Features

- **Multi-user support**: Multiple clients can connect and chat simultaneously
- **Real-time messaging**: Instant message broadcasting to all connected users
- **Username system**: Users set nicknames when joining the chat
- **Join/leave notifications**: Automatic announcements when users enter or exit
- **Timestamped messages**: All messages include time information
- **Graceful disconnection**: Proper cleanup when clients disconnect
- **Command support**: Special commands like `/quit` to exit

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
