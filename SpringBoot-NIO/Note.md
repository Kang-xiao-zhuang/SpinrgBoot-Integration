# Java 网络编程系列之 NIO

# 1. Java NIO 概述

Java NIO（New IO 或 Non Blocking IO）是从 Java 1.4 版本开始引入的一个新的 IO API，可以替代标准的 Java IO API。NIO 支持面向缓冲区的、基于通道的 IO 操 作。NIO 将以更加高效的方式进行文件的读写操作。

## 1.1 阻塞IO

通常在进行同步 I/O 操作时，如果读取数据，代码会阻塞直至有可供读取的数据。同 样，写入调用将会阻塞直至数据能够写入。传统的 Server/Client 模式会基于 TPR （Thread per Request）,服务器会为每个客户端请求建立一个线程，由该线程单独负 责处理一个客户请求。这种模式带来的一个问题就是线程数量的剧增，大量的线程会 增大服务器的开销。大多数的实现为了避免这个问题，都采用了线程池模型，并设置 线程池线程的最大数量，这由带来了新的问题，如果线程池中有 100 个线程，而有 100 个用户都在进行大文件下载，会导致第 101 个用户的请求无法及时处理，即便第 101 个用户只想请求一个几 KB 大小的页面。传统的 Server/Client 模式如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/75bce2f9861c4c10968f42e05ec20ce5.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

## 1.2 非阻塞 IO(NIO)

NIO 中非阻塞 I/O 采用了基于 Reactor 模式的工作方式，I/O 调用不会被阻塞，相反 是注册感兴趣的特定 I/O 事件，如可读数据到达，新的套接字连接等等，在发生特定 事件时，系统再通知我们。NIO 中实现非阻塞 I/O 的核心对象就是 Selector， Selector 就是注册各种 I/O 事件地方，而且当我们感兴趣的事件发生时，就是这个对 象告诉我们所发生的事件，如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/fc5711fdeaae439bb6f529912b47172c.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

从图中可以看出，当有读或写等任何注册的事件发生时，可以从 Selector 中获得相应 的 SelectionKey，同时从 SelectionKey 中可以找到发生的事件和该事件所发生的具 体的 SelectableChannel，以获得客户端发送过来的数据。 非阻塞指的是 IO 事件本身不阻塞,但是获取 IO 事件的 select()方法是需要阻塞等待的. 区别是阻塞的 IO 会阻塞在 IO 操作上, NIO 阻塞在事件获取上,没有事件就没有 IO, 从 高层次看 IO 就不阻塞了.也就是说只有 IO 已经发生那么我们才评估 IO 是否阻塞,但是 select()阻塞的时候 IO 还没有发生,何谈 IO 的阻塞呢?NIO 的本质是延迟 IO 操作到真 正发生 IO 的时候,而不是以前的只要 IO 流打开了就一直等待 IO 操作

## 1.3 NIO 概述

Java NIO 由以下几个核心部分组成：

- Channels
- Buffers
- Selectors

### 1.3.1 Channel

首先说一下 Channel，可以翻译成“通道”。Channel 和 IO 中的 Stream(流)是差不 多一个等级的。只不过 Stream 是单向的，譬如：InputStream, OutputStream.而 Channel 是双向的，既可以用来进行读操作，又可以用来进行写操作。 NIO 中的 Channel 的主要实现有：FileChannel、DatagramChannel、 SocketChannel 和 ServerSocketChannel，这里看名字就可以猜出个所以然来：分别 可以对应文件 IO、UDP 和 TCP（Server 和 Client）。

### 1.3.2 Buffer

NIO 中的关键 Buffer 实现有：ByteBuffer, CharBuffer, DoubleBuffer, FloatBuffer,  IntBuffer, LongBuffer, ShortBuffer，分别对应基本数据类型: byte, char, double,  float, int, long, short。

### 1.3.3 Selector

