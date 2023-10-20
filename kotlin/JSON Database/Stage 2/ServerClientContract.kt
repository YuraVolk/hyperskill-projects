package jsondatabase

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

interface ServerClientContract {
    val socket: Socket
    val input: DataInputStream
    val output: DataOutputStream
}
