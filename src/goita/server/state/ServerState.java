package goita.server.state;
import goita.ReadWrite;
import goita.StateMachine;
import goita.server.GoitaServerThread;
import goita.server.ThreadSynchro;

public abstract class ServerState extends StateMachine {
    final GoitaServerThread thread;
    final ReadWrite sc;
    final ThreadSynchro synchro = ThreadSynchro.getInstance();

    ServerState(GoitaServerThread thread) {
        super();
        this.thread = thread;
        sc = thread.getReadWrite();
    }

    @Override
    public ServerState doState() {
        executeState();
        return decideState();
    }

    @Override
    protected abstract ServerState decideState();
}
