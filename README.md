# Multi-Chatroom

A Java-based multi-user chatroom application built with **Netty** framework to demonstrate **TCP network communication** concepts and event-driven network programming patterns.

## 🎯 Learning Objectives

This project was created as a learning exercise to explore:

- **TCP Socket Programming**: Understanding reliable, connection-oriented network communication
- **Netty Framework**: Learning modern, asynchronous, event-driven network application development
- **Client-Server Architecture**: Implementing a scalable multi-client server system
- **Channel Pipeline Pattern**: Using encoders, decoders, and handlers for message processing
- **Concurrent Programming**: Managing multiple client connections simultaneously

## 🚀 Features

- **Multi-user support**: Multiple clients can connect and chat simultaneously
- **Real-time messaging**: Instant message broadcasting to all connected users
- **Username system**: Users set nicknames when joining the chat
- **Join/leave notifications**: Automatic announcements when users enter or exit
- **Timestamped messages**: All messages include time information
- **Graceful disconnection**: Proper cleanup when clients disconnect
- **Command support**: Special commands like `/quit` to exit

## 🛠️ Prerequisites

- **Java 17** or higher
- **Maven 3.6+** for dependency management

## 📦 Build Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/AmpF5/multi-chatroom.git
   cd multi-chatroom
   ```

2. **Build the project**:
   ```bash
   mvn clean compile
   ```

3. **Package the application** (optional):
   ```bash
   mvn package
   ```

## 🏃 Running the Application

### Starting the Server

1. **Compile and run the server**:
   ```bash
   mvn exec:java -Dexec.mainClass="org.chatroom.server.Main"
   ```
   
   Or run directly with Java (including dependencies):
   ```bash
   mvn dependency:build-classpath -Dmdep.outputFile=cp.txt
   java -cp "target/classes:$(cat cp.txt)" org.chatroom.server.Main
   ```

2. **Server will start on port 8007**:
   ```
   Server started on port:8007
   ```

### Connecting Clients

1. **In separate terminal windows, start multiple clients**:
   ```bash
   mvn exec:java -Dexec.mainClass="org.chatroom.client.Main"
   ```
   
   Or with Java (including dependencies):
   ```bash
   java -cp "target/classes:$(cat cp.txt)" org.chatroom.client.Main
   ```

2. **Set your username** when prompted:
   ```
   Welcome to MultiChat!
   Set your nickname:
   > YourUsername
   ```

3. **Start chatting**:
   - Type messages and press Enter to send
   - Type `/quit` to disconnect
   - All connected users will see your messages in real-time

## 🏗️ Architecture Overview

### Server Components

- **`ChatServer`**: Main server class using Netty's `ServerBootstrap`
- **`ClientInitHandler`**: Handles new client connections and username setup
- **`ServerHandler`**: Broadcasts messages to all connected clients
- **`MessageAttributes`**: Defines channel attributes for storing user data

### Client Components

- **`ChatClient`**: Client connection manager with user input handling
- **`ClientHandler`**: Processes incoming messages from the server

### Pipeline Components

#### Encoders
- **`StringEncoder`**: Converts strings to bytes for network transmission
- **`TimeEncoder`**: Adds timestamps to outgoing messages
- **`MessageEncoder`**: Final message formatting

#### Decoders
- **`StringDecoder`**: Converts incoming bytes to strings
- **`UsernameDecoder`**: Handles username processing during initial setup

### Network Flow

1. **Client Connection**: Client connects to server on localhost:8007
2. **Username Setup**: Server prompts for nickname, stored in channel attributes
3. **Join Notification**: Server broadcasts user join message to all clients
4. **Message Broadcasting**: Any client message is forwarded to all connected clients
5. **Leave Handling**: Server notifies all clients when someone disconnects

## 🔧 Technical Implementation Details

### Netty Channel Pipeline
```
Client → StringDecoder → StringEncoder → TimeEncoder → MessageEncoder → UsernameDecoder → ClientInitHandler → ServerHandler
```

### Key Concepts Demonstrated

- **Asynchronous I/O**: Non-blocking network operations using Netty's event loop
- **Channel Groups**: Efficient broadcasting to multiple clients
- **Pipeline Architecture**: Modular message processing with reusable components
- **Resource Management**: Proper cleanup of connections and event loop groups

## 📝 Example Session

```
Terminal 1 (Server):
$ java -cp target/classes org.chatroom.server.Main
Server started on port:8007

Terminal 2 (Client 1):
$ java -cp target/classes org.chatroom.client.Main
Welcome to MultiChat!
Set your nickname:
> Alice
Alice has joined the chat
> Hello everyone!

Terminal 3 (Client 2):
$ java -cp target/classes org.chatroom.client.Main
Welcome to MultiChat!
Set your nickname:
> Bob
Bob has joined the chat
14:30:25
Alice has joined the chat
14:30:45
Hello everyone!
> Hi Alice!
```

## 🧰 Dependencies

- **[Netty 4.2.3.Final](https://netty.io/)**: High-performance asynchronous event-driven network application framework

## 📚 Learning Resources

To better understand the concepts used in this project:

- [Netty Official Documentation](https://netty.io/wiki/)
- [TCP Protocol Fundamentals](https://tools.ietf.org/html/rfc793)
- [Java NIO and Netty](https://netty.io/wiki/user-guide-for-4.x.html)
- [Event-Driven Programming Patterns](https://en.wikipedia.org/wiki/Event-driven_programming)

## 🤝 Contributing

This is a learning project, but contributions are welcome! Feel free to:
- Add new features (private messaging, chat rooms, etc.)
- Improve error handling
- Add unit tests
- Enhance the user interface

## 📄 License

This project is open source and available under the [MIT License](LICENSE).