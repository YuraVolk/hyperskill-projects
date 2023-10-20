package jsondatabase.server

import java.net.InetAddress
import java.net.ServerSocket
import java.net.SocketException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantReadWriteLock

class Server {
    companion object {
        const val ADDRESS = "127.0.0.1"
        const val PORT = 23456
    }

    init {
        println("Server started!")
    }

    val database = JSONDatabase(this)
    private val lock = ReentrantReadWriteLock()
    val readLock: ReentrantReadWriteLock.ReadLock = lock.readLock()
    val writeLock: ReentrantReadWriteLock.WriteLock = lock.writeLock()
    private val serverSocket = ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))

    fun stop() {
        serverSocket.close()
    }

    fun start() {
        val executor: ExecutorService = Executors.newCachedThreadPool()
        while (!serverSocket.isClosed) {
            try {
                serverSocket.accept().also { if (it != null) executor.submit { ClientThread(it, this) } }
            } catch (e: SocketException) { break }
        }
    }
}

fun main() = Server().start()
