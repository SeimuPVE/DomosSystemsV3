package modules;

import network.Client;
import rsc.STRINGS;


// Used to create lists of modules.
public class ModulePattern {
    private Client client;

    public String exec(String cmd) {
        return STRINGS.log_module_err;
    }

    protected void clientLogSuccess(String log) {
<<<<<<< HEAD
        client.getSocketWriter().print(STRINGS.log_success + log);
    }

    protected void clientLogError(String log) {
        client.getSocketWriter().print(STRINGS.log_error + log);
=======
        // TODO : add it later, can crash the server if there's not any client.
        // client.sendLogs(STRINGS.log_success + log);
        // client.getWriter().print(STRINGS.log_success + log);
    }

    protected void clientLogError(String log) {
        // TODO : add it later, can crash the server if there's not any client.
        // client.getWriter().print(STRINGS.log_error + log);
>>>>>>> a034d5f88bf925c941b11d08ba8cebdb885d1b39
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public Client getClient() {
        return client;
    }
}
