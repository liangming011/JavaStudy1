package io.nio.channel;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 在Java NIO中，如果两个通道中有一个是FileChannel，那你可以直接将数据从一个channel（译者注：channel中文常译作通道）传输到另外一个channel。
 * */
public class ChannelToChannel {

    public static void main(String[] args) {
        //transferFrom():从源channel获取
        transferFromT();

        //transferTo():传输到目标 channel
        transferToT();
    }

    public static void transferFromT() {
        try {
            /**
             * transferFrom()
             * FileChannel的transferFrom()方法可以将数据从源通道传输到FileChannel中
             * （译者注：这个方法在JDK文档中的解释为将字节从给定的可读取字节通道传输到此通道的文件中）。
             *
             * 此外要注意，在SoketChannel的实现中，SocketChannel只会传输此刻准备好的数据（可能不足count字节）。
             * 因此，SocketChannel可能不会将请求的所有数据(count个字节)全部传输到FileChannel中。
             * */
            RandomAccessFile fromFile = new RandomAccessFile("pom.xml", "rw");
            FileChannel fromChannel = fromFile.getChannel();

            RandomAccessFile toFile = new RandomAccessFile("pom.xml", "rw");
            FileChannel toChannel = toFile.getChannel();

            //方法的输入参数position表示从position处开始向目标文件写入数据，count表示最多传输的字节数。
            long position = 0;
            long count = fromChannel.size();

            //如果源通道的剩余空间小于 count 个字节，则所传输的字节数要小于请求的字节数。
            toChannel.transferFrom(fromChannel,position, count);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void transferToT() {
        try {
            /**
             * transferTo()
             *
             * transferTo()方法将数据从FileChannel传输到其他的channel中。下面是一个简单的例子：
             *
             * 是不是发现这个例子和前面那个例子特别相似？除了调用方法的FileChannel对象不一样外，其他的都一样。
             * 上面所说的关于SocketChannel的问题在transferTo()方法中同样存在。SocketChannel会一直传输数据直到目标buffer被填满。
             * */
            RandomAccessFile fromFile = new RandomAccessFile("pom.xml", "rw");
            FileChannel fromChannel = fromFile.getChannel();

            RandomAccessFile toFile = new RandomAccessFile("pom.xml", "rw");
            FileChannel toChannel = toFile.getChannel();

            //方法的输入参数position表示从position处开始向目标文件写入数据，count表示最多传输的字节数。
            long position = 0;
            long count = fromChannel.size();

            //如果源通道的剩余空间小于 count 个字节，则所传输的字节数要小于请求的字节数。
            fromChannel.transferTo(position, count,toChannel);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