Selector 运行单线程处理多个 Channel，如果你的应用打开了多个通道，但每个连接 的流量都很低，使用 Selector 就会很方便。例如在一个聊天服务器中。要使用 Selector, 得向 Selector 注册 Channel，然后调用它的 select()方法。这个方法会一直 阻塞到某个注册的通道有事件就绪。一旦这个方法返回，线程就可以处理这些事件， 事件的例子有如新的连接进来、数据接收等。

# 2. Java NIO（Channel）

## 2.1 Channel 概述

Channel 是一个通道，可以通过它读取和写入数据，它就像水管一样，网络数据通过 Channel 读取和写入。通道与流的不同之处在于通道是双向的，流只是在一个方向上 移动（一个流必须是 InputStream 或者 OutputStream 的子类），而且通道可以用于 读、写或者同时用于读写。因为 Channel 是全双工的，所以它可以比流更好地映射底 层操作系统的 API。 NIO 中通过 channel 封装了对数据源的操作，通过 channel 我们可以操作数据源，但 又不必关心数据源的具体物理结构。这个数据源可能是多种的。比如，可以是文件， 也可以是网络 socket。在大多数应用中，channel 与文件描述符或者 socket 是一一 对应的。Channel 用于在字节缓冲区和位于通道另一侧的实体（通常是一个文件或套 接字）之间有效地传输数据。

与缓冲区不同，通道 API 主要由接口指定。不同的操作系统上通道实现（Channel  Implementation）会有根本性的差异，所以通道 API 仅仅描述了可以做什么。因此很 自然地，通道实现经常使用操作系统的本地代码。通道接口允许您以一种受控且可移 植的方式来访问底层的 I/O 服务。 Channel 是一个对象，可以通过它读取和写入数据。拿 NIO 与原来的 I/O 做个比 较，通道就像是流。所有数据都通过 Buffer 对象来处理。您永远不会将字节直接写入 通道中，相反，您是将数据写入包含一个或者多个字节的缓冲区。同样，您不会直接 从通道中读取字节，而是将数据从通道读入缓冲区，再从缓冲区获取这个字节。 Java NIO 的通道类似流，但又有些不同： - 既可以从通道中读取数据，又可以写数据到通道。但流的读写通常是单向的。 - 通道可以异步地读写。 - 通道中的数据总是要先读到一个 Buffer，或者总是要从一个 Buffer 中写入。 正如上面所说，从通道读取数据到缓冲区，从缓冲区写入数据到通道。如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/c3feaa9f56d248a580d850b66ee35217.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

## 2.2 Channel 实现

下面是 Java NIO 中最重要的 Channel 的实现：

- FileChannel
-  DatagramChannel
- SocketChannel
- ServerSocketChannel
- FileChannel 从文件中读写数据
- DatagramChannel 能通过 UDP 读写网络中的数据
- SocketChannel 能通过 TCP 读写网络中的数据
- ServerSocketChannel 可以监听新进来的 TCP 连接，像 Web 服务器那样。对 每一个新进来的连接都会创建一个 SocketChannel。 正如你所看到的，这些通道涵盖了 UDP 和 TCP 网络 IO，以及文件 IO

## 2.3 FileChannel 介绍和示例

FileChannel 类可以实现常用的 read，write 以及 scatter/gather 操作，同时它也提 供了很多专用于文件的新方法。

