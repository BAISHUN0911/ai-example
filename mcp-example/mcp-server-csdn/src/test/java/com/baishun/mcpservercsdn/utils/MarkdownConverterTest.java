package com.baishun.mcpservercsdn.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description:
 * @Author shengy
 * @Date 2025/7/30 17:58
 */
@ExtendWith(MockitoExtension.class)
class MarkdownConverterTest {
  @Test
  void testConvertToHtml() {
//    String markdown = "### This is a test\n123";
    String markdown = """
    好的，作为Java开发岗位的技术面试官，以下是两个关于Redis技术的常见面试题及其对应的理想回答：

---

**面试题 1： 请谈谈你对Redis的理解，以及它在Java应用中常见的应用场景有哪些？**

**回答要点：**

1.  **核心理解与特点：**
    *   首先，我会强调Redis是一个**开源的、内存中的数据结构存储系统**。
    *   它可以用作**数据库、缓存和消息中间件**。
    *   关键点在于**内存存储**，这使得它的读写速度非常快（通常是微秒级），远超基于磁盘的传统数据库。
    *   它支持**多种数据结构**，如字符串（Strings）、哈希（Hashes）、列表（Lists）、集合（Sets）、有序集合（Sorted Sets）、位图（Bitmaps）、 HyperLogLogs 等，这为各种应用场景提供了灵活性。
    *   数据**持久化**机制（RDB快照和AOF日志）保证了即使服务器重启，数据也不会完全丢失（根据配置策略）。
    *   支持高可用性（如哨兵 Sentinel）和分布式扩展（如集群 Cluster）。

2.  **在Java应用中的常见应用场景：**
    *   **缓存层 (Caching Layer)：** 这是最常见的应用。Java应用可以将频繁访问但更新不频繁的数据（如数据库查询结果、配置信息、用户会话信息）缓存到Redis中。通过缓存，可以**减轻后端数据库的压力**，**显著提高应用的响应速度**。例如，使用Spring Cache结合Redis实现方法级别的缓存。
    *   **会话管理 (Session Management)：** 将用户的HTTP会话信息存储在Redis中，而不是默认的Tomcat内存或文件中。这使得**应用服务器可以分布式部署**，因为所有服务器都可以访问同一个Redis实例来获取用户会话数据，解决了会话粘滞问题。
    *   **分布式锁 (Distributed Locking)：** 在分布式环境中，多个Java进程可能需要安全地访问共享资源。可以使用Redis的 `SET resource_name my_random_value NX PX 30000` 命令（结合Lua脚本或Redlock算法）来实现**互斥锁**，确保同一时间只有一个进程能执行特定操作。
    *   **计数器与限流 (Counters & Rate Limiting)：** 利用Redis的原子操作（如INCR, INCRBY）可以实现精确的计数器，例如网站访问量统计、用户请求次数统计等。结合TTL（Time To Live）可以方便地实现**接口限流**，防止恶意请求或突发流量压垮服务。
    *   **消息队列/发布订阅 (Message Queue / Pub/Sub)：** Redis的发布订阅功能虽然不是专业的消息队列（没有持久化保证和ACK机制），但可以用于简单的**实时通知、事件驱动**等场景。例如，用户注册成功后发布一个消息，让其他服务（如发送欢迎邮件的服务）订阅并处理。
    *   **排行榜 (Leaderboards)：** 利用有序集合（Sorted Set）数据结构，可以非常高效地实现各种排行榜功能，如按积分、按时间等排序，并快速获取Top N。

**总结：** Redis凭借其高性能、丰富的数据结构和易用性，在Java应用中扮演着越来越重要的角色，尤其是在提升性能、实现分布式协作方面。

---

**面试题 2： 请解释一下Redis的持久化机制RDB和AOF，它们各自的优缺点是什么？在Java应用中，你会如何配置它们？**

**回答要点：**

1.  **RDB (Redis Database) 持久化：**
    *   **原理：** 在指定的时间间隔内，将Redis在内存中的**数据集快照**（Snapshot）保存到磁盘上的一个单独的文件中（通常是 `.rdb` 文件）。保存过程是由**子进程**（`bgsave` 命令触发）完成的，主进程**不阻塞**，对服务的影响较小。
    *   **优点：**
        *   **文件紧凑，适合备份：** RDB文件是一个紧凑的、全量的数据快照，非常适合进行**备份**和**灾难恢复**。
        *   **恢复速度快：** 从RDB文件恢复数据比AOF快得多，因为文件体积通常更小，且数据是全量的。
        *   **性能影响小：** `bgsave` 由子进程完成，主进程只fork一次，之后子进程完成IO操作，对性能影响相对较小。
    *   **缺点：**
        *   **可能丢失数据：** RDB是**定时**保存的快照，如果在两次保存之间Redis发生故障，那么这段时间内的数据**会丢失**。保存间隔越短，丢失的数据越少，但性能开销越大。
        *   **fork开销：** `bgsave` 需要fork子进程，如果Redis数据量非常大，fork操作本身可能会**阻塞**主线程几毫秒甚至更长时间，影响服务。

2.  **AOF (Append Only File) 持久化：**
    *   **原理：** 记录服务器**执行的所有写操作**（如 SET, LPUSH 等）命令。这些命令会被追加到一个日志文件（AOF文件）的末尾。当Redis重启时，它会**重新执行**AOF文件中的所有命令来恢复数据。
    *   **优点：**
        *   **数据更安全，丢失更少：** AOF提供了**多种同步策略**（`appendfsync`配置项：always, everysec, no），其中`everysec`（默认推荐）可以做到**每秒**将缓冲区中的命令同步到AOF文件，即使发生故障，最多也只会丢失**1秒**的数据。`always`模式则完全无数据丢失，但性能最低。
        *   **兼容性更好：** Redis 4.0 后支持**AOF重写 (rewrite)**，可以压缩AOF文件，移除冗余命令，保持文件体积不至于过大。
    *   **缺点：**
        *   **文件体积通常更大：** AOF文件记录的是命令本身，相比RDB的紧凑结构，文件体积通常更大。
        *   **恢复速度可能更慢：** 重启时需要重新执行所有命令，如果AOF文件很大，恢复时间会比RDB长。
        *   **性能开销可能更大：** 每次写命令都需要记录到AOF缓冲区并可能触发fsync，对性能有一定影响（`everysec`和`no`模式影响较小，`always`影响较大）。

3.  **Java应用中的配置建议：**
    *   **推荐组合：** 在生产环境中，**通常建议同时启用RDB和AOF**，以获得最佳的数据安全性和性能平衡。
        *   **RDB：** 配置一个相对较长但合理的保存间隔（例如 `save 900 1` 表示15分钟内至少1个key变化就触发 `bgsave`）。主要用于**灾难恢复和备份**。
        *   **AOF：** 启用AOF，并配置 `appendfsync everysec`。这是**数据安全**的主要保障，平衡了性能和数据丢失风险。
    *   **配置位置：** 这些配置通常在Redis服务器的配置文件 `redis.conf` 中进行设置。
    *   **Java客户端（如Jedis, Lettuce, Redisson）：** Java客户端本身**不直接控制**RDB/AOF的生成和配置。这些机制是Redis服务器层面的。Java应用启动时，只需要确保能够正确连接到配置好持久化策略的Redis服务器实例即可。客户端库可能会提供与持久化相关的健康检查或监控接口，但配置本身是在服务端完成的。

**总结：** 理解RDB和AOF的机制、优缺点以及如何根据业务需求（对数据丢失的容忍度、性能要求、备份策略）进行组合配置，是评估候选人是否具备Redis实战经验的重要方面。同时启用RDB和AOF是生产环境下的常见且推荐的做法。
    """;
    String actualHtml = MarkdownConverter.convertToHtml(markdown);
    // <h3>This is a test</h3>
    // <p>123</p>
    System.out.println(actualHtml);
  }

  @Test
  void printTime() {
    System.out.println(new Date());
    System.out.println(LocalDateTime.now());
  }
}