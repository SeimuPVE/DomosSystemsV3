package modules;

import msc.Client;
import rsc.STRINGS;


// Used to create lists of modules.
public class ModulePattern {
    private Client client;

    public String exec(String cmd) {
        return STRINGS.log_module_err;
    }

    protected void clientLogSuccess(String log) {
        client.getWriter().print(STRINGS.log_success + log);
    }

    protected void clientLogError(String log) {
        client.getWriter().print(STRINGS.log_error + log);
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public Client getClient() {
        return client;
    }
}