![在这里插入图片描述](https://img-blog.csdnimg.cn/df8d4e17024947a39b1e85c6a72d30eb.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

```java
package com.zhuang.springbootnio.channel;

import lombok.extern.slf4j.Slf4j;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class FileChannelDemo1 {
    public static void main(String[] args) throws Exception {
        // 创建FileChannel
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:\\01.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        // 创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 读取数据到buffer中
        int bytesRead = channel.read(buffer);
        while (bytesRead != -1) {
            log.info("读取了：" + bytesRead);
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println((char) buffer.get());
            }
            buffer.clear();
            bytesRead = channel.read(buffer);
        }
        randomAccessFile.close();
        log.info("结束了！！！！！！！！");
    }
}
```

**Buffer 通常的操作**

将数据写入缓冲区

调用 buffer.flip() 反转读写模式

从缓冲区读取数据

调用 buffer.clear() 或 buffer.compact() 清除缓冲区内容

## 2.4 FileChannel 操作详解

### 2.4.1 打开 FileChannel

在使用 FileChannel 之前，必须先打开它。但是，我们无法直接打开一个 FileChannel，需要通过使用一个 InputStream、OutputStream 或 RandomAccessFile 来获取一个 FileChannel 实例。下面是通过 RandomAccessFile 打开 FileChannel 的示例：

```java
RandomAccessFile randomAccessFile = new RandomAccessFile("d:\\01.txt", "rw");
FileChannel channel = randomAccessFile.getChannel();
```

### 2.4.2 从 FileChannel 读取数据

调用多个 read()方法之一从 FileChannel 中读取数据。如：

```java
// 创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 读取数据到buffer中
        int bytesRead = channel.read(buffer);
```

首先，分配一个 Buffer。从 FileChannel 中读取的数据将被读到 Buffer 中。然后，调 用 FileChannel.read()方法。该方法将数据从 FileChannel 读取到 Buffer 中。read() 方法返回的 int 值表示了有多少字节被读到了 Buffer 中。如果返回-1，表示到了文件 末尾。

### 2.4.3 向 FileChannel 写数据

使用 FileChannel.write()方法向 FileChannel 写数据，该方法的参数是一个 Buffer。 如：

```java
package com.zhuang.springbootnio.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileChannelDemo2 {
    public static void main(String[] args) throws Exception {
        //FileChannel写操作
        // 打开FielChannel
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:\\01.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        // 创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        String data = "data 123";
        buffer.clear();

        //写入数据
        buffer.put(data.getBytes(StandardCharsets.UTF_8));
        buffer.flip();

        // FileChannel最终完成实现
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        // 关闭
        channel.close();
    }
}
```

注意 FileChannel.write()是在 while 循环中调用的。因为无法保证 write()方法一次能 向 FileChannel 写入多少字节，因此需要重复调用 write()方法，直到 Buffer 中已经没 有尚未写入通道的字节。

### 2.4.4 关闭 FileChannel

用完 FileChannel 后必须将其关闭。如：

```java
inChannel.close();
```

### 2.4.5 FileChannel 的 position 方法

有时可能需要在 FileChannel 的某个特定位置进行数据的读/写操作。可以通过调用 position()方法获取 FileChannel 的当前位置。也可以通过调用 position(long pos)方 法设置 FileChannel 的当前位置。

这里有两个例子:

**long pos = channel.position();**

**channel.position(pos +123);**

如果将位置设置在文件结束符之后，然后试图从文件通道中读取数据，读方法将返回1 （文件结束标志）。 如果将位置设置在文件结束符之后，然后向通道中写数据，文件将撑大到当前位置并 写入数据。这可能导致“文件空洞”，磁盘上物理文件中写入的数据间有空隙。

### 2.4.6 FileChannel 的 size 方法

FileChannel 实例的 size()方法将返回该实例所关联文件的大小。

**如: long fileSize = channel.size();**

### 2.4.7 FileChannel 的 truncate 方法

可以使用 FileChannel.truncate()方法截取一个文件。截取文件时，文件将中指定长度 后面的部分将被删除。如： **channel.truncate(1024);** 这个例子截取文件的前 1024 个字节。

### 2.4.8 FileChannel 的 force 方法

FileChannel.force()方法将通道里尚未写入磁盘的数据强制写到磁盘上。出于性能方 面的考虑，操作系统会将数据缓存在内存中，所以无法保证写入到 FileChannel 里的 数据一定会即时写到磁盘上。

要保证这一点，需要调用 force()方法。

**force()方法有一个 boolean 类型的参数，指明是否同时将文件元数据（权限信息等） 写到磁盘上。**

### 2.4.9 FileChannel 的 transferTo 和 transferFrom 方法

通道之间的数据传输： 如果两个通道中有一个是 FileChannel，那你可以直接将数据从一个 channel 传输到 另外一个 channel。

**（1）transferFrom()方法**

FileChannel 的 transferFrom()方法可以将数据从源通道传输到 FileChannel 中（译 者注：这个方法在 JDK 文档中的解释为将字节从给定的可读取字节通道传输到此通道 的文件中）。下面是一个 FileChannel 完成文件间的复制的例子：

```java
package com.zhuang.springbootnio.channel;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class FileChannelDemo3 {
    //通道之间数据传输
    public static void main(String[] args) throws Exception {
        // transferForm
        // 创建两个FileChannel
        RandomAccessFile aFile = new RandomAccessFile("d:\\01.txt", "rw");
        FileChannel fromChannel = aFile.getChannel();

        RandomAccessFile bFile = new RandomAccessFile("d:\\02.txt", "rw");
        FileChannel toChannel = bFile.getChannel();

        // fromChannel 传输到 toChannel
        long position = 0;
        long size = fromChannel.size();
        toChannel.transferFrom(fromChannel, position, size);

        aFile.close();
        bFile.close();
        System.out.println("over!");
    }
}
```

方法的输入参数 position 表示从 position 处开始向目标文件写入数据，count 表示 最多传输的字节数。如果源通道的剩余空间小于 count 个字节，则所传输的字节数要 小于请求的字节数。此外要注意，在 SoketChannel 的实现中，SocketChannel 只会 传输此刻准备好的数据（可能不足 count 字节）。因此，SocketChannel 可能不会将 请求的所有数据(count 个字节)全部传输到 FileChannel 中。

**（2）transferTo()方法**

transferTo()方法将数据从 FileChannel 传输到其他的 channel 中。 下面是一个 transferTo()方法的例子：

```java
package com.zhuang.springbootnio.channel;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class FileChannelDemo4 {
    //通道之间数据传输
    public static void main(String[] args) throws Exception {
        // transferForm
        // 创建两个FileChannel
        RandomAccessFile aFile = new RandomAccessFile("d:\\01.txt", "rw");
        FileChannel fromChannel = aFile.getChannel();

        RandomAccessFile bFile = new RandomAccessFile("d:\\03.txt", "rw");
        FileChannel toChannel = bFile.getChannel();

        // fromChannel 传输到 toChannel
        long size = fromChannel.size();
        fromChannel.transferTo(1, size, toChannel);

        aFile.close();
        bFile.close();
        System.out.println("over!");
    }
}
```

## 2.5 Socket 通道

（1）新的 socket 通道类可以运行非阻塞模式并且是可选择的，可以激活大程序（如 网络服务器和中间件组件）巨大的可伸缩性和灵活性。本节中我们会看到，再也没有 为每个 socket 连接使用一个线程的必要了，也避免了管理大量线程所需的上下文交换 开销。借助新的 NIO 类，一个或几个线程就可以管理成百上千的活动 socket 连接了 并且只有很少甚至可能没有性能损失。所有的 socket 通道类(DatagramChannel、 SocketChannel 和 ServerSocketChannel)都继承了位于 java.nio.channels.spi 包中 的 AbstractSelectableChannel。这意味着我们可以用一个 Selector 对象来执行 socket 通道的就绪选择（readiness selection）。

（2）请注意 DatagramChannel 和 SocketChannel 实现定义读和写功能的接口而 ServerSocketChannel 不实现。ServerSocketChannel 负责监听传入的连接和创建新 的 SocketChannel 对象，它本身从不传输数据。

（3）在我们具体讨论每一种 socket 通道前，您应该了解 **socket 和 socket 通道之间 的关系**。通道是一个连接 I/O 服务导管并提供与该服务交互的方法。就**某个 socket 而 言，它不会再次实现与之对应的 socket 通道类中的 socket 协议 API，而 java.net 中 已经存在的 socket 通道都可以被大多数协议操作重复**使用。

全部 socket 通道类（**DatagramChannel、SocketChannel 和 ServerSocketChannel**）在被实例化时都会创建一个对等 socket 对象。这些是我们所 熟悉的来自 java.net 的类（Socket、ServerSocket 和 DatagramSocket），它们已 经被更新以识别通道。对等 socket 可以通过调用 socket( )方法从一个通道上获取。 此外，这三个 java.net 类现在都有 getChannel( )方法。

（4）要把一个 socket 通道置于非阻塞模式，我们要依靠所有 socket 通道类的公有 超级类：SelectableChannel。就绪选择（readiness selection）是一种可以用来查 询通道的机制，该查询可以判断通道是否准备好执行一个目标操作，如读或写。非阻 塞 I/O 和可选择性是紧密相连的，那也正是管理阻塞模式的 API 代码要在 SelectableChannel 超级类中定义的原因。 设置或重新设置一个通道的阻塞模式是很简单的，只要调用 configureBlocking( )方 法即可，传递参数值为 true 则设为阻塞模式，参数值为 false 值设为非阻塞模式。可 以通过调用 isBlocking( )方法来判断某个 socket 通道当前处于哪种模式。

![在这里插入图片描述](https://img-blog.csdnimg.cn/4e9210bb46cf442ba92f520ea0556889.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

非阻塞 socket 通常被认为是服务端使用的，因为它们使同时管理很多 socket 通道变 得更容易。但是，在客户端使用一个或几个非阻塞模式的 socket 通道也是有益处的， 例如，借助非阻塞 socket 通道，GUI 程序可以专注于用户请求并且同时维护与一个或 多个服务器的会话。在很多程序上，非阻塞模式都是有用的。

偶尔地，我们也会需要防止 socket 通道的阻塞模式被更改。API 中有一个 blockingLock( )方法，该方法会返回一个非透明的对象引用。返回的对象是通道实现 修改阻塞模式时内部使用的。只有拥有此对象的锁的线程才能更改通道的阻塞模式。

下面分别介绍这 3 个通道

### 2.5.1  ServerSocketChannel

ServerSocketChannel 是一个基于通道的 socket 监听器。它同我们所熟悉的 java.net.ServerSocket 执行相同的任务，不过它增加了通道语义，因此能够在非阻塞 模式下运行。 由于 ServerSocketChannel 没有 bind()方法，因此有必要取出对等的 socket 并使用 它来绑定到一个端口以开始监听连接。我们也是使用对等 ServerSocket 的 API 来根 据需要设置其他的 socket 选项。

同 java.net.ServerSocket 一样，ServerSocketChannel 也有 accept( )方法。一旦创 建了一个 ServerSocketChannel 并用对等 socket 绑定了它，然后您就可以在其中一 个上调用 accept()。如果您选择在 ServerSocket 上调用 accept( )方法，那么它会同 任何其他的 ServerSocket 表现一样的行为：总是阻塞并返回一个 java.net.Socket 对 象。如果您选择在 ServerSocketChannel 上调用 accept( )方法则会返回 SocketChannel 类型的对象，返回的对象能够在非阻塞模式下运行。

换句话说： ServerSocketChannel 的 accept()方法会返回 SocketChannel 类型对象， SocketChannel 可以在非阻塞模式下运行。 其它 Socket 的 accept()方法会阻塞返回一个 Socket 对象。如果 ServerSocketChannel 以非阻塞模式被调用，当没有传入连接在等待时， ServerSocketChannel.accept( )会立即返回 null。正是这种检查连接而不阻塞的能力 实现了可伸缩性并降低了复杂性。可选择性也因此得到实现。我们可以使用一个选择 器实例来注册 ServerSocketChannel 对象以实现新连接到达时自动通知的功能

**以下代码演示了如何使用一个非阻塞的 accept( )方法：**

```java
package com.zhuang.springbootnio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ServerSocketChannelDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 端口号
        int port = 8888;

        // buffer
        ByteBuffer buffer = ByteBuffer.wrap("hello zk".getBytes(StandardCharsets.UTF_8));

        //  ServerSocketChannel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 绑定
        ssc.socket().bind(new InetSocketAddress(port));

        //设置非阻塞模式
        ssc.configureBlocking(false);

        while (true) {
            System.out.println("WAITING FOR CONNECTION");
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                System.out.println("NULL");
                Thread.sleep(2000);
            } else {
                System.out.println("INCOMING CONNECTION FROM ：" + sc.socket().getRemoteSocketAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }
}
```

**（1）打开 ServerSocketChannel**

通过调用 ServerSocketChannel.open() 方法来打开 ServerSocketChannel.  ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

**（2）关闭 ServerSocketChannel**

通过调用 ServerSocketChannel.close() 方法来关闭 ServerSocketChannel.  serverSocketChannel.close();

**（3）监听新的连接**

通过 ServerSocketChannel.accept() 方法监听新进的连接。当 accept()方法返回时 候,它返回一个包含新进来的连接的 SocketChannel。因此, accept()方法会一直阻塞 到有新连接到达。

通常不会仅仅只监听一个连接,在 while 循环中调用 accept()方法. 如下面的例子：

![在这里插入图片描述](https://img-blog.csdnimg.cn/8f09695058744d1d9433d95c4bafc58f.png)

**（4）阻塞模式**

![在这里插入图片描述](https://img-blog.csdnimg.cn/f40b41bad27f4d48853c68f4e718c4c5.png)

会在 SocketChannel sc = ssc.accept();这里阻塞住进程。

**（5）非阻塞模式**

ServerSocketChannel 可以设置成非阻塞模式。在非阻塞模式下，accept() 方法会立 刻返回，如果还没有新进来的连接,返回的将是 null。 因此，需要检查返回的 SocketChannel 是否是 null.如：

![在这里插入图片描述](https://img-blog.csdnimg.cn/791a6249f84341c19cb305fadabdd6fa.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

### 2.5.2 SocketChannel

**SocketChannel 介绍**

Java NIO 中的 SocketChannel 是一个连接到 TCP 网络套接字的通道。

> A selectable channel for stream-oriented connecting sockets.

以上是 Java docs 中对于 SocketChannel 的描述：SocketChannel 是一种面向流连接 sockets 套接字的可选择通道。从这里可以看出：

- SocketChannel 是用来连接 Socket 套接字
- SocketChannel 主要用途用来处理网络 I/O 的通道
- SocketChannel 是基于 TCP 连接传输
- SocketChannel 是基于 TCP 连接传输

**SocketChannel 特征：**

（1）对于已经存在的 socket 不能创建 SocketChannel

（2）SocketChannel 中提供的 open 接口创建的 Channel 并没有进行网络级联，需要使 用 connect 接口连接到指定地址 （3）未进行连接的 SocketChannle 执行 I/O 操作时，会抛出 NotYetConnectedException

（4）SocketChannel 支持两种 I/O 模式：阻塞式和非阻塞式

（5）SocketChannel 支持异步关闭。如果 SocketChannel 在一个线程上 read 阻塞，另 一个线程对该SocketChannel 调用 shutdownInput，则读阻塞的线程将返回-1 表示没有 读取任何数据；如果 SocketChannel 在一个线程上 write 阻塞，另一个线程对该 SocketChannel 调用 shutdownWrite，则写阻塞的线程将抛出 AsynchronousCloseException

（6）SocketChannel 支持设定参数

SO_SNDBUF 套接字发送缓冲区大小

SO_RCVBUF 套接字接收缓冲区大小

SO_KEEPALIVE 保活连接

O_REUSEADDR 复用地址

SO_LINGER 有数据传输时延缓关闭 Channel (只有在非阻塞模式下有用)

TCP_NODELAY 禁用 Nagle 算法

**SocketChannel 的使用**

**创建 SocketChannel**

方法一：

```java
SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));
```

方法二：

```java
SocketChannel socketChanne2 = SocketChannel.open();
socketChanne2.connect(new InetSocketAddress("www.baidu.com", 80));
```

直接使用有参 open api 或者使用无参 open api，但是在无参 open 只是创建了一个 SocketChannel 对象，并没有进行实质的 tcp 连接。

**连接校验**

```java
socketChannel.isOpen(); // 测试 SocketChannel 是否为 open 状态
socketChannel.isConnected(); //测试 SocketChannel 是否已经被连接
socketChannel.isConnectionPending(); //测试 SocketChannel 是否正在进行连接
socketChannel.finishConnect(); //校验正在进行套接字连接的 SocketChannel是否已经完成连接
```

**读写模式**

SocketChannel 支持阻塞和非阻塞两种模式：

```java
socketChannel.configureBlocking(false);
```

通过以上方法设置 SocketChannel 的读写模式。false 表示非阻塞，true 表示阻塞。

**读写**

```java
SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));
ByteBuffer byteBuffer = ByteBuffer.allocate(16);
socketChannel.read(byteBuffer);
socketChannel.close();
System.out.println("read over");
```

以上为阻塞式读，当执行到 read 出，线程将阻塞，控制台将无法打印 read over

```java
SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));
socketChannel.configureBlocking(false);
ByteBuffer byteBuffer = ByteBuffer.allocate(16);
socketChannel.read(byteBuffer);
socketChannel.close();
System.out.println("read over");
```

以上为非阻塞读，控制台将打印 read over 读写都是面向缓冲区，这个读写方式与前文中的 FileChannel 相同。

**设置和获取参数**

```java
socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, 
Boolean.TRUE)
 .setOption(StandardSocketOptions.TCP_NODELAY, Boolean.TRUE);
```

通过 setOptions 方法可以设置 socket 套接字的相关参数

```java
socketChannel.getOption(StandardSocketOptions.SO_KEEPALIVE);
socketChannel.getOption(StandardSocketOptions.SO_RCVBUF);
```

可以通过 getOption 获取相关参数的值。如默认的接收缓冲区大小是 8192byte。 SocketChannel 还支持多路复用，但是多路复用在后续内容中会介绍到。

### 2.5.3 DatagramChannel

正如 SocketChannel 对应 Socket，ServerSocketChannel 对应 ServerSocket，每 一个 DatagramChannel 对象也有一个关联的 DatagramSocket 对象。正如 SocketChannel 模拟连接导向的流协议（如 TCP/IP），DatagramChannel 则模拟包 导向的无连接协议（如 UDP/IP）。DatagramChannel 是无连接的，每个数据报 （datagram）都是一个自包含的实体，拥有它自己的目的地址及不依赖其他数据报的 数据负载。与面向流的的 socket 不同，DatagramChannel 可以发送单独的数据报给 不同的目的地址。同样，DatagramChannel 对象也可以接收来自任意地址的数据 包。每个到达的数据报都含有关于它来自何处的信息（源地址）

```java
@Test
    void sendDatagram() throws Exception {
        DatagramChannel sendChannel = DatagramChannel.open();
        InetSocketAddress sendAdderss = new InetSocketAddress("127.0.0.1", 9999);
        // 发送
        while (true) {
            ByteBuffer buffer = ByteBuffer.wrap("发送hello".getBytes(StandardCharsets.UTF_8));
            sendChannel.send(buffer, sendAdderss);
            System.out.println("已经完成发送");
            Thread.sleep(1000);
        }
    }

    @Test
    void receiveDatagram() throws Exception {
        DatagramChannel receiveChannel = DatagramChannel.open();
        InetSocketAddress receiveAdderss = new InetSocketAddress(9999);
        // 发送
        receiveChannel.bind(receiveAdderss);

        // buffer
        ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);

        //接收
        while (true) {
            SocketAddress socketAddress = receiveChannel.receive(receiveBuffer);
            receiveBuffer.flip();
            System.out.println(socketAddress.toString());
            System.out.println(Charset.forName("UTF-8").decode(receiveBuffer));
        }
    }

    @Test
    void testConnect() throws IOException {
        DatagramChannel connChannel = DatagramChannel.open();
        // 绑定
        connChannel.bind(new InetSocketAddress(9999));
        connChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        // wriete方法
        connChannel.write(ByteBuffer.wrap("发送hello".getBytes(StandardCharsets.UTF_8)));
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        while (true) {
            readBuffer.clear();
            connChannel.read(readBuffer);
            readBuffer.flip();
            System.out.println(Charset.forName("UTF-8").decode(readBuffer));
        }
    }
```

## 2.6 Scatter/Gather

Java NIO 开始支持 scatter/gather，scatter/gather 用于描述从 Channel 中读取或 者写入到 Channel 的操作。

**分散（scatter）**从 Channel 中读取是指在读操作时将读取的数据写入多个 buffer 中。因此，Channel 将从 Channel 中读取的数据“分散（scatter）”到多个 Buffer 中。

**聚集（gather）**写入 Channel 是指在写操作时将多个 buffer 的数据写入同一个 Channel，因此，Channel 将多个 Buffer 中的数据“聚集（gather）”后发送到 Channel。

scatter / gather 经常用于需要将传输的数据分开处理的场合，例如传输一个由消息头 和消息体组成的消息，你可能会将消息体和消息头分散到不同的 buffer 中，这样你可 以方便的处理消息头和消息体。

### 2.5.1 Scattering Reads

Scattering Reads 是指数据从一个 channel 读取到多个 buffer 中。如下图描述：

![在这里插入图片描述](https://img-blog.csdnimg.cn/98eff57d2d0e43389c563b83cdda18cf.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

```java
ByteBuffer header = ByteBuffer.allocate(128);
ByteBuffer body = ByteBuffer.allocate(1024);
ByteBuffer[] bufferArray = { header, body };
channel.read(bufferArray);
```

注意 buffer 首先被插入到数组，然后再将数组作为 channel.read() 的输入参数。 read()方法按照 buffer 在数组中的顺序将从 channel 中读取的数据写入到 buffer，当 一个 buffer 被写满后，channel 紧接着向另一个 buffer 中写。 Scattering Reads 在移动下一个 buffer 前，必须填满当前的 buffer，这也意味着它 不适用于动态消息(译者注：消息大小不固定)。换句话说，如果存在消息头和消息体， 消息头必须完成填充（例如 128byte），Scattering Reads 才能正常工作。

### 2.6.2 Gathering Writes

Gathering Writes 是指数据从多个 buffer 写入到同一个 channel。如下图描述：

![在这里插入图片描述](https://img-blog.csdnimg.cn/98eff57d2d0e43389c563b83cdda18cf.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

```java
ByteBuffer header = ByteBuffer.allocate(128);
ByteBuffer body = ByteBuffer.allocate(1024);
//write data into buffers
ByteBuffer[] bufferArray = { header, body };
channel.write(bufferArray);
```

buffers 数组是 write()方法的入参，write()方法会按照 buffer 在数组中的顺序，将数 据写入到 channel，注意只有 position 和 limit 之间的数据才会被写入。因此，如果 一个 buffer 的容量为 128byte，但是仅仅包含 58byte 的数据，那么这 58byte 的数 据将被写入到 channel 中。因此与 Scattering Reads 相反，Gathering Writes 能较 好的处理动态消息。