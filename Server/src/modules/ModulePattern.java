package modules;

import network.Client;
import rsc.STRINGS;

import java.util.Observable;


// Used to create lists of modules.
public class ModulePattern extends Observable {
    private Client client;

    public String exec(String cmd) {
        return STRINGS.log_module_err;
    }

    protected void clientLogSuccess(String log) {
        if(client != null)
            client.sendLogs(STRINGS.log_success + log);
    }

    protected void clientLogError(String log) {
        if(client != null)
            client.sendLogs(STRINGS.log_error + log);
    }

    protected void clientReturnMessage(String message) {
        if(client != null)
            client.sendLogs(message);
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public Client getClient() {
        return client;
    }
}
